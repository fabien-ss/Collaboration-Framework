package test;

import annotation.Column;
import annotation.PrimaryKey;

public class Test3 {
    @PrimaryKey(name = "id_3", autoIncrement = true)
    Integer id3;
    @Column(name = "teny_3")
    String teny3;

//GETTERS AND SETTERS
    public Integer getId3() {
        return id3;
    }
    public void setId3(Integer id3) {
        this.id3 = id3;
    }
    public String getTeny3() {
        return teny3;
    }
    public void setTeny3(String teny3) {
        this.teny3 = teny3;
    }

//CONSTRUCTOR
    public Test3(){}    
}
