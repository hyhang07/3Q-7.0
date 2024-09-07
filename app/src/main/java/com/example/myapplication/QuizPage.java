package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.HashSet;
import java.util.Set;

public class QuizPage extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;
    ProgressBar progressBar;
    MediaPlayer correctSound;
    MediaPlayer incorrectSound;
    int score = 0;
    int totalQuestion = QuestionAns.question.length;
    int questionsAsked = 0;
    String selectedAnswer = "";

    Set<Integer> askedQuestions = new HashSet<>();

    QuestionAns.QuestionSet currentQuestion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_button);
        progressBar = findViewById(R.id.progress_bar);
        correctSound = MediaPlayer.create(this,R.raw.correct);
        incorrectSound = MediaPlayer.create(this,R.raw.wrong);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions: " + totalQuestion);

        loadNewQuestion();


        // Toolbar setup
        ShapeableImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> toolbar.navigateToMainActivity(QuizPage.this));
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> toolbar.showOptionsDialog(QuizPage.this, which -> {
            switch (which) {
                case 0:
                    toolbar.profile(QuizPage.this);
                    break;
                case 1:
                    toolbar.setting(QuizPage.this);
                    break;
            }
        }));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit_button) {
            // Check if the selected answer is correct and change color accordingly
            if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
                // If correct, change the selected answer's button color to green
                highlightCorrectAnswer();
                score++;
                view.setBackgroundColor(Color.GREEN); // Change Submit button to green if correct
                correctSound.start();
            } else {
                // If incorrect, change the selected answer's button color to red
                highlightCorrectAnswer();
                highlightSelectedAnswer(Color.RED);
                view.setBackgroundColor(Color.RED); // Change Submit button to red if incorrect
                incorrectSound.start();
            }

            questionsAsked++; // Increment the number of questions asked

            // Delay to show the result color before loading the next question
            view.postDelayed(() -> {
                resetAnswerButtons();
                view.setBackgroundColor(Color.GRAY); // Reset Submit button color
                animateOutAndLoadNextQuestion();
            }, 1000);

        } else {
            // User selected an answer
            selectedAnswer = ((Button) view).getText().toString();

            // Highlight the selected button with a temporary color
            resetAnswerButtons(); // Reset colors before applying the new one
            view.setBackgroundColor(Color.MAGENTA); // Highlight the selected button
        }
    }

    void highlightCorrectAnswer() {
        // Highlight the correct answer's button with green
        if (ansA.getText().toString().equals(currentQuestion.getCorrectAnswer())) {
            ansA.setBackgroundColor(Color.GREEN);
        } else if (ansB.getText().toString().equals(currentQuestion.getCorrectAnswer())) {
            ansB.setBackgroundColor(Color.GREEN);
        } else if (ansC.getText().toString().equals(currentQuestion.getCorrectAnswer())) {
            ansC.setBackgroundColor(Color.GREEN);
        } else if (ansD.getText().toString().equals(currentQuestion.getCorrectAnswer())) {
            ansD.setBackgroundColor(Color.GREEN);
        }
    }

    void highlightSelectedAnswer(int color) {
        // Highlight the selected answer's button with the specified color
        if (selectedAnswer.equals(ansA.getText().toString())) {
            ansA.setBackgroundColor(color);
        } else if (selectedAnswer.equals(ansB.getText().toString())) {
            ansB.setBackgroundColor(color);
        } else if (selectedAnswer.equals(ansC.getText().toString())) {
            ansC.setBackgroundColor(color);
        } else if (selectedAnswer.equals(ansD.getText().toString())) {
            ansD.setBackgroundColor(color);
        }
    }

    void resetAnswerButtons() {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);
    }

    void animateOutAndLoadNextQuestion() {
        questionTextView.animate()
                .alpha(0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loadNewQuestion();
                        questionTextView.setAlpha(1f);
                    }
                });

        ansA.animate().alpha(1f).setDuration(300).setListener(null);
        ansB.animate().alpha(1f).setDuration(300).setListener(null);
        ansC.animate().alpha(1f).setDuration(300).setListener(null);
        ansD.animate().alpha(1f).setDuration(300).setListener(null);
    }


    void loadNewQuestion() {

        if (questionsAsked == totalQuestion) {
            finishQuiz();
            return;
        }

        currentQuestion = QuestionAns.getRandomQuestion();

        // Ensure the question hasn't been asked before
        while (askedQuestions.contains(currentQuestion.getQuestion().hashCode())) {
            currentQuestion = QuestionAns.getRandomQuestion();
        }

        // Track this question as asked
        askedQuestions.add(currentQuestion.getQuestion().hashCode());

        progressBar.setProgress((questionsAsked * 100) / totalQuestion);

        // Update the UI with the new question and its answers
        questionTextView.setText(currentQuestion.getQuestion());
        ansA.setText(currentQuestion.getChoices()[0]);
        ansB.setText(currentQuestion.getChoices()[1]);
        ansC.setText(currentQuestion.getChoices()[2]);
        ansD.setText(currentQuestion.getChoices()[3]);
    }



    void finishQuiz() {
        String passStatus = "";
        if (score > totalQuestion * 0.60) {
            passStatus = "Passed";
        } else {
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Your Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Redo", (dialogInterface, i) -> redoQuiz())
                .setCancelable(false)
                .show();
    }

    void redoQuiz() {
        score = 0;
        questionsAsked = 0;
        askedQuestions.clear(); // Clear the set of asked questions
        loadNewQuestion();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        if(correctSound != null){
            correctSound.release();
            correctSound = null;
        }
        if(incorrectSound != null){
            incorrectSound.release();
            incorrectSound = null;
        }
    }
}
