package com.duncan.komodomyversion;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import java.util.List;
import java.util.Map;


public class ProductsView extends Activity {

    private String jsonResult;
    private ArrayList<CableInfo> cList = new ArrayList<>();
    View v;
    RecyclerView recList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessWebService();
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
            ci.title = ci.getTitle();
         //   ci.type = ci.getType();
            ci.price = ci.getPrice();

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
            recList.addOnItemTouchListener(
                    new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            Intent i = new Intent(getApplicationContext(), ItemViewer.class);
                            i.putExtra("cableTitle", cList.get(position).getTitle());
                            i.putExtra("cableQuantity", cList.get(position).getQuantity());
                            i.putExtra("cableLength", "2 Metres");
                            i.putExtra("cableDesc", cList.get(position).getDescription());
                            i.putExtra("cableCost", cList.get(position).getPrice());
                            i.putExtra("imgURL", cList.get(position).getImgURL());

                            startActivity(i);
                        }
                    })
            );
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
                String description = jsonChildNode.optString("description");
                String imgURL = jsonChildNode.optString("fullurl");
                String quantity = jsonChildNode.optString("quantity");
                String outPut = name + "-" + number;
                cList.add(createCable(name, number, description, imgURL, quantity));
            }
        } catch (JSONException e) {
            Log.v("JSON", "ERROR");
        }
    }

    private ArrayList<CableInfo> createCable2 (String name, String number, String description, String imgURL, String quantity){
        ArrayList<CableInfo> cList = new ArrayList<>();
        CableInfo ci = new CableInfo();
        ci.setTitle(name);
        ci.setPrice(number);
        ci.setDescription(description);
        ci.setImgURL(imgURL);
        ci.setQuantity(quantity);
        return cList;

    }
    private CableInfo createCable(String name, String number, String description, String imgURL, String quantity){
        CableInfo ci = new CableInfo();
        ci.setTitle(name);
        ci.setPrice(number);
        ci.setDescription(description);
        ci.setImgURL(imgURL);
        ci.setQuantity(quantity);
        return ci;
    }


}