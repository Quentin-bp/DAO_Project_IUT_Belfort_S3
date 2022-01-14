package Models;

public class Modele extends Entity{
    private int id;
    private String denomination;
    private String puissance_fiscale;

    public Modele(){
        this(0,null,null);
    }
    
    public Modele(int id){
        this(id,null,null);
    }
    
    public Modele(int id, String denomination, String puissance_fiscale) {
        super();
        this.id = id;
        this.denomination = denomination;
        this.puissance_fiscale = puissance_fiscale;
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
     * @return the denomination
     */
    public String getDenomination() {
        return denomination;
    }
    /**
     * @param denomination the denomination to set
     */
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }
    /**
     * @return the puissance_fiscale
     */
    public String getPuissance_fiscale() {
        return puissance_fiscale;
    }
    /**
     * @param puissance_fiscale the puissance_fiscale to set
     */
    public void setPuissance_fiscale(String puissance_fiscale) {
        this.puissance_fiscale = puissance_fiscale;
    }


}
