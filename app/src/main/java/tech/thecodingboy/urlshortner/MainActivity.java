package tech.thecodingboy.urlshortner;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    EditText edtUrl;
    Button btnShortURL;
    ListView listView;
    String[] listitems={};
    String shortenurl;
    ArrayAdapter  <String >arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUrl = (EditText) findViewById(R.id.url);
        btnShortURL = (Button) findViewById(R.id.shorturl);
        listView=(ListView)findViewById(R.id.listview);


        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);

        btnShortURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetURL().execute();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("shortenurl:",shortenurl);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Link coppied succesfully", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private class GetURL extends AsyncTask<String, Void, String > {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... args) {

            String API="https://api.shorte.st/s/c97fca121088e9b527946eb8120dcb2d/";
            String url=API+edtUrl.getText().toString();
            String res= HttpRequest.excuteGet(url);
            return  res;


          /*  String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=landon&units=metric&appid=8118ed6ee68db2debfaaa5a44c832918");
            return response;*/
        }

        protected  void onPostExecute(String result) {
            try{

                JSONObject jsonObject=new JSONObject(result);
                shortenurl=jsonObject.getString("shortenedUrl");
                //Toast.makeText(MainActivity.this, "shorte URL:"+sh, Toast.LENGTH_SHORT).show();
                arrayAdapter.add(edtUrl.getText().toString());

            }catch (Exception e)
            {
                Toast.makeText(MainActivity.this, "Ex:"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}







