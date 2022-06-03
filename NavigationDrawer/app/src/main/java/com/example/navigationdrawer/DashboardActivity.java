package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void onClickMenu(View view) {
        MainActivity.openDrawer(drawerLayout);
    }

    public void onClickLogo(View view) {
        MainActivity.closeDrawer(drawerLayout);
    }

    public void onClickHome(View view) {
        MainActivity.redirectActivity(this, MainActivity.class);
    }

    public void onClickDashboard(View view) {
        recreate();
    }

    public void onClickAbout(View view) {
        MainActivity.redirectActivity(this, AboutActivity.class);
    }

    public void onClickLogout(View view) {
        MainActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}