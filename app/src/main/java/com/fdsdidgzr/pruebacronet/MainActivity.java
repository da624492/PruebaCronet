package com.fdsdidgzr.pruebacronet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.net.CronetProviderInstaller;

import org.chromium.net.CronetEngine;
import org.chromium.net.UrlRequest;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Carga la libreria de google play
        Task t = CronetProviderInstaller.installProvider(this);
        t.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Log.i("general", "exito");
                } else {
                    Exception exception = task.getException();
                    Log.i("general", exception.getMessage());
                }
            }
        });

        CronetEngine.Builder myBuilder = new CronetEngine.Builder(this);
        CronetEngine cronetEngine = myBuilder.build();
        Executor executor = Executors.newSingleThreadExecutor();
        PruebaURLRequestCallback obsURLReq = new PruebaURLRequestCallback();
        String url = "https://www.googleapis.com/drive/v3/files?corpora=user&key=AIzaSyDtdWNoO-aUA74etUjXSVxXFkXhNten8JY";
        url = "https://www.example.com";

        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(url, obsURLReq, executor);
        UrlRequest request = requestBuilder.build();
        request.start();
    }
}