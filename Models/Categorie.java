package Models;

public class Categorie extends Entity{
     
    private int id; 
    private String libelle; 

    public Categorie() { 
        this(0); 
    } 
 
    public Categorie(int id) { 
        this(id, null); 
    } 
 
    public Categorie(int id, String libelle) { 
        super();
        this.id = id;
        this.libelle = libelle;
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
     * @return the libelle
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * @param libelle the libelle to set
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    } 
}

