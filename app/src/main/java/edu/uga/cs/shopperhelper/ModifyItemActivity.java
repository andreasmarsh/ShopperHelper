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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ModifyItemActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "ModifyItem";

    private TextView name;
    private TextView description;

    ShoppingItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_shopping_item);
        item = (ShoppingItem)getIntent().getSerializableExtra("obj");

        Log.d( DEBUG_TAG, "xxxxxxxxxxxxxxxxxxxxxxxxxxx" + item.getItemName());

//        name = findViewById( R.id.itemName);
//        description = findViewById( R.id.itemDescription);

        Button updateButton = findViewById( R.id.button1 );
        Button purchaseButton = findViewById( R.id.button2 );
        Button deleteButton = findViewById( R.id.button3 );

        name = (TextView) findViewById(R.id.textView);
        description = (TextView) findViewById(R.id.textView2);

        updateButton.setOnClickListener( new ModifyItemActivity.UpdateButtonClickListener() );
        purchaseButton.setOnClickListener( new ModifyItemActivity.PurchaseButtonClickListener() );
        deleteButton.setOnClickListener( new ModifyItemActivity.DeleteButtonClickListener() );

        name.setText(item.getItemName());
        description.setText(item.getItemDescription());

    }

    private class UpdateButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick( View view ) {
            Intent intent = new Intent(view.getContext(), UpdateItemActivity.class);
            Log.d( DEBUG_TAG, "xxxxxxxxxxxxxxxxxxxxxxxxxxxOPENING UPDATEITEMACTIVITY" );

            intent.putExtra("obj", item);
            view.getContext().startActivity( intent );
        }
    }

    private class PurchaseButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d( DEBUG_TAG, "xxxxxxxxxxxxxxxxxxxxxxxxxxxOPENING PURCHASE ACTIVITY" );
            Intent intent = new Intent(view.getContext(), PurchaseItemActivity.class);
            intent.putExtra("obj", item);
            view.getContext().startActivity( intent );
        }
    }

    private class DeleteButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            Log.d( DEBUG_TAG, "xxxxxxxxxxxxxxxxxxxxxxxxxxx DELETED" );

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("shoppingitems");
            myRef.orderByChild("itemDescription").equalTo(item.getItemDescription()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
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

            Toast.makeText(getApplicationContext(), "Deleted " + item.getItemName(),
                    Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(view.getContext(), ReviewShoppingItemsActivity.class);
//            view.getContext().startActivity(intent);
        }
    }


    // These activity callback methods are not needed and are for educational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "JobLead: MainActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "JobLead: MainActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "JobLead: MainActivity.onPause()" );
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "JobLead: MainActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "JobLead: MainActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "JobLead: MainActivity.onRestart()" );
        super.onRestart();
    }
}
