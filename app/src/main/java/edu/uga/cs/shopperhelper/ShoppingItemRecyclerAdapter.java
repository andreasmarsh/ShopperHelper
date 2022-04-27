package edu.uga.cs.shopperhelper;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ShoppingItemRecyclerAdapter extends RecyclerView.Adapter<ShoppingItemRecyclerAdapter.ShoppingItemHolder> {

    public static final String DEBUG_TAG = "SIRecyclerAdapter";

    private List<ShoppingItem> shoppingItemList;

    public ShoppingItemRecyclerAdapter( List<ShoppingItem> shoppingItemList ) {
        this.shoppingItemList = shoppingItemList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class ShoppingItemHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemDescription;
        Button moreButton;
//        TextView url;
//        TextView comments;

        public ShoppingItemHolder(View itemView ) {
            super(itemView);

            itemName = (TextView) itemView.findViewById( R.id.itemName );
            itemDescription = (TextView) itemView.findViewById( R.id.itemDescription );
            moreButton = (Button) itemView.findViewById( R.id.button4);
//            url = (TextView) itemView.findViewById( R.id.url );
//            comments = (TextView) itemView.findViewById( R.id.comments );
        }
    }

    @Override
    public ShoppingItemHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.shopping_item, parent, false );
        //moreButton = findViewById( R.id.button4 );
        return new ShoppingItemHolder( view );
    }

    // This method fills in the values of the Views to show a JobLead
    @Override
    public void onBindViewHolder(ShoppingItemHolder holder, final int position ) {
        final ShoppingItem shoppingItem = shoppingItemList.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + shoppingItem );

        holder.itemName.setText( shoppingItem.getItemName());
        holder.itemDescription.setText( shoppingItem.getItemDescription());

        holder.moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //shoppingItem.setItemName("Lego");

//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("shoppingitems");
//                myRef.orderByChild("itemName").equalTo(shoppingItem.getItemName()).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Getting Post failed, log a message
//                        Log.w(DEBUG_TAG, "loadPost:onCancelled", databaseError.toException());
//                        // ...
//                    }
//                });

                Intent intent = new Intent(view.getContext(), ModifyItemActivity.class);
                intent.putExtra("obj", shoppingItem);
                view.getContext().startActivity( intent );
            }
        });
//        holder.url.setText( jobLead.getUrl() );
//        holder.comments.setText( jobLead.getComments() );
    }

    @Override
    public int getItemCount() {
        return shoppingItemList.size();
    }
}
