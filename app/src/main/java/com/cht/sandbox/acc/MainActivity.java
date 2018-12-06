package com.cht.sandbox.acc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity
public class MainActivity extends AppCompatActivity {

    @Pref
    Prefs_ prefs;

    @ViewById(R.id.currentSeekBar)
    SeekBar currentSeekBar;

    @ViewById(R.id.current)
    TextView current;

    @ViewById(R.id.minText)
    TextView min;

    @ViewById(R.id.maxText)
    TextView max;

    @ViewById(R.id.value)
    TextView value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        min.setText(String.format("%d", prefs.min().get()));
        max.setText(String.format("%d", prefs.max().get()));

        currentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                calculate(i);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

        min.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                calculate(currentSeekBar.getProgress());
            }
        });

        max.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                calculate(currentSeekBar.getProgress());
            }
        });

        currentSeekBar.setProgress(prefs.current().get());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        prefs.edit()
                .min().put(Integer.parseInt(min.getText().toString()))
                .max().put(Integer.parseInt(max.getText().toString()))
                .current().put(currentSeekBar.getProgress())
                .apply();

        super.onDestroy();
    }

    void calculate(int i) {
        current.setText(String.format("%.1f", i / 10d + 4));

        try {
            int a = Integer.parseInt(min.getText().toString());
            int z = Integer.parseInt(max.getText().toString());

            value.setText(String.format("%,d", (z - a) * i / 160 + a));

        } catch (Exception e) {
            value.setText("N/A");
        }
    }

    public void onCalculateMax(View view) {
        try {
            int i = currentSeekBar.getProgress();
            int a = Integer.parseInt(min.getText().toString());
            int x = Integer.parseInt(value.getText().toString());

            int z = (x - a) * 160 / i + a;

            max.setText(String.format("%d", z));

            max.requestFocus();

        } catch (Exception e) {
        }
    }
}
