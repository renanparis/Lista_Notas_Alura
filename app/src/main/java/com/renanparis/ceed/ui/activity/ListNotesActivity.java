package com.renanparis.ceed.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.renanparis.ceed.R;
import com.renanparis.ceed.asynctask.SaveNoteTask;
import com.renanparis.ceed.asynctask.SearchAllNotes;
import com.renanparis.ceed.asynctask.UpdateNoteTask;
import com.renanparis.ceed.database.ListNotesDatabase;
import com.renanparis.ceed.database.dao.NoteDao;
import com.renanparis.ceed.model.Note;
import com.renanparis.ceed.ui.activity.preferences.NotesPreferences;
import com.renanparis.ceed.ui.recycler.adapter.ListNotesAdapter;
import com.renanparis.ceed.ui.recycler.helper.callback.NoteItemTouchHelperCallback;

import java.util.List;

import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.KEY_NOTE;
import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.REQUEST_CODE_INSERT_NOTE;
import static com.renanparis.ceed.ui.activity.ConstantsActivityNotes.REQUEST_CODE_UPDATE_NOTE;

public class ListNotesActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Notas";
    public static final int GRID_SPAN_COUNT = 2;
    public static final int GRID_ORIENTATION = 1;
    private ListNotesAdapter adapter;
    private RecyclerView listNotes;
    private NotesPreferences preferences;
    private NoteDao dao;
    private Note note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        setTitle(TITLE_APPBAR);
        ListNotesDatabase db = ListNotesDatabase.getInstance(this);
        dao = db.getNoteDao();
        preferences = new NotesPreferences(this);
        configList();
        configButtonInsertNote();
    }

    private void configList() {
        new SearchAllNotes(dao, this::configRecyclerView).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_options_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.list_linear_layout:
                configLinearLayoutManager();
                preferences.setLinearMode(true);
                invalidateOptionsMenu();
                break;

            case R.id.list_grid_layout:
                configStaggeredGridManager();
                preferences.setLinearMode(false);
                invalidateOptionsMenu();
                break;

            case R.id.ic_feedback_activity:
                goToFeedback();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToFeedback() {
        Intent goToFeedback = new Intent(this, FeedBackActivity.class);
        startActivity(goToFeedback);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (preferences.isLinearMode()) {
            setGridVisible(menu);
        } else {
            setLinearVisible(menu);

        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void setLinearVisible(Menu menu) {
        menu.findItem(R.id.list_linear_layout).setVisible(true);
        menu.findItem(R.id.list_grid_layout).setVisible(false);
    }

    private void setGridVisible(Menu menu) {
        menu.findItem(R.id.list_linear_layout).setVisible(false);
        menu.findItem(R.id.list_grid_layout).setVisible(true);
    }

    private void configStaggeredGridManager() {
        StaggeredGridLayoutManager grid = new StaggeredGridLayoutManager(GRID_SPAN_COUNT, GRID_ORIENTATION);
        listNotes.setLayoutManager(grid);
    }

    private void configButtonInsertNote() {
        TextView insertNote = findViewById(R.id.list_notes_insert_note);
        insertNote.setOnClickListener(v -> goToInsertNoteFormActivity());
    }

    private void goToInsertNoteFormActivity() {
        Intent intent = new Intent(ListNotesActivity.this, FormNoteActivity.class);
        startActivityForResult(intent, REQUEST_CODE_INSERT_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (isRequestCodeInsertNote(requestCode)) {
            if (isResultOk(resultCode) && hasNote(data)) {

                if (data != null) {
                    note = data.getParcelableExtra(KEY_NOTE);
                }
              new SaveNoteTask(dao, note, id -> {
                  note.setId(id);
                  adapter.addNote(note);
                  Toast.makeText(ListNotesActivity.this,
                          "Nota criada com sucesso", Toast.LENGTH_SHORT).show();
              }).execute();
            }

        }

        if (isaRequestCodeUpdateNote(requestCode)) {
            if (isResultOk(resultCode) && hasNote(data)) {
                if (data != null) {
                    note = data.getParcelableExtra(KEY_NOTE);
                }
                    new UpdateNoteTask(dao, note).execute();
                    adapter.updateNote(note);
                    Toast.makeText(this, "Nota alterada com sucesso", Toast.LENGTH_SHORT).show();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isaRequestCodeUpdateNote(int requestCode) {
        return requestCode == REQUEST_CODE_UPDATE_NOTE;
    }

    private boolean hasNote(@Nullable Intent data) {
        return data.hasExtra(KEY_NOTE);
    }


    private boolean isResultOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean isRequestCodeInsertNote(int requestCode) {
        return requestCode == REQUEST_CODE_INSERT_NOTE;
    }

    private void configRecyclerView(List<Note> list) {
        listNotes = findViewById(R.id.activity_list_notes_recyclerview);
        configAdapter(list, listNotes);
        configPreferencesLayoutMode();
        configItemTouchHelper(listNotes);
    }

    private void configPreferencesLayoutMode() {
        if (preferences.isLinearMode()) {

            configLinearLayoutManager();

        } else {
            configStaggeredGridManager();
        }
    }

    private void configItemTouchHelper(RecyclerView listNotes) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NoteItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listNotes);
    }

    private void configLinearLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listNotes.setLayoutManager(layoutManager);
    }

    private void configAdapter(List<Note> list, RecyclerView listNotes) {
        adapter = new ListNotesAdapter(this, list, dao);
        listNotes.setAdapter(adapter);
        adapter.setOnItemClickListener(this::goToUpdateNoteFormActivity);
    }

    private void goToUpdateNoteFormActivity(Note note) {
        Intent sendDataToForm = new Intent(ListNotesActivity.this, FormNoteActivity.class);
        sendDataToForm.putExtra(KEY_NOTE, note);
        startActivityForResult(sendDataToForm, REQUEST_CODE_UPDATE_NOTE);
    }

}
