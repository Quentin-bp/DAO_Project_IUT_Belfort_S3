package DAO;

import java.util.Collection;

import Models.Entity;

public interface Dao { 
    Collection<Entity> findAll() throws DaoException; 
    Entity findById(int id) throws DaoException; 
    void create(Entity entity) throws DaoException; 
    void update(Entity entity) throws DaoException; 
    void delete(Entity entity) throws DaoException; 
}