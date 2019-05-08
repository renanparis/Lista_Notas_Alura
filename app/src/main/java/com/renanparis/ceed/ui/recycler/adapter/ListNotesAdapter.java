package com.renanparis.ceed.ui.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.renanparis.ceed.R;
import com.renanparis.ceed.model.Note;
import com.renanparis.ceed.ui.recycler.adapter.listener.OnItemClickListener;

import java.util.Collections;
import java.util.List;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.NotesViewHolder> {

    private final List<Note> notes;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public ListNotesAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
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
        holder.setValuesAdapter(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void add(Note note) {
        notes.add(note);
        notifyDataSetChanged();

    }

    public void update(int positionRceived, Note noteReceived) {
        notes.set(positionRceived, noteReceived);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        notes.remove(position);
        notifyDataSetChanged();
    }

    public void change(int positionHome, int positionEnd) {
        Collections.swap(notes, positionHome, positionEnd);
        notifyDataSetChanged();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView description;
        private Note note;

        private NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_note_title);
            description = itemView.findViewById(R.id.item_note_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(note, getAdapterPosition());
                }
            });
        }

        private void setValuesAdapter(Note note) {
            this.note = note;
            title.setText(note.getTitle());
            description.setText(note.getDescription());
        }
    }
}
