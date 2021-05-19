/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entity;

import java.util.Date;

/**
 *
 * @author splin
 */
public class Participer {
    private int id_participant ;
    private int id_evenement;
    private int nbr_reservation;
    private int paiement;
    private Date date_paiement;

    public Participer() {
    }

    public Participer(int id_participant, int id_evenement, int nbr_reservation, int paiement, Date date_paiement) {
        this.id_participant = id_participant;
        this.id_evenement = id_evenement;
        this.nbr_reservation = nbr_reservation;
        this.paiement = paiement;
        this.date_paiement = date_paiement;
    }

    public int getId_participant() {
        return id_participant;
    }

    public void setId_participant(int id_participant) {
        this.id_participant = id_participant;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public int getNbr_reservation() {
        return nbr_reservation;
    }

    public void setNbr_reservation(int nbr_reservation) {
        this.nbr_reservation = nbr_reservation;
    }

    public int getPaiement() {
        return paiement;
    }

    public void setPaiement(int paiement) {
        this.paiement = paiement;
    }

    public Date getDate_paiement() {
        return date_paiement;
    }

    public void setDate_paiement(Date date_paiement) {
        this.date_paiement = date_paiement;
    }

    @Override
    public String toString() {
        return "Participer{" + "id_participant=" + id_participant + ", id_evenement=" + id_evenement + ", nbr_reservation=" + nbr_reservation + ", paiement=" + paiement + ", date_paiement=" + date_paiement + '}';
    }
    
}
