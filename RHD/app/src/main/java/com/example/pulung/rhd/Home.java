package com.example.pulung.rhd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {

    ImageButton scan;
    String contents,plant_id, format;
    Context Context;
    TextView TXTNamaView,TXTBSUView;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    String UsernamePref="";

    ProgressDialog pDialog;
    String URLCekToken;
    String data,makassar_name,indonesia_name,latin_name,category,seed,collection_code,year,conservation,quantity,area,description,story,benefit,descriptor;
    // Data Array JSONArray
    JSONArray DatafromJson = null;

    private static final String Json_data              ="data";
    private static final String Json_makassar_name     ="makassar_name",
                                Json_indonesia_name    ="indonesia_name",
                                Json_latin_name        ="latin_name",
                                Json_category          ="category",
                                Json_seed              ="seed",
                                Json_collection_code   ="collection_code",
                                Json_year              ="year",
                                Json_conservation      ="conservation",
                                Json_quantity          ="quantity",
                                Json_area              ="area",
                                Json_description       ="description",
                                Json_story             ="story",
                                Json_benefit           ="benefit",
                                Json_descriptor        ="descriptor";
    SharedPreferences sharePref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        scan =(ImageButton) findViewById(R.id.TombolScan);


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException anfe) {
                    showDialog(Home.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                }
            }
        });
    }
    private static android.app.AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(act);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });

        dialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return dialog.show();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                contents = intent.getStringExtra("SCAN_RESULT");
                format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                plant_id = contents;
                URLCekToken = getString(R.string.main_web)+"plant_data.php?plant_id="+plant_id;
                new ShowData().execute();

                Toast.makeText(Home.this,URLCekToken,Toast.LENGTH_LONG).show();

            }
        }
    }

    private class ShowData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Home.this);
            pDialog.setMessage("Checking Data ...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            data = new Fungsi().getString(Home.this, URLCekToken);

            Log.d("Response: ", "> " +data);
            if (data != null) {
                try {
                    JSONObject jsonObj = new JSONObject(data);

                    // Getting JSON Array node
                    DatafromJson = jsonObj.getJSONArray(Json_data);

                    // looping through All Contacts
                    for (int i = 0; i < DatafromJson.length(); i++) {
                        JSONObject c = DatafromJson.getJSONObject(i);

                        // mengambil data json
                        makassar_name   = c.getString(Json_makassar_name);
                        indonesia_name  = c.getString(Json_indonesia_name);
                        latin_name      = c.getString(Json_latin_name);
                        category        = c.getString(Json_category);
                        seed            = c.getString(Json_seed);
                        collection_code = c.getString(Json_collection_code);
                        year            = c.getString(Json_year);
                        conservation    = c.getString(Json_conservation);
                        quantity        = c.getString(Json_quantity);
                        area            = c.getString(Json_area);
                        description     = c.getString(Json_description);
                        story           = c.getString(Json_story);
                        benefit         = c.getString(Json_benefit);
                        descriptor      = c.getString(Json_descriptor);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;

        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.dismiss();


                Intent i = new Intent(Home.this, Plant_data.class);
                i.putExtra("makassar_name", makassar_name);//kirim
                i.putExtra("indonesia_name", indonesia_name);//kirim
                i.putExtra("latin_name", latin_name);//kirim
                i.putExtra("category", category);//kirim
                i.putExtra("seed", seed);//kirim
                i.putExtra("collection_code", collection_code);//kirim
                i.putExtra("year", year);//kirim
                i.putExtra("conservation", conservation);//kirim
                i.putExtra("quantity", quantity);//kirim
                i.putExtra("area", area);//kirim
                i.putExtra("description", description);//kirim
                i.putExtra("story", story);//kirim
                i.putExtra("benefit", benefit);//kirim
                i.putExtra("descriptor", descriptor);//kirim
                //i.putExtra("username", UsernamePref);
                startActivity(i);
                //Toast.makeText(Main.this, URLCekToken, Toast.LENGTH_SHORT).show();




        }

    }
}
