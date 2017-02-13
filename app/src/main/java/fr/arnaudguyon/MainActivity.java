package fr.arnaudguyon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.arnaudguyon.toolbox.AutoGridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoGridView autoGridView = (AutoGridView) findViewById(R.id.autoGrid);
        AutoGridAdapter adapter = new AutoGridAdapter(this);
        autoGridView.setAdapter(adapter);
        autoGridView.setNumColumns(3);
        autoGridView.setMatchParent(true);
    }
}
