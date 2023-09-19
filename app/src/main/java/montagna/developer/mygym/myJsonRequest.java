package montagna.developer.mygym;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Monti on 30/06/2017.
 */

public class myJsonRequest {

    public Activity activity;

    public myJsonRequest(Activity activity) {
        this.activity = activity;
    }

    public interface VolleyCallback {
        void onSuccessResponse(String result);
    }

    public void requestJson(String url, final VolleyCallback callback) {

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, url,  new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                callback.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
            }
        });

        MySingleton.getInstance(activity).addToRequestQueue(jsObjRequest);
    }


}
