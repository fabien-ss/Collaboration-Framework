/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import utils.DaoUtility;
import utils.ObjectUtility;

/**
 *
 * @author Mamisoa
 * @param <T>
 */
public class BddObject<T>  {
    //INSERT
    public  void save(Connection con) throws Exception{
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        String query = "INSERT INTO "+DaoUtility.getTableName(this)+DaoUtility.getListColumns(this)+" VALUES (";
        List<Method> lst = DaoUtility.getAllGettersMethod(this);
        for(Method method : lst){
            Class<?> returnParam = method.getReturnType();
            if(method.equals(DaoUtility.getPrimaryKeyGetMethod(this)) && method.invoke(this, (Object[]) null) == null){
                DaoUtility.setPrimaryKey(this).invoke(this, this.constructPK(con));     
                query += "'" + DaoUtility.getPrimaryKeyGetMethod(this).invoke(this, (Object[]) null) + "'";  
            }
            else if(returnParam.equals(java.sql.Date.class))
                query += "TO_DATE('" + method.invoke(this, (Object[]) null) + "', 'YYYY-MM-DD')";
            else
                if(!ObjectUtility.isAtDefaultValue(method, this))
                    query += "'" + method.invoke(this, (Object[]) null) + "'"; 
                else 
                    query += "null";
            query = query + ", ";
        }
        query = query.substring(0, query.lastIndexOf(','));
        query = query + ")";
        System.out.println(query);
        Statement stmt =  con.createStatement();
        stmt.executeUpdate(query);
        if( state == true) con.close();
    }
    
    //DELETE
    public void delete(Connection con) throws Exception {
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        String query = "DELETE FROM " + DaoUtility.getTableName(this)+" WHERE " + DaoUtility.getPrimaryKeyName(this)  + " = '" + DaoUtility.getPrimaryKeyGetMethod(this).invoke(this, (Object[]) null) + "'" ;
//        System.out.println(query);
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        if( state == true) con.close();
    }
    // DELETE By specifyc id
    public void deleteById(Connection con, Object id) throws Exception{
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        String query = "DELETE FROM " + DaoUtility.getTableName(this)+" WHERE " + DaoUtility.getPrimaryKeyName(this)  +" = '" + id +"'";
        System.out.println(query);
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        if( state == true) con.close();
    }
    // Delete by specific conditions
    public void deleteWhere(Connection con, String condition) throws Exception {
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        String query = "DELETE FROM " + DaoUtility.getTableName(this) + " WHERE " + condition;
        System.out.println(query);
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        if( state == true) con.close();
    }
    // search line to delete
    public void deleteWhere(Connection con) throws Exception{
         boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        String condition = DaoUtility.getConditionByAttributeValue(this);
        String query = "DELETE FROM " + DaoUtility.getTableName(this) + condition;
        System.out.println(query);
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        if( state == true) con.close();
    }

    //UPDATE by primary key
    public void update(Connection con) throws Exception {
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        String query = "UPDATE "+ DaoUtility.getTableName(this) +" SET ";
        List<Method> methods = DaoUtility.getAllGettersMethod(this);
        List<Field> fields = DaoUtility.getColumnFields(this.getClass());
        for( int i = 0; i < methods.size(); i++ ){
            Class returnParam = methods.get(i).getReturnType();
            if(ObjectUtility.isAtDefaultValue(methods.get(i), this)) continue;
            if(returnParam.equals(java.util.Date.class) || returnParam.equals(java.sql.Date.class))
                query += DaoUtility.getFieldColumnName(fields.get(i)) + " = TO_DATE('" + methods.get(i).invoke(this, (Object[]) null)+"','YYYY-MM-DD')";
            else
                query += DaoUtility.getFieldColumnName(fields.get(i)) + " = '"+methods.get(i).invoke(this, (Object[]) null)+"'"; 
            query = query + ",";
        }
        query = query.substring(0, query.lastIndexOf(','));
        query += " WHERE " + DaoUtility.getTableName(this) +" = '" + DaoUtility.getPrimaryKeyGetMethod(this).invoke( this, (Object[]) null)+"'";
        System.out.println(query);
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        if( state == true) con.close();
    }

    // search line to update
    public void update(Connection con, Object obj) throws Exception{
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;    
        }
        if(this.getClass() != obj.getClass()) throw new Exception("Class type mismatch");
        String condition = DaoUtility.getConditionByAttributeValue(this);
        DaoUtility.mergeTwoObject(this, obj);  // ObjectA <= ObjectB

