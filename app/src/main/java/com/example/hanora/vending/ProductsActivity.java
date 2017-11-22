package com.example.hanora.vending;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    final private ArrayList<Product> productsList = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        final ProductsListViewAdapter listViewAdapter = new ProductsListViewAdapter(productsList);
        ListView productsListView = findViewById(R.id.products_listView);
        productsListView.setAdapter(listViewAdapter);
        String vendingMahcineKey = getIntent().getStringExtra("VENDING_MACHINE_KEY");
        //Log.d("test", vendingMahcineKey);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/VendingMachines/" + vendingMahcineKey);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot product : dataSnapshot.getChildren()) {
                    Product p = product.getValue(Product.class);
                    productsList.add(p);
                    listViewAdapter.notifyDataSetChanged();
                    Log.d("test_db", p.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("test_db", "The read failed: " + databaseError.getCode());
            }
        });
    }


    private class ProductsListViewAdapter extends BaseAdapter {
        private ArrayList<Product> productsList;

        private ProductsListViewAdapter(ArrayList<Product> productsList) {
            this.productsList = productsList;
        }


        @Override
        public int getCount() {
            return productsList.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layout_product_card, null);

            final Product p = productsList.get(position);

            TextView name = (TextView) myView.findViewById(R.id.name);
            name.setText(p.getName());

            TextView expireDate = (TextView) myView.findViewById(R.id.expireDate);
            expireDate.setText(p.getExpireDate());

            TextView disc = (TextView) myView.findViewById(R.id.discription);
            disc.setText(p.getDescription());

            TextView logo = (TextView) myView.findViewById(R.id.image);
            logo.setText(p.getBrandLogo());

            return myView;
        }

    }
}


