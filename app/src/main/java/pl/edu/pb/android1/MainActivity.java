package pl.edu.pb.android1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private int currentIndex=0;
    private int currentpoints=0;
    private boolean alreadyAnswered=false;
    private Question[] questions=new Question[]{
            new Question(R.string.q_activity,true),
            new Question(R.string.q_find_resources,false),
            new Question(R.string.q_listener,true),
            new Question(R.string.q_resources,true),
            new Question(R.string.q_version,false),
            new Question(R.string.q_kernel,true),
            new Question(R.string.q_ios,true)
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trueButton=findViewById(R.id.true_button);
        falseButton=findViewById(R.id.false_button);
        nextButton=findViewById(R.id.next_button);
        questionTextView=findViewById(R.id.question_text_view);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alreadyAnswered) printAlreadyAnswered(); else{
                    checkAnswerCorrectness(true);
                    alreadyAnswered=true;}
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alreadyAnswered) printAlreadyAnswered(); else{
                checkAnswerCorrectness(false);
                alreadyAnswered=true;}
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex=(currentIndex+1)%questions.length;
                if(currentIndex==0) showResult();
                alreadyAnswered=false;
                setNextQuestion();
            }
        });
        setNextQuestion();
    }
    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
    private void printAlreadyAnswered(){
        Toast.makeText(this,R.string.already_ans,Toast.LENGTH_SHORT).show();
    }
    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer=questions[currentIndex].isTrueAnswer();
        int resultMessageId;
        if(userAnswer==correctAnswer){
            resultMessageId=R.string.correct_answer;
            currentpoints++;
        }else{
            resultMessageId=R.string.incorrect_answer;
        }
        Toast.makeText(this,resultMessageId,Toast.LENGTH_SHORT).show();
    }
    private void showResult(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        String result_text=getString(R.string.result_message,currentpoints,questions.length);
        builder.setMessage(result_text).setTitle(R.string.result_title);
        AlertDialog dialog =builder.create();
        dialog.show();
        currentpoints=0;
    }
}