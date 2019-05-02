package com.renanparis.ceed.dao;

import com.renanparis.ceed.model.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NoteDao {

    private final static ArrayList<Note> notes = new ArrayList<>();

    public List<Note> todos() {
        return (List<Note>) notes.clone();
    }

    public void insert(Note... notas) {
        NoteDao.notes.addAll(Arrays.asList(notas));
    }

    public void update(int posicao, Note note) {
        notes.set(posicao, note);
    }

    public void remove(int position) {
        notes.remove(position);
    }

    public void change(int positionHome, int positionEnd) {
        Collections.swap(notes, positionHome, positionEnd);
    }

    public void removeAllNotes() {
        notes.clear();
    }
}
