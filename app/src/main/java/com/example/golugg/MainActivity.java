package com.example.golugg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.golugg.userdashboard.DashboardFragment;
import com.example.golugg.userdashboard.EditProfileFragment;
import com.example.golugg.utils.FragmentManagerHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager = getSupportFragmentManager();
    public FragmentManagerHelper fragmentManagerHelper;
    Toolbar toolbar;
    DrawerLayout drawer;
    ImageView closeDrawerBtn;
    NavigationView navigationView;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        inItWidgets();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_closed);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        closeDrawerBtn.setOnClickListener(view1 -> drawer.closeDrawer(GravityCompat.START));

        navigationView.setCheckedItem(R.id.nav_dashboard);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManagerHelper.replace(R.id.frag_container, new DashboardFragment(), false);
    }

    private void inItWidgets() {
        fragmentManagerHelper = new FragmentManagerHelper(fragmentManager);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.dark_grey));
        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        headerView = navigationView.getHeaderView(0);
        closeDrawerBtn = headerView.findViewById(R.id.close_nav_icon);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int viewId = item.getItemId();
        if (viewId == R.id.nav_dashboard) {
            // Handle the Dashboard menu item click
            toolbar.setTitle("Dashboard");
            fragmentManagerHelper.replace(R.id.frag_container, new DashboardFragment(), false);
        } else if (viewId == R.id.nav_edit_profile){
            toolbar.setTitle("Edit Profile");
            fragmentManagerHelper.replace(R.id.frag_container, new EditProfileFragment(), false);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_bell) {
            Toast.makeText(MainActivity.this, "Notification Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}