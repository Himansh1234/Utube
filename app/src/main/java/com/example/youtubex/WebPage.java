package com.example.youtubex;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class WebPage extends AppCompatActivity {

    WebView webView;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);
        webView = findViewById(R.id.webView);

        back = findViewById(R.id.back);

        Bundle bundle = getIntent().getExtras();
        String channel = bundle.getString("URL");
        String Forclass = bundle.getString("ForClass");
        String videoid = bundle.getString("videoid");
        //The advantage of using webView is we can show the web in our app itself rather than going to a browser.
        //Also we need to give internet permission to our app in order to load page in our android app.

        TextView textView = findViewById(R.id.textView);
        textView.setText("Click Back After Subscribe/Like");


        String URL;
        if(Forclass.equals("ForSub")) {
            URL = "https://www.youtube.com/channel/" + channel.toString();
        }
        else{
            URL = "https://www.youtube.com/watch?v="+videoid.toString();
        }

        //the below code will help to load all the further searches in our app.
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(URL);

        //The above code will load the  page you have searched for, however when you click on the back button it will close the app
        //To avoid this we need override onBackPress method.


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack() == true){
            webView.goBack();
        }

        else if(webView.canGoBack() == false)
        {
            //here we can add and alert dialog box saying "Are you sure you want to leave"

            //the below line will close the app if no page to go back
            super.onBackPressed();
        }

    }
}