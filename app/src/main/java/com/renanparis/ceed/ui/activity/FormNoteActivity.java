package com.renanparis.ceed.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.renanparis.ceed.R;
import com.renanparis.ceed.dao.NoteDao;
import com.renanparis.ceed.model.Note;

public class FormNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_note);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_ic_save_note){
            EditText title = findViewById(R.id.form_note_title);
            EditText description = findViewById(R.id.form_note_description);
            Note noteCreated = new Note(title.getText().toString(), description.getText().toString());
            NoteDao dao = new NoteDao();
            dao.insert(noteCreated);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
