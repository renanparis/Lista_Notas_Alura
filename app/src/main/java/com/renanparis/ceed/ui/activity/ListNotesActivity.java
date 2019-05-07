package com.renanparis.ceed.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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

import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.KEY_NOTE;
import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.REQUEST_CODE_INSERT_NOTE;
import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.RESULT_CODE_CREATED_NOTE;

public class ListNotesActivity extends AppCompatActivity {

    private ListNotesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        List<Note> list = configNotes();
        configRecyclerView(list);

        configButtonInsertNote();


    }

    private void configButtonInsertNote() {
        TextView insertNote = findViewById(R.id.list_notes_insert_note);
        insertNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFormActivity();
            }
        });
    }

    private void goToFormActivity() {
        Intent intent = new Intent(ListNotesActivity.this, FormNoteActivity.class);
        startActivityForResult(intent, REQUEST_CODE_INSERT_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (isRequestCodeInsertNote(REQUEST_CODE_INSERT_NOTE) && isResultCodeCreateNote(RESULT_CODE_CREATED_NOTE) && hasNote(data)) {
            Note noteReceived = null;
            if (data != null) {
                noteReceived = data.getParcelableExtra(KEY_NOTE);
            }
            addNote(noteReceived);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean hasNote(@Nullable Intent data) {
        return data.hasExtra(KEY_NOTE);
    }

    private void addNote(Note note) {
        new NoteDao().insert(note);
        adapter.add(note);
    }

    private boolean isResultCodeCreateNote(int resultCode) {
        return resultCode == RESULT_CODE_CREATED_NOTE;
    }

    private boolean isRequestCodeInsertNote(int requestCode) {
        return requestCode == REQUEST_CODE_INSERT_NOTE;
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

    private List<Note> configNotes() {
        NoteDao dao = new NoteDao();

        return dao.allNotes();
    }
}
