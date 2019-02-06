package com.ourproject.mohankumardhakal.agroproject.AcitivityClasses;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.PostsAttributes;
import com.ourproject.mohankumardhakal.agroproject.R;
import com.ourproject.mohankumardhakal.agroproject.TestPackage.MapActivity;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class CustomerCreatePost extends AppCompatActivity implements LocationListener{
    TextView title, desc, mylocation, customer_name;
    Button postBtn;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference dbRef;
    String user_id;
    String email_value;
    ProgressDialog progressDialog;
    protected LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_create_post);
        //database setup
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://agroproject-b9829.firebaseio.com/");
        dbRef = database.getInstance().getReference("Customer Posts/");
        user_id = firebaseAuth.getCurrentUser().getUid();
        email_value=firebaseAuth.getCurrentUser().getEmail();
        progressDialog = new ProgressDialog(this);
        //map relaetd
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //checks if permission isn't granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //#take permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //if permission is already granted
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, CustomerCreatePost.this);
        }

        //finding the views
        title = findViewById(R.id.textTitle);
        desc = findViewById(R.id.textDesc);
        postBtn = findViewById(R.id.postBtn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_value = title.getText().toString();
                if (!TextUtils.isEmpty(title_value)) {
                    progressDialog.setMessage("Saving...");
                    progressDialog.show();
                    sendDataTofireBaseDatabase(title_value);
                }
            }
        });
    }

    public void sendDataTofireBaseDatabase(String title_value) {
        String customer_post_id = dbRef.push().getKey();
        String desc_value = desc.getText().toString();
        String location_value = mylocation.getText().toString();
        PostsAttributes postsAttributes = new PostsAttributes(user_id,customer_post_id, title_value, desc_value, location_value, getDate(),email_value);
        dbRef.child(user_id).child(customer_post_id).setValue(postsAttributes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(CustomerCreatePost.this, "Task Sucessfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CustomerCreatePost.this, Application_main.class);
                    intent.putExtra("user_id", user_id);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CustomerCreatePost.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
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

    @Override
    public void onLocationChanged(Location location) {
        String myCurrentlocation = getCurrentLocationName(location.getLatitude(), location.getLongitude());
        mylocation = findViewById(R.id.myLocation);
        mylocation.setText(myCurrentlocation);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    //listens if the premission is granted or not
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, CustomerCreatePost.this);
            }
        }
    }
    public String getCurrentLocationName(double lat, double lang) {
        Geocoder myLocation = new Geocoder(this, Locale.getDefault());
        String addressStr = "";
        try {
            List<Address> myList = myLocation.getFromLocation(lat, lang, 1);
            Address address = (Address) myList.get(0);
            addressStr = address.getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressStr;
    }
}
