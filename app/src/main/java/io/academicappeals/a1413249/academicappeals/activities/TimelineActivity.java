package io.academicappeals.a1413249.academicappeals.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.academicappeals.a1413249.academicappeals.R;
import io.academicappeals.a1413249.academicappeals.adapters.TimelineAdapter;
import io.academicappeals.a1413249.academicappeals.models.Department;
import io.academicappeals.a1413249.academicappeals.models.Timeline;
import io.realm.Realm;

public class TimelineActivity extends AppCompatActivity {

    private static int departmentId = 0;
    private static String departmentName = "";

    @BindView(R.id.recycler_view_timeline)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        departmentName = getIntent().getExtras().getString("departmentNameExtra");
        departmentId = getIntent().getExtras().getInt("departmentIdExtra");
        this.setTitle(departmentName);
        FloatingActionButton fab = findViewById(R.id.new_timeline_fab);
        fab.setOnClickListener((view) -> {
            new MaterialDialog.Builder(this)
                    .title("New timeline")
                    .input("Name", "", false, (dialog, input) -> {
                        createTimeline(input.toString());
                    })
                    .negativeText("Cancel")
                    .show();
                }
        );

        // Populate recycler view
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        Realm realm = Realm.getDefaultInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(
                new TimelineAdapter(
                        realm.where(Timeline.class)
                                .equalTo("departments.departmentId", departmentId)
                                .findAllAsync())
        );
        recyclerView.setHasFixedSize(true);
    }

    public static void createTimeline(String timelineName) {
        Log.d("DEBUG", timelineName);

        if (timelineName.isEmpty()) {
            return;
        }

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((r) -> {
            Department department = realm.where(Department.class).equalTo("departmentId", departmentId).findFirst();

            Number currentIdNum = r.where(Timeline.class).max("timelineId");

            int nextId;
            if(currentIdNum == null) {
                nextId = 1;
            } else {
                nextId = currentIdNum.intValue() + 1;
            }
            // Create new department timeline
            Timeline timeline = new Timeline();
            timeline.setTimelineId(nextId);
            timeline.setTimelineName(timelineName);
            department.addTimeline(timeline);
            // Insert
            r.insertOrUpdate(department);
        });
        realm.close();
    }

}
