
package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;


import Models.*;

public class ContratDaoImpl extends JdbcDao{

    private ClientDaoImpl clientDao;
    private VehiculeDaoImpl vehiculeDao;
    private AgenceDaoImpl agenceDao;

    public ContratDaoImpl(Connection connection) {
        super(connection); 
    }
 
    @Override 
    public Entity findById(int id) throws DaoException { 
        Contrat contrat  = null; 
        clientDao = new ClientDaoImpl(connection);
        vehiculeDao = new VehiculeDaoImpl(connection);
        agenceDao = new AgenceDaoImpl(connection);

        String sqlReq = "SELECT * FROM contrat WHERE id_contrat = ?"; 
 
        PreparedStatement statement = null; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, id); 
            ResultSet resultSet = statement.executeQuery(); 
 
            while (resultSet.next()) { 
                contrat = new Contrat(); 
                contrat.setId(resultSet.getInt("id_contrat")); 
                contrat.setDate_retrait(resultSet.getDate("date_retrait"));
                contrat.setDate_retour(resultSet.getDate("date_retour"));
                contrat.setKm_retrait(resultSet.getDouble("km_retrait"));
                contrat.setKm_retour(resultSet.getDouble("km_retour"));

                Client client = (Client) clientDao.findById(resultSet.getInt("id_client")); 
                Vehicule vehicule = (Vehicule) vehiculeDao.findById(resultSet.getInt("immatriculation")); 
                Agence agence = (Agence) agenceDao.findById(resultSet.getInt("id_agence_retour")); 
                
                System.out.println("Contrat : "+ contrat.getId() + " || " + contrat.getDate_retour() + "||"+ contrat.getKm_retrait() + "||"
                + contrat.getKm_retour() + "||" + client.getId() + " || "+ vehicule.getImmatriculation() + " ||" + agence.getId());
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 
 
        return contrat; 
    } 

    @Override 
    public void update(Entity entity) throws DaoException { 
        Contrat contrat = (Contrat) entity; 
 
        PreparedStatement statement = null; 

        clientDao = new ClientDaoImpl(connection);
        vehiculeDao = new VehiculeDaoImpl(connection);
        agenceDao = new AgenceDaoImpl(connection);

        String sqlReq = "update contrat set date_retrait = ?, date_retour = ?, km_retrait = ?, km_retour = ? where id_contrat = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setDate(1, contrat.getDate_retrait());  
            statement.setDate(2, contrat.getDate_retour()); 
            statement.setDouble(3, contrat.getKm_retrait());
            statement.setDouble(4, contrat.getKm_retour()); 
            statement.setInt(5, contrat.getId());

            int res = statement.executeUpdate(); 
            if (res> 0) System.out.println("Ligne modifié");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    @Override 
    public void delete(Entity entity) throws DaoException { 
        Contrat contrat = (Contrat) entity; 
        PreparedStatement statement = null; 
 
        String sqlReq = "delete from contrat where id_contrat = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, contrat.getId()); 
 
            int res = statement.executeUpdate(); 
            if (res > 0) System.out.println("Ligne supprimé");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    }

    @Override 
    public Collection<Entity> findAll() throws DaoException { 
        Collection<Entity> contrats = new ArrayList<>();
        clientDao = new ClientDaoImpl(connection);
        vehiculeDao = new VehiculeDaoImpl(connection);
        agenceDao = new AgenceDaoImpl(connection);

        try { 
            Statement statement = connection.createStatement(); 
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contrat"); 
            while (resultSet.next()) {
                Contrat contrat = new Contrat(); 
                contrat.setId(resultSet.getInt("id_contrat")); 
                contrat.setDate_retrait(resultSet.getDate("date_retrait"));
                contrat.setDate_retour(resultSet.getDate("date_retour"));
                contrat.setKm_retrait(resultSet.getDouble("km_retrait"));
                contrat.setKm_retour(resultSet.getDouble("km_retour"));

                
                Client client = (Client) clientDao.findById(resultSet.getInt("id_client")); 
                Vehicule vehicule = (Vehicule) vehiculeDao.findById(resultSet.getInt("immatriculation")); 
                Agence agence = (Agence) agenceDao.findById(resultSet.getInt("id_agence_retour")); 

                System.out.println("Contrat : "+contrat.getId() + " || " + contrat.getDate_retour() + "||"+ contrat.getKm_retrait() + "||"
                + contrat.getKm_retour() + "||" + client.getId() + " || "+ vehicule.getImmatriculation() + " ||" + agence.getId());
                
            } 
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
       
        return contrats; 
    }

    @Override 
    public void create(Entity entity) throws DaoException { 
        Contrat contrat = (Contrat) entity; 
        
        PreparedStatement statement = null; 
        String sqlReq = "insert into contrat(id_contrat, date_retrait, date_retour, km_retrait, km_retour, id_client, immatriculation,id_agence_retour) values (?,?,?,?,?,?,?,?)"; 
        try { 
            statement = connection.prepareStatement(sqlReq);

            statement.setInt(1, contrat.getId()); 
            statement.setDate(2, contrat.getDate_retrait());  
            statement.setDate(3, contrat.getDate_retour()); 

            statement.setDouble(4, contrat.getKm_retrait());
            statement.setDouble(5, contrat.getKm_retour());
            
            statement.setInt(6, contrat.getClient().getId());
            statement.setInt(7, contrat.getVehicule().getImmatriculation());
            statement.setInt(8, contrat.getAgence().getId());

            int res = statement.executeUpdate();
            if (res > 0) System.out.println("Ligne insérée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        }  
    } 

    /**
     * Permet de recevoir le nombre de l'id final dans la table contrat, principalement utilise pour inserer des lignes
     * s'utilise en incrementant le retour de cette fonction : getCompteur() + 1 
     * @return int compteur
     * @throws DaoException
     */
    public int getCompteur() throws DaoException {
        int compteur = 0;
        PreparedStatement statement = null; 
        String sqlReq = "SELECT MAX(id_contrat) as compteur FROM contrat"; 
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
