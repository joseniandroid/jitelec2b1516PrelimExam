package tech.sidespell.prelimexam;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private RadioGroup   mRadioGroup;
    private SeekBar      mSeekBarSpeed;
    private TextView     mTvSpeed;
    private TextView     mTvTimer;
    private ToggleButton mTglBtnCountdown;

    private int     mValue;
    private Handler mHandler;

    private Runnable mCountdownRunnable = new Runnable() {
        @Override
        public void run() {
            if (mRadioGroup.getCheckedRadioButtonId() == R.id.radBtnIncrement) {
                mValue++;
            } else {
                mValue--;
            }

            mTvTimer.setText(String.valueOf(mValue));

            if (mTglBtnCountdown.isChecked()) {
                mHandler.postDelayed(this, mSeekBarSpeed.getProgress());
            } else {
                mHandler.removeCallbacks(this);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find views
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mSeekBarSpeed = (SeekBar) findViewById(R.id.seekBarSpeed);
        mTvSpeed = (TextView) findViewById(R.id.tvSpeed);
        mTvTimer = (TextView) findViewById(R.id.tvTimer);
        mTglBtnCountdown = (ToggleButton) findViewById(R.id.tglBtnCountdown);

        // Set appropriate listeners
        mSeekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvSpeed.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing..
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing..
            }
        });

        mTglBtnCountdown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mHandler.postDelayed(mCountdownRunnable, mSeekBarSpeed.getProgress());
                } else {
                    mHandler.removeCallbacks(mCountdownRunnable);
                }
            }
        });

        mHandler = new Handler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
