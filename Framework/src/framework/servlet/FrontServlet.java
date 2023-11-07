/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package framework.servlet;

import framework.Mapping;
import framework.ModelView;
import framework.annotation.Url;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mamisoa
 */
public class FrontServlet extends HttpServlet{
    HashMap<String,Mapping> mappingUrls;

    public HashMap<String, Mapping> getMappingUrls() {
        return mappingUrls;
    }

    public void setMappingUrls(HashMap<String, Mapping> mappingUrls) {
        this.mappingUrls = mappingUrls;
    }
    
    public List<Class<?>> findClasses(File directory, String packageName) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()){
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files){
            if (file.isDirectory()){
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            }else if (file.getName().endsWith(".class")){
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public List<Class<?>> getClasses(String packageName) throws Exception{
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        System.out.println(path);
        Enumeration<URL> resources = classLoader.getResources(path);
        ArrayList<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()){
            URL resource = resources.nextElement();
            URI uri = new URI(resource.toString());
            dirs.add(new File(uri.getPath()));
        }
        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs){
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }
    
        @Override
        public void init() throws ServletException{
        HashMap<String,Mapping> temp = new HashMap< >();
        try{
            List<String> list = getClasses(getInitParameter("modelPackage").trim());
            for(String element : list){
               Method[] methods = Class.forName(element).getDeclaredMethods();
               for(Method method : methods){
                   if(method.isAnnotationPresent(Url.class)){
                       Url annotation = method.getAnnotation(Url.class);
                       temp.put(annotation.url(),new Mapping(element ,method.getName()));
                   }
               }
            }
            this.setMappingUrls(temp);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e){
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, e);
        }
    }
        
    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>FrontServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>Servlet FrontServlet at " + request.getContextPath() + "</h3>");
        try{
            String[] values = request.getRequestURI().split("/");
            String key = values[values.length-1];
            out.print("<p>");
            out.println(this.getMappingUrls());
            out.print("</p>");
            if(this.getMappingUrls().containsKey(key)){
                Mapping map = this.getMappingUrls().get(key);
                String methodString = map.getMethods();
                Object obj = Class.forName(map.getClassName()).getConstructor().newInstance();
                Method[] listMethod = obj.getClass().getDeclaredMethods();
                Method method = null;
                int i = 0;
                while(!listMethod[i].getName().equals(methodString)){
                    i++;
                }
                method = listMethod[i];
                List<Object> args = new ArrayList<>();
                // Verify if there are data sent
//                if(request.getParameterNames().nextElement() != null){
//                    obj = setDynamic(request , map.getClassName() , obj);
//                    args = getFunctionArgument( request , m);
//
//                }
                ModelView view = (ModelView) method.invoke( obj , (Object[]) args.toArray());if(view.getData() != null){
                    for(String dataKey : view.getData().keySet()){
                        request.setAttribute(dataKey , view.getData().get(dataKey));
                        out.print("<p>");
                        out.println(dataKey);
                        out.print("</p>");
                    }
                }
                request.getRequestDispatcher(view.getView()).forward(request,response);
        }
        }catch(Exception e){
            e.printStackTrace(out);
        }
        out.println("</body>");
        out.println("</html>");
    }

}
