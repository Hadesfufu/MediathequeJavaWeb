/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Epulapp
 */
public class Adherent {

    public int id;
    public String nom;
    public String prenom;
    
    public Adherent(){
        
    }
    public Adherent(Adherent a){
        id = a.id;
        nom = a.nom;
        prenom = a.prenom;
    }
    public Adherent(int id, String nom, String prenom){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }
}
