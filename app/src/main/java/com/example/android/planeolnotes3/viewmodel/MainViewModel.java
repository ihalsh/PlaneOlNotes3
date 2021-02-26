package com.example.android.planeolnotes3.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.planeolnotes3.database.AppRepository;
import com.example.android.planeolnotes3.database.NoteEntity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>> mNotes;
    private AppRepository mAppRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance(application.getApplicationContext());
        mNotes = mAppRepository.mNotes;
    }

    public void addSampleData() {
        mAppRepository.addSampleData();
    }

    public void deleteAllData() {
        mAppRepository.deleteAllData();
    }
}
