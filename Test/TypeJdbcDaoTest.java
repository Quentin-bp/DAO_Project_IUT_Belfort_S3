
package Test;

import java.sql.Connection;

import DAO.DaoException;
import DAO.TypeDaoImpl;
import Models.Type;

public class TypeJdbcDaoTest {
    
    public static void main(String[] args) throws DaoException {
        Connection conn = PostgresConnection.getInstance();

        TypeDaoImpl typeDao = new TypeDaoImpl(conn);
        
        Type type = new Type(typeDao.getCompteur()+1,"essence");

        try {
            /*
            typeDao.create(type); // fonctionne
            typeDao.update(type); // fonctionne
            typeDao.delete(type); // fonctionne
            */
            typeDao.findAll(); // fonctionne
            typeDao.getChiffreAffaireParType();
            
        } catch (Exception ex) {
            
        }

    }
}