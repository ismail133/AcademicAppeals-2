package io.academicappeals.a1413249.academicappeals.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.academicappeals.a1413249.academicappeals.R;
import io.academicappeals.a1413249.academicappeals.adapters.DepartmentAdapter;
import io.academicappeals.a1413249.academicappeals.models.Department;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.recycler_view_department)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        FloatingActionButton newDepartmentFab = findViewById(R.id.new_department_fab);
        newDepartmentFab.setOnClickListener((view) -> {
            new MaterialDialog.Builder(this)
                    .title("New department")
                    .input("Name", "", false, (dialog, input) -> {
                        createDepartment(input.toString());
                    })
                    .negativeText("Cancel")
                    .show();
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Populate recycler view
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        Realm realm = Realm.getDefaultInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(
                new DepartmentAdapter(realm.where(Department.class).findAllAsync())
        );
        recyclerView.setHasFixedSize(true);
    }

    private static void createDepartment(String departmentName) {
        Log.d("DEBUG", departmentName);

        if (departmentName.isEmpty()) {
            return;
        }

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction((r) -> {
            Number currentIdNum = r.where(Department.class).max("departmentId");
            int nextId;
            if(currentIdNum == null) {
                nextId = 1;
            } else {
                nextId = currentIdNum.intValue() + 1;
            }
            // Create new department type
            Department department = new Department();
            department.setDepartmentId(nextId);
            department.setDepartmentName(departmentName);
            // Insert
            r.insertOrUpdate(department);
        });
        realm.close();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
