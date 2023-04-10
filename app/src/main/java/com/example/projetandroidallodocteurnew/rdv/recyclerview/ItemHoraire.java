package com.example.projetandroidallodocteurnew.rdv.recyclerview;

public class ItemHoraire {

    String dateDebut;
    String dateFin;
    String horaire;
    String emailMedecin;

    public ItemHoraire(String dateDebut, String dateFin, String horaire, String emailMedecin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.horaire = horaire;
        this.emailMedecin = emailMedecin;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public String getEmailMedecin() {
        return emailMedecin;
    }

    public void setEmailMedecin(String emailMedecin) {
        this.emailMedecin = emailMedecin;
    }
}
