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
public class User {
    private int id,num;
    private String nom,prenom,email,type,photo,mdp;

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", num=" + num + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", type=" + type + ", photo=" + photo + ", mdp=" + mdp + '}';
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public int getNum() {
        return num;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getPhoto() {
        return photo;
    }

    public User(int num, String nom, String prenom, String email, String type, String photo) {
        this.num = num;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.type = type;
        this.photo = photo;
    }
    
   
    
}
