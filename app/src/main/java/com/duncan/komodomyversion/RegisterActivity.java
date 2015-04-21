package com.duncan.komodomyversion;

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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;


public class RegisterActivity extends ActionBarActivity {

    private EditText usernameField, firstnameField, surnameField, emailField, passwordField, addressField, postcodeField, townField, confPassword;
    private RegisterTask register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        usernameField = (EditText) findViewById(R.id.username);
        firstnameField = (EditText) findViewById(R.id.firstname);
        surnameField = (EditText) findViewById(R.id.surname);
        emailField = (EditText) findViewById(R.id.email);
        addressField = (EditText) findViewById(R.id.address);
        postcodeField = (EditText) findViewById(R.id.postcode);
        townField = (EditText) findViewById(R.id.town);
        passwordField = (EditText) findViewById(R.id.password);
        confPassword = (EditText) findViewById(R.id.confirm_password);



        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                String firstname = firstnameField.getText().toString();
                String surname = surnameField.getText().toString();
                String email = emailField.getText().toString();
                String address = addressField.getText().toString();
                String postcode = postcodeField.getText().toString();
                String town = townField.getText().toString();
                String password = passwordField.getText().toString();
                String confirmPassword = confPassword.getText().toString();
                register = new RegisterTask(RegisterActivity.this);
                register.execute(username, firstname, surname, email, address, postcode, town, password, confirmPassword);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_activity, menu);
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

class RegisterTask extends AsyncTask<String, String, String> {

    private RegisterActivity activity;

    public RegisterTask(RegisterActivity a) {
        this.activity = a;
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            String username =  arg0[0];
            String firstname =  arg0[1];
            String surname = arg0[2];
            String email =  arg0[3];
            String address =  arg0[4];
            String postcode =  arg0[5];
            String town = arg0[6];
            String password =  arg0[7];
            String confpassword =  arg0[8];

            for(String s : arg0){
                if(s.equals("")){
                    return "Please enter a value in all fields";
                }
            }
            if(!password.equals(confpassword)){
                return "Passwords don't match";
            }
            String link = "http://alihassan.co/register.php?username="+ username +"&firstname="
                    + firstname +"&surname="+ surname +"&email="+ email+"&password="
                    + password+"&address="+ address+"&postcode="+ postcode +"&town="+ town;
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
            return sb.toString();
        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("RegisterResult", result);
        Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
    }
}
