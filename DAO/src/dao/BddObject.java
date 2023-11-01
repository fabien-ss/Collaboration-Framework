/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Connection;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Mamisoa
 */
public class BddObject {
    
    public <T> List<T> findAll(Connection con)throws Exception{
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            System.out.println(con);
            state = true;
        }
        List<T> list = new ArrayList<T>();
        String query = "SELECT * FROM " + Helper.getTableName(this);
//        System.out.println(query);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            T temp = (T)this.getClass().getDeclaredConstructor().newInstance((Object[]) null);
            List<Field> attribut = Helper.getColumnFields(temp);
            for (int col = 1; col <= rsmd.getColumnCount(); col++) {
                Class<?> fieldType = attribut.get(col-1).getType();
                if(fieldType.getName().equals("java.time.LocalDateTime")){
                    Timestamp tempo = rs.getTimestamp(col);
                    if( tempo != null){
                        temp.getClass().getDeclaredMethod("set" + Helper.capitalize(attribut.get(col-1).getName()) , fieldType ).invoke( temp , tempo.toLocalDateTime());
                    }
                }else if(fieldType.getName().equals("java.sql.Date")){
                    temp.getClass().getDeclaredMethod("set" + Helper.capitalize(attribut.get(col-1).getName()) , fieldType ).invoke( temp , Date.valueOf(rs.getString(col)));
//                }else if(fieldType.getName().equals("etu2060.framework.FileUpload")){
//                    FileUpload fu = new FileUpload();
//                    fu.setName(rs.getString(col));
//                    temp.getClass().getDeclaredMethod("set" + Helper.capitalize(attribut.get(col-1).getName()) , fieldType ).invoke( temp , fu);
                }
                else{
                    Object args = fieldType.getDeclaredConstructor(String.class).newInstance(rs.getString(col));
                    System.out.println(args);
                    temp.getClass().getDeclaredMethod("set" + Helper.capitalize(attribut.get(col-1).getName()) , fieldType ).invoke( temp , args );
                }
            }
            list.add(temp);
        }
        if( state == true) con.close();
        return list;

    }
}
