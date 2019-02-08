package com.ourproject.mohankumardhakal.agroproject.AcitivityClasses;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ourproject.mohankumardhakal.agroproject.R;

public class News_activity extends AppCompatActivity {
    TextView doa,naturekhabar,newsagro,ncf,ain,fao;
    String url=null;
    WebView wv1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);


        wv1=(WebView)findViewById(R.id.web_view);
        wv1.setWebViewClient(new MyBrowser());
        doa=findViewById(R.id.doa);
        naturekhabar=findViewById(R.id.nature_khabar);
        newsagro=findViewById(R.id.news_agro);
        ncf=findViewById(R.id.ncf);
        ain=findViewById(R.id.ain);
        fao=findViewById(R.id.fao);



        doa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                url="http://www.doanepal.gov.np/";
                wv1.setVisibility(wv1.VISIBLE);
                Toast.makeText(News_activity.this, "The url is"+url, Toast.LENGTH_SHORT).show();
                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);


            }
        });

        naturekhabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url="http://naturekhabar.com/en/";
                wv1.setVisibility(wv1.VISIBLE);
                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);

            }
        });

        newsagro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url="http://www.newsagro.com/";
                wv1.setVisibility(wv1.VISIBLE);
                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);

            }
        });

        ncf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              url="https://cityfarmer.info/category/nepal/";
                wv1.setVisibility(wv1.VISIBLE);
                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);
            }
        });

        ain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url="http://www.nepalkrishi.com/";
                wv1.setVisibility(wv1.VISIBLE);
                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);
            }
        });

        fao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              url="http://www.fao.org/nepal/news/en/";
                wv1.setVisibility(wv1.VISIBLE);
                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);
            }
        });


   /* if(url!=null) {
        wv1.setVisibility(wv1.VISIBLE);
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(url);
    }*/

    }


    public class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv1.canGoBack()) {
            wv1.goBack();
            return true;
        }

        if ((keyCode == KeyEvent.KEYCODE_BACK) && !(wv1.canGoBack())&& (wv1.getVisibility()==wv1.VISIBLE))
        {
            wv1.setVisibility(wv1.INVISIBLE);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}




