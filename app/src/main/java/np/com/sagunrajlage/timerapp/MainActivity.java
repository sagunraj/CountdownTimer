package np.com.sagunrajlage.timerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import static np.com.sagunrajlage.timerapp.R.id.timerTextView;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar timerSeekBar;
    MediaPlayer mediaPlayer;
    Boolean counterIsActive = false;
    Button controllerButton;
    CountDownTimer countDownTimer;

    public void updateTimer(int secondsLeft) {
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        if (minutes >= 0 && minutes < 10) {
            if (seconds == 0) {
                timerTextView.setText("0" + Integer.toString(minutes) + ":" + Integer.toString(seconds) + "0");
            } else if (seconds > 0 && seconds < 10) {
                timerTextView.setText("0" + Integer.toString(minutes) + ":" + "0" + Integer.toString(seconds));
            } else {
                timerTextView.setText("0" + Integer.toString(minutes) + ":" + Integer.toString(seconds));
            }
        } else {
            timerTextView.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds) + "0");
        }
    }

    public void controlTimer(View view) {
        if (!counterIsActive) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop!");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("00:00");
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    controllerButton.setText("Reset Timer!");
                }
            }.start();
        }

        else{
            counterIsActive = false;

            timerTextView.setText("00:30");
            timerSeekBar.setProgress(30);
            timerSeekBar.setEnabled(true);
            countDownTimer.cancel();
            controllerButton.setText("Go!");

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        controllerButton = (Button) findViewById(R.id.controllerButton);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}