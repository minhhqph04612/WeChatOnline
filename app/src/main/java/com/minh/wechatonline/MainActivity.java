package com.minh.wechatonline;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minh.wechatonline.Friends.FriendsActivity;
import com.minh.wechatonline.LoginAndRegister.SignInActivity;
import com.minh.wechatonline.Message.MessagersActivity;
import com.minh.wechatonline.News.NewsActivity;

public class MainActivity extends TabActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    TextView tvUserEmail;
    //Button btnLogout;
    //private FrameLayout mMainFrame;
    //private TextView mTextMessage;
    //bitch

    //private BottomNavigationView bottomNavigationView;
    private FrameLayout mainFrame;


    /*private NewsFragment  newsFragment ;
    private MessagersFragment messagersFragment;
    private FriendsFragment friendsFragment;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*mTextMessage = (TextView) findViewById(R.id.message);
*/
        //mainFrame =(FrameLayout) findViewById(R.id.main_frame);
        //bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        //khoi tao fragment
        /*newsFragment = new NewsFragment();
        messagersFragment = new MessagersFragment();
        friendsFragment = new FriendsFragment();*/


        /*bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_news:
                        setFragment(newsFragment);

                        return true;
                    case R.id.navigation_messegers:

                        setFragment(messagersFragment);
                        return true;
                    case R.id.navigation_friends:
                        setFragment(friendsFragment);
                        return true;
                    default:
                        return false;
                }

            }
        });*/
        //Tabhost
        TabHost tabHost = getTabHost();

        // Tab for News
        TabHost.TabSpec newspec = tabHost.newTabSpec("News");
        newspec.setIndicator("News",getResources().getDrawable(R.drawable.ic_art_track_black_24dp));
        Intent photosIntent = new Intent(this, NewsActivity.class);
        newspec.setContent(photosIntent);

        // Tab for Messagers
        TabHost.TabSpec messagersspec = tabHost.newTabSpec("Messagers");
        // setting Title and Icon for the Tab
        messagersspec.setIndicator("Messagers",getResources().getDrawable(R.drawable.ic_chat_bubble_black_24dp));
        Intent songsIntent = new Intent(this,  MessagersActivity.class);
        messagersspec.setContent(songsIntent);

        // Tab for Friends
        TabHost.TabSpec friendsspec = tabHost.newTabSpec("Friends");
        friendsspec.setIndicator("Friends",getResources().getDrawable(R.drawable.ic_people_black_24dp));
        Intent videosIntent = new Intent(this, FriendsActivity.class);
        friendsspec.setContent(videosIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(newspec); // Adding photos tab
        tabHost.addTab(messagersspec); // Adding songs tab
        tabHost.addTab(friendsspec); // Adding videos tab
        //fireAuth
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }else{
            Toast.makeText(this,
                    "Welcome " + firebaseAuth
                            .getCurrentUser().getEmail(),
                    Toast.LENGTH_LONG)
                    .show();

            // Load chat room contents

        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        /*tvUserEmail = (TextView) findViewById(R.id.useremail);
        tvUserEmail.setText("Welcome to " + firebaseUser.getEmail());*/
        //btnLogout = (Button) findViewById(R.id.sign_out);



    }

   /* private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }
*/
    public void Signout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this, SignInActivity.class));
    }
    public void chat(View view) {
        //startActivity(new Intent(MainActivity.this, MessageActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
//        MenuItem shareItem = menu.findItem(R.id.action_settings);
//        mShareActionProvider = (ShareActionProvider)
//                MenuItemCompat.getActionProvider(shareItem);
//        mShareActionProvider.setShareIntent(getDefaultIntent());

        return true;
    }

//    private Intent getDefaultIntent() {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("image/*");
//        return intent;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //int i = item.getItemId();
        switch(item.getItemId()){
            case R.id.action_settings:

                return true;
            case R.id.action_sign_out:
               Signout();
                return true;
            default:
               return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }
}
