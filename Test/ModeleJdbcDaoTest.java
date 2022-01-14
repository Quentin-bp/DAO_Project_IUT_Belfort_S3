
package Test;

import java.sql.Connection;

import DAO.DaoException;
import DAO.ModeleDaoImpl;
import Models.Modele;

public class ModeleJdbcDaoTest {
    
    public static void main(String[] args) throws DaoException {
        Connection conn = PostgresConnection.getInstance();

        ModeleDaoImpl modeleDao = new ModeleDaoImpl(conn);
        
        Modele modele = new Modele(modeleDao.getCompteur()+1,"deno","puiss");

        try {
            modeleDao.create(modele); // fonctionne
            modeleDao.update(modele); // fonctionne
            modeleDao.delete(modele); // fonctionne
            modeleDao.findAll(); // fonctionne
            
        } catch (Exception ex) {
            
        }

    }
}