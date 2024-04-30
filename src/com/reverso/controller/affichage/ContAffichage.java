package com.reverso.controller.affichage;

import com.reverso.dao.ClientDAO;
import com.reverso.dao.ProspectDAO;
import com.reverso.vue.affichage.Affichage;

import java.util.ArrayList;
import java.util.Objects;

public class ContAffichage {
    /**
     * Affiche une fenêtre pour visualiser les données des clients ou des prospects en fonction de la raison sociale.
     *
     * @param type  Le type des données à afficher (client ou prospect).
     */
    public static void Affichage(String type) {
        Affichage affichage = new Affichage(type);
        affichage.setVisible(true);
    }

    /**
     * Récupère toutes les données des clients ou des prospects en fonction de la raison sociale.
     *
     * @param type  Le type des données à récupérer (client ou prospect).
     * @return Une liste contenant les données des clients ou des prospects.
     * @throws Exception   Une exception.
     */
    public static ArrayList getData(String type) throws Exception {
        if (Objects.equals(type, "client")) {
            return ClientDAO.findAll();
        } else {
            return ProspectDAO.findAll();
        }
    }
}

