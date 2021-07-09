package com.trotos.notes.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.trotos.notes.R;
import com.trotos.notes.models.Note;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.ViewHolder> {
    private List<Note> notes;
    private Context context;
    final OnItemClickListener listener;

    public AdapterNotes(Context c, List<Note> notes, OnItemClickListener listener) {
        this.context = c;
        this.notes = notes;
        this.listener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        holder.bindData(notes.get(position));
    }

    public interface OnItemClickListener {
        void onItemClick(Note item);
    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView noteCardView;
        TextView noteTitleTextView, noteDescriptionTextView, noteDateTextView;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            noteCardView = itemView.findViewById(R.id.noteCardView);
            noteTitleTextView = itemView.findViewById(R.id.noteTitle);
            noteDescriptionTextView = itemView.findViewById(R.id.noteDescription);
            noteDateTextView = itemView.findViewById(R.id.noteDate);
        }

        public void bindData(Note item) {
            noteTitleTextView.setText(item.getTitle());
            noteDescriptionTextView.setText(item.getContent());
            noteDateTextView.setText(item.getUpdatedAt());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