        String query = "UPDATE "+ DaoUtility.getTableName(this) +" SET ";
        List<Method> methods = DaoUtility.getAllGettersMethod(this);
        List<Field> fields = DaoUtility.getColumnFields(this.getClass());
        for( int i = 0; i < methods.size(); i++ ){
            Class returnParam = methods.get(i).getReturnType();
            if(ObjectUtility.isAtDefaultValue(methods.get(i), obj)) continue;
            if(returnParam.equals(java.util.Date.class) || returnParam.equals(java.sql.Date.class))
                query += DaoUtility.getFieldColumnName(fields.get(i))  + " = TO_DATE('" + methods.get(i).invoke(this, (Object[]) null)+"','YYYY-MM-DD')";
            else
                query += DaoUtility.getFieldColumnName(fields.get(i)) + " = '"+methods.get(i).invoke(this, (Object[]) null)+"'"; 
            query = query + ",";
        }
        query = query.substring(0, query.lastIndexOf(','));
        query += condition;
        System.out.println(query);
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
        if( state == true) con.close(); 
    }
    
    //SELECT
    public List<T> findAll(Connection con)throws Exception{
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        String query = "SELECT * FROM " + DaoUtility.getTableName(this);
        List<T> list = this.fetch(con, query);
        if( state == true) con.close();
        return list;
    }
    
    public T findById(Connection con, Object id)throws Exception{
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        String query = "SELECT * FROM " + DaoUtility.getTableName(this) + " WHERE " + DaoUtility.getPrimaryKeyName(this) + " = '" + id + "'";
        T obj = this.fetch(con, query).get(0);
        System.out.println(query);
        if( state == true) con.close();
        return (T) obj;
    }

    // search line to print
    public List<T> findWhere(Connection con) throws Exception{
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        String condition = DaoUtility.getConditionByAttributeValue(this);
        String query = "SELECT * FROM " + DaoUtility.getTableName(this) + condition;
        System.out.println(query);
        List<T> lst = this.fetch(con, query);
        if( state == true) con.close();
        return lst;
    }
    
     
    public List<T> findWhere(Connection con, String condition) throws Exception {
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        String query = "SELECT * FROM " + DaoUtility.getTableName(this) + " WHERE " + condition;
        System.out.println(query);
        List<T> lst = this.fetch(con, query);
        if( state == true) con.close();
        return lst;
    }
    
    //OTHERS
    public void executeUpdate(Connection con, String query) throws Exception{
        boolean state = false;        
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        Statement stmt =  con.createStatement();
        stmt.executeUpdate(query);
        if( state == true) con.close();
    }
    public List<T> executeQuery(Connection con, String query, Object obj) throws Exception{
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        List<T> list = new ArrayList<>();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List<Field> fields = DaoUtility.getColumnFields(obj.getClass());
        List<Method> methods = DaoUtility.getAllSettersMethod(obj);
        while( rs.next() ){
            T now = this.convertToObject(rs, fields, methods, obj);
            list.add(now);
        }
        if( state == true) con.close();
        return list;
    }
    
    public List<T> fetch( Connection con, String query ) throws Exception{
        List<T> list = new ArrayList<>();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List<Field> fields = DaoUtility.getColumnFields(this.getClass());
        List<Method> methods = DaoUtility.getAllSettersMethod(this);
        while( rs.next() ){
            T now = this.convertToObject(rs, fields, methods);
            list.add(now);
        }
        return list;
    }
    
    private T convertToObject(ResultSet resultSet, List<Field> fields, List<Method> methods, Object obj) throws Exception{
        Object object = obj.getClass().getDeclaredConstructor().newInstance();
        for( int i = 0; i < fields.size() ; i++ ){
           // try{
                String name = DaoUtility.getName(fields.get(i));
                Method method = methods.get(i);
                Object value = resultSet.getObject(name); //, fields.get(i).getType());
                if(value == null){
                    value = ObjectUtility.getPrimitiveDefaultValue(fields.get(i).getType());
                }
                method.invoke(object, value);
            /* }
            catch(org.postgresql.util.PSQLException e){
                System.out.println(e);
            }*/
        }
        return (T) object;
    }
    
    private T convertToObject(ResultSet resultSet, List<Field> fields, List<Method> methods) throws Exception{
        Object object = this.getClass().getDeclaredConstructor().newInstance();
        for( int i = 0; i < fields.size() ; i++ ){
          // try{
                String name = DaoUtility.getName(fields.get(i));
                Method method = methods.get(i);
                Object value = resultSet.getObject(name);// , fields.get(i).getType());
              //  System.out.println(value+" valeur "+name);
                if(value == null){
                    value = ObjectUtility.getPrimitiveDefaultValue(fields.get(i).getType());
                }
                method.invoke(object, value);
           /*  }
            catch(org.postgresql.util.PSQLException e){
                System.out.println(e);
            }*/
        }
        return (T) object;
    }   
        
    public String constructPK(Connection con)throws Exception{
        boolean state = false;
        if(con == null){
            con = new DbConnection().connect();
            state = true;
        }
        String[] detail = DaoUtility.getPrimaryKeyDetails(this);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT nextval('" + detail[1] + "')");
        rs.next();
        String isa = ObjectUtility.fillZero(Integer.parseInt(detail[2]), Integer.parseInt(detail[3]), rs.getString(1));
        if(state == true) con.close();
        return detail[0]+isa;
    }
}
