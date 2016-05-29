package com.example.yyergg.getimage;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button btConnect;
    private EditText etURL;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        this.btConnect = (Button) this.findViewById(R.id.btConnect);
        this.etURL = (EditText) this.findViewById(R.id.etURL);
        this.ivImage = (ImageView) this.findViewById(R.id.ivImage);
        final Handler handler = new Handler();
        final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        sleep(1000);
                        String url = etURL.getText().toString();
                        final Drawable d = LoadImageFromWebOperations(url);
                        sleep(2000);
                        handler.post(new Runnable() {
                            public void run() {
                                ivImage.setImageDrawable(d);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        btConnect.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!thread.isAlive()) {
                    thread.start();
                }
            }
        });
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "img");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
