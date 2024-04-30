package com.reverso.entites;


import com.reverso.exception.ReException;
import com.reverso.utilitaire.Regex;


public abstract class Societe {
    private int id;
    private String raisonSocial;
    private String tel;
    private String email;
    private String nomRue;
    private String numRue;
    private String ville;
    private String cp;
    private String commentaire;

    /**
     * Constructeur par défaut de la classe Societe.
     */
    public Societe(){

    }

    /**
     * Constructeur avec paramètres de la classe Societe.
     *
     * @param p_id l'identifiant de la société
     * @param p_raisonSocial la raison sociale de la société
     * @param p_numRue le numéro de rue de la société
     * @param p_nomRue le nom de rue de la société
     * @param p_cp le code postal de la société
     * @param p_ville la ville de la société
     * @param p_tel le numéro de téléphone de la société
     * @param p_email l'adresse email de la société
     * @param p_commentaire le commentaire associé à la société
     * @throws ReException si une erreur survient lors de la validation des données
     */
    public Societe(int p_id, String p_raisonSocial, String p_numRue, String p_nomRue, String p_cp, String p_ville, String p_tel, String p_email,
                   String p_commentaire) throws ReException {
        setId(p_id);
        setRaisonSocial(p_raisonSocial);
        setTel(p_tel);
        setEmail(p_email);
        setNomRue(p_nomRue);
        setNumRue(p_numRue);
        setVille(p_ville);
        setCp(p_cp);
        setCommentaire(p_commentaire);
    }

    //Getters
    public int getId(){
        return id;
    }
    public String getRaisonSocial() {
        return raisonSocial;
    }
    public String getTel() {
        return tel;
    }
    public String getEmail() {
        return email;
    }
    public String getNomRue() {
        return nomRue;
    }
    public String getNumRue() {
        return numRue;
    }
    public String getVille() {
        return ville;
    }
    public String getCp() {
        return cp;
    }
    public String getCommentaire() {
        return commentaire;
    }


    //Setters
    public void setId(int id){
        this.id = id;
    }
    public void setRaisonSocial(String raisonSocial) throws ReException {
        if(raisonSocial == null || raisonSocial.isEmpty()){
            throw new ReException("Entrez votre raison social. ");
        }
        this.raisonSocial = raisonSocial;
    }
    public void setTel(String tel) throws ReException{
        if(tel.length() < 10){
            throw new ReException("Entrez votre numéro de téléphone.");
        }
        this.tel = tel;
    }
    public void setEmail(String email) throws ReException{
        if (!Regex.PATTERN_EMAIL.matcher(email).matches()) {
            throw new ReException("L'adresse email n'est pas valide");
        }
        this.email = email;
    }
    public void setNomRue(String nomRue) throws ReException {
        if(nomRue == null || nomRue.isEmpty()){
            throw new ReException("Entrez le nom de votre rue.");
        }
        this.nomRue = nomRue;
    }
    public void setNumRue(String numRue) throws ReException{
        if(numRue == null || numRue.isEmpty()){
            throw new ReException("Entrez le numéro de votre rue.");
        }
        this.numRue = numRue;
    }
    public void setVille(String ville) throws ReException{
        if(ville == null || ville.isEmpty()){
            throw new ReException("Entrez le nom de votre ville.");
        }
        this.ville = ville;
    }
    public void setCp(String cp) throws ReException{
        if(cp == null || cp.isEmpty()){
            throw new ReException("Entrez votre code postale.");
        }
        this.cp = cp;
    }
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    /**
     * Redéfinition de la méthode toString pour afficher les informations de la société.
     *
     * @return une chaîne de caractères représentant les informations de la société
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Raison social : ").append(raisonSocial).append("\n");
        sb.append("Tel : ").append(tel).append("\n");
        sb.append("Email : ").append(email).append("\n");
        sb.append("Adresse : ").append(numRue).append(", ").append(nomRue).append(", ").append(cp).append(", ").append(ville).append("\n");
        sb.append("Commentaire : ").append(commentaire);
        return sb.toString();
    }
}
