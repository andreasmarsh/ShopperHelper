package edu.uga.cs.shopperhelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ModifyPurchaseActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "ModifyPurchase";

    private TextView name;
    private TextView description;

    ShoppingItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_purchased_item);
        item = (ShoppingItem) getIntent().getSerializableExtra("obj");

        //Log.d(DEBUG_TAG, "xxxxxxxxxxxxxxxxxxxxxxxxxxx" + item.getItemName());

//        name = findViewById( R.id.itemName);
//        description = findViewById( R.id.itemDescription);

        Button editButton = findViewById(R.id.button1);
        Button undoButton = findViewById(R.id.button2);

        name = (TextView) findViewById(R.id.textView);
        description = (TextView) findViewById(R.id.textView2);

        editButton.setOnClickListener(new ModifyPurchaseActivity.editButtonClickListener());
        undoButton.setOnClickListener(new ModifyPurchaseActivity.undoButtonClickListener());

        name.setText(item.getItemName());
        description.setText(item.getItemDescription());

    }

    private class editButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), UpdatePurchaseActivity.class);
            intent.putExtra("obj", item);
            view.getContext().startActivity(intent);
        }
    }

    private class undoButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("purchaseditems");

            // delete item from puchasedList
            myRef.orderByChild("itemName").equalTo(item.getItemName()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        postSnapshot.getRef().removeValue();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(DEBUG_TAG, "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            });

            // put item into shoppingList
            DatabaseReference myRef2 = database.getReference("shoppingitems");

            // First, a call to push() appends a new node to the existing list (one is created
            // if this is done for the first time).  Then, we set the value in the newly created
            // list node to store the new job lead.
            // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
            // the previous apps to maintain job leads.
            myRef2.push().setValue(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Show a quick confirmation
                            Toast.makeText(getApplicationContext(), "Moved " + item.getItemName() + " to Shopping List",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to create a Job lead for " + item.getItemName(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

            Intent intent = new Intent(view.getContext(), ShoppingItemManagementActivity.class);
            view.getContext().startActivity(intent);
        }
    }


    // These activity callback methods are not needed and are for educational purposes only
    @Override
    protected void onStart() {
        Log.d(DEBUG_TAG, "JobLead: MainActivity.onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(DEBUG_TAG, "JobLead: MainActivity.onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(DEBUG_TAG, "JobLead: MainActivity.onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(DEBUG_TAG, "JobLead: MainActivity.onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(DEBUG_TAG, "JobLead: MainActivity.onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d(DEBUG_TAG, "JobLead: MainActivity.onRestart()");
        super.onRestart();
    }
}
