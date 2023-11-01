/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import annotation.Table;
import annotation.Column;
import annotation.PrimaryKey;
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
    public static List<Field> getColumnFields(Object obj){
        List<Field> lst = new ArrayList<>();
        for(Field declaredField : obj.getClass().getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(Column.class)) {
                lst.add(declaredField);
            }
        }
        return lst;
    }
    
    public static String[] getColumns(Object obj){
        List<Field> lst = getColumnFields(obj);
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
        String res = "("; 
        for(int i = 0; i < lst.length; i++){
            res += lst[i];
            if(i < lst.length -1){
                res += ",";
            }
        }
        return res;
    }
    
    //PRIMARY KEY
    public static String getPrimaryKeyName(Object obj){
        List<Field> lst = getColumnFields(obj);
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
        List<Field> lst = getColumnFields(obj);
        for(Field elt : lst){
            if(elt.isAnnotationPresent(PrimaryKey.class))
               return obj.getClass().getMethod("get" + ObjectUtility.capitalize(elt.getName()));
        }
        return null;
    }
    public static Field getPrimaryKeyField(Object obj) throws Exception{
        List<Field> lst = getColumnFields(obj);
        for(Field elt : lst){
            if(elt.isAnnotationPresent(PrimaryKey.class))
                return elt;
        }
        return null;
    }
    public static String[] getPrimaryKeyDetails(Object obj) throws Exception{
        String[] lst = new String[3];
        Field field = getPrimaryKeyField(obj);
        PrimaryKey pk = field.getAnnotation(PrimaryKey.class);
        lst[0] = pk.prefix();
        lst[1] = pk.sequence();
        lst[2] = ""+pk.length();
        return lst;
    }
    //OTHERS (GETTERS AND SETTERS)
    public static Method[] getGettersMethod(Object obj) throws Exception{
        List<Field> list = getColumnFields(obj);
        Method[] res = new Method[list.size()];
        for(int i=0; i<list.size(); i++){
            if( list.get(i).getType().equals(boolean.class))
               res[i] = obj.getClass().getDeclaredMethod("is" + capitalize(list.get(i).getName()), list.get(i).getType()); 
            else
                res[i] = obj.getClass().getDeclaredMethod("get" + capitalize(list.get(i).getName()), list.get(i).getType());
        }
        return res;
    } 
    public static Method[] getSettersMethod(Object obj) throws Exception{
        List<Field> list = getColumnFields(obj);
        Method[] res = new Method[list.size()];
        for(int i=0; i<list.size(); i++)
            res[i] = obj.getClass().getDeclaredMethod("set" + capitalize(list.get(i).getName()), list.get(i).getType());
        return res;
    }
    
}
