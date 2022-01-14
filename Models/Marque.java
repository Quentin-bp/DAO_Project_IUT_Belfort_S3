package Models;

public class Marque extends Entity{
        
    private int id; 
    private String nom; 

    public Marque() { 
        this(0); 
    } 
 
    public Marque(int id) { 
        this(id, null); 
    } 
 
    public Marque(int id, String nom) { 
        super();
        this.id = id;
        this.nom = nom;
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
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    } 
}

