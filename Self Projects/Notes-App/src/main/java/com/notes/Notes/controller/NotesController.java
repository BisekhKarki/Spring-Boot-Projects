package com.notes.Notes.controller;


import com.notes.Notes.error.ErrorMessage;
import com.notes.Notes.model.Notes;
import com.notes.Notes.repositories.NotesRepositories;
import com.notes.Notes.service.NotesService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
public class NotesController {


    private final NotesRepositories notesRepositories;
    private final NotesService notesService;

    public NotesController(NotesRepositories notesRepositories, NotesService notesService){
        this.notesRepositories = notesRepositories;
        this.notesService = notesService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllNotes(){
        List<Notes> notes = notesService.getAllNotes();
        if(notes.isEmpty()){
            return  ResponseEntity.status(400).body(new ErrorMessage(400,"No notes available"));
        }

        return  ResponseEntity.status(200).body(new ErrorMessage(200,notes));

    };

    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable Long id){
        Notes existingNote = notesRepositories.findById(id).orElse(null);
        if(existingNote == null){
            return  ResponseEntity.status(400).body(new ErrorMessage(400,"No note found with id "+ id));

        }
        Notes note = notesService.getNoteById(id);
        return  ResponseEntity.status(200).body(new ErrorMessage(200,note));

    };


    @GetMapping("/search")
    public ResponseEntity<?> searchNotes(@RequestParam(required = false) String query){
        if(query == null || query.isEmpty()){
            List<Notes> notes = notesService.getAllNotes();
            return ResponseEntity.status(200).body(new ErrorMessage(200,notes));
        }

        List<Notes> searchedNotes = notesService.searchNotes(query);
        if(searchedNotes.isEmpty()){
            return ResponseEntity.status(200).body(new ErrorMessage(200,"No  notes found"));
        }

        return ResponseEntity.status(200).body(new ErrorMessage(200,searchedNotes));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNotes( @RequestBody Notes notes){
        Notes newNote = new Notes();
        newNote.setDescription(notes.getDescription());
        newNote.setTitle(notes.getTitle());
        newNote.setFavourite(false);
        newNote.setCreatedAt(LocalDateTime.now());
        newNote.setUpdatedAt(LocalDateTime.now());

        Notes savedNote = notesService.createNotes(newNote);
        if(savedNote == null){
            return  ResponseEntity.status(400).body(new ErrorMessage(400,"Failed to save note"));

        }
        return  ResponseEntity.status(201).body(new ErrorMessage(201,savedNote));

    };

    @PatchMapping("/{id}/update")
    public ResponseEntity<?> updateNotes( @PathVariable Long id, @RequestBody Notes notes){
        Notes updatedNote = notesService.updateNotes(id,notes);

        if(updatedNote == null){
            return  ResponseEntity.status(400).body(new ErrorMessage(400,"Failed to save note"));

        }

        Map<String,Object> response = new HashMap<>();
        response.put("message", "Notes updated Successfully");
        response.put("data", updatedNote);

        return  ResponseEntity.status(200).body(new ErrorMessage(200,response));



    };

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteNotes( @PathVariable Long id){

        String deletedNotes = notesService.deleteNotes(id);
        if( deletedNotes == null || deletedNotes.isEmpty() ){
            return ResponseEntity.status(400).body(new ErrorMessage(400,"Failed to delete note"));
        }

        return ResponseEntity.status(400).body(new ErrorMessage(400,deletedNotes));
    };


}
