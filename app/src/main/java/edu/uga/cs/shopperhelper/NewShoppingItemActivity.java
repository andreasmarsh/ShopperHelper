package edu.uga.cs.shopperhelper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewShoppingItemActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = "NewJobLeadActivity";

    private EditText itemNameView;
    private EditText itemDescriptionView;
    private Button saveButton;

    //private JobLeadsData jobLeadsData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shopping_item);

        itemNameView = (EditText) findViewById( R.id.editText1 );
        itemDescriptionView = (EditText) findViewById( R.id.editText2 );
        saveButton = (Button) findViewById( R.id.button );

        // Create a JobLeadsData instance, since we will need to save a new JobLead to the service.
        //jobLeadsData = new JobLeadsData( this );

        saveButton.setOnClickListener( new ButtonClickListener()) ;
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String itemName = itemNameView.getText().toString();
            String itemDescription = itemDescriptionView.getText().toString();
            Double itemPrice = 0.00;

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String whoAdded = user.getEmail();
            Log.d( DEBUG_TAG, "-------------------------- EMAIL --> " + user.getEmail() );

            String whoPurchased = "User";

            if (!itemName.equals("")) {
                final ShoppingItem shoppingItem = new ShoppingItem(itemName, itemDescription, itemPrice, whoAdded, whoPurchased);

                // Add a new element (JobLead) to the list of job leads in Firebase.
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("shoppingitems");
                //DatabaseReference myRef2 = database.getReference("purchaseditems");


                // First, a call to push() appends a new node to the existing list (one is created
                // if this is done for the first time).  Then, we set the value in the newly created
                // list node to store the new job lead.
                // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
                // the previous apps to maintain job leads.
                myRef.push().setValue(shoppingItem)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Show a quick confirmation
                                Toast.makeText(getApplicationContext(), "Shopping Item created for " + shoppingItem.getItemName(),
                                        Toast.LENGTH_SHORT).show();

                                // Clear the EditTexts for next use.
                                itemNameView.setText("");
                                itemDescriptionView.setText("");
                                //urlView.setText("");
                                //commentsView.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(getApplicationContext(), "Failed to create a Job lead for " + shoppingItem.getItemName(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(getApplicationContext(), "Please Input a Name",
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
