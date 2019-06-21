package com.renanparis.ceed.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.renanparis.ceed.database.dao.NoteDao;
import com.renanparis.ceed.model.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class ListNotesDatabase extends RoomDatabase {

    private static final String NAME_DB = "ceep.db";
    private static ListNotesDatabase instance;

    public abstract NoteDao getNoteDao();

    public static ListNotesDatabase getInstance(Context context){
        if (instance == null){

            instance = Room.databaseBuilder(context, ListNotesDatabase.class, NAME_DB).build();
        }

        return instance;
    }
}
