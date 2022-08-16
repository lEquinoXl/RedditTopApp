package com.equinox.reddittop.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.equinox.reddittop.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class ImageActivity extends AppCompatActivity {

    private ImageView image;
    private Button download_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        image = (ImageView) findViewById(R.id.my_image_view);
        download_button = (Button) findViewById(R.id.save_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String image_source = extras.getString("image_source");
            Picasso.get().load(image_source).into(image);
        }
        download_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                    saveReceivedImage(bitmap, LocalDateTime.now().toString());
                } catch (NullPointerException e) {
                    Toast toast = Toast.makeText(ImageActivity.this, "Error. No image", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private void saveReceivedImage(Bitmap image, String imageName) {
        try {
            File path = new File(ImageActivity.this.getFilesDir(), "RedditTop" + File.separator + "Images");
            if (!path.exists()) {
                path.mkdirs();
            }
            File outFile = new File(path, imageName + ".jpeg");
            FileOutputStream outputStream = new FileOutputStream(outFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Toast toast = Toast.makeText(ImageActivity.this, "Saving", Toast.LENGTH_LONG);
            toast.show();
            outputStream.close();
        } catch (IOException e) {
            Toast toast = Toast.makeText(ImageActivity.this, "Error", Toast.LENGTH_LONG);
            toast.show();
        }
    }

}
