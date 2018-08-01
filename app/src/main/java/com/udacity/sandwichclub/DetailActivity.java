package com.udacity.sandwichclub;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mPlaceOfOrigin;
    private TextView mAlsoKnownAs;
    private TextView mIngredients;
    private TextView mDescription;
    private ImageView mIngredientsIv;
    private TextView mMainName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);
            initView();

            Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
            }

            int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
            if (position == DEFAULT_POSITION) {
                // EXTRA_POSITION not found in intent
                closeOnError();
                return;
            }

            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            setTitle(sandwich.getMainName());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView(){
        mIngredientsIv = findViewById(R.id.image_iv);
        mPlaceOfOrigin = findViewById(R.id.origin_tv);
        mAlsoKnownAs = findViewById(R.id.also_known_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mDescription = findViewById(R.id.description_tv);
        mMainName = findViewById(R.id.main_name_tv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(final Sandwich sandwich) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                    Glide.with(DetailActivity.this)
                            .load(sandwich.getImage())
                            .error(R.mipmap.ic_launcher)
                            .into(mIngredientsIv);
            }
        });
        StringBuffer sb = new StringBuffer();
        for (int i =0;i<sandwich.getIngredients().size();i++){
            if(i > 0 && ((sandwich.getIngredients().size()-1)-i >0))sb.append(", ");
            String ingredient =  sandwich.getIngredients().get(i);
            sb.append(ingredient);
        }
        mIngredients.setText(sb.toString());
        sb = new StringBuffer();
        for (int i =0;i<sandwich.getAlsoKnownAs().size();i++){
            if(i > 0 && ((sandwich.getAlsoKnownAs().size()-1)-i >0))sb.append(", ");
            String alsoKnownAs =  sandwich.getAlsoKnownAs().get(i);
            sb.append(alsoKnownAs);
        }
        mAlsoKnownAs.setText(TextUtils.isEmpty(sb.toString()) ? "No other name" : sb.toString());
        mMainName.setText(sandwich.getMainName());
        mDescription.setText(sandwich.getDescription());
        mPlaceOfOrigin.setText(TextUtils.isEmpty(sandwich.getPlaceOfOrigin()) ? "Not Known" : sandwich.getPlaceOfOrigin());
    }
}
