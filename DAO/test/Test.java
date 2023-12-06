package test;

import annotation.PrimaryKey;

import java.sql.Date;
import java.sql.Timestamp;

import annotation.Column;
import annotation.Table;
import dao.BddObject;

@Table(name = "test")
public class Test extends BddObject{
    
	@PrimaryKey(name = "id", autoIncrement = true)
	Integer id;
	@Column(name = "text")
	String teny;
	@Column(name = "date_insert")
	Date dateInsert;
	@Column(name = "date_heure")
	Timestamp dateHeure;

    //SETTERS AND GETTERS
        
	public String getTeny(){
		return this.teny;
	}
	public void setTeny(String teny){
		this.teny = teny;
	}
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Date getDateInsert() {
		return dateInsert;
	}
	public void setDateInsert(Date dateInsert) {
		this.dateInsert = dateInsert;
	}
	public Timestamp getDateHeure() {
		return dateHeure;
	}
	public void setDateHeure(Timestamp dateHeure) {
		this.dateHeure = dateHeure;
	}
    //CONSTRUCTORS

 	public Test(){}


}
