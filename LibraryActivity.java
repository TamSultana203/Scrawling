package com.example.Library;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;



import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TableLayout;
//import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;


public class LibraryActivity extends AppCompatActivity {

        Toolbar toolbar,toolbartab ;
        ViewPager viewPager ;
        TabLayout tabLayout ;
        PageAdapter pageAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

                toolbar= (Toolbar) findViewById(R.id.toolbar) ;
                toolbartab= (Toolbar) findViewById(R.id.toolbartab) ;
                viewPager = (ViewPager) findViewById(R.id.viewpager) ;
                tabLayout = (TabLayout) findViewById(R.id.tablayout) ;

                setSupportActionBar(toolbar);


                pageAdapter= new PageAdapter(getSupportFragmentManager())  ;
                pageAdapter.addFragment(new RedFragment(),"CURRENT READ" );
                pageAdapter.addFragment(new GreenFragment(),"READING LIST" );

                viewPager.setAdapter(pageAdapter);

                tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}




