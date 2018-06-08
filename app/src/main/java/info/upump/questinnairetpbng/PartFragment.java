package info.upump.questinnairetpbng;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.upump.questinnairetpbng.adapter.AllPartAdapter;
import info.upump.questinnairetpbng.db.AllPartLoader;
import info.upump.questinnairetpbng.entity.Part;

public class PartFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Part>> {
    private RecyclerView recyclerView;
    private List<Part> parts = new ArrayList<>();
    private AllPartAdapter partAdapter;

    public PartFragment() {
        // Required empty public constructor
    }

    public static PartFragment newInstance() {
        PartFragment fragment = new PartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        partAdapter = new AllPartAdapter(parts);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_part, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = inflate.findViewById(R.id.part_fr_recycler);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(partAdapter);
        return inflate;
    }

    @NonNull
    @Override
    public Loader<List<Part>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AllPartLoader(getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Part>> loader, List<Part> data) {
        parts.clear();
        parts.addAll(data);
        partAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Part>> loader) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(getString(R.string.title_part_fr));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(1,null, this);
    }
}
