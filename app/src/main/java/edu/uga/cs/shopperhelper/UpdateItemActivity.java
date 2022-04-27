package edu.uga.cs.shopperhelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateItemActivity extends AppCompatActivity {
    public static final String DEBUG_TAG = "NewJobLeadActivity";

    private EditText itemNameView;
    private EditText itemDescriptionView;
    //    private EditText itemPriceView;
//    private EditText whoAddedView;
//    private EditText whoPurchasedView;
    private Button saveButton;
    ShoppingItem item;
    private String itemName;
    private String itemDescription;

    //private JobLeadsData jobLeadsData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shopping_item);
        item = (ShoppingItem)getIntent().getSerializableExtra("obj");
        itemNameView = (EditText) findViewById( R.id.editText1 );
        itemDescriptionView = (EditText) findViewById( R.id.editText2 );
        //urlView = (EditText) findViewById( R.id.editText3 );
        //commentsView = (EditText) findViewById( R.id.editText4 );
        saveButton = (Button) findViewById( R.id.button );

        // Create a JobLeadsData instance, since we will need to save a new JobLead to the service.
        //jobLeadsData = new JobLeadsData( this );

        saveButton.setOnClickListener( new UpdateItemActivity.ButtonClickListener()) ;
    }

        class ButtonClickListener implements View.OnClickListener {

                @Override
                public void onClick (View v){
                        itemName = itemNameView.getText().toString();
                        itemDescription = itemDescriptionView.getText().toString();

                        if (!itemName.equals("")) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("shoppingitems");
                            myRef.orderByChild("itemName").equalTo(item.getItemName()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        if (item.getItemName() != itemName) {
                                            postSnapshot.getRef().child("itemName").setValue(itemName);
                                        }
                                        if (item.getItemDescription() != itemDescription) {
                                            postSnapshot.getRef().child("itemDescription").setValue(itemDescription);
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Getting Post failed, log a message
                                    Log.w(DEBUG_TAG, "loadPost:onCancelled", databaseError.toException());
                                    // ...
                                }
                            });
                            Toast.makeText(getApplicationContext(), "Updated " + item.getItemName(),
                                    Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(v.getContext(), ReviewShoppingItemsActivity.class);
//                v.getContext().startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter a New Name/Description for " + item.getItemName(),
                                    Toast.LENGTH_SHORT).show();
                        }
            }
        }



    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onPause()" );
        super.onPause();
    }

    // The following activity callback methods are not needed and are for educational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onRestart()" );
        super.onRestart();
    }
}



