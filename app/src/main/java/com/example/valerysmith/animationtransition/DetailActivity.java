package com.example.valerysmith.animationtransition;

import android.animation.Animator;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.valerysmith.animationtransition.databinding.DetailLayoutBinding;

/**
 * Created by Valery Smith on 25.12.2017.
 */

public class DetailActivity extends AppCompatActivity {
    private static final String EXTRA_CONTACT = "contact";
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindData();
        setupEnterAnimations();
        setupLayout();
        setupToolbar();
    }

    void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void bindData() {
        DetailLayoutBinding binding = DataBindingUtil.setContentView(this, R.layout.detail_layout);
        Contacts contacts = (Contacts) getIntent().getExtras().getSerializable(EXTRA_CONTACT);
        binding.setReveal1Sample(contacts);
    }

    private void setupEnterAnimations() {
        final TransitionSet transitionSet = new TransitionSet();
        transitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        transitionSet.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        ChangeBounds changeBounds = new ChangeBounds();
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumVerticalAngle(0);
        arcMotion.setMinimumHorizontalAngle(45);
        changeBounds.setPathMotion(arcMotion);
        transitionSet.addTransition(changeBounds);
        getWindow().setSharedElementEnterTransition(transitionSet);
        transitionSet.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transitionSet.removeListener(this);
                hideTarget();
                animateRevealShow();
                reColorTitle();
                animateBody();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void hideTarget() {
        ImageView targetImage = findViewById(R.id.shared_target);
        targetImage.setVisibility(View.GONE);
    }

    private void reColorTitle() {
        TextView titleTextView = findViewById(R.id.titleText);
        titleTextView.setTextColor(Color.WHITE);
    }

    private void animateRevealShow() {
        ImageView titleView = findViewById(R.id.titleView);
        int cx = (titleView.getLeft() + titleView.getRight()) / 2;
        int cy = (titleView.getTop() + titleView.getBottom()) / 2;
        int finalRadius = Math.max(titleView.getWidth(), titleView.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(titleView, cx, cy, 0, finalRadius);
        titleView.setVisibility(View.VISIBLE);
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        anim.setInterpolator(new AccelerateInterpolator());
        anim.start();
    }

    private void setupLayout() {
        relativeLayout = findViewById(R.id.reveal_root);
    }

    private void animateBody() {
        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            View child = relativeLayout.getChildAt(i);
            int DELAY = 50;
            child.animate()
                    .setStartDelay(100 + i * DELAY)
                    .setInterpolator(new AccelerateInterpolator())
                    .alpha(1)
                    .translationY(0);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
