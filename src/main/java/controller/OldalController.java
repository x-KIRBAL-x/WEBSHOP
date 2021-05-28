package controller;

import adatb.Adatbazis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;

public class OldalController {


    @FXML
    private Label hibasadat;
    @FXML
    private Label osszegzo;
    @FXML
    private CheckBox box1;
    @FXML
    private TextField db1;
    @FXML
    private CheckBox box2;
    @FXML
    private TextField db2;
    @FXML
    private CheckBox box3;
    @FXML
    private TextField db3;
    @FXML
    private CheckBox box4;
    @FXML
    private TextField db4;
    @FXML
    private TextField nev;
    @FXML
    private TextField cim;
    @FXML
    private TextField email;

    private int termek1=250000;
    private int termek2=200000;
    private int termek3=35000;
    private int termek4=150000;
    private int azon=0;
    private String neve;
    private String cime;
    private String ecim;
    private int osszeg=0;
    private boolean ok=true;

    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("webshop-mysql");

    //Darabszám konvertálás
    public int osszead(String s){
        int darab = 0;
        try {
            darab = Integer.parseInt(s);
        }catch (Exception e) {
            hibasadat.setText("Hibás kitöltés!");
            ok=false;
        }
        ok=true;
        if (darab<1) {
            hibasadat.setText("Hibás kitöltés!");
            darab=0;
            ok=false;
            return darab;
        }
        return darab;
    }

    //Név helyesség ellenőrzés
    public void helyesnev(TextField nev) {
        ok=true;
        String neve = nev.getText();
        char[] karakter = neve.toCharArray();
        for (char c : karakter) {
            if (!(Character.isLetter(c) || Character.isSpaceChar(c))) {
                ok = false;
            }
        }
    }

    //Adatbázis létrehozás
    public static void createadatb(int id,String nev,String cim, String emailcim, int osszesen) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Adatbazis(id,nev,cim,emailcim,osszesen));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //Adatok lekérdezése
    private static List<Adatbazis> getadatok() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT A FROM Adatbazis A ORDER BY A.osszeg DESC", Adatbazis.class).getResultList();
        } finally {
            em.close();
        }
    }

    //Legnagyobb azonosító keresés
    public int maxID(){
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT A FROM Adatbazis A WHERE id = (SELECT MAX(A.id) FROM Adatbazis A)");
        Adatbazis adat = (Adatbazis) query.getSingleResult();
        Object id = adat.getId();
        if (id == null)
            return 0;
        return (Integer)id;
    }

    //Rendelés leadása
    @FXML
    private void rendel(javafx.event.ActionEvent actionEvent){
        if (box1.isSelected() == true || box2.isSelected() == true || box3.isSelected() == true || box4.isSelected() == true){

            helyesnev(nev);

            if (nev.getText().isEmpty() || cim.getText().isEmpty() || email.getText().isEmpty() || !email.getText().contains("@")
                    || !email.getText().contains(".") || ok == false ) {
                hibasadat.setText("Hibás kitöltés!");
                ok = false;
            }
            else {
                ok=true;
                neve = nev.getText();
                cime = cim.getText();
                ecim = email.getText();
                hibasadat.setText("");

                if (box1.isSelected() == true && ok)
                    osszeg += osszead(db1.getText())*termek1;
                if (box2.isSelected() == true && ok)
                    osszeg += osszead(db2.getText())*termek2;
                if (box3.isSelected() == true && ok)
                    osszeg += osszead(db3.getText())*termek3;
                if (box4.isSelected() == true && ok)
                    osszeg += osszead(db4.getText())*termek4;
                if(ok==false)
                    osszeg = 0;
                osszegzo.setText("Összesen: " + osszeg + " Ft");
            }
        }
        else {
            hibasadat.setText("Hibás kitöltés!");
            ok = false;
        }
        if (ok) {
            try {
                azon= maxID();
            }catch (Exception e){
                System.out.println("");
            }
            azon++;
            System.out.println(neve + " " + cime + " " + ecim + " " + osszeg + " " + azon);
            createadatb(azon,neve,cime,ecim,osszeg);
            box1.setSelected(false);
            box2.setSelected(false);
            box3.setSelected(false);
            box4.setSelected(false);
            db1.setText("1");
            db2.setText("1");
            db3.setText("1");
            db4.setText("1");
            nev.setText("");
            cim.setText("");
            email.setText("");
            osszeg = 0;
        }
    }

    //Adatbázis megnyitása
    public void adatblekerdezese(ActionEvent actionEvent){
        List<Adatbazis> adatok = getadatok();
        for (int i=0;i<adatok.size();i++){
            System.out.println(adatok.get(i));
        }
    }
}
