package com.example.projetandroidallodocteurnew.mesRDV.medecin.recyclerview;

public class ItemHoraire2 {

    String dateDebut;
    String dateFin;
    String horaire;
    String emailMedecin;
    String occupe;

    public ItemHoraire2(String dateDebut, String dateFin, String horaire, String occupe, String emailMedecin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.horaire = horaire;
        this.occupe = occupe;
        this.emailMedecin = emailMedecin;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public String getHoraire() {
        return horaire;
    }

    public String getEmailMedecin() {
        return emailMedecin;
    }

    public String getOccupe() {
        return occupe;
    }
}
