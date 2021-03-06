package com.example.solution.asynctask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import com.example.solution.MainActivity;

import com.example.solution.ViewNote;
import com.example.solution.util.HttpHandler;

import com.example.solution.database.Note;




class GetJsonFromUrlTask extends AsyncTask<Void, Void, String> {

    OnGetJsonFromUrlTaskComplete caller;
    Context context;

    private Activity activity;
    private String activityName;
    private String url;
    private String deal_id;
    private String main_web;
    private static JSONArray jarr = null;
    //private ProgressDialog pDialog;

    private ArrayList<Note> noteList = new ArrayList<Note>();

    private final static String TAG = GetJsonFromUrlTask.class.getSimpleName();

    public GetJsonFromUrlTask(Activity activity, String url, String deal_id, String main_web) {
        //super();
        this.activity = activity;
        this.activityName = activity.getLocalClassName();
        //caller = (OnGetJsonFromUrlTaskComplete) activity;
        context = activity;
        this.url = url;
        this.deal_id = deal_id;
        this.main_web = main_web;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // Interface to be implemented by calling activity
    public interface OnGetJsonFromUrlTaskComplete {
        public void urlResponse(String result);
    }


    public interface OnTaskCompleted {
        void onTaskCompleted(String response);
    }

    @Override
    protected String doInBackground(Void... params) {

        //String rage = loadJSON(this.url).toString();
        JSONObject rage = loadJSON(this.url);
        String asher = (rage != null) ? rage.toString() : "hakuna any!";
        return  asher;

    }

    @Override
    protected void onPostExecute(String result) {



        //Log.e(TAG, "activityName: " + activityName);

        String actName = String.valueOf(activityName);
        Log.e(TAG, "json_result: " + result);
//
//        /*if(actName.equals("MenuDrawer") ) {
//            //Log.e(TAG, "MenuDrawer: " + result);
//            if(main_web == null){
//                ((MenuDrawer) activity).jsonResults(result);
//            } else {
//                ((MenuDrawer) activity).jsonResults_single(result);
//            }
//
//        }
//        else if(actName.equals("AccountDeals") ) {
//            //Log.e(TAG, "AccountDeals: " + result);
//            ((AccountDeals) activity).jsonDealFetch(result);
//        }
//        else if(actName.equals("AccountDealsMain") ) {
//            //Log.e(TAG, "AccountDeals: " + result);
//            ((AccountDealsMain) activity).jsonDealFetch(result, deal_id);
//        }
//        */
    }



    protected void onCancelled(String result) {
        /*if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }*/
        //caller.urlResponse(result);
    }


    public JSONObject loadJSON(String url) {
        // Creating JSON Parser instance
        JSONGetter jParser = new JSONGetter();

        JSONObject json = jParser.getJSONFromUrl(url);

        return json;
    }


    private class JSONGetter {

        private InputStream is = null;
        private JSONObject jObj = null;
        private String json = "";

        // constructor
        public JSONGetter() {

        }

        public JSONObject getJSONFromUrl(String url) {


            HttpHandler sh = new HttpHandler();
            JSONObject jsonObj = null;
            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    jsonObj = new JSONObject(jsonStr);

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }



            } else {
                Log.e(TAG, "Couldn't get json from server.");

            }

            return jsonObj;

        }

    }

}

