package framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

/**
 * @author Manoary Sarobidy ETU 002032
 * 
 * The Auth annotation is used to 
 * annotate a method or function. 
 * It'll make sure that the method is secured 
 * by an authentification ( like login ) before calling.
 * 
 */

public @interface Auth{
	String user() default "";
}