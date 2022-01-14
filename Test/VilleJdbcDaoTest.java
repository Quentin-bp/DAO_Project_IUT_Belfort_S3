package Test;

import java.sql.Connection;

import DAO.DaoException;
import DAO.VilleDaoImpl;
import Models.Ville;

public class VilleJdbcDaoTest {
    
    public static void main(String[] args) throws DaoException {
        Connection conn = PostgresConnection.getInstance();

        VilleDaoImpl villeDao = new VilleDaoImpl(conn);
        
        Ville ville = new Ville(villeDao.getCompteur()+1,"Update",5);

        try {
            villeDao.create(ville); // fonctionne
            villeDao.update(ville); // fonctionne
            villeDao.delete(ville); // fonctionne
            villeDao.findAll(); // fonctionne
            
        } catch (Exception ex) {
            
        }

    }
}
