package com.example.lingolearn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private Button submitButton;
    private Button nextButton;
    private TextView questionTextView;
    private ProgressBar progressBar;
    private TextView progressText;
    private List<Question> questions;
    private int currentQuestionIndex;
    private boolean isSubmitted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        radioGroup = findViewById(R.id.radioGroup);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);
        questionTextView = findViewById(R.id.questionTextView);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        questions = getSampleQuestions();
        currentQuestionIndex = 0;
        isSubmitted = false;

        progressBar.setMax(questions.size() * 100); // Set the maximum value based on the number of questions
        updateProgress();

        displayQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSubmitted) {
                    checkAnswer();
                    isSubmitted = true;
                } else {
                    Toast.makeText(QuizActivity.this, "Answer already submitted!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSubmitted) {
                    moveToNextQuestion();
                } else {
                    Toast.makeText(QuizActivity.this, "Submit your answer first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getQuestion());
        radioGroup.clearCheck();
        radioGroup.removeAllViews();

        for (String option : currentQuestion.getOptions()) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            radioGroup.addView(radioButton);
        }

        isSubmitted = false;
    }

    private void checkAnswer() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);

        if (selectedRadioButton != null) {
            String selectedAnswer = selectedRadioButton.getText().toString();
            Question currentQuestion = questions.get(currentQuestionIndex);

            if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No answer selected!", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveToNextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            displayQuestion();
            updateProgress();
        } else {
            Toast.makeText(this, "Quiz completed!", Toast.LENGTH_SHORT).show();
            updateProgress();
            // Optionally, navigate back to the main activity or show results
        }
    }

    private void updateProgress() {
        int progress = ((currentQuestionIndex + 1) * 100) / questions.size();
        progressBar.setProgress(progress);
        progressText.setText("Progress: " + progress + "%");
    }

    private List<Question> getSampleQuestions() {
        List<Question> sampleQuestions = new ArrayList<>();
        sampleQuestions.add(new Question("What is 'Hello' in Japanese?", "こんにちは", new String[]{"こんばんは", "おはよう", "こんにちは", "さようなら"}));
        sampleQuestions.add(new Question("What is 'Thank you' in Japanese?", "ありがとう", new String[]{"ありがとう", "すみません", "はい", "いいえ"}));
        sampleQuestions.add(new Question("What is 'Goodbye' in Japanese?", "さようなら", new String[]{"こんばんは", "おはよう", "こんにちは", "さようなら"}));
        sampleQuestions.add(new Question("What is 'Good morning' in Japanese?", "おはよう", new String[]{"おはよう", "こんにちは", "こんばんは", "さようなら"})); // Added question
        return sampleQuestions;
    }

    private class Question {
        private String question;
        private String correctAnswer;
        private String[] options;

        public Question(String question, String correctAnswer, String[] options) {
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.options = options;
        }

        public String getQuestion() {
            return question;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public String[] getOptions() {
            return options;
        }
    }
}
