package test;

import annotation.PrimaryKey;
import annotation.Column;
import annotation.Table;
import dao.BddObject;

@Table(name = "v_test")
public class Test2 extends BddObject{
    
	@Column(name = "teny")
	String teny;
	@Column(name = "cucu")
	Double cucu;
	@PrimaryKey(sequence = "seq_id2")
	@Column(name = "id")
	String id;

    //SETTERS AND GETTERS
        
	public String getTeny(){
		return this.teny;
	}
	public void setTeny(String teny){
		this.teny = teny;
	}
	public Double getCucu(){
		return this.cucu;
	}
	public void setCucu(Double cucu){
		this.cucu = cucu;
	}
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}

    //CONSTRUCTORS

 	public Test2(){}
	public Test2(String teny, Double cucu, String id){
		setTeny(teny);
		setCucu(cucu);
		setId(id);
	}

}
