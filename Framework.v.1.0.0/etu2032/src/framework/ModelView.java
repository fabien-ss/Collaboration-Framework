package framework;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class ModelView {
    
    String view;
    HashMap<String , Object> data;
    HashMap<String , Object> sessions = new HashMap<>();

    boolean json = false;
    boolean sessionInvalidate = false;
    boolean redirect = false;

    List<String> removeSession = new ArrayList<>();

    public ModelView(){
        this.setData();
    }
    public ModelView( String view ) throws Exception{
        this.setView(view);
        this.setData();
    }
    public ModelView( String view, boolean redirect ) throws Exception{
        this.setView(view);
        this.setRedirect(redirect);
        this.setData();
    }

    public boolean invalidate(){
        return this.sessionInvalidate;      
    }

    public void invalidateSession( boolean inv ){
        this.sessionInvalidate = inv;
    }

    public void addItem( String key , Object value ){
        this.getData().put(key , value);
    }

    public String getView() {
        return view;
    }

    public void setView(String view) throws Exception{
        // if(!view.contains(".jsp") && !view.contains(".do"))
        //     throw new Exception("You have to insert a jsp file");
        this.view = view;
    }

    public void setData(){
        this.data = new HashMap<String , Object>();
    }
    public void setData( HashMap<String , Object> ob ){
        this.data = ob;
    }
    
    public HashMap<String, Object> getData(){
        return this.data;
    }

    public void setSession( HashMap<String, Object> session ){
        this.sessions = session;
    }
    public HashMap<String, Object> getSession(){
        return this.sessions;
    }

    public void addSession( String name , Object value ){
        this.getSession().put( name , value );
    }

    public void setJson( boolean json ){
        this.json = json;
    }

    public boolean isJson(){
        return this.json;
    }
    public void setRedirect(boolean redirect){
        this.redirect = redirect;
    }
    public boolean isRedirect(){
        return this.redirect;
    }
    
    public void removeSessions( String ...sessions ){
        for(String session : sessions){
            this.getRemoveSession().add(session);
        }
    }

    public void setRemoveSession( List<String> session ){
        this.removeSession = session;
    }

    public List<String> getRemoveSession() {
        return this.removeSession;
    }

}
