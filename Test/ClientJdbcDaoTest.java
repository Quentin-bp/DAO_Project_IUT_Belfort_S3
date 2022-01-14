
package Test;

import java.sql.Connection;

import DAO.AgenceDaoImpl;
import DAO.ClientDaoImpl;
import DAO.DaoException;

import Models.*;

public class ClientJdbcDaoTest {
    
    public static void main(String[] args) throws DaoException {
        Connection conn = PostgresConnection.getInstance();

        ClientDaoImpl clientDao = new ClientDaoImpl(conn);
        AgenceDaoImpl agenceDao = new AgenceDaoImpl(conn);

        Ville ville = new Ville(0);

        Client client = new Client(2,"Test", "aaa", "codepostal", ville);

        Vehicule vehicule = new Vehicule(12345);
        vehicule.setAgence((Agence) agenceDao.findById(1));
        vehicule.setPrix_location_jour(10);

        Agence agence = new Agence(2);
        try {
            //clientDao.create(client); // fonctionne
            //clientDao.update(client);
            //clientDao.findAll();
            //clientDao.delete(client);
            //clientDao.findAll(); // fonctionne
             
            Contrat contrat = clientDao.louer(client, vehicule, agence, "2054-12-07", 70); // on creer le contrat de location, sans les valeur de km retour et la date de retour
    
            vehicule.setNb_km(300);  // on materialise un nombre de km parcouru 
            contrat = clientDao.rendreVoiture(contrat, vehicule, "2057-01-03"); // on rend la voiture avec un nb de km different 

            Facture facture = clientDao.etablirFacture(client, vehicule, contrat); // on fait la facture a partir du contrat acheve
            
            
        } catch (Exception ex) {
            
        }

    }
}