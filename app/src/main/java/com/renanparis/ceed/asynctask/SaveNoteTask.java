package com.renanparis.ceed.asynctask;

import android.os.AsyncTask;

import com.renanparis.ceed.database.dao.NoteDao;
import com.renanparis.ceed.model.Note;

public class SaveNoteTask extends AsyncTask<Void, Void, Long> {


    private NoteDao dao;
    private Note note;
    private FinishListener listener;

    public SaveNoteTask(NoteDao dao, Note note, FinishListener listener) {
        this.dao = dao;
        this.note = note;
        this.listener = listener;
    }

    @Override
    protected Long doInBackground(Void... voids) {

        return dao.saveNote(note);
    }


    @Override
    protected void onPostExecute(Long id) {
        super.onPostExecute(id);
        listener.whenItEnds(id);

    }

    public interface FinishListener {

        void whenItEnds(Long id);
    }
}
