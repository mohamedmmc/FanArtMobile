/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entity;
/**
 *
 * @author splin
 */
public class Evenement {
    
    public int Id_evenement;
    public String Titre;
    public String Description;
    public String Locall;
    public String Date_debut;
    public String Date_Fin;
    public int Nombre_place;
    public int Prix;
    public String image;

    public Evenement() {
    }

    public Evenement( String Titre, String Description, String Locall, String Date_debut, String Date_Fin, int Nombre_place, int Prix,String image) {
        
        this.Titre = Titre;
        this.Description = Description;
        this.Locall = Locall;
        this.Date_debut = Date_debut;
        this.Date_Fin = Date_Fin;
        this.Nombre_place = Nombre_place;
        this.Prix = Prix;
        this.image=image;
    }



    public int getId_evenement() {
        return Id_evenement;
    }

    public void setId_evenement(int Id_evenement) {
        this.Id_evenement = Id_evenement;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String Titre) {
        this.Titre = Titre;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getLocall() {
        return Locall;
    }

    public void setLocall(String Locall) {
        this.Locall = Locall;
    }

    public String getDate_debut() {
        return Date_debut;
    }

    public void setDate_debut(String Date_debut) {
        this.Date_debut = Date_debut;
    }

    public String getDate_Fin() {
        return Date_Fin;
    }

    public void setDate_Fin(String Date_Fin) {
        this.Date_Fin = Date_Fin;
    }

    

    public int getNombre_place() {
        return Nombre_place;
    }

    public void setNombre_place(int Nombre_place) {
        this.Nombre_place = Nombre_place;
    }

    public int getPrix() {
        return Prix;
    }

    public void setPrix(int Prix) {
        this.Prix = Prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Evenement{" + "Id_evenement=" + Id_evenement + ", Titre=" + Titre + ", Description=" + Description + ", Locall=" + Locall + ", Date_debut=" + Date_debut + ", Date_Fin=" + Date_Fin + ", Nombre_place=" + Nombre_place + ", Prix=" + Prix + ", image=" + image + '}';
    }


    
    
    
    
}
