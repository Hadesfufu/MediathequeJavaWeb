package modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import outils.Utilitaire;

public class Reservation {

    private int id_oeuvre;
    private int id_adherent;
    private java.util.Date date_reservation;
    private String statut;
    private Adherent adherent;
    private Oeuvre oeuvre;

    public Reservation() {
        this.setAdherent(new Adherent());
        this.setOeuvre(new Oeuvre());
        this.setStatut("");
    }
    /**
     * Initialise l'Adhérent et l'Oeuvre de la Reservation
     * @param id_oeuvre Id de l'oeuvre réservée
     * @param id_adherent Id de l'adhérent réservant
     * @throws Exception
     */
    public Reservation(int id_oeuvre, int id_adherent) throws Exception {
        setId_oeuvre(id_oeuvre);
        setId_adherent(id_adherent);
        //this.setAdherent(new Adherent().lire_Id(id_adherent));
        //this.setOeuvre(new Oeuvre().lire_Id(id_oeuvre));
        this.setDate_reservation(date_reservation);
        this.setStatut(statut);
    }
    
    // <editor-fold desc="Propriétés"> 
    
    public Adherent getAdherent() {
        return adherent;
    }
    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }
    public Oeuvre getOeuvre() {
        return oeuvre;
    }
    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
    public void setOeuvre(Oeuvre oeuvre) {
        this.oeuvre = oeuvre;
    }
    public int getId_oeuvre() {
        return id_oeuvre;
    }
    public void setId_oeuvre(int id_oeuvre) {
        this.id_oeuvre = id_oeuvre;
    }
    public int getId_adherent() {
        return id_adherent;
    }
    public void setId_adherent(int id_adherent) {
        this.id_adherent = id_adherent;
    }
    public java.util.Date getDate_reservation() {
        return date_reservation;
    }
    public void setDate_reservation(java.util.Date date_reservation) throws Exception {
        this.date_reservation = date_reservation;
    }
    // </editor-fold> 

    public List<Reservation>  liste() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        Reservation reservation;
        List<Reservation> lReservations = null;
        try {
            lReservations = new ArrayList<Reservation>();
            
            connection = Utilitaire.connecter();
            ps = connection.prepareStatement("SELECT * FROM reservation");
            rs = ps.executeQuery();
            while (rs.next()) {
                reservation = new Reservation();
                reservation.setId_oeuvre(rs.getInt("id_oeuvre"));
                reservation.setId_adherent(rs.getInt("id_adherent"));
                reservation.setDate_reservation(rs.getDate("date_reservation"));
                reservation.setStatut(rs.getString("statut"));
                
                reservation.setOeuvre(Oeuvre.getOeuvreByID(reservation.getId_oeuvre()));
                reservation.setAdherent(Adherent.getAdherentByID(reservation.getId_adherent()));
                
                lReservations.add(reservation);
            }
            return (lReservations);
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
    
    public void confirmer() throws Exception{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            
            connection = Utilitaire.connecter();
            ps = connection.prepareStatement("UPDATE reservation SET statut='Confirmée' WHERE id_oeuvre=? AND id_adherent=? ");
            ps.setInt(1, this.id_oeuvre);
            ps.setInt(2, this.id_adherent);
            ps.executeUpdate();
            
            this.setStatut("Confirmée");
            
        } catch (Exception e) {
            throw e;
        } finally {
            try {
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

    public void supprimer() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            
            connection = Utilitaire.connecter();
            ps = connection.prepareStatement("DELETE FROM reservation WHERE id_oeuvre=? AND id_adherent=? ");
            ps.setInt(1, this.id_oeuvre);
            ps.setInt(2, this.id_adherent);
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

}
