package com.sabri.inf4041_sabritanich.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sabri.inf4041_sabritanich.ContactDetail;
import com.sabri.inf4041_sabritanich.R;
import com.sabri.inf4041_sabritanich.Storage.DbHelper;


public class AddContactActivity extends AppCompatActivity {

    DbHelper dbHelper;
    SQLiteDatabase db;

    EditText editTextName;
    EditText editTextFirstName;
    EditText editTextPhone;
    ImageView imageViewPhoto;

    String name,firstName,phone,photo;
    private static int RESULT_LOAD_IMAGE = 1;

    SharedPreferences preferences;

    String langue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();
        photo=null;

        editTextFirstName = (EditText) findViewById(R.id.input_first_name);
        editTextName = (EditText) findViewById(R.id.input_name);
        editTextPhone = (EditText) findViewById(R.id.input_phone);
        imageViewPhoto = (ImageView) findViewById(R.id.photo_contact);

        /*imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });*/
        imageViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.photo_avatar_add_contact));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_ok:

                if(validate()) {
                    ContactDetail contact;
                    if(photo!=null)
                    {
                        contact = new ContactDetail(name,firstName,phone,photo);
                    }
                    else
                    {
                        contact = new ContactDetail(name,firstName,phone,"");
                    }
                    dbHelper.addContact(db,contact);
                    if(langue.equals("English")) Toast.makeText(this,"Contact successfully created !",Toast.LENGTH_LONG).show();
                    else Toast.makeText(this,"Contact ajouté avec succès !",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddContactActivity.this,ContactActivity.class);
                    startActivity(intent);
                }


                break;

        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        langue = preferences.getString("langue","Français");
        if(langue.equals("English")) {
            this.setTitle(R.string.add_contact_en);
            editTextFirstName.setHint("First name");
            editTextPhone.setHint("Phone number");
            editTextName.setHint("Name");
        }
        else  {
            this.setTitle(R.string.add_contact_fr);
            editTextFirstName.setHint("Prénom");
            editTextPhone.setHint("Téléphone");
            editTextName.setHint("Nom");
        }

    }

    public boolean validate() {
            boolean valid = true;

            name = editTextName.getText().toString();
            firstName = editTextFirstName.getText().toString();
            phone = editTextPhone.getText().toString();
    //|| !android.util.Patterns.EMAIL_ADDRESS.matcher(id).matches()
            if (name.isEmpty() ) {
            editTextName.setError("entrez un nom valide");
            valid = false;
            } else {
            editTextName.setError(null);
            }

            if (firstName.isEmpty() ) {
            editTextFirstName.setError("entrez un prénom valide");
            valid = false;
            } else {
            editTextFirstName.setError(null);
            }

        if (phone.isEmpty() ) {
            editTextPhone.setError("entrez un numéro valide");
            valid = false;
        } else {
            editTextPhone.setError(null);
        }


        return valid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            SharedPreferences userDetails = getSharedPreferences("smartphoneInfos", Context.MODE_PRIVATE);
            String screenSize = userDetails.getString("screenSize", "");
            int density = userDetails.getInt("density", 1);

            ImageView imageView = (ImageView) findViewById(R.id.photo_contact);
            Bitmap img = BitmapFactory.decodeFile(picturePath);
            photo = picturePath;

            imageView.setImageBitmap(photoConfig(screenSize,density,img));

        }


    }

    public Bitmap scaleImage (Bitmap logo, int x, int y) {

        if ((logo == null)) {
            return logo;
        }

        int sizeX = Math.round(x);
        int sizeY = Math.round(y);

        Bitmap bitmapResized = Bitmap.createScaledBitmap(logo, sizeX, sizeY, false);

        return bitmapResized;
    }

    public Bitmap photoConfig(String screenSize,int density,Bitmap logo)
    {
        LinearLayout.LayoutParams params;
        /*if (screenSize.equals("small")) {
            logo = scaleImage(logo,96,96);
        }

        if (screenSize.equals("medium") && density < 200) {
            logo = scaleImage(logo,180,180);
        } else if (screenSize.equals("medium") && density > 200) {
            logo = scaleImage(logo,220,220);
        } else {
            logo = scaleImage(logo,140,140);
        }

        if (screenSize.equals("large")) {
            logo = scaleImage(logo,320,320);
        }*/
        logo = scaleImage(logo,90,90);

        return logo;
    }



}
