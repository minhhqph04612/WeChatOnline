package com.minh.wechatonline.News;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.minh.wechatonline.Activity.SearchActivity;
import com.minh.wechatonline.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    private ListView listNews;
    private NewsAdapter adapter;
    private final String news = "https://vnexpress.net/rss/thoi-su.rss";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listNews = (ListView) findViewById(R.id.listNews);
        listNews.setOnItemClickListener(onItemClick);
        new LoadRss().execute(news);
    }
    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                long arg3) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(NewsActivity.this,NewDetailActivity.class);
            intent.putExtra("LINK", adapter.getItem(position).getLink());
            startActivity(intent);
        }

    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.all_user_action_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.action_search:
                startActivity(new Intent(NewsActivity.this, SearchActivity.class));
                return true;
        }
        return true;
    }
    class LoadRss extends AsyncTask<String, Void, ArrayList<NewObject>>

    {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            dialog = new ProgressDialog(NewsActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }
        @Override
        protected ArrayList<NewObject> doInBackground(String... params) {
            // TODO Auto-generated method stub
            String url = params[0];
            ArrayList<NewObject> newsObjects = new ArrayList<NewObject>();
            try{
                Document doc = Jsoup.connect(url).get();
                Elements elements = doc.select("item");
                for(Element item : elements){


                    String title = item.select("title").text();
                    //String pubDate  = item.select("pubDate").text();
                    String link = item.select("link").text();
                    //String guid = item.select("guid").text();

                    String  description = item.select("description").text();



                    //html => dùng Jsoup parser để lấy ảnh
                    Document docImage =  Jsoup.parse(description);
                    String imageURL =  docImage.select("img").get(0).attr("src");

                    newsObjects.add(new NewObject(title, link, imageURL));
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            return newsObjects;
        }
        @Override
        protected void onPostExecute(ArrayList<NewObject> truyenObjects) {
            // TODO Auto-generated method stub
            dialog.dismiss();
            adapter = new NewsAdapter(NewsActivity.this, 0, truyenObjects, getLayoutInflater());
            listNews.setAdapter(adapter);
        }
    }
}
