package info.upump.questinnairetpbng.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.upump.questinnairetpbng.R;
import info.upump.questinnairetpbng.entity.Answer;
import info.upump.questinnairetpbng.entity.Question;


public class QuestionViewHolder extends RecyclerView.ViewHolder {
    public TextView number;
    public TextView questionBody;
    public ImageView img;
    public TextView comment;
    public LinearLayout linearLayoutAnswer;
    public View comDiv;
    private Context context;
    private Question question;

    public QuestionViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        linearLayoutAnswer = itemView.findViewById(R.id.list_answer);
        number = itemView.findViewById(R.id.number);
        questionBody = itemView.findViewById(R.id.question);
        img = itemView.findViewById(R.id.img);
        comDiv = itemView.findViewById(R.id.com_div);
    }

    public void bind(Question question) {
        this.question = question;
        Resources resources = context.getResources();
        String numberStr = String.format(resources.getString(R.string.number_question), getAdapterPosition() + 1);
        number.setText(numberStr);

        questionBody.setText(question.getBody());
        addAnswers();
        setImg();
    }

    private void setImg() {
    /*    RequestOptions options = new RequestOptions()
                .transforms(new RoundedCorners(50))
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        String imgStr = question.getImg();
        if(imgStr!= null){
            img.setVisibility(View.VISIBLE);
            int ident = context.getResources().getIdentifier(imgStr, "drawable", context.getPackageName());
            Glide.with(context).load(ident).apply(options).into(img);
        } else img.setVisibility(View.GONE);*/

    }

    private void addAnswers() {
        linearLayoutAnswer.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,15,0,0);

        for (Answer answer : question.getAnswers()) {
            TextView text = new TextView(context);
            text.setLayoutParams(layoutParams);
            switch (answer.getRight()) {
                case 1:
                    text.setTypeface(null, Typeface.BOLD_ITALIC);
                    break;
                case 0:
                    text.setTypeface(null, Typeface.ITALIC);
                    break;
                case -1:
                    break;
            }
            text.setTextColor(context.getResources().getColor(R.color.colorText));
            text.setText(String.format(context.getString(R.string.answer_text), answer.getBody()));
            linearLayoutAnswer.addView(text);
        }
    }
}
