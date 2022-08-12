package com.equinox.reddittop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.equinox.reddittop.models.Post;
import com.equinox.reddittop.models.PostAdapter;
import com.equinox.reddittop.models.Result;

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
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Post> posts = new ArrayList<>();
    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.viewArticles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PostAdapter(posts);
        recyclerView.setAdapter(adapter);
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
            posts.clear();
            ArrayList<String> json_posts = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonPosts = jsonObject.getJSONObject("data").getJSONArray("children");
                if (jsonPosts != null) {

                    for (int i = 0; i < 10; i++) {
                        Post post = new Post();
                        json_posts.add(jsonPosts.getString(i));
                        post.setAuthor_name(jsonPosts.getJSONObject(i).getJSONObject("data").getString("subreddit"));
                        post.setCreation_date(jsonPosts.getJSONObject(i).getJSONObject("data").getDouble("created_utc"));
                        post.setImage_source(jsonPosts.getJSONObject(i).getJSONObject("data").getString("url_overridden_by_dest"));
                        post.setComments_count(jsonPosts.getJSONObject(i).getJSONObject("data").getString("num_comments"));
                        posts.add(post);
                    }
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return;
        }
    }
}
