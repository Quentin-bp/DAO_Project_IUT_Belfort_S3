
package Test;

import java.sql.Connection;

import DAO.FactureDaoImpl;
import DAO.DaoException;
import Models.Contrat;
import Models.Facture;

public class FactureJdbcDaoTest {
    
    public static void main(String[] args) throws DaoException {
        Connection conn = PostgresConnection.getInstance();

        FactureDaoImpl factureDao = new FactureDaoImpl(conn);

        Contrat contrat = new Contrat(1);
        Facture facture = new Facture(factureDao.getCompteur()+1,80.0,contrat);

        try {
            factureDao.create(facture); // fonctionne
            //factureDao.update(facture);
            //factureDao.delete(facture);
            factureDao.findAll(); // fonctionne
            
        } catch (Exception ex) {
            
        }

    }
}