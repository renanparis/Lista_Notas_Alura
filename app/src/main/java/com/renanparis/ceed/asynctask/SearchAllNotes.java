package com.renanparis.ceed.asynctask;

import android.os.AsyncTask;

import com.renanparis.ceed.database.dao.NoteDao;
import com.renanparis.ceed.model.Note;

import java.util.List;

public class SearchAllNotes extends AsyncTask<Void, Void, List<Note>> {

    private FinishListener listener;


    private NoteDao dao;

    public SearchAllNotes(NoteDao dao, FinishListener listener) {
        this.listener = listener;
        this.dao = dao;
    }

    @Override
    protected List<Note> doInBackground(Void... voids) {

        return dao.searchAllNotes();

    }

    @Override
    protected void onPostExecute(List<Note> notes) {
        super.onPostExecute(notes);
        listener.whenItEnds(notes);

    }

    public interface FinishListener{

        void whenItEnds(List<Note> notes);
    }
}
