package com.sabri.inf4042_sabritanich.Fragments;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.sabri.inf4042_sabritanich.Activities.ContactActivity;
import com.sabri.inf4042_sabritanich.ContactDetail;
import com.sabri.inf4042_sabritanich.R;
import com.sabri.inf4042_sabritanich.Services.GetContactHandler;
import com.sabri.inf4042_sabritanich.Storage.DbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.google.android.gms.internal.zzid.runOnUiThread;


public class DownloadFragment extends Fragment {

    ImageView download;
    String langue;
    public static ArrayList<ContactDetail> contactList;
    SharedPreferences preferences;
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null;
        view = inflater.inflate(R.layout.fragment_download, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        langue = preferences.getString("langue","Français");

        contactList = new ArrayList<ContactDetail>();

        download = (ImageView) view.findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "",oui ="", non = "";
                if(langue.equals("Français")) {
                    message = "Voulez-vous vraiment télécharger des contacts ?";
                    oui = "Oui";
                    non = "Non";
                }
                else if(langue.equals("English")){
                    message = "Do you really want to download contacts ?";
                    oui = "Yes";
                    non = "No";
                }
                Dialog dialog = new AlertDialog.Builder(getActivity()).setMessage(message)    // dialog message
                        .setTitle("Confirmation")    // dialog header
                        .setPositiveButton(oui, new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    /*Intent msgIntent = new Intent(getActivity(), GetContactService.class);
                                    msgIntent.putExtra(GetContactService.URL,"http://test.chiffaboutique.fr/json/contact.json");
                                    getActivity().startService(msgIntent);*/
                                    new GetContacts().execute();

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton(non, null)    // cancel button
                        .create();
                dialog.show();
            }
        });

        // on lance le service

        return view;
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            GetContactHandler getContactHandler = new GetContactHandler();
            dbHelper = new DbHelper(getActivity());
            sqLiteDatabase = dbHelper.getWritableDatabase();
            // Making a request to url and getting response
            String jsonStr = getContactHandler.callService("http://test.chiffaboutique.fr/json/contacts.json");


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String name = c.getString("name");
                        String first_name = c.getString("first_name");
                        String telephone = c.getString("telephone");


                        ContactDetail contactDetail = new ContactDetail(name,first_name,telephone,"");
                        dbHelper.addContact(sqLiteDatabase,contactDetail);

                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            /*Fragment currentFragment = getFragmentManager().findFragmentByTag("ContactFragment");
            FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
            fragTransaction.detach(currentFragment);
            fragTransaction.attach(currentFragment);
            fragTransaction.commit();*/
           createNotification();
            getActivity().finish();
            Intent intent = new Intent(getActivity(),ContactActivity.class);
            getActivity().startActivity(intent);
        }

    }

    public void createNotification(){
        final NotificationManager mNotification = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(getActivity(), ContactActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),
                0, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.download2)
                        .setContentTitle("Download")
                        .setContentText("Download success");

        mNotification.notify(0, mBuilder.build());
    }
}
