package me.mehdi.lollipoptransitions;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.PathMotion;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int TRANSITION_DURATION = 100;
    Transition mTransition;
    ImageView mImageView;
    ImageView mImageView2;
    ImageView mImageView3;
    Scene mScene1;
    Scene mScene2;
    Button mButton;
    GridView mGridView;


    Transition mTransform;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.imageView);
        mImageView2 = findViewById(R.id.imageView2);
        mImageView3 = findViewById(R.id.dog);
        mButton = findViewById(R.id.start);
        mGridView = findViewById(R.id.gridview);

        mGridView.setAdapter(new GridAdapter(this));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView a, View v, int position, long id) {
                TransitionManager.beginDelayedTransition((ViewGroup)findViewById(android.R.id.content), new TransitionSet().addTransition(new ChangeTransform()).addTransition(new ChangeImageTransform()));
                v.setScaleX(v.getScaleX() == 1.0f ? 3.0f : 1.0f);
                v.setScaleY(v.getScaleY() == 1.0f ? 3.0f : 1.0f);
                v.setRotation(v.getRotation() == 0 ? 45 : 0);
                ImageView image = (ImageView)v;
                image.setScaleType(image.getScaleType() == ImageView.ScaleType.CENTER ? ImageView.ScaleType.CENTER_INSIDE : ImageView.ScaleType.CENTER);
            }
        });
        setTitle(String.format("(%s, %s)", mImageView.getX(), mImageView.getY()));
        final ViewGroup root = findViewById(R.id.root);
        mScene2 = Scene.getSceneForLayout(root, R.layout.activity_main2, this);

        //Initialize transitions
        mTransition = TransitionInflater.from(this).inflateTransition(R.transition.transitions);
        mTransform = TransitionInflater.from(this).inflateTransition(R.transition.transforms).setDuration(3000);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < mGridView.getChildCount(); i++) {
                    TransitionManager.beginDelayedTransition(mGridView, new Slide(Gravity.START));
                    View view = mGridView.getChildAt(i);
                    view.setVisibility(view.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);

//                    view.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                                view.setVisibility(view.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
//                        }
//                    }, TRANSITION_DURATION * i);
                }


//                TransitionManager.go(mScene2, mTransform);
////                TransitionManager.go(mScene2, mTransition);
//                setTitle(String.format("(%s, %s)", mImageView.getX(), mImageView.getY()));


//Todo: only one of them works at a time (TransitionManager.go or TransitionManager.beginDelayedTransition)
//                TransitionManager.beginDelayedTransition(root, new ChangeImageTransform());
////                mImageView3.setImageResource(R.drawable.small_dog);
//                mImageView3.setScaleType(mImageView3.getScaleType() == ImageView.ScaleType.CENTER ? ImageView.ScaleType.CENTER_INSIDE : ImageView.ScaleType.CENTER);
//                mImageView3.setX(50);
//                mImageView3.setY(600);
//
//
//                mImageView3.setScaleX(2f);
//                mImageView3.setScaleY(1.5f);

//                //Hide mImageView2
//                mImageView.setVisibility(View.GONE);
//                mImageView2.setVisibility(View.GONE);
//                mImageView3.setVisibility(View.GONE);

            }
        });

    }
}
