/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import annotation.Table;
import annotation.Column;
import annotation.PrimaryKey;

import dao.BddObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import static utils.ObjectUtility.capitalize;

/**
 *
 * @author Mamisoa
 */
public class DaoUtility {

    public static void mergeTwoObject(Object o1, Object o2) throws Exception {
        Field[] fields = o2.getClass().getDeclaredFields();
        System.out.println(fields.length);
      //  List<Method> lst = getAllGettersMethod(o2);
        for (int i = 0; i < fields.length; i++) {
            Method method = o2.getClass().getDeclaredMethod("get"+ObjectUtility.capitalize(fields[i].getName()));
            System.out.println(method.getName());
            if(!ObjectUtility.isAtDefaultValue(method, o2)){
                fields[i].setAccessible(true);
                fields[i].set(o1, method.invoke(o2));
                fields[i].setAccessible(false);
            }
        }
    }
    
    public static String getFieldColumnName(Field field) {
        if(field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if(!column.equals("")){
                return column.name();
            }
        }
        return field.getName();
    }

    public static String getConditionByAttributeValue(Object obj) throws Exception{
        String condition = " WHERE ";
        Field[] fields = obj.getClass().getDeclaredFields();
        List<Method> lst = getAllGettersMethod(obj);
        for (int i = 0; i < lst.size(); i++) {
            if(!ObjectUtility.isAtDefaultValue(lst.get(i), obj)){
                condition += getFieldColumnName(fields[i]) + "='" + lst.get(i).invoke(obj) + "' AND ";
            }
        }
        return condition.substring(0, condition.length() - 5);
    }
    
    //TABLE
    public static String getTableName(Object obj){
        if(obj.getClass().isAnnotationPresent(Table.class)){
            Table annotation = obj.getClass().getAnnotation(Table.class);
            if(!annotation.name().equals(""))return annotation.name();
        }
        String str = obj.getClass().getName();
        return str.split("\\.")[str.split("\\.").length - 1];
    }
    
    //COLUMN
    public static List<Field> getColumnFields(Class objClass){
        List<Field> lst = new ArrayList<>();
        while(objClass != BddObject.class){
            for(Field declaredField : objClass.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(Column.class)) {
                    lst.add(declaredField);
                }
            }
            objClass = objClass.getSuperclass();
        }
            return lst;
    }
    
    public static String getName( Field field ){
        if( field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().isEmpty() ){
            return field.getAnnotation(Column.class).name();
        }
        return field.getName();
    }
    
    public static String[] getColumns(Object obj){
        List<Field> lst = getColumnFields(obj.getClass());
        String[] list = new String[lst.size()];
        for(int i = 0; i < lst.size(); i++){
            Column col = lst.get(i).getAnnotation(Column.class);
            if(col.name().equals(""))
                list[i] = ObjectUtility.capitalize( lst.get(i).getName());
            else
                list[i] = ObjectUtility.capitalize(col.name());
        }
        return list;
    }
    
    public static String getListColumns(Object obj){
        String[] lst = getColumns(obj);
        String res = " ("; 
        for(String elt : lst){
            res += elt+",";
        }
        res = res.substring(0, res.lastIndexOf(','));
        return res+")";
    }
    
    //PRIMARY KEY
    public static String getPrimaryKeyName(Object obj){
        List<Field> lst = getColumnFields(obj.getClass());
        for(Field elt : lst){
            if(elt.isAnnotationPresent(PrimaryKey.class)){
                Column col = elt.getAnnotation(Column.class);
                if(col.name().equals(""))
                    return elt.getName();
                return col.name();
            }
        }
        return "";
    }
    public static Method getPrimaryKeyGetMethod(Object obj) throws Exception{
        List<Field> lst = getColumnFields(obj.getClass());
        for(Field elt : lst){
            if(elt.isAnnotationPresent(PrimaryKey.class))
               return obj.getClass().getDeclaredMethod("get" + ObjectUtility.capitalize(elt.getName()), (Class[]) null);
        }
        return null;
    }
    public static Method setPrimaryKey(Object obj) throws Exception{
        List<Field> lst = getColumnFields(obj.getClass());
        for(Field elt : lst){
            if(elt.isAnnotationPresent(PrimaryKey.class))
               return obj.getClass().getDeclaredMethod("set" + ObjectUtility.capitalize(elt.getName()), elt.getType());
        }
        return null;
    }
    public static Field getPrimaryKeyField(Object obj) throws Exception{
        List<Field> lst = getColumnFields(obj.getClass());
        for(Field elt : lst){
            if(elt.isAnnotationPresent(PrimaryKey.class))
                return elt;
        }
        return null;
    }
    public static String[] getPrimaryKeyDetails(Object obj) throws Exception{
        String[] lst = new String[4];
        Field field = getPrimaryKeyField(obj);
        PrimaryKey pk = field.getAnnotation(PrimaryKey.class);
        String prefix = pk.prefix();
        lst[0] = prefix;
        lst[1] = pk.sequence();
        lst[2] = ""+pk.length();
        lst[3] = ""+prefix.length();
        return lst;
    }
    //OTHERS (GETTERS AND SETTERS)
    public static List<Method> getGettersMethod(Class obj) throws Exception{
        List<Field> list = getColumnFields(obj);
        List<Method> res = new ArrayList<>();
        for(Field field : list){
            if( field.getType().equals(boolean.class))
               res.add(obj.getDeclaredMethod("is" + capitalize(field.getName()),  (Class[])null)); 
            else
                res.add(obj.getDeclaredMethod("get" + capitalize(field.getName()),  (Class[])null));
        }
        return res;
    }
    public static List<Method> getAllGettersMethod(Object obj) throws Exception{
        Class objClass = obj.getClass();
        List<Method> res = new ArrayList<>();
        while(objClass != BddObject.class){
            res.addAll(getGettersMethod(objClass));
            objClass = objClass.getSuperclass();
        }
        return res;
    }
    public static List<Method> getSettersMethod(Class obj) throws Exception{
        List<Field> list = getColumnFields(obj);
        List<Method> res = new ArrayList<>();
        for(Field field : list)
            res.add(obj.getDeclaredMethod("set" + capitalize(field.getName()), field.getType()));
        return res;
    }
    public static List<Method> getAllSettersMethod(Object obj) throws Exception{
        Class objClass = obj.getClass();
        List<Method> res = new ArrayList<>();
        while(objClass != BddObject.class){
            res.addAll(getSettersMethod(objClass));
            // System.out.println(res);
            objClass = objClass.getSuperclass();
        }
        return res;
    }
}
