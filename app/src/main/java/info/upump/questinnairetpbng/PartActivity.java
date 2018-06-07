package info.upump.questinnairetpbng;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.upump.questinnairetpbng.entity.Part;

public class PartActivity extends AppCompatActivity {

    private static final String PART = "part";

    public static Intent createIntent(Context context, Part part){
        Intent intent = new Intent(context, PartActivity.class);
        intent.putExtra(PART, part.getId());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part);
    }
}
