package com.reverso.dao;

import com.reverso.entites.Prospect;
import com.reverso.exception.DaoException;
import com.reverso.exception.ReException;
import com.reverso.log.LoggerRE;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class ProspectDAO {

    /**
     * Récupère la liste de tous les prospects.
     *
     * @return la liste de tous les prospects
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws ReException si une erreur survient lors de la récupération des données des prospects
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     */
    public static ArrayList<Prospect> findAll() throws Exception {
        Connection connection = DatabaseConnexion.getConnexion();

        ArrayList<Prospect> listProspect = new ArrayList<>();

        PreparedStatement stmt = null;
        String query = "SELECT * FROM Prospect";
        try {
            stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Prospect prospect = new Prospect();
                prospect.setId(rs.getInt("ID"));
                prospect.setRaisonSocial(rs.getString("Raison Social"));
                prospect.setNumRue(rs.getString("Numéro Rue"));
                prospect.setNomRue(rs.getString("Nom Rue"));
                prospect.setCp(rs.getString("CP"));
                prospect.setVille(rs.getString("Ville"));
                prospect.setTel(rs.getString("Tel"));
                prospect.setEmail(rs.getString("Email"));
                prospect.setDateProspect(rs.getDate("Date Prospect").toLocalDate());
                prospect.setProspectInteress(rs.getInt("Prospect Intéresser"));
                prospect.setCommentaire(rs.getString("Commentaire"));

                listProspect.add(prospect);
            }
        } catch (SQLException se){
            LoggerRE.LOGGER.log(Level.SEVERE, "Erreur ClientDAO. ");
            throw new DaoException("Une erreur de recherche s'est produite.");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return listProspect;
    }

    /**
     * Recherche un prospect par son nom.
     *
     * @param raisonSocial le nom du prospect à rechercher
     * @return le prospect trouvé ou null s'il n'existe pas
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws ReException si une erreur survient lors de la récupération des données du prospect
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     */
    public static Prospect findByRS(String raisonSocial) throws Exception {
        Connection connection = DatabaseConnexion.getConnexion();

        Prospect prospect = null;
        PreparedStatement stmt = null;
        String query = "SELECT * FROM Prospect WHERE `Raison Social` = ?";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, raisonSocial);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                prospect = new Prospect();
                prospect.setId(rs.getInt("ID"));
                prospect.setRaisonSocial(rs.getString("Raison Social"));
                prospect.setNumRue(rs.getString("Numéro Rue"));
                prospect.setNomRue(rs.getString("Nom Rue"));
                prospect.setCp(rs.getString("CP"));
                prospect.setVille(rs.getString("Ville"));
                prospect.setTel(rs.getString("Tel"));
                prospect.setEmail(rs.getString("Email"));
                prospect.setDateProspect(rs.getDate("Date Prospect").toLocalDate());
                prospect.setProspectInteress(rs.getInt("Prospect Intéresser"));
                prospect.setCommentaire(rs.getString("Commentaire"));
            }
        } catch (SQLException se){
            LoggerRE.LOGGER.log(Level.SEVERE, "Erreur ProspectDAO. ");
            throw new DaoException("Une erreur de recherche s'est produite.");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return prospect;
    }

    /**
     * Crée un nouveau prospect.
     *
     * @param prospect le prospect à créer
     * @throws ReException si une erreur survient lors de la création du prospect
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     * @throws DaoException si une erreur survient lors de l'accès aux données dans la base de données
     */
    public static void create(Prospect prospect) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnexion.getConnexion();
            String query = "INSERT INTO Prospect (`Raison Social`, `Numéro Rue`, `Nom Rue`, `CP`, `Ville`, `Tel`, `Email`, `Date Prospect`, `Prospect Intéresser`, `Commentaire`) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, prospect.getRaisonSocial());
            statement.setString(2, prospect.getNumRue());
            statement.setString(3, prospect.getNomRue());
            statement.setString(4, prospect.getCp());
            statement.setString(5, prospect.getVille());
            statement.setString(6, prospect.getTel());
            statement.setString(7, prospect.getEmail());
            statement.setDate(8, Date.valueOf(prospect.getDateProspect()));
            statement.setInt(9, prospect.getProspectInteress());
            statement.setString(10, prospect.getCommentaire());

            int lignesModifier = statement.executeUpdate();
            if (lignesModifier <= 0) {
                throw new DaoException("Erreur d'insertion. ");
            }
        }catch (SQLException se){
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
     * Met à jour les informations d'un prospect existant.
     *
     * @param prospect le prospect à mettre à jour
     * @throws ReException si une erreur survient lors de la mise à jour du prospect
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     */
    public static void update(Prospect prospect) throws Exception {
        try {
            Connection connection = DatabaseConnexion.getConnexion();
            String query = "UPDATE Prospect " +
                    "SET `Raison Social` = ?, " +
                    "`Numéro Rue` = ?, " +
                    "`Nom Rue` = ?, " +
                    "`CP` = ?, " +
                    "`Ville` = ?, " +
                    "`Tel` = ?, " +
                    "`Email` = ?, " +
                    "`Date Prospect` = ?, " +
                    "`Prospect Intéresser` = ?, " +
                    "`Commentaire` = ? " +
                    "WHERE `ID` = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, prospect.getRaisonSocial());
            statement.setString(2, prospect.getNumRue());
            statement.setString(3, prospect.getNomRue());
            statement.setString(4, prospect.getCp());
            statement.setString(5, prospect.getVille());
            statement.setString(6, prospect.getTel());
            statement.setString(7, prospect.getEmail());
            statement.setDate(8, Date.valueOf(prospect.getDateProspect()));
            statement.setInt(9, prospect.getProspectInteress());
            statement.setString(10, prospect.getCommentaire());
            statement.setInt(11, prospect.getId());

            int lignemodif = statement.executeUpdate();
            if (lignemodif <= 0) {
                throw new DaoException("La mise à jour du prospect a échoué. ");
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
     * Supprime un prospect existant.
     *
     * @param prospect le prospect à supprimer
     * @throws ReException si une erreur survient lors de la suppression du prospect
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     * @throws IOException si une erreur survient lors de l'accès aux fichiers
     * @throws DaoException si une erreur survient lors de l'accès aux données dans la base de données
     */
    public static void delete(Prospect prospect) throws Exception {

        Connection connection = DatabaseConnexion.getConnexion();
        String query = "DELETE FROM Prospect WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, prospect.getId());

        int lignesModifier = statement.executeUpdate();
        if(lignesModifier <= 0){
            throw new DaoException("La suppression du pospect a échoué.");
        }
    }


}
