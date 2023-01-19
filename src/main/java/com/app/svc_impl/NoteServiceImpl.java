package com.app.svc_impl;

import com.app.config.JwtTokenUtil;
import com.app.dao.NoteDao;
import com.app.model.Note;
import com.app.model.NoteDTO;
import com.app.model.NoteEntity;
import com.app.repository.UserRepository;
import com.app.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteDao noteDao;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Note createNote(NoteDTO noteDto) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = userRepository.findByUsername(userName).getId();
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setCreatorId(userId);
        noteEntity.setContent(noteDto.getContent());
        noteEntity.setCreatedOn(LocalDate.now());
        noteEntity.setUpdatedOn(LocalDate.now());

        NoteEntity savedNote = noteDao.saveNote(noteEntity);

        Note note = new Note();
        note.setId(savedNote.getId());
        note.setContent(savedNote.getContent());
        note.setCreator(userName);
        note.setCreatedOn(savedNote.getCreatedOn());
        note.setUpdatedOn(savedNote.getUpdatedOn());
        return note;
    }

    @Override
    public List<Note> getNotes() {
        Authentication sc = SecurityContextHolder.getContext().getAuthentication();
        long creatorId = userRepository.findByUsername(sc.getName()).getId();
        List<NoteEntity> noteEntities = noteDao.getNotesByCreatorId(creatorId);

        List<Note> noteList = new ArrayList<>();

        noteEntities.forEach(it -> {
            Note note = new Note();
            note.setId(it.getId());
            note.setContent(it.getContent());
            note.setCreator(userRepository.findById(it.getCreatorId()).getUsername());
            note.setCreatedOn(it.getCreatedOn());
            note.setUpdatedOn(it.getUpdatedOn());
            noteList.add(note);
        });

        noteList.sort((a,b) -> b.getCreatedOn().compareTo(a.getCreatedOn()));

        return noteList;
    }

    @Override
    public Note updateNote(long noteId, NoteDTO noteDto) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = userRepository.findByUsername(userName).getId();
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setId(noteId);
        noteEntity.setCreatorId(userId);
        noteEntity.setContent(noteDto.getContent());
        noteEntity.setUpdatedOn(LocalDate.now());

        NoteEntity savedNote = noteDao.updateNote(noteId, noteEntity);

        Note note = new Note();
        note.setId(savedNote.getId());
        note.setContent(savedNote.getContent());
        note.setCreator(userName);
        note.setCreatedOn(savedNote.getCreatedOn());
        note.setUpdatedOn(savedNote.getUpdatedOn());
        return note;
    }

    @Override
    public boolean deleteNote(long noteId) {
        return noteDao.deleteNote(noteId);
    }
}
