package com.sabri.inf4041_sabritanich.Fragments;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sabri.inf4041_sabritanich.Services.GetContactService;
import com.sabri.inf4041_sabritanich.R;


public class DownloadFragment extends Fragment {

    ImageView download;
    String langue;
    private MyReceiver receiver;

    public class MyReceiver extends BroadcastReceiver {
        public static final String CONTACTS_UPDATE ="com.sabri.inf4041_sabritanich.CONTACTS_UPDATE";

        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra(GetContactService.SOURCE_URL);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null;
        view = inflater.inflate(R.layout.fragment_download, container, false);

        IntentFilter intentFilter = new IntentFilter(MyReceiver.CONTACTS_UPDATE);
        receiver = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver,intentFilter);

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
                                    Intent msgIntent = new Intent(getActivity(), GetContactService.class);
                                    msgIntent.putExtra(GetContactService.URL,"http://api.openweathermap.org/data/2.5/weather?q=London&mode=xml");
                                    getActivity().startService(msgIntent);

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

   /* public void handleActionContacts(){
        URL url = null;
        try{
            url = new URL("");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if(HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                copyInputStreamToFile(connection.getInputStream(),new File(getCacheDir(),"contacts.json"));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyInputStreamToFile(InputStream inputStream,File file){
        try {
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int length;
            while((length = inputStream.read(buf)) > 0){
                outputStream.write(buf,0,length);
            }
            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
