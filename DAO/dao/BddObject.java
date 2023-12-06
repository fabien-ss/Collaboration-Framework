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
 * @author Mamisoa, fabien
 * @param <T>
 */ 
public class BddObject  {
    List<Field> columnFields;
    List<Method> getters;
    List<Method> setters;
    List<String> columns;
    Method primaryKeyGetter;
    String tableName;
    String primaryKeyName;

    //GETTERS & SETTERS
    
    public List<Field> getColumnFields() {
        return columnFields;
    }

    public void setColumnFields(List<Field> columnFields) {
        this.columnFields = columnFields;
    }

    public List<Method> getGetters() {
        return getters;
    }

    public void setGetters(List<Method> getters) {
        this.getters = getters;
    }

    public Method getPrimaryKeyGetter(){
        return this.primaryKeyGetter;
    }

    public void setPrimaryKeyGetter(Method method){
        this.primaryKeyGetter = method;
    }

    public List<Method> getSetters() {
        return setters;
    }

    public void setSetters(List<Method> setters) {
        this.setters = setters;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }
    
    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    //CONSTRUCTOR
    public BddObject() throws Exception{
        init();
    }

    //METHODS
    public void init() throws Exception{
        this.setColumnFields(DaoUtility.getAllColumnFields(this)); 
        this.setGetters(DaoUtility.getAllGettersMethod(this));
        this.setSetters(DaoUtility.getAllSettersMethod(this));
        this.setPrimaryKeyName(this.getPrimaryKeyName());
        this.setPrimaryKeyGetter(this.getPrimaryKeyGetter());
        this.setTableName(this.getTableName());
        this.setColumns(DaoUtility.getTableColumns(this.getTableName()));
    }

    //INSERT 
    public void save(Connection con) throws Exception{
        boolean state = false;
        try{
            if(con == null){
                con = DbConnection.connect();
                state = true;
            }
            String query = "INSERT INTO " + this.getTableName() + DaoUtility.getListColumns(this)+" VALUES (";
            List<Method> lst = this.getGetters();
            System.out.println(lst);
            for(Method method : lst){
                Class<?> returnParam = method.getReturnType();
                if(method.equals(this.getPrimaryKeyGetter()) && method.invoke(this, (Object[]) null) == null && returnParam.equals(String.class)){
                    query += "'" + constructPK(con) + "'";  
                }else if(method.equals(this.getPrimaryKeyGetter()) && method.invoke(this, (Object[]) null) == null && returnParam.equals(Integer.class)){
                    query += constructPK(con);
                }
                else if(method.invoke(this, (Object[]) null) == null){
                    query += "default";
                }
                else
                    query += "'" + method.invoke(this, (Object[]) null) + "'"; 
                query = query + ", ";
            }
            query = query.substring(0, query.lastIndexOf(','));
            query = query + ")";
            Statement stmt =  con.createStatement();
            stmt.executeUpdate(query);
            }finally {
                if(state == true) con.close();
        }
    }
    
    //DELETE
    public void delete(Connection con) throws Exception {
        boolean state = false;
        try{
            if(con == null){
                con = DbConnection.connect();
                state = true;
            }
            String query = "DELETE FROM " + this.getTableName() +" WHERE " + this.getPrimaryKeyName()  + " = '" + this.getPrimaryKeyGetter().invoke(this, (Object[]) null) + "'" ;
        //    System.out.println(query);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }finally {
            if(state == true) con.close();
        }
    }
    public void deleteById(Connection con, Object id) throws Exception{
        boolean state = false;
        try{
            if(con == null){
                con = DbConnection.connect();
                state = true;
            }
            String query = "DELETE FROM " + this.getTableName() +" WHERE " + this.getPrimaryKeyName()  +" = '" + id +"'";
            // System.out.println(query);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }finally {
                if(state == true) con.close();
        }
    }
    public void deleteWhere(Connection con, String condition) throws Exception {
        boolean state = false;
        try{
            if(con == null){
                con = DbConnection.connect();
                state = true;
            }
            String query = "DELETE FROM " + this.getTableName() + " WHERE " + condition;
            // System.out.println(query);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }finally {
            if(state == true) con.close();
    }    }
    
    //UPDATE
    public void update(Connection con) throws Exception {
        boolean state = false;
        try{
            if(con == null){
                con = DbConnection.connect();
                state = true;
            }
            String query = "UPDATE "+ this.getTableName() +" SET ";
            List<Method> methods = this.getGetters();
            List<Field> fields = this.getColumnFields();
            for( int i = 0; i < methods.size(); i++ )
                query += DaoUtility.getName(fields.get(i)) + " = '" + methods.get(i).invoke(this, (Object[]) null) + "', ";
            query = query.substring(0, query.lastIndexOf(','));
            query += " WHERE " + this.getPrimaryKeyName() +" = '" + this.getPrimaryKeyGetter().invoke( this, (Object[]) null)+"'";
            // System.out.println(query);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }finally{
            if(state == true) con.close();
        }    
    }
    //SELECT
    public <T> List<T> findAll(Connection con)throws Exception{
        boolean state = false;
        try{
            if(con == null){
                con = DbConnection.connect();
                state = true;
            }
            String query = "SELECT * FROM " + this.getTableName();
            System.out.println(query);
            List<T> list = this.fetch(con, query);
            if(state == true) con.close();
            return list;
        }finally {
                if(state == true) con.close();
        }
    }
    public <T> List<T> findAllFromTable(Connection con, String tableName)throws Exception{
        boolean state = false;
        if(con == null){
            con = DbConnection.connect();
            state = true;
        }
        String query = "SELECT * FROM " + tableName;
        List<T> list = this.fetch(con, query);
        if(state == true) con.close();
        return list;
    }
    
