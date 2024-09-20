package vn.edu.usth.wikipedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import vn.edu.usth.wikipedia.adapters.PagerAdapter;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        PagerAdapter adapter = new PagerAdapter(this);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(4);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.explore_button).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.bookmark_button).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.history_button).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.search_button).setChecked(true);
                        break;
                }
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.explore_button) {
                    viewPager2.setCurrentItem(0, true);
                } else if (itemId == R.id.bookmark_button) {
                    viewPager2.setCurrentItem(1, true);
                } else if (itemId == R.id.history_button) {
                    viewPager2.setCurrentItem(2, true);
                } else if (itemId == R.id.search_button) {
                    viewPager2.setCurrentItem(3, true);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.setting_button) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}