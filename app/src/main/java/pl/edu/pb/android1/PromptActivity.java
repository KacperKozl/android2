package pl.edu.pb.android1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {
    private boolean correctAnswer;
    private Button showCorrectAnswerButton;
    private TextView answerTextView;
public static final String KEY_EXTRA_ANSWER_SHOWN="pb.edu.pb.android1.answerShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);
        showCorrectAnswerButton=findViewById(R.id.show_correct_answer_button);
        answerTextView=findViewById(R.id.answer_text_view);
        correctAnswer=getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER,true);
        showCorrectAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answer=correctAnswer?R.string.button_true:R.string.button_false;
                answerTextView.setText(answer);
                setAnswerShownResult(true);

            }
        });
    }
    private void setAnswerShownResult(boolean answerWasShown){
        Intent resultIntent=new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN,answerWasShown);
        setResult(MainActivity.REQUEST_CODE_PROMPT,resultIntent);
    }
}