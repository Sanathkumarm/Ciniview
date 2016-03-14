package sk.com.ciniv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    MaterialDialog mMaterialDialog;
    Button bookShow;
    TextView movieTitle, lastShow, director, releaseDate, duration, description;
    // YouTube player view
    private YouTubePlayerView youTubeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FontsOverride.applyFont(getApplicationContext(),
                findViewById(R.id.activity_main_root), "fonts/font.ttf");
        FontsOverride.applyFont(getApplicationContext(),
                findViewById(android.R.id.list), "fonts/font.ttf");
        movieTitle = (TextView) findViewById(R.id.movie_title);
        lastShow = (TextView) findViewById(R.id.last_show);
        director = (TextView) findViewById(R.id.director);
        releaseDate = (TextView) findViewById(R.id.release_date);
        duration = (TextView) findViewById(R.id.duration);
        description = (TextView) findViewById(R.id.description);

        movieTitle.setText(Config.MOVIE_NAME);
//        lastShow.setText(Config.LAST_DATE);
        director.setText(Config.DIRECTOR);
        releaseDate.setText(Config.RELEASE_DATE);
        duration.setText(Config.DURATION);
        description.setText(Config.DESCRIPTION);

        // SET LAST SHOW
        String[] lastdate = Config.LAST_DATE.split("-");
        String day = lastdate[2];
        String month = "";
        switch (lastdate[1]) {
            case "01":
                month = "Jan";
                break; //optional

            case "02":
                month = "Feb";
                break; //optional

            case "03":
                month = "Mar";
                break; //optional

            case "04":
                month = "April";
                break; //optional

            case "05":
                month = "May";
                break; //optional

            case "06":
                month = "June";
                break; //optional

            case "07":
                month = "July";
                break; //optional

            case "08":
                month = "Aug";
                break; //optional

            case "09":
                month = "Sept";
                break; //optional

            case "10":
                month = "Oct";
                break; //optional

            case "11":
                month = "Nov";
                break; //optional

            case "12":
                month = "Dec";
                break; //optional

            default:
        }

        lastShow.setText(day + " " + month);

        mMaterialDialog = new MaterialDialog(this)

                .setContentView(R.layout.dialog_book_show)

                .setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();

                    }
                });


        bookShow = (Button) findViewById(R.id.book_show);
        bookShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookShowActivity.class);
                startActivity(intent);
            }
        });
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        // Initializing video player with developer key
        youTubeView.initialize(Config.DEVELOPER_KEY, this);


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
//            String errorMessage = String.format(
//                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.cueVideo(Config.YOUTUBE_VIDEO_CODE);

            // Hiding player controls
//            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }


}
