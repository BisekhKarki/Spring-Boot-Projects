package com.notes.Notes.service;

import com.notes.Notes.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface NotesService {

    List<Notes> getAllNotes();
    Notes getNoteById(Long id);
    Notes createNotes(Notes notes);
    Notes updateNotes(Long id, Notes notes);
    String deleteNotes(Long id);

    List<Notes> searchNotes(String query);

}
