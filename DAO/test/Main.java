/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package test;

import java.util.ArrayList;
import java.util.List;
import utils.ObjectUtility;

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
            List<Test> lst = new Test().findAll(null);
            for( Test elt : lst){
                System.out.println(elt.getId()+" "+elt.getText()+" "+elt.getDate()+" "+elt.getDateheure());
            }
//            List<String> lst = new ArrayList<>();
//            lst.add("huhu");
//            lst.add("hoho");
//            lst.add("1");
//            lst.add("2");
//            Integer[] array = ObjectUtility.toArray(lst, new String("string"));
//            for(Integer elt : array)
//                System.out.println(elt);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
