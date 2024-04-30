package com.reverso.entites;


import com.reverso.exception.ReException;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Prospect extends Societe{
    private LocalDate dateProspect;
    private int prospectInteress;

    /**
     * Constructeur par défaut de la classe Prospect.
     */
    public Prospect(){

    }

    /**
     * Constructeur avec paramètres de la classe Prospect.
     *
     * @param id l'identifiant du prospect
     * @param raisonSocial la raison sociale du prospect
     * @param numRue le numéro de rue du prospect
     * @param nomRue le nom de rue du prospect
     * @param cp le code postal du prospect
     * @param ville la ville du prospect
     * @param tel le numéro de téléphone du prospect
     * @param email l'adresse email du prospect
     * @param p_dateProspect la date à laquelle le prospect a été enregistré
     * @param p_prospectInteress indique si le prospect est intéressé ou non (0 ou 1)
     * @param commentaire le commentaire associé au prospect
     * @throws ReException si une erreur survient lors de la validation des données
     */
    public Prospect(int id, String raisonSocial, String numRue, String nomRue, String cp, String ville, String tel, String email,
                    LocalDate p_dateProspect, int p_prospectInteress, String commentaire) throws ReException {
        super(id, raisonSocial, numRue, nomRue, cp, ville, tel, email, commentaire);
        setDateProspect(p_dateProspect);
        setProspectInteress(p_prospectInteress);
    }

    //Getters
    public LocalDate getDateProspect() {
        return dateProspect;
    }
    public int getProspectInteress() {
        return prospectInteress;
    }

    //Setters
    public void setDateProspect(LocalDate dateProspect) throws ReException{
        if(dateProspect == null){
            throw new ReException("Vous devez entrer une date.");
        }
        this.dateProspect = dateProspect;
    }
    public void setProspectInteress(Integer prospectInteress) throws ReException{
        if(prospectInteress == null){
            throw new ReException("Entrez une valeur.");
        } else if(prospectInteress < 0|| prospectInteress > 1){
            throw new ReException("La valeur possible sont 0 ou 1.");
        }
        this.prospectInteress = prospectInteress;
    }

    /**
     * Redéfinition de la méthode toString pour afficher les informations du prospect.
     *
     * @return une chaîne de caractères représentant les informations du prospect
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("Date prospect : ").append(dateProspect).append("\n");
        sb.append("Prospect interesse : ").append(prospectInteress).append("\n");
        return sb.toString();
    }
}
