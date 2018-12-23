package com.ashugo.wikitap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity {

    String[] fruits = {"Apple","Ape", "And",  "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};
    private WebView webView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

webView1 = (WebView)findViewById(R.id.webView1);
        webView1 = (WebView) findViewById(R.id.webView1);
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        webView1.loadUrl(
                "https://www.linkedin.com/in/ashutosh-gourav-a3347494/");


        //Creating the instance of ArrayAdapter containing list of fruit names
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                (this, android.R.layout.select_dialog_item, fruits);
        //Getting the instance of AutoCompleteTextView
        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.main_auto_Complete_TV);
        actv.setThreshold(1);//will start working from first character
//        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);

        final AutocompleteAdapter adapter = new AutocompleteAdapter(this,android.R.layout.simple_dropdown_item_1line);
        actv.setAdapter(adapter);
        //when autocomplete is clicked
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String countryName = adapter.getItem(position);
                String redirectUrl = adapter.getmCountry().get(0).getSuggestionLinks().get(position).replace("\"", "");
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(redirectUrl));
            actv.setText(countryName);
            webView1.loadUrl(redirectUrl);
//            startActivity(intent);
            }
        });

    }


}
