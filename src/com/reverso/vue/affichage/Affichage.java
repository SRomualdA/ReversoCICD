package com.reverso.vue.affichage;

import com.reverso.controller.ControllerAccueil;
import com.reverso.controller.affichage.ContAffichage;
import com.reverso.entites.Prospect;
import com.reverso.entites.Client;
import com.reverso.exception.DaoException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

import static com.reverso.log.LoggerRE.LOGGER;

public class Affichage extends JDialog {
    private JPanel contentPane;
    private JButton btnRetour;
    private JButton buttonCancel;
    private JLabel lbClient;
    private JTable table;
    private JLabel lbProspect;

    /**
     * Constructeur de la classe AffichageClient.
     *
     * @throws Exception si une exception survient lors de la construction de la fenêtre.
     */
    public Affichage(String type) {
        setContentPane(contentPane);
        //setModal(true);
        getRootPane().setDefaultButton(btnRetour);
        this.setSize(800, 400);

        btnRetour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ControllerAccueil.init();
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Id");
        model.addColumn("Raison sociale");
        model.addColumn("Numéro de rue");
        model.addColumn("Nom de rue");
        model.addColumn("Code postal");
        model.addColumn("Ville");
        model.addColumn("Téléphone");
        model.addColumn("Email");
        model.addColumn("Commentaire");
        try {
            //Remplir le tableau avec les info de Client
            if (Objects.equals(type, "client")) {
                model.addColumn("Chiffre d'Affaire");
                model.addColumn("Nombre d'Employés");
                ArrayList<Client> clients = ContAffichage.getData(type);
                for (Client client : clients) {
                    Object[] fillTableau = {
                            client.getId(),
                            client.getRaisonSocial(),
                            client.getNumRue(),
                            client.getNomRue(),
                            client.getCp(),
                            client.getVille(),
                            client.getTel(),
                            client.getEmail(),
                            client.getCommentaire(),
                            client.getChiffreAffaire(),
                            client.getNbeEmploye(),
                    };
                    model.addRow(fillTableau);
                    table.setModel(model);
                }
                //Remplir le tableau avec les info du Prospect
            } else if (Objects.equals(type, "prospect")) {
                model.addColumn("Date Prospection");
                model.addColumn("Intéret Prospection");
                ArrayList<Prospect> prospects = ContAffichage.getData(type);
                for (Prospect prospect : prospects) {
                    Object[] fillTableau = {
                            prospect.getId(),
                            prospect.getRaisonSocial(),
                            prospect.getNumRue(),
                            prospect.getNomRue(),
                            prospect.getCp(),
                            prospect.getVille(),
                            prospect.getTel(),
                            prospect.getEmail(),
                            prospect.getCommentaire(),
                            prospect.getDateProspect(),
                            prospect.getProspectInteress(),
                    };
                    model.addRow(fillTableau);
                    table.setModel(model);
                }
            }
        } catch (DaoException de) {
            JOptionPane.showMessageDialog(null, "Une erreur est survenue.");
            LOGGER.log(Level.SEVERE, "Erreur DAO dans Affichage : " + de.getMessage());
            System.exit(1);
        } catch (Exception ex){
            LOGGER.log(Level.SEVERE, ex.getMessage());
            JOptionPane.showMessageDialog(null, "Un problème est survenu");
            System.exit(1);
        }
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
