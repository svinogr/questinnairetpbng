package info.upump.questinnairetpbng.db;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import info.upump.questinnairetpbng.entity.Question;


public class QuestionLoader extends AsyncTaskLoader<List<Question>> {
    private Context context;

    public QuestionLoader(Context context) {
        super(context);
    }


    @Override
    public List<Question> loadInBackground() {
        QuestionDAO questionDAO = new QuestionDAO(context);
        List<Question> list =  questionDAO.getQuestions();
        return list;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();

    }

    @Override
    public void deliverResult(List<Question> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
