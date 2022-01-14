
package Test;

import java.sql.Connection;
import java.sql.Date;

import DAO.ContratDaoImpl;
import DAO.DaoException;
import Models.Agence;
import Models.Client;
import Models.Contrat;
import Models.Vehicule;

public class ContratJdbcDaoTest {
    
    public static void main(String[] args) throws DaoException {
        Connection conn = PostgresConnection.getInstance();

        ContratDaoImpl contratDao = new ContratDaoImpl(conn);

        Date date_retrait=Date.valueOf("2012-03-31");//converting string into sql date
        Date date_retour=Date.valueOf("2020-03-31");//converting string into sql date
        Client client = new Client(0);
        Vehicule vehicule = new Vehicule(12345);
        Agence agence = new Agence(0);

        Contrat contrat = new Contrat(contratDao.getCompteur()+1,date_retrait,date_retour,20.0, 70.0,client,vehicule,agence);

        try {
            //contratDao.create(contrat); // fonctionne
            contratDao.update(contrat);
            contratDao.delete(contrat);
            contratDao.findAll(); // fonctionne
            
        } catch (Exception ex) {
            
        }

    }
}