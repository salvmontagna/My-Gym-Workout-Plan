package montagna.developer.mygym;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static montagna.developer.mygym.R.id.date;

public class TabStarting extends AppCompatActivity {
    static Global g = new Global();
    private AdView mAdView;
    private Button nextconfig, giorni, info;
    private EditText tabExpiry,lunedi1,martedi1,mercoledi1,giovedi1,venerdi1,sabato1;
    private CheckBox lunedi,martedi,mercoledi,giovedi,venerdi,sabato;
    int conta=0;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_starting);
        nextconfig = (Button) findViewById(R.id.nextconfig);
        tabExpiry = (EditText) findViewById(R.id.tabExpiry);
        info = (Button) findViewById(R.id.info);

        lunedi=(CheckBox) findViewById(R.id.lunedi);
        martedi=(CheckBox) findViewById(R.id.martedi);
        mercoledi=(CheckBox) findViewById(R.id.mercoledi);
        giovedi=(CheckBox) findViewById(R.id.giovedi);
        venerdi=(CheckBox) findViewById(R.id.venerdi);
        sabato=(CheckBox) findViewById(R.id.sabato);
        lunedi1=(EditText) findViewById(R.id.lunedi1);
        martedi1=(EditText) findViewById(R.id.martedi1);
        mercoledi1=(EditText) findViewById(R.id.mercoledi1);
        giovedi1=(EditText) findViewById(R.id.giovedi1);
        venerdi1=(EditText) findViewById(R.id.venerdi1);
        sabato1=(EditText) findViewById(R.id.sabato1);

        lunedi1.setText("0");
        martedi1.setText("0");
        mercoledi1.setText("0");
        giovedi1.setText("0");
        venerdi1.setText("0");
        sabato1.setText("0");
        tabExpiry.setFocusable(false);

        //Date picker build
        tabExpiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(

                        TabStarting.this,
                        android.R.style.Theme_Material_Dialog_Alert,
                        mDateSetListener,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dialog.show();

            }
        });

        //Date picker show
        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;

                String date = day + "-" + month + "-" + year;
                tabExpiry.setText(date);
            }
        };



        //Admob
        MobileAds.initialize(this, "ca-app-pub-3255746612546275~3713289009");
        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

            lunedi.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    if (lunedi.isChecked()){
                        lunedi1.setText("1");
                        conta=conta+1;
                    }
                    else{
                        lunedi1.setText("0");
                        conta=conta-1;
                    }


                }
                });

            martedi.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (martedi.isChecked()){
                        martedi1.setText("1");
                        conta=conta+1;
                    }

                    else{
                        martedi1.setText("0");
                        conta=conta-1;
                    }

                }
            });

            mercoledi.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (mercoledi.isChecked()){
                        mercoledi1.setText("1");
                        conta=conta+1;
                    }

else{
                        mercoledi1.setText("0");
                        conta=conta-1;
                    }

                }
            });
            giovedi.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (giovedi.isChecked()){
                        giovedi1.setText("1");
                        conta=conta+1;
                    }
                    else{
                        giovedi1.setText("0");
                        conta=conta-1;
                    }


                }
            });
            venerdi.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (venerdi.isChecked()){
                        venerdi1.setText("1");
                        conta=conta+1;
                    }
                    else{
                        venerdi1.setText("0");
                        conta=conta-1;
                    }


                }
            });
            sabato.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (sabato.isChecked()){
                        sabato1.setText("1");
                        conta=conta+1;

                    }
                    else{
                        sabato1.setText("0");
                        conta=conta-1;
                    }


                }
            });


            nextconfig.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                //Prende value dalle editText
                final String tAbExpiry = tabExpiry.getText().toString();
                final String monday = lunedi1.getText().toString();
                final String tuesday = martedi1.getText().toString();
                final String wednesday = mercoledi1.getText().toString();
                final String thursday = giovedi1.getText().toString();
                final String friday = venerdi1.getText().toString();
                final String saturday = sabato1.getText().toString();

                SharedPreferences mPrefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String tokenGlobal = mPrefs.getString("token",null);

                //Aggiunge al root dell'url del database i parametri dei giorni e della scadenza della scheda tramite GET, forniti da editText e checkbox
                final String url = g.getRootServer() + "/tabStarting.php?token="+tokenGlobal+"&date="+tAbExpiry+"&monday="+monday+"&tuesday="+tuesday+"&wednesday="+wednesday+"&thursday="+thursday+"&friday="+friday+"&saturday="+saturday;
                Log.d("api",url);
                final Map<String, String> params = new HashMap<String, String>();

                //Parametri tab starting
                params.put("token", tokenGlobal);
                params.put("date", tAbExpiry);
                params.put("monday", monday);
                params.put("tuesday", tuesday);
                params.put("wednesday", wednesday);
                params.put("thursday", thursday);
                params.put("friday", friday);
                params.put("saturday", saturday);

                    JsonObjectRequest myRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                                    //condizione di verifica numero dell'error code
                                    //se è vera apre la nuova activity, altrimenti apre dialogo d'allerta
                                    if (conta<2) {
                                        AlertDialog.Builder builder;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            builder = new AlertDialog.Builder(TabStarting.this, android.R.style.Theme_Material_Dialog_Alert);
                                        } else {
                                            builder = new AlertDialog.Builder(TabStarting.this);
                                        }
                                        builder.setTitle("Parametri mancanti")
                                                .setMessage("Ricordati di selezionare almeno due giorni e facoltativamente di inserire la data di scadenza della scheda nel formato corretto (AAAA-MM-GG)")
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
                                                .setIcon(R.mipmap.alert)
                                                .show();

                                    } else {

                                        //response tramite dialogo d'allerta
                                        Log.d("Giorni cliccati","" +  conta);
                                        Intent intent = new Intent(getBaseContext(), TabStarting2.class);
                                        intent.putExtra("countButton",conta);
                                        startActivity(intent);
                                        finish();
                                        //



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
                    Log.d("REQUEST",myRequest.toString());


                }
            });
            // info
            info.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(TabStarting.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(TabStarting.this);
                    }
                    builder.setTitle("Info")
                            .setMessage("Se inserisci la data, verrai notificato alla scadenza della scheda, in base alla data da te impostata. ")
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
                            .setIcon(R.mipmap.info )
                            .show();

                    //



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

