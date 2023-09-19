package montagna.developer.mygym;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
//import java.util.Map;

public class TabStarting2 extends AppCompatActivity {

    private AdView mAdView;
    static Global g = new Global();
    private Button nextconfig;
    private Spinner gruppo1,gruppo2,gruppo3,gruppo4,gruppo5,gruppo6,gruppo7;
    private TextView giorno;
    String[] array ;
    String [] arrayTwo;
    String idSelectedSpinner1, idSelectedSpinner2,idSelectedSpinner3,idSelectedSpinner4,idSelectedSpinner5,idSelectedSpinner6,idSelectedSpinner7;
    String id;
    String name;
    String[] days = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato"};

    int[] daysBool;
    int countClick=1;
    int countButton;
    int countDaysInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_starting_2);
        nextconfig = (Button) findViewById(R.id.nextconfig);
        gruppo1=(Spinner) findViewById(R.id.gruppo1);
        gruppo2=(Spinner) findViewById(R.id.gruppo2);
        gruppo3=(Spinner) findViewById(R.id.gruppo3);
        gruppo4=(Spinner) findViewById(R.id.gruppo4);
        gruppo5=(Spinner) findViewById(R.id.gruppo5);
        gruppo6=(Spinner) findViewById(R.id.gruppo6);
        gruppo7=(Spinner) findViewById(R.id.gruppo7);
        giorno=(TextView) findViewById(R.id.giorno1);

        //Admob
        MobileAds.initialize(this, "ca-app-pub-3255746612546275~3713289009");
        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Intent intent = getIntent();
        int conta=intent.getIntExtra("countButton",0);
        countButton=conta;

        final SharedPreferences mPrefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        gruppo1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idSelectedSpinner1=arrayTwo[position];
                Log.d("idSpinner:","" + arrayTwo[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gruppo2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idSelectedSpinner2=arrayTwo[position];
                Log.d("idSpinner:","" + arrayTwo[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gruppo3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idSelectedSpinner3=arrayTwo[position];
                Log.d("idSpinner:","" + arrayTwo[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gruppo4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idSelectedSpinner4=arrayTwo[position];
                Log.d("idSpinner:","" + arrayTwo[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gruppo5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idSelectedSpinner5=arrayTwo[position];
                Log.d("idSpinner:","" + arrayTwo[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gruppo6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idSelectedSpinner6=arrayTwo[position];
                Log.d("idSpinner:","" + arrayTwo[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gruppo7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idSelectedSpinner7=arrayTwo[position];
                Log.d("idSpinner:","" + arrayTwo[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Aggiunge al root dell'url del database i parametri di email e password tramite GET, forniti da editText
        final String url = g.getRootServer() + "/getMuscolarGroups.php";
        final String urlDays = g.getRootServer() + "/getTab.php?token="+mPrefs.getString("token",null);


        //RICHIESTA DEI GRUPPI MUSCOLARI
        JsonObjectRequest myRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {

            @Override
                public void onResponse(JSONObject response) {
                 //   Log.d("RESPONSE", response.toString());
                    // verifica numero error code
                    if (response.has("errorCode")) {
                        try {

                            //condizione di verifica numero dell'error code
                            //se è vera apre la nuova activity, altrimenti apre dialogo d'allerta
                            if (response.getInt("errorCode") == 0) {
                                JSONObject data = response.getJSONObject("data");
                                JSONArray muscolarGroups = data.getJSONArray("muscolarGroups");
                                array= new String [muscolarGroups.length()];
                                arrayTwo= new String [muscolarGroups.length()];
                                for (int i=0;i<muscolarGroups.length();i++){
                                    JSONObject muscolarData = muscolarGroups.getJSONObject(i);
                                    id = muscolarData.getString("id");
                                    name = muscolarData.getString("name");
                                    Log.d("Gruppi",id+"\n"+name+"\n\n");
                                    array[i]=name;
                                    arrayTwo[i]=id;
                                }

                                addToSpinner(array);
                            }

                        } catch (JSONException e) {
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


        //RICHIESTA DEI GIORNI DELLA SETTIMANA

        JsonObjectRequest myRequestDays = new JsonObjectRequest(Request.Method.GET, urlDays, new JSONObject(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("RESPONSE", response.toString());
                // verifica numero error code
                if (response.has("errorCode")) {
                Log.d("response","" + response);
                    try {

                        //condizione di verifica numero dell'error code
                        //se è vera apre la nuova activity, altrimenti apre dialogo d'allerta
                        if (response.getInt("errorCode") == 0) {
                            JSONObject data = response.getJSONObject("data");
                            JSONObject tab = data.getJSONObject("tab");
                            daysBool = new int[tab.length() -1];
                            int monday = tab.getInt("monday");
                            int tuesday = tab.getInt("tuesday");
                            int wednesday = tab.getInt("wednesday");
                            int thursday = tab.getInt("thursday");
                            int friday = tab.getInt("friday");
                            int saturday = tab.getInt("saturday");

                            Log.d("Saturday", " "+saturday);

                            daysBool[0] = monday;
                            daysBool[1] = tuesday;
                            daysBool[2] = wednesday;
                            daysBool[3] = thursday;
                            daysBool[4] = friday;
                            daysBool[5] = saturday;

                            if (monday == 1) {
                                giorno.setText(days[0]);
                                countDaysInt=0;
                            } else if (tuesday == 1) {
                                giorno.setText(days[1]);
                                countDaysInt=1;
                            } else if (wednesday == 1) {
                                giorno.setText(days[2]);
                                countDaysInt=2;
                            } else if (thursday == 1) {
                                giorno.setText(days[3]);
                                countDaysInt=3;
                            }else if (friday == 1) {
                                giorno.setText(days[4]);
                                countDaysInt=4;
                            }else if (saturday == 1) {
                                giorno.setText(days[5]);
                                countDaysInt=5;
                            }


                        }

                    } catch (JSONException e) {
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

        MySingleton.getInstance(getBaseContext()).addToRequestQueue(myRequestDays);
        Log.d("REQUEST", myRequestDays.toString());

        //INVIO DI TUTTI I PARAMETRI TRAMITE BUTTONCLICK
            nextconfig.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                int dayNumberAPI = 0;
                switch (giorno.getText().toString()) {
                    case "Lunedì":
                        dayNumberAPI = 1;
                        break;
                    case "Martedì":
                        dayNumberAPI = 2;
                        break;
                    case "Mercoledì":
                        dayNumberAPI = 3;
                        break;
                    case "Giovedì":
                        dayNumberAPI = 4;
                        break;
                    case "Venerdì":
                        dayNumberAPI = 5;
                        break;
                    case "Sabato":
                        dayNumberAPI = 6;
                        break;
                }


                if (idSelectedSpinner1!="Bicipiti" || idSelectedSpinner2!="Bicipiti"){

                String url = g.getRootServer() + "/setTabGroup.php?token=" + mPrefs.getString("token", null) + "&groupOneId=" + idSelectedSpinner1 + "&groupTwoId=" + idSelectedSpinner2 + "&groupThreeId=" + idSelectedSpinner3+ "&groupFourId=" + idSelectedSpinner4+ "&groupFiveId=" + idSelectedSpinner5+ "&groupSixId=" + idSelectedSpinner6+ "&groupSevenId=" + idSelectedSpinner7 + "&dayNumber=" + dayNumberAPI;
                final Map<String, String> params = new HashMap<>();
                //Parametri
                params.put("token", "token" + mPrefs.getString("token", null));
                params.put("groupOneId", "groupOneId" + idSelectedSpinner1);
                params.put("groupTwoId", "groupTwoId" + idSelectedSpinner2);
                params.put("groupThreeId", "groupThreeId" + idSelectedSpinner3);
                params.put("groupFourId", "groupFourId" + idSelectedSpinner4);
                params.put("groupFiveId", "groupFiveId" + idSelectedSpinner5);
                params.put("groupSixId", "groupSixId" + idSelectedSpinner6);
                params.put("groupSevenId", "groupSevenId" + idSelectedSpinner7);
                params.put("dayNumber", "dayNumber" + dayNumberAPI);

                JsonObjectRequest myRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        // verifica numero error code
                        if (response.has("errorCode")) {
                            try {

                                //condizione di verifica numero dell'error code
                                //se è vera apre la nuova activity, altrimenti apre dialogo d'allerta
                                if (response.getInt("errorCode") == 0) {
                                    Log.d("ENTRATO", "ECCOLOOOO");


                                    if (countClick == countButton) {
                                        Intent intent = new Intent(getBaseContext(), Gruppo.class);
                                        intent.putExtra("daysTab", days);
                                        intent.putExtra("daysTabBool", daysBool);
                                        startActivity(intent);
                                        finish();
                                    } else {

                                        countClick++;
                                        int countDay = 0;
                                        for (int i = 0; i < daysBool.length; i++) {

                                            if (daysBool[i] == 1 && countDay > countDaysInt) {
                                                giorno.setText(days[i]);
                                                countDaysInt = i;
                                                Log.d("NGIORNO", "" + countDaysInt);
                                                break;

                                            } else {
                                                countDay++;
                                            }


                                        }

                                    }


                                }
                            } catch (JSONException e) {
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

                } else{

                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(TabStarting2.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(TabStarting2.this);
                    }
                    builder.setTitle("Attenzione")
                            .setMessage("Non puoi inserire gli stessi gruppi muscolari uguali nello stesso giorno.")
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
                            .setIcon(R.mipmap.alert )
                            .show();

                    //





                }

                    }
                });
        }

        void getDays() {






        }


        void addToSpinner(String[] array) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item, array);
            gruppo1.setAdapter(adapter);
            gruppo2.setAdapter(adapter);
            gruppo3.setAdapter(adapter);
            gruppo4.setAdapter(adapter);
            gruppo5.setAdapter(adapter);
            gruppo6.setAdapter(adapter);
            gruppo7.setAdapter(adapter);

            }


            @Override
            public void onBackPressed() {

                Intent intent = new Intent(getBaseContext(), TabStarting.class);
                startActivity(intent);
                finish();

        }


    }
