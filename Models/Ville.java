package Models;
public class Ville extends Entity { 
 
    private int id; 
    private String nom; 
    private int nbrHabitant;

    public Ville() { 
        this(0); 
    } 
 
    public Ville(int id) { 
        this(id, null); 
    } 
 
    public Ville(int id, String nom) { 
        this(id,nom,0); 
    } 
    public Ville(int id, String nom, int nbrHabitant){
    super();
    this.id = id; 
    this.nom = nom; 
    this.nbrHabitant = nbrHabitant;
    }
 
    public int getId() { 
        return id; 
    } 
 
    public void setId(int id) { 
        this.id = id; 
    } 
 
    public String getNom() { 
        return nom; 
    } 
 
    public void setNom(String nom) { 
        this.nom = nom; 
    } 
        /**
     * @return the nbrHabitant
     */
    public int getNbrHabitant() {
        return nbrHabitant;
    }

    /**
     * @param nbrHabitant the nbrHabitant to set
     */
    public void setNbrHabitant(int nbrHabitant) {
        this.nbrHabitant = nbrHabitant;
    }
}