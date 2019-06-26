package com.renanparis.ceed.ui.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.renanparis.ceed.R;
import com.renanparis.ceed.asynctask.RemoveNoteTask;
import com.renanparis.ceed.asynctask.SavePositionTask;
import com.renanparis.ceed.database.dao.NoteDao;
import com.renanparis.ceed.model.Note;

import java.util.Collections;
import java.util.List;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.NotesViewHolder> {

    private final List<Note> notes;
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private NoteDao dao;

    public ListNotesAdapter(Context context, List<Note> notes, NoteDao dao) {
        this.context = context;
        this.notes = notes;
        this.dao = dao;
        setHasStableIds(true);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListNotesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCreated = LayoutInflater.from(context)
                .inflate(R.layout.item_note, parent, false);

        return new NotesViewHolder(viewCreated);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNotesAdapter.NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        Integer color = note.getColor();
        holder.setValuesAdapter(note);
        holder.setBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public long getItemId(int position) {

        return notes.get(position).getId();
    }


    private void updateNotePosition() {

        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getPosition() != i) {
                notes.get(i).setPosition(i);
                new SavePositionTask(dao, notes.get(i));

            }
        }
    }

    public void remove(int position) {
        Note note = notes.get(position);
        new RemoveNoteTask(dao, note).execute();
        notes.remove(position);
        notifyItemRangeRemoved(position, getItemCount());
        updateNotePosition();
    }


    public void change(int positionHome, int positionEnd) {
        Collections.swap(notes, positionHome, positionEnd);
        notifyDataSetChanged();
        notifyItemMoved(positionHome, positionEnd);
        changePositionDao(positionHome, positionEnd);


    }

    private void changePositionDao(int positionHome, int positionEnd) {
        Note noteHomeDao = notes.get(positionHome);
        Note noteEndDao = notes.get(positionEnd);
        noteHomeDao.setPosition(noteEndDao.getPosition());
        noteEndDao.setPosition(noteHomeDao.getPosition());
        new SavePositionTask(dao, noteHomeDao).execute();
        new SavePositionTask(dao, noteEndDao).execute();
    }

    public void addNote(Note note) {
        notes.add(0, note);
        notifyItemInserted(0);
        updateNotePosition();
    }


    public void updateNote(Note note) {
        notes.set(note.getPosition(), note);
        notifyItemChanged(note.getPosition(), note);
    }


    class NotesViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView description;
        private Note note;
        private View backgroundItem;

        private NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_note_title);
            description = itemView.findViewById(R.id.item_note_description);
            backgroundItem = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(note);
                }
            });
        }

        private void setValuesAdapter(Note note) {
            this.note = note;
            title.setText(note.getTitle());
            description.setText(note.getDescription());
        }

        private void setBackgroundColor(Integer color) {
            backgroundItem.setBackgroundColor(color);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(Note note);
    }

}
