package com.duncan.komodomyversion;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

    private EditText usernameField, firstnameField, surnameField, emailField, passwordField, addressField, postcodeField, townField, confPasswordField;
    private RegisterTask registerTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameField = (EditText)findViewById(R.id.username);
        firstnameField = (EditText)findViewById(R.id.firstname);
        surnameField = (EditText)findViewById(R.id.surname);
        emailField = (EditText)findViewById(R.id.email);
        passwordField = (EditText)findViewById(R.id.password);
        confPasswordField = (EditText)findViewById(R.id.confirm_password);
        addressField = (EditText)findViewById(R.id.address);
        postcodeField = (EditText)findViewById(R.id.postcode);
        townField = (EditText)findViewById(R.id.town);
        Button btnRegister = (Button)findViewById(R.id.register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                String firstname = firstnameField.getText().toString();
                String surname = surnameField.getText().toString();
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                String confPassword = confPasswordField.getText().toString();
                String address = addressField.getText().toString();
                String postcode = postcodeField.getText().toString();
                String town = townField.getText().toString();
                registerTask = new RegisterTask(RegisterActivity.this);
                registerTask.execute(username, firstname, surname, email, password, confPassword, address, postcode, town);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    RegisterActivity activity;

    public RegisterTask(RegisterActivity a) {
        this.activity = a;
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            String username = arg0[0];
            String firstname = arg0[1];
            String surname = arg0[2];
            String email = arg0[3];
            String password = arg0[4];
            String confPassword = arg0[5];
            String address = arg0[6];
            String postcode = arg0[7];
            String town = arg0[8];
            for (String s : arg0) {
                if(s.equals(""))
                    return "Please enter a value in all fields";
            }

            if(password.equals(confPassword)) {
                String link = "http://alihassan.co/register.php?username=" + username + "&firstname="
                        + firstname + "&surname=" + surname + "&email=" + email + "&password="
                        + password + "&address=" + address + "&postcode=" + postcode + "&town=" + town;
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
            }else{
                return "Passwords Don't Match";
            }
        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }

    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
    }
}

