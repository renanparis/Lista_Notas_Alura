package com.renanparis.ceed.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.renanparis.ceed.R;
import com.renanparis.ceed.model.Note;

import java.util.List;

public class LitsAdapterNotes extends BaseAdapter {

    private final Context context;
    private final List<Note> notas;

    public LitsAdapterNotes(Context context, List<Note> note) {
        this.context = context;
        this.notas = note;
    }

    @Override
    public int getCount() {
        return notas.size();
    }

    @Override
    public Note getItem(int position) {
        return notas.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View viewCreated = LayoutInflater.from(context).inflate(R.layout.item_note, viewGroup, false);
        Note note = notas.get(position);

        TextView title = viewCreated.findViewById(R.id.item_note_title);
        title.setText(note.getTitle());

        TextView description = viewCreated.findViewById(R.id.item_note_description);
        description.setText(note.getDescription());

        return viewCreated;
    }
}
