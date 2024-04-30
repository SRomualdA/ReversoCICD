package com.reverso.controller;

import com.reverso.controller.affichage.ContAffichage;
import com.reverso.controller.formulaire.ContFormulaire;
import com.reverso.dao.ClientDAO;
import com.reverso.dao.ProspectDAO;
import com.reverso.entites.Client;
import com.reverso.entites.Prospect;
import com.reverso.exception.ReException;
import com.reverso.vue.Accueil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.reverso.dao.ClientDAO.findByName;
import static com.reverso.dao.ProspectDAO.findByRS;


public class ControllerAccueil {

    /**
     * Initialise l'interface graphique de l'accueil.
     */
    public static void init(){
        Accueil accueil = new Accueil();
        accueil.setVisible(true);
    }

    public static void handleButtonOKClicked(String selectedType, boolean isModification, boolean isSuppression, String selectedItem) throws Exception {
        if (isModification || isSuppression) {
            handleModificationOrSuppression(selectedType, selectedItem, isModification, isSuppression);
        } else {
            // Vérifie le type sélectionné et définit l'entité en conséquence
            Object entity = null;
            if ("client".equals(selectedType)) {
                entity = new Client();
            } else if ("prospect".equals(selectedType)) {
                entity = new Prospect();
            }
            // Appel de handleFormulaire avec l'entité appropriée pour une nouvelle inscription
            handleFormulaire(selectedType, entity, false, false);
        }
    }

    private static void handleModificationOrSuppression(String selectedType, String selectedItem, boolean isModification, boolean isSuppression) throws Exception {
        if ("client".equals(selectedType)) {
            Client client = findByName(selectedItem);
            ContFormulaire.afficherFormulaire(client, isModification, isSuppression);
        } else if ("prospect".equals(selectedType)) {
            Prospect prospect = findByRS(selectedItem);
            ContFormulaire.afficherFormulaire(prospect, isModification, isSuppression);
        }
    }



    public static void handleFormulaire(String selectedType, Object entity, boolean isModification, boolean isSuppression) {
        if ("client".equals(selectedType)) {
            ContFormulaire.afficherFormulaire(entity, isModification, isSuppression);
        } else if ("prospect".equals(selectedType)) {
            ContFormulaire.afficherFormulaire(entity, isModification, isSuppression);
        }
    }

    public static void handleAffichage(String selectedType) throws Exception {
        if ("client".equals(selectedType)) {
            ContAffichage.Affichage(selectedType);
        } else if ("prospect".equals(selectedType)) {
            ContAffichage.Affichage(selectedType);
        }
    }

    /**
     * Récupère la liste de tous les clients.
     *
     * @return la liste de tous les clients
     * @throws ReException si une erreur survient lors de la récupération des clients
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     */
    public static ArrayList<Client> boxClient() throws Exception {
        return ClientDAO.findAll();
    }

    /**
     * Récupère la liste de tous les prospects.
     *
     * @return la liste de tous les prospects
     * @throws ReException si une erreur survient lors de la récupération des prospects
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     */
    public static ArrayList<Prospect> boxProspect() throws Exception {
        return ProspectDAO.findAll();
    }
}
