/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controleurs;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import outils.*;
import modeles.*;

/**
 *
 * @author alain
 */

public class slReservation extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    private String erreur;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String demande;
        String vueReponse = "/home.jsp";
        erreur = "";
        try {
            demande = getDemande(request);
            if (demande.equalsIgnoreCase("reserver.res")) {
                vueReponse = reserverOeuvre(request);
            } else if (demande.equalsIgnoreCase("enregistrerReservation.res")) {
                vueReponse = enregistrerReservation(request);
            } else if (demande.equalsIgnoreCase("listeReservations.res")) {
                vueReponse = listeReservations(request);
            } else if (demande.equalsIgnoreCase("confirmerReservation.res")) {
                vueReponse = confirmerReservation(request);
            } else if (demande.equalsIgnoreCase("supprimerReservation.res")) {
                vueReponse = supprimerReservation(request);
            }
        } catch (Exception e) {
            erreur = e.getMessage();
        } finally {
            request.setAttribute("erreurR", erreur);
            request.setAttribute("pageR", vueReponse); 
            RequestDispatcher dsp = request.getRequestDispatcher("/index.jsp");
            if (vueReponse.contains(".res"))
                dsp = request.getRequestDispatcher(vueReponse);
            dsp.forward(request, response);
        }
    }

    /**
     * Transforme dans la base de données une réservation en Attente
     * en une réservation Confirmée
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String confirmerReservation(HttpServletRequest request) throws Exception {

        Reservation reservation;
        String pageReponse;
        try {
            reservation = new Reservation();
            HttpSession session = request.getSession(true);
            int id_adherent = Integer.parseInt(request.getParameter("idAdherent").toString());
            int id_oeuvre = Integer.parseInt(request.getParameter("idOeuvre").toString());
            reservation.setId_oeuvre(id_oeuvre);
            reservation.setId_adherent(id_adherent);
            
            reservation.confirmer();
            
            //session.setAttribute("", reservation.getStatut());

            return ("listeReservations.res");
        } catch (Exception e) {
            throw e;
        }
    }

    private String supprimerReservation(HttpServletRequest request) throws Exception {
        
        Reservation reservation;
        String pageReponse;
        try {
            reservation = new Reservation();
            HttpSession session = request.getSession(true);
            int id_adherent = Integer.parseInt(request.getParameter("idAdherent").toString());
            int id_oeuvre = Integer.parseInt(request.getParameter("idOeuvre").toString());
            reservation.setId_oeuvre(id_oeuvre);
            reservation.setId_adherent(id_adherent);
            
            reservation.supprimer();

            return ("listeReservations.res");
        } catch (Exception e) {         
            throw e;
        }
    }     
    
    /**
     * liste des réservations en Attente
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String listeReservations(HttpServletRequest request) throws Exception {
        
        Reservation resa;
        String pageReponse;
        try {
            pageReponse = "";
            resa = new Reservation();
            request.setAttribute("lReservationsR", resa.liste());
            //pageReponse = "/liste.jsp";
            

            return ("listereservations.jsp");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Enregistre une réservation et la met en Attente
     * @param request
     * @return
     * @throws Exception
     */
    private String enregistrerReservation(HttpServletRequest request) throws Exception {
        
        Reservation reservation;
        Oeuvre oeuvre;
        Adherent adherent;
        String pageReponse;
        try {
            reservation = new Reservation();
            HttpSession session = request.getSession(true);
            int id_adherent = Integer.parseInt(request.getParameter("lstAdherents").toString());
            int id_oeuvre = Integer.parseInt(request.getParameter("idOeuvre").toString());
            Date date = Date.valueOf(request.getParameter("txtDate").toString());
            
            reservation.setDate_reservation(date);
            
            adherent = Adherent.getAdherentByID(id_adherent);
            oeuvre = Oeuvre.getOeuvreByID(id_oeuvre);
            
            reservation.setId_oeuvre(oeuvre.getId_oeuvre());
            reservation.setId_adherent(adherent.getId_adherent());
            
            reservation.setStatut("Attente");
            
            reservation.reserver();
            
            
            return ("/listeReservations.res");
        } catch (Exception e) {
            erreur = e.getMessage();
            //if(erreur.contains("PRIMARY"))
                //erreur = "L'oeuvre " + titre + " a déjà été réservée pour le : " + date + " !";            
            throw new Exception(erreur);
        }
    }

    /**
     * Lit une oeuvre, l'affiche et initialise la liste des adhérents
     * pour pouvoir saisir une réservation :
     * Saisie date et sélection de l'adhérent
     * @param request
     * @return
     * @throws Exception
     */
    private String reserverOeuvre(HttpServletRequest request) throws Exception {
        
        Reservation reservation;
        Oeuvre oeuvre;
        String pageReponse;
        try {
            reservation = new Reservation();
            
            HttpSession session = request.getSession(true);
            int id_oeuvre = Integer.parseInt(request.getParameter("idOeuvre").toString());
            reservation.setId_oeuvre(id_oeuvre);
            
            oeuvre = Oeuvre.getOeuvreByID(reservation.getId_oeuvre());
            request.setAttribute("titre", oeuvre.getTitre());
            request.setAttribute("prix", oeuvre.getPrix());
            request.setAttribute("idOeuvre", oeuvre.getId_oeuvre());
            request.setAttribute("lAdherentR", Adherent.liste());
         
            return ("/reservation.jsp");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Extrait le texte de la demande de l'URL
     * @param request
     * @return String texte de la demande
     */
    private String getDemande(HttpServletRequest request) {
        String demande = "";
        demande = request.getRequestURI();
        demande = demande.substring(demande.lastIndexOf("/") + 1);
        return demande;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
