package com.hajalibayram.copy_top10downloadapps14_09_1;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    private String mFileContents;
    private Button btn;
    private TextView text;

    //Toast yaratmaq ucun
    private static Context context;
    public static Context  getContext(){
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this.getApplicationContext();

        /** IMPORTANT */
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topMovies/xml");


        text = (TextView)findViewById(R.id.textView);
        btn = (Button)findViewById(R.id.button);
        /** END IMPORTANT */


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               text.setText(mFileContents);

                ParseClassInstance parseClassInstance = new ParseClassInstance(mFileContents);
                parseClassInstance.process();

                /*burdan sonrasi materialin listde cixarilmasi ucundur*/
                /*
                 *ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<>(
                 *MainActivity.this, R.layout.list_item, parseApplications.getApplications());
                 *listApps.setAdapter(arrayAdapter);
                 */

            }
        });

        /***/


    }
    /**
     * TEMPLATE for downloading and saving xml file in mFileContents
     * IMPORTANT
     */

    private class DownloadData extends AsyncTask<String,Void,String> {


        private String downloadXmlFile(String urlPath) { // onClick-de download ucun olan method;
            StringBuilder tempBuffer = new StringBuilder(); // yuklenen xmlleri save etmek ucun muveqqeti buffer yaradilir. StringBuilder bunun ucun ela vasitedir.
            try {
                URL url = new URL(urlPath); // urlPathi URL obyektine cevirir
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//url-i http obyektine cevirir
                //int response = httpURLConnection.getResponseCode(); Extra bir sheydi ???????????Response code alir
                InputStream inputStream = httpURLConnection.getInputStream();//??????????????http-i inputStreame cevirir,
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream); //???????????inputStreamReader bir nov adaptor rolunu oynayir

            /* TEMPLATE */
                int charRead; // Datani char tipli variable-a yazmaq ucun.
                char[] inputBuffer = new char[500]; //kifayet qeder olmasi ucun 500yaziriq. Deyishe biler.
                while (true) {
                    charRead = inputStreamReader.read(inputBuffer);
                    if (charRead <= 0)
                        break;
                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charRead));
//commment test github
                }
            /*END*/
                return tempBuffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected String doInBackground(String... params) {
            mFileContents = downloadXmlFile(params[0]);//yuklenen ve tempBuffer-da olan datani esas mFilecontentse yazdirir
            if(mFileContents==null)//boshdusa, tempBufferda da hec ne yoxdusa
                    //hec ne bash vermir; ERROR
                    Log.d("ERROR","tempBuffer boshdu");

            return mFileContents;// diger-normal hallarda mFileContents qaytarir
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("DownloadData", "Result was: " + result);
        }
        /** END TEMPLATE IMPORTANT */

    }
}
