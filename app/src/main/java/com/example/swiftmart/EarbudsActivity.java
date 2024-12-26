package com.example.swiftmart;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class EarbudsActivity extends AppCompatActivity {
    TextView earbudstext, earbudstext1;
    LinearLayout earbudsdetailes;
    ImageView backearbuds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earbuds);

        earbudstext = findViewById(R.id.earbudstext);
        earbudstext1 = findViewById(R.id.earbudstext1);
        earbudsdetailes = findViewById(R.id.earbudsdetailes);
        backearbuds = findViewById(R.id.backearbuds);

        // Strike-through text
        earbudstext.setPaintFlags(earbudstext.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        earbudstext1.setPaintFlags(earbudstext1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        // VideoView setup
        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.v; // Replace with your video file name
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // Play video automatically without seek bar
        videoView.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.setLooping(true); // Optional: Loop the video
            videoView.start();
        });

        // Disable media controller (seek bar)
        videoView.setMediaController(null);

        earbudsdetailes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EarbudsActivity.this, EarbusDetailsActivity.class);
                startActivity(i);
            }
        });

        backearbuds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
