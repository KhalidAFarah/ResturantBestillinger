package com.oslomet.s341843.database;

import android.content.Context;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    //Resturant tabellen
    private static final String TABLE_RESTURANT = "Resturanter";
    private static final String RESTURANT_KEY_ID = "_ID";
    private static final String RESTURANT_KEY_NAME = "Name";
    private static final String RESTURANT_KEY_ADRESS = "Adresse";
    private static final String RESTURANT_KEY_TLF = "Tlf";
    private static final String RESTURANT_KEY_TYPE = "Type";

    //Kontakt tabellen
    private static final String TABLE_KONTAKTER = "Kontakter";
    private static final String KONTAKTER_KEY_ID = "_ID";
    private static final String KONTAKTER_KEY_NAME = "Name";
    private static final String KONTAKTER_KEY_TLF = "Tlf";

    //Bestilling tabellen
    private static final String TABLE_BESTILLING = "Bestillinger";
    private static final String BESTILLING_KEY_ID = "_ID";
    private static final String BESTILLING_KEY_RESTURANT_ID = "Resturant ID";
    private static final String BESTILLING_KEY_KONTAKT_ID = "Kontakt ID";
    private static final String BESTILLING_KEY_DATE = "Date";

    //Databasen
    private static final String DATABASE_NAME = "Resturantbestillinger";
    private static int DATABASE_VERSION = 1;

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String lagResturantTabell = "CREATE TABLE " + TABLE_RESTURANT + "("
                + RESTURANT_KEY_ID + " INTEGER PRIMARY KEY," + RESTURANT_KEY_NAME + " TEXT,"
                + RESTURANT_KEY_ADRESS + " TEXT," + RESTURANT_KEY_TLF + " TEXT,"
                + RESTURANT_KEY_TYPE + " TEXT)";

        final String lagKontaktTabell = "CREATE TABLE " + TABLE_KONTAKTER + "("
                + KONTAKTER_KEY_ID + " INTEGER PRIMARY KEY," + KONTAKTER_KEY_NAME + " TEXT,"
                + KONTAKTER_KEY_TLF + " TEXT)";

        final   String lagBestillingTabell = "CREATE TABLE " + TABLE_BESTILLING + "("
                + BESTILLING_KEY_ID + " INTEGER PRIMARY KEY"
                + BESTILLING_KEY_RESTURANT_ID + " INTEGER,"
                + BESTILLING_KEY_KONTAKT_ID + " INTEGER,"
                + BESTILLING_KEY_DATE + " DATE,"

                + "FOREIGN KEY (" + BESTILLING_KEY_RESTURANT_ID + ") "
                + "REFERENCES " + TABLE_RESTURANT + "(" + RESTURANT_KEY_ID + ")),"

                + "FOREIGN KEY (" + BESTILLING_KEY_KONTAKT_ID + ") "
                + "REFERENCES " + TABLE_KONTAKTER + "(" + KONTAKTER_KEY_ID + "));";

        Log.d("DB", "lager Resturant tabellen: " + lagResturantTabell);
        Log.d("DB", "lager Kontakt tabellen: " + lagKontaktTabell);
        Log.d("DB", "lager Bestilling tabellen: " + lagBestillingTabell);

        sqLiteDatabase.execSQL(lagResturantTabell);
        sqLiteDatabase.execSQL(lagKontaktTabell);
        sqLiteDatabase.execSQL(lagBestillingTabell);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Dropper tabellene
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTURANT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_KONTAKTER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BESTILLING);

        onCreate(sqLiteDatabase);
    }

    //Registrering, Henting, Oppdater og Sletting for Resturant tabellen
    public void registrerResturant(Resturant resturant){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RESTURANT_KEY_NAME, resturant.getName());
        values.put(RESTURANT_KEY_ADRESS, resturant.getAdress());
        values.put(RESTURANT_KEY_TLF, resturant.getTlf());
        values.put(RESTURANT_KEY_TYPE, resturant.getType());

        db.insert(TABLE_RESTURANT, null, values);
        db.close();
    }
    public List<Resturant> hentAlleResturanter(){
        List<Resturant> resturanter = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_RESTURANT;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                resturanter.add(new Resturant(cursor.getLong(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            }while(cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return resturanter;
    }
    public Resturant hentResturant(long id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_RESTURANT, new String[]{RESTURANT_KEY_ID, RESTURANT_KEY_NAME,
                RESTURANT_KEY_ADRESS, RESTURANT_KEY_TLF, RESTURANT_KEY_TYPE},
                RESTURANT_KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if(cursor == null) return null;

        cursor.moveToFirst();
        Resturant resturant = new Resturant(cursor.getLong(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return resturant;
    }
    public int oppdaterResturant(Resturant resturant){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RESTURANT_KEY_NAME, resturant.getName());
        values.put(RESTURANT_KEY_ADRESS, resturant.getAdress());
        values.put(RESTURANT_KEY_TLF, resturant.getTlf());
        values.put(RESTURANT_KEY_TYPE, resturant.getType());

        int oppdatert = db.update(TABLE_RESTURANT, values, RESTURANT_KEY_ID + "= ?",
                new String[]{String.valueOf(resturant.getId())});
        return  oppdatert;
    }
    public void slettResturant(long id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_RESTURANT, RESTURANT_KEY_ID + " =?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    //Registrering, Henting, Oppdatering og Sletting for Kontakter tabellen
    public void registrerKontakter(Kontakt kontakter){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KONTAKTER_KEY_NAME, kontakter.getName());
        values.put(KONTAKTER_KEY_TLF, kontakter.getTlf());

        db.insert(TABLE_KONTAKTER, null, values);
        db.close();
    }
    public List<Kontakt> hentAlleKontakter(){
        List<Kontakt> kontakter = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_RESTURANT;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                kontakter.add(new Kontakt(cursor.getLong(0),
                        cursor.getString(1), cursor.getString(2)));
            }while(cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return kontakter;
    }
    public Kontakt hentKontakt(long id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_KONTAKTER, new String[]{KONTAKTER_KEY_ID, KONTAKTER_KEY_NAME,
                        KONTAKTER_KEY_TLF},
                KONTAKTER_KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if(cursor == null) return null;

        cursor.moveToFirst();
        Kontakt kontakt = new Kontakt(cursor.getLong(0),
                cursor.getString(1), cursor.getString(2));
        return kontakt;
    }
    public int oppdaterKontakt(Kontakt kontakt){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KONTAKTER_KEY_NAME, kontakt.getName());
        values.put(KONTAKTER_KEY_TLF, kontakt.getTlf());

        int oppdatert = db.update(TABLE_KONTAKTER, values, KONTAKTER_KEY_ID + "= ?",
                new String[]{String.valueOf(kontakt.getId())});
        return  oppdatert;
    }
    public void slettKontakt(long id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_KONTAKTER, KONTAKTER_KEY_ID + " =?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    //Registrering, Henting for Bestillinger tabellen
    public void registrerBestilling(Bestilling bestilling){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BESTILLING_KEY_RESTURANT_ID, bestilling.getResturantID());
        values.put(BESTILLING_KEY_KONTAKT_ID, bestilling.getKontaktID());
        values.put(BESTILLING_KEY_DATE, bestilling.getDate().toString());

        db.insert(TABLE_BESTILLING, null, values);
        db.close();
    }
    public List<Bestilling> hentAlleBestillinger(){
        List<Bestilling> bestillinger = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_BESTILLING;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                bestillinger.add(new Bestilling(cursor.getLong(0),
                        cursor.getLong(1), cursor.getLong(2), Date.valueOf(cursor.getString(3))));//hvordan henter vi DATE fra sqlite
            }while(cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return bestillinger;
    }
    public Bestilling hentBestilling(long id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_BESTILLING, new String[]{BESTILLING_KEY_ID, BESTILLING_KEY_RESTURANT_ID,
                        BESTILLING_KEY_KONTAKT_ID, BESTILLING_KEY_DATE},
                BESTILLING_KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if(cursor == null) return null;

        cursor.moveToFirst();
        Bestilling bestilling = new Bestilling(cursor.getLong(0),
                cursor.getLong(1), cursor.getLong(2), Date.valueOf(cursor.getString(3)));//hvordan henter vi DATE fra sqlite
        return bestilling;
    }

}
