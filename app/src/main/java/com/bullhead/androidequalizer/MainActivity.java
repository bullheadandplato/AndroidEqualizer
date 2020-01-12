package com.bullhead.androidequalizer;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bullhead.equalizer.DialogEqualizerFragment;
import com.bullhead.equalizer.EqualizerFragment;
import com.bullhead.equalizer.EqualizerModel;
import com.bullhead.equalizer.Settings;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadEqualizerSettings();
        mediaPlayer = MediaPlayer.create(this, R.raw.lenka);
        final int sessionId = mediaPlayer.getAudioSessionId();
        mediaPlayer.setLooping(true);
        EqualizerFragment equalizerFragment = EqualizerFragment.newBuilder()
                .setAccentColor(Color.parseColor("#4caf50"))
                .setAudioSessionId(sessionId)
                .build();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.eqFrame, equalizerFragment)
                .commit();
    }

    private void showInDialog() {
        int sessionId = mediaPlayer.getAudioSessionId();
        if (sessionId > 0) {
            DialogEqualizerFragment fragment = DialogEqualizerFragment.newBuilder()
                    .setAudioSessionId(sessionId)
                    .title(R.string.app_name)
                    .themeColor(ContextCompat.getColor(this, R.color.primaryColor))
                    .textColor(ContextCompat.getColor(this, R.color.textColor))
                    .accentAlpha(ContextCompat.getColor(this, R.color.playingCardColor))
                    .darkColor(ContextCompat.getColor(this, R.color.primaryDarkColor))
                    .setAccentColor(ContextCompat.getColor(this, R.color.secondaryColor))
                    .build();
            fragment.show(getSupportFragmentManager(), "eq");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        saveEqualizerSettings();
    }

    @Override
    protected void onPause() {
        try {
            mediaPlayer.pause();
        } catch (Exception ex) {
            //ignore
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.start();
                }
            }, 2000);
        } catch (Exception ex) {
            //ignore
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemEqDialog) {
            showInDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveEqualizerSettings(){
        if (Settings.equalizerModel != null){
            EqualizerSettings settings = new EqualizerSettings();
            settings.bassStrength = Settings.equalizerModel.getBassStrength();
            settings.presetPos = Settings.equalizerModel.getPresetPos();
            settings.reverbPreset = Settings.equalizerModel.getReverbPreset();
            settings.seekbarpos = Settings.equalizerModel.getSeekbarpos();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

            Gson gson = new Gson();
            preferences.edit()
                    .putString(PREF_KEY, gson.toJson(settings))
                    .apply();
        }
    }

    private void loadEqualizerSettings(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Gson gson = new Gson();
        EqualizerSettings settings = gson.fromJson(preferences.getString(PREF_KEY, "{}"), EqualizerSettings.class);
        EqualizerModel model = new EqualizerModel();
        model.setBassStrength(settings.bassStrength);
        model.setPresetPos(settings.presetPos);
        model.setReverbPreset(settings.reverbPreset);
        model.setSeekbarpos(settings.seekbarpos);

        Settings.isEqualizerEnabled = true;
        Settings.isEqualizerReloaded = true;
        Settings.bassStrength = settings.bassStrength;
        Settings.presetPos = settings.presetPos;
        Settings.reverbPreset = settings.reverbPreset;
        Settings.seekbarpos = settings.seekbarpos;
        Settings.equalizerModel = model;
    }

    public static final String PREF_KEY = "equalizer";
}
