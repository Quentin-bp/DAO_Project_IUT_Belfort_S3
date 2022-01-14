
package Test;

import java.sql.Connection;
import java.sql.Date;

import DAO.VehiculeDaoImpl;
import Models.*;

public class VehiculeJdbcDaoTest {
    
    public static void main(String[] args) {
        Connection conn = PostgresConnection.getInstance();

        VehiculeDaoImpl vehiculeDao = new VehiculeDaoImpl(conn);
        Date date_circulation=Date.valueOf("2012-03-31");//converting string into sql date

        Marque marque = new Marque(4);
        Modele modele = new Modele(0);
        Categorie categorie = new Categorie(0);
        Type type = new Type(0);
        Agence agence = new Agence(0);

        Vehicule vehicule = new Vehicule(978658721,date_circulation,"etat",20000,70.0,marque,modele,categorie,type,agence);

        try {
            //vehiculeDao.create(vehicule); // fonctionne
            //vehiculeDao.update(vehicule); // fonctionne
            //vehiculeDao.delete(vehicule); // fonctionne
            //vehiculeDao.findAll(); // fonctionne
            
            vehiculeDao.getNbVehiculeParAgencePlus2ansEt150kKm();
        } catch (Exception ex) {
            
        }

    }
}