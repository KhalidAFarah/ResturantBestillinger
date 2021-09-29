package com.oslomet.s341843;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.oslomet.s341843.database.DBHandler;
import com.oslomet.s341843.database.Resturant;
import com.oslomet.s341843.database.ResturantType;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DBHandler db;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(getApplicationContext());
        list = (ListView) findViewById(R.id.list);
    }

    public void registerResturant(){
        //Resturant resturant = new Resturant("MM", "kl street", "123123132", ResturantType.FAST_FOOD);
        Resturant resturant = new Resturant("MM", "kl street", "123123132", "Fast food");
        db.registrerResturant(resturant);
        Log.d("Legg inn", "la til en ny resturant: " + resturant);
    }

    public void getResturanter(){
        List<Resturant> resturanter = db.hentAlleResturanter();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, resturanter);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        list.setOnItemLongClickListener();


    }
    public void getKontakter(){

    }
    public void getBestillinger(){

    }
}