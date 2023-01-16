package com.app.repository;


import com.app.model.NoteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<NoteEntity, Long> {
//    @Query(value = "SELECT * FROM notes WHERE creator_id = :creatorId")
    List<NoteEntity> findByCreatorId(long creatorId);
}
