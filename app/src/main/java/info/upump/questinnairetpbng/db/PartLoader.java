package info.upump.questinnairetpbng.db;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import info.upump.questinnairetpbng.entity.Part;
import info.upump.questinnairetpbng.entity.Question;

public class PartLoader  extends AsyncTaskLoader<List<Question>> {
    private Context context;
    private int partId;

    public PartLoader(Context context, int partId) {
        super(context);
        this.partId = partId;
    }

    @Override
    public List<Question> loadInBackground() {
       QuestionDAO questionDAO = new QuestionDAO(context);
       List<Question> questions = questionDAO.getQuestionsByPartId(partId);
        return questions;
    }

    private String stringToUpperCase(String s) {
        return s != null && s.length() != 0 ? s.substring(0, 1).toUpperCase() + s.substring(1) : null;
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
