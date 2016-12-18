package com.sabri.inf4041_sabritanich.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.sabri.inf4041_sabritanich.Activities.ContactActivity;
import com.sabri.inf4041_sabritanich.Fragments.DownloadFragment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetContactHandler {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String SOURCE_URL = "destination_source";


    public GetContactHandler() {

    }


    public String callService(String urlPath) {
        URL url = null;
        BufferedReader buf = null;
        InputStream inputStream = null;
        StringBuilder stringBuilder = null;
        String ligne;
        try {
            url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            inputStream = new BufferedInputStream(connection.getInputStream());
            buf = new BufferedReader(new InputStreamReader(inputStream));
            stringBuilder = new StringBuilder();
            while ((ligne = buf.readLine()) != null){
                stringBuilder.append(ligne);
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(buf!=null){
                try {
                    buf.close();
                }
                catch (IOException e){

                }
            }
        }

        /*Intent broadcastIntent = new Intent(ContactActivity.MyReceiver.CONTACTS_UPDATE);
        //broadcastIntent.setAction(DownloadFragment.MyReceiver.CONTACTS_UPDATE);
        //broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(SOURCE_URL, stringBuilder.toString());
        sendBroadcast(broadcastIntent);

        //LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(DownloadFragment.MyReceiver.CONTACTS_UPDATE));*/
        return stringBuilder.toString();

    }

}
