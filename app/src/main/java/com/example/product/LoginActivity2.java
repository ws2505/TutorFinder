package com.example.product;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity2 extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
//    EditText inputPhone;
//    EditText inputPassword;
    EditText inputDesc;
    TextView studentName;
    TextView studentPhone;

    List<NameValuePair> params = new ArrayList<NameValuePair>();
    // url to create new product
    private static String url_login = "http://115.159.214.78/testproduct/login.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE="message";

    boolean loginSuccess = false;
    String LogMessage=null;
    String phone = null;
    String password = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        // Edit Text
//        inputPhone = (EditText) findViewById(R.id.inputPhone);
//        inputPassword = (EditText) findViewById(R.id.inputPassword);

        studentName = (TextView) findViewById(R.id.student_name);
        studentPhone = (TextView)findViewById(R.id.student_phone);
//        inputDesc = (EditText) findViewById(R.id.inputDesc);

        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        studentName.setText("姓名：  "+sp.getString("name", null));
        studentPhone.setText("手机号："+sp.getString("phone",null));

        // 推出按钮
        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        //Back button
        Button btnBack = (Button)findViewById(R.id.back);
        //Register button
//        Button btnRegister = (Button)findViewById(R.id.login_register);

        //Register button click event
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent startRegister = new Intent(getApplicationContext(),RegisterActivity.class);
//                startActivity(startRegister);
//                finish();
//            }
//        });



        //Back button click event
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //推出登陆
        // button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sp =getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("status", "0");
                editor.commit();

                finish();
            }
        });
    }

    /**
     * Background Async Task to Create new product
     */
//    class CreateNewProduct extends AsyncTask<String, String, String> {
//
//        /**
//         * Before starting background thread Show Progress Dialog
//         */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(LoginActivity2.this);
//            pDialog.setMessage("登陆中..");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }
//
//        /**
//         * Creating product
//         */
//        protected String doInBackground(String... args) {
//
//
//
////			String name = "Song";
////			String price = "123";
////    		String description = "beauty";
//
//
//
////		    System.out.print(params);
//            // getting JSON Object
//            // Note that create product url accepts POST method
//
//            JSONObject json = jsonParser.makeHttpRequest(url_login,
//                    "GET", params);
//
//
//            // check log cat for response
//            Log.d("Create Response", json.toString());
//
//
//
////			// check for success tag
//            try {
////
//
//                int success = json.getInt(TAG_SUCCESS);
//
//                LogMessage = json.getString(TAG_MESSAGE);
//
//                if (success == 1) {
//                    // closing this screen
//                    loginSuccess=true;
//
////                    new LoginUtils().login(phone);
//   ///////////
//                    /////////
//                    //
//                    ///////
//
//
//
//
//                    ////////////
//                    //记住登陆状态
//                    SharedPreferences sp =getSharedPreferences("user", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString("phone", phone);
//                    editor.putString("status", "1");
//                    editor.commit();
//
//                    finish();
//                } else {
//
//                }
//                // failed to create product
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return LogMessage;
//
//        }
//
//        /**
//         * After completing background task Dismiss the progress dialog
//         **/
//        protected void onPostExecute(String result) {
////			// dismiss the dialog once done
//            pDialog.dismiss();
//            Toast.makeText(getApplicationContext(), "" + result, Toast.LENGTH_SHORT).show();
////			super.onPostExecute(file_url);
////			Intent i = new Intent(getApplicationContext(),
////							AllProductsActivity.class);
////			startActivity(i);
////			finish();
//        }
//    }
}
