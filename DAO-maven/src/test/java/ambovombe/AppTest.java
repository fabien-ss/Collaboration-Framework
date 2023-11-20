package ambovombe;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;

import annotation.Column;
import annotation.PrimaryKey;
import annotation.Table;
import dao.BddObject;

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
    int age;
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
    public AppTest(String nom, String prenom, int a, Timestamp date) {
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
    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
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
        AppTest app = new AppTest("Jean", "coco",4, new Timestamp(65656));
        AppTest app2 = new AppTest("Jean",null, 0, new Timestamp(515154));
        app2.setT(34);
        Double d = 43.0;
       // app.setT(1);
        app.save(null);
        app.setId(null);
        app.update(null, app2);
        List<AppTest> apps = app.findWhere(null);
        System.out.println("size = " + apps.size());
        System.out.println(apps.get(0).getAge());
        System.out.println(apps.get(0).getId());
        System.out.println(apps.get(0).getC());
        System.out.println(apps.get(0).getT());
    }
}
