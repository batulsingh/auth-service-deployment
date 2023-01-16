package com.app.dao;

import com.app.model.NoteEntity;

import java.util.List;

public interface NoteDao {
    NoteEntity saveNote(NoteEntity noteEntity);

    List<NoteEntity> getNotesByCreatorId(long creatorId);
}
