/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import annotation.Column;
import annotation.Table;
import dao.BddObject;
import java.sql.Timestamp;
import java.sql.Date;

/**
 *
 * @author Mamisoa
 */
@Table(name="test")
public class Test extends BddObject{
    @Column
    Integer id;
    @Column
    String text;
    @Column(name="date_insert")
    Date date;
    @Column(name="date_heure")
    Timestamp dateheure;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getDateheure() {
        return dateheure;
    }

    public void setDateheure(Timestamp dateheure) {
        this.dateheure = dateheure;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Test() {
    }

    public Test(int id, String text) {
        this.id = id;
        this.text = text;
    }
    
}
