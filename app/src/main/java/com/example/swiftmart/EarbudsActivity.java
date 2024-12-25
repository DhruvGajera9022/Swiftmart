package com.example.swiftmart;

import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class EarbudsActivity extends AppCompatActivity {
    TextView earbudstext, earbudstext1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earbuds);

        earbudstext = findViewById(R.id.earbudstext);
        earbudstext1 = findViewById(R.id.earbudstext1);

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
    }
}
