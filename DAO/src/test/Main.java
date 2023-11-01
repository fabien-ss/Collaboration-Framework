/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package test;

import java.sql.Connection;
import dao.DbConnection;
/**
 *
 * @author Mamisoa
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            Connection connection = new DbConnection().connect();
            System.out.println(connection);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
