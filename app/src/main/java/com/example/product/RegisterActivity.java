package com.example.product;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputPhone;
    EditText inputPassword;
    EditText inputPasswordConfirm;
    EditText inputDesc;

    List<NameValuePair> params = new ArrayList<NameValuePair>();
    // url to create new product
    private static String url_create_product = "http://115.159.214.78/testproduct/register.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE="message";

    boolean registerSuccess = false;
    String LogMessage=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregist_layout);

        // Edit Text
        inputName = (EditText) findViewById(R.id.inputName);
        inputPhone = (EditText) findViewById(R.id.inputPhone);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
//        inputDesc = (EditText) findViewById(R.id.inputDesc);
        inputPasswordConfirm=(EditText)findViewById(R.id.inputPassword_confirm);
        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnStudentRegister);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //get the data from UI
                String name = inputName.getText().toString();
                String phone = inputPhone.getText().toString();
                String password = inputPassword.getText().toString();
                String passwordConfirm=inputPasswordConfirm.getText().toString();
//                String description = inputDesc.getText().toString();

                //Test the correctness

                if(Utils.StringUtils.isBlank(name)){
                    Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Utils.StringUtils.isBlank(password)){
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Utils.StringUtils.isBlank(passwordConfirm)){
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(passwordConfirm)){
                    Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }

                // Building Parameters

                params.add(new BasicNameValuePair("name", name + ""));
                params.add(new BasicNameValuePair("phone", phone + ""));
                params.add(new BasicNameValuePair("password", password + ""));
//                params.add(new BasicNameValuePair("description", description + ""));

                // creating new product in background thread
                new CreateNewProduct().execute(url_create_product);

            }
        });

        Button btnBack=(Button)findViewById(R.id.back);
        //Back button click event
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("注册中..");
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



//		    System.out.print(params);
            // getting JSON Object
            // Note that create product url accepts POST method

            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "GET", params);


            // check log cat for response
            Log.d("Create Response", json.toString());



//			// check for success tag
            try {
//

                int success = json.getInt(TAG_SUCCESS);

				LogMessage = json.getString(TAG_MESSAGE);

                if (success == 1) {
                    registerSuccess = true;
                    // closing this screen
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
