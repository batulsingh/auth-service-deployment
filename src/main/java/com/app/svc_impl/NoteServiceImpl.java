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
        noteEntity.setCreatedOn(LocalDate.now());
        noteEntity.setContent(noteDto.getContent());

        NoteEntity savedNote = noteDao.saveNote(noteEntity);

        Note note = new Note();
        note.setContent(savedNote.getContent());
        note.setCreator(userName);
        note.setCreatedOn(savedNote.getCreatedOn());
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
            note.setContent(it.getContent());
            note.setCreator(userRepository.findById(it.getCreatorId()).getUsername());
            note.setCreatedOn(it.getCreatedOn());
            noteList.add(note);
        });

        return noteList;
    }
}
