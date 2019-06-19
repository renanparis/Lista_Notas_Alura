package com.renanparis.ceed.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.renanparis.ceed.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> allNotes();


    @Insert
    Long saveNote(Note note);
}
