package com.sabri.inf4042_sabritanich.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sabri.inf4042_sabritanich.ContactDetail;
import com.sabri.inf4042_sabritanich.R;

import java.util.List;


public class ContactsAdapter extends ArrayAdapter<ContactDetail> {
    Context context;  // contexte de l'activité
    List<ContactDetail> list;     // Liste des certificats

    public ContactsAdapter(Context context, int resource) {
        super(context, resource);
    }

    public ContactsAdapter(Context context, List<ContactDetail> list) {
        super(context, R.layout.contact, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {   // Méthode pour l'affichage des certificats dans l'adaptateur
        // Récupération de la vue
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.contact, parent, false);
        SharedPreferences userDetails = this.context.getSharedPreferences("smartphoneInfos", Context.MODE_PRIVATE);
        String screenSize = userDetails.getString("screenSize", "");
        int density = userDetails.getInt("density", 1);

        // récupérations des widgets présents sur la vue
        ImageView photoContact = (ImageView) view.findViewById(R.id.imageViewContact);

        TextView contactFullName = (TextView) view.findViewById(R.id.textViewContactInfos);
        // Attribution des valeurs
        if(list.get(position).getPhoto()!=null && !list.get(position).getPhoto().equals("")) {
            Bitmap img = BitmapFactory.decodeFile(list.get(position).getPhoto());
            photoContact.setImageBitmap(img);
        }
        else {
            Bitmap img = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo_avatar_add_contact);
            photoContact.setImageBitmap(img);
        }
        contactFullName.setText(list.get(position).getName()+" "+ list.get(position).getFirstName());

        return view;

    }

}
