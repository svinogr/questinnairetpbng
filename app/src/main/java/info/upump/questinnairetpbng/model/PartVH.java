package info.upump.questinnairetpbng.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import info.upump.questinnairetpbng.PartActivity;
import info.upump.questinnairetpbng.R;
import info.upump.questinnairetpbng.entity.Part;

public class PartVH extends RecyclerView.ViewHolder {
    private Context context;
    private TextView shortName, name;
    private Part part;

    public PartVH(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.shortName = itemView.findViewById(R.id.part_card_text_short_name);
        this.name = itemView.findViewById(R.id.part_card_text_name);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = PartActivity.createIntent(context, part);
                context.startActivity(intent);
            }
        });
    }

    public void bind(Part part) {
        this.part = part;
        String sName = String.format(context.getString(R.string.title_card_part_item), part.getShortName());
        shortName.setText(sName);
        name.setText(part.getName());
    }
}
