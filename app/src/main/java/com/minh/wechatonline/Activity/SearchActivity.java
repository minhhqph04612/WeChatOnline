package com.minh.wechatonline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.minh.wechatonline.Holder.SearchHolder;
import com.minh.wechatonline.R;
import com.minh.wechatonline.model.SearchUser;

public class SearchActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private EditText edtSearch;
    private ImageView imageView;
    private DatabaseReference userDatabase;

    private RecyclerView search_list;

    private String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        userDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        imageView = (ImageView) findViewById(R.id.img_btn_search);
        edtSearch = (EditText) findViewById(R.id.edt_search);
        search_list = (RecyclerView) findViewById(R.id.list_search);
        search_list.setHasFixedSize(true);
        search_list.setLayoutManager(new LinearLayoutManager(this));
//        search_list = (ListView) findViewById(R.id.list_search);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(search)) {
                    Toast.makeText(SearchActivity.this, "Please write a user", Toast.LENGTH_SHORT).show();
                }
                search = edtSearch.getText().toString();
                searchFriends(search);
            }
        });


    }

    private void searchFriends(String search) {
        Toast.makeText(SearchActivity.this, "Searching...", Toast.LENGTH_SHORT).show();
        Query search_query = userDatabase.orderByChild("email").startAt(search).endAt(search + "\uf8ff");

        FirebaseRecyclerAdapter<SearchUser, SearchHolder> adapter = new FirebaseRecyclerAdapter<SearchUser, SearchHolder>(
                SearchUser.class,
                R.layout.show_search_friend,
                SearchHolder.class,
                search_query
        ) {
            @Override
            protected void populateViewHolder(SearchHolder viewHolder, SearchUser searchUser, int position) {
                final String user_id = getRef(position).getKey();
                viewHolder.setEmail(searchUser.getEmail());
                viewHolder.setStatus(searchUser.getStatus());
                viewHolder.setImage(searchUser.getImage(),SearchActivity.this);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SearchActivity.this, UserActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                    }
                });
            }
        };
        search_list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return  true;
        }
        return true;
    }
}
