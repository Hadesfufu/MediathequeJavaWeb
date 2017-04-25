package modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        Adherent adherent = null;
        try {
            adherent = new Adherent();
            
            connection = Utilitaire.connecter();
            ps = connection.prepareStatement("SELECT * FROM oeuvre where id = ?");
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
    
}
