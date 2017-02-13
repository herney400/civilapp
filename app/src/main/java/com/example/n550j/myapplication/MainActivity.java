package com.example.n550j.myapplication;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.n550j.myapplication.fragments.FragmentCurvadeCostos;
import com.example.n550j.myapplication.fragments.FragmentProjects;
import com.example.n550j.myapplication.fragments.FragmentProyecto;

public class MainActivity extends AppCompatActivity {

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
 int id;
    String email,userName;


/*Un cambio*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);

       // getSupportActionBar().setHomeAsUpIndicator(R.);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        obtenerDatosdeUsuario();
        setFragment(0);
        /*
        //Eventos del Drawer Layout
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }
            @Override
            public void onDrawerOpened(View drawerView) {
            }
            @Override
            public void onDrawerClosed(View drawerView) {
            }
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        */

        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;
                        fragment = new FragmentProyecto();
                        fragmentTransaction = true;
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                        menuItem.setChecked(true);
                        getSupportActionBar().setTitle(menuItem.getTitle());
                        
                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                setFragment(0);
                               /* fragment = new FragmentProyecto();
                                fragmentTransaction = true;
                                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();*/
                                menuItem.setChecked(true);
                                getSupportActionBar().setTitle(menuItem.getTitle());
                                break;

                            case R.id.menu_seccion_2:
                                setFragment(1);
                               // fragment = new FragmentProjects.newInstance(id,email);
                               /* FragmentProjects f=FragmentProjects.newInstance(userName,id,email);
                                fragmentTransaction = true;
                                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();*/
                                menuItem.setChecked(true);
                                getSupportActionBar().setTitle(menuItem.getTitle());
                                break;
/*
                            case R.id.menu_seccion_3:
                                fragment = new FragmentProyecto();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_4:
                                fragment = new FragmentCurvadeCostos();
                                fragmentTransaction = true;
                                break;
*/
                            case R.id.menu_opcion_1:
                                Log.i("NavigationView", "Pulsada opción 1");
                                break;
                            case R.id.menu_opcion_2:
                                Log.i("NavigationView", "Pulsada opción 2");
                                break;
                        }

                      /*  if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();
                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }*/

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });
    }

    public void setFragment(int positio){
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        Fragment fragment = null;
        switch(positio){

            case 0:
                fragmentManager =getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                FragmentProyecto fragmento = FragmentProyecto.newInstance(id);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmento).commit();
                break;

           // getSupportActionBar().setTitle(menuItem.getTitle());
            case 1:
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                FragmentProjects f=FragmentProjects.newInstance(userName,id,email);
                //fragmentTransaction = true;
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
                break;


        }


    }


    public void obtenerDatosdeUsuario(){
       this.userName = getIntent().getExtras().getString("userName");
      this.id       = getIntent().getExtras().getInt("id");
       this.email    = getIntent().getExtras().getString("email");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}