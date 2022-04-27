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

public class PurchasedItemRecyclerAdapter extends RecyclerView.Adapter<PurchasedItemRecyclerAdapter.PurchasedItemHolder> {

    public static final String DEBUG_TAG = "SIRecyclerAdapter";

    private List<ShoppingItem> purchasedItemList;

    public PurchasedItemRecyclerAdapter( List<ShoppingItem> shoppingItemList ) {
        this.purchasedItemList = shoppingItemList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class PurchasedItemHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemDescription;
        TextView itemPrice;
        Button moreButton;

        public PurchasedItemHolder(View itemView ) {
            super(itemView);

            itemName = (TextView) itemView.findViewById( R.id.itemName );
            itemDescription = (TextView) itemView.findViewById( R.id.itemDescription );
            itemPrice = (TextView) itemView.findViewById( R.id.itemPrice);
            moreButton = (Button) itemView.findViewById( R.id.button4);
        }
    }

    @Override
    public PurchasedItemHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.purchased_item, parent, false );
        return new PurchasedItemHolder( view );
    }

    // This method fills in the values of the Views to show a JobLead
    @Override
    public void onBindViewHolder(PurchasedItemHolder holder, final int position ) {
        final ShoppingItem shoppingItem = purchasedItemList.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + shoppingItem );

        holder.itemName.setText( shoppingItem.getItemName());
        holder.itemDescription.setText( shoppingItem.getItemDescription());

        holder.itemPrice.setText( shoppingItem.getPrice().toString());

        holder.moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ModifyPurchaseActivity.class);
                intent.putExtra("obj", shoppingItem);
                view.getContext().startActivity( intent );
            }
        });
    }

    @Override
    public int getItemCount() {
        return purchasedItemList.size();
    }
}
