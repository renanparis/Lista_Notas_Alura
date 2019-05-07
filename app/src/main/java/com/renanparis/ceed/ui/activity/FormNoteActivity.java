package com.renanparis.ceed.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.renanparis.ceed.R;
import com.renanparis.ceed.model.Note;

import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.KEY_NOTE;
import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.KEY_POSITION;
import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.POSITION_INVALID;

public class FormNoteActivity extends AppCompatActivity {



    private int positionReceived = -POSITION_INVALID;
    private TextView title;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_note);
        startFieldForm();


        Intent dataReceived = getIntent();
        if (dataReceived.hasExtra(KEY_NOTE)){
            Note noteReceived = dataReceived.getParcelableExtra(KEY_NOTE);
            positionReceived = dataReceived.getIntExtra(KEY_POSITION, POSITION_INVALID);
            fillFieldForm(noteReceived);
        }
    }

    private void fillFieldForm(Note noteReceived) {
        title.setText(noteReceived.getTitle());
        description.setText(noteReceived.getDescription());
    }

    private void startFieldForm() {
        title = findViewById(R.id.form_note_title);
        description = findViewById(R.id.form_note_description);
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
        intent.putExtra(KEY_POSITION, positionReceived);
        setResult(Activity.RESULT_OK, intent);

    }

    private boolean isSaveMenu(MenuItem item) {
        return item.getItemId() == R.id.menu_ic_save_note;
    }

    private Note createNote() {

        return new Note(title.getText().toString(), description.getText().toString());
    }
}
