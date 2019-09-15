package com.subdico.moviecatalogue4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.subdico.moviecatalogue4.db.FavHelper;
import com.subdico.moviecatalogue4.model.ListData;


public class DetailActivity extends AppCompatActivity {
    public final static String Extra = "extra";
    ListData mListData;
    ImageView imgDetail;
    TextView tvName, tvDate, tvOverview, tvRatingBar;
    RatingBar ratingBar;
    Menu menu;

    FavHelper favHelper = new FavHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgDetail = findViewById(R.id.img_detail);
        tvName = findViewById(R.id.detail_name);
        tvDate = findViewById(R.id.release_date);
        tvOverview = findViewById(R.id.overview);
        tvRatingBar = findViewById(R.id.rating_label);
        ratingBar = findViewById(R.id.rating_bar);

        mListData = getIntent().getParcelableExtra(Extra);

        favHelper = FavHelper.getInstance(getApplicationContext());
        favHelper.open();
        checkSql();


        if(mListData != null){

            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle(mListData.getName());
            }

            Glide.with(this)
                    .load(mListData.getPoster_path())
                    .apply(new RequestOptions()
                        .override(350, 550))
                    .into(imgDetail);

            tvName.setText(mListData.getName());
            tvDate.setText(mListData.getFirst_air_date());
            tvOverview.setText(mListData.getOverview());

            float d = mListData.getVote_average().floatValue()/2;
            String label_rating = Float.toString(d);

            tvRatingBar.setText(getResources().getString(R.string.label_rating, label_rating));
            ratingBar.setRating(d);

        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_detail, menu);
        if (checkSql()){
            menu.getItem(0).setIcon(R.drawable.ic_favorite_red);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.add_favorites:
//                insert();
                showAlertDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog(){
        String dialogTitle, dialogMessage;

        if (checkSql()){
            dialogTitle = getResources().getString(R.string.dialog_title_remove);
            dialogMessage = getResources().getString(R.string.dialog_message_remove);

        }else{
            dialogTitle = getResources().getString(R.string.dialog_title_add);
            dialogMessage = getResources().getString(R.string.dialog_message_add);
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(dialogTitle);
        alert.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.btn_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (checkSql()){
                            long delete = favHelper.deleteFav(mListData.getName());
                            if (delete > 0 ){
                                Toast.makeText(DetailActivity.this, getResources().getString(R.string.sukses_delete), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }else{
                            insert();
                            menu.getItem(0).setIcon(R.drawable.ic_favorite_red);
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.btn_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    private void insert(){
        long result = favHelper.insertFav(mListData);
        if (result > 0){
            mListData.setId((int) result);
            Toast.makeText(DetailActivity.this, getResources().getString(R.string.sukses_add), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(DetailActivity.this, getResources().getString(R.string.sukses_fail), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkSql(){
        Cursor cekIsFav = favHelper.checkFav(mListData.getName());
        boolean state = false;
        if (cekIsFav.getCount() > 0){
            state = true;
        }
        return state;
    }
}
