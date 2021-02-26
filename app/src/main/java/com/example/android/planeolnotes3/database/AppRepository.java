package com.example.android.planeolnotes3.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.android.planeolnotes3.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {

    private static AppRepository ourInstance;
    private AppDatabase mDatabase;
    public LiveData<List<NoteEntity>> mNotes;
    private Executor mExecutor = Executors.newSingleThreadExecutor();


    private AppRepository(Context context) {
        mDatabase = AppDatabase.getInstanse(context);
        mNotes = getAllNotes();
    }

    public static AppRepository getInstance(Context context) {

        if (ourInstance == null) {
            ourInstance =  new AppRepository(context);
        }

        return ourInstance;
    }


    public void addSampleData() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.noteDao().insertAll(SampleData.getNotes());
            }
        });
    }

    private LiveData<List<NoteEntity>> getAllNotes (){
        return mDatabase.noteDao().getAll();
    }

    public void deleteAllData() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.noteDao().deleteAll();
            }
        });
    }

    public NoteEntity getNoteById(int noteId) {
        return mDatabase.noteDao().getNoteByID(noteId);
    }

    public void saveNote(final NoteEntity note) {

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.noteDao().insertNote(note);
            }
        });
    }

    public void deleteSingleNote(final NoteEntity note) {

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.noteDao().deleteNote(note);
            }
        });
    }
}
