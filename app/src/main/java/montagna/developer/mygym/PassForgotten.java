package montagna.developer.mygym;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PassForgotten extends AppCompatActivity {

    private AdView mAdView;
    static Global g = new Global();
    private Button bForgotten;
    private EditText emailField;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_forgotten);

        bForgotten=(Button) findViewById(R.id.bForgotten);
        emailField=(EditText)findViewById(R.id.email);

        //Admob
        MobileAds.initialize(this, "ca-app-pub-3255746612546275~3713289009");
        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Evento click del bottone accedi
        bForgotten.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            //Prende value dalle editText
            final String eMailValue = emailField.getText().toString();

            //Aggiunge al root dell'url del database i parametri di email e password tramite GET, forniti da editText
            final String url = g.getRootServer() + "/retrievePassword.php?user_mail=" + eMailValue;
            final Map<String, String> params = new HashMap<>();

            //Parametri di email e password
            params.put("user_mail", eMailValue);


            JsonObjectRequest myRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("RESPONSE", response.toString());
                    // verifica numero error code
                    if (response.has("errorCode"))
                    {
                        try {

                            //condizione di verifica numero dell'error code
                            //se è vera apre la nuova activity, altrimenti apre dialogo d'allerta
                            if (response.getInt("errorCode") == 0 ) {

                                //response tramite dialogo d'allerta
                                AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(PassForgotten.this, android.R.style.Theme_Material_Dialog_Alert);
                                } else {
                                    builder = new AlertDialog.Builder(PassForgotten.this);
                                }
                                builder.setTitle("Operazione riuscita")
                                        .setMessage("La tua password è stata mandata alla mail: "+eMailValue)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(getBaseContext(), Login.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                          .setIcon(R.mipmap.alert)
                                        .show();

                                //


                            } else {

                                //response tramite dialogo d'allerta
                                AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(PassForgotten.this, android.R.style.Theme_Material_Dialog_Alert);
                                } else {
                                    builder = new AlertDialog.Builder(PassForgotten.this);
                                }
                                builder.setTitle("Operazione fallita")
                                        .setMessage("Utente non trovato o email non valida")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .setIcon(R.drawable.alert)
                                        .show();

                                //

                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERRORE", "ERRORE PROBABILE NELL'API");
                }
            }) {
            };

            MySingleton.getInstance(getBaseContext()).addToRequestQueue(myRequest);
            Log.d("REQUEST", myRequest.toString());
        }
    });


    }

}
