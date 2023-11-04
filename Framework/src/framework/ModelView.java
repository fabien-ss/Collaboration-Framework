package framework;

import java.util.HashMap;


public class ModelView {
    String view;
    HashMap<String,Object> data = new HashMap<>();

//SETTERS & GETTERS
    public String getView() {
        return view;
    }

    public void setView(String view) throws Exception {
        if(!view.contains(".jsp"))
            throw new Exception("View should be a jsp page");
        this.view = view;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
//CONSTRUCTOR
    public ModelView(){}
    public ModelView(String view) throws Exception{
        this.setView(view);
    }
//METHOD
    public void addItem(String key , Object value){
        this.getData().put(key,value);
    }



}
