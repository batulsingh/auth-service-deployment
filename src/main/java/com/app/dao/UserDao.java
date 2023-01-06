package com.app.dao;


import com.app.model.DAOUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/*DAO stands for Data Access Object. DAO Design Pattern is used to separate the data persistence logic in a separate layer.
This way, the service remains completely in dark about how the low-level operations to access the database is done.
This is known as the principle of Separation of Logic.*/

@Repository
public interface UserDao extends CrudRepository<DAOUser, Integer> {
    DAOUser findByUsername(String username);
}
