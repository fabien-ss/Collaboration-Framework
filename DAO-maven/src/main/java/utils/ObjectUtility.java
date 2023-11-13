/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.lang.reflect.Array;
import java.util.List;

/**
 *
 * @author Mamisoa
 */
public class ObjectUtility {
    public static String capitalize(String text){
        return text.substring(0,1).toUpperCase().concat(text.substring(1));
    }
    
    public static String fillZero(int length, int prefixLength, String num){ //Fill the zero Before the number
        int lim = (length - prefixLength) - num.length();
        String zero = ""+0;
        for(int i = 1 ; i <= lim ; i++){
            num = zero+num;
        }
        return num;
    }
    public static <T> T[] toArray(List<T> list, T obj){
//        Object array = Array.newInstance(obj.getClass(), list.size());
        Object[] array = new Object[list.size()];
        for(int i = 0; i < list.size(); i++){
            T temp = (T) list.get(i);
            Array.set(array, i, temp);
        }
        T[] res = (T[]) array;
        return res;
        
    }
}
