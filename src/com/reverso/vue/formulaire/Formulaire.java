package com.reverso.vue.formulaire;

import com.reverso.controller.ControllerAccueil;
import com.reverso.controller.formulaire.ContFormulaire;
import com.reverso.entites.Client;
import com.reverso.entites.Prospect;
import com.reverso.exception.DaoException;
import com.reverso.exception.ReException;
import com.reverso.log.LoggerRE;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

public class Formulaire extends JDialog {
    private JPanel contentPane;
    private JButton buttonRetour;
    private JButton buttonCancel;
    private JTextField txtRaisonS;
    private JTextField txtNumRue;
    private JTextField txtNomRue;
    private JTextField txtCP;
    private JTextField txtVille;
    private JTextField txtTel;
    private JTextField txtEmail;
    private JTextField txtDate;
    private JRadioButton reBtnOui;
    private JRadioButton rdBtnNon;
    private JTextArea txtCommentaire;
    private JButton buttonValider;
    private JTextField txtID;
    private JTextField txtEmp;
    private JTextField txtChiffre;
    private JPanel pnlClient;
    private JPanel pnlProspect;
    private JButton btnMAJ;
    private JButton btnSupp;

    /**
     * Constructeur de la classe FormulaireProspect.
     */
    public Formulaire() {
        setContentPane(contentPane);
        //setModal(true);
        getRootPane().setDefaultButton(buttonRetour);
        this.setSize(400, 700);
        reBtnOui.setSelected(true);
        pnlClient.setVisible(false);
        pnlProspect.setVisible(false);

        buttonRetour.addActionListener(new ActionListener() {
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

        buttonValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(txtID.getText().isEmpty() ||
                        txtRaisonS.getText().isEmpty() ||
                        txtNumRue.getText().isEmpty() ||
                        txtNomRue.getText().isEmpty() ||
                        txtCP.getText().isEmpty() ||
                        txtVille.getText().isEmpty() ||
                         txtTel.getText().isEmpty() ||
                        txtEmail.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vous devez entrer tout les champs. ");
                    }

                        if(pnlProspect.isVisible()){
                        //Vérifie que c'est pas null
                        if (
                            txtDate.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Vous devez entrer tout les champs. ");

                        } else {
                            //Créer un prospect
                            ContFormulaire.creerProspect(collecterProspect());
                            JOptionPane.showMessageDialog(null, "Prospect ajouter avec succès. ");
                        }
                    } else if(pnlClient.isVisible()){
                        //Vérifie que c'est pas null
                        if(txtEmp.getText().isEmpty() ||
                            txtChiffre.getText().isEmpty()){
                            JOptionPane.showMessageDialog(null, "Vous devez entrer tout les champs. ");
                        } else {
                            //Créer un client
                            ContFormulaire.creerClient(collecterClient());
                            JOptionPane.showMessageDialog(null, "Client ajouter avec succès. ");
                        }

                    }
                } catch (ReException re) {
                    JOptionPane.showMessageDialog(null, re.getMessage());
                } catch (SQLException se) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue. ");
                    LoggerRE.LOGGER.log(Level.SEVERE, "Erreur SQL dans Formulaire : " + se.getMessage());
                    System.exit(1);
                } catch (IOException ie) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite. ");
                    LoggerRE.LOGGER.log(Level.SEVERE, "Erreur IO dans formulaire : " + ie.getMessage());
                    System.exit(1);
                } catch (DaoException de) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite. ");
                    LoggerRE.LOGGER.log(Level.WARNING, "Erreur DAO dans formulaire : " + de.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite.");
                    LoggerRE.LOGGER.log(Level.SEVERE, "Une erreur est survenue dans formulaire : " + ex.getMessage());
                    System.exit(1);
                }
                ControllerAccueil.init();
                onOK();
            }
        });

        btnMAJ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (pnlProspect.isVisible()) {
                        //Modifier prospect
                        ContFormulaire.updateProspect(collecterProspect());
                        JOptionPane.showMessageDialog(null, "Prospect modifier avec succès. ");
                    } else if(pnlClient.isVisible()){
                        //Mise a jour client
                        ContFormulaire.updateClient(collecterClient());
                        JOptionPane.showMessageDialog(null, "Client modifier avec succès. ");
                    }
                } catch (ReException re) {
                    JOptionPane.showMessageDialog(null, re.getMessage());
                } catch (SQLException se) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue. ");
                    LoggerRE.LOGGER.log(Level.SEVERE, "Erreur SQL dans Formulaire : " + se.getMessage());
                    System.exit(1);
                } catch (IOException ie) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite. ");
                    LoggerRE.LOGGER.log(Level.SEVERE, "Erreur IO dans formulaire : " + ie.getMessage());
                    System.exit(1);
                } catch (DaoException de) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite. ");
                    LoggerRE.LOGGER.log(Level.WARNING, "Erreur DAO dans formulaire : " + de.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite.");
                    LoggerRE.LOGGER.log(Level.SEVERE, "Une erreur est survenue dans formulaire : " + ex.getMessage());
                    System.exit(1);
                }
                ControllerAccueil.init();
                onOK();
            }
        });

        btnSupp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(pnlProspect.isVisible()){
                        ContFormulaire.deleteProspect(collecterProspect());
                        JOptionPane.showMessageDialog(null, "Prospect supprimer.");
                    } else if(pnlClient.isVisible()){
                        ContFormulaire.deleteClient(collecterClient());
                        JOptionPane.showMessageDialog(null, "Client supprimer.");
                    }
                } catch (ReException re) {
                    JOptionPane.showMessageDialog(null, re.getMessage());
                } catch (SQLException se) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue. ");
                    LoggerRE.LOGGER.log(Level.SEVERE, "Erreur SQL dans Formulaire : " + se.getMessage());
                    System.exit(1);
                } catch (IOException ie) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite. ");
                    LoggerRE.LOGGER.log(Level.SEVERE, "Erreur IO dans formulaire : " + ie.getMessage());
                    System.exit(1);
                } catch (DaoException de) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite. ");
                    LoggerRE.LOGGER.log(Level.WARNING, "Erreur DAO dans formulaire : " + de.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite.");
                    LoggerRE.LOGGER.log(Level.SEVERE, "Une erreur est survenue dans formulaire : " + ex.getMessage());
                    System.exit(1);
                }
                ControllerAccueil.init();
                onOK();
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * Remplit le formulaire avec les informations du prospect.
     *
     * @param prospect le client à afficher dans le formulaire
     */
    public void remplirFormulaireProspect(Prospect prospect){
        txtID.setText(String.valueOf(prospect.getId()));
        txtRaisonS.setText(String.valueOf(prospect.getRaisonSocial()));
        txtNumRue.setText(prospect.getNumRue());
        txtNomRue.setText(prospect.getNomRue());
        txtCP.setText(prospect.getCp());
        txtVille.setText(prospect.getVille());
        txtTel.setText(prospect.getTel());
        txtEmail.setText(prospect.getEmail());
        txtDate.setText(String.valueOf(prospect.getDateProspect()));
        if (prospect.getDateProspect() != null) {
            txtDate.setText(prospect.getDateProspect().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        txtCommentaire.setText(prospect.getCommentaire());

        buttonValider.setVisible(false);
    }

    /**
     * Remplit le formulaire avec les informations du client.
     *
     * @param client le client à afficher dans le formulaire
     */
    public void remplirFormulaireClient(Client client){
        txtID.setText(String.valueOf(client.getId()));
        txtRaisonS.setText(String.valueOf(client.getRaisonSocial()));
        txtNumRue.setText(client.getNumRue());
        txtNomRue.setText(client.getNomRue());
        txtCP.setText(client.getCp());
        txtVille.setText(client.getVille());
        txtTel.setText(client.getTel());
        txtEmail.setText(client.getEmail());
        txtEmp.setText(String.valueOf(client.getNbeEmploye()));
        txtChiffre.setText(String.valueOf(client.getChiffreAffaire()));
        txtCommentaire.setText(client.getCommentaire());

        buttonValider.setVisible(false);
    }

    /**
     * Affiche le formulaire pour saisir les informations d'un prospect et masque le formulaire client.
     * Le panneau prospect devient visible et le panneau client devient invisible.
     */
    public void formulaireProspect(){
        pnlProspect.setVisible(true);
        pnlClient.setVisible(false);
    }

    /**
     * Affiche le formulaire pour saisir les informations d'un client et masque le formulaire prospect.
     * Le panneau client devient visible et le panneau prospect devient invisible.
     */
    public void formulaireClient(){
        pnlClient.setVisible(true);
        pnlProspect.setVisible(false);
    }

    /**
     * Affiche le bouton de validation et masque les boutons de mise à jour et de suppression.
     */
    public void fCreer(){
        buttonValider.setVisible(true);
        btnMAJ.setVisible(false);
        btnSupp.setVisible(false);
    }

    /**
     * Affiche le bouton de mise à jour et masque les boutons de validation et de suppression.
     */
    public void fModif(){
        btnMAJ.setVisible(true);
        buttonValider.setVisible(false);
        btnSupp.setVisible(false);
    }

    /**
     * Affiche le bouton de suppression et masque les boutons de validation et de mise à jour.
     */
    public void fSupp(){
        btnSupp.setVisible(true);
        buttonValider.setVisible(false);
        btnMAJ.setVisible(false);
    }

    /**
     * Désactive la modification des champs de saisie pour suppression.
     */
    public void editSupp(){
        txtID.setEditable(false);
        txtRaisonS.setEditable(false);
        txtNumRue.setEditable(false);
        txtNomRue.setEditable(false);
        txtCP.setEditable(false);
        txtVille.setEditable(false);
        txtTel.setEditable(false);
        txtEmail.setEditable(false);
        txtDate.setEditable(false);
        txtEmp.setEditable(false);
        txtChiffre.setEditable(false);
        txtCommentaire.setEditable(false);
    }

    /**
     * Désactive la modification du champ ID pour modification.
     */
    public void editModif(){
        txtID.setEditable(false);
    }

    /**
     * Collecte les informations saisies pour un prospect.
     * @return Un objet Prospect contenant les informations saisies.
     * @throws ReException Si une erreur de format survient lors de la saisie des données.
     */
    private Prospect collecterProspect() throws ReException {
        int ID = Integer.parseInt(txtID.getText());
        String RaisonSocial = txtRaisonS.getText();
        String NumeroRue = txtNumRue.getText();
        String NomRue = txtNomRue.getText();
        String CP = txtCP.getText();
        String Ville = txtVille.getText();
        String NumTel = txtTel.getText();
        String Email = txtEmail.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate Date = LocalDate.parse(txtDate.getText(), formatter);
        int Choix = reBtnOui.isSelected() ? 1 : 0;
        String Commentaire = txtCommentaire.getText();

        return new Prospect(ID, RaisonSocial, NumeroRue, NomRue, CP, Ville, NumTel, Email, Date, Choix, Commentaire);
    }

    /**
     * Collecte les informations saisies pour un client.
     * @return Un objet Client contenant les informations saisies.
     * @throws ReException Si une erreur de format survient lors de la saisie des données.
     */
    private Client collecterClient() throws ReException {
        int ID = Integer.parseInt(txtID.getText());
        String RaisonSocial = txtRaisonS.getText();
        String NumeroRue = txtNumRue.getText();
        String NomRue = txtNomRue.getText();
        String CP = txtCP.getText();
        String Ville = txtVille.getText();
        String NumTel = txtTel.getText();
        String Email = txtEmail.getText();
        int NbeEmp = Integer.parseInt(txtEmp.getText());
        double Chiffre = Double.parseDouble(txtChiffre.getText());
        String Commentaire = txtCommentaire.getText();

        return new Client(ID, RaisonSocial, NumeroRue, NomRue, CP, Ville, NumTel, Email, NbeEmp, Chiffre, Commentaire);
    }

}
