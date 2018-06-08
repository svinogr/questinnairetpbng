package info.upump.questinnairetpbng.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.upump.questinnairetpbng.R;
import info.upump.questinnairetpbng.entity.Part;
import info.upump.questinnairetpbng.model.PartVH;

public class AllPartAdapter extends RecyclerView.Adapter<PartVH> {
    private List<Part> parts = new ArrayList<>();

    public AllPartAdapter(List<Part> parts) {
        this.parts = parts;
    }

    @NonNull
    @Override
    public PartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.part_card_item, parent, false);
        return new PartVH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PartVH holder, int position) {
        holder.bind(parts.get(position));
    }

    @Override
    public int getItemCount() {
        return parts.size();
    }
}
