package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Models.*;

public class VilleDaoImpl extends JdbcDao{

    public VilleDaoImpl(Connection connection) {
        super(connection); 
    }
 
    @Override 
    public Entity findById(int id){ 
        Ville ville  = null; 
        String sqlReq = "SELECT * FROM ville WHERE id_ville = ?"; 
        PreparedStatement statement = null; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            
            statement.setInt(1, id); 
            
            ResultSet resultSet = statement.executeQuery(); 
            
            while (resultSet.next()) { 
                ville = new Ville(); 
                ville.setId(resultSet.getInt("id_ville")); 
                ville.setNom(resultSet.getString("nom_ville")); 
                System.out.println("Ville : " + ville.getId() + " || " + ville.getNom());
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 
 
        return ville; 
    } 

    @Override 
    public void update(Entity entity) throws DaoException { 
        Ville ville = (Ville) entity; 
 
        PreparedStatement statement = null; 
 
        String sqlReq = "update ville set nom_ville = ?, nbr_habitant = ? where id_ville = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setString(1, ville.getNom()); 
            statement.setInt(2, ville.getNbrHabitant()); 
            statement.setInt(3, ville.getId()); 
 
            int res = statement.executeUpdate(); 
            if (res> 0) System.out.println("Ligne modifiée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    @Override 
    public void delete(Entity entity) throws DaoException { 
        Ville ville = (Ville) entity; 
        PreparedStatement statement = null; 
 
        String sqlReq = "delete from ville where id_ville = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, ville.getId()); 
 
            int res = statement.executeUpdate(); 
            if (res > 0) System.out.println("Ligne supprimée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    }

    @Override 
    public Collection<Entity> findAll() throws DaoException { 
        Collection<Entity> villes = new ArrayList<>();
        try { 
            Statement statement = connection.createStatement(); 
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ville"); 
            while (resultSet.next()) {
                
                Ville ville = new Ville(); 
                ville.setId(resultSet.getInt("id_ville")); 
                ville.setNom(resultSet.getString("nom_ville")); 
                ville.setNbrHabitant(resultSet.getInt("nbr_habitant"));  
 
                villes.add(ville); 
                System.out.println("Ville : " + ville.getId() + " || " + ville.getNom() + " || " + ville.getNbrHabitant());
                
            } 
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
       
        return villes; 
    }

    @Override 
    public void create(Entity entity) throws DaoException { 
        Ville ville = (Ville) entity; 
 
        PreparedStatement statement = null; 
        String sqlReq = "insert into ville( id_ville, nom_ville, nbr_habitant) values (?, ?, ?)"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setInt(1, ville.getId()); 
            statement.setString(2, ville.getNom()); 
            statement.setInt(3, ville.getNbrHabitant()); 

            int res = statement.executeUpdate();
            if (res > 0) System.out.println("Ligne insérée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    /**
     * Permet de recevoir le nombre de l'id final dans la table ville, principalement utilise pour inserer des lignes
     * s'utilise en incrementant le retour de cette fonction : getCompteur() + 1 
     * @return int compteur
     * @throws DaoException
     */
    public int getCompteur() throws DaoException {
        int compteur = 0;
        PreparedStatement statement = null; 
        String sqlReq = "SELECT MAX(id_ville) as compteur FROM ville"; 
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
}
