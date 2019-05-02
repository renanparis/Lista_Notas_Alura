package com.renanparis.ceed.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.renanparis.ceed.R;
import com.renanparis.ceed.dao.NoteDao;
import com.renanparis.ceed.model.Note;
import com.renanparis.ceed.ui.adapter.LitsAdapterNotes;

import java.util.List;

public class ListNotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);

        ListView listNotes = findViewById(R.id.activity_list_notes_listView);

        NoteDao dao = new NoteDao();
        for (int i = 1; i <= 10000; i++) {
            dao.insert(new Note("Titulo" + 1, "Descrição" + i));
        }
        List<Note> list = dao.todos();
        listNotes.setAdapter(new LitsAdapterNotes(this, list));


    }
}
