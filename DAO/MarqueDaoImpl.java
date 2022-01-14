package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Models.*;

public class MarqueDaoImpl extends JdbcDao{

    public MarqueDaoImpl(Connection connection) {
        super(connection); 
    }
 
    @Override 
    public Entity findById(int id) throws DaoException { 
        Marque marque  = null; 
 
        String sqlReq = "SELECT * FROM marque WHERE id_marque = ?"; 
 
        PreparedStatement statement = null; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, id); 
            ResultSet resultSet = statement.executeQuery(); 
 
            while (resultSet.next()) { 
                marque = new Marque(); 
                marque.setId(resultSet.getInt("id_marque")); 
                marque.setNom(resultSet.getString("nom_marque")); 
                System.out.println("Marque : "+marque.getId() + " || " + marque.getNom());
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 
 
        return marque; 
    } 

    @Override 
    public void update(Entity entity) throws DaoException { 
        Marque marque = (Marque) entity; 
 
        PreparedStatement statement = null; 
 
        String sqlReq = "update marque set nom_marque = ? where id_marque = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setString(1, marque.getNom());  
            statement.setInt(2, marque.getId());
             
            int res = statement.executeUpdate(); 

            if (res> 0) System.out.println("Ligne modifiée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    @Override 
    public void delete(Entity entity) throws DaoException { 
        Marque marque = (Marque) entity; 
        PreparedStatement statement = null; 
 
        String sqlReq = "delete from marque where id_marque = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, marque.getId()); 
 
            int res = statement.executeUpdate(); 
            if (res > 0) System.out.println("Ligne supprimée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    }

    @Override 
    public Collection<Entity> findAll() throws DaoException { 
        Collection<Entity> marques = new ArrayList<>();
        try { 
            Statement statement = connection.createStatement(); 
            ResultSet resultSet = statement.executeQuery("SELECT * FROM marque"); 
            while (resultSet.next()) {
                
                Marque marque = new Marque(); 
                marque.setId(resultSet.getInt("id_marque")); 
                marque.setNom(resultSet.getString("nom_marque")); 
 
                marques.add(marque); 
                System.out.println("Marque : "+marque.getId() + " || " + marque.getNom());
                
            } 
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
       
        return marques; 
    }

    @Override 
    public void create(Entity entity) throws DaoException { 
        Marque marque = (Marque) entity; 
 
        PreparedStatement statement = null; 
        String sqlReq = "insert into marque( id_marque, nom_marque) values (?, ?)"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setInt(1, marque.getId()); 
            statement.setString(2, marque.getNom());  

            int res = statement.executeUpdate();
            if (res > 0) System.out.println("Ligne insérée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    /**
     * Permet de recevoir le nombre de l'id final dans la table marque, principalement utilise pour inserer des lignes
     * s'utilise en incrementant le retour de cette fonction : getCompteur() + 1 
     * @return int compteur
     * @throws DaoException
     */
    public int getCompteur() throws DaoException {
        int compteur = 0;
        PreparedStatement statement = null; 
        String sqlReq = "SELECT MAX(id_marque) as compteur FROM marque"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            ResultSet resultSet = statement.executeQuery(); 
            while (resultSet.next()) { 
                compteur = resultSet.getInt("compteur"); 
            }
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
        return compteur;
    }

    /**
     * Permet d'afficher le nombre de vehicule pour chaque marque 
     * @throws DaoException
     */
    public void afficherNombreVehiculeParMarque() throws DaoException{
        PreparedStatement statement = null; 
        String sqlReq = "SELECT COUNT(immatriculation) as result, marque.nom_marque FROM vehicule " +
        "INNER JOIN marque ON vehicule.id_marque = marque.id_marque GROUP BY marque.nom_marque"; 
        /*
        Operation effectuee : 
        1) On relies les marques et les vehicules (join)
        2) On compte et on regroupe en fonction des marques (group by)
        1) On retourne le resultat

        */
        try { 
            statement = connection.prepareStatement(sqlReq); 
            ResultSet resultSet = statement.executeQuery(); 
            while (resultSet.next()) { 
                int nbrVehiculeParMarque = resultSet.getInt("result"); 
                String nom_marque = resultSet.getString("nom_marque");
                System.out.println("Nombre de vehicule pour la marque " + nom_marque + " : "+ nbrVehiculeParMarque) ;
            }
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    }
}
    