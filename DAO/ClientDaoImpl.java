package DAO;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Models.*;

public class ClientDaoImpl extends JdbcDao{

    private VilleDaoImpl villeDao; 

    public ClientDaoImpl(Connection connection) {
        super(connection); 
    }
 
    @Override 
    public Entity findById(int id) throws DaoException { 
        Client client  = null; 
        villeDao = new VilleDaoImpl(connection);
        String sqlReq = "SELECT * FROM client WHERE id_client = ?"; 
 
        PreparedStatement statement = null; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, id); 
            ResultSet resultSet = statement.executeQuery(); 
 
            while (resultSet.next()) { 
                client = new Client(); 
                client.setId(resultSet.getInt("id_client")); 
                client.setNom(resultSet.getString("nom_client"));
                client.setAddress(resultSet.getString("adresse_client"));
                client.setCodePostal(resultSet.getString("code_postal_client"));


                Ville ville = (Ville) villeDao.findById(resultSet.getInt("id_ville"));  
                
                System.out.println("Client : " + client.getId() + " || " + client.getNom() + "||" + 
                client.getAddress() + " || " + client.getCodePostal() + " || " + ville.getId());
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 
 
        return client; 
    } 

    @Override 
    public void update(Entity entity) throws DaoException { 
        Client client = (Client) entity; 
 
        PreparedStatement statement = null; 
 
        String sqlReq = "update client set nom_client = ?, adresse_client = ?, code_postal_client = ? where id_client = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setString(1, client.getNom());  
            statement.setString(2, client.getAddress());  
            statement.setString(3, client.getCodePostal());  
            statement.setInt(4, client.getId());    

            int res = statement.executeUpdate(); 
            if (res> 0) System.out.println("Ligne modifiÃ©e");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    @Override 
    public void delete(Entity entity) throws DaoException { 
        Client client = (Client) entity; 
        PreparedStatement statement = null; 
 
        String sqlReq = "delete from client where id_client = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, client.getId()); 
 
            int res = statement.executeUpdate(); 
            if (res > 0) System.out.println("Ligne supprimÃ©e");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    }

    @Override 
    public Collection<Entity> findAll() throws DaoException { 
        Collection<Entity> clients = new ArrayList<>();
        villeDao = new VilleDaoImpl(connection);
        try { 
            Statement statement = connection.createStatement(); 
            ResultSet resultSet = statement.executeQuery("SELECT * FROM client"); 
            while (resultSet.next()) {
                
                Client client = new Client(); 
                Ville ville = (Ville) villeDao.findById(resultSet.getInt("id_ville")); 

                client.setId(resultSet.getInt("id_client")); 
                client.setNom(resultSet.getString("nom_client"));
                client.setAddress(resultSet.getString("adresse_client"));
                client.setCodePostal(resultSet.getString("code_postal_client"));
 
                clients.add(client); 
                System.out.println("Client : " + client.getId() + " || " + client.getNom() + " || " + client.getAddress() + " || " + client.getCodePostal() + " || " + ville.getId());                
            } 
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
       
        return clients; 
    }

    @Override 
    public void create(Entity entity) throws DaoException { 
        Client client = (Client) entity; 
 
        PreparedStatement statement = null; 
        String sqlReq = "insert into client(id_client, nom_client,adresse_client, code_postal_client,id_ville) values (?, ?, ?,?,?)"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setInt(1, client.getId()); 
            statement.setString(2, client.getNom()); 
            statement.setString(3, client.getAddress()); 
            statement.setString(4, client.getCodePostal()); 
            statement.setInt(5, client.getVille().getId());  

            int res = statement.executeUpdate();
            if (res > 0) System.out.println("Ligne insÃ©rÃ©e");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 
    
    /**
     * Permet de recevoir le nombre de l'id final dans la table client, principalement utilise pour inserer des lignes
     * s'utilise en incrementant le retour de cette fonction : getCompteur() + 1 
     * @return int compteur
     * @throws DaoException
     */
    public int getCompteur() throws DaoException {
        int compteur = 0;
        PreparedStatement statement = null; 
        String sqlReq = "SELECT MAX(id_client) as compteur FROM client"; 
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
 *  Creer un contrat avec toutes les valeurs donnees
 * @param client
 * @param vehicule
 * @param agence
 * @param dateLocation
 * @return Contrat (initialiser) 
 * @throws DaoException
 */
    public Contrat louer(Client client, Vehicule vehicule,Agence agence, String dateLocation,int duree) throws DaoException {
        
        AgenceDaoImpl agenceDao = new AgenceDaoImpl(connection); // on instancie la partie de traitement des donnees
        ContratDaoImpl contratDao = new ContratDaoImpl(connection);

        Agence agenceDepart = (Agence) agenceDao.findById(vehicule.getAgence().getId()); // on recupere les 2 agences
        Agence agenceRetour = (Agence) agenceDao.findById(agence.getId());

        if (agenceDepart.getId() == agenceRetour.getId()){ // si les memes , erreur
            System.out.println("L'agence de départ ne doit pas être la même que celle de retour");
            return null; 
        }
        Date date_retrait = Date.valueOf(dateLocation); // on convertie la string en date
        Date date_retour = Contrat.ajouterJourDepuisUneDate(date_retrait, duree); // on creer la date de retour a partir de la duree
        // On creer un nouveau contrat avec un id tel que id = maximum id +1, le nbr de km au retour est null car pas encore utilise
        Contrat contrat = new Contrat(contratDao.getCompteur()+1,date_retrait,date_retour,vehicule.getNb_km(), vehicule.getNb_km(),client, vehicule,agence);
        contratDao.create(contrat); // on creer le contrat dans la bdd 
        return contrat;
    }
    /**
     * modifie le contrat creer dans la fonction louer() pour rajouter le km du retour et une date de rendu du vehicule
     * @param contrat
     * @param vehicule
     * @param dateFin
     * @return Contrat final
     * @throws DaoException
     */
    public Contrat rendreVoiture(Contrat contrat,Vehicule vehicule, String dateFin) throws DaoException{
        ContratDaoImpl contratDao = new ContratDaoImpl(connection);

        if ( ((Contrat) contratDao.findById(contrat.getId()) == null)){ // on verifie que le contrat est valide
            System.out.println("Le contrat n'existe pas ou n'existe plus");
            return null;
        };

        if (contrat.getVehicule().getImmatriculation() != vehicule.getImmatriculation()){ // on verifie que le vehicule est le meme que dans le contrat
            System.out.println("Le vehicule et le contrat ne vont pas ensemble, veuillez vÃ©rifier vos informations");
            return null;
        }
        // On admet que le contrat est le bon 

        Date date_retour = Date.valueOf(dateFin); // on remplie la date de retour du contrat
        contrat.setDate_retour(date_retour);

        contrat.setKm_retour(vehicule.getNb_km()); // on remplie le nombre de km total parcouru apres le retour (cad l'ancien + la variation) [...]
        contratDao.update(contrat); // [suite] pour juste la variation, on fait :  vehicule.getNb_km() - contrat.getVehicule().getNb_km()
        return contrat;
    }
    /**
     * Creer une facture en fonction du contrat final obtenu dans la fonction rendreVoiture()
     * @param client
     * @param vehicule
     * @param contrat
     * @return Facture
     * @throws DaoException
     */
    public Facture etablirFacture(Client client, Vehicule vehicule,Contrat contrat) throws DaoException{
        FactureDaoImpl factureDao = new FactureDaoImpl(connection);
        ContratDaoImpl contratDao = new ContratDaoImpl(connection);

        if ( ((Contrat) contratDao.findById(contrat.getId()) == null)){  // on verifie que le contrat est valide
            System.out.println("Le contrat n'existe pas ou n'existe plus");
            return null;
        };
        if (contrat.getClient().getId() != client.getId()){ // on verifie que le client est le meme que celui du contrat
            System.out.println("Le client et le contrat ne vont pas ensemble, veuillez vÃ©rifier vos informations");
            return null;
        }

        if (contrat.getVehicule().getImmatriculation() != vehicule.getImmatriculation()){ // on verifie que le vehicule est le meme que dans le contrat
            System.out.println("Le vehicule et le contrat ne vont pas ensemble, veuillez vÃ©rifier vos informations");
            return null;
        }
        // on commence le traitement
        Date date_retrait = contrat.getDate_retrait(); // on recupere les date du contrat
        Date date_retour = contrat.getDate_retour();
        
        int nbJour = Contrat.getNbJourDifference(date_retrait,date_retour); // on recupere le nbr de jour entre les deux dates
        double montant = nbJour * vehicule.getPrix_location_jour(); // prix total de la location 

        Facture facture = new Facture(factureDao.getCompteur()+1,montant,contrat); 
        factureDao.create(facture); // on creer la facture dans la bdd

        return facture;
    }
}
