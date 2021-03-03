package ks.kanhustoryview;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class KanhuPlayer extends AppCompatActivity implements KanhuProgressView.StoryPlayerListener {
    public static final String STORY_IMAGE_KEY = "KanhuImages";
    KanhuProgressView kanhuProgressView;
    ImageView imageView;
    TextView name;
    TextView time;
    ArrayList<KanhuStoryModel> stories;
    KanhuPreference kanhuPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanhu_player);
        kanhuProgressView = findViewById(R.id.progressBarView);
        name = findViewById(R.id.storyUserName);
        time = findViewById(R.id.storyTime);
        kanhuProgressView.setSingleStoryDisplayTime(2000);
        imageView = findViewById(R.id.storyImage);
        kanhuPreference = new KanhuPreference(this);
        Intent intent = getIntent();
        if (intent != null) {
            stories = intent.getParcelableArrayListExtra(STORY_IMAGE_KEY);
            initStoryProgressView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        kanhuProgressView.cancelAnimation();
    }

    private void initStoryProgressView() {
        if (stories != null && stories.size() > 0) {
            kanhuProgressView.setStoryPlayerListener(this);
            kanhuProgressView.setProgressBarsCount(stories.size());
            setTouchListener();
        }
    }

    private void setTouchListener() {
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //pause
                    kanhuProgressView.pauseProgress();
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //resume
                    kanhuProgressView.resumeProgress();
                    return true;
                }else {
                    return false;
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onStartedPlaying(int index) {
        loadImage(index);
        name.setText(stories.get(index).name);
        time.setText(stories.get(index).time);
        kanhuPreference.setStoryVisited(stories.get(index).imageUri);
    }

    @Override
    public void onFinishedPlaying() {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void loadImage(int index) {
        if(!this.isDestroyed()) {
            kanhuProgressView.pauseProgress();
            Glide.with(this).clear(imageView);
            Glide.with(this)
                    .load(stories.get(index).imageUri)
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .addListener(new RequestListener<Drawable>() {
                        @SuppressLint("NewApi")
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            kanhuProgressView.resumeProgress();
                            return false;
                        }

                        @SuppressLint("NewApi")
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            kanhuProgressView.resumeProgress();
                            return false;
                        }
                    })
                    .into(imageView);
        }
    }
}
