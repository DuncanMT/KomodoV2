package com.duncan.komodomyversion;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;


public class LoginActivity extends ActionBarActivity {

    private EditText emailField, passwordField;
    private LoginTask login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                login = new LoginTask(LoginActivity.this);
                login.execute(email, password);
            }
        });

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(mainIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_activity, menu);
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
}
class LoginTask extends AsyncTask<String, String, String> {

    private LoginActivity activity;
    private String usernameSave= "username";

    public LoginTask(LoginActivity a) {
        this.activity = a;
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            String email = arg0[0];
            String password = arg0[1];
            for(String s : arg0){
                if(s.equals("")){
                    return "Please enter a value in all fields";
                }
            }
            String link = "http://alihassan.co/login.php?email=" + email + "&password=" + password;
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder("");
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            in.close();
            if(sb.toString().equals(""))
            {
                return "Login Incorrect";
            }else{
                return "Login Successful";
            }
        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
        if(result.equals("Login Successful")){
            Log.d("JSON", usernameSave);
            LoginState.setUserName(activity, usernameSave );
            Intent mainIntent = new Intent(activity, ProductsView.class);
            activity.startActivity(mainIntent);
            activity.finish();
        }
    }
}

