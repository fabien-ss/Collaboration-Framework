/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import parser.FileParser;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Mamisoa
 */
public class DbConnection {
    static String datasource;
    static String driver;
    static String username;
    static String password;

    //SETTERS and GETTERS

    public static String getDatasource() {
        return datasource;
    }

    public static void setDatasource(String datasource) {
        DbConnection.datasource = datasource;
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        DbConnection.driver = driver;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DbConnection.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DbConnection.password = password;
    }

    //CONSTRUCTOR
    public DbConnection(){}
    
    //FUNCTION 
    public static void readFile()throws Exception{
        String separator = File.separator;
        String confFile = System.getProperty("user.dir") + separator +"database.conf";
        List<String[]> lst = FileParser.readFile(confFile);
        for(String[] elt : lst ){
            switch (elt[0]) {
                case "datasource":
                    setDatasource(elt[1]);
                    break;
                case "driver":
                    setDriver(elt[1]);
                    break;
                case "username":
                    setUsername(elt[1]);
                    break;
                case "password":
                    setPassword(elt[1]);
                    break;
                default:
                    break;
            }
        }
    }
    
    public static Connection connect()throws Exception{
        readFile(); 
        Class.forName(getDriver());
        Connection con = DriverManager.getConnection(getDatasource(),getUsername(),getPassword());
        return con;
    }
}
