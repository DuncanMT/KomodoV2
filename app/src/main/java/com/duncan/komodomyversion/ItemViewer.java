package com.duncan.komodomyversion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;


public class ItemViewer extends Activity {
    private static final String TAG = ItemViewer.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_viewer);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
        setFields();
    }

    private void setFields() {
        Intent i = getIntent();
        String cableType = i.getStringExtra("cableType");
        String cableLength = i.getStringExtra("cableLength");
        String cableDesc = i.getStringExtra("cableDesc");
        String cableCost = i.getStringExtra("cableCost");
        String cableTitle = i.getStringExtra("cableTitle");
//        i.putExtra("cableType", "HDMI CABLE");
//        i.putExtra("cableLength", "2 Metres");
//        i.putExtra("cableDesc", "2 Metre long high quality shielded HDMI cable");
//        i.putExtra("cableCost", "5.00");
        final TextView cableTitleView = (TextView)findViewById(R.id.cableTitleView);
        final TextView cableLengthView = (TextView)findViewById(R.id.lengthView);
        final TextView cableTypeView = (TextView)findViewById(R.id.typeView);
        // final TextView cableDescView = (TextView)findViewById(R.id.);
        final TextView cableCostView = (TextView)findViewById(R.id.costView);
        Log.v(TAG, "3");
        cableTitleView.setText(cableTitle);
        Log.v(TAG, "4");
        cableLengthView.setText(cableLength);
        Log.v(TAG, "5");
        cableCostView.setText("£" + cableCost);
        cableTypeView.setText(cableType);
        ImageView productImage = (ImageView)findViewById(R.id.imageView);
        Drawable d;
        d = LoadImageFromWebOperations("http://hometheaterreview.com/images/HDMIcable.jpg");
        productImage.setImageDrawable(d);
        productImage.setImageResource(R.drawable.hdmi);
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, url);
            return d;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_viewer, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }


    }
}

