package com.renanparis.ceed.ui.recycler.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.renanparis.ceed.dao.FNote;
import com.renanparis.ceed.ui.recycler.adapter.ListNotesAdapter;

public class NoteItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ListNotesAdapter adapter;

    public NoteItemTouchHelperCallback(ListNotesAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        int slideNote = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int dragsNote = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
                | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragsNote,slideNote);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        FNote dao = new FNote();
        int positionHome = viewHolder.getAdapterPosition();
        int positionEnd= target.getAdapterPosition();
        changeNote(dao, positionHome, positionEnd);
        return true;
    }

    private void changeNote(FNote dao, int positionHome, int positionEnd) {
        dao.change(positionHome, positionEnd);
        adapter.change(positionHome, positionEnd);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        removeNote(position);

    }

    private void removeNote(int position) {
        FNote dao = new FNote();
        dao.remove(position);
        adapter.remove(position);
    }
}
