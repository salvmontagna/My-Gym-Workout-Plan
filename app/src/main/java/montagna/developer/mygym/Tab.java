package montagna.developer.mygym;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Iterator;

public class Tab extends AppCompatActivity  {

    static Global g = new Global();
    private EditText gruppo1,gruppo2;
    private TextView txtGruppo1,txtGruppo2,dayTxt;
    private Button exit,completeTab;
    private AdView mAdView;

    String deletingTab="";
    String []array;
    String []arrayId;
    String []arrayRep;
    String []arraySet;
    String []arrayGruppo;
    String id,name,rep,set,gruppo;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();
    Toast toast;
    Bundle token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        gruppo1=(EditText) findViewById(R.id.gruppo1) ;
        gruppo2=(EditText) findViewById(R.id.gruppo2) ;
        txtGruppo1=(TextView) findViewById(R.id.txtGruppo1);
        txtGruppo2=(TextView) findViewById(R.id.txtGruppo2);
        dayTxt=(TextView)findViewById(R.id.day) ;
        exit=(Button)findViewById(R.id.exit);
        completeTab=(Button) findViewById(R.id.completeTab);
        gruppo1.setMovementMethod(new ScrollingMovementMethod());
        gruppo1.setEnabled(true);
        gruppo1.setKeyListener(null);
        gruppo2.setMovementMethod(new ScrollingMovementMethod());
        gruppo2.setEnabled(true);
        gruppo2.setKeyListener(null);
        token=getIntent().getExtras();

        MobileAds.initialize(this,"ca-app-pub-3255746612546275~3713289009");
        final InterstitialAd interstitialAd=new InterstitialAd(this);
        //interstitialAd.setAdUnitId("ca-app-pub-3255746612546275/2902005875");
        interstitialAd.setAdUnitId("ca-app-pub-3255746612546275/2902005875");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        interstitialAd.show();
                    }
                }, 2000);
            }}  );


        completeTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TabComplete.class);
                startActivity(intent);
               // interstitialAd.show();
                Tab.this.finish();
            }
        });


        Intent intent = getIntent();
        String daysTab=intent.getStringExtra("daysTab");
        int daysTabBool=intent.getIntExtra("daysTabBool",0);

        interstitialAd.show();
        gruppo1.setEnabled(false);
        gruppo2.setEnabled(false);
        final SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final SharedPreferences mPrefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //Eliminazione della scheda tramite dialogo
        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(Tab.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(Tab.this);
                }
                builder.setTitle("Eliminazione scheda attuale")
                        .setMessage("Sei sicuro di voler cancellare la scheda? Dovrai effettuare nuovamente il login per sostituire quella attuale")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //cancellazione scheda
                                String url;
                                url = g.getRootServer() + "/removeTab.php?token=" + mPrefs.getString("token", null);
                                downloadJsonGroups(url);
                                Log.d("name",url);

                                //Info all'apertura dell'activity login
                                AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(Tab.this, android.R.style.Theme_Material_Dialog_Alert);
                                } else {
                                    builder = new AlertDialog.Builder(Tab.this);
                                }
                                builder.setTitle("Info")
                                        .setMessage("Sei stato portato al login. Effettua nuovamente l'accesso e crea la nuova scheda per sostituire quella attuale.")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete
                                                SharedPreferences.Editor editor = sharedPref.edit();
                                                editor.remove("tabComplete");
                                                editor.remove("initialized");
                                                editor.remove("token");
                                                editor.commit();
                                                Intent intent2 = new Intent(Tab.this, Login.class);
                                                startActivity(intent2);
                                                finish();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .setIcon(R.mipmap.info )
                                        .show();
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

        if (!mPrefs.contains("initialized")) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("tabComplete", true);
            editor.commit();
            Intent intent2 = new Intent(Tab.this, Login.class);
            startActivity(intent2);
            finish();
        } else{

            Log.d("TOKEN",mPrefs.getString("token",null));
            // Current day is Monday

            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            String url;
             switch (day) {
                case Calendar.MONDAY:
                    dayTxt.setText("Lunedì");
                      url= g.getRootServer() + "/getTabGroup.php?token="+mPrefs.getString("token",null)+"&dayNumber=1";
                    downloadJsonGroups(url);

                    break;

              case Calendar.TUESDAY:
                  dayTxt.setText("Martedì");
                  url= g.getRootServer() + "/getTabGroup.php?token="+mPrefs.getString("token",null)+"&dayNumber=2";
                  downloadJsonGroups(url);
                  Log.d("name",url);

                  break;

                case Calendar.WEDNESDAY:
                    dayTxt.setText("Mercoledì");
                   url= g.getRootServer() + "/getTabGroup.php?token="+mPrefs.getString("token",null)+"&dayNumber=3";
                    downloadJsonGroups(url);


                    break;

                 case Calendar.THURSDAY:
                     dayTxt.setText("Giovedì");
                    url= g.getRootServer() + "/getTabGroup.php?token="+mPrefs.getString("token",null)+"&dayNumber=4";
                     downloadJsonGroups(url);


                     break;

                 case Calendar.FRIDAY:
                     dayTxt.setText("Venerdì");
                 url= g.getRootServer() + "/getTabGroup.php?token="+mPrefs.getString("token",null)+"&dayNumber=5";
                     downloadJsonGroups(url);
                     Log.d("name",url);

                     break;

                 case Calendar.SATURDAY:
                     dayTxt.setText("Sabato");
                      url= g.getRootServer() + "/getTabGroup.php?token="+mPrefs.getString("token",null)+"&dayNumber=6";
                     downloadJsonGroups(url);


                     break;


            }



        }

    }


    void downloadJsonGroups(String url) {
        myJsonRequest requestJsonGroups = new myJsonRequest(this);

        requestJsonGroups.requestJson(url,new myJsonRequest.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                String[] keyArray;
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("errorCode") == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONObject groupsExercises = data.getJSONObject("groupsExercises");
                        keyArray = new String[groupsExercises.length()];

                        Iterator iterator = groupsExercises.keys();
                        String key = "";
                        int z = 0;
                        while(iterator.hasNext()){
                            key = (String)iterator.next();
                            keyArray[z] = key;
                            z++;
                        }

                        for (int j=0; j<groupsExercises.length(); j++) {

                            JSONArray jsonArrGE1 = groupsExercises.getJSONArray(keyArray[j]);

                            for (int i=0; i< jsonArrGE1.length(); i++) {
                                JSONObject jsonObject1 = jsonArrGE1.getJSONObject(i);
                                String id = jsonObject1.getString("id");
                                String name = jsonObject1.getString("exerciseName");
                                String groupName = jsonObject1.getString("groupName");
                                String set = jsonObject1.getString("set");
                                String rep = jsonObject1.getString("rep");

                                if (j == 0) {
                                    txtGruppo1.setText(groupName);
                                    gruppo1.setText(gruppo1.getText().toString() + "\n" + name + " " + set + " x " + rep);
                                } else if (j == 1) {
                                    txtGruppo2.setText(groupName);
                                    gruppo2.setText(gruppo2.getText().toString() + "\n" + name + " " + set + " x " + rep);
                                }
                            }

                        }
                    }

                } catch (JSONException e){
                    Log.d("name",e.toString());
                }

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
