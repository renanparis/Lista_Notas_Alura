package com.renanparis.ceed.asynctask;

import android.os.AsyncTask;

import com.renanparis.ceed.database.dao.NoteDao;
import com.renanparis.ceed.model.Note;

public class SaveNoteTask extends AsyncTask<Void, Void, Long> {


    private NoteDao dao;
    private Note note;

    public SaveNoteTask(NoteDao dao, Note note) {
        this.dao = dao;
        this.note = note;
    }

    @Override
    protected Long doInBackground(Void... voids) {

        return dao.saveNote(note);
    }

}
