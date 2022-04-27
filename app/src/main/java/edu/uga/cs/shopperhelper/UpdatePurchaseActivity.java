package edu.uga.cs.shopperhelper;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

public class UpdatePurchaseActivity extends AppCompatActivity {
    public static final String DEBUG_TAG = "NewJobLeadActivity";

    private EditText itemNameView;
    private EditText itemTotalView;
    private Button saveButton;
    ShoppingItem item;
    private String itemName;
    private Double itemTotal;

    //private JobLeadsData jobLeadsData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shopping_item);
        item = (ShoppingItem)getIntent().getSerializableExtra("obj");
        itemNameView = (EditText) findViewById( R.id.editText1 );
        itemTotalView = (EditText) findViewById( R.id.editText2 );
        itemTotalView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        itemTotalView.setHint("Enter Updated Total Price");

        saveButton = (Button) findViewById( R.id.button );

        saveButton.setOnClickListener( new UpdatePurchaseActivity.ButtonClickListener()) ;
    }

    class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick (View v){

            if (!itemTotalView.getText().toString().matches("")) {
                itemTotal = Double.parseDouble(itemTotalView.getText().toString());
                itemName = itemNameView.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("purchaseditems");
                myRef.orderByChild("itemName").equalTo(item.getItemName()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            if (item.getItemName() != itemName && !itemName.equals("")) {
                                postSnapshot.getRef().child("itemName").setValue(itemName);
                                Log.d( DEBUG_TAG, "ooooooooooooooooooooooooooo" );
                            }
                            if (item.getPrice() != itemTotal) {
                                postSnapshot.getRef().child("price").setValue(itemTotal);
                                Log.d( DEBUG_TAG, "ooooooooooooooooooooooooooo00000000000000" );
                            }
                            break;
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

                Intent intent = new Intent(v.getContext(), ShoppingItemManagementActivity.class);
                v.getContext().startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "Enter at least Price for " + item.getItemName(),
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



