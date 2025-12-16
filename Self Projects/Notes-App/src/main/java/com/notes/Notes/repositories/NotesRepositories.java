package com.notes.Notes.repositories;

import com.notes.Notes.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepositories extends JpaRepository<Notes,Long> {
    List<Notes> findByTitleContainingIgnoreCase(String title);
    List<Notes> findByDescriptionContainingIgnoreCase(String description);

}
