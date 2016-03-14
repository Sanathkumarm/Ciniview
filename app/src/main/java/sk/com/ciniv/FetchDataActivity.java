package sk.com.ciniv;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchDataActivity extends AppCompatActivity {
    // MaterialDialog mMaterialDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /*mMaterialDialog = new MaterialDialog.Builder(this)
                .title("Please Wait")
                .content("Fetching Content. Please Wait !")
                .progress(true, 0).show();*/
        String url = "http://smohith.com/cineback/gethomescreen.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //  mMaterialDialog.dismiss();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        ParseJsonObject(jsonObject);
                        Intent intent = new Intent(getApplicationContext(), SubscriberHome.class);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                        //    mMaterialDialog.dismiss();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_LONG).show();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                10,
                2));
        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);


    }

    private void ParseJsonObject(JSONObject jsonObject) throws JSONException {
        //Parsing the Json Object
        Toast.makeText(getApplicationContext(), jsonObject.getString("title"), Toast.LENGTH_LONG).show();
        Config.MOVIE_NAME = jsonObject.getString("title");
        Config.BANNER = jsonObject.getString("banner");
        Config.LAST_DATE = jsonObject.getString("lastdate");
        Config.RELEASE_DATE = jsonObject.getString("releasedate");
        Config.DIRECTOR = jsonObject.getString("director");
        Config.YOUTUBE_VIDEO_CODE = jsonObject.getString("video");
        Config.DURATION = jsonObject.getString("duration");
        Config.DESCRIPTION = jsonObject.getString("description");
        Log.e("Movie_name", Config.MOVIE_NAME);
        Log.e("banner", Config.BANNER);
        Log.e("Director", Config.DIRECTOR);
        Log.e("Movie_name", Config.YOUTUBE_VIDEO_CODE);
        Log.e("Movie_name", Config.DURATION);
        Log.e("Movie_name", Config.DESCRIPTION);

    }
}
