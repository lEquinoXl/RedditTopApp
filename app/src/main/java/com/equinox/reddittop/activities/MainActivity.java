package com.equinox.reddittop.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.equinox.reddittop.R;
import com.equinox.reddittop.models.Post;
import com.equinox.reddittop.models.PostAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Post> posts = new ArrayList<>();
    private LinearLayoutManager manager;
    private ProgressBar progressBar;
    private PostAdapter adapter;
    private Boolean is_scrolling = false;
    int current_items, total_items, scrolled_out_items;
    int page_size = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.viewArticles);
        progressBar = findViewById(R.id.progressBar);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    is_scrolling = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                current_items = manager.getChildCount();
                total_items = manager.getItemCount();
                scrolled_out_items = manager.findFirstVisibleItemPosition();

                if (is_scrolling && (total_items == current_items + scrolled_out_items)) {
                    progressBar.setVisibility(View.VISIBLE);
                    is_scrolling = false;
                    new GetData().execute(getString(R.string.source));
                }
            }
        });

        PostAdapter.OnPhotoClickListener photoClickListener = new PostAdapter.OnPhotoClickListener() {
            @Override
            public void onPhotoClick(String image_source) {
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                intent.putExtra("image_source", image_source);

                startActivity(intent);
            }
        };

        adapter = new PostAdapter(posts, photoClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        posts.clear();
        progressBar.setVisibility(View.VISIBLE);
        new GetData().execute(getString(R.string.source));
    }

    private class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                reader.close();
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ArrayList<String> json_posts = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonPosts = jsonObject.getJSONObject("data").getJSONArray("children");
                if (jsonPosts != null) {
                    int size = posts.size() + page_size;
                    for (int i = posts.size(); i < size; i++) {
                        System.out.println(posts.size());
                        if (i >= jsonPosts.length()) {
                            Toast.makeText(MainActivity.this, "End", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        Post post = new Post();
                        json_posts.add(jsonPosts.getString(i));
                        post.setAuthor_name(jsonPosts.getJSONObject(i).getJSONObject("data").getString("subreddit"));
                        post.setCreation_date(jsonPosts.getJSONObject(i).getJSONObject("data").getDouble("created_utc"));
                        post.setThumbnail_source(jsonPosts.getJSONObject(i).getJSONObject("data").getString("thumbnail"));
                        post.setTitle(jsonPosts.getJSONObject(i).getJSONObject("data").getString("title"));
                        post.setImage_source(jsonPosts.getJSONObject(i).getJSONObject("data").getString("url_overridden_by_dest"));
                        post.setComments_count(jsonPosts.getJSONObject(i).getJSONObject("data").getString("num_comments"));
                        posts.add(post);
                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return;
        }
    }
}
