package Models;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Contrat extends Entity {
    private int id;
    private Date date_retrait;
    private Date date_retour;
    private double km_retrait;
    private double km_retour;
    private Client client;
    private Vehicule vehicule;
    private Agence agence;

    public Contrat(){
        this(0);
    }
    public Contrat(int id) {
        this(id, null, null, 0, 0, null, null, null);
    };

    public Contrat(int id, Date date_retrait, Date date_retour, double km_retrait, double km_retour, Client client,
            Vehicule vehicule, Agence agence) {
        super();
        this.id = id;
        this.date_retrait = date_retrait;
        this.date_retour = date_retour;
        this.km_retrait = km_retrait;
        this.km_retour = km_retour;
        this.client = client;
        this.vehicule = vehicule;
        this.agence = agence;
    }
    /**
     * On met cette fonction dans le modele car c'est le modele qui est cense gerer les donnees, et non le Dao qui doit uniquement faire la transition Objet->BDD
     * Methode statique pour pouvoir l'appeler sans forcement instancier de contrat, ce qui serait peu logique
     * @param d1
     * @param d2
     * @return INT nombre de jour de difference entre d1 et 2
     */
    public static int getNbJourDifference(Date d1, Date d2){ // fonction qui retourne en entier le nombre de jour de difference
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
   
        cal1.setTime(d1);
        cal2.setTime(d2);
        
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 
     * @param mois
     * @return 0 si erreur, mois en entier sinon
     */
    public static int getMoisValide(String mois){

        int mois_entier;

        try{ // on teste si le mois est bien une valeur entiere
            mois_entier = Integer.parseInt(mois);
        } catch (NumberFormatException e) {
            System.out.println("Erreur de conversion du mois en un entier");
            return 0;
        }
        // Le mois est forcement un entier, on test maintenant si il correspond a un vrai mois (1 a 12)
        if ( mois_entier > 12 || mois_entier < 1 ){
            System.out.println("Veuillez entrer un mois correcte ( 1 <= mois <= 12 )");
            return 0;
        }
        return mois_entier;
    }
    /**
     * 
     * @param annee
     * @return 0 si erreur , l'annee en INT sinon
     */
    public static int getAnneeValide(String annee){

        int annee_entiere;

        try{ // on teste si l'annee est bien une valeur entiere
            annee_entiere = Integer.parseInt(annee);
        } catch (NumberFormatException e) {
            System.out.println("Erreur de conversion de l'année en un entier");
            return 0;
        }
        // L'annee est forcement un entier, on test maintenant si l'annee donnee est une vraie annee (4 valeurs)
        if ( annee.length() != 4 ){
            System.out.println("Veuillez entrer une année correcte ( 4 valeurs )");
            return 0;
        }
        return annee_entiere;
    }

    public static Date ajouterJourDepuisUneDate(Date dep, int duree){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(dep);
        c.add(Calendar.DATE, duree);
        String date = sdf.format(c.getTime());
        Date date_retour = Date.valueOf(date);
        return date_retour;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the date_retrait
     */
    public Date getDate_retrait() {
        return date_retrait;
    }

    /**
     * @param date_retrait the date_retrait to set
     */
    public void setDate_retrait(Date date_retrait) {
        this.date_retrait = date_retrait;
    }

    /**
     * @return the date_retour
     */
    public Date getDate_retour() {
        return date_retour;
    }

    /**
     * @param date_retour the date_retour to set
     */
    public void setDate_retour(Date date_retour) {
        this.date_retour = date_retour;
    }

    /**
     * @return the km_retrait
     */
    public double getKm_retrait() {
        return km_retrait;
    }

    /**
     * @param km_retrait the km_retrait to set
     */
    public void setKm_retrait(double km_retrait) {
        this.km_retrait = km_retrait;
    }

    /**
     * @return the km_retour
     */
    public double getKm_retour() {
        return km_retour;
    }

    /**
     * @param km_retour the km_retour to set
     */
    public void setKm_retour(double km_retour) {
        this.km_retour = km_retour;
    }

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * @return the vehicule
     */
    public Vehicule getVehicule() {
        return vehicule;
    }

    /**
     * @param vehicule the vehicule to set
     */
    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
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
