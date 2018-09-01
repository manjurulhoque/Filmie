package google.com.filmie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import google.com.filmie.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    HomeFragment mHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }
}
