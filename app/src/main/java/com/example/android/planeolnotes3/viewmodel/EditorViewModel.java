package com.example.android.planeolnotes3.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.android.planeolnotes3.database.AppRepository;
import com.example.android.planeolnotes3.database.NoteEntity;
import com.example.android.planeolnotes3.utilities.SampleData;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<NoteEntity> mMutableLiveData =
            new MutableLiveData<>();

    private NoteEntity entity;

    private AppRepository mAppRepository;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public boolean newNote;

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int noteId) {

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                entity = mAppRepository.getNoteById(noteId);
                mMutableLiveData.postValue(entity);
            }
        });
    }

    public void saveAndReturn(String noteText) {

        if (newNote) {
            entity = new NoteEntity(new Date(), noteText);
        } else {
            entity.setText(noteText);
        }
        mAppRepository.saveNote(entity);
    }

    public void deleteSingleNote() {
            mAppRepository.deleteSingleNote(entity);
    }
}
