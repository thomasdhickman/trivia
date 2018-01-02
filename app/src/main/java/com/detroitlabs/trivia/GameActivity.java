package com.detroitlabs.trivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends Activity implements View.OnClickListener {

    List<Trivia> myQuestionsList = new ArrayList<>();

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button nextButton;

    TextView playerName;
    TextView questionTextView;

    int progress = 0;
    Trivia currentTrivia;
    boolean answeredCorrectly;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        myQuestionsList.add(new Trivia("Who was the first US president?", "John Adams", "Barack Obama", "George Washington", "Donald Trump", R.id.button3));
        myQuestionsList.add(new Trivia("Who invented the first hovercraft?", "Christopher Cockrell", "Benjamin Franklin", "Thomas Edison", "Albert Einstein", R.id.button1));
        myQuestionsList.add(new Trivia("Who defined gravity?", "Santa Claus", "Aristotle", "Copernicus", "Issac Newton", R.id.button4));
        myQuestionsList.add(new Trivia("Who slayed Cyrus the Great?", "Alexander the Great", "Hercules", "Tomyris", "Cleopatra", R.id.button3));

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        nextButton = (Button) findViewById(R.id.nextButton);

        playerName = (TextView) findViewById(R.id.playerName);
        questionTextView = (TextView) findViewById(R.id.question);

        String name = getIntent().getStringExtra("NAME");
        playerName.setText("Hi, " + name + "!");

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress++;
                nextQuestion();
            }
        });

        nextQuestion();
    }

    private void nextQuestion() {
        if (progress == 4) {
            Intent intent = new Intent(GameActivity.this, EndScreenActivity.class);
            startActivity(intent);
        } else {
            answeredCorrectly = false;
            currentTrivia = myQuestionsList.get(progress);
            questionTextView.setText(currentTrivia.question);
            button1.setText(currentTrivia.answer1);
            button2.setText(currentTrivia.answer2);
            button3.setText(currentTrivia.answer3);
            button4.setText(currentTrivia.answer4);

            int gray = getResources().getColor(android.R.color.darker_gray);
            button1.setBackgroundColor(gray);
            button2.setBackgroundColor(gray);
            button3.setBackgroundColor(gray);
            button4.setBackgroundColor(gray);

            nextButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int correctAnswer = currentTrivia.correctAnswer;
        if (view.getId() == correctAnswer) {
            // You are correct!
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            nextButton.setVisibility(View.VISIBLE);
            answeredCorrectly = true;
        } else if (!answeredCorrectly) {
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    class Trivia {
        String question;
        String answer1;
        String answer2;
        String answer3;
        String answer4;
        int correctAnswer;

        public Trivia(String question, String answer1, String answer2, String answer3, String answer4, int correctAnswer) {
            this.question = question;
            this.answer1 = answer1;
            this.answer2 = answer2;
            this.answer3 = answer3;
            this.answer4 = answer4;
            this.correctAnswer = correctAnswer;
        }
    }
}
