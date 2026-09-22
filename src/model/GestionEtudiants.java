package model;

import java.util.ArrayList;

public class GestionEtudiants {

    private ArrayList<Etudiant> etudiants;

    public GestionEtudiants() {
        etudiants = new ArrayList<>();
    }

    // Ajouter
    public void ajouter(Etudiant etudiant) {
        etudiants.add(etudiant);
    }

    // Récupérer tous les étudiants
    public ArrayList<Etudiant> getEtudiants() {
        return etudiants;
    }

    // Supprimer
    public boolean supprimer(String matricule) {

        for (Etudiant etudiant : etudiants) {

            if (etudiant.getMatricule().equalsIgnoreCase(matricule)) {

                etudiants.remove(etudiant);

                return true;
            }
        }

        return false;
    }

    // Rechercher
    public ArrayList<Etudiant> rechercher(
            String matricule,
            String nom,
            String parcours,
            String statut) {

        ArrayList<Etudiant> resultats = new ArrayList<>();

        for (Etudiant etudiant : etudiants) {

            boolean ok = true;

            // Matricule
            if (matricule != null && !matricule.isEmpty()) {

                if (!etudiant.getMatricule()
                        .toLowerCase()
                        .contains(matricule.toLowerCase())) {

                    ok = false;
                }
            }

            // Nom
            if (nom != null && !nom.isEmpty()) {

                if (!etudiant.getNom()
                        .toLowerCase()
                        .contains(nom.toLowerCase())) {

                    ok = false;
                }
            }

            // Parcours
            if (parcours != null
                    && !parcours.isEmpty()
                    && !parcours.equals("-- Tous --")) {

                if (!etudiant.getParcours()
                        .equalsIgnoreCase(parcours)) {

                    ok = false;
                }
            }

            // Statut
            if (statut != null
                    && !statut.isEmpty()
                    && !statut.equals("-- Tous --")) {

                if (!etudiant.getStatut()
                        .equalsIgnoreCase(statut)) {

                    ok = false;
                }
            }

            if (ok) {
                resultats.add(etudiant);
            }
        }

        return resultats;
    }

    // Modifier
    public boolean modifier(
            String matricule,
            Etudiant nouvelEtudiant) {

        for (int i = 0; i < etudiants.size(); i++) {

            if (etudiants.get(i)
                    .getMatricule()
                    .equalsIgnoreCase(matricule)) {

                etudiants.set(i, nouvelEtudiant);

                return true;
            }
        }

        return false;
    }
}