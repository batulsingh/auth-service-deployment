package com.app.service;

import com.app.model.Note;
import com.app.model.NoteDTO;

import java.time.LocalDate;
import java.util.List;


public interface NoteService {
    Note createNote(NoteDTO noteDTO);

    List<Note> getNotes();
}
