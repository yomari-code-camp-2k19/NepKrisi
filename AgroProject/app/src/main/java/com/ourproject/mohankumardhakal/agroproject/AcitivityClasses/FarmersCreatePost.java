package com.ourproject.mohankumardhakal.agroproject.AcitivityClasses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.PostsAttributes;
import com.ourproject.mohankumardhakal.agroproject.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class FarmersCreatePost extends AppCompatActivity {
    TextView mylocation, title, description;
    ImageButton imageButton;
    Button sendbtn;
    //    String title_value, desc_value, location;
    private static final int GALLERY_REQUEST = 1;
    private Uri imageUri = null;
    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
    private StorageReference storageReference;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    String email_value, user_id;
    FirebaseDatabase database;
    DatabaseReference dbRef;
    static double lattitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmers_post);
        mylocation = findViewById(R.id.myLocation);
        imageButton = findViewById(R.id.imageBtn);
        title = findViewById(R.id.textTitle);
        description = findViewById(R.id.textDesc);
        progressDialog = new ProgressDialog(this);
        sendbtn = findViewById(R.id.postBtn);
        //firebase initialization
        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        email_value = firebaseAuth.getCurrentUser().getEmail();
        database = FirebaseDatabase.getInstance("https://agroproject-b9829.firebaseio.com/");
        dbRef = database.getInstance().getReference("Farmer Posts");
        storageReference = FirebaseStorage.getInstance().getReference();
        //buttonlistners
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromGallery();
            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImagetoStorageAndFireStore();
            }
        });
        //filtering the autolocation picker
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setHint("Search and select Location");
        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setCountry("NP")
                .build();
        autocompleteFragment.setFilter(filter);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mylocation.setText(place.getName());
                LatLng queriedLocation = place.getLatLng();
                lattitude = queriedLocation.latitude;
                longitude = queriedLocation.longitude;
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(FarmersCreatePost.this, "Error finding location try later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getImageFromGallery() {
        Intent explictitIntentForImage = new Intent(Intent.ACTION_GET_CONTENT);
        explictitIntentForImage.setType("image/*");
        startActivityForResult(explictitIntentForImage, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imageButton.setImageURI(imageUri);
        } else {
        }
    }

    public void sendImagetoStorageAndFireStore() {
        progressDialog.setMessage("Posting...");
        progressDialog.show();
        String title_value = title.getText().toString();
        if (!TextUtils.isEmpty(title_value)) {
            final StorageReference filepath = storageReference.child("PostsImages").child(getRandomString(10) + ".jpg");
            if (imageUri == null) {
                imageUri = Uri.parse("android.resource://com.ourproject.mohankumardhakal.agroproject/mipmap/farmers");
                Task<Uri> urlTask = filepath.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            sendDatatorealTimeDB(downloadUri);
                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });
            } else {
                Task<Uri> urlTask = filepath.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            sendDatatorealTimeDB(downloadUri);
                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });

            }
        }
    }

    //sends data to real time database including imageurl from storage
    public void sendDatatorealTimeDB(Uri downloaduri) {
        String post_id = dbRef.push().getKey();
        String title_value = title.getText().toString();
        String desc_value = description.getText().toString();
        String location = mylocation.getText().toString();
        //using helper class  to insert every posts
        PostsAttributes postsAttributes = new PostsAttributes(user_id, post_id, title_value, desc_value, location, getDate(), downloaduri.toString(), lattitude, longitude,email_value);
        dbRef.child(user_id).setValue(postsAttributes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(FarmersCreatePost.this, Application_main.class);
                    intent.putExtra("user_id", user_id);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(FarmersCreatePost.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    //returns the current date
    public String getDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    //generate the random strings for the image name
    private static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        title.setText("");
        description.setText("");
    }
}


