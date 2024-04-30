package com.reverso.dao;

import com.reverso.log.LoggerRE;

import java.awt.datatransfer.FlavorEvent;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

public class DatabaseConnexion{
    private static Connection connection;

    /**
     * Constructeur privé pour empêcher l'instanciation de la classe DatabaseConnexion
     * depuis l'extérieur.
     */
    private DatabaseConnexion() throws Exception {
        try{
            final Properties dataProperties = new Properties();
            File fichier = new File("database.properties");
            try (FileInputStream input = new FileInputStream(fichier)) {
                dataProperties.load(input);
            }

            //Récupère les informations de connexion depuis le fichier de propriété
            String url = dataProperties.getProperty("url");
            String login = dataProperties.getProperty("login");
            String password = dataProperties.getProperty("password");

            //Etablissement de la connexion à la base de données
            connection = DriverManager.getConnection(url, login, password);

            //Fermer la connexion lors de la fermeture de l'application
            Runtime.getRuntime().addShutdownHook(new Thread()
            {
                public void run()
                {
                    if (connection != null) {
                        try {
                            System.out.println("Database fermé.");
                            connection.close();

                        } catch (SQLException ex) {
                            System.out.println("pb fermeture Database.");
                            LoggerRE.LOGGER.log(Level.SEVERE, "Problème de fermeture de la BDD");
                        }
                    }
                }
            });
        } catch (SQLException se) {
            throw new SQLException("Une erreur est survenue.");
        }
    }

    /**
     * Méthode permettant d'obtenir une instance de connexion à la base de données.
     * Si aucune connexion n'est ouverte ou si elle est fermée, une nouvelle connexion est établie.
     *
     * @return l'objet Connection représentant la connexion à la base de données
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     */
    public static Connection getConnexion() throws Exception {
        if (connection == null || connection.isClosed()) {
            new DatabaseConnexion();
        }
        return connection;
    }
}
