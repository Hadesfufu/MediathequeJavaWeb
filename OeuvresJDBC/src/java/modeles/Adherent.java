package modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import outils.Utilitaire;

public class Adherent {
    private int id_adherent;
    private String nom_adherent;
    private String prenom_adherent;

    public Adherent() {
    }
    // <editor-fold desc="Propriétés"> 
    public int getId_adherent() {
        return id_adherent;
    }

    public void setId_adherent(int id_adherent) {
        this.id_adherent = id_adherent;
    }

    public String getNom_adherent() {
        return nom_adherent;
    }

    public void setNom_adherent(String nom_adherent) {
        this.nom_adherent = nom_adherent;
    }

    public String getPrenom_adherent() {
        return prenom_adherent;
    }

    public void setPrenom_adherent(String prenom_adherent) {
        this.prenom_adherent = prenom_adherent;
    }
    // </editor-fold> 
    
    public static Adherent getAdherentByID(int id) {
        System.out.println("modeles.Adherent.getAdherentByID() id : "  + id );
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        Adherent adherent = null;
        try {
            adherent = new Adherent();
            
            connection = Utilitaire.connecter();
            ps = connection.prepareStatement("SELECT * FROM adherent where id_adherent = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                adherent.setId_adherent(rs.getInt("id_adherent"));
                adherent.setNom_adherent(rs.getString("nom_adherent"));
                adherent.setPrenom_adherent(rs.getString("prenom_adherent"));

            }
            
        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(Oeuvre.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return adherent;
        }
    }
    
    public static List<Adherent>  liste() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        Adherent adherent;
        List<Adherent> lAdherents = null;
        try {
            lAdherents = new ArrayList<Adherent>();
            
            connection = Utilitaire.connecter();
            ps = connection.prepareStatement("SELECT * FROM adherent");
            rs = ps.executeQuery();
            while (rs.next()) {
                adherent = new Adherent();
                adherent.setId_adherent(rs.getInt("id_adherent"));
                adherent.setNom_adherent(rs.getString("nom_adherent"));
                adherent.setPrenom_adherent(rs.getString("prenom_adherent"));
                
                lAdherents.add(adherent);
            }
            return (lAdherents);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
}
