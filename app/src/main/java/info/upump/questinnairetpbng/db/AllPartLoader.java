package info.upump.questinnairetpbng.db;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import info.upump.questinnairetpbng.entity.Part;

public class AllPartLoader extends AsyncTaskLoader<List<Part>> {
    private Context context;

    public AllPartLoader(Context context) {
        super(context);
    }

    @Override
    public List<Part> loadInBackground() {
        PartDAO partDAO = new PartDAO(context);
        List<Part> list = partDAO.getParts();
        return list;
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
    public void deliverResult(List<Part> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
