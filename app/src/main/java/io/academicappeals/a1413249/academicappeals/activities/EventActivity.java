package io.academicappeals.a1413249.academicappeals.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.academicappeals.a1413249.academicappeals.R;
import io.academicappeals.a1413249.academicappeals.adapters.EventAdapter;
import io.academicappeals.a1413249.academicappeals.models.Event;
import io.academicappeals.a1413249.academicappeals.models.Timeline;
import io.realm.Realm;

public class EventActivity extends AppCompatActivity {

    private static int timelineId = 0;
    private static String timelineName = "";

    @BindView(R.id.recycler_view_event)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        timelineName = getIntent().getExtras().getString("timelineNameExtra");
        timelineId = getIntent().getExtras().getInt("timelineIdExtra");
        this.setTitle(timelineName);
        FloatingActionButton fab = findViewById(R.id.new_event_fab);
        fab.setOnClickListener((view) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


            new MaterialDialog.Builder(this)
                    .title("New event")
                    .content("Event name")
                    .input("", "", false, (dialog, input) -> {
                        Date d = new Date();
                        try {
                            d = sdf.parse("10/10/2017");
                        } catch (ParseException e) {
                            //
                        }
                        createEvent(input.toString(), "test desc", d);
                    })
                    .negativeText("Cancel")
                    .show();
        });
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        Realm realm = Realm.getDefaultInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(
                new EventAdapter(
                        realm.where(Event.class)
                                .equalTo("timelines.timelineId", timelineId)
                                .sort("eventDate")
                                .findAllAsync())
        );
        recyclerView.setHasFixedSize(true);
    }

    public static void createEvent(String eventName, String eventDesc, Date eventDate) {
        Log.d("DEBUG", timelineName);

        if (timelineName.isEmpty()) {
            return;
        }

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((r) -> {
            Timeline timeline = realm.where(Timeline.class).equalTo("timelineId", timelineId).findFirst();

            Number currentIdNum = r.where(Event.class).max("eventId");

            int nextId;
            if(currentIdNum == null) {
                nextId = 1;
            } else {
                nextId = currentIdNum.intValue() + 1;
            }

            // Create new department timeline
            Event event = new Event();
            event.setEventId(nextId);
            event.setEventDate(eventDate);
            event.setEventDesc(eventDesc);
            event.setEventName(eventName);
            timeline.addEvent(event);
            // Insert
            r.insertOrUpdate(timeline);
        });
        realm.close();
    }

}
