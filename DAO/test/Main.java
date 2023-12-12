package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import dao.DbConnection;
import dao.GenericDao;

public class Main {
   public static void main(String[] args) throws SQLException{
        Connection con = null;
        try {
            con = DbConnection.connect();
            // List<Test> lst = new Test().findAll(con);
            // for (Test test : lst) {
            //     System.out.println(test.getId() + " " + test.getTeny() + " " + test.getDateInsert());
            // }
            // Test testInsert = new Test();
            
            // testInsert.setDateInsert(Date.valueOf("2023-12-06"));
            // testInsert.setTeny("test n");
            // testInsert.save(con);
            // System.out.println("saved succesfully");
            
            // testInsert.setId(8);
            // testInsert.delete(con);

            // testInsert = testInsert.findById(con, 9);
            // testInsert.setDateHeure(Timestamp.valueOf("2023-12-06 08:00:00"));
            // testInsert.update(con);
            // System.out.println("updated succesfully");

            // Test2 test2 = new Test2();
            // test2.setTeny("Teny");
            // test2.setTeny2("Teny 2");
            // test2.setDateInsert(Date.valueOf("2023-12-06"));
            // test2.setDateHeure(Timestamp.valueOf("2023-12-06 08:00:00"));
            // test2.save(con);
            // System.out.println("saved succesfully");

            // List<Test2> list2 = test2.findAll(con);
            // for (Test2 test : list2) {
            //     System.out.println(test.getId2() + " " + test.getTeny() + " " + test.getDateInsert());
            // }   

            System.out.println("HOHOHOHOHO");
            List<Test3> lstTest = GenericDao.findAll(con, new Test3());
            for (Test3 test : lstTest) {
                System.out.println(test.getTeny3());
            }

            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            con.close();
        }
   } 
}
