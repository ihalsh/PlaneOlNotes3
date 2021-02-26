package com.example.android.planeolnotes3;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.planeolnotes3.database.NoteEntity;
import com.example.android.planeolnotes3.viewmodel.EditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.planeolnotes3.utilities.Constants.EDITING_KEY;
import static com.example.android.planeolnotes3.utilities.Constants.NOTE_ID_KEY;

public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.note_text)
    TextView mTextView;

    private EditorViewModel mViewModel;

    private boolean amIediting;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        amIediting = true;
        outState.putBoolean(EDITING_KEY, amIediting);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            amIediting = savedInstanceState.getBoolean(EDITING_KEY);
        }

        initViewModel();

    }

    private void initViewModel() {

        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

        mViewModel.mMutableLiveData.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(@Nullable NoteEntity noteEntity) {
                if (noteEntity != null && !amIediting) {
                    mTextView.setText(noteEntity.getText());
                }
            }
        });

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            setTitle(getString(R.string.new_note));
            mViewModel.newNote = true;
        } else {
            setTitle(getString(R.string.edit_note));
            int noteId = bundle.getInt(NOTE_ID_KEY);
            mViewModel.loadData(noteId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!mViewModel.newNote) getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveAndReturn();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                saveAndReturn();
                break;
            case R.id.delete_note:
                deleteSingleNote();
                break;
        }
        return true;
    }

    private void deleteSingleNote() {
        mViewModel.deleteSingleNote();
        finish();
    }

    private void saveAndReturn() {
        String noteText = mTextView.getText().toString().trim();
        if (!TextUtils.equals(noteText, "")) {
            mViewModel.saveAndReturn(noteText);
        } else if (!mViewModel.newNote) deleteSingleNote();
        finish();
    }
}
