package com.example.myquiz2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.transition.Hold;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView question_num, question_text, duration_time;
    private Button option1, option2, option3, option4;
    private List<Question> questionList;
    private Integer questNum, score;
    private CountDownTimer downTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        score = 0;

        question_num = findViewById(R.id.txv_numOfQuestion_title);
        question_text = findViewById(R.id.txv_question);
        duration_time = findViewById(R.id.txv_count);

        option1 = findViewById(R.id.btn_option1);
        option2 = findViewById(R.id.btn_option2);
        option3 = findViewById(R.id.btn_option3);
        option4 = findViewById(R.id.btn_option4);

        option1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E99C03")));
        option2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E99C03")));
        option3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E99C03")));
        option4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E99C03")));
        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        getQuestionsList();

    }

    private void getQuestionsList() {
        questionList = new ArrayList<>();
        questionList.add(new Question("Question 1", "A", "B", "C", "D", 2));
        questionList.add(new Question("Question 2", "B", "A", "C", "D", 3));
        questionList.add(new Question("Question 3", "A", "B", "C", "D", 4));
        questionList.add(new Question("Question 4", "C", "B", "D", "A", 1));
        questionList.add(new Question("Question 5", "D", "B", "C", "A", 3));
        questionList.add(new Question("Question 6", "A", "C", "B", "D", 2));

        setQuestion();

    }

    private void setQuestion() {
        duration_time.setText(String.valueOf(10));

        question_text.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOptionA());
        option2.setText(questionList.get(0).getOptionB());
        option3.setText(questionList.get(0).getOptionC());
        option4.setText(questionList.get(0).getOptionD());

        question_num.setText(String.valueOf(1) + "/" + String.valueOf(questionList.size()));

        startTimer();
        questNum = 0;
    }

    private void startTimer() {
        downTimer = new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 10000) {
                    duration_time.setText(String.valueOf(millisUntilFinished / 1000));
                }

            }

            @Override
            public void onFinish() {

                changeQuestion();

            }
        };

        downTimer.start();
    }

    @Override
    public void onClick(View v) {
        Integer selectedOption = 0;

        switch (v.getId()) {
            case R.id.btn_option1:
                selectedOption = 1;
                break;
            case R.id.btn_option2:
                selectedOption = 2;
                break;
            case R.id.btn_option3:
                selectedOption = 3;
                break;
            case R.id.btn_option4:
                selectedOption = 4;
                break;
            default:
        }

        downTimer.cancel();

        checkAnswer(selectedOption, v);

    }

    private void checkAnswer(Integer selectedOption, View view) {
        if (selectedOption == questionList.get(questNum).getCorrectAnsw()) {
            //Right answer

            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score++;
        } else {
            //wrong answer
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));

            switch (questionList.get(questNum).getCorrectAnsw()) {
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;

            }
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //do something after 2000 milliseconds
                changeQuestion();
            }
        }, 500);


    }

    private void changeQuestion() {
        if (questNum < questionList.size() - 1) {

            questNum++;

            playanim(question_text, 0, 0);
            playanim(option1, 0, 1);
            playanim(option2, 0, 2);
            playanim(option3, 0, 3);
            playanim(option4, 0, 4);

            question_num.setText(String.valueOf(questNum + 1) + "/" + String.valueOf(questionList.size()));

            duration_time.setText(String.valueOf(10));

            startTimer();


        } else {
            //Last question go to
            //Score Activity
            Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
            intent.putExtra("SCORE", String.valueOf(score + " / " + String.valueOf(questionList.size())));
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }

    private void playanim(View view, final Integer value, Integer viewNum) {

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        if (value == 0) {
                            switch (viewNum) {
                                case 0:
                                    ((TextView) view).setText(questionList.get(questNum).getQuestion());
                                    break;
                                case 1:
                                    ((Button) view).setText(questionList.get(questNum).getOptionA());
                                    break;
                                case 2:
                                    ((Button) view).setText(questionList.get(questNum).getOptionB());
                                    break;
                                case 3:
                                    ((Button) view).setText(questionList.get(questNum).getOptionC());
                                    break;
                                case 4:
                                    ((Button) view).setText(questionList.get(questNum).getOptionD());
                                    break;
                            }

                            if (viewNum != 0) {
                                ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E99C03")));
                            }

                            playanim(view, 1, viewNum);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        downTimer.cancel();
    }
}