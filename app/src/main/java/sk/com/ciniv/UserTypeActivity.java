package sk.com.ciniv;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


public class UserTypeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView subscriber, distributor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        init();
        FontsOverride.applyFont(getApplicationContext(),
                findViewById(R.id.activity_user_type_root), "fonts/font.ttf");
        FontsOverride.applyFont(getApplicationContext(),
                findViewById(android.R.id.list), "fonts/font.ttf");


    }

    private void init() {
        subscriber = (TextView) findViewById(R.id.subscriber);
        distributor = (TextView) findViewById(R.id.distributer);
        subscriber.setOnClickListener(this);
        distributor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.subscriber:
                Intent intent = new Intent(getApplicationContext(), SubscriberLogin.class);
                startActivity(intent);
                break;
            case R.id.distributer:

        }
    }
}
