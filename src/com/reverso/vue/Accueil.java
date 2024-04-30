package com.reverso.vue;

import com.reverso.controller.ControllerAccueil;
import com.reverso.entites.Client;
import com.reverso.entites.Prospect;
import com.reverso.exception.DaoException;
import com.reverso.exception.ReException;
import com.reverso.log.LoggerRE;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;


public class Accueil extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel lbTitre;
    private JRadioButton rdBtnClient;
    private JRadioButton rdBtnProspect;
    private JRadioButton rdBtnAfficher;
    private JRadioButton rdBtnSupp;
    private JRadioButton rdBtnNew;
    private JRadioButton rdBtnModif;
    private JComboBox<String> comboBox;
    private JPanel pnlComboBox;
    private String selected;

    /**
     * Constructeur de la classe Accueil.
     */
    public Accueil() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        this.setSize(400, 300);
        rdBtnClient.setSelected(true);
        rdBtnAfficher.setSelected(true);

        buttonOK.addActionListener(e -> {
            try {
                String selectedItem = (String) comboBox.getSelectedItem();
                if(selectedItem != null) {
                    if (rdBtnNew.isSelected()) {
                            String type = rdBtnClient.isSelected() ? "client" : "prospect";
                            ControllerAccueil.handleFormulaire(type, type, false, false);
                    } else if(rdBtnModif.isSelected() || rdBtnSupp.isSelected()) {
                        ControllerAccueil.handleButtonOKClicked(selected, rdBtnModif.isSelected(), rdBtnSupp.isSelected(), comboBox.getSelectedItem().toString());
                    } else if(rdBtnAfficher.isSelected()){
                        String type = rdBtnClient.isSelected() ? "client" : "prospect";
                        ControllerAccueil.handleAffichage(type);
                    }
                } else {
                    throw new ReException("Sélectionnez un item.");
                }
            } catch (DaoException de) {
                JOptionPane.showMessageDialog(null, "Une erreur est surnevue.");
                LoggerRE.LOGGER.log(Level.SEVERE, "Erreur DAO dans Accueil: " + de.getMessage());
                System.exit(1);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Une erreur est survenue.");
                LoggerRE.LOGGER.log(Level.SEVERE, "Erreur dans Accueil: " + ex.getMessage());
                System.exit(1);
            }
            onCancel();
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

        rdBtnModif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlComboBox.setVisible(true);
            }
        });

        rdBtnSupp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlComboBox.setVisible(true);
            }
        });

        rdBtnAfficher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlComboBox.setVisible(false);
            }
        });

        rdBtnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlComboBox.setVisible(false);
            }
        });

        rdBtnModif.addActionListener(e -> pnlComboBox.setVisible(true));
        rdBtnSupp.addActionListener(e -> pnlComboBox.setVisible(true));
        rdBtnAfficher.addActionListener(e -> pnlComboBox.setVisible(false));
        rdBtnNew.addActionListener(e -> pnlComboBox.setVisible(false));

        rdBtnClient.addActionListener(e -> handleRadioButtonChange("client"));
        rdBtnProspect.addActionListener(e -> handleRadioButtonChange("prospect"));
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void handleRadioButtonChange(String type) {
        try {
            selected = type;
            comboBox.removeAllItems();
            fillComboBox();
        } catch (Exception re) {
            JOptionPane.showMessageDialog(null, "Une erreur est survenue, réessayez.");
        }
    }


    /**
     * Remplit la combobox avec les données des clients ou des prospects.
     *
     * @throws Exception une exception lors du remplissage de la combobox
     */
    private void fillComboBox() throws Exception {
        try {
            if (Objects.equals(selected, "client")) {
                ArrayList<Client> clients = ControllerAccueil.boxClient();
                for (Client client : clients) {
                    comboBox.addItem(client.getRaisonSocial());
                }
            } else if (Objects.equals(selected, "prospect")) {
                ArrayList<Prospect> prospects = ControllerAccueil.boxProspect();
                for (Prospect prospect : prospects) {
                    comboBox.addItem(prospect.getRaisonSocial());
                }
            }
        } catch (DaoException de){
            JOptionPane.showMessageDialog(null, "Une erreur est survenue.");
            LoggerRE.LOGGER.log(Level.SEVERE, "Erreur DAO dans accueil: " + de.getMessage());
            System.exit(1);
        }
    }

}
