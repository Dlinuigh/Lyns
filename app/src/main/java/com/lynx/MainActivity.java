package com.lynx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;
import com.lynx.navigation.about.AboutActivity;
import com.lynx.navigation.feedback.FeedbackActivity;
import com.lynx.navigation.report.ReportActivity;
import com.lynx.navigation.settings.SettingsActivity;
import com.lynx.uclass.item.ItemActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Log.wtf("MainActivity","begin def drawer");
        drawer = findViewById(R.id.drawer_layout);
        //View view=View.inflate(this,R.layout.)
        NavigationView navigationView = findViewById(R.id.nav_view);
        Log.e("MainActivity",navigationView.toString());
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24);
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navigationView.setCheckedItem(R.menu.activity_main_drawer);

        Log.wtf("MainActivity","begin set Listener");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Log.e("MainActivity","begin closeDrawers");
                drawer.closeDrawers();
                Log.wtf("MainActivity","begin switch");
                switch(menuItem.getItemId()){

                    case R.id.learn:
                        //ItemActivity.actionStart(MainActivity.this,true);
                        Intent learn_intent = new Intent(MainActivity.this, ItemActivity.class);
                        Bundle bundle =new Bundle();
                        bundle.putBoolean("islearn",true);
                        learn_intent.putExtra("bundle",bundle);
                        startActivity(learn_intent);
                        break;

                    case R.id.practice:
                        //ItemActivity.actionStart(MainActivity.this,false);
                        Intent practice_intent = new Intent(MainActivity.this, ItemActivity.class);
                        Bundle pbundle =new Bundle();
                        pbundle.putBoolean("islearn",false);
                        practice_intent.putExtra("bundle",pbundle);
                        startActivity(practice_intent);
                        break;

                    case R.id.settings:
                        Intent settings_intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(settings_intent);
                        break;
                    case R.id.feedback:
                        Intent feedback_intent = new Intent(MainActivity.this, FeedbackActivity.class);
                        startActivity(feedback_intent);
                        break;
                    case R.id.report:
                        Intent report_intent = new Intent(MainActivity.this, ReportActivity.class);
                        startActivity(report_intent);
                        break;
                    case R.id.about:
                        Intent about_intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(about_intent);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.action_settings:
                Intent feedback_intent=new Intent(this, FeedbackActivity.class);
                startActivity(feedback_intent);
                break;
            case R.id.report:
                Intent report_intent=new Intent(this, ReportActivity.class);
                startActivity(report_intent);
            default:
        }
        return true;
    }
}