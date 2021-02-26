package com.example.android.planeolnotes3.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.planeolnotes3.EditorActivity;
import com.example.android.planeolnotes3.R;
import com.example.android.planeolnotes3.database.NoteEntity;
import com.example.android.planeolnotes3.utilities.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.planeolnotes3.utilities.Constants.*;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    List<NoteEntity> mList;
    Context mContext;

    public NotesAdapter(List<NoteEntity> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final NoteEntity note = mList.get(position);
            holder.mNoteText.setText(note.getText());

            holder.mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EditorActivity.class);
                    intent.putExtra(NOTE_ID_KEY, note.getId());
                    mContext.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.note_text)
        TextView mNoteText;

        @BindView(R.id.fab)
        FloatingActionButton mFab;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
