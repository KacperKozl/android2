package pl.edu.pb.android1;

public class Question {
    private int questionId;
    private Boolean trueAnswer;
    public Question(int questionId,Boolean trueAnswer){
        this.questionId=questionId;
        this.trueAnswer=trueAnswer;
    }
    public boolean isTrueAnswer(){
        return trueAnswer;
    }
    public int getQuestionId(){
        return questionId;
    }
}
