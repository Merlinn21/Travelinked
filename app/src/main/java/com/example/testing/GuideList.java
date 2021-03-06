package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GuideList extends AppCompatActivity {
    ListView guideList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_list);

        guideList = findViewById(R.id.listGuide);

        final ArrayList<String> list = new ArrayList<>();
        final ArrayList<String> guideArray = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_guide,list);
        guideList.setAdapter(adapter);
        final String location = getIntent().getStringExtra("Location");

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Destination").child(location);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String Fname = snapshot.child("FirstName").getValue().toString();
                    String Lname = snapshot.child("LastName").getValue().toString();
                    guideArray.add(snapshot.getKey());
                    list.add(Fname + " " + Lname);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        guideList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GuideList.this, guideProfile2.class);
                intent.putExtra("guideId",guideArray.get(position));
                intent.putExtra("Location",location);
                startActivity(intent);
            }
        });
    }
    private void ToastMaker(String text){
        Toast.makeText(GuideList.this,text, Toast.LENGTH_SHORT).show();
    }
}
