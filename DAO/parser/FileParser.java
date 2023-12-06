/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Mamisoa
 */
public class FileParser {    
    
    public static String[] splitLine(String string){
        return string.split("=");
    }
    
    public static List<String[]> readFile(String path) throws Exception{
        File myObj = new File(path);
        Scanner myReader = new Scanner(myObj);
        List<String[]> res = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            res.add(splitLine(data));
        }
        myReader.close();
        return res;
    }

    public static String[] listAllFiles(String path){
        File file = new File(path);
        return file.list();
     }
     
     public static String readOneFile(String path) throws Exception{
         StringBuilder builder = new StringBuilder();
         BufferedReader reader = new BufferedReader(new FileReader(path));
         String line;
         while((line = reader.readLine()) != null){
             builder.append(line).append("\n");
         }
         return builder.toString();
     }
}
