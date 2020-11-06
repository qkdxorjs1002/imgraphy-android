package com.teamig.imgraphy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.teamig.imgraphy.service.Imgraphy;

public class SplashActivity extends AppCompatActivity {

    private Imgraphy imgraphy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        final String userID = sharedPreferences.getString("userID", null);

        if (userID == null) {
            setContentView(R.layout.activity_splash);
            onInit();
        } else {
            exitSplash();
        }
    }

    private void onInit() {
        imgraphy = new Imgraphy();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("서비스 이용 약관");
        builder.setMessage("<서비스 이용 약관에 대한 내용>");

        builder.setPositiveButton("동의", (dialog, which) -> {
            Toast.makeText(this,"서비스 이용 약관에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
            imgraphy.generateID(true).observe(this, result -> {
                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).edit();
                editor.putString("userID", result.log);
                editor.apply();

                exitSplash();
            });
        });

        builder.setNegativeButton("거절", (dialog, which) -> {
            Toast.makeText(this,"서비스 이용 약관에 거절하셨습니다.", Toast.LENGTH_SHORT).show();
            closeApp();
        });

        builder.show();
    }

    private void exitSplash() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    private void closeApp() {
        moveTaskToBack(true);
        finishAndRemoveTask();
        System.exit(0);
    }
}