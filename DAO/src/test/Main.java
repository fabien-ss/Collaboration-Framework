/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package test;

import java.util.List;
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
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
