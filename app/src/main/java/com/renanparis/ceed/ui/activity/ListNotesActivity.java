package com.renanparis.ceed.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.renanparis.ceed.R;
import com.renanparis.ceed.dao.NoteDao;
import com.renanparis.ceed.model.Note;
import com.renanparis.ceed.ui.recycler.adapter.ListNotesAdapter;
import com.renanparis.ceed.ui.recycler.adapter.listener.OnItemClickListener;

import java.util.List;

import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.KEY_NOTE;
import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.KEY_POSITION;
import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.POSITION_INVALID;
import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.REQUEST_CODE_INSERT_NOTE;
import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.REQUEST_CODE_UPDATE_NOTE;

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
                goToInsertNoteFormActivity();
            }
        });
    }

    private void goToInsertNoteFormActivity() {
        Intent intent = new Intent(ListNotesActivity.this, FormNoteActivity.class);
        startActivityForResult(intent, REQUEST_CODE_INSERT_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (isRequestCodeInsertNote(requestCode)) {
            if (isResultOk(resultCode) && hasNote(data)) {
                Note noteReceived = null;
                if (data != null) {
                    noteReceived = data.getParcelableExtra(KEY_NOTE);
                }
                addNote(noteReceived);
            }
        }

        if (isaRequestCodeUpdateNote(requestCode)) {
            if (isResultOk(resultCode) && hasNote(data)) {
                Note noteReceived = data.getParcelableExtra(KEY_NOTE);
                int positionReceived = data.getIntExtra(KEY_POSITION, POSITION_INVALID);
                if (positionReceived > POSITION_INVALID) {
                    updateNote(noteReceived, positionReceived);
                    Toast.makeText(this, "Nota alterada com sucesso", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this,
                            "Ocorreu um problema, informar o suporte o ocorrido", Toast.LENGTH_SHORT).show();

                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateNote(Note noteReceived, int positionReceived) {
        new NoteDao().update(positionReceived, noteReceived);
        adapter.update(positionReceived, noteReceived);
    }

    private boolean isaRequestCodeUpdateNote(int requestCode) {
        return requestCode == REQUEST_CODE_UPDATE_NOTE;
    }

    private boolean hasNote(@Nullable Intent data) {
        return data.hasExtra(KEY_NOTE);
    }

    private void addNote(Note note) {
        new NoteDao().insert(note);
        adapter.add(note);
    }

    private boolean isResultOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean isRequestCodeInsertNote(int requestCode) {
        return requestCode == REQUEST_CODE_INSERT_NOTE;
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
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Note note, int position) {
                goToUpdateNoteFormActivity(note, position);
            }
        });
    }

    private void goToUpdateNoteFormActivity(Note note, int position) {
        Intent sendDataToForm = new Intent(ListNotesActivity.this, FormNoteActivity.class);
        sendDataToForm.putExtra(KEY_NOTE, note);
        sendDataToForm.putExtra(KEY_POSITION, position);
        startActivityForResult(sendDataToForm, REQUEST_CODE_UPDATE_NOTE);
    }

    private List<Note> configNotes() {
        NoteDao dao = new NoteDao();
        for (int i = 1; i <= 10; i++) {
            dao.insert(new Note("Titulo " + i, "Descrição " + i));
        }
        return dao.allNotes();
    }
}
