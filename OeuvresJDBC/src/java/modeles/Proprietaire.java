package modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import outils.Utilitaire;

public class Proprietaire {

    

    private int id_proprietaire;
    private String nom_proprietaire;
    private String prenom_proprietaire;
    private String login;
    private String pwd;

    public Proprietaire() {
    }
    
    // <editor-fold desc="Propriétés">
    public int getId_proprietaire() {
        return id_proprietaire;
    }
    public void setId_proprietaire(int id_proprietaire) {
        this.id_proprietaire = id_proprietaire;
    }
    public String getNom_proprietaire() {
        return nom_proprietaire;
    }
    public void setNom_proprietaire(String nom_proprietaire) {
        this.nom_proprietaire = nom_proprietaire;
    }
    public String getPrenom_proprietaire() {
        return prenom_proprietaire;
    }
    public void setPrenom_proprietaire(String prenom_proprietaire) {
        this.prenom_proprietaire = prenom_proprietaire;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getLogin() {
        return this.login;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getPwd() {
        return this.pwd;
    }
    // </editor-fold> 

    public static Proprietaire getProprietaireByID(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        Proprietaire proprietaire = null;
        try {
            proprietaire = new Proprietaire();
            
            connection = Utilitaire.connecter();
            ps = connection.prepareStatement("SELECT * FROM oeuvre where id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                proprietaire.setId_proprietaire(rs.getInt("id_proprietaire"));
                proprietaire.setNom_proprietaire(rs.getString("nom_proprietaire"));
                proprietaire.setPrenom_proprietaire(rs.getString("prenom_proprietaire"));
                proprietaire.setLogin(rs.getString("login"));
                proprietaire.setPwd(rs.getString("pwd"));
                
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
            return proprietaire;
        }
    }
}
