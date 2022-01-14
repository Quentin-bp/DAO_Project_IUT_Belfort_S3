package Models;

public class Facture extends Entity{
    private int id;
    private double montant;
    private Contrat contrat;

    public Facture(){
        
    }

    public Facture(int id, double montant, Contrat contrat){
        super();
        this.id = id;
        this.montant = montant;
        this.contrat = contrat;
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
     * @return the montant
     */
    public double getMontant() {
        return montant;
    }

    /**
     * @param montant the montant to set
     */
    public void setMontant(double montant) {
        this.montant = montant;
    }

    /**
     * @return the contrat
     */
    public Contrat getContrat() {
        return contrat;
    }

    /**
     * @param contrat the contrat to set
     */
    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

}
