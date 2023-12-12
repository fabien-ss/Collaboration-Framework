/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import annotation.Table;
import dao.DbConnection;
import annotation.Column;
import annotation.PrimaryKey;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import static utils.ObjectUtility.capitalize;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
/**
 *
 * @author Mamisoa
 */
public class DaoUtility {
    
    public static void mergeTwoObject(Object o1, Object o2) throws Exception {
        Field[] fields = o2.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Method method = o2.getClass().getDeclaredMethod("get"+ObjectUtility.capitalize(fields[i].getName()));
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
            if(!column.name().equals("")){
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
                if(fields[i].isAnnotationPresent(Column.class)){
                    condition += getFieldColumnName(fields[i]) + " = '" + lst.get(i).invoke(obj) + "' AND ";
                }
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
    
    public static List<Field> getColumnFields(Class<?> objClass) throws Exception{
        List<Field> lst = new ArrayList<>();
        while(objClass != Object.class){
            for(Field declaredField : objClass.getDeclaredFields()) {
                if(declaredField.isAnnotationPresent(Column.class)){
                    lst.add(declaredField);
                }
            }
            objClass = objClass.getSuperclass();
        }
        return lst;
    }
    
    public static List<Field> getAllColumnFields(Object obj) throws Exception{
        List<Field> lst = new ArrayList<>();
        lst.add(getPrimaryKeyField(obj));
        lst.addAll(getColumnFields(obj.getClass()));
        return lst;
    }
    
    public static String getName( Field field ){
        if( field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().isEmpty() )
            return field.getAnnotation(Column.class).name();
        else if(field.isAnnotationPresent(PrimaryKey.class) && !field.getAnnotation(PrimaryKey.class).name().isEmpty())
            return field.getAnnotation(PrimaryKey.class).name();
        return field.getName();
    }
    
    public static String[] getColumns(Object obj) throws Exception{
        List<Field> lst = getAllColumnFields(obj);
        String[] list = new String[lst.size()];
        list[0] = getPrimaryKeyColumnName(obj);
        for(int i = 1; i < list.length ; i++){
            Column col = lst.get(i).getAnnotation(Column.class);
            if(col.name().equals(""))
                list[i] = ObjectUtility.capitalize( lst.get(i).getName());
            else
                list[i] = ObjectUtility.capitalize(col.name());
        }
        return list;
    }

    public static List<String> getTableColumns(String tableName) throws Exception{
        Connection con = null;
        try{
            con = DbConnection.connect();
            List<String> res = new ArrayList<>();
            String query = "SELECT * FROM "+tableName;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            for(int i = 1; i <= count; i++){
                res.add(rsmd.getColumnName(i));
            }
            return res;
        }finally{
            con.close();
        }
    }
    

    public static List<String> getTableColumns(Connection con, String tableName) throws Exception{
        List<String> res = new ArrayList<>();
        String query = "SELECT * FROM "+tableName;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        for(int i = 1; i <= count; i++){
            res.add(rsmd.getColumnName(i));
        }
        return res;
    }
    
    public static String getListColumns(Object obj) throws Exception{
        String[] lst = getColumns(obj);
        String res = " ("; 
        for(String elt : lst)
            res += elt+",";
        res = res.substring(0, res.lastIndexOf(','));
        return res+")";
    }
    
    //PRIMARY KEY
    public static String getPrimaryKeyName(Object obj) throws Exception{
        Field[] lst = obj.getClass().getDeclaredFields();
        for(Field elt : lst){
            if(elt.isAnnotationPresent(PrimaryKey.class)){
                PrimaryKey col = elt.getAnnotation(PrimaryKey.class);
                if(col.name().equals(""))
                    return elt.getName();
                return col.name();
            }
        }
        return "";
    }
    public static String getPrimaryKeyColumnName(Object obj) throws Exception{
        Field[] lst = obj.getClass().getDeclaredFields();
        for(Field elt : lst){
            if(elt.isAnnotationPresent(PrimaryKey.class)){
                PrimaryKey col = elt.getAnnotation(PrimaryKey.class);
                if(col.name().equals(""))
                    return elt.getName();
                return col.name();
            }
        }
        return "";
    }
    public static Method getPrimaryKeyGetMethod(Object obj) throws Exception{
        Field[] lst = obj.getClass().getDeclaredFields();
        for(Field elt : lst){
            if(elt.isAnnotationPresent(PrimaryKey.class))
               return obj.getClass().getDeclaredMethod("get" + ObjectUtility.capitalize(elt.getName()), (Class[]) null);
        }
        return null;
    }

    public static Method setPrimaryKey(Object obj) throws Exception{
        Field[] lst = obj.getClass().getDeclaredFields();
        for(Field elt : lst){
            if(elt.isAnnotationPresent(PrimaryKey.class))
               return obj.getClass().getDeclaredMethod("set" + ObjectUtility.capitalize(elt.getName()), elt.getType());
        }
        return null;
    }
    public static Field getPrimaryKeyField(Object obj) throws Exception{
        Field[] lst = obj.getClass().getDeclaredFields();
        for(Field elt : lst){
            // System.out.println(elt);
            if(elt.isAnnotationPresent(PrimaryKey.class))
                return elt;
        }
        return null;
    }
    public static String[] getPrimaryKeyDetails(Object obj) throws Exception{
        String[] lst = new String[5];
        Field field = getPrimaryKeyField(obj);
        PrimaryKey pk = field.getAnnotation(PrimaryKey.class);
        String prefix = pk.prefix();
        lst[0] = ""+pk.autoIncrement();
        lst[1] = prefix;
        lst[2] = pk.sequence();
        lst[3] = ""+pk.length();
        lst[4] = ""+prefix.length();
        return lst;
    }
    //OTHERS (GETTERS AND SETTERS)
    public static List<Method> getGettersMethod(Class<?> objClass) throws Exception{
        List<Field> list = getColumnFields(objClass);
        List<Method> res = new ArrayList<>();
        for(Field field : list){
            if(field.getType().equals(boolean.class))
               res.add(objClass.getMethod("is" + capitalize(field.getName()),  (Class[])null)); 
            else{
                res.add(objClass.getMethod("get" + capitalize(field.getName()),  (Class[])null));
            }
        }
        return res;
    }
    public static List<Method> getAllGettersMethod(Object obj) throws Exception{
        Class<?> objClass = obj.getClass();
        List<Method> res = new ArrayList<>();
        res.add(getPrimaryKeyGetMethod(obj));
        res.addAll(getGettersMethod(objClass));
        objClass = objClass.getSuperclass();
        return res;
    }
    public static List<Method> getSettersMethod(Class<?> obj) throws Exception{
        List<Field> list = getColumnFields(obj);
        List<Method> res = new ArrayList<>();
        for(Field field : list)
            res.add(obj.getMethod("set" + capitalize(field.getName()), field.getType()));
        return res;
    }
    public static List<Method> getAllSettersMethod(Object obj) throws Exception{
        Class<?> objClass = obj.getClass();
        List<Method> res = new ArrayList<>();
        res.add(setPrimaryKey(obj));
        res.addAll(getSettersMethod(objClass));
        objClass = objClass.getSuperclass();
        return res;
    }
    
    public static int getColumnCount(ResultSet rs) throws Exception{
        ResultSetMetaData rsmd = rs.getMetaData();
        return rsmd.getColumnCount();
    }
    
    public static HashMap<String, Class<?>> getColumnNameAndType(ResultSet rs) throws Exception{
        HashMap<String, Class<?>> map = new HashMap<>();
        HashMap<Integer, Class<?>> mapping = ClassMapping.getClassMapTable();
        
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        
        for(int i = 1; i <= count; i++){
            Integer key = rsmd.getColumnType(i);
            String column = rsmd.getColumnName(i);
            map.put(column, mapping.get(key));
        }
        return map;
    }   
}
