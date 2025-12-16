package com.notes.Notes.service.Impl;

import com.notes.Notes.dto.NotesDto;
import com.notes.Notes.model.Notes;
import com.notes.Notes.repositories.NotesRepositories;
import com.notes.Notes.service.NotesService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class NotesServiceImpl  implements NotesService {

    private final NotesRepositories notesRepositories;

    public NotesServiceImpl(NotesRepositories notesRepositories){
        this.notesRepositories = notesRepositories;
    }


    @Override
    public List<Notes> getAllNotes() {
        return notesRepositories.findAll();
    }

    @Override
    public Notes getNoteById(Long id) {
        return notesRepositories.findById(id).orElseThrow(()-> new RuntimeException("No notes found with id "+id));
    }

    @Override
    public Notes createNotes(Notes notes) {
        return notesRepositories.save(notes);
    }

    @Override
    public Notes updateNotes(Long id, Notes notes) {
        Notes existingNotes = notesRepositories.findById(id).orElseThrow(()-> new RuntimeException("No notes found with id "+id));
        existingNotes.setDescription(notes.getDescription());
        existingNotes.setTitle(notes.getTitle());
        existingNotes.setUpdatedAt(LocalDateTime.now());
        existingNotes.setFavourite(!existingNotes.isFavourite());
        notesRepositories.save(existingNotes);
        return existingNotes;
    }

    @Override
    public String deleteNotes(Long id) {
        Notes existingNotes = notesRepositories.findById(id).orElseThrow(()-> new RuntimeException("No notes found with id "+id));
        notesRepositories.deleteById(id);
        return "Note Deleted Successfully";
    }

    @Override
    public List<Notes> searchNotes(String query) {
        List<Notes> fromTitle = notesRepositories.findByTitleContainingIgnoreCase(query);
        List<Notes> fromDescription = notesRepositories.findByDescriptionContainingIgnoreCase(query);

        Set<Notes> results = new LinkedHashSet<>();
        results.addAll(fromTitle);
        results.addAll(fromDescription);

        return new ArrayList<>(results);
    }
}
