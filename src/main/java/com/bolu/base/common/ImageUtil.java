package com.bolu.base.common;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import sun.misc.BASE64Decoder;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ImageUtil {
    private static final Logger logger = Logger.getLogger(ImageUtil.class);

    // 图片类型
    private static List<String> fileTypes = new ArrayList<String>();

    static {
        fileTypes.add(".jpg");
        fileTypes.add(".jpeg");
        fileTypes.add(".bmp");
        fileTypes.add(".gif");
        fileTypes.add(".png");
    }

    /***
     * 高清缩略图
     * @param srcBufferedImage
     * @param targetWidth
     * @param quality
     * @param square
     * @return
     * @throws IOException
     */
    private static byte[] resize(BufferedImage srcBufferedImage, int targetWidth, float quality, boolean square) throws IOException {
        if (quality > 1) {
            throw new IllegalArgumentException(
                    "Quality has to be between 0 and 1");
        }
        if (square) {
            // 正方形，需要提前进行裁剪
            int width = srcBufferedImage.getWidth();
            int height = srcBufferedImage.getHeight();
            if (width > height) {
                int x = (width - height) / 2;
                srcBufferedImage = srcBufferedImage.getSubimage(x, 0, height,
                        height);
            } else if (width < height) {
                int y = (height - width) / 2;
                srcBufferedImage = srcBufferedImage.getSubimage(0, y, width,
                        width);
            }
        }

        Image resizedImage = null;
        int iWidth = srcBufferedImage.getWidth();
        int iHeight = srcBufferedImage.getHeight();

        if (iWidth > iHeight) {
            resizedImage = srcBufferedImage.getScaledInstance(targetWidth,
                    (targetWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
        } else {
            resizedImage = srcBufferedImage.getScaledInstance(
                    (targetWidth * iWidth) / iHeight, targetWidth,
                    Image.SCALE_SMOOTH);
        }

        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = {0, softenFactor, 0, softenFactor,
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0};
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);

        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(1.0F); // Highest quality
        // Write the JPEG to our ByteArray stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageOutputStream imageOutputStream = ImageIO
                .createImageOutputStream(byteArrayOutputStream);
        writer.setOutput(imageOutputStream);
        writer.write(null, new IIOImage(bufferedImage, null, null), param);

        return byteArrayOutputStream.toByteArray();
    }

    /***
     * 从上传文件获取图片
     * @param oldImage
     * @param targetWidth
     * @param square
     * @return
     * @throws IOException
     */
    private static byte[] resizereq(MultipartFile oldImage, int targetWidth, boolean square) throws IOException {

        ByteArrayInputStream bais = new ByteArrayInputStream(oldImage.getBytes());
        MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);
        BufferedImage src = ImageIO.read(mciis);

        return resize(src, targetWidth, 1f, square);

    }


    /***
     * 从上传文件获取图片
     * @param oldImage
     * @param targetWidth
     * @param square
     * @return
     * @throws IOException
     */
    public static BufferedImage resizereqToImg(MultipartFile oldImage, int targetWidth, boolean square) throws IOException {

        ByteArrayInputStream bais = new ByteArrayInputStream(oldImage.getBytes());
        MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);
        BufferedImage src = ImageIO.read(mciis);

        byte[] imgb = resize(src, targetWidth, 1f, square);

        ByteArrayInputStream in = new ByteArrayInputStream(imgb);
        BufferedImage images = ImageIO.read(in);

        return images;

    }



    /*****
     * 上传2张图
     * @param req
     * @param rsp
     * @param saveDir
     * @param realDir
     * @param sWidth
     * @throws JSONException
     */
    public static void FileUploadPic(HttpServletRequest req, HttpServletResponse rsp,
                                     String saveDir, String realDir, int sWidth) throws JSONException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        MultipartFile multipartFile = multipartRequest.getFile("fileToUpload");

        int random = (int) (Math.random() * 10000);
        long newFileName = System.currentTimeMillis() + random;

        //判断存储路径是否存在，不存在则创建
        File Directoryfile =new File(realDir);
        if  (!Directoryfile .exists()  && !Directoryfile .isDirectory())
        {
            System.out.println("//不存在");
            Directoryfile .mkdir();
        }

        String filename = String.valueOf(newFileName) + ".jpg";
        String filenameb = String.valueOf(newFileName) + "b.jpg";

        String filen = saveDir + filename;
        File file = new File(realDir, filename);

        String filenb = saveDir + filenameb;
        File fileb = new File(realDir, filenameb);

        JSONObject json = new JSONObject();

        try {
            BufferedImage newImage = ImageUtil.resizereqToImg(multipartFile, sWidth, false);
            ImageIO.write(newImage, "jpg", file);

            multipartFile.transferTo(fileb);    //原始图片，不经压缩

            json.put("code", "1");
            json.put("msg", "上传成功");
            json.put("Pic", filen);
            json.put("Picb", filenb);
        } catch (Exception ex) {
            json.put("code", "-1");
            json.put("msg", "上传失败");
            logger.info(ex);
        }

        try {
            rsp.setContentType("text/html;charset=utf-8");
            rsp.getWriter().write(json.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*****
     * 上传视频
     * @param req
     * @param rsp
     * @param saveDir
     * @param realDir
     * @throws JSONException
     */
    public static void Uploadvideo(HttpServletRequest req, HttpServletResponse rsp,
                                   String saveDir, String realDir) throws JSONException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        MultipartFile multipartFile = multipartRequest.getFile("videoToUpload");

        int random = (int) (Math.random() * 10000);
        long newFileName = System.currentTimeMillis() + random;

        String filename = String.valueOf(newFileName) + ".mp4";

        String filen = saveDir + filename;
        File file = new File(realDir, filename);

        JSONObject json = new JSONObject();

        try {
            multipartFile.transferTo(file);

            json.put("code", "1");
            json.put("msg", filen);

        } catch (Exception ex) {
            json.put("code", "-1");
            json.put("msg", "上传失败");
            logger.info(ex);
        }

        try {
            rsp.setContentType("text/html;charset=utf-8");
            rsp.getWriter().write(json.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /***
     * Base64上传图片
     * @param req
     * @param rsp
     * @param base64
     * @param size
     * @param sDir
     * @param PathDir
     * @param json
     * @throws IOException
     * @throws JSONException
     */
    public static void Base64UploadPic(HttpServletRequest req, HttpServletResponse rsp, String base64,
                                       int size, String sDir, String PathDir, JSONObject json) throws IOException, JSONException {

        try {
            if (!base64.equals("") && base64.length() == size) {
                int random = (int) (Math.random() * 10000);
                long newFileName = System.currentTimeMillis() + random;

                String filenameb = String.valueOf(newFileName) + "b.jpg";
                String filename = String.valueOf(newFileName) + ".jpg";

                String filen = sDir + filename;
                String filenb = sDir + filenameb;
                File file = new File(PathDir, filename);
                File fileb = new File(PathDir, filenameb);

                base64 = base64.substring(base64.indexOf(",") + 1);    //需要去掉头部信息

                FileOutputStream outputStream = new FileOutputStream(fileb);    //写原图片

                BASE64Decoder bd64 = new BASE64Decoder();
                byte[] result = bd64.decodeBuffer(base64);    //解码
                for (int i = 0; i < result.length; ++i) {
                    if (result[i] < 0) {
                        //调整异常数据
                        result[i] += 256;
                    }
                }
                outputStream.write(result);
                outputStream.flush();
                outputStream.close();

                ByteArrayInputStream bais = new ByteArrayInputStream(result);
                MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);
                BufferedImage src = ImageIO.read(mciis);

                byte[] imgb = resize(src, 240, 1f, false);
                ByteArrayInputStream in = new ByteArrayInputStream(imgb);
                BufferedImage newImage = ImageIO.read(in);

                ImageIO.write(newImage, "jpg", file);                //写压缩图片

                json.put("Pic", filen);
                json.put("Picb", filenb);

                json.put("code", "1");
                json.put("msg", "上传成功");
            } else {
                json.put("code", "-1");
                json.put("msg", "上传失败");
            }
        } catch (Exception ex) {
            logger.info(ex);
            json.put("code", "-1");
            json.put("msg", "上传失败");
        }

        try {
            //rsp.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            rsp.setContentType("text/html;charset=utf-8");
            rsp.getWriter().write(json.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /****
     *  压缩图片至32k以下
     * @param req
     * @param rsp
     * @param sDir
     * @param PathDir
     * @param json
     * @throws Exception
     */
    public static void ZipPic(HttpServletRequest req, HttpServletResponse rsp,
                              String sDir, String PathDir, JSONObject json) throws Exception {
        int random = (int) (Math.random() * 10000);
        long newFileName = System.currentTimeMillis() + random;
        String filename = String.valueOf(newFileName) + ".jpg";
        String filen = PathDir + filename;

        if (sDir.contains("/")) {    //如果是网上路经，则下载到服务器中
            sDir = download(sDir, PathDir);
        }

        File f = new File(sDir);
        BufferedImage bi = ImageIO.read(f);

        byte[] imgb = resize(bi, 60, 1f, false);
        ByteArrayInputStream in = new ByteArrayInputStream(imgb);
        BufferedImage newImage = ImageIO.read(in);

        File file = new File(PathDir, filename);
        ImageIO.write(newImage, "jpg", file);

        json.put("Pic", "\\uploads\\bbs\\" + filename);

        try {
            //rsp.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            json.put("code", 1);
            rsp.setContentType("text/html;charset=utf-8");
            rsp.getWriter().write(json.toString());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            json.put("code", -1);
        }
    }

    /***
     *
     * @param urlString
     * @param PathDir
     * @return
     * @throws Exception
     */
    public static String download(String urlString, String PathDir) throws Exception {
        int random = (int) (Math.random() * 10000);
        long newFileName = System.currentTimeMillis() + random;
        String filename = String.valueOf(newFileName) + ".jpg";
        String filen = PathDir + "\\" + filename;

        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 输入流
        InputStream is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        OutputStream os = new FileOutputStream(filen);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();

        return filen;
    }


    /**
     * ckeditor文件上传功能，回调，传回图片路径，实现预览效果。
     *
     * @param request
     * @param response
     * @param DirectoryName 文件上传目录：比如upload(无需带前面的/) upload/..
     * @throws IOException
     */
    public static JSONObject ckeditor(HttpServletRequest request, HttpServletResponse response, String DirectoryName)
            throws IOException {
        String fileName = upload(request, DirectoryName);
        // 结合ckeditor功能
        // imageContextPath为图片在服务器地址，如upload/123.jpg,非绝对路径
        String imageContextPath = PubData.baseUrl + "/" + DirectoryName + fileName;
        System.out.println("imageContextPath : " + imageContextPath);
        String Path = request.getContextPath();
        response.setContentType("text/html;charset=UTF-8");
        String callback = request.getParameter("CKEditorFuncNum");
        JSONObject json = new JSONObject();
        json.put("uploaded", 1);
        json.put("fileName", fileName);
        json.put("url", imageContextPath);
        return json;
    }


    /**
     * 图片上传
     *
     * @param request
     * @param DirectoryName 文件上传目录：比如upload(无需带前面的/) upload/news ..
     * @return
     * @throws IllegalStateException
     * @throws IOException
     *
     */
    public static String upload(HttpServletRequest request, String DirectoryName) throws IllegalStateException,
            IOException {
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        // 图片名称
        String fileName = null;
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 记录上传过程起始时的时间，用来计算上传时间
                // int pre = (int) System.currentTimeMillis();
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    // 取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        // 获得图片的原始名称
                        String originalFilename = file.getOriginalFilename();
                        // 获得图片后缀名称,如果后缀不为图片格式，则不上传
                        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
                        if (!fileTypes.contains(suffix)) {
                            continue;
                        }
                        // 获得上传路径的绝对路径地址
                        String realPath = PubData.resPath + DirectoryName;
                        String Path = request.getSession().getServletContext().getRealPath("/" + DirectoryName);
                        System.out.println(realPath);
                        // 如果路径不存在，则创建该路径
                        File realPathDirectory = new File(realPath);
                        if (realPathDirectory == null || !realPathDirectory.exists()) {
                            realPathDirectory.mkdirs();
                        }
                        // 重命名上传后的文件名 111112323.jpg
                        fileName = DateHelper.getDateToStr(new Date(), "yyMMddHHmmss") + suffix;
                        // 定义上传路径 .../upload/111112323.jpg
                        File uploadFile = new File(realPathDirectory + "\\" + fileName);
                        System.out.println(uploadFile);
                        file.transferTo(uploadFile);
                    }
                }
            }
        }
        return fileName;
    }

}
