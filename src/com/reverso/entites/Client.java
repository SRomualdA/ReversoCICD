package com.reverso.entites;

import com.reverso.exception.ReException;

public class Client extends  Societe{
    private double chiffreAffaire;
    private int nbeEmploye;

    /**
     * Constructeur par défaut de la classe Client.
     */
    public Client(){}

    /**
     * Constructeur avec paramètres de la classe Client.
     *
     * @param id l'identifiant du client
     * @param raisonSocial la raison sociale du client
     * @param numRue le numéro de rue du client
     * @param nomRue le nom de rue du client
     * @param cp le code postal du client
     * @param ville la ville du client
     * @param tel le numéro de téléphone du client
     * @param email l'adresse email du client
     * @param p_nbeEmploye le nombre d'employés du client
     * @param p_chiffreAffaire le chiffre d'affaires du client
     * @param commentaire le commentaire associé au client
     * @throws ReException si une erreur survient lors de la validation des données
     */
    public Client(int id, String raisonSocial, String numRue, String nomRue, String cp, String ville, String tel, String email,
                  int p_nbeEmploye, double p_chiffreAffaire, String commentaire) throws ReException {
        super(id, raisonSocial, numRue, nomRue, cp, ville, tel, email, commentaire);
        setChiffreAffaire(p_chiffreAffaire);
        setNbeEmploye(p_nbeEmploye);
    }


    //Getters
    public double getChiffreAffaire() {
        return chiffreAffaire;
    }
    public int getNbeEmploye() {
        return nbeEmploye;
    }

    //Setters
    public void setChiffreAffaire(double chiffreAffaire) throws ReException{
        if(chiffreAffaire < 200){
            throw new ReException("Entrez un chiffre d'affaire valide.");
        }
        this.chiffreAffaire = chiffreAffaire;
    }
    public void setNbeEmploye(int nbeEmploye) throws ReException{
        if(nbeEmploye < 1){
            throw new ReException("Vous devez avoir plus de 0 employés.");
        }
        this.nbeEmploye = nbeEmploye;
    }

    /**
     * Redéfinition de la méthode toString pour afficher les informations du client.
     *
     * @return une chaîne de caractères représentant les informations du client
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("Chiffre d'affaire : ").append(chiffreAffaire).append("\n");
        sb.append("Nombre d'employés : ").append(nbeEmploye).append("\n");
        return sb.toString();
    }
}
