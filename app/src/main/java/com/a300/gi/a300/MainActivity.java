package com.a300.gi.a300;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.a300.gi.a300.Fragments.AccountFragment;
import com.a300.gi.a300.Fragments.ConsultarEventosFragment;
import com.a300.gi.a300.Fragments.PerfilUsuarioFragment;
import com.a300.gi.a300.Fragments.RequirementsFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private AccountFragment accountFragment;
    private RequirementsFragment requirementsFragment;
    private PerfilUsuarioFragment perfilUsuarioFragment;
    private ConsultarEventosFragment consultarEventosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        accountFragment = new AccountFragment();
        requirementsFragment = new RequirementsFragment();
        perfilUsuarioFragment = new PerfilUsuarioFragment();
        consultarEventosFragment = new ConsultarEventosFragment();

        comprobarSesion();


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_account:
                        SharedPreferences preferences = getSharedPreferences(
                                "Credenciales", Context.MODE_PRIVATE);
                        String id = preferences.getString("id", "");
                        if (id.equals("")) {
                            setFragment(accountFragment);
                            return true;
                        } else {
                            setFragment(perfilUsuarioFragment);
                            {
                                return true;
                            }
                        }

                    case R.id.nav_requirements:
                        setFragment(requirementsFragment);
                        return true;

                    case R.id.nav_eventos:
                        setFragment(consultarEventosFragment);
                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    private void comprobarSesion() {
        SharedPreferences preferences = getSharedPreferences(
                "Credenciales", Context.MODE_PRIVATE);
        String id = preferences.getString("id", "");
        if (id.equals("")) {
            setFragment(accountFragment);
        } else {
            setFragment(perfilUsuarioFragment);
            {
            }
        }
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}