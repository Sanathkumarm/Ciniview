package sk.com.ciniv;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SubscriberLogin extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView operatorLogo;
    Button subscriberSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FontsOverride.applyFont(getApplicationContext(),
                findViewById(R.id.activity_subscriber_login_root), "fonts/font.ttf");
        FontsOverride.applyFont(getApplicationContext(),
                findViewById(android.R.id.list), "fonts/font.ttf");
        operatorLogo = (ImageView) findViewById(R.id.operator_logo);
        Spinner spinner = (Spinner) findViewById(R.id.operator);
        subscriberSignIn = (Button) findViewById(R.id.subscriber_signin);
        subscriberSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FetchDataActivity.class);
                startActivity(intent);
            }
        });
        spinner.setOnItemSelectedListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("SELECT YOUR OPERATOR");
        categories.add("KERALA VISION");
        categories.add("ACV");
        categories.add("DEN");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String item = parent.getItemAtPosition(position).toString();

        if (item.equals("KERALA VISION")) {
            operatorLogo.setImageResource(R.drawable.keralavision);
        } else if (item.equals("ACV")) {
            operatorLogo.setImageResource(R.drawable.acv);
        } else if (item.equals("DEN")) {
            operatorLogo.setImageResource(R.drawable.den);
        } else {
            operatorLogo.setImageResource(R.drawable.dish);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
