package com.reverso.controller.formulaire;

import com.reverso.dao.ClientDAO;
import com.reverso.dao.ProspectDAO;
import com.reverso.entites.Client;
import com.reverso.entites.Prospect;
import com.reverso.exception.DaoException;
import com.reverso.exception.ReException;
import com.reverso.vue.formulaire.Formulaire;

import java.io.IOException;
import java.sql.SQLException;


public class ContFormulaire {
    public static void afficherFormulaire(Object entity, boolean isModification, boolean isSuppression) {
        Formulaire formulaire = new Formulaire();
        if (entity instanceof Client) {
            formulaire.remplirFormulaireClient((Client) entity);
            formulaire.formulaireClient();
        } else if (entity instanceof Prospect) {
            formulaire.remplirFormulaireProspect((Prospect) entity);
            formulaire.formulaireProspect();
        } else {
            if ("client".equals(entity)) {
                formulaire.formulaireClient();
                formulaire.fCreer();
            } else if ("prospect".equals(entity)) {
                formulaire.formulaireProspect();
                formulaire.fCreer();
            }
        }

        if (isModification) {
            formulaire.fModif();
            formulaire.editModif();
        } else if (isSuppression) {
            formulaire.fSupp();
            formulaire.editSupp();
        }
        formulaire.setVisible(true);
    }

    /**
     * Crée un nouveau prospect avec les informations fournies.
     *
     * @throws Exception si une erreur survient lors de la création du prospect
     */
    public static void creerProspect(Prospect prospect) throws Exception {
        ProspectDAO.create(prospect);
    }

    /**
     * Met à jour les informations d'un prospect existant.
     *
     * @throws ReException si une erreur survient lors de la mise à jour du prospect
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     */
    public static void updateProspect(Prospect prospect) throws Exception {
        ProspectDAO.update(prospect);
    }

    /**
     * Supprime un prospect existant.
     *
     * @param prospect le prospect à supprimer
     * @throws ReException si une erreur survient lors de la suppression du prospect
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     * @throws DaoException si une erreur survient lors de l'accès aux données dans la base de données
     */
    public static void deleteProspect(Prospect prospect) throws Exception {
        ProspectDAO.delete(prospect);
    }

    //Client
    /**
     * Crée un nouveau client avec les informations fournies.
     *
     * @throws Exception si une erreur survient lors de la création du client
     */
    public static void creerClient(Client client) throws Exception {
        ClientDAO.create(client);
    }

    /**
     * Met à jour les informations d'un client existant.
     *
     * @throws ReException si une erreur survient lors de la mise à jour du client
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     */
    public static void updateClient(Client client) throws Exception {
        ClientDAO.update(client);
    }

    /**
     * Supprime un client existant.
     *
     * @param client le client à supprimer
     * @throws ReException si une erreur survient lors de la suppression du client
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     * @throws DaoException si une erreur survient lors de l'accès aux données dans la base de données
     */
    public static void deleteClient(Client client) throws Exception {
        ClientDAO.delete(client);
    }
}
