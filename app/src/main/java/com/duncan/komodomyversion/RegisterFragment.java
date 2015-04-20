package com.duncan.komodomyversion;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText usernameField, firstnameField, surnameField, emailField, passwordField, addressField, postcodeField, townField;
    private TextView Status, role;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        Button button = (Button) view.findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Status = (TextView) getView().findViewById(R.id.Status);
                role = (TextView) getView().findViewById(R.id.role);

                usernameField = (EditText) getView().findViewById(R.id.username);
                firstnameField = (EditText) getView().findViewById(R.id.firstname);
                surnameField = (EditText) getView().findViewById(R.id.surname);
                emailField = (EditText) getView().findViewById(R.id.email);
                passwordField = (EditText) getView().findViewById(R.id.password);
                addressField = (EditText) getView().findViewById(R.id.address);
                postcodeField = (EditText) getView().findViewById(R.id.postcode);
                townField = (EditText) getView().findViewById(R.id.town);

                String username = usernameField.getText().toString();
                String firstname = firstnameField.getText().toString();
                String surname = surnameField.getText().toString();
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                String address = addressField.getText().toString();
                String postcode = postcodeField.getText().toString();
                String town = townField.getText().toString();
                new RegisterActivity(getActivity(), Status, role).execute(username, firstname, surname, email, password, address, postcode, town);

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

class RegisterActivity extends AsyncTask<String, Void, String> {

    private TextView statusField, roleField;
    private Context context;

    public RegisterActivity(Context context, TextView statusField, TextView roleField) {
        this.context = context;
        this.statusField = statusField;
        this.roleField = roleField;
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            String username =  arg0[0];
            String firstname =  arg0[1];
            String surname = arg0[2];
            String email =  arg0[3];
            String password =  arg0[4];
            String address =  arg0[5];
            String postcode =  arg0[6];
            String town = arg0[7];
            String link = "http://alihassan.co/register.php?username="+ username +"&firstname="
                    + firstname +"&surname="+ surname +"&email="+ email+"&password="
                    + password+"&address="+ address+"&postcode="+ postcode +"&town="+ town;
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            in.close();
            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {

    }
}
