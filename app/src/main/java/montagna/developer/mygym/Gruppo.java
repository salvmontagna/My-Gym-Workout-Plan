package montagna.developer.mygym;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Gruppo extends AppCompatActivity {

    static Global g = new Global();
    private Button buttonAdd, nextGroup;
    private EditText riepilogo,rep,set;
    private Spinner spinner;
    private TextView gruppo,titoloRiepilogo;
    private ImageView imageGroup;
    String idSelectedSpinner1;
    String []array;
    String []arrayId;
    String []arrayGroups;
    String []arrayIdGroups;
    String id,name;
    int clickBtn=1;
    int countClickError=0;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gruppo);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        riepilogo=(EditText) findViewById(R.id.riepilogo) ;
        spinner=(Spinner) findViewById(R.id.spinner);
        imageGroup=(ImageView)findViewById(R.id.imageGroup);
        gruppo=(TextView) findViewById(R.id.gruppo);
        titoloRiepilogo=(TextView) findViewById(R.id.textView18);
        set=(EditText) findViewById(R.id.set);
        rep=(EditText)findViewById(R.id.rep);
        nextGroup=(Button) findViewById(R.id.nextGroup);
        riepilogo.setMovementMethod(new ScrollingMovementMethod());
        riepilogo.setEnabled(true);
        riepilogo.setKeyListener(null);

        final SharedPreferences mPrefs = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idSelectedSpinner1 = arrayId[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        myJsonRequest requestJson = new myJsonRequest(this);
        //Get esercizi pettorali
        final String url = g.getRootServer() + "/getMuscolarGroups.php";
        requestJson.requestJson(url,new myJsonRequest.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                try{
                    JSONObject myJson = new JSONObject(response);
                    JSONObject data = myJson.getJSONObject("data");
                    JSONArray muscolarGroups = data.getJSONArray("muscolarGroups");
                    arrayGroups= new String [muscolarGroups.length()];
                    arrayIdGroups= new String [muscolarGroups.length()];
                    for (int i=0;i<muscolarGroups.length();i++){
                        JSONObject muscolarData = muscolarGroups.getJSONObject(i);
                        id = muscolarData.getString("id");
                        name = muscolarData.getString("name");
                        arrayGroups[i]=name;
                        arrayIdGroups[i]=id;
                    }
                    gruppo.setText(arrayGroups[0]);
                    final String urlGroups = g.getRootServer() + "/getExerciseGroups.php?groupId="+arrayIdGroups[0];
                    downloadJsonGroups(urlGroups);
                } catch (JSONException e){
                }
            }
        });
        // Aggiungi esercizi alla listbox
        buttonAdd.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v){
            //Prende value dalle editText
            final String setVariable = set.getText().toString();
            final String repVariable = rep.getText().toString();

            //Aggiunge al root dell'url del database i parametri di set e ripetizioni tramite GET, forniti da editText
            final String urlSend = g.getRootServer() + "/setExercise.php?token="+mPrefs.getString("token",null)+"&exerciseId="+ idSelectedSpinner1+"&set="+setVariable+"&rep="+repVariable;
            final Map<String, String> params = new HashMap<>();

            //Parametri di set e rep
            params.put("set", setVariable);
            params.put("rep", repVariable);


            JsonObjectRequest myRequestSend = new JsonObjectRequest(Request.Method.GET, urlSend, new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("RESPONSE", response.toString());
                    // verifica numero error code
                    //condizione di verifica numero dell'error code
                    //se è vera apre la nuova activity, altrimenti apre dialogo d'allerta
                    if (repVariable.isEmpty() || setVariable.isEmpty()) {
                        //response tramite dialogo d'allerta
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(Gruppo.this, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(Gruppo.this);
                        }
                        builder.setTitle("Errore").setMessage("Serie e ripetizioni devono avere almeno un numero").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
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
                            String textTextView = riepilogo.getText().toString();
                            riepilogo.setText(textTextView + "\n" + spinner.getSelectedItem().toString()+": "+setVariable + " x " + repVariable +"\n");
                            countClickError++;

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERRORE", "ERRORE PROBABILE NELL'API");
                    }
                }) {
                };

                MySingleton.getInstance(getBaseContext()).addToRequestQueue(myRequestSend);
                Log.d("REQUEST", myRequestSend.toString());
                }
            });

            nextGroup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (clickBtn==arrayGroups.length){
                    Intent intent = new Intent(Gruppo.this,Tab.class);
                    startActivity(intent);
                    interstitialAd.show();
                    finish();
                }

                else{
                    if(clickBtn==arrayGroups.length-1){
                        nextGroup.setText("COMPLETA");
                    }

                    riepilogo.setText("");
                    gruppo.setText(arrayGroups[clickBtn]);
                    final String urlGroups = g.getRootServer() + "/getExerciseGroups.php?groupId=" + arrayIdGroups[clickBtn];
                    downloadJsonGroups(urlGroups);

                    switch (clickBtn) {
                        case 0:
                            imageGroup.setImageDrawable(getResources().getDrawable(R.mipmap.petto));
                            titoloRiepilogo.setText("Riepilogo Pettorali");
                            break;
                        case 1:
                            imageGroup.setImageDrawable(getResources().getDrawable(R.mipmap.bicipiti));
                            titoloRiepilogo.setText("Riepilogo Bicipiti");
                            break;
                        case 2:
                            imageGroup.setImageDrawable(getResources().getDrawable(R.mipmap.tricipiti));
                            titoloRiepilogo.setText("Riepilogo Tricipiti");
                            break;
                        case 3:
                            imageGroup.setImageDrawable(getResources().getDrawable(R.mipmap.lat));
                            titoloRiepilogo.setText("Riepilogo Dorsali");
                            break;
                        case 4:
                            imageGroup.setImageDrawable(getResources().getDrawable(R.mipmap.leg));
                            titoloRiepilogo.setText("Riepilogo Gambe");
                            break;
                        case 5:
                            imageGroup.setImageDrawable(getResources().getDrawable(R.mipmap.shoulder));
                            titoloRiepilogo.setText("Riepilogo Spalle");
                            break;
                        case 6:
                            imageGroup.setImageDrawable(getResources().getDrawable(R.mipmap.abs));
                            titoloRiepilogo.setText("Riepilogo Addominali");
                            break;

                        default:
                            imageGroup.setImageDrawable(getResources().getDrawable(R.mipmap.petto));
                            titoloRiepilogo.setText("Riepilogo Pettorali");
                            break;
                    }

                    clickBtn++;
                }
            }
        });
    }

    void downloadJsonGroups(String url) {
        myJsonRequest requestJsonGroups = new myJsonRequest(this);

        requestJsonGroups.requestJson(url,new myJsonRequest.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {

                try{

                    JSONObject myJson = new JSONObject(response);
                    JSONObject data = myJson.getJSONObject("data");
                    JSONArray exercises = data.getJSONArray("exercises");
                    array= new String [exercises.length()];
                    arrayId= new String [exercises.length()];
                    for (int i=0;i<exercises.length();i++){
                        JSONObject muscolarData = exercises.getJSONObject(i);
                        id = muscolarData.getString("id");
                        name = muscolarData.getString("name");
                        //  Log.d("Gruppi",id+"\n"+name+"\n\n");
                        array[i]=name;
                        arrayId[i]=id;
                    }

                    addToSpinner(array);

                } catch (JSONException e){

                }

            }
        });

    }

    void addToSpinner(String[] array) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item, array);
        spinner.setAdapter(adapter);
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

