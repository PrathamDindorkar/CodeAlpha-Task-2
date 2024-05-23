package com.example.lingolearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class LessonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
    }

    public void openNextLesson(View view) {
        // Replace NextLessonActivity with the actual activity class for the next lesson
        Intent intent = new Intent(this, NextLessonActivity.class);
        startActivity(intent);
    }
}
