package com.theleafapps.pro.udacityimplicitintentsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button openWebPageButton;
    Button openMapsButton;
    Button shareTextContentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openWebPageButton = (Button) findViewById(R.id.open_url_btn);
        openMapsButton = (Button) findViewById(R.id.open_map_btn);
        shareTextContentButton = (Button) findViewById(R.id.share_text_content_btn);

        openWebPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlAsString = "http://www.udacity.com";
                openWebPage(urlAsString);
            }
        });

        openMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressString = "1600 Amphitheatre Parkway, CA";

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("geo")
                        .path("0,0")
                        .query(addressString);
                Uri addressUri = builder.build();

                showMap(addressUri);
            }
        });

        shareTextContentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Create the String that you want to share */
                String textThatYouWantToShare =
                        "Sharing the coolest thing I've learned so far. You should " +
                                "check out Udacity and Google's Android Nanodegree!";

                /* Send that text to our method that will share it. */
                shareText(textThatYouWantToShare);
            }
        });
    }


    private void openWebPage(String url) {
        /*
         * We wanted to demonstrate the Uri.parse method because its usage occurs frequently. You
         * could have just as easily passed in a Uri as the parameter of this method.
         */
        Uri webpage = Uri.parse(url);

        /*
         * Here, we create the Intent with the action of ACTION_VIEW. This action allows the user
         * to view particular content. In this case, our webpage URL.
         */
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        /*
         * This is a check we perform with every implicit Intent that we launch. In some cases,
         * the device where this code is running might not have an Activity to perform the action
         * with the data we've specified. Without this check, in those cases your app would crash.
         */
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void showMap(Uri geoLocation) {
        /*
         * Again, we create an Intent with the action, ACTION_VIEW because we want to VIEW the
         * contents of this Uri.
         */
        Intent intent = new Intent(Intent.ACTION_VIEW);

        /*
         * Using setData to set the Uri of this Intent has the exact same affect as passing it in
         * the Intent's constructor. This is simply an alternate way of doing this.
         */
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void shareText(String textToShare) {
        /*
         * You can think of MIME types similarly to file extensions. They aren't the exact same,
         * but MIME types help a computer determine which applications can open which content. For
         * example, if you double click on a .pdf file, you will be presented with a list of
         * programs that can open PDFs. Specifying the MIME type as text/plain has a similar affect
         * on our implicit Intent. With text/plain specified, all apps that can handle text content
         * in some way will be offered when we call startActivity on this particular Intent.
         */
        String mimeType = "text/plain";

        /* This is just the title of the window that will pop up when we call startActivity */
        String title = "Learning How to Share";

        /* ShareCompat.IntentBuilder provides a fluent API for creating Intents */
        ShareCompat.IntentBuilder
                /* The from method specifies the Context from which this share is coming from */
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(textToShare)
                .startChooser();
    }
}
