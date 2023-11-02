/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

//import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import utils.DaoUtility;

/**
 *
 * @author Mamisoa
 */
public class BddObject<T>  {
    //INSERT
    public void save(Connection con) throws Exception{
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        String query = "INSERT INTO "+DaoUtility.getTableName(this)+DaoUtility.getListColumns(this)+" VALUES (";
        Method[] lst = DaoUtility.getGettersMethod(this);
        for( Method method : lst){
            Class returnParam = method.getReturnType();
            if(returnParam.equals(java.util.Date.class) || returnParam.equals(java.sql.Date.class))
                query += "TO_DATE('"+method.invoke(this, (Object[]) null)+"','YYYY-MM-DD')";
            else
                query += "'"+method.invoke(this, (Object[]) null)+"'"; 
            query = query + ",";
        }
        query = query.substring(0, query.lastIndexOf(','));
        query = query + ")";
        Statement stmt =  con.createStatement();
        stmt.executeUpdate(query);
        if( state == true) con.close();
    }
    //DELETE
    
    //UPDATE
    
    //SELECT
    public List<T> findAll(Connection con)throws Exception{
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            System.out.println(con);
            state = true;
        }
        String query = "SELECT * FROM "+DaoUtility.getTableName(this);
        List<T> list = this.fetch(con, query);
        if( state == true) con.close();
        return list;
    }
    
    public T findById(Connection con, Object id)throws Exception{
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            System.out.println(con);
            state = true;
        }
        String query = "SELECT * FROM "+DaoUtility.getTableName(this)+" WHERE "+DaoUtility.getPrimaryKeyName(this)+" = '"+id+"'";
        T obj = this.fetch(con, query).get(0);
        if( state == true) con.close();
        return obj;
    }
    
    public List<T> executeQuery(Connection con, String query, Object obj) throws Exception{
        List<T> list = new ArrayList<>();
        System.out.println(query);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List<Field> fields = DaoUtility.getColumnFields(obj);
        Method[] methods = DaoUtility.getSettersMethod(obj);
        while( rs.next() ){
            T now = this.convertToObject(rs, fields, methods, obj);
            list.add(now);
        }
        return list;
    }
    
    public List<T> fetch( Connection con, String query ) throws Exception{
        List<T> list = new ArrayList<>();
        System.out.println(query);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List<Field> fields = DaoUtility.getColumnFields(this);
        Method[] methods = DaoUtility.getSettersMethod(this);
        while( rs.next() ){
            T now = this.convertToObject(rs, fields, methods);
            list.add(now);
        }
        return list;
    }
    
    private T convertToObject(ResultSet resultSet, List<Field> fields, Method[] methods, Object obj) throws Exception{
        Object object = obj.getClass().getDeclaredConstructor().newInstance();
        for( int i = 0; i < fields.size() ; i++ ){
            String name = DaoUtility.getName(fields.get(i));
            Method method = methods[i];
            Object value = resultSet.getObject( name , fields.get(i).getType());
            method.invoke(object, value);
        }
        return (T) object;
    }
    
    private T convertToObject(ResultSet resultSet, List<Field> fields, Method[] methods) throws Exception{
        Object object = this.getClass().getDeclaredConstructor().newInstance();
        for( int i = 0; i < fields.size() ; i++ ){
            String name = DaoUtility.getName(fields.get(i));
            Method method = methods[i];
            Object value = resultSet.getObject( name , fields.get(i).getType());
            method.invoke(object, value);
        }
        return (T) object;
    }
    
    
}
