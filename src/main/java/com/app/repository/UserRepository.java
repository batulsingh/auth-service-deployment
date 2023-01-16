package com.app.repository;


import com.app.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/*DAO stands for Data Access Object. DAO Design Pattern is used to separate the data persistence logic in a separate layer.
This way, the service remains completely in dark about how the low-level operations to access the database is done.
This is known as the principle of Separation of Logic.*/

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    UserEntity findById(long id);
}
