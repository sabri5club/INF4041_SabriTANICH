package com.sabri.inf4042_sabritanich.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sabri.inf4042_sabritanich.Activities.AddContactActivity;
import com.sabri.inf4042_sabritanich.Activities.ContactActivity;
import com.sabri.inf4042_sabritanich.Activities.DisplayContactActivity;
import com.sabri.inf4042_sabritanich.ContactDetail;
import com.sabri.inf4042_sabritanich.Adapters.ContactsAdapter;
import com.sabri.inf4042_sabritanich.R;
import com.sabri.inf4042_sabritanich.Storage.DbHelper;

import java.util.List;


public class ContactFragment extends Fragment {

    SQLiteDatabase db;
    DbHelper mDbHelper;
    SharedPreferences preferences;
    public static List<ContactDetail> contactList;
    ContactDetail contact;
    FloatingActionButton addButton;
    public static ListView listViewContacts;
    ContactsAdapter contactsAdapter;
    String langue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null;
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        langue = preferences.getString("langue","Français");

        mDbHelper = new DbHelper(getActivity());
        db = mDbHelper.getReadableDatabase();

        contactList = mDbHelper.chargeContact(db);

        if(contactList.isEmpty())
        {
            view = inflater.inflate(R.layout.fragment_contact_empty, container, false);

            addButton = (FloatingActionButton) view.findViewById(R.id.btn_add_contact);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),AddContactActivity.class);
                    startActivity(intent);
                }
            });
        }
        else {

            view = inflater.inflate(R.layout.fragment_contact, container, false);
            addButton = (FloatingActionButton) view.findViewById(R.id.btn_add_contact);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),AddContactActivity.class);
                    startActivity(intent);
                }
            });
            listViewContacts = (ListView) view.findViewById(R.id.listContacts);
            contactsAdapter = new ContactsAdapter(getActivity(),contactList);
            listViewContacts.setAdapter(contactsAdapter);
            listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ContactDetail contactDetail = contactsAdapter.getItem(i);
                    onContactclick(contactDetail);
                }
            });

            registerForContextMenu(listViewContacts);
        }
            return view;
    }

    public void onContactclick(ContactDetail contact){
        Intent intent = new Intent(getActivity(), DisplayContactActivity.class);
        intent.putExtra("id",contact.getId());
        intent.putExtra("name",contact.getName());
        intent.putExtra("firstName",contact.getFirstName());
        intent.putExtra("phone",contact.getNumber());
        intent.putExtra("photo",contact.getPhoto());
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        langue = preferences.getString("langue","Français");
        if (v.getId()==R.id.listContacts) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("Options");
            String[] menuItems= null;
            if(langue.equals("English")) menuItems = getResources().getStringArray(R.array.menu_contacts_en);
            else menuItems = getResources().getStringArray(R.array.menu_contacts_fr);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String[] menuItems= null;
        if(langue.equals("English")) menuItems = getResources().getStringArray(R.array.menu_contacts_en);
        else menuItems = getResources().getStringArray(R.array.menu_contacts_fr);
        String menuItemName = menuItems[item.getItemId()];
        final ContactDetail contactDetail = contactList.get(info.position);
        if(menuItemName.equals("Supprimer") || menuItemName.equals("Remove"))
        {
            String message = "",oui ="", non = "";
            if(langue.equals("Français")) {
                message = "Voulez-vous vraiment supprimer ce contact ?";
                oui = "Oui";
                non = "Non";
            }
            else if(langue.equals("English")){
                message = "Do you really want to remove this contact ?";
                oui = "Yes";
                non = "No";
            }
            Dialog dialog = new AlertDialog.Builder(getActivity()).setMessage(message)    // dialog message
                    .setTitle("Confirmation")    // dialog header
                    .setPositiveButton(oui, new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                removeContact(contactDetail);
                                getActivity().finish();
                                startActivity( new Intent(getActivity(),ContactActivity.class));

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton(non, null)    // cancel button
                    .create();
            dialog.show();    // showing dialog
        }
        return true;
    }

    public void removeContact(ContactDetail contactDetail){
        mDbHelper = new DbHelper(getActivity());
        db = mDbHelper.getWritableDatabase();
        mDbHelper.removeContact(db,contactDetail);
        db.close();
    }

}
