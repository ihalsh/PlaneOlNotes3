package com.example.android.planeolnotes3;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.planeolnotes3.database.NoteEntity;
import com.example.android.planeolnotes3.ui.NotesAdapter;
import com.example.android.planeolnotes3.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.arch.lifecycle.ViewModelProviders.of;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    List<NoteEntity> notesData = new ArrayList<>();
    NotesAdapter mNotesAdapter;
    MainViewModel mMainViewModel;


    @OnClick(R.id.fab_main)
    void fabClickHandle() {
        Intent intent = new Intent(MainActivity.this, EditorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {

        final Observer<List<NoteEntity>> observer = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                notesData.clear();
                notesData.addAll(noteEntities);

                if (mNotesAdapter == null) {
                    mNotesAdapter = new NotesAdapter(notesData,
                            MainActivity.this);
                    mRecyclerView.setAdapter(mNotesAdapter);
                } else mNotesAdapter.notifyDataSetChanged();
            }
        };

        mMainViewModel = of(this).get(MainViewModel.class);
        mMainViewModel.mNotes.observe(this, observer);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(decoration);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_sample_data:
                addSampleData();
                return true;

            case R.id.delete_all:
                deleteAllData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addSampleData() {
        mMainViewModel.addSampleData();
    }

    private void deleteAllData() {

        mMainViewModel.deleteAllData();
    }
}
