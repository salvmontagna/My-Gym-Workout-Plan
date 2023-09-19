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
import android.widget.TextView;
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

import static android.R.id.content;


public class Register extends AppCompatActivity {

    private AdView mAdView;
    private TextView login;
    static Global g = new Global();
    private EditText emailField, passwordField;
    private Button registerButton;
    private TextView signinButton, forgottenButton;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        login=(TextView) findViewById(R.id.textView15);
        emailField = (EditText) findViewById(R.id.email);
        signinButton = (TextView) findViewById(R.id.textView);
        passwordField = (EditText) findViewById(R.id.password);
        registerButton = (Button) findViewById(R.id.bSignin);
        forgottenButton = (TextView) findViewById(R.id.forgotten);




        //Admob
        MobileAds.initialize(this, "ca-app-pub-3255746612546275~3713289009");
        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Evento click del bottone registrati
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Prende value dalle editText
                final String eMailValue = emailField.getText().toString();
                final String passwordValue = passwordField.getText().toString();
                final String url = g.getRootServer() + "/register.php?user_mail="+eMailValue +"&user_pass=" +passwordValue;
                final Map<String,String> params = new HashMap<>();
                params.put("user_mail", eMailValue);
                params.put("user_pass",passwordValue);

                JsonObjectRequest myRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(params), new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                            Log.d("RESPONSE", response.toString());
                            // verifica numero error code


                            if (response.has("errorCode")){
                                try {

                                    //condizione di verifica numero dell'error code
                                    //se è vera apre la nuova activity, altrimenti apre dialogo d'allerta
                                    if (response.getInt("errorCode")==0){

                                        //salvataggio del token
                                        String  myToken = response.getJSONObject("data").getString("token");
                                        Log.d("TOKEN: ", myToken);
                                        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("token",myToken);
                                        editor.putBoolean("initialized", true);
                                        editor.commit();

                                        Intent intent = new Intent(getBaseContext(), TabAdding.class);
                                        intent.putExtra("email", emailField.getText().toString());
                                        startActivity(intent);
                                        finish();
                                    }

                                    else {

                                        //response tramite dialogo d'allerta
                                        Log.d("RESPONSE",response.toString());

                                        AlertDialog.Builder builder;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            builder = new AlertDialog.Builder(Register.this, android.R.style.Theme_Material_Dialog_Alert);
                                        } else {
                                            builder = new AlertDialog.Builder(Register.this);
                                        }
                                        builder.setTitle("Registrazione fallita")
                                                .setMessage("Utente già esistente o credenziali non valide")
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
                                                . setIcon(R.mipmap.alert)
                                                .show();

                                        //

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("ERRORE","ERRORE PROBABILE NELL'API");
                        }
                    })
                    {


                    };

                    MySingleton.getInstance(getBaseContext()).addToRequestQueue(myRequest);
                    Log.d("REQUEST",myRequest.toString());
                }
            });



            login.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(getBaseContext(), Login.class);
                    startActivity(intent);
                }
            });



        }

        //Toast per uscire dall'app
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Clicca nuovamente indietro per uscire", Toast.LENGTH_SHORT).show();
        Toast toast= Toast.makeText(getApplicationContext(),
                "Clicca nuovamente indietro per uscire", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
