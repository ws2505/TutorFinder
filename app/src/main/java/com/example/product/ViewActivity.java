package com.example.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class ViewActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, Object>> productsList;

    // url to get all products list
    private static String url_all_products = "http://115.159.214.78/testproduct/get_all_products.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PIC="picture";
    private static final String TAG_DESCRIPTION="description";
    private static final String TAG_SUBJECT="subject";

    //Subject message
    private String subjectMessage="math";

    // products JSONArray
    JSONArray products = null;

    //log message
    int getProductMsg ;

    //get product success flag
//    boolean getProductSuccess = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        //receive passed subject message p
        Bundle bundle = this.getIntent().getExtras();
        subjectMessage = bundle.getString("subject");
        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, Object>>();
//        Toast.makeText(getApplicationContext(),"running", Toast.LENGTH_SHORT).show();
        // Loading products in Background Thread
        new LoadAllProducts().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        ViewDetailActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_PID, pid);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });

    }

    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewActivity.this);
//            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("subject", subjectMessage + ""));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

            /*json returned
            {"products":[{"pid":"1","name":"Song","price":"123.00","created_at":"2017-06-12 14:48:39","updated_at":null},
            {"pid":"2","name":"Song","price":"123.00","created_at":"2017-06-12 14:52:26","updated_at":null},
            {"pid":"3","name":"Song","price":"123.00","created_at":"2017-06-12 14:52:42","updated_at":null}],"success":1}

            */

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                getProductMsg = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);
                    //set the get product success flag to be true
//                    getProductSuccess = true;
                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);
                        String description = c.getString(TAG_DESCRIPTION);
                        // creating new HashMap
                        HashMap<String, Object> map = new HashMap<String, Object>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);
                        map.put(TAG_DESCRIPTION,description);
                        map.put(TAG_PIC,R.drawable.bbs_avatar_anonymity_1);

                        // adding HashList to ArrayList
                        productsList.add(map);


                    }
                }
                /**null product found
                 /*
                 else {
                 // no products found
                 // Launch Add New product Activity
                 Intent i = new Intent(getApplicationContext(),
                 NewProductActivity.class);
                 // Closing all previous activities
                 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(i);
                 }
                 */
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ""+getProductMsg;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String result) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            ViewActivity.this, productsList,
                            R.layout.list_item, new String[] { TAG_PID,
                            TAG_NAME, TAG_DESCRIPTION, TAG_PIC},
                            new int[] { R.id.pid, R.id.name, R.id.description, R.id.image });

//productsList = [{name=宋, pid=50}, {name=song, pid=51}]

                    // updating listview
                    setListAdapter(adapter);


                }
            });
//            Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_SHORT).show();
        }

    }
}