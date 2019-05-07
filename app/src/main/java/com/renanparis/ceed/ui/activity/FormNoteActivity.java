package com.renanparis.ceed.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.renanparis.ceed.R;
import com.renanparis.ceed.model.Note;

import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.KEY_NOTE;
import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.RESULT_CODE_CREATED_NOTE;

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
        if (isSaveMenu(item)) {
            Note noteCreated = createNote();
            returnNote(noteCreated);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnNote(Note note) {
        Intent intent = new Intent();
        intent.putExtra(KEY_NOTE, note);
        setResult(RESULT_CODE_CREATED_NOTE, intent);

    }

    private boolean isSaveMenu(MenuItem item) {
        return item.getItemId() == R.id.menu_ic_save_note;
    }

    private Note createNote() {
        EditText title = findViewById(R.id.form_note_title);
        EditText description = findViewById(R.id.form_note_description);
        return new Note(title.getText().toString(), description.getText().toString());
    }
}
