
package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Models.*;

public class AgenceDaoImpl extends JdbcDao{

    private VilleDaoImpl villeDao; 

    public AgenceDaoImpl(Connection connection) {
        super(connection); 
    }
 
    @Override 
    public Entity findById(int id) throws DaoException { 
        Agence agence  = null; 
        villeDao = new VilleDaoImpl(connection);

        String sqlReq = "SELECT * FROM agence WHERE id_agence = ?"; 
 
        PreparedStatement statement = null; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, id); 
            ResultSet resultSet = statement.executeQuery(); 
 
            while (resultSet.next()) { 
                agence = new Agence(); 
                agence.setId(resultSet.getInt("id_agence")); 
                agence.setNbrEmployees(resultSet.getInt("nbr_employee"));

                Ville ville = (Ville) villeDao.findById(resultSet.getInt("id_ville"));  
                System.out.println("Agence : " + agence.getId() + " || " + agence.getNbrEmployees() + "||" + ville.getId());
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 
 
        return agence; 
    } 

    @Override 
    public void update(Entity entity) throws DaoException { 
        Agence agence = (Agence) entity; 
 
        PreparedStatement statement = null; 
 
        String sqlReq = "update agence set nbr_employee = ? where id_agence = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setDouble(1, agence.getNbrEmployees());  
            statement.setInt(2, agence.getId());  

            int res = statement.executeUpdate(); 
            if (res> 0) System.out.println("Ligne modifiée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    @Override 
    public void delete(Entity entity) throws DaoException { 
        Agence agence = (Agence) entity; 
        PreparedStatement statement = null; 
 
        String sqlReq = "delete from agence where id_agence = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, agence.getId()); 
 
            int res = statement.executeUpdate(); 
            if (res > 0) System.out.println("Ligne supprimée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    }

    @Override 
    public Collection<Entity> findAll() throws DaoException { 
        Collection<Entity> agences = new ArrayList<>();
        villeDao = new VilleDaoImpl(connection);
        
        try { 
            Statement statement = connection.createStatement(); 
            ResultSet resultSet = statement.executeQuery("SELECT * FROM agence"); 
            while (resultSet.next()) {
                Agence agence = new Agence(); 
                Ville ville = (Ville) villeDao.findById(resultSet.getInt("id_ville")); 
            
                agence.setId(resultSet.getInt("id_agence")); 
                agence.setNbrEmployees(resultSet.getInt("nbr_employee"));
                
 
                agences.add(agence); 
                System.out.println("Agence : " +agence.getId() + " || " + agence.getNbrEmployees() + " || " + ville.getId());
                
            } 
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
       
        return agences; 
    }

    @Override 
    public void create(Entity entity) throws DaoException { 
        Agence agence = (Agence) entity; 
        PreparedStatement statement = null; 
        String sqlReq = "insert into agence(id_agence, nbr_employee, id_ville) values (?, ?, ?)"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 

            statement.setInt(1, agence.getId()); 
            statement.setInt(2, agence.getNbrEmployees());
            statement.setInt(3,agence.getVille().getId());

            int res = statement.executeUpdate();
            System.out.println(res);
            if (res > 0) System.out.println("Ligne insérée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    /**
     * Permet de recevoir le nombre de l'id final dans la table agence, principalement utilise pour inserer des lignes
     * s'utilise en incrementant le retour de cette fonction : getCompteur() + 1 
     * @return int compteur
     * @throws DaoException
     */
    public int getCompteur() throws DaoException {
        int compteur = 0;
        PreparedStatement statement = null; 
        String sqlReq = "SELECT MAX(id_agence) as compteur FROM agence"; 
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
     * Permet de recuperer le chiffre d'affaire d'une agence donnee en fonction d'un mois donnee
     * @param agence
     * @param mois
     * @return le chiffre d'affaire : Double
     * @throws DaoException
     */
    public double getChiffreAffairePourMois(Agence agence, String mois) throws DaoException{
        AgenceDaoImpl agenceDao = new AgenceDaoImpl(connection);

        // Partie traitement du mois, pour ne pas demander n'importe quoi a la bdd
        if ((Agence)agenceDao.findById(agence.getId()) == null) { // on regarde si l'agence existe bien
            System.out.println("L'agence ne correspond à rien dans la base de données");
            return 0;
        }
        if (Contrat.getMoisValide(mois) == 0){
            System.out.println("Le mois entré n'est pas valide");
            return 0;
        }

        //debut de la partie SQL
        String sqlReq = "SELECT SUM(montant) as chiffre_affaire FROM facture "+
        "INNER JOIN contrat ON facture.id_contrat = contrat.id_contrat " +
        "INNER JOIN agence ON contrat.id_agence_retour = agence.id_agence "+
        "WHERE agence.id_agence = " + agence.getId() + " AND EXTRACT(MONTH FROM contrat.date_retour ) =" + mois +" ;" ; 
        /* Operation effectuee
        1) on recupere la somme de tous les montants de facture 
        2) on lie les factures a un contrat (join)
        3) les contrat sont liees a une agence donnee (join)
        4) on cherche pour l'agence donnee tous les contrats avec un mois de date de retour egale au moins donnee 
        5) on renvoie le tout
        */
        PreparedStatement statement = null; 
        int chiffre_affaire = 0;
        try { 
            statement = connection.prepareStatement(sqlReq); 
            ResultSet resultSet = statement.executeQuery(); 
 
            while (resultSet.next()) { 
                chiffre_affaire = resultSet.getInt("chiffre_affaire"); 

                System.out.println("Chiffre d'affaire : " + chiffre_affaire);
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 

        return chiffre_affaire;
    }

    /**
     * Permet de recuperer le ou les clients avec le plus de location pour une annee donnees
     * dans une agence donnee
     * @param agence
     * @param annee
     * @throws DaoException
     */
    public void topClientLocation(Agence agence, String annee) throws DaoException{
        AgenceDaoImpl agenceDao = new AgenceDaoImpl(connection);
        
        if ( ((Agence) agenceDao.findById(agence.getId()) == null)){  // on verifie que le contrat est valide
            System.out.println("L'agence n'existe pas ou n'existe plus");
            return;
        };
        if (Contrat.getAnneeValide(annee) == 0){ // on verifie que l'annee est valide (4 chiffres)
            System.out.println("L'année n'est pas valide");
             return;
        }
        // on commence le traitement
        
        String sqlReq = "SELECT cli.nom_client, cli.id_client as id_client, COUNT(*) as result FROM client as cli "+
        "INNER JOIN contrat as co ON cli.id_client = co.id_client "+ 
        "INNER JOIN agence ON co.id_agence_retour = agence.id_agence "+
        "WHERE agence.id_agence = " + agence.getId() + "AND EXTRACT(YEAR FROM co.date_retour ) = "+ annee+
        " GROUP BY cli.id_client HAVING COUNT(*) = "+
        "(SELECT MAX(result) FROM (SELECT cli.nom_client, cli.id_client as id, COUNT(*) as result FROM client as cli "+
        "INNER JOIN contrat as co ON cli.id_client = co.id_client "+ 
        "INNER JOIN agence ON co.id_agence_retour = agence.id_agence "+ 
        "WHERE agence.id_agence =  " + agence.getId() + " AND EXTRACT(YEAR FROM co.date_retour ) = "+ annee+
        " GROUP BY cli.id_client) as result)"; 
        /* Operation effectuee
        1) on recupere le nom l'id et le nbr de location pour le ou les meilleurs clients
        2) on lie les contrats avec les clients (join)
        3) les contrats sont liees a une agence donnee (join)
        4) on cherche les contrats datant d'une annee donnee, et on compte le nombre de contrat lie a chaque client
        5) on recupere ce nombre et on cherche tous les client avec le meme nombre de contrat, pour enfin les afficher
        */
        PreparedStatement statement = null; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            ResultSet resultSet = statement.executeQuery(); 
 
            while (resultSet.next()) { 
                String nom_client = resultSet.getString("nom_client");
                int id_client = resultSet.getInt("id_client");
                int resultat = resultSet.getInt("result");

                System.out.println("Id client : " + id_client + " || " +"Nom client : " + nom_client + " || " + "Nombre de location : " + resultat);
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 
    }

    /**
     * affiche le chiffre d'affaire de chaque agence pour une annee donnee
     * @param annee
     */
    public void getChiffreAffaireToutesAgencePourAnnee(String annee){

        if (Contrat.getAnneeValide(annee) == 0){  // on verifie que l'annee est valide (4 chiffres)
            System.out.println("L'année n'est pas valide"); 
             return;
        }
        String sqlReq = "SELECT agence.id_agence, SUM(montant) as chiffre_affaire FROM facture "+
        "INNER JOIN contrat ON facture.id_contrat = contrat.id_contrat "+
        "INNER JOIN vehicule ON contrat.immatriculation = vehicule.immatriculation  "+
        "INNER JOIN agence ON vehicule.id_agence = agence.id_agence  "+
        "WHERE EXTRACT(YEAR FROM contrat.date_retour) = "+ annee + 
        " GROUP BY agence.id_agence";
        /* Operation effectuee
        1) on join les agences avec les contrats, puis les contrats avec les factures
        2) on garde uniquement les contrats avec une date de retour ayant une annee egal a l'annee donnee
        3) on additionnes tous les montants des factures de ces contrats, pour chaque agence distinctes
        4) on retourne le tout
        */
        PreparedStatement statement = null; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            ResultSet resultSet = statement.executeQuery(); 
 
            while (resultSet.next()) { 
                int id_agence = resultSet.getInt("id_agence");
                double chiffre_affaire = resultSet.getDouble("chiffre_affaire");

                System.out.println("Id agence : " + id_agence + " || " +" Chiffre d'affaire : " + chiffre_affaire + " à l'année " + annee);
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 
    }
}
