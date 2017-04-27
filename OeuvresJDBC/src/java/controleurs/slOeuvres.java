/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleurs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTML;
import modeles.*;
import org.apache.tomcat.util.net.SSLSupport;
import outils.Utilitaire;

/**
 *
 * @author arsane
 */
public class slOeuvres extends HttpServlet {

    private String erreur;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String demande;
        String vueReponse = "/home.jsp";
        erreur = "";
        try {
            demande = getDemande(request);
            if (demande.equalsIgnoreCase("login.oe")) {
                vueReponse = login(request);
            } else  if (demande.equalsIgnoreCase("connecter.oe")) {
                vueReponse = connecter(request);
            } else  if (demande.equalsIgnoreCase("deconnecter.oe")) {
                vueReponse = deconnecter(request);
            } else if (demande.equalsIgnoreCase("ajouter.oe")) {
                vueReponse = creerOeuvre(request);
            } else if (demande.equalsIgnoreCase("enregistrer.oe")) {
                vueReponse = enregistrerOeuvre(request);
            } else if (demande.equalsIgnoreCase("modifier.oe")) {
                vueReponse = modifierOeuvre(request);
            } else if (demande.equalsIgnoreCase("catalogue.oe")) {
                vueReponse = listerOeuvres(request);
            } else if (demande.equalsIgnoreCase("supprimer.oe")) {
                vueReponse = supprimerOeuvre(request);
            } else{
                throw(new Exception());
            }
        } catch (Exception e) {
            erreur = e.getMessage();
        } finally {
            request.setAttribute("erreurR", erreur);
            request.setAttribute("pageR", vueReponse);            
            RequestDispatcher dsp = request.getRequestDispatcher("/index.jsp");
            if (vueReponse.contains(".oe"))
                dsp = request.getRequestDispatcher(vueReponse);
            dsp.forward(request, response);
        }
    }

    /**
     * Enregistre une oeuvre qui a été soit créée (id_oeuvre = 0)
     * soit modifiée (id_oeuvre > 0)
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String enregistrerOeuvre(HttpServletRequest request) throws Exception {

        String vueReponse;
        Connection cnx;
        
        try {
            cnx = Utilitaire.connecter();
            

            String command = "INSERT INTO oeuvre (id_oeuvre, id_proprietaire, titre, prix) VALUES (?,?,?,?)";
            
            PreparedStatement pstatement = cnx.prepareStatement(command);
            pstatement.setInt(1, getMaxIdOeuvre());
            pstatement.setInt(2, 4);
            pstatement.setString(3, "ee");
            pstatement.setInt(4, 5);
            pstatement.executeUpdate();
           
            vueReponse = "catalogue.oe";
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }

    public int getMaxIdOeuvre(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        int valeurMax = 0;
        try {
            connection = Utilitaire.connecter();
            ps = connection.prepareStatement("SELECT max(id_oeuvre) as maxId FROM oeuvre");

            rs = ps.executeQuery();
            if (rs.next()) {
                valeurMax = rs.getInt("maxId");
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Oeuvre.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valeurMax+1;
    }
    /**
     * Lit et affiche une oeuvre pour pouvoir la modifier
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String modifierOeuvre(HttpServletRequest request) throws Exception 
    {
        String vueReponse;
        Connection cnx;
        PreparedStatement ps;
        ResultSet rs;
        
//        ArrayList<Proprietaire> lProprietaire = new ArrayList<Proprietaire>();
       
        try 
        {
            cnx = Utilitaire.connecter();
            ps = cnx.prepareStatement("select * from oeuvre where id_oeuvre = '" + request.getParameter("Id")+"' ");
            rs = ps.executeQuery();
            
            Oeuvre oeuvre = new Oeuvre();
            if(rs.next())
            {
                oeuvre.setId_oeuvre(rs.getInt("id_oeuvre"));
                oeuvre.setId_proprietaire(rs.getInt("id_proprietaire"));
                oeuvre.setTitre(rs.getString("titre"));
                oeuvre.setPrix(rs.getDouble("prix"));

                oeuvre.setProprietaire(Proprietaire.getProprietaireByID(oeuvre.getId_proprietaire()));  
            }
            request.setAttribute("titre", "Modifier l'oeuvre "+request.getParameter("Id")+"");    
            request.setAttribute("oeuvre", oeuvre); 
            
            vueReponse = "/oeuvre.jsp";
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Supprimer une oeuvre
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String supprimerOeuvre(HttpServletRequest request) throws Exception {
        String vueReponse;
        try {
//TODO
            vueReponse = "catalogue.oe";           
            return (vueReponse);  
        } catch (Exception e) {
            erreur = e.getMessage();
            //if(erreur.contains("FK_RESERVATION_OEUVRE"))
              //  erreur = "Il n'est pas possible de supprimer l'oeuvre : " + titre + " car elle a été réservée !";            
            throw new Exception(erreur);
        }
    }    
    /**
     * Affiche le formulaire vide d'une oeuvre
     * Initialise la liste des propriétaires
     * Initialise le titre de la page
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String creerOeuvre(HttpServletRequest request) throws Exception 
    {
        String vueReponse;
        Connection cnx;
        PreparedStatement ps;
        ResultSet rs;
        
        ArrayList<Proprietaire> lProprietaire = new ArrayList<Proprietaire>();
        
        try 
        {
            cnx = Utilitaire.connecter();
            ps = cnx.prepareStatement("select * from proprietaire");
            rs = ps.executeQuery();
            while(rs.next())
            {
                Proprietaire proprietaire = new Proprietaire();
                proprietaire.setId_proprietaire(rs.getInt("id_proprietaire"));
                proprietaire.setNom_proprietaire(rs.getString("nom_proprietaire"));
                proprietaire.setPrenom_proprietaire(rs.getString("prenom_proprietaire"));
                
                lProprietaire.add(proprietaire);
            }
            request.setAttribute("lProprietaires", lProprietaire);    
            request.setAttribute("titre", "Ajouter une oeuvre");    

            vueReponse = "/oeuvre.jsp";
            return (vueReponse);
        } 
        catch (Exception e) 
        {
            throw e;
        }
    }

    /**
     * Vérifie que l'utilisateur a saisi le bon login et mot de passe
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String connecter(HttpServletRequest request) throws Exception {
        String vueReponse;
        Connection cnx;
        PreparedStatement ps;
        ResultSet rs;
        try{
            cnx = Utilitaire.connecter();
            ps = cnx.prepareStatement("select * from proprietaire where login = '" + request.getParameter("txtLogin") + "' and pwd = '" + request.getParameter("txtPwd") + "'");
            rs = ps.executeQuery();
            if(rs.next())
                request.getSession(true).setAttribute("userId", request.getParameter("txtLogin"));
            vueReponse = "/home.jsp";
            return (vueReponse);
        } catch (Exception e) {
            
            throw e;
        }
    }

    private String deconnecter(HttpServletRequest request) throws Exception {
        try {
            request.getSession(true).setAttribute("userId", null);
            return ("/home.jsp");
        } catch (Exception e) {
            throw e;
        }
    } 
    
    /**
     * Afficher la page de login
     * @param request
     * @return
     * @throws Exception 
     */
        private String login(HttpServletRequest request) throws Exception {
        try {
            return ("/login.jsp");
        } catch (Exception e) {
            throw e;
        }
    }    
    /**
     * liste des oeuvres pour le catalogue
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String listerOeuvres(HttpServletRequest request) throws Exception {
        
        Connection cnx;
        PreparedStatement ps;
        ResultSet rs;
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
            request.setAttribute("lOeuvres", lOeuvre);    
            return ("/catalogue.jsp");
        } catch (Exception e) {
            e.printStackTrace();
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
