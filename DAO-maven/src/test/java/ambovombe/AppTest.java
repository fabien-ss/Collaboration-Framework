package ambovombe;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;

import annotation.Column;
import annotation.PrimaryKey;
import annotation.Table;
import dao.BddObject;
import dao.DbConnection;

/**
 * Unit test for simple App.
 */
@Table(name = "test")
public class AppTest extends BddObject
{
    
    @Column(name = "nom")
    String nom;
    @Column(name = "prenom")
    String prenom;
    @Column(name = "age")
    Integer age;
    @Column(name = "moment")
    Timestamp date;
    @PrimaryKey(prefix = "T", sequence = "seq_test", length = 7)
    @Column(name = "id")
    String id;
    @Column(name = "c")
    public String C;
    @Column(name = "t")
    int t;

    public AppTest(){}
    public AppTest(String nom, String prenom, Integer a, Timestamp date) {
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setAge(a);
        this.setDate(date);
    }
    public void setT(int t) {
        this.t = t;
    }
    public int getT() {
        return t;
    }
    public String getC() {
        return C;
    }
    public void setC(String c) {
        C = c;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Integer getAge() {
        return age;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }
    public Timestamp getDate() {
        return date;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    public static void main(String[] args) throws Exception {

        Connection connection = new DbConnection().connect();
        connection.setAutoCommit(false);
        AppTest app = new AppTest("Propla", "coco",null, new Timestamp(65656));
        AppTest app2 = new AppTest("Fabien","New fabien updated", 48, new Timestamp(515154));
        app2.setT(2324);
       // app.setT(1);
        app.save(connection);
        connection.commit();
     //   app.setId(null);
        app.update(null, app2);
        connection.commit();
        List<AppTest> apps = app.findWhere(connection);
        System.out.println("size = " + apps.size());
        System.out.println(apps.get(0).getAge());
        System.out.println(apps.get(0).getId());
        System.out.println(apps.get(0).getC());
        System.out.println(apps.get(0).getT());

        connection.close();
    }
}
