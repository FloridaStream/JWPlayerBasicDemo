package com.jwplayer.jwplayerbasicdemo;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.events.listeners.AdvertisingEvents;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.adaptive.QualityLevel;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
//import com.longtailvideo.jwplayer.media.source.SingleSource;


public class MainActivity extends ActionBarActivity {

    /**
     * Reference to the JW Player View
     */
    JWPlayerView mPlayerView;

    /**
     * Reference to the status output TextView
     */
    TextView mOutput;

    /**
     * An instance of our event handling class
     */
    private JWEventHandler mEventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPlayerView = (JWPlayerView)findViewById(R.id.jwplayer);
        mOutput = (TextView)findViewById(R.id.output);

        // Instantiate the JW Player event handler class
        mEventHandler = new JWEventHandler();

        // Subscribe to all JW Player events
        mPlayerView.setOnCompleteListener(mEventHandler);
        mPlayerView.setOnAdCompleteListener(mEventHandler);
        mPlayerView.setOnAdClickListener(mEventHandler);
        mPlayerView.setOnPlayListener(mEventHandler);
        mPlayerView.setOnAdPlayListener(mEventHandler);
        mPlayerView.setOnAdImpressionListener(mEventHandler);
        mPlayerView.setOnPauseListener(mEventHandler);
        mPlayerView.setOnAdPauseListener(mEventHandler);
        mPlayerView.setOnBufferListener(mEventHandler);
        mPlayerView.setOnAdBufferListener(mEventHandler);
        mPlayerView.setOnIdleListener(mEventHandler);
        mPlayerView.setOnAdIdleListener(mEventHandler);
        mPlayerView.setOnSeekListener(mEventHandler);
        mPlayerView.setOnTimeListener(mEventHandler);
        mPlayerView.setOnErrorListener(mEventHandler);
        mPlayerView.setOnAdErrorListener(mEventHandler);
        mPlayerView.setOnFullscreenListener(mEventHandler);
        mPlayerView.setOnAdFullscreenListener(mEventHandler);
        mPlayerView.setOnQualityLevelsListener(mEventHandler);
        mPlayerView.setOnQualityChangeListener(mEventHandler);

