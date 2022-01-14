package Models;

import java.sql.Date;

public class Vehicule extends Entity{
    private int immatriculation;
    private Date date_circulation;
    private String etat;
    private double nb_km;
    private double prix_location_jour;
    private Marque marque;
    private Modele modele;
    private Categorie categorie;
    private Type type;
    private Agence agence;

    public Vehicule(int immatriculation){
        this(immatriculation,null,null,0,0.0,null,null,null,null,null);
    }
    
    public Vehicule(
        int immatriculation, Date date_circulation,String etat,
        int nb_km,double prix_location_jour,Marque marque,
        Modele modele,Categorie categorie,Type type,Agence agence
        ){
            super();
            this.immatriculation = immatriculation;
            this.date_circulation = date_circulation;
            this.etat = etat;
            this.nb_km = nb_km;
            this.prix_location_jour = prix_location_jour;
            this.marque = marque;
            this.modele = modele;
            this.categorie = categorie;
            this.type = type;
            this.agence = agence;
        }


    public Vehicule() {
        this(0,null,"",0,0.0,null,null,null,null,null);
	}


	/**
     * @return the immatriculation
     */
    public int getImmatriculation() {
        return immatriculation;
    }
    /**
     * @param immatriculation the immatriculation to set
     */
    public void setImmatriculation(int immatriculation) {
        this.immatriculation = immatriculation;
    }
    /**
     * @return the date_circulation
     */
    public Date getDate_circulation() {
        return date_circulation;
    }
    /**
     * @param date_circulation the date_circulation to set
     */
    public void setDate_circulation(Date date_circulation) {
        this.date_circulation = date_circulation;
    }
    /**
     * @return the etat
     */
    public String getEtat() {
        return etat;
    }
    /**
     * @param etat the etat to set
     */
    public void setEtat(String etat) {
        this.etat = etat;
    }
    /**
     * @return the nb_km
     */
    public double getNb_km() {
        return nb_km;
    }
    /**
     * @param nb_km the nb_km to set
     */
    public void setNb_km(double nb_km) {
        this.nb_km = nb_km;
    }
    /**
     * @return the prix_location_jour
     */
    public double getPrix_location_jour() {
        return prix_location_jour;
    }
    /**
     * @param prix_location_jour the prix_location_jour to set
     */
    public void setPrix_location_jour(double prix_location_jour) {
        this.prix_location_jour = prix_location_jour;
    }
    /**
     * @return the marque
     */
    public Marque getMarque() {
        return marque;
    }
    /**
     * @param marque the marque to set
     */
    public void setMarque(Marque marque) {
        this.marque = marque;
    }
    /**
     * @return the modele
     */
    public Modele getModele() {
        return modele;
    }
    /**
     * @param modele the modele to set
     */
    public void setModele(Modele modele) {
        this.modele = modele;
    }
    /**
     * @return the categorie
     */
    public Categorie getCategorie() {
        return categorie;
    }
    /**
     * @param categorie the categorie to set
     */
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }
    /**
     * @return the agence
     */
    public Agence getAgence() {
        return agence;
    }
    /**
     * @param agence the agence to set
     */
    public void setAgence(Agence agence) {
        this.agence = agence;
    }
    

}
