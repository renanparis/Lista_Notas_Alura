package com.renanparis.ceed.ui.recycler.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.renanparis.ceed.R;

import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.FormViewHolder> {

    private List<Integer> colors;
    private Context context;

    public FormAdapter(List<Integer> colors, Context context) {
        this.colors = colors;
        this.context = context;
    }


    @NonNull
    @Override
    public FormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewCreated = LayoutInflater.from(context).inflate(R.layout.item_form_list_colors, parent, false);

        return new FormViewHolder(viewCreated);
    }

    @Override
    public void onBindViewHolder(@NonNull FormViewHolder holder, int position) {

        Integer color = colors.get(position);
        holder.setColors(color);

    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    class FormViewHolder extends RecyclerView.ViewHolder{
        private View palette;
        private Drawable paletteColor;


        public FormViewHolder(@NonNull View itemView) {
            super(itemView);
            palette = itemView.findViewById(R.id.form_item_color);
            paletteColor = palette.getBackground();
        }

        public void setColors(Integer color) {

            paletteColor.setColorFilter(color, PorterDuff.Mode.DARKEN);
        }
    }
}
