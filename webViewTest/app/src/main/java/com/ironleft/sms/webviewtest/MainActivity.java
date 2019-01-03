package com.ironleft.sms.webviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView =(WebView) findViewById(R.id._webView);
        WebView.setWebContentsDebuggingEnabled(true);
        //webView.loadUrl("http://pooq.co.kr/");
        Button btn =(Button) findViewById(R.id._btn);
        btn.setOnClickListener(this);
    }

    public void onClick(View arg)
    {

        webView.loadUrl("http://m.pooq.co.kr/");
    }
}
