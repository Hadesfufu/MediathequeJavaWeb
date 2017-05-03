package modeles;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import outils.Utilitaire;

public class Oeuvre {

    private int id_oeuvre;
    private int id_proprietaire;
    private String titre;
    private double prix;
    private Proprietaire proprietaire;

    public Oeuvre() {
    }
    
//    public Oeuvre(int id) {
//        getOeuvreByID(id);
//    }
    // <editor-fold desc="Propriétés"> 
    public int getId_oeuvre() {
        return id_oeuvre;
    }

    public void setId_oeuvre(int id_oeuvre) {
        this.id_oeuvre = id_oeuvre;
    }
    public int getId_proprietaire() {
        return id_proprietaire;
    }

    public void setId_proprietaire(int id_proprietaire) {
        this.id_proprietaire = id_proprietaire;
    }
    public Proprietaire getProprietaire() {
        return proprietaire;
    }
    public void setProprietaire(Proprietaire proprietaire) {
        this.proprietaire = proprietaire;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    // </editor-fold> 

    public static Oeuvre getOeuvreByID(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        Oeuvre oeuvre = null;
        try {
            oeuvre = new Oeuvre();
            
            connection = Utilitaire.connecter();
            ps = connection.prepareStatement("SELECT * FROM oeuvre where id_oeuvre = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                oeuvre.setId_oeuvre(rs.getInt("id_oeuvre"));
                oeuvre.setId_proprietaire(rs.getInt("id_proprietaire"));
                oeuvre.setTitre(rs.getString("titre"));
                oeuvre.setPrix(rs.getDouble("prix"));
                
                oeuvre.setProprietaire(Proprietaire.getProprietaireByID(rs.getInt("id_proprietaire")));
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
            return oeuvre;
        }
        
    }
    
    public static  List<Oeuvre> lister() throws Exception{
        
    
        Connection cnx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Oeuvre> lOeuvre = new ArrayList<Oeuvre>();
        try {
            
            cnx = Utilitaire.connecter();
            ps = cnx.prepareStatement("select * from oeuvre");
            rs = ps.executeQuery();
            while(rs.next()){
                
                Oeuvre oeuvre = new Oeuvre();
                oeuvre.setId_oeuvre(rs.getInt("id_oeuvre"));
                oeuvre.setId_proprietaire(rs.getInt("id_proprietaire"));
                oeuvre.setTitre(rs.getString("titre"));
                oeuvre.setPrix(rs.getDouble("prix"));
                
                oeuvre.setProprietaire(Proprietaire.getProprietaireByID(oeuvre.getId_proprietaire()));
                
                lOeuvre.add(oeuvre);
            }
            
            return lOeuvre;
            
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
                if (cnx != null) {
                    cnx.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }        
        
    public void creer() throws Exception{
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            
            connection = Utilitaire.connecter();
            
            String command = "INSERT INTO oeuvre (id_oeuvre, id_proprietaire, titre, prix) VALUES (?,?,?,?)";
            ps = connection.prepareStatement(command);
            
            ps.setInt(1, this.id_oeuvre);
            ps.setInt(2, this.id_proprietaire);
            ps.setString(3, this.titre);
            ps.setDouble(4, this.prix);
               
            ps.executeUpdate();
            
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
    
    public void modifier() throws Exception{
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            
            connection = Utilitaire.connecter();
            
            String command = "UPDATE oeuvre SET id_proprietaire = ?, titre = ?, prix = ? WHERE id_oeuvre = ?";
            ps = connection.prepareStatement(command);
            
            ps.setInt(1, this.id_proprietaire);
            ps.setString(2, this.titre);
            ps.setDouble(3, this.prix);
            ps.setInt(4, this.id_oeuvre);
            
               
            ps.executeUpdate();
            
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
    
    public void supprimer() throws Exception{
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            
           connection = Utilitaire.connecter();
            
           String command =  "DELETE FROM oeuvre WHERE id_oeuvre =? ";
           ps = connection.prepareStatement(command);
           
           ps.setInt(1, this.id_oeuvre);  
               
           ps.executeUpdate();
            
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
     
    public static  int getMaxIdOeuvre() throws Exception{
        
        CallableStatement cs = null;
        ResultSet rs = null;
        Connection connection = null;
        
        try {
            connection = Utilitaire.connecter();
            cs = connection.prepareCall("{? = call generer_pk(?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            
            cs.setString(2, "OEUVRE");
            
            cs.execute();
            return cs.getInt(1);
            
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
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
