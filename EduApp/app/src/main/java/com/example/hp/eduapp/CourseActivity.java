package com.example.hp.eduapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hp.eduapp.adapters.CourseActivityPageAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class CourseActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private CourseActivityPageAdapter mCourseActivityPageAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static final int FILES_FRAG = 1;
    private int[] icons = {
            R.drawable.ic_bookmark_border_black_24dp,
            R.drawable.ic_folder_open_black_24dp,
            R.drawable.ic_today_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mCourseActivityPageAdapter = new CourseActivityPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mCourseActivityPageAdapter);
        mViewPager.setCurrentItem(FILES_FRAG); //goes to the "files" tab by default

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++){
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }

        Intent intent = getIntent();
        String title = intent.getStringExtra("Course Name");
        getSupportActionBar().setTitle(title);

        final FrameLayout fabLayout = (FrameLayout) findViewById(R.id.course_screen_fab_layout);
        fabLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.course_screen_fab_menu);
        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                fabLayout.getBackground().setAlpha(240);
                fabLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        fabMenu.collapse();
                        return true;
                    }
                });
                FloatingActionButton newLinkFab = (FloatingActionButton) findViewById(R.id.new_link_fab);
                FloatingActionButton newPictureFab = (FloatingActionButton) findViewById(R.id.new_picture_fab);
                FloatingActionButton newFileFab = (FloatingActionButton) findViewById(R.id.new_file_fab);
                FloatingActionButton newRemindersFab = (FloatingActionButton) findViewById(R.id.new_reminders_fab);
                newLinkFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Adding a Link", Toast.LENGTH_SHORT).show();
                        fabMenu.collapse();
                    }
                });
                newPictureFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Adding a Picture", Toast.LENGTH_SHORT).show();
                        fabMenu.collapse();
                    }
                });
                newFileFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Adding a File", Toast.LENGTH_SHORT).show();
                        fabMenu.collapse();
                    }
                });
                newRemindersFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Adding a Reminder", Toast.LENGTH_SHORT).show();
                        fabMenu.collapse();
                    }
                });

            }

            @Override
            public void onMenuCollapsed() {
                fabLayout.getBackground().setAlpha(0);
                fabLayout.setOnTouchListener(null);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_course_details) {
            Toast.makeText(this, "Open Course Details", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
