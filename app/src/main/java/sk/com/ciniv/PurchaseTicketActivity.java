package sk.com.ciniv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class PurchaseTicketActivity extends AppCompatActivity {
    LinearLayout ticketRoot;
    TextView ticket, cDate;

    //MaterialDialog mMaterialDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_ticket);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ticket = (TextView) findViewById(R.id.ticket);
        ticketRoot = (LinearLayout) findViewById(R.id.ticket_root);
        cDate = (TextView) findViewById(R.id.cdate);
//        ticketRoot.setVisibility(LinearLayout.GONE);
       /* mMaterialDialog = new MaterialDialog.Builder(this)
                .title("Please Wait")
                .content("Purchasing Ticket. Please Wait !")
                .progress(true, 0).show();*/
        String url = "http://smohith.com/cineback/prchase-ticket.php";
        final JSONObject jsonObject = new JSONObject();
        try {
            //Create a JSON object
            jsonObject.put("Operator", Config.OPERATOR);
            jsonObject.put("STBNumber", Config.STBNUMBER);
            jsonObject.put("MobileNumber", Config.MOBILENUMBER);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //mMaterialDialog.dismiss();
//                ticketRoot.setVisibility(LinearLayout.VISIBLE);
                try {
                    JSONArray responseArray = response.getJSONArray("result");
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject responseObject = responseArray.getJSONObject(i);
                        String ticketNumber = responseObject.get("ticketcode").toString();
                        String createdDate = responseObject.get("cdate").toString();
                        Toast.makeText(getApplicationContext(), ticketNumber, Toast.LENGTH_LONG).show();
                        ticket.setText(ticketNumber);
                        cDate.setText(createdDate);
                        File file = saveBitMap(getApplicationContext(), ticketRoot);    //which view you want to pass that view as parameter
                        if (file != null) {
                            Log.i("TAG", "Drawing saved to the gallery!");
                        } else {
                            Log.i("TAG", "Oops! Image could not be saved.");
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "JSON Exception", Toast.LENGTH_LONG).show();
                    //    mMaterialDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                //     mMaterialDialog.dismiss();
            }
        }
        );
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                10,
                2));
        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    private File saveBitMap(Context context, View drawView) {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Cineview");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if (!isDirectoryCreated)
                Log.i("ATG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap = getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery(context, pictureFile.getAbsolutePath());
        return pictureFile;
    }

    //create bitmap from view and returns it
    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
