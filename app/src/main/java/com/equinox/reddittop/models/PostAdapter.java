package com.equinox.reddittop.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.equinox.reddittop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts = new ArrayList<>();

    public interface OnPhotoClickListener {
        void onPhotoClick(String image_source);
    }

    private OnPhotoClickListener onPhotoClickListener;

    public PostAdapter(List<Post> posts, OnPhotoClickListener onPhotoClickListener) {
        this.posts = posts;
        this.onPhotoClickListener = onPhotoClickListener;
    }

    public void setItems(Collection<Post> new_posts) {
        posts.addAll(new_posts);
        notifyDataSetChanged();
    }

    public void clearItems() {
        posts.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.author.setText(post.getAuthor_name());
        holder.creation_date.setText(post.getCreation_date());
        holder.title.setText(post.getTitle());
        holder.comments_count.setText(post.getComments_count());
        Picasso.get().load(post.getThumbnail_source()).resize(140, 78).into(holder.image);
        holder.image.setVisibility(post.getImage_source() != null ? View.VISIBLE : View.GONE);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPhotoClickListener.onPhotoClick(post.getImage_source());
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView creation_date;
        private ImageView image;
        private TextView comments_count;
        private TextView title;

        public PostViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author_name);
            creation_date = itemView.findViewById(R.id.creation_date);
            image = itemView.findViewById(R.id.imageView);
            comments_count = itemView.findViewById(R.id.comments_count);
            title = itemView.findViewById(R.id.title);
        }
    }
}
