package com.renanparis.ceed.asynctask;

import android.os.AsyncTask;

import com.renanparis.ceed.database.dao.NoteDao;
import com.renanparis.ceed.model.Note;

public class RemoveNoteTask extends AsyncTask<Void, Void, Void> {


    private NoteDao dao;
    private Note note;

    public RemoveNoteTask(NoteDao dao, Note note) {
        this.dao = dao;
        this.note = note;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        dao.remove(note);
        return null;
    }
}
