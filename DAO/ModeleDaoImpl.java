package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Models.*;

public class ModeleDaoImpl extends JdbcDao{

    public ModeleDaoImpl(Connection connection) {
        super(connection); 
    }
 
    @Override 
    public Entity findById(int id) throws DaoException { 
        Modele modele  = null; 
 
        String sqlReq = "SELECT * FROM modele WHERE id_modele = ?"; 
 
        PreparedStatement statement = null; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, id); 
            ResultSet resultSet = statement.executeQuery(); 
 
            while (resultSet.next()) { 
                modele = new Modele(); 
                modele.setId(resultSet.getInt("id_modele")); 
                modele.setDenomination(resultSet.getString("denomination")); 
                modele.setPuissance_fiscale(resultSet.getString("puissance_fiscale")); 
                System.out.println("Modele : "+modele.getId() + " || " + modele.getDenomination()+ " || " + modele.getPuissance_fiscale());
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 
 
        return modele; 
    } 

    @Override 
    public void update(Entity entity) throws DaoException { 
        Modele modele = (Modele) entity; 
 
        PreparedStatement statement = null; 
 
        String sqlReq = "update modele set denomination = ?, puissance_fiscale = ? where id_modele = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setString(1, modele.getDenomination()); 
            statement.setString(2, modele.getPuissance_fiscale()); 
            statement.setInt(3, modele.getId()); 
 
            int res = statement.executeUpdate(); 
            if (res> 0) System.out.println("Ligne modifiée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    @Override 
    public void delete(Entity entity) throws DaoException { 
        Modele modele = (Modele) entity; 
        PreparedStatement statement = null; 
 
        String sqlReq = "delete from modele where id_modele = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, modele.getId()); 
 
            int res = statement.executeUpdate(); 
            if (res > 0) System.out.println("Ligne supprimée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    }

    @Override 
    public Collection<Entity> findAll() throws DaoException { 
        Collection<Entity> modeles = new ArrayList<>();
        try { 
            Statement statement = connection.createStatement(); 
            ResultSet resultSet = statement.executeQuery("SELECT * FROM modele"); 
            while (resultSet.next()) {
                
                Modele modele = new Modele(); 
                modele.setId(resultSet.getInt("id_modele")); 
                modele.setDenomination(resultSet.getString("denomination")); 
                modele.setPuissance_fiscale(resultSet.getString("puissance_fiscale")); 
                
 
                modeles.add(modele); 
                System.out.println("Modele : "+modele.getId() + " || " + modele.getDenomination()+ " || " + modele.getPuissance_fiscale());                
            } 
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
       
        return modeles; 
    }

    @Override 
    public void create(Entity entity) throws DaoException { 
        Modele modele = (Modele) entity; 
 
        PreparedStatement statement = null; 
        String sqlReq = "insert into modele( id_modele, denomination, puissance_fiscale) values (?, ?, ?)"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setInt(1, modele.getId()); 
            statement.setString(2, modele.getDenomination()); 
            statement.setString(3, modele.getPuissance_fiscale()); 
            

            int res = statement.executeUpdate();
            if (res > 0) System.out.println("Ligne insérée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    /**
     * Permet de recevoir le nombre de l'id final dans la table modele, principalement utilise pour inserer des lignes
     * s'utilise en incrementant le retour de cette fonction : getCompteur() + 1 
     * @return int compteur
     * @throws DaoException
     */
    public int getCompteur() throws DaoException {
        int compteur = 0;
        PreparedStatement statement = null; 
        String sqlReq = "SELECT MAX(id_modele) as compteur FROM modele"; 
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
