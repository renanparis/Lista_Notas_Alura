package com.renanparis.ceed.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.renanparis.ceed.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    Long saveNote(Note note);

    @Query("SELECT * FROM note ORDER BY id DESC")
    List<Note> searchAllNotes();
}
