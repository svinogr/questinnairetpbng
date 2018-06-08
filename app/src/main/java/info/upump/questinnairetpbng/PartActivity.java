package info.upump.questinnairetpbng;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import info.upump.questinnairetpbng.adapter.QuestionAdapter;
import info.upump.questinnairetpbng.db.PartLoader;
import info.upump.questinnairetpbng.entity.Part;
import info.upump.questinnairetpbng.entity.Question;

public class PartActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Question>> {

    private static final String PART_SHORT = "short name";
    private List<Question> questions = new ArrayList<>();
    private QuestionAdapter questionAdapter;
    private static final String PART = "part";
    private Part part;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private LinearLayoutManager linearLayoutManager;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 100) {
                try {

                    int number = Integer.parseInt((String) msg.obj);
                    if (number > recyclerView.getAdapter().getItemCount()) {
                        number = recyclerView.getAdapter().getItemCount();
                    }
                    if (number < 1) {
                        number = 1;
                    }
                    linearLayoutManager.scrollToPositionWithOffset(number - 1, 0);

                } catch (NumberFormatException e) {
                    questionAdapter.filter(((String) msg.obj).trim());

                } catch (IndexOutOfBoundsException e) {
                    recyclerView.stopScroll();
                }
            }
        }
    };


    public static Intent createIntent(Context context, Part part){
        Intent intent = new Intent(context, PartActivity.class);
        intent.putExtra(PART, part.getId());
        intent.putExtra(PART_SHORT, part.getShortName());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        part = new Part();
        part.setId(getIntent().getIntExtra(PART, 0));
        part.setShortName(getIntent().getStringExtra(PART_SHORT));

        setTitle(part.getShortName());
        questionAdapter = new QuestionAdapter(this, questions);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.part_activity_recycler);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(questionAdapter);
        progressBar = findViewById(R.id.part_activity_progressSearchCategory);
        searchView = findViewById(R.id.part_activity_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handler.removeMessages(100);
                handler.sendMessageDelayed(handler.obtainMessage(100, newText), 250);
                return true;
            }
        });
        getSupportLoaderManager().initLoader(2, null, this);
    }

    @NonNull
    @Override
    public Loader<List<Question>> onCreateLoader(int id, @Nullable Bundle args) {
        return new PartLoader(this, part.getId());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Question>> loader, List<Question> data) {
        questions.clear();
        questions.addAll(data);
        questionAdapter.notifyDataSetChanged();
        progressBar.setVisibility(progressBar.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Question>> loader) {

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_two, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.checkit:
                intent = ChooseInterval.createIntent(this,String.valueOf(part.getId()));
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
