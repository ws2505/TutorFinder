package com.example.product;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Utils.LoginUtils;

public class ViewDetailActivity extends Activity {
	 
    TextView txtName;
    TextView txtSubject;
    TextView txtDesc;
    TextView txtCreatedAt;
    Button btnSendMessage;
 
    String pid;
    String phone;
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
 
    // single product url
    private static final String url_product_detials = "http://115.159.214.78/testproduct/get_product_details.php";
 
    // url to update product
    private static final String url_update_product = "http://115.159.214.78/testproduct/update_product.php";
 
    // url to delete product
    private static final String url_delete_product = "http://115.159.214.78/testproduct/delete_product.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_PHONE="phone";
    private static final String TAG_SUBJECT="subject";


    //teacher details
    public String teacher_name=null;
    public String teacher_pid = null;
    public String teacher_description = null;
    public String teacher_subject = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdetail);
 
        // save button
        btnSendMessage = (Button) findViewById(R.id.btnSendMessage);
 
        // getting product details from intent
        Intent i = getIntent();
 
        // getting product id (pid) from intent
        pid = i.getStringExtra(TAG_PID);
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        phone = sp.getString("phone",null);
 
        // Getting complete product details in background thread
        new GetProductDetails().execute();
 
        // save button click event
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
//                String user_phone = null;
                SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
//                user_phone = sp.getString("phone",null);
                String status = sp.getString("status", null);
                //判断登陆状态

                // starting background task to update product
                if(LoginUtils.isLogin(status)){
                    new SendMessage().execute();
                }
                else{
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
                // starting background task to update product


            }
        });
 
        // Delete button click event
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // deleting product in background thread
//                new DeleteProduct().execute();
//            }
//        });
 
    }
 
    /**
     * Background Async Task to Get complete product details
     * */
    class GetProductDetails extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewDetailActivity.this);
            pDialog.setMessage("Loading details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {
 
            // updating UI from Background Thread


                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                        params2.add(new BasicNameValuePair("pid", pid));
 
                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_product_detials, "GET", params2);

                        // check your log for json response
                        Log.d("Single Product Details", json.toString());
 
                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received product details
                            JSONArray productObj = json
                                    .getJSONArray(TAG_PRODUCT); // JSON Array
 
                            // get first product object from JSON Array
                            JSONObject product = productObj.getJSONObject(0);
 
                            // product with this pid found
                            // Edit Text


                            teacher_pid = product.getString(TAG_PID);
                            teacher_name = product.getString(TAG_NAME);
                            teacher_description=product.getString(TAG_DESCRIPTION);
                            teacher_subject = product.getString(TAG_SUBJECT);


                        }else{
                            // product with pid not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            txtName =(TextView) findViewById(R.id.teacherName);
            txtSubject = (TextView) findViewById(R.id.teacherSubject);
            txtDesc = (TextView) findViewById(R.id.teacherDesc);

            // display product data in EditText
            txtName.setText(teacher_name);
            txtSubject.setText(teacher_subject);
            txtDesc.setText(teacher_description);
            // dismiss the dialog once got all details
            pDialog.dismiss();

        }
    }
 
    /**
     * Background Async Task to  Save product Details
     * */
    class SendMessage extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewDetailActivity.this);
//            pDialog.setMessage("Saving product ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {
 
            // getting updated data from EditTexts
//            String name = txtName.getText().toString();
//            String price = txtPrice.getText().toString();
//            String description = txtDesc.getText().toString();
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_PHONE, phone));
            params.add(new BasicNameValuePair(TAG_PID, pid));
//            params.add(new BasicNameValuePair(TAG_NAME, name));
//            params.add(new BasicNameValuePair(TAG_PRICE, price));
//            params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));
 
            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_product,
                    "GET", params);
 
            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            Toast.makeText(getApplicationContext(),"已发送",Toast.LENGTH_SHORT).show();
        }
    }
 
    /*****************************************************************
     * Background Async Task to Delete Product
     * */

//    class DeleteProduct extends AsyncTask<String, String, String> {
//
//        /**
//         * Before starting background thread Show Progress Dialog
//         * */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(ViewDetailActivity.this);
//            pDialog.setMessage("Deleting Product...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }
//
//        /**
//         * Deleting product
//         * */
//        protected String doInBackground(String... args) {
//
//            // Check for success tag
//            int success;
//            try {
//                // Building Parameters
//                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                params.add(new BasicNameValuePair("pid", pid));
//
//                // getting product details by making HTTP request
//                JSONObject json = jsonParser.makeHttpRequest(
//                        url_delete_product, "Get", params);
//
//                // check your log for json response
//                Log.d("Delete Product", json.toString());
//
//                // json success tag
//                success = json.getInt(TAG_SUCCESS);
//                if (success == 1) {
//                    // product successfully deleted
//                    // notify previous activity by sending code 100
//                    Intent i = getIntent();
//                    // send result code 100 to notify about product deletion
//                    setResult(100, i);
//                    finish();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        /**
//         * After completing background task Dismiss the progress dialog
//         * **/
//        protected void onPostExecute(String file_url) {
//            // dismiss the dialog once product deleted
//            pDialog.dismiss();
//
//        }
//
//    }

}