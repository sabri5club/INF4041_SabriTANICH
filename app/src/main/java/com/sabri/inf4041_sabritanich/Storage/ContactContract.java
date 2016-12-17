package com.sabri.inf4041_sabritanich.Storage;

import android.provider.BaseColumns;


public class ContactContract {
    public ContactContract(){}


    public static abstract class Contact implements BaseColumns {
        public static final String TABLE_NAME = "contact";
        public static final String COLUMN_NAME_ID_CONTACT = "idcontact";
        public static final String COLUMN_NAME_FIRST_NAME = "firstName";
        public static final String COLUMN_NAME_LAST_NAME = "lastname";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_PHOTO = "photo";
    }
}
