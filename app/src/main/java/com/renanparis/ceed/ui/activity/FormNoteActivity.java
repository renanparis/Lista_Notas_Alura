package com.renanparis.ceed.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.renanparis.ceed.R;
import com.renanparis.ceed.model.Colors;
import com.renanparis.ceed.model.Note;
import com.renanparis.ceed.ui.recycler.adapter.FormAdapter;

import java.util.List;

import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.KEY_NOTE;

public class FormNoteActivity extends AppCompatActivity {


    public static final String TITLE_APPBAR_INSERT_NOTE = "Insere Nota";
    public static final String TITLE_APPBAR_UPDATE_NOTE = "Altera Nota";
    public static final String KEY_NOTE_COLOR = "noteColor";
    private TextView title;
    private TextView description;
    private List<Integer> listColors;
    private RecyclerView paletteColors;
    private View backgroundForm;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_note);
        startFieldForm();
        retrievesSavedData(savedInstanceState);
        listColors = new Colors().allColors();
        configAdapter();
        loadNote();
    }

    private void retrievesSavedData(Bundle savedInstanceState) {
        if (savedInstanceState != null){
           note = savedInstanceState.getParcelable(KEY_NOTE_COLOR);
        }
    }

    private void loadNote() {
        Intent dataReceived = getIntent();
        if (dataReceived.hasExtra(KEY_NOTE)) {
            setTitle(TITLE_APPBAR_UPDATE_NOTE);
            note = getNote(dataReceived);
            fillFieldForm(note);
        } else {
            setTitle(TITLE_APPBAR_INSERT_NOTE);
            setBackgroundColor(searchWhiteColor());
            note = new Note();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_NOTE_COLOR, note);
    }

    private void setBackgroundColor(Integer color) {

        backgroundForm.setBackgroundColor(color);
    }

    private Note getNote(Intent dataReceived) {
        note = dataReceived.getParcelableExtra(KEY_NOTE);
        return note;
    }

    private void configAdapter() {
        paletteColors = findViewById(R.id.form_list_colors);
        configLayoutManager();
        FormAdapter adapter = new FormAdapter(listColors, this);
        paletteColors.setAdapter(adapter);
        adapter.setOnItemClickListener(new FormAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Integer color) {
                backgroundForm.setBackgroundColor(color);
                note.setColor(color);
            }
        });
    }

    private void configLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        paletteColors.setLayoutManager(layoutManager);
    }

    private void fillFieldForm(Note note) {
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        backgroundForm.setBackgroundColor(note.getColor());
    }

    private void startFieldForm() {
        title = findViewById(R.id.form_note_title);
        description = findViewById(R.id.form_note_description);
        backgroundForm = findViewById(R.id.form_note_background);
        backgroundForm.setBackgroundColor(searchWhiteColor());
    }

    private int searchWhiteColor() {
        Colors color = new Colors();
        return color.getWhite();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isSaveMenu(item)) {
            createNote();
            returnNote(note);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnNote(Note note) {
        Intent intent = new Intent();
        intent.putExtra(KEY_NOTE, note);
        setResult(Activity.RESULT_OK, intent);

    }

    private boolean isSaveMenu(MenuItem item) {
        return item.getItemId() == R.id.menu_ic_save_note;
    }

    private void createNote() {
        note.setTitle(title.getText().toString());
        note.setDescription(description.getText().toString());

    }
}
