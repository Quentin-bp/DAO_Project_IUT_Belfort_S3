package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Models.*;

public class TypeDaoImpl extends JdbcDao{

    public TypeDaoImpl(Connection connection) {
        super(connection); 
    }
 
    @Override 
    public Entity findById(int id) throws DaoException { 
        Type type  = null; 
 
        String sqlReq = "SELECT * FROM type WHERE id_type = ?"; 
 
        PreparedStatement statement = null; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, id); 
            ResultSet resultSet = statement.executeQuery(); 
 
            while (resultSet.next()) { 
                type = new Type(); 
                type.setId(resultSet.getInt("id_type")); 
                type.setLibelle(resultSet.getString("libelle_type")); 
                System.out.println("Type : "+type.getId() + " || " + type.getLibelle());
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 
 
        return type; 
    } 

    @Override 
    public void update(Entity entity) throws DaoException { 
        Type type = (Type) entity; 
 
        PreparedStatement statement = null; 
 
        String sqlReq = "update type set libelle_type = ? where id_type = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setString(1, type.getLibelle());  
            statement.setInt(2, type.getId()); 
            
            int res = statement.executeUpdate(); 

            if (res> 0) System.out.println("Ligne modifiée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    @Override 
    public void delete(Entity entity) throws DaoException { 
        Type type = (Type) entity; 
        PreparedStatement statement = null; 
 
        String sqlReq = "delete from type where id_type = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, type.getId()); 
 
            int res = statement.executeUpdate(); 
            if (res > 0) System.out.println("Ligne supprimée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    }

    @Override 
    public Collection<Entity> findAll() throws DaoException { 
        Collection<Entity> types = new ArrayList<>();
        try { 
            Statement statement = connection.createStatement(); 
            ResultSet resultSet = statement.executeQuery("SELECT * FROM type"); 
            while (resultSet.next()) {
                
                Type type = new Type(); 
                type.setId(resultSet.getInt("id_type")); 
                type.setLibelle(resultSet.getString("libelle_type")); 
 
                types.add(type); 
                System.out.println("Type : "+type.getId() + " || " + type.getLibelle());
                
            } 
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
       
        return types; 
    }

    @Override 
    public void create(Entity entity) throws DaoException { 
        Type type = (Type) entity; 
 
        PreparedStatement statement = null; 
        String sqlReq = "insert into type( id_type, libelle_type) values (?, ?)"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setInt(1, type.getId()); 
            statement.setString(2, type.getLibelle());  

            int res = statement.executeUpdate();
            if (res > 0) System.out.println("Ligne insérée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    /**
     * Permet d'afficher le chiffre d'affaire pour chaque type
     * @throws DaoException
     */
    public void getChiffreAffaireParType() throws DaoException{
        PreparedStatement statement = null; 
        
        String sqlReq = "SELECT type.id_type,type.libelle_type, SUM(montant) as chiffre_affaire FROM facture "+ 
        "INNER JOIN contrat ON facture.id_contrat = contrat.id_contrat "+ 
        "INNER JOIN vehicule ON contrat.immatriculation = vehicule.immatriculation "+
        "INNER JOIN type ON vehicule.id_type = type.id_type GROUP BY type.id_type";
        /* Operation effectuee
        1) on recupere la somme de tous les montants de facture 
        2) on lie les factures a un contrat (join)
        3) les contrat sont liees a un vehicule (join)
        4) on lie les vehicules avec les type (join) 
        4) on additionne pour chaque type, les montants des factures en lien
        5) on renvoie le tout
        */
        try { 
            statement = connection.prepareStatement(sqlReq); 
            ResultSet resultSet = statement.executeQuery(); 
 
            while (resultSet.next()) { 
                int id_type = resultSet.getInt("id_type");
                String libelle_type = resultSet.getString("libelle_type");
                double chiffre_affaire = resultSet.getDouble("chiffre_affaire");

                System.out.println("Id type : " + id_type + " || " +"Libelle type : " + libelle_type + " || " + "Chiffre d'affaire : " + chiffre_affaire);
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 
    }

    /**
     * Permet de recevoir le nombre de l'id final dans la table type, principalement utilise pour inserer des lignes
     * s'utilise en incrementant le retour de cette fonction : getCompteur() + 1 
     * @return int compteur
     * @throws DaoException
     */
    public int getCompteur() throws DaoException {
        int compteur = 0;
        PreparedStatement statement = null; 
        String sqlReq = "SELECT MAX(id_type) as compteur FROM type"; 
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
