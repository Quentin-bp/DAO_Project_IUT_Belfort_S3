
package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;


import Models.*;

public class FactureDaoImpl extends JdbcDao{

    private ContratDaoImpl contratDao; 

    public FactureDaoImpl(Connection connection) {
        super(connection); 
    }
 
    @Override 
    public Entity findById(int id) throws DaoException { 
        Facture facture  = null; 
 
        String sqlReq = "SELECT * FROM facture WHERE id_facture = ?"; 
 
        PreparedStatement statement = null; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, id); 
            ResultSet resultSet = statement.executeQuery(); 
 
            while (resultSet.next()) { 
                facture = new Facture(); 
                facture.setId(resultSet.getInt("id_facture")); 
                facture.setMontant(resultSet.getDouble("montant"));

                Contrat contrat = (Contrat) contratDao.findById(resultSet.getInt("id_contrat"));  
                
                System.out.println("Facture : "+ facture.getId() + " || " + facture.getMontant() + "||" + contrat.getId());
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 
 
        return facture; 
    } 

    @Override 
    public void update(Entity entity) throws DaoException { 
        Facture facture = (Facture) entity; 
 
        PreparedStatement statement = null; 
 
        String sqlReq = "update facture set montant = ? where id_facture = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setDouble(1, facture.getMontant());  
            statement.setInt(2, facture.getId());  

            int res = statement.executeUpdate(); 
            if (res> 0) System.out.println("Ligne modifiée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    @Override 
    public void delete(Entity entity) throws DaoException { 
        Facture facture = (Facture) entity; 
        PreparedStatement statement = null; 
 
        String sqlReq = "delete from facture where id_facture = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, facture.getId()); 
 
            int res = statement.executeUpdate(); 
            if (res > 0) System.out.println("Ligne supprimée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    }

    @Override 
    public Collection<Entity> findAll() throws DaoException { 
        Collection<Entity> factures = new ArrayList<>();
        contratDao = new ContratDaoImpl(connection);
        try { 
            Statement statement = connection.createStatement(); 
            ResultSet resultSet = statement.executeQuery("SELECT * FROM facture"); 
            while (resultSet.next()) {
                
                Facture facture = new Facture(); 
                Contrat contrat = (Contrat) contratDao.findById(resultSet.getInt("id_contrat")); 

                facture.setId(resultSet.getInt("id_facture")); 
                facture.setMontant(resultSet.getDouble("montant"));
 
                factures.add(facture); 
                System.out.println("Facture : "+ facture.getId() + " || " + facture.getMontant() + " || " + contrat.getId());
                
            } 
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
       
        return factures; 
    }

    @Override 
    public void create(Entity entity) throws DaoException { 
        Facture facture = (Facture) entity; 
 
        PreparedStatement statement = null; 
        String sqlReq = "insert into facture(id_facture, montant,id_contrat) values (?, ?, ?)"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            
            statement.setInt(1, facture.getId()); 
            statement.setDouble(2, facture.getMontant()); 
            statement.setInt(3, facture.getContrat().getId());  
            
            int res = statement.executeUpdate();
            if (res > 0) System.out.println("Ligne insérée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 

       
    } 

    /**
     * Permet de recevoir le nombre de l'id final dans la table facture, principalement utilise pour inserer des lignes
     * s'utilise en incrementant le retour de cette fonction : getCompteur() + 1 
     * @return int compteur
     * @throws DaoException
     */
    public int getCompteur() throws DaoException {
        int compteur = 0;
        PreparedStatement statement = null; 
        String sqlReq = "SELECT MAX(id_facture) as compteur FROM facture"; 
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
