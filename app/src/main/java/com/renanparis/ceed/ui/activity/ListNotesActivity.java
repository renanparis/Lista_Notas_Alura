package com.renanparis.ceed.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.renanparis.ceed.R;
import com.renanparis.ceed.dao.NoteDao;
import com.renanparis.ceed.model.Note;
import com.renanparis.ceed.ui.recycler.adapter.ListNotesAdapter;

import java.util.List;

public class ListNotesActivity extends AppCompatActivity {

    private ListNotesAdapter adapter;
    private List<Note> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        list = configNotesExample();
        configRecyclerView(list);

        TextView insertNote = findViewById(R.id.list_notes_insert_note);
        insertNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListNotesActivity.this, FormNoteActivity.class);
                startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == 1 && resultCode == 2 && data.hasExtra("note")){
            Note noteReceived = data.getParcelableExtra("note");
            new NoteDao().insert(noteReceived);
            adapter.add(noteReceived);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
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
        adapter = new ListNotesAdapter(this, list);
        listNotes.setAdapter(adapter);
    }

    private List<Note> configNotesExample() {
        NoteDao dao = new NoteDao();
        dao.insert(new Note("Primeiro Título", "Primeira Descrição Pequena"),
                new Note("Segundo Título", "Segunda Descrição muito maior que a primeira"));
        return dao.allNotes();
    }
}
