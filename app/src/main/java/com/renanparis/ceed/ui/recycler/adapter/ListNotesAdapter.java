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

import java.util.List;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.NotesViewHolder> {

    private final List<Note> notes;
    private final Context context;

    public ListNotesAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
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

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView description;

        private NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_note_title);
            description = itemView.findViewById(R.id.item_note_description);
        }

        private void setValuesAdapter(Note note) {

            title.setText(note.getTitle());
            description.setText(note.getDescription());
        }
    }
}
