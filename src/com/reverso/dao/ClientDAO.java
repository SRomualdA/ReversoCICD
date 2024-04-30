package com.reverso.dao;

import com.reverso.entites.Client;
import com.reverso.exception.DaoException;
import com.reverso.exception.ReException;
import com.reverso.log.LoggerRE;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class ClientDAO {

    /**
     * Récupère la liste de tous les clients.
     *
     * @return la liste de tous les clients
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws ReException si une erreur survient lors de la récupération des données des clients
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     */
    public static ArrayList<Client> findAll() throws Exception {
        Connection connection = DatabaseConnexion.getConnexion();
        ArrayList<Client> listClient = new ArrayList<>();

        PreparedStatement stmt = null;
        String query = "SELECT * FROM Client";
        try {
            stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Client client = new Client();
                        client.setId(rs.getInt("ID"));
                        client.setRaisonSocial(rs.getString("Raison Social"));
                        client.setNumRue(rs.getString("Numéro Rue"));
                        client.setNomRue(rs.getString("Nom Rue"));
                        client.setCp(rs.getString("CP"));
                        client.setVille(rs.getString("Ville"));
                        client.setTel(rs.getString("Tel"));
                        client.setEmail(rs.getString("Email"));
                        client.setChiffreAffaire(rs.getDouble("Chiffre Affaire"));
                        client.setNbeEmploye(rs.getInt("Nombre Employés"));
                        client.setCommentaire(rs.getString("Commentaire"));
                listClient.add(client);
            }
        } catch (SQLException se){
            LoggerRE.LOGGER.log(Level.SEVERE, "Erreur ClientDAO : " + se.getMessage());
            throw new DaoException("Une erreur de recherche s'est produite.");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return listClient;
    }

    /**
     * Recherche un client par son nom.
     *
     * @param raisonSocial le nom du client à rechercher
     * @return le client trouvé ou null s'il n'existe pas
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws ReException si une erreur survient lors de la récupération des données du client
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     */
    public static Client findByName(String raisonSocial) throws Exception {
        Connection connection = DatabaseConnexion.getConnexion();

        Client client = null;
        PreparedStatement stmt = null;
        String query = "SELECT * FROM Client WHERE `Raison Social` = ?";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, raisonSocial);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("ID"));
                client.setRaisonSocial(rs.getString("Raison Social"));
                client.setNumRue(rs.getString("Numéro Rue"));
                client.setNomRue(rs.getString("Nom Rue"));
                client.setCp(rs.getString("CP"));
                client.setVille(rs.getString("Ville"));
                client.setTel(rs.getString("Tel"));
                client.setEmail(rs.getString("Email"));
                client.setChiffreAffaire(rs.getDouble("Chiffre Affaire"));
                client.setNbeEmploye(rs.getInt("Nombre Employés"));
                client.setCommentaire(rs.getString("Commentaire"));
            }
        } catch (SQLException se){
            LoggerRE.LOGGER.log(Level.SEVERE, "Erreur ClientDAO : " + se.getMessage());
            throw new DaoException("Une erreur de recherche s'est produite.");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return client;
    }

    /**
     * Crée un nouveau client.
     *
     * @param client le client à créer
     * @throws ReException si une erreur survient lors de la création du client
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     * @throws DaoException si une erreur survient lors de l'accès aux données dans la base de données
     */
    public static void create(Client client) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnexion.getConnexion();
            String query = "INSERT INTO Client (`Raison Social`, `Numéro Rue`, `Nom Rue`, `CP`, `Ville`, `Tel`, `Email`, `Chiffre Affaire`, `Nombre Employés`, `Commentaire`) values (?,?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, client.getRaisonSocial());
            statement.setString(2, client.getNumRue());
            statement.setString(3, client.getNomRue());
            statement.setString(4, client.getCp());
            statement.setString(5, client.getVille());
            statement.setString(6, client.getTel());
            statement.setString(7, client.getEmail());
            statement.setDouble(8, client.getChiffreAffaire());
            statement.setInt(9, client.getNbeEmploye());
            statement.setString(10, client.getCommentaire());


            int lignesModifier = statement.executeUpdate();
            if (lignesModifier <= 0) {
                throw new DaoException("Erreur d'insertion. ");
            }
        } catch (SQLException se){
            if (se.getErrorCode() == 1062) { // Vérifie si le code d'erreur est 1062 (erreur de violation de contrainte unique)
                throw new DaoException("Erreur d'insertion : Doublon détecté !"); // Gérer le cas de doublon
            } else {
                throw new SQLException("Erreur SQL");
            }
        } finally {
            // Fermeture des ressources
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                LoggerRE.LOGGER.log(Level.SEVERE, "Erreur lors de la fermeture des ressources : " + se.getMessage());
                throw new DaoException("Erreur lors de la fermeture des ressources : " + se.getMessage());
            }
        }
    }

    /**
     * Met à jour les informations d'un client existant.
     *
     * @param client le client à mettre à jour
     * @throws ReException si une erreur survient lors de la mise à jour du client
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     */
    public static void update(Client client) throws Exception {
        try {
            Connection connection = DatabaseConnexion.getConnexion();
            String query = "UPDATE Client " +
                    "SET `Raison Social` = ?, " +
                    "`Numéro Rue` = ?, " +
                    "`Nom Rue` = ?, " +
                    "`CP` = ?, " +
                    "`Ville` = ?, " +
                    "`Tel` = ?, " +
                    "`Email` = ?, " +
                    "`Chiffre Affaire` = ?, " +
                    "`Nombre Employés` = ?, " +
                    "`Commentaire` = ? " +
                    "WHERE `ID` = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, client.getRaisonSocial());
            statement.setString(2, client.getNumRue());
            statement.setString(3, client.getNomRue());
            statement.setString(4, client.getCp());
            statement.setString(5, client.getVille());
            statement.setString(6, client.getTel());
            statement.setString(7, client.getEmail());
            statement.setDouble(8, client.getChiffreAffaire());
            statement.setInt(9, client.getNbeEmploye());
            statement.setString(10, client.getCommentaire());
            statement.setInt(11, client.getId());

            int lignemodif = statement.executeUpdate();
            if (lignemodif <= 0){
                throw new DaoException("La mise à jour du client à échoué. ");
            }
            statement.close();

        } catch (SQLException se) {
            if (se.getErrorCode() == 1062) { // Vérifie si le code d'erreur est 1062 (erreur de violation de contrainte unique)
                throw new DaoException("Erreur d'insertion : Doublon détecté !"); // Gérer le cas de doublon
            } else {
                throw new SQLException("Erreur SQL");
            }
        }

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
    public static void delete(Client client) throws Exception {

        Connection connection = DatabaseConnexion.getConnexion();
        String query = "DELETE FROM Client WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, client.getId());

        int lignesModifier = statement.executeUpdate();
        if(lignesModifier <= 0){
            throw new DaoException("La suppression du client a échoué.");
        }
    }
}
