package com.duncan.komodomyversion;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductsView extends Activity {
    private String jsonResult;
    private ArrayList<CableInfo> cList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessWebService();
        Log.v("CLIST: ", ""+cList.size());
        Log.v("CLIST: ", ""+jsonResult);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private List<CableInfo> createList(int size) {

        List<CableInfo> result = new ArrayList<CableInfo>();
        for (int i=0; i <= size-1; i++) {
            CableInfo ci = new CableInfo();
            ci = cList.get(i);
            ci.title = CableInfo.TITLE_PREFIX + ci.getTitle();
            ci.type = CableInfo.TYPE_PREFIX + i;
            ci.price = CableInfo.PRICE_PREFIX + ci.getPrice();

            result.add(ci);

        }

        return result;
    }
    // Async Task to access the web
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
            }

            catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            }

            catch (IOException e) {
                // e.printStackTrace();
                Log.v("JSON", "ERROR");
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            ListDrwaer();

            setContentView(R.layout.activity_products_view);

            View v = findViewById(R.id.cardList);
            RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
            recList.setHasFixedSize(true);
            registerForContextMenu(recList);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recList.setLayoutManager(llm);
            HomeViewAdapter ca = new HomeViewAdapter(createList(cList.size()));
            recList.setAdapter(ca);
            registerForContextMenu(v);
        }
    }// end async task

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[] { "http://alihassan.co/getitem.php?" });
    }

    // build hash set for list view
    public void ListDrwaer() {
        List<Map<String, String>> cableList = new ArrayList<Map<String, String>>();
       // ArrayList<CableInfo> cList = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("product");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String name = jsonChildNode.optString("name");
                String number = jsonChildNode.optString("cost");
                String outPut = name + "-" + number;
//                CableInfo ci = new CableInfo();
//                ci.setTitle(name);
//                ci.setPrice(number);
//                ci.setType("CABLE");
//                ci.price = number;
//                cList.add(ci);
                cableList.add(createCable("cable", outPut));
                cList.add(createCable3(name, number));
            }
        } catch (JSONException e) {
            Log.v("JSON", "ERROR");
        }


        Log.v("HELLLLO", cableList.get(0).toString());
        Log.v("HELLLLO", cableList.get(1).toString());
        Log.v("ARRAYLIST: ", cList.get(0).title);
    }

    private ArrayList<CableInfo> createCable2 (String name, String number){
        ArrayList<CableInfo> cList = new ArrayList<>();
        CableInfo ci = new CableInfo();
        ci.setTitle(name);
        ci.setPrice(number);
        ci.setType("HDMI");
        Log.v("TEST: ", ci.title);
        return cList;

    }
    private CableInfo createCable3 (String name, String number){
        CableInfo ci = new CableInfo();
        ci.setTitle(name);
        ci.setPrice(number);
        ci.setType("HDMI");
        Log.v("TEST: ", ci.title);
        return ci;
    }

    private HashMap<String, String> createCable(String name, String number) {
        HashMap<String, String> employeeNameNo = new HashMap<String, String>();
        employeeNameNo.put(name, number);
        ArrayList<CableInfo> cableList = new ArrayList<>();
        CableInfo ci = new CableInfo();
        ci.title = name;
        ci.price = number;
        cableList.add(ci);
        return employeeNameNo;
    }
}