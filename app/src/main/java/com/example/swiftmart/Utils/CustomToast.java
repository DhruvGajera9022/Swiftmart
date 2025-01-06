package com.example.swiftmart.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swiftmart.R;

public class CustomToast {

    public static void showToast(Context context, int imageResource, String message) {
        // Inflate the custom toast layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.custom_toast, null);

        // Set the image and message for the toast
        ImageView toastImage = customView.findViewById(R.id.toast_image);
        TextView toastMessage = customView.findViewById(R.id.toast_text);

        toastImage.setImageResource(imageResource);
        toastMessage.setText(message);

        // Apply entry animation
        Animation slideIn = AnimationUtils.loadAnimation(context, R.anim.slide_in);
        customView.startAnimation(slideIn);

        // Create and show the custom toast
        Toast customToast = new Toast(context);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.setView(customView);
//        customToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
        customToast.show();

        // Schedule exit animation
        customView.postDelayed(() -> {
            Animation slideOut = AnimationUtils.loadAnimation(context, R.anim.slide_out);
            customView.startAnimation(slideOut);
        }, Toast.LENGTH_SHORT == Toast.LENGTH_SHORT ? 2000 : 3500);

    }
}