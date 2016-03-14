package sk.com.ciniv;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class SubscriberHome extends AppCompatActivity implements View.OnClickListener {

    TextView bookShow, myShow, buyTickets, contact, movieTitle;
    ImageView banner;
    Button purchaseTickets;
    //MaterialDialog mMaterialDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FontsOverride.applyFont(getApplicationContext(),
                findViewById(R.id.activity_subscriber_home_root), "fonts/font.ttf");
        FontsOverride.applyFont(getApplicationContext(),
                findViewById(android.R.id.list), "fonts/font.ttf");

//
//        mMaterialDialog = new MaterialDialog(this)
//
//                .setContentView(R.layout.dialog_book_show)
//
//                .setNegativeButton("CANCEL", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mMaterialDialog.dismiss();
//
//                    }
//                });

        bookShow = (TextView) findViewById(R.id.book_show);
        myShow = (TextView) findViewById(R.id.myshow);
        contact = (TextView) findViewById(R.id.contact);
        buyTickets = (TextView) findViewById(R.id.buy_tickets);
        movieTitle = (TextView) findViewById(R.id.movie_title);
        movieTitle.setText(Config.MOVIE_NAME);
        banner = (ImageView) findViewById(R.id.banner);


        banner.setOnClickListener(this);
        bookShow.setOnClickListener(this);
        myShow.setOnClickListener(this);
        contact.setOnClickListener(this);
        buyTickets.setOnClickListener(this);


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setTitle("Cine View");

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        int barColor = Color.parseColor("#FFC107");
        collapsingToolbarLayout.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673AB7")));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.book_show:
                Intent bookShow = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(bookShow);
                break;
            case R.id.banner:
                Intent banner = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(banner);
                break;
            case R.id.buy_tickets:
              /*  new MaterialDialog.Builder(this)
                        .title("Confirm Purchase")
                        .content("Press Buy Now to purchase your ticket.You will recieve your ticket shortly via SMS")
                        .positiveText("Buy Now")
                        .negativeText("Cancel")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent intent = new Intent(getApplicationContext(),PurchaseTicketActivity.class);
                                startActivity(intent);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback(){
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Toast.makeText(getApplicationContext(),"NegetiveText",Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        })
                        .show();*/
                break;
            case R.id.book_now:
                Toast.makeText(getApplicationContext(), "haai", Toast.LENGTH_LONG).show();
                break;


        }
    }
}
