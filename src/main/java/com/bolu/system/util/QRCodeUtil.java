package com.bolu.system.util;

import com.bolu.base.common.PubData;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

public class QRCodeUtil {


    public static  String createQRCode(String path,String name,String content){
        int width = 400;
        int height = 400;
        //制定图片格式
        String format="png";
        //定义二维码的参数
        HashMap map = new HashMap();
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");//编码
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        map.put(EncodeHintType.MARGIN, 2);
        String  imagename= path+"/"+name+".png";
        //生成二维码
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,map);
            Path file = new File(PubData.resPath+imagename).toPath();//二维码存放路径
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagename;
    }


}
