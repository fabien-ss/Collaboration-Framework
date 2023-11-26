package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.DbConnection;

public class Main {
   public static void main(String[] args) throws SQLException{
        Connection con = null;
        try {
            con = DbConnection.connect();
            List<Test2> lst = new Test2().findAll(con);
            for (Test2 test2 : lst) {
                System.out.println(test2.getId() + " " + test2.getTeny() + " " + test2.getCucu());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            con.close();
        }
   } 
}
