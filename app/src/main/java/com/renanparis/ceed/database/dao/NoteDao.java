package com.renanparis.ceed.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.renanparis.ceed.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    Long saveNote(Note note);

    @Query("SELECT * FROM note ORDER BY position")
    List<Note> searchAllNotes();

    @Update
    void updateNote(Note note);
}
