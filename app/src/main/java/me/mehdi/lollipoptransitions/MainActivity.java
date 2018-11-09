package me.mehdi.lollipoptransitions;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int TRANSITION_DURATION = 100;
    Transition mTransition;
    ImageView mCoffeeImage;
    ImageView mImageView2;
    ImageView mImageView3;
    Scene mScene1;
    Scene mScene2;
    Button mButton;
    GridView mGridView;


    Transition mTransform;
    private ViewGroup mRoot;
    private Rect mRect;
    private boolean mClipStarted;
    private Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRoot = findViewById(R.id.root);
        mCoffeeImage = findViewById(R.id.coffee);
        mImageView2 = findViewById(R.id.imageView2);
        mImageView3 = findViewById(R.id.dog);
        mButton = findViewById(R.id.start);
        mGridView = findViewById(R.id.gridview);

        mRect = mImageView3.getClipBounds();

        mGridView.setAdapter(new GridAdapter(this));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView a, View v, int position, long id) {
                switch (position) {
                    case 0:
                        TransitionManager.beginDelayedTransition((ViewGroup) findViewById(android.R.id.content), new TransitionSet().addTransition(new ChangeTransform()).addTransition(new ChangeImageTransform()));
                        v.setScaleX(v.getScaleX() == 1.0f ? 3.0f : 1.0f);
                        v.setScaleY(v.getScaleY() == 1.0f ? 3.0f : 1.0f);
                        v.setRotation(v.getRotation() == 0 ? 45 : 0);
                        ImageView image = (ImageView) v;
                        image.setScaleType(image.getScaleType() == ImageView.ScaleType.CENTER ? ImageView.ScaleType.CENTER_INSIDE : ImageView.ScaleType.CENTER);
                        Snackbar.make(mRoot, R.string.change_transform_and_change_image_transform, Snackbar.LENGTH_LONG).show();
                        break;

                    case 1:
                        ImageView img = (ImageView) v;
                        Transition transition = TransitionInflater.from(mContext).inflateTransition(R.transition.arc_motion);
                        TransitionManager.beginDelayedTransition(mGridView, transition);
                        img.setTranslationX(500);
                        img.setTranslationY(600);
                        break;
                    case 2:
                        ImageView imgShared = (ImageView) v;

                        break;
                }
            }
        });
        setTitle(String.format("(%s, %s)", mCoffeeImage.getX(), mCoffeeImage.getY()));
        mScene2 = Scene.getSceneForLayout(mRoot, R.layout.activity_main2, this);

        //Initialize transitions
        mTransition = TransitionInflater.from(this).inflateTransition(R.transition.transitions);
        mTransform = TransitionInflater.from(this).inflateTransition(R.transition.transforms).setDuration(3000);

        mCoffeeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, mCoffeeImage, getString(R.string.coffee));
                startActivity(intent, options.toBundle());

            }
        });

        mImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(mRoot, new ChangeClipBounds());
                if (mClipStarted) {
                    mImageView3.setClipBounds(mRect);
                    mClipStarted = false;
                } else {
                    mImageView3.setClipBounds(new Rect(100, 100, 400, 700));
                    mClipStarted = true;
                }

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.grid_move:
                moveGrid();
                Snackbar.make(mRoot, R.string.coordinated_slide_transition, Snackbar.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void moveGrid() {
        for (int i = 0; i < mGridView.getChildCount(); i++) {
            final View view = mGridView.getChildAt(i);

            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TransitionManager.beginDelayedTransition(mGridView, new Slide(Gravity.START));
                    view.setVisibility(view.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                }
            }, TRANSITION_DURATION * i);
        }

    }
}
