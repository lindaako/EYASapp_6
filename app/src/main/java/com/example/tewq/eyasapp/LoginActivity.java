package com.example.tewq.eyasapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

        Button bLogin, bRegister;
        EditText etUsername, etPassword;
        TextView etForgotPassword;
        EditText edittext;
        //private static String URL  ="https://youngashly.000webhostapp.com/login.php";
        private static String URL ="http://eyas.dx.am/login.php";
        private static String URL2 ="http://eyas.dx.am/emailcheck.php";
        private Snackbar snackbar;
        private ProgressDialog pd;
        private ProgressDialog em;
        private AlertDialog.Builder alert;



        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            final Context mcontext=this;
            etForgotPassword = findViewById(R.id.etForgotPassword);
            etUsername =  findViewById(R.id.etUsername);
            etPassword =  findViewById(R.id.etPassword);
            pd = new ProgressDialog(LoginActivity.this);
            em = new ProgressDialog(LoginActivity.this);
            bLogin = findViewById(R.id.bLogin);
            bRegister = findViewById(R.id.bRegister);


            etForgotPassword.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {

                    alert = new AlertDialog.Builder(mcontext);
                    edittext = new EditText(LoginActivity.this);
                    alert.setMessage("Enter the email address associated with your account to get a verification code");
                    alert.setTitle("Forgot password ");
                    alert.setView(edittext);
                    alert.setPositiveButton("Continue", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            emailcheck();

                        }
                    });

                    alert.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            dialog.cancel();
                        }
                    });

                    alert.show();


                }
            });

            bRegister.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {

                    Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                    LoginActivity.this.startActivity(registerIntent);
                }
            });

            bLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginRequest();
                }
            });


        }

        private void emailcheck ()
        {
            em.setMessage("Checking for existence . . .");
            em.show();
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

            String response2 = null;
            final String finalResponse2 = response2;
            StringRequest postRequest = new StringRequest(Request.Method.POST,URL2,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response2) {

                            em.hide();
                            showSnackbar(response2);

                            if(response2.equals("Email exists"))
                            {

                                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));

                            }
                            else{}

                        }

                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            // error
                            em.hide();
                            Log.d("ErrorResponse", finalResponse2);


                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("email", edittext.getText().toString());
                    return params;

                }
            };
            postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(postRequest);

        }


    private void loginRequest(){
            pd.setMessage("Signing In . . .");
            pd.show();
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

        String response = null;
        final String finalResponse = response;
            StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {

                            pd.hide();
                            showSnackbar(response);

                            if(response.equals("Login"))
                            {

                                startActivity(new Intent(getApplicationContext(), UserActivity.class));
                            }


                        }

                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            pd.hide();
                            Log.d("ErrorResponse", finalResponse);

                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("username", etUsername.getText().toString());
                    params.put("password", etPassword.getText().toString());
                    return params;
                }
            };
            postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(postRequest);



        }


        public void showSnackbar(String stringSnackbar){
            snackbar.make(findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                    .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        }


    public void clickexit(View v)
    {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}