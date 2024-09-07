package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;

public class toolbar {

        public static void navigateToMainActivity(Context context) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }


    public static void showOptionsDialog(Context context, final DialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Menu")
                .setItems(new CharSequence[]{"Profile", "Setting"}, (dialog, which) -> {
                    if (callback != null) {
                        callback.onOptionSelected(which);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void profile(Context context) {
        Intent intent = new Intent(context, menu_profile.class);
        context.startActivity(intent);
    }

    public static void setting(Context context) {
        Intent intent = new Intent(context, menu_setting.class);
        context.startActivity(intent);
    }

    public interface DialogCallback {
        void onOptionSelected(int which);
    }
}
