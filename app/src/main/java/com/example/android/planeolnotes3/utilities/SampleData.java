package com.example.android.planeolnotes3.utilities;

import com.example.android.planeolnotes3.database.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {

    public static final String SAMPLE_TEXT_1 = "A simple note";
    public static final String SAMPLE_TEXT_2 = "A note with a \n line feed";
    public static final String SAMPLE_TEXT_3 = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n\n" +
                        "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.";

    private static Date getDate(int diff) {

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MILLISECOND, diff);
        return calendar.getTime();

    }

    public static List<NoteEntity> getNotes() {

        List<NoteEntity> sampleNote = new ArrayList<>();
        sampleNote.add(new NoteEntity(getDate(0), SAMPLE_TEXT_1));
        sampleNote.add(new NoteEntity(getDate(-1), SAMPLE_TEXT_2));
        sampleNote.add(new NoteEntity(getDate(-2), SAMPLE_TEXT_3));

        return sampleNote;

    }

}
