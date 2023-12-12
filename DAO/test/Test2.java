package test;

import annotation.PrimaryKey;
import annotation.Column;
import annotation.Table;

@Table(name = "test_2")
public class Test2 extends Test{
    @PrimaryKey(name = "id_2", autoIncrement = true)
    Integer id2;
    @Column(name = "teny_2")
    String teny2;

//GETTERS AND SETTERS
    public Integer getId2() {
        return id2;
    }
    public void setId2(Integer id2) {
        this.id2 = id2;
    }
    public String getTeny2() {
        return teny2;
    }
    public void setTeny2(String teny2) {
        this.teny2 = teny2;
    }

//CONSTRUCTOR
    public Test2() throws Exception{}
}
