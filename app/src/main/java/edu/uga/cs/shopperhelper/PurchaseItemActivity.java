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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PurchaseItemActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = "Purchase Activity";

    private TextView name;
    private TextView description;
    private EditText itemPrice;

    ShoppingItem item;

    private Button purchaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_shopping_item);
        item = (ShoppingItem)getIntent().getSerializableExtra("obj");

        name = (TextView) findViewById(R.id.textView);
        description = (TextView) findViewById(R.id.textView2);

        name.setText("Purchase " + item.getItemName() + "?");
        description.setText(item.getItemDescription());

        itemPrice = (EditText) findViewById( R.id.editText );
        purchaseButton = (Button) findViewById( R.id.button );

        // Create a JobLeadsData instance, since we will need to save a new JobLead to the service.
        //jobLeadsData = new JobLeadsData( this );

        purchaseButton.setOnClickListener( new PurchaseItemActivity.ButtonClickListener()) ;
    }


    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("shoppingitems");

            // delete item from shoppingList
            myRef.orderByChild("itemName").equalTo(item.getItemName()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        // check if a valid price was input
                        if (!itemPrice.getText().toString().matches("")) {
                            //delete object
                            postSnapshot.getRef().removeValue();
                        } else {
                            // Show a quick confirmation
                            Toast.makeText(getApplicationContext(), "Please Input a Price for " + item.getItemName(),
                                    Toast.LENGTH_SHORT).show();
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

            if (!itemPrice.getText().toString().matches("")) {
                // put item into purchasedList
                DatabaseReference myRef2 = database.getReference("purchaseditems");
                item.setPrice(Double.parseDouble(itemPrice.getText().toString()));
                item.setItemName(item.getItemName() + " ");

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                item.setWhoPurchased(user.getEmail());
                Log.w(DEBUG_TAG, "------------------ PRICE -->" + item.getPrice().toString());

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
                                Toast.makeText(getApplicationContext(), "Purchased Item created for " + item.getItemName(),
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

                Intent intent = new Intent(v.getContext(), ShoppingItemManagementActivity.class);
                v.getContext().startActivity(intent);
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
