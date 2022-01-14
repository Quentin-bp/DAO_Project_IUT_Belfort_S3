
package Test;

import java.sql.Connection;

import DAO.AgenceDaoImpl;
import DAO.DaoException;
import Models.Agence;
import Models.Ville;

public class AgenceJdbcDaoTest {
    
    public static void main(String[] args) throws DaoException {
        Connection conn = PostgresConnection.getInstance();

        AgenceDaoImpl agenceDao = new AgenceDaoImpl(conn);

        Ville ville = new Ville(0);
        //Agence agence = new Agence(agenceDao.getCompteur()+1,60,ville);
        Agence agence = new Agence(2);

        try {
            //agenceDao.create(agence); //
            //agenceDao.update(agence);
            //agenceDao.delete(agence);
            //agenceDao.findAll(); // 

            //genceDao.getChiffreAffairePourMois(agence,"1");
            agenceDao.topClientLocation(agence, "2023");
            //agenceDao.getChiffreAffaireToutesAgencePourAnnee("2023");
            
        } catch (Exception ex) {
            
        }

    }
}