    public <T> T findById(Connection con, Object id)throws Exception{
        boolean state = false;
        try{
            if(con == null){
                con = DbConnection.connect();
                state = true;
            }
            String query = "SELECT * FROM " + this.getTableName() + " WHERE " + this.getPrimaryKeyName() + " = '" + id + "'";
            T obj = (T) this.fetch(con, query).get(0);
            // System.out.println(query);
            return (T) obj;
        }
        finally {
            if(state == true) con.close();
        }
    }
    // search line to print
    public <T>  List<T> findWhere(Connection con) throws Exception{
        boolean state = false;
        try{
            if(con == null){
                con = DbConnection.connect();
                state = true;
            }
            String condition = DaoUtility.getConditionByAttributeValue(this);
            String query = "SELECT * FROM " + this.getTableName() + condition;
            // System.out.println(query);
            List<T> lst = this.fetch(con, query);
            return lst;
        }finally{
            if( state == true) con.close();
        }
    }
    
    public <T>  List<T> findWhere(Connection con, String condition) throws Exception {
        boolean state = false;
        try{
            if(con == null){
                con = DbConnection.connect();
                state = true;
            }
            String query = "SELECT * FROM " + this.getTableName() + " WHERE " + condition;
            List<T> lst = this.fetch(con, query);
            return lst;
        }finally {
                if(state == true) con.close();
        }
    }
    
    //OTHERS
    public void executeUpdate(Connection con, String query) throws Exception{
        boolean state = false;   
        try{     
            if(con == null){
                con = DbConnection.connect();
                state = true;
            }
            Statement stmt =  con.createStatement();
            stmt.executeUpdate(query);
        }finally {
                if(state == true) con.close();
        }
    }
    public <T>  List<T> executeQuery(Connection con, String query, Object obj) throws Exception{
        boolean state = false;
        try{
            if(con == null){
                con = DbConnection.connect();
                state = true;
            }
            List<T> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            List<Field> fields = this.getColumnFields();
            List<Method> methods = this.getSetters();
            while( rs.next() ){
                T now = this.convertToObject(con, rs, fields, methods, obj);
                list.add(now);
            }
            return list;
        }finally {
                if(state == true) con.close();
        }
    }
    
    public <T> List<T> fetch( Connection con, String query ) throws Exception{
        List<T> list = new ArrayList<>();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List<Field> fields = this.getColumnFields();
        List<Method> methods = this.getSetters();
        while( rs.next() ){
            T now = this.convertToObject(con, rs, fields, methods);
            list.add(now);
        }
        return list;
    }
    
    private <T> T convertToObject(Connection con, ResultSet resultSet, List<Field> fields, List<Method> methods, Object obj) throws Exception{
        Object object = obj.getClass().getDeclaredConstructor().newInstance();
        List<String> columns = this.getColumns();
        for (String column : columns) {
            for( int i = 0; i < fields.size() ; i++ ){
                if(DaoUtility.getName(fields.get(i)).equals(column)){
                    // String name = DaoUtility.getName(fields.get(i));
                    Method method = methods.get(i);
                    Object value = resultSet.getObject(column);
                    if(value == null){
                        value = ObjectUtility.getPrimitiveDefaultValue(fields.get(i).getType());
                    }
                    method.invoke(object, value);
                }
            }
        }
        return (T) object;
    }
    
    private <T>  T convertToObject(Connection con, ResultSet resultSet, List<Field> fields, List<Method> methods) throws Exception{
        Object object = this.getClass().getDeclaredConstructor().newInstance();        
        List<String> columns = this.getColumns();
        for (String column : columns) {
            for( int i = 0; i < fields.size() ; i++ ){
                if(DaoUtility.getName(fields.get(i)).equals(column)){
                    // String name = DaoUtility.getName(fields.get(i));
                    Method method = methods.get(i);
                    Object value = resultSet.getObject(column);
                    if(value == null){
                        value = ObjectUtility.getPrimitiveDefaultValue(fields.get(i).getType());
                    }
                    method.invoke(object, value);
                }
            }    
        }
        return (T) object;
    }   
        
    public String constructPK(Connection con)throws Exception{
        boolean state = false;
        try{
            if(con == null){
                con = DbConnection.connect();
                state = true;
            }
            String[] detail = DaoUtility.getPrimaryKeyDetails(this);
            if(detail[0].equals("true"))
                return "default";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nextval('" + detail[2] + "')");
            rs.next();
            String isa = ObjectUtility.fillZero(Integer.parseInt(detail[3]), Integer.parseInt(detail[4]), rs.getString(1));
            return detail[1]+isa;
        }finally {
                if(state == true) con.close();
        }
    }

}
