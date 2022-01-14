package Models;

public class Type extends Entity{
    
    private int id;
    private String libelle;

    public Type() {
        this(0, null);
    }
    public Type(int id) {
        this(id, null);
    }

    public Type(int id, String libelle) {
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
