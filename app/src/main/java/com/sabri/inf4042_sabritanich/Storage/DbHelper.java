package com.sabri.inf4042_sabritanich.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sabri.inf4042_sabritanich.ContactDetail;

import java.util.ArrayList;


public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Queue.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String NUMBER_TYPE = " NUMBER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_CONTACTS =
            "CREATE TABLE " + ContactContract.Contact.TABLE_NAME + " (" +
                    ContactContract.Contact._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ContactContract.Contact.COLUMN_NAME_ID_CONTACT   + " INTEGER" + COMMA_SEP +
                    ContactContract.Contact.COLUMN_NAME_FIRST_NAME   + TEXT_TYPE + COMMA_SEP +
                    ContactContract.Contact.COLUMN_NAME_LAST_NAME    + TEXT_TYPE + COMMA_SEP +
                    ContactContract.Contact.COLUMN_NAME_PHONE + TEXT_TYPE  + COMMA_SEP +
                    ContactContract.Contact.COLUMN_NAME_PHOTO + TEXT_TYPE  +
                    " );";

    private static final String SQL_DELETE_CONTACTS = "DROP TABLE IF EXISTS " + ContactContract.Contact.TABLE_NAME;

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_CONTACTS);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addContact(SQLiteDatabase db, ContactDetail contact){
        ContentValues values = new ContentValues();

        values.put(ContactContract.Contact.COLUMN_NAME_FIRST_NAME, contact.getFirstName().toString());
        values.put(ContactContract.Contact.COLUMN_NAME_LAST_NAME, contact.getName().toString());
        values.put(ContactContract.Contact.COLUMN_NAME_PHONE, contact.getNumber().toString());
        values.put(ContactContract.Contact.COLUMN_NAME_PHOTO, contact.getPhoto().toString());
        long newRow;
        newRow = db.insert(ContactContract.Contact.TABLE_NAME, null, values);
    }

    public void removeContact(SQLiteDatabase db,ContactDetail contactDetail)
    {
        db.delete(ContactContract.Contact.TABLE_NAME, ContactContract.Contact.COLUMN_NAME_LAST_NAME +"= ? and "+ ContactContract.Contact.COLUMN_NAME_FIRST_NAME+" = ? and "+ ContactContract.Contact.COLUMN_NAME_PHONE+" = ?",new String[]{String.valueOf(contactDetail.getName()),String.valueOf(contactDetail.getFirstName()),String.valueOf(contactDetail.getNumber())});
    }

    public ArrayList<ContactDetail> chargeContact(SQLiteDatabase db)
    {
        ArrayList<ContactDetail> liste = new ArrayList<ContactDetail>();

        String[] projection = {
                ContactContract.Contact.COLUMN_NAME_ID_CONTACT,
                ContactContract.Contact.COLUMN_NAME_LAST_NAME,
                ContactContract.Contact.COLUMN_NAME_FIRST_NAME,
                ContactContract.Contact.COLUMN_NAME_PHONE,
                ContactContract.Contact.COLUMN_NAME_PHOTO
        };

        Cursor cursor = db.query(
                ContactContract.Contact.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            ContactDetail contactDetail = new ContactDetail(
                    cursor.getInt(cursor.getColumnIndexOrThrow(ContactContract.Contact.COLUMN_NAME_ID_CONTACT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactContract.Contact.COLUMN_NAME_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactContract.Contact.COLUMN_NAME_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactContract.Contact.COLUMN_NAME_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactContract.Contact.COLUMN_NAME_PHOTO))
            );
            liste.add(contactDetail);
        }
        //Toast.makeText(this.mcontext, String.valueOf(liste.size()), Toast.LENGTH_SHORT).show();
        cursor.close();
        return liste;
    }
}
