package com.example.hp.eduapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hp.eduapp.fragments.CoursesFragment;

public class AddCourseActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddCourseActivity.class.getSimpleName();
    private String day;
    private EditText courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseName = (EditText) findViewById(R.id.course_name_edittext);

        Spinner weekdays = (Spinner) findViewById(R.id.weekdays_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.week_days));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekdays.setAdapter(adapter);

        weekdays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                day = (String) adapterView.getItemAtPosition(i);
                Log.v(LOG_TAG, "Day Selected is " + day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        courseName.setError(null);
        switch (id) {
            case R.id.action_accept:
                String course = courseName.getText().toString();
                if (!TextUtils.isEmpty(course)) {
                    CoursesFragment.courses.add(course);
                    CoursesFragment.adapter.notifyDataSetChanged();
                    Intent intent = new Intent(this, com.example.hp.eduapp.CourseActivity.class);
                    intent.putExtra("Course Name", course);
                    startActivity(intent);
                    finish();
                } else if (TextUtils.isEmpty(course)) {
                    courseName.setError(getString(R.string.error_course_name_required));
                    courseName.requestFocus();
                }
                break;
            case R.id.action_discard:
                finish();
                break;
            default: return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