        // Load a media source
        PlaylistItem pi = new PlaylistItem("http://playertest.longtailvideo.com/adaptive/bipbop/gear4/prog_index.m3u8");
        mPlayerView.load(pi);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // Set fullscreen when the device is rotated to landscape
        mPlayerView.setFullscreen(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE, true);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        // Let JW Player know that the app has returned from the background
        mPlayerView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        // Let JW Player know that the app is going to the background
        mPlayerView.onPause();
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Exit fullscreen when the user pressed the Back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPlayerView.getFullscreen()) {
                mPlayerView.setFullscreen(false, true);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Updates the status output, note that this must be run on the UI thread
     *
     * @param output The new output status to display
     */
    public void setOutput(final String output) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mOutput.setText(output);
            }
        });
    }

    /**
     * This class handles all JW Player events.  These listeners can be implemented directly on the Activity or Fragment containing the JWPlayerView.
     * They were put in this class to keep all event handling in one place for readability.
     */
    public abstract class JWEventHandler implements VideoPlayerEvents.OnBufferListener,
            VideoPlayerEvents.OnCompleteListener,
            VideoPlayerEvents.OnErrorListener,
            VideoPlayerEvents.OnIdleListener,
            VideoPlayerEvents.OnMediaSelectedListener,
            VideoPlayerEvents.OnPauseListener,
            VideoPlayerEvents.OnPlayListener,
            VideoPlayerEvents.OnQualityChangeListener,
            VideoPlayerEvents.OnQualityLevelsListener,
            VideoPlayerEvents.OnSeekListener,
            VideoPlayerEvents.OnTimeListener,
            VideoPlayerEvents.OnFullscreenListener,
            AdvertisingEvents.OnAdSkipListener,
            AdvertisingEvents.OnAdTimeListener,
            AdvertisingEvents.OnAdPlayListener,
            AdvertisingEvents.OnAdPauseListener,
            AdvertisingEvents.OnAdBufferListener,
            AdvertisingEvents.OnAdIdleListener,
            AdvertisingEvents.OnAdImpressionListener,
            AdvertisingEvents.OnAdFullscreenListener,
            AdvertisingEvents.OnAdClickListener,
            AdvertisingEvents.OnAdErrorListener,
            AdvertisingEvents.OnAdCompleteListener
    {

        @Override
        public void onAdBuffer(String adTag) {
            setOutput("Ad Buffering: " + adTag);
        }

        @Override
        public void onAdClick(String adTag, String url) {
            setOutput("Ad Clicked: " + adTag + " URL: " + url);
        }

        @Override
        public void onAdComplete(String adTag) {
            setOutput("Ad Complete: " + adTag);
        }

        @Override
        public void onAdError(String adTag, String message) {
            setOutput("Ad Error: " + adTag + ": " + message);
        }

        @Override
        public void onAdFullscreen(boolean fullscreen, boolean userRequest) {
            setOutput("Ad fullscreen: " + fullscreen + ", by user: " + userRequest);
            handleFullscreen(fullscreen);
        }

        @Override
        public void onAdIdle(String adTag) {
            setOutput("Ad Idle: " + adTag);
        }

        @Override
        public void onAdImpression(String adTag) {
            setOutput("Ad Impression: " + adTag);
        }

        @Override
        public void onAdPause(String adTag) {
            setOutput("Ad Paused: " + adTag);
        }

        @Override
        public void onAdPlay(String adTag) {
            setOutput("Ad Playing: " + adTag);
        }

        @Override
        public void onAdSkipped(String adTag) {
            setOutput("Ad Skipped: " + adTag);
        }

        @Override
        public void onAdTime(String adTag, int position, int duration) {
            // Nothing implemented here because this event fires very frequently which would overwrite a lot of the other output
        }

        @Override
        public void onBuffer() {
            setOutput("Buffering");
        }

        @Override
        public void onComplete() {
            setOutput("Complete");
        }

        @Override
        public void onError(String message) {
            setOutput("Error: " + message);
        }

        @Override
        public void onFullscreen(boolean fullscreen, boolean userRequest) {
            setOutput("Fullscreen: " + fullscreen + ", by user: " + userRequest);
            handleFullscreen(fullscreen);
        }

        @Override
        public void onIdle() {
            setOutput("Idle");
        }

        @Override
        public void onMediaSelected() {
            setOutput("Media Selected");
        }

        @Override
        public void onPause() {
            setOutput("Paused");
        }

        @Override
        public void onPlay() {
            setOutput("Playing");
        }

        @Override
        public void onQualityChange(QualityLevel qualityLevel) {
            setOutput("Quality changed to: " + qualityLevel);
        }

        @Override
        public void onQualityLevels(QualityLevel[] qualityLevels) {
            StringBuilder sb = new StringBuilder();
            sb.append("Quality levels loaded: ");
            if(qualityLevels.length > 0) {
                sb.append(qualityLevels[0]);
                for(int i = 1 ; i < qualityLevels.length ; i++) {
                    sb.append(", ");
                    sb.append(qualityLevels[i]);
                }
            }
            setOutput(sb.toString());
        }

        @Override
        public void onSeek(int position, int offset) {
            setOutput("Seek to: " + position + " with offset: " + offset);
        }

        @Override
        public void onTime(int position, int duration) {
            // Nothing implemented here because this event fires very frequently which would overwrite a lot of the other output
        }
    }

    /**
     * Handles JW Player going to and returning from fullscreen by hiding on screen UI controls (e.g. home button, ActionBar)
     * @param fullscreen true if the player is fullscreen
     */
    @SuppressLint("NewApi")
    private void handleFullscreen(boolean fullscreen) {
        View decorView = getWindow().getDecorView();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);

        if (fullscreen) {
            // Detecting Navigation Bar
            if (!hasBackKey && !hasHomeKey) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
        } else {
            // Detecting Navigation Bar
            if (!hasBackKey && !hasHomeKey) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
