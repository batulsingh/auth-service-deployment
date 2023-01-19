package com.app.dao_impl;

import com.app.dao.NoteDao;
import com.app.model.NoteEntity;
import com.app.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Override
    public NoteEntity updateNote(long noteId, NoteEntity note) {
        Optional<NoteEntity> noteFromDb = noteRepository.findById(noteId);
        if(noteFromDb.isPresent()){
            NoteEntity noteToBeUpdated = noteFromDb.get();
            noteToBeUpdated.setUpdatedOn(note.getUpdatedOn());
            noteToBeUpdated.setContent(note.getContent());
            return noteRepository.save(noteToBeUpdated);
        }
        return note;
    }

    @Override
    public boolean deleteNote(long noteId) {
        boolean isDeleted = false;
        Optional<NoteEntity> noteFromDb = noteRepository.findById(noteId);
        if(noteFromDb.isPresent()){
            noteRepository.deleteById(noteId);
            isDeleted = true;
        }
        return isDeleted;
    }
}
