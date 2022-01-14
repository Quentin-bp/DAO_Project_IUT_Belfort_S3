package Models;

public class Agence  extends Entity{
    private int id;
    private int nbrEmployees;
    private Ville ville;

    public Agence() { 
        this(0); 
    } 
 
    public Agence(int id) { 
        this(id, null); 
    } 
 
    public Agence(int id, Ville ville) { 
        this(id,0, ville); 
    } 
    public Agence(int id, int nbrEmployees,  Ville ville){
    super();
    this.id = id; 
    this.ville = ville; 
    this.nbrEmployees = nbrEmployees;
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
     * @return the nbrEmployees
     */
    public int getNbrEmployees() {
        return nbrEmployees;
    }

    /**
     * @param nbrEmployees the nbrEmployees to set
     */
    public void setNbrEmployees(int nbrEmployees) {
        this.nbrEmployees = nbrEmployees;
    }

    /**
     * @return the ville
     */
    public Ville getVille() {
        return ville;
    }

    /**
     * @param ville the ville to set
     */
    public void setVille(Ville ville) {
        this.ville = ville;
    }
}


