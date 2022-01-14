package Models;

public class Client  extends Entity{
    private int id;
    private String nom;
    private String address;
    private String codePostal;
    private Ville ville;


    public Client(){
        this(0,null,null,null,null);
    }
    public Client(int id){
        this(id,null,null,null,null);
    }
    
    public Client(int id, String nom, String address, String codePostal,  Ville ville){
    super();
    this.id = id; 
    this.nom = nom;
    this.address = address;
    this.codePostal = codePostal;
    this.ville = ville; 
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

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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
    
    /**
     * @return the codePostal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * @param codePostal the codePostal to set
     */
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

}



