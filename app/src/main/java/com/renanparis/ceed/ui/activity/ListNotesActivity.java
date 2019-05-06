package com.renanparis.ceed.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        TextView insertNote = findViewById(R.id.list_notes_insert_note);
        insertNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListNotesActivity.this, FormNoteActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        NoteDao dao = new NoteDao();
        List<Note> list = dao.allNotes();
        configRecyclerView(list);
        super.onResume();
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
