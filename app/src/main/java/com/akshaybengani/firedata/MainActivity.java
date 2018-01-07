package com.akshaybengani.firedata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextArtistName;
    private Spinner spinnerGenre;
    private Button buttonPush;

    //Here we created a DatabaseReference Variable used to create Database
    DatabaseReference databaseArtist;

    ListView listViewArtists;
    List<Artist> artistList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextArtistName=(EditText)findViewById(R.id.edittextArtistName);
        spinnerGenre=(Spinner)findViewById(R.id.spinnerGenre);
        buttonPush=(Button)findViewById(R.id.buttonPush);

        // This is for connecting FirebaseDatabase with DatabaseReference
        databaseArtist = FirebaseDatabase.getInstance().getReference("artists");
        listViewArtists=(ListView)findViewById(R.id.listviewArtist);

        artistList = new ArrayList<>();

        buttonPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addArtist();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseArtist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artistList.clear();
                for (DataSnapshot artistSnapshot:  dataSnapshot.getChildren())
                {
                    Artist artist= artistSnapshot.getValue(Artist.class);
                    artistList.add(artist);
                }
                ArtistList adapter = new ArtistList(MainActivity.this,artistList);
                listViewArtists.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addArtist()
    {
        String name = editTextArtistName.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();
        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(MainActivity.this,"You should add a name at least...",Toast.LENGTH_SHORT).show();
            return;
        }
            //This will create a unique key in the FirebaseDatabase
            String id = databaseArtist.push().getKey();

            //Now we will create an object of the class Artist through which we will pass the data
            //Here we pass the parameters which take value from the EditText and the Spinner
            Artist artist = new Artist(id,name,genre);

            //Now we will push the data inside the FirebaseDatabase using the DatabaseRefernce
            //Which we created in the onCreate method
            databaseArtist.child(id).setValue(artist);
            /*
            Here we used .child method this is used to create a child node in the artist node of the
            database to store the data uniquely and here we used the key attribute in the parameter
            and we used a .setValue methid where we pass the class Artist object
            */
            //Now we can show a toast to show data inserted
            Toast.makeText(MainActivity.this,"Artist Added",Toast.LENGTH_LONG);
        



    }



}
