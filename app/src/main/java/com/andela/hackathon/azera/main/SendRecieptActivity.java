package com.andela.hackathon.azera.main;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andela.hackathon.azera.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;

public class SendRecieptActivity extends AppCompatActivity {

    ImageView preview;

    EditText tagsView;
    EditText descriptionView;

    ProgressBar progressBar;
    LinearLayout layout;

    Bitmap reciept = null;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StorageReference storageRef;

    DatabaseReference catRef = database.getReference("categories");
    DatabaseReference recRef = database.getReference();

    ActionBar actionBar;
    AppCompatSpinner categorySpinner;

    ArrayList<String> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_reciept);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progress_sendReciept);
        layout = findViewById(R.id.layout_sendReciept);

        storageRef = FirebaseStorage.getInstance().getReference();

        preview = findViewById(R.id.reciept_preview);
        preview.setImageBitmap(Statics.bitmap);


        initCategories();
        tagsView = findViewById(R.id.reciept_tags);
        descriptionView = findViewById(R.id.reciept_description);

        initActionbar();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }

    void initActionbar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_clear);
    }


    void initCategories() {
        // setup categories
        categorySpinner = findViewById(R.id.category_spinner);
        categories.add("Select category");
        final ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(categoriesAdapter);
        catRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Category category = snapshot.getValue(Category.class);
                    category.id = dataSnapshot.getKey();
                    categoriesAdapter.add(category.name);
                    categoriesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("DB_ERROR", "Failed to read value.", error.toException());
            }
        });
    }

    void showProgress(boolean show){
        if (show){
            layout.setVisibility(View.GONE);
        }else{
            layout.setVisibility(View.VISIBLE);
        }
    }

    void upload() {
        final String cagetory = categorySpinner.getSelectedItem().toString();

        if (categorySpinner.getSelectedItem().toString() == categories.get(0)){
            Toast.makeText(this, "Please select category", Toast.LENGTH_SHORT);
            return;
        }

        showProgress(true);
        final ProgressDialog dialog = ProgressDialog.show(this, "Please wait ...", "Sending receipt");
        dialog.setCancelable(false);
        dialog.show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Statics.bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        // save to db
        StorageReference recieptRef = storageRef.child("azera/media/receipt_".concat(Calendar.getInstance().getTime().toString()));
        UploadTask task = recieptRef.putBytes(baos.toByteArray());

        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                recRef.child("receipts").push().setValue(new Reciept(cagetory, "pending", FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        downloadUrl.toString(), tagsView.getText().toString(), descriptionView.getText().toString()
                        , ServerValue.TIMESTAMP.toString(), ServerValue.TIMESTAMP.toString()));
                finish();
                dialog.hide();
                showProgress(false);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(SendRecieptActivity.this, "Upload failed",Toast.LENGTH_SHORT);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class Category {
        public String id;
        public String name;
    }

    public static class Reciept{
        public String category;
        public String status;
        public String user_id;
        public String imageUrl;
        public String tags;
        public String description;
        public String createdAt;
        public String updatedAt;

        public Reciept(String category, String status, String user_id, String imageUrl, String tags, String description, String createdAt, String updatedAt) {
            this.category = category;
            this.status = status;
            this.user_id = user_id;
            this.imageUrl = imageUrl;
            this.tags = tags;
            this.description = description;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }
}
