
package Test;

import java.sql.Connection;

import DAO.CategorieDaoImpl;
import DAO.DaoException;
import Models.Categorie;

public class CategorieJdbcDaoTest {
    
    public static void main(String[] args) throws DaoException {
        Connection conn = PostgresConnection.getInstance();

        CategorieDaoImpl categorieDao = new CategorieDaoImpl(conn);
        
        Categorie categorie = new Categorie(categorieDao.getCompteur()+1,"Test");

        try {
            /*
            categorieDao.findAll(); // fonctionne
            categorieDao.create(categorie); // fonctionne
            categorieDao.update(categorie); // fonctionne
            categorieDao.delete(categorie); // fonctionne
            */
            //categorieDao.findAll(); // fonctionne
            

            categorieDao.getChiffreAffaireParCategorie();
        } catch (Exception ex) {
            
        }

    }
}