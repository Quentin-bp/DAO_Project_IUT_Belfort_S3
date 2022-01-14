package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Models.*;

public class VehiculeDaoImpl extends JdbcDao {

    private MarqueDaoImpl marqueDao;
    private ModeleDaoImpl modeleDao;
    private CategorieDaoImpl categorieDao;
    private TypeDaoImpl typeDao;
    private AgenceDaoImpl agenceDao;

    public VehiculeDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Vehicule vehicule = null;

        marqueDao = new MarqueDaoImpl(connection);
        modeleDao = new ModeleDaoImpl(connection);
        categorieDao = new CategorieDaoImpl(connection);
        typeDao = new TypeDaoImpl(connection);
        agenceDao = new AgenceDaoImpl(connection);

        String sqlReq = "SELECT * FROM vehicule WHERE immatriculation = ?";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlReq);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                vehicule = new Vehicule();
                vehicule.setImmatriculation(resultSet.getInt("immatriculation"));
                vehicule.setDate_circulation(resultSet.getDate("date_circulation"));
                vehicule.setEtat(resultSet.getString("etat"));
                vehicule.setNb_km(resultSet.getInt("nb_km"));
                vehicule.setPrix_location_jour(resultSet.getDouble("prix_par_jour_de_location"));

                Marque marque = (Marque) marqueDao.findById(resultSet.getInt("id_marque"));
                Modele modele = (Modele) modeleDao.findById(resultSet.getInt("id_modele"));
                Categorie categorie = (Categorie) categorieDao.findById(resultSet.getInt("id_categorie"));
                Type type = (Type) typeDao.findById(resultSet.getInt("id_type"));
                Agence agence = (Agence) agenceDao.findById(resultSet.getInt("id_agence"));

                System.out.println("Vehicule : " + vehicule.getImmatriculation() + " || "
                        + vehicule.getDate_circulation() + " || " +
                        vehicule.getEtat() + " || " + vehicule.getNb_km() + " || " + vehicule.getPrix_location_jour()
                        + " || " +
                        marque.getId() + " || " + modele.getId() + " || " + categorie.getId() + " || " + type.getId()
                        + " || " + agence.getId());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return vehicule;
    }

    @Override
    public void update(Entity entity) throws DaoException {
        Vehicule vehicule = (Vehicule) entity;

        PreparedStatement statement = null;

        String sqlReq = "update vehicule set date_circulation = ?, etat = ?, nb_km = ?, prix_par_jour_de_location = ? where immatriculation = ?";
        try {
            statement = connection.prepareStatement(sqlReq);

            statement.setDate(1, vehicule.getDate_circulation());
            statement.setString(2, vehicule.getEtat());
            statement.setDouble(3, vehicule.getNb_km());
            statement.setDouble(4, vehicule.getPrix_location_jour());
            statement.setInt(5, vehicule.getImmatriculation());

            int res = statement.executeUpdate();
            if (res > 0)
                System.out.println("Ligne modifiée");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Entity entity) throws DaoException {
        Vehicule vehicule = (Vehicule) entity;
        PreparedStatement statement = null;

        String sqlReq = "delete from vehicule where immatriculation = ?";
        try {
            statement = connection.prepareStatement(sqlReq);
            statement.setInt(1, vehicule.getImmatriculation());

            int res = statement.executeUpdate();
            if (res > 0)
                System.out.println("Ligne supprimée");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> vehicules = new ArrayList<>();

        marqueDao = new MarqueDaoImpl(connection);
        modeleDao = new ModeleDaoImpl(connection);
        categorieDao = new CategorieDaoImpl(connection);
        typeDao = new TypeDaoImpl(connection);
        agenceDao = new AgenceDaoImpl(connection);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM vehicule");
            while (resultSet.next()) {

                Vehicule vehicule = new Vehicule();
                vehicule.setImmatriculation(resultSet.getInt("immatriculation"));
                vehicule.setDate_circulation(resultSet.getDate("date_circulation"));
                vehicule.setEtat(resultSet.getString("etat"));
                vehicule.setNb_km(resultSet.getDouble("nb_km"));
                vehicule.setPrix_location_jour(resultSet.getDouble("prix_par_jour_de_location"));

                Marque marque = (Marque) marqueDao.findById(resultSet.getInt("id_marque"));
                Modele modele = (Modele) modeleDao.findById(resultSet.getInt("id_modele"));
                Categorie categorie = (Categorie) categorieDao.findById(resultSet.getInt("id_categorie"));
                Type type = (Type) typeDao.findById(resultSet.getInt("id_type"));
                Agence agence = (Agence) agenceDao.findById(resultSet.getInt("id_agence"));

                vehicules.add(vehicule);
                System.out.println("Vehicule : " + vehicule.getImmatriculation() + " || "
                        + vehicule.getDate_circulation() + " || " +
                        vehicule.getEtat() + " || " + vehicule.getNb_km() + " || " + vehicule.getPrix_location_jour()
                        + " || " +
                        marque.getId() + " || " + modele.getId() + " || " + categorie.getId() + " || " + type.getId()
                        + " || " + agence.getId());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return vehicules;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Vehicule vehicule = (Vehicule) entity;

        PreparedStatement statement = null;
        String sqlReq = "insert into vehicule(immatriculation, date_circulation,etat, nb_km,prix_par_jour_de_location,id_marque,id_modele,id_categorie,id_type,id_agence) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            statement = connection.prepareStatement(sqlReq);

            statement.setInt(1, vehicule.getImmatriculation());
            statement.setDate(2, vehicule.getDate_circulation());
            statement.setString(3, vehicule.getEtat());
            statement.setDouble(4, vehicule.getNb_km());
            statement.setDouble(5, vehicule.getPrix_location_jour());

            statement.setInt(6, vehicule.getMarque().getId());
            statement.setInt(7, vehicule.getModele().getId());
            statement.setInt(8, vehicule.getCategorie().getId());
            statement.setInt(9, vehicule.getType().getId());
            statement.setInt(10, vehicule.getAgence().getId());

            int res = statement.executeUpdate();
            if (res > 0)
                System.out.println("Ligne insérée");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void getNbVehiculeParAgencePlus2ansEt150kKm() {
        PreparedStatement statement = null;

        String sqlReq = "SELECT id_agence, COUNT(*) as nb_vehicule FROM vehicule "+ 
        "WHERE EXTRACT (YEAR FROM NOW())  - EXTRACT(YEAR FROM vehicule.date_circulation) > 2 AND nb_km > 150000 GROUP BY id_agence";
        /*
        Operation effectuee : 
        1) On affiche l'id agence et on compte le nbr de vehicule 
        2) Uniquement la ou l'annee actuelle MOINS l'annee de mise en circulation du vehicule est au dessus de 2
        3) et la ou le nbr de km du vehicule est au dessus de 150 000
        4) on regroupe le tout par les id_agence
        */
        try {
            statement = connection.prepareStatement(sqlReq);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id_agence = resultSet.getInt("id_agence");
                int nb_vehicule = resultSet.getInt("nb_vehicule");

                System.out.println("Id agence : " + id_agence + " || " + "Nombre de véhicule : " + nb_vehicule);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
