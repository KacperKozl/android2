package pl.edu.pb.android1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button answerButton;
    private TextView questionTextView;
    private int currentIndex=0;
    private int currentpoints=0;
    private boolean answerWasShown=false;
    private boolean alreadyAnswered=false;
    public static final String KEY_CURRENT_INDEX="currentIndex";
    public static final String KEY_EXTRA_ANSWER="pl.edu.pb.android1.correctAnswer";
    public static final String TAG="MainActivity";
    public static final int REQUEST_CODE_PROMPT=0;
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"Wywołana metoda cyklu zycia:onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX,currentIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"Wywołana metoda cyklu zycia:onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Wywołana metoda cyklu zycia:onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Wywołana metoda cyklu zycia:onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Wywołana metoda cyklu zycia:onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Wywołana metoda cyklu zycia:onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Wywołana metoda cyklu zycia:onCreate");
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            currentIndex=savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
        trueButton=findViewById(R.id.true_button);
        falseButton=findViewById(R.id.false_button);
        nextButton=findViewById(R.id.next_button);
        questionTextView=findViewById(R.id.question_text_view);
        answerButton=findViewById(R.id.answer_button);
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                            if(result.getResultCode()==REQUEST_CODE_PROMPT){
                                Log.d("MainActivity","shownreuslt");
                                Intent data=result.getData();
                                if(data==null) {return;}
                                answerWasShown=data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN,false);
                            }
                        }
                });
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,PromptActivity.class);
                boolean correctAnswer=questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER,correctAnswer);
                someActivityResultLauncher.launch(intent);
            }
        });
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
                answerWasShown=false;
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
        if(answerWasShown) resultMessageId=R.string.answer_was_shown;else
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