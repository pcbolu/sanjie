package com.bolu.system.util;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pc
 * @date 2019/5/17
 */
public class ReflectUtils {

    /**
     * 获取实体类主键 属性
     *
     * @param clazz
     * @return
     */
    public static Field getIdField(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Field item = null;
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                field.setAccessible(true);
                item = field;
                break;
            }
        }
        if (item == null) {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null) {
                item = getIdField(superclass);
            }
        }
        return item;
    }


    /**
     * 获取对象的主键值
     *
     * @param obj
     * @return
     */
    public static String getIdValue(Object obj) {
        Field field= ReflectUtils.getIdField(obj.getClass());
        String fieldId="";
        if(null==field){
            fieldId="fid";
        }else {
            fieldId=field.getName();
        }
        String idValue= ReflectUtils.getPkValueByName(obj,fieldId).toString();
        return idValue;
    }


    /**
     * 根据属性名称获取实体类主键属性值
     *
     * @param clazz
     * @param pkName
     * @return
     */
    public static Object getPkValueByName(Object clazz, String pkName) {
        try {
            String firstLetter = pkName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + pkName.substring(1);
            Method method = clazz.getClass().getMethod(getter);
            Object value = method.invoke(clazz, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 通过反射将 class1不为空的值赋值给class2
     *
     * @param class1
     * @param class2
     * @throws Exception
     */
    public static void reflectClass1ToClass2(Object class1, Object class2) throws Exception {
        Field[] field = class1.getClass().getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            String name = field[i].getName();
            if ("serialVersionUID".equals(name)) {
                continue;
            }
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            Method m1 = class1.getClass().getMethod("get" + name);
            Object value = m1.invoke(class1);
            if (value != null) {
                Field f = field[i];
                f.setAccessible(true);
                f.set(class2, value);
            }
        }
    }


    /**
     * 获取实体类 @Column 的其中一个属性名称
     *
     * @param clazz
     * @return
     */
    public static Map<String, String> getColumnName(Class<?> clazz) {
        Map<String, String> map = new ConcurrentHashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                /**
                 * 获取字段名
                 */
                Column declaredAnnotation = field.getDeclaredAnnotation(Column.class);
                String column = declaredAnnotation.name();
                map.put("fieldNames", field.getName());
                map.put("column", column);
                break;
            }
        }
        return map;
    }


    /**
     * 获取实体类不为空的 {key(属性值)：value(属性值)}
     * @param object
     * @return
     */
    public static Map<String, Object> getNameAndValue(Object object) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
       // int i=0;
        for (Field field : fields) {
            if(field.isAnnotationPresent(Transient.class)){
                continue;
            }
            //i++;
            //获取字段的属性名称
            String fieldName=field.getName();
            Object fieldValue= getPkValueByName(object,fieldName);
            if(null!=fieldValue){
                map.put(fieldName,fieldValue);
            }
        }
       // System.out.println("总共属性： "+i+ " 个");
        return map;
    }



   /* *//**
     * 通过获取类上的@Table注解获取表名称
     *
     * @param clazz
     * @return
     *//*
    public static Map<String, String> getTableName(Class<?> clazz) {
        Map<String, String> map = new ConcurrentHashMap<>();
        Table annotation = clazz.getAnnotation(Table.class);
        String name = annotation.name();
        String className = clazz.getSimpleName();
        map.put("tableName", name);
        map.put("className", className);
        return map;
    }*/


    /**
     * 通过获取类上的@Table注解获取表名称
     *
     * @param clazz
     * @return
     */
    public static String getTableName(Class<?> clazz) {
        Map<String, String> map = new ConcurrentHashMap<>();
        Table annotation = clazz.getAnnotation(Table.class);
        String name=null;
        if(null!=annotation){
            name=annotation.name();
        }
        if((null==name)||(null!=name&&(name.trim().length())==0)){
            String className = clazz.getSimpleName();
            return className;
        }
        return name;
    }


    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    private String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return  (String)field.get(object);
        } catch (Exception e) {

            return null;
        }
    }
}
