package com.app.dao_impl;

import com.app.dao.NoteDao;
import com.app.model.NoteEntity;
import com.app.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteDaoImpl implements NoteDao {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public NoteEntity saveNote(NoteEntity noteEntity) {
        return noteRepository.save(noteEntity);
    }

    @Override
    public List<NoteEntity> getNotesByCreatorId(long creatorId) {
        return noteRepository.findByCreatorId(creatorId);
    }
}
