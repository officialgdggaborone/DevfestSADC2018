package org.gdggaborone.devfestsadc2018.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.firebase.auth.FirebaseAuth;

import org.gdggaborone.devfestsadc2018.R;
import org.gdggaborone.devfestsadc2018.fragments.ChatFragment;
import org.gdggaborone.devfestsadc2018.fragments.ScheduleFragment;
import org.gdggaborone.devfestsadc2018.fragments.SpeakersFragment;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_schedule:
                    fragment = new ScheduleFragment();
                    break;
                case R.id.navigation_speakers:
                    fragment = new SpeakersFragment();
                    break;
                case R.id.navigation_chat:
                    fragment = new ChatFragment();
                    break;
            }

            if (fragment!=null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
                return true;
            }
            return false;
        }

    };

    private ImageView profilePictureImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser()==null) {
            finish();
            startActivity(new Intent(MainActivity.this, GoogleSignInActivity.class));
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        if (savedInstanceState==null) {
            fragment = new ScheduleFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
            navigation.setSelectedItemId(R.id.navigation_schedule);
        }

        profilePictureImageView = (ImageView) findViewById(R.id.profilePictureImageView);
        profilePictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDialog();
            }
        });

        Uri uri = FirebaseAuth.getInstance().getCurrentUser()!=null?FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl():null;

        Glide.with(this)
                .asBitmap()
                .load(uri)
                .apply(bitmapTransform(new CircleCrop()))
                .into(profilePictureImageView);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity(0);
        System.exit(0);
    }

    void launchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.container);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog,viewGroup, false);
        Button logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(MainActivity.this, GoogleSignInActivity.class);
                intent.putExtra("logout", "zzz");
                startActivity(intent);
            }
        });
        builder.setTitle("Credits and stuff");
        builder.setView(view);
        builder.setNegativeButton("Ok", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void launchMeetup(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.meetup.com/GDG-Gaborone/events/252870386/")));
    }
}
