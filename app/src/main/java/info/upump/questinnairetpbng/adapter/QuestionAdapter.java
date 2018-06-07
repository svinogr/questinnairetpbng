package info.upump.questinnairetpbng.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.questinnairetpbng.R;
import info.upump.questinnairetpbng.entity.Answer;
import info.upump.questinnairetpbng.entity.Question;
import info.upump.questinnairetpbng.filter.CategoryFilter;
import info.upump.questinnairetpbng.model.QuestionViewHolder;


public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {
    private Activity activity;
    protected List<Question> list;
    private CategoryFilter filter;
    private List<Answer> answers;

    public QuestionAdapter(Activity activity, List<Question> list) {
        this.activity = activity;
        this.list = list;
        this.filter = new CategoryFilter(list, this);
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_card_item, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestionViewHolder holder, final int position) {
       holder.bind(list.get(position));

    }

    public void setList(List<Question> list) {
        this.list = list;
    }

    public void filter(String text) {
        filter.filter(text);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
