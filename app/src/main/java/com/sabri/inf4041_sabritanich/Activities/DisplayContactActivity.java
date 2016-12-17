package com.sabri.inf4041_sabritanich.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sabri.inf4041_sabritanich.R;

import org.w3c.dom.Text;

public class DisplayContactActivity extends AppCompatActivity {

    SharedPreferences preferences;
    TextView textViewName,textViewPhone;
    ImageView call,sms;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        String name = getIntent().getStringExtra("name");
        String firstName = getIntent().getStringExtra("firstName");
        final String phone = getIntent().getStringExtra("phone");

        textViewName = (TextView) findViewById(R.id.textViewContactName);
        textViewPhone = (TextView) findViewById(R.id.textViewContactNumber);

        textViewName.setText(firstName + " "+ name);
        textViewPhone.setText(phone);

        call = (ImageView) findViewById(R.id.call_contact);
        sms =  (ImageView) findViewById(R.id.sms_contact);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms:"+phone));
                startActivity(smsIntent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String langue = preferences.getString("langue","Français");
        if(langue.equals("English")) this.setTitle(R.string.contact_details_en);
        else  this.setTitle(R.string.contact_details_fr);

    }
}
