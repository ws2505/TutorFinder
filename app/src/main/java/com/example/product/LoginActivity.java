package com.example.product;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

import Utils.LoginUtils;


public class LoginActivity extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputPhone;
    EditText inputPassword;
    EditText inputDesc;

    List<NameValuePair> params = new ArrayList<NameValuePair>();
    // url to create new product
    private static String url_login = "http://115.159.214.78/testproduct/login.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE="message";
    private static final String TAG_NAME="name";
    private static final String TAG_PHONE="phone";

    boolean loginSuccess = false;
    String LogMessage=null;
    String phone = null;
    String password = null;
    String name = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Edit Text
        inputPhone = (EditText) findViewById(R.id.inputPhone);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
//        inputDesc = (EditText) findViewById(R.id.inputDesc);

        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);
        //Back button
        Button btnBack = (Button)findViewById(R.id.back);
        //Register button
        Button btnRegister = (Button)findViewById(R.id.login_register);

        //Register button click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startRegister = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(startRegister);
                finish();
            }
        });



        //Back button click event
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //get the data from UI
                phone = inputPhone.getText().toString();
                password = inputPassword.getText().toString();
//                String description = inputDesc.getText().toString();

                // Building Parameters

                params.add(new BasicNameValuePair("phone", phone + ""));
                params.add(new BasicNameValuePair("password", password + ""));
//                params.add(new BasicNameValuePair("description", description + ""));

                // creating new product in background thread
                new CreateNewProduct().execute(url_login);

            }
        });
    }

    /**
     * Background Async Task to Create new product
     */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("登陆中..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {

//			String name = "Song";
//			String price = "123";
//    		String description = "beauty";
//		   System.out.print(params);
            // getting JSON Object
            // Note that create product url accepts POST method

            JSONObject json = jsonParser.makeHttpRequest(url_login,
                    "GET", params);


            // check log cat for response
            Log.d("Create Response", json.toString());



//			// check for success tag
            try {
//

                int success = json.getInt(TAG_SUCCESS);

                LogMessage = json.getString(TAG_MESSAGE);


                if (success == 1) {
                    // closing this screen
                    loginSuccess=true;
                    phone = json.getString(TAG_PHONE);
                    name = json.getString(TAG_NAME);
//                    new LoginUtils().login(phone);
   ///////////
                    /////////
                    //
                    ///////
                    ////////////
                    //记住登陆状态
                    SharedPreferences sp =getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("phone", phone);
                    editor.putString("status", "1");
                    editor.putString("name",name);
                    editor.commit();

                    finish();
                } else {

                }
                // failed to create product
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return LogMessage;

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String result) {
//			// dismiss the dialog once done
            pDialog.dismiss();
            Toast.makeText(getApplicationContext(), "" + result, Toast.LENGTH_SHORT).show();
//			super.onPostExecute(file_url);
//			Intent i = new Intent(getApplicationContext(),
//							AllProductsActivity.class);
//			startActivity(i);
//			finish();
        }
    }
}
