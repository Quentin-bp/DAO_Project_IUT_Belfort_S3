
package Test;

import java.sql.Connection;

import DAO.DaoException;
import DAO.MarqueDaoImpl;
import Models.Marque;

public class MarqueJdbcDaoTest {
    
    public static void main(String[] args) throws DaoException {
        Connection conn = PostgresConnection.getInstance();

        MarqueDaoImpl marqueDao = new MarqueDaoImpl(conn);
        
        Marque marque = new Marque(marqueDao.getCompteur()+1,"Test");

        try {
            /*
            marqueDao.create(marque); // fonctionne
            marqueDao.update(marque); // fonctionne
            marqueDao.delete(marque); // fonctionne
            marqueDao.findAll(); // fonctionne
            */
            marqueDao.afficherNombreVehiculeParMarque();
        } catch (Exception ex) {
            
        }

    }
}