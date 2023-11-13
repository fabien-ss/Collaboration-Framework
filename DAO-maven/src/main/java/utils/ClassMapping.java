/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.HashMap;
/**
 *
 * @author Mamisoa
 */
public class ClassMapping {
    
    public static HashMap<String, Class> getClassMap(){
        HashMap<String, Class> mapping = new HashMap<>();
        
        mapping.put("int", Integer.class);
        mapping.put("Integer", Integer.class);
        mapping.put("double", Double.class);
        mapping.put("Double", Double.class);
        mapping.put("Float", Float.class);
        mapping.put("Float", Float.class);
        mapping.put("boolean", Boolean.class);
        mapping.put("Boolean", Boolean.class);
        mapping.put("byte", Byte.class);
        mapping.put("Byte", Byte.class);
        
        return mapping;
    } 
}
