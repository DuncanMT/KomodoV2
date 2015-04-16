package com.duncan.komodomyversion;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class ProductsView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_view);
        View v = findViewById(R.id.cardList);
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        registerForContextMenu(recList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        HomeViewAdapter ca = new HomeViewAdapter(createList(10));
        recList.setAdapter(ca);
        registerForContextMenu(v);
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
        for (int i=1; i <= size; i++) {
            CableInfo ci = new CableInfo();
            ci.title = CableInfo.TITLE_PREFIX + i;
            ci.type = CableInfo.TYPE_PREFIX + i;
            ci.price = CableInfo.PRICE_PREFIX + i;

            result.add(ci);

        }

        return result;
    }
}
