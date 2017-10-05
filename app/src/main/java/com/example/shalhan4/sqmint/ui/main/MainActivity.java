package com.example.shalhan4.sqmint.ui.main;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.job.JobFragment;
import com.example.shalhan4.sqmint.ui.login.LoginActivity;
import com.example.shalhan4.sqmint.ui.server.ServerFragment;
import com.example.shalhan4.sqmint.ui.usage.UsageFragment;
import com.example.shalhan4.sqmint.ui.user.UserFragment;
import com.example.shalhan4.sqmint.ui.user.add_user.AddUserActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MainPresenter mMainPresenter;
    private TextView mName;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(!this.sharedPreferences.getBoolean("IS_USER_LOGGEDIN", false))
        {
            Intent intent = new Intent(this , LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }


        //Inisiasi MainPresenter
        this.mMainPresenter = new MainPresenter(this);
        //Setup toolbar, navigation drawer
        setUp();
        //Default Fragment = Jobs
        onNavigationItemSelected(this.navigationView.getMenu().getItem(0).setChecked(true));

    }

    @Override
    public void onResume(){
        super.onResume();
        if(!this.sharedPreferences.getBoolean("IS_USER_LOGGEDIN", false))
        {
            Intent intent = new Intent(this , LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.i("USRNAME SBLM LOGOUT",this.sharedPreferences.getString("USERNAME", null));
            this.sharedPreferences.edit().clear().commit();

            Intent intent = new Intent(this , LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        UserFragment fragmentUser;
        ServerFragment fragmentServer;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_dashboard) {
            this.toolbar.setTitle(R.string.server_fragment);
            fragmentServer = new ServerFragment();
            fragmentTransaction.replace(R.id.frame, fragmentServer);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_user) {
            this.toolbar.setTitle(R.string.user_fragment);
            fragmentUser = new UserFragment();
            fragmentTransaction.replace(R.id.frame, fragmentUser);
            fragmentTransaction.commit();
        }

        this.drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setUp()
    {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setTitle(R.string.job_fragment);

        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, this.drawer, this.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawer.setDrawerListener(toggle);
        toggle.syncState();

        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        this.navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        Menu nav_menu = navigationView.getMenu();
        if(!this.sharedPreferences.getString("STATUS", null).equals("SUPERADMIN")) {
            nav_menu.findItem(R.id.nav_super_admin).setVisible(false);
        }

        this.mName = (TextView) header.findViewById(R.id.tv_user_name);
//        Log.i("HARUSNYA NAME ", this.sharedPreferences.getString("NAME", null));
        this.mName.setText(this.sharedPreferences.getString("NAME", null));
    }


}
