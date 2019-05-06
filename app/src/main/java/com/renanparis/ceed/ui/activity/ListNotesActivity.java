package com.renanparis.ceed.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.renanparis.ceed.R;
import com.renanparis.ceed.dao.NoteDao;
import com.renanparis.ceed.model.Note;
import com.renanparis.ceed.ui.recycler.adapter.ListNotesAdapter;

import java.util.List;

public class ListNotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        List<Note> list = configNotesExample();
        configRecyclerView(list);


    }

    private void configRecyclerView(List<Note> list) {
        RecyclerView listNotes = findViewById(R.id.activity_list_notes_recyclerview);
        configAdapter(list, listNotes);
        configLayoutManager(listNotes);

    }

    private void configLayoutManager(RecyclerView listNotes) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listNotes.setLayoutManager(layoutManager);
    }

    private void configAdapter(List<Note> list, RecyclerView listNotes) {
        listNotes.setAdapter(new ListNotesAdapter(this, list));
    }

    private List<Note> configNotesExample() {
        NoteDao dao = new NoteDao();
        dao.insert(new Note("Primeiro Título", "Primeira Descrição Pequena"),
                new Note("Segundo Título", "Segunda Descrição muito maior que a primeira"));
        return dao.allNotes();
    }
}
