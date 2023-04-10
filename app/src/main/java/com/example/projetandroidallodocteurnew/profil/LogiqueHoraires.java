package com.example.projetandroidallodocteurnew.profil;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LogiqueHoraires {

    public LogiqueHoraires() {}

    public ArrayList<ArrayList> associeJourAvecPlagesHoraires(String jour, String horaires, int duree, int nbrMois) {

        ArrayList<String> plagesHoraires = getPlagesHoraires(horaires, duree);
        ArrayList<LocalDate> allDates = getAllDatesOfDay(jour, nbrMois);

        ArrayList<String> listeDateHeureDebut = new ArrayList<>();
        ArrayList<String> listeDateHeureFin = new ArrayList<>();

        for (int i = 0; i < allDates.size(); i++) {
            String tempDate = allDates.get(i).toString();

            for (int y = 0; y < plagesHoraires.size(); y++) {
                String[] tempPlagesHoraires = plagesHoraires.get(y).split("[|]");
                listeDateHeureDebut.add(tempDate + " " + tempPlagesHoraires[0] + ":00");
                listeDateHeureFin.add(tempDate + " " + tempPlagesHoraires[1] + ":00");
            }
        }

        ArrayList<ArrayList> rep = new ArrayList<>();
        rep.add(listeDateHeureDebut);
        rep.add(listeDateHeureFin);

        return rep;
    }

    public ArrayList<ArrayList> associeDateAvecPlagesHoraires(LocalDate date, String horaires, int duree) {

        ArrayList<String> plagesHoraires = getPlagesHoraires(horaires, duree);
        ArrayList<LocalDate> allDates = new ArrayList<>();
        allDates.add(date);

        ArrayList<String> listeDateHeureDebut = new ArrayList<>();
        ArrayList<String> listeDateHeureFin = new ArrayList<>();

        for (int i = 0; i < allDates.size(); i++) {
            String tempDate = allDates.get(i).toString();

            for (int y = 0; y < plagesHoraires.size(); y++) {
                String[] tempPlagesHoraires = plagesHoraires.get(y).split("[|]");
                listeDateHeureDebut.add(tempDate + " " + tempPlagesHoraires[0] + ":00");
                listeDateHeureFin.add(tempDate + " " + tempPlagesHoraires[1] + ":00");
            }
        }

        ArrayList<ArrayList> rep = new ArrayList<>();
        rep.add(listeDateHeureDebut);
        rep.add(listeDateHeureFin);

        return rep;
    }

    public ArrayList<String> getPlagesHoraires(String inputHoraire, int dureeSeance) {
        ArrayList<String> plageHorairesRDV = new ArrayList<>();
        if (inputHoraire.contains("|")) {
            String[] horaires = inputHoraire.split("[|]");
            for (int i = 0; i < horaires.length; i++) {
                String tempHoraires = horaires[i];

                String tempHoraireDebut = tempHoraires.split("-")[0];
                String tempHoraireFin = tempHoraires.split("-")[1];

                ArrayList<String> tempPlageHorairesRDV = getRendezVous(tempHoraireDebut, tempHoraireFin, dureeSeance);
                for (int y = 0; y < tempPlageHorairesRDV.size(); y++) {
                    plageHorairesRDV.add(tempPlageHorairesRDV.get(y));
                }
            }
        } else {
            String horaireDebut = inputHoraire.split("-")[0];
            String horaireFin = inputHoraire.split("-")[1];

            plageHorairesRDV = getRendezVous(horaireDebut, horaireFin, dureeSeance);
        }

        return plageHorairesRDV;
    }
    
    public ArrayList<String> getRendezVous(String horaireDebut, String horaireFin, int dureeSeance) {

        int heureDebut = Integer.parseInt(horaireDebut.split(":")[0]);
        int minuteDebut = Integer.parseInt(horaireDebut.split(":")[1]);
        int heureFin = Integer.parseInt(horaireFin.split(":")[0]);
        int minuteFin = Integer.parseInt(horaireFin.split(":")[1]);

        ArrayList<String> rep = new ArrayList<>();

        int seanceHeureDebut = heureDebut;
        int seanceMinuteDebut = minuteDebut;

        int[] seanceHoraireFin = addDureeHoraire(seanceHeureDebut, seanceMinuteDebut, dureeSeance);
        int seanceHeureFin = seanceHoraireFin[0];
        int seanceMinuteFin = seanceHoraireFin[1];

        while (isSeanceValid(seanceHeureFin, seanceMinuteFin, heureFin, minuteFin)) {
            String stringHeureDebut = "0" + Integer.toString(seanceHeureDebut);
            String stringMinuteDebut = "0" + Integer.toString(seanceMinuteDebut);
            String stringHeureFin = "0" + Integer.toString(seanceHeureFin);
            String stringMinuteFin = "0" + Integer.toString(seanceMinuteFin);

            String seance = stringHeureDebut.substring(stringHeureDebut.length() - 2) + ":" + stringMinuteDebut.substring(stringMinuteDebut.length() - 2) + "|" + stringHeureFin.substring(stringHeureFin.length() - 2) + ":" + stringMinuteFin.substring(stringMinuteFin.length() - 2);
            rep.add(seance);

            seanceHeureDebut = seanceHeureFin;
            seanceMinuteDebut = seanceMinuteFin;
            seanceHoraireFin = addDureeHoraire(seanceHeureDebut, seanceMinuteDebut, dureeSeance);
            seanceHeureFin = seanceHoraireFin[0];
            seanceMinuteFin = seanceHoraireFin[1];
        }

        return rep;
    }

    public int[] addDureeHoraire(int heure, int minute, int duree) {
        int newMinute = minute + duree;

        if (newMinute > 59) {
            heure += 1;
            newMinute = newMinute - 60;
        }

        int[] rep = new int[2];
        rep[0] = heure;
        rep[1] = newMinute;

        return rep;
    }

    public boolean isSeanceValid(int seanceHeureFin, int seanceMinuteFin, int heureFin, int minuteFin) {
        boolean rep = true;

        if (seanceHeureFin > heureFin) {
            rep = false;
        } else if (seanceHeureFin == heureFin) {
            if (seanceMinuteFin > minuteFin) {
                rep = false;
            }
        }

        return rep;
    }

    public ArrayList<LocalDate> getAllDatesOfDay(String day, int monthToAdd) {
        DayOfWeek jour = null;
        switch (day) {
            case "lundi":
                jour = DayOfWeek.MONDAY;
                break;
            case "mardi":
                jour = DayOfWeek.TUESDAY;
                break;
            case "mercredi":
                jour = DayOfWeek.WEDNESDAY;
                break;
            case "jeudi":
                jour = DayOfWeek.THURSDAY;
                break;
            case "vendredi":
                jour = DayOfWeek.FRIDAY;
                break;
            case "samedi":
                jour = DayOfWeek.SATURDAY;
                break;
            case "dimanche":
                jour = DayOfWeek.SUNDAY;
                break;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateDebut = formatter.format(date);
        LocalDate start = LocalDate.parse(dateDebut);
        LocalDate end = start.plusMonths(monthToAdd);

        ArrayList<LocalDate> totalDates = new ArrayList<>();

        LocalDate nextSearched = start;
        int daysToAdvance = 1;
        while (!nextSearched.isAfter(end)) {
            if (nextSearched.getDayOfWeek() == jour) {
                daysToAdvance = 7;
                totalDates.add(nextSearched);
            }
            nextSearched = nextSearched.plusDays(daysToAdvance);
        }

        return totalDates;
    }
}
