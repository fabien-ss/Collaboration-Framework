package packages;

import framework.annotation.Scope;
import framework.ModelView;
import framework.utility.FileUpload;
import framework.annotation.Url;
import framework.annotation.RequestParameter;
import java.util.Vector;
import java.sql.Date;

public class Dept{
	String dept;

	public Dept(){}

	public void setDept(String name){
		this.dept = name;
	}
	public String getDept(){
		return this.dept;
	}

	@Url( url="/insert-dept" )
	public ModelView insertDept() throws Exception{
		ModelView view = new ModelView("DeptList.jsp");
		view.addItem("employe" , this);
		return view;
	}
}