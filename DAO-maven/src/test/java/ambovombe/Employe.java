package ambovombe;

import annotation.Column;
import annotation.PrimaryKey;
import annotation.Table;
import dao.BddObject;
import dao.DbConnection;

import java.sql.Connection;
import java.sql.Timestamp;

import java.util.List;

@Table(name = "EMPLOYE")
public class Employe extends BddObject{

    @PrimaryKey(prefix = "EMP", sequence = "seq_emp", length = 7)
    @Column(name = "id_employe")
    String idEmploye;
    @Column(name = "nom")
    String nom;
    @Column(name = "prenom")
    String prenom;
    @Column(name = "date_enregistrement")
    Timestamp dateEnregistrement;
    @Column(name = "mail")
    String mail;

    public Employe(){}

    public Employe(String nom, String prenom, String mail) {
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setDateEnregistrement(new Timestamp(System.currentTimeMillis()));
        this.setMail(mail);
    }

    public String getIdEmploye() {
        return idEmploye;
    }
    public void setIdEmploye(String idEmploye) {
        this.idEmploye = idEmploye;
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
    public Timestamp getDateEnregistrement() {
        return dateEnregistrement;
    }
    public void setDateEnregistrement(Timestamp dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public static void main(String[] args) throws Exception {

        Connection connection = new DbConnection().connect();
        connection.setAutoCommit(false);
        
        Employe employe = new Employe("NomTest", "PrenomTest", "mailTest@gmail.com");   
        employe.save(connection); 
        connection.commit();

        System.out.println("Update");
        Employe employe2 = new Employe(null, "xxx", "xxx@gmail.com");
      //  employe.setIdEmploye(null);
      //  employe.setDateEnregistrement(null);
        employe.update(connection, employe2);
        connection.commit();

        List<Employe> employees = employe.findWhere(connection); 
       // List<Employe> employees = employe.findAll(connection);

        for (Employe emp : employees) {
            System.out.println("Nom = "+emp.getNom());
            System.out.println("Prenom = "+emp.getPrenom());
            System.out.println("Date d'enregistrement = "+emp.getDateEnregistrement());
           // emp.delete(connection);
        }
        connection.commit();
        connection.close();
    }
}
