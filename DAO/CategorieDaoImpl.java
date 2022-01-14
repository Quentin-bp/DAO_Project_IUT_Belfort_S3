package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Models.*;

public class CategorieDaoImpl extends JdbcDao{

    public CategorieDaoImpl(Connection connection) {
        super(connection); 
    }
 
    @Override 
    public Entity findById(int id) throws DaoException { 
        Categorie categorie  = null; 
 
        String sqlReq = "SELECT * FROM categorie WHERE id_categorie = ?"; 
 
        PreparedStatement statement = null; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, id); 
            ResultSet resultSet = statement.executeQuery(); 
 
            while (resultSet.next()) { 
                categorie = new Categorie(); 
                categorie.setId(resultSet.getInt("id_categorie")); 
                categorie.setLibelle(resultSet.getString("libelle_categorie")); 
                System.out.println("Categorie : "+categorie.getId() + " || " + categorie.getLibelle());
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 
 
        return categorie; 
    } 

    @Override 
    public void update(Entity entity) throws DaoException { 
        Categorie categorie = (Categorie) entity; 
 
        PreparedStatement statement = null; 
 
        String sqlReq = "update categorie set libelle_categorie = ? where id_categorie = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setString(1, categorie.getLibelle());  
            statement.setInt(2, categorie.getId()); 
            int res = statement.executeUpdate(); 
            
            if (res> 0) System.out.println("Ligne modifiée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    @Override 
    public void delete(Entity entity) throws DaoException { 
        Categorie categorie = (Categorie) entity; 
        PreparedStatement statement = null; 
 
        String sqlReq = "delete from categorie where id_categorie = ?"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
            statement.setInt(1, categorie.getId()); 
 
            int res = statement.executeUpdate(); 
            if (res > 0) System.out.println("Ligne supprimée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    }

    @Override 
    public Collection<Entity> findAll() throws DaoException { 
        Collection<Entity> categories = new ArrayList<>();
        try { 
            Statement statement = connection.createStatement(); 
            ResultSet resultSet = statement.executeQuery("SELECT * FROM categorie"); 
            while (resultSet.next()) {
                
                Categorie categorie = new Categorie(); 
                categorie.setId(resultSet.getInt("id_categorie")); 
                categorie.setLibelle(resultSet.getString("libelle_categorie")); 
 
                categories.add(categorie); 
                System.out.println("Categorie : "+categorie.getId() + " || " + categorie.getLibelle());
                
            } 
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
       
        return categories; 
    }

    @Override 
    public void create(Entity entity) throws DaoException { 
        Categorie categorie = (Categorie) entity; 
 
        PreparedStatement statement = null; 
        String sqlReq = "insert into categorie( id_categorie, libelle_categorie) values (?, ?)"; 
        try { 
            statement = connection.prepareStatement(sqlReq); 
 
            statement.setInt(1, categorie.getId()); 
            statement.setString(2, categorie.getLibelle());  

            int res = statement.executeUpdate();
            if (res > 0) System.out.println("Ligne insérée");
        } catch (SQLException e) { 
            throw new DaoException(e); 
        } 
    } 

    /**
     * afficher le chiffre d'affaire de chaque categorie
     * @throws DaoException
     */
    public void getChiffreAffaireParCategorie() throws DaoException{
        PreparedStatement statement = null; 
        
        String sqlReq = "SELECT categorie.id_categorie, categorie.libelle_categorie, SUM(montant) as chiffre_affaire FROM facture "+
        "INNER JOIN contrat ON facture.id_contrat = contrat.id_contrat "+
        "INNER JOIN vehicule ON contrat.immatriculation = vehicule.immatriculation "+
        "INNER JOIN categorie ON vehicule.id_categorie = categorie.id_categorie "+
        "GROUP BY categorie.id_categorie";
        /* Operation effectuee
        1) on recupere la somme de tous les montants de facture 
        2) on lie les factures a un contrat (join)
        3) les contrat sont liees a un vehicule (join)
        4) on lie les vehicules avec les categories (join) 
        5) on additionne pour chaque categorie, les montants des factures en lien
        6) on renvoie le tout
        */
        try { 
            statement = connection.prepareStatement(sqlReq); 
            ResultSet resultSet = statement.executeQuery(); 
 
            while (resultSet.next()) { 
                int id_categorie = resultSet.getInt("id_categorie");
                String libelle_categorie = resultSet.getString("libelle_categorie");
                double chiffre_affaire = resultSet.getDouble("chiffre_affaire");

                System.out.println("Id catégorie : " + id_categorie + " || " +"Nom catégorie : " + libelle_categorie + " || " + 
                "Chiffre d'affaire : " + chiffre_affaire);
            } 
        } catch (SQLException throwables) { 
            throwables.printStackTrace(); 
        } 
    }

    /**
     * Permet de recevoir le nombre de l'id final dans la table categorie, principalement utilise pour inserer des lignes
     * s'utilise en incrementant le retour de cette fonction : getCompteur() + 1 
     * @return int compteur
     * @throws DaoException
     */
    public int getCompteur() throws DaoException {
        int compteur = 0;
        PreparedStatement statement = null; 
        String sqlReq = "SELECT MAX(id_categorie) as compteur FROM categorie"; 
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
