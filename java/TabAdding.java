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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class TabAdding extends AppCompatActivity {
    private AdView mAdView;
    Login class1 = new Login();
    private Button tabadd;
    private TextView welcome;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();
   // final SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_adding);
       tabadd=(Button) findViewById(R.id.tabadd);
        welcome=(TextView) findViewById(R.id.welcome);
        Bundle bu;
        bu=getIntent().getExtras();
        welcome.setText(bu.getString("email")+",");


        //Admob
        MobileAds.initialize(this, "ca-app-pub-3255746612546275~3713289009");
        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Apertura altra activity
        tabadd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                    Intent intent = new Intent(getBaseContext(), TabStarting.class);
                    startActivity(intent);
                    finish();


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
