package edu.uga.cs.shopperhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ShoppingItemManagementActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "ManagementActivity";

    private Button reviewItemsButton;
    private Button purchasedItemsButton;
    private TextView signedInTextView;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_item_management);

        Log.d( DEBUG_TAG, "JobLeadManagementActivity.onCreate()" );

        reviewItemsButton = findViewById( R.id.button2 );
        purchasedItemsButton = findViewById( R.id.button3 );
        signedInTextView = findViewById( R.id.textView3 );

        reviewItemsButton.setOnClickListener( new ShoppingItemsButtonClickListener() );
        purchasedItemsButton.setOnClickListener( new PurchasedItemsButtonClickListener() );

        // Setup a listener for a change in the sign in status (authentication status change)
        // when it is invoked, check if a user is signed in and update the UI text view string,
        // as needed.
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if( currentUser != null ) {
                    // User is signed in
                    Log.d(DEBUG_TAG, "onAuthStateChanged:signed_in:" + currentUser.getUid());
                    String userEmail = currentUser.getEmail();
                    signedInTextView.setText( "Signed in as: " + userEmail );
                } else {
                    // User is signed out
                    Log.d( DEBUG_TAG, "onAuthStateChanged:signed_out" );
                    signedInTextView.setText( "Signed in as: not signed in" );
                }
            }
        });
    }

//    private class NewLeadButtonClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            Intent intent = new Intent(view.getContext(), NewShoppingItemActivity.class);
//            view.getContext().startActivity( intent );
//        }
//    }

    private class ShoppingItemsButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ReviewShoppingItemsActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class PurchasedItemsButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ReviewPurchasedItemsActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    // These activity callback methods are not needed and are for edational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "JobLead: ManagementActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "JobLead: ManagementActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "JobLead: ManagementActivity.onPause()" );
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "JobLead: ManagementActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "JobLead: ManagementActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "JobLead: ManagementActivity.onRestart()" );
        super.onRestart();
    }
}
