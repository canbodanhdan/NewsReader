package vn.edu.usth.newsreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;

import java.util.concurrent.Executors;

import vn.edu.usth.newsreader.storage.Prefs;
import vn.edu.usth.newsreader.login.LoginActivity;
import vn.edu.usth.newsreader.login.User;
import vn.edu.usth.newsreader.dialog.FeedbackDialog;

public class MainActivity extends AppCompatActivity implements FeedbackDialog.FeedbackListener {

    private DrawerLayout drawerLayout;
    private NavController navController;


    @Override
    protected void onStart() {
        super.onStart();

        // Query the current user from SharedPreferences
        Executors.newSingleThreadExecutor().execute(() -> {
            User currentUser = Prefs.getLoggedInUser(this);

            if (currentUser == null || !currentUser.isLoggedIn()) {
                // Redirect to LoginActivity if not logged in
                runOnUiThread(() -> {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if user is logged in
        checkIfUserLoggedIn();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("NewsApp");

        // Set up DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        // Show navigation open button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_24); // Replace with your menu icon
        }

        // Handle Navigation Drawer item selections
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();

            if (itemId == R.id.nav_tin_moi_nhat) {
                navController.popBackStack(R.id.navigation_new, false); // Navigate to HomeFragment if on another fragment
            } else if (itemId == R.id.nav_gui_y_kien) {
                showFeedbackDialog();
            } else if (itemId == R.id.nav_thoat) {
                signOutUser();
            } else if (itemId == R.id.nav_lich_su) {
                Log.d("MainActivity", "Attempting to navigate to HistoryFragment");
                navController.navigate(R.id.historyFragment);
            }   else if (itemId == R.id.nav_bookmark) {
                navController.navigate(R.id.bookmarkFragment);
            }

            // Close Navigation Drawer after selection
            drawerLayout.closeDrawers();
            return true;
        });
    }

    /**
     * Check whether the user is logged in.
     * If not logged in, redirect to LoginActivity.
     */
    private void checkIfUserLoggedIn() {
        Executors.newSingleThreadExecutor().execute(() -> {
            User currentUser = Prefs.getLoggedInUser(this);
            if (currentUser == null) {
                runOnUiThread(() -> {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        });
    }


    /**
     * Sign out the current user.
     * Clear login state and redirect to LoginActivity.
     */
    private void signOutUser() {
        Executors.newSingleThreadExecutor().execute(() -> {
            Prefs.logout(this);
            runOnUiThread(() -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            });
        });
    }



    /** Show the feedback dialog. */
    private void showFeedbackDialog() {
        FeedbackDialog feedbackDialog = FeedbackDialog.newInstance();
        feedbackDialog.show(getSupportFragmentManager(), "FeedbackDialog");
    }

    @Override
    public void onFeedbackSent(String feedback, int rating) {
        // Optional: handle post-feedback actions
        Log.d("MainActivity", "Feedback sent: " + feedback + ", Rating: " + rating);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        } else if (item.getItemId() == R.id.action_refresh) {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment);
            if (navHostFragment != null) {
                Fragment current = navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment();
                if (current instanceof vn.edu.usth.newsreader.news.NewsFragment) {
                    ((vn.edu.usth.newsreader.news.NewsFragment) current).refreshNow();
                    return true;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
