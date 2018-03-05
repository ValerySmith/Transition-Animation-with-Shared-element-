package com.example.valerysmith.animationtransition;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.animation.AccelerateInterpolator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Valery Smith on 25.12.2017.
 */

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    List<Contacts> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWindowAnimations();
        setupContacts();
        setupToolbar();
        setupLayout();
    }


    private void setupWindowAnimations() {
        // Re-enter and Exit animation
        Fade fadeTransition = new Fade();
        fadeTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        TransitionSet transitionSet = new TransitionSet();
        Slide slide = new Slide();
        slide.setDuration(getResources().getInteger(R.integer.anim_duration_slide));
        slide.setSlideEdge(Gravity.TOP);
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(R.id.titleAnimationTransition, true);
        slide.excludeTarget(R.id.toolbar, true);

        Fade fade = new Fade();
        fade.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        int DELAY = 50;
        fade.setStartDelay(DELAY);

        transitionSet.setInterpolator(new LinearOutSlowInInterpolator());
        transitionSet.addTransition(fade);
        transitionSet.addTransition(slide);

        getWindow().setReenterTransition(fadeTransition);
        getWindow().setExitTransition(transitionSet);
    }

    private void setupContacts() {
        contacts = Arrays.asList(
                new Contacts(ContextCompat.getColor(this, R.color.sample_red), "Item One"),
                new Contacts(ContextCompat.getColor(this, R.color.sample_blue), "Item Two"),
                new Contacts(ContextCompat.getColor(this, R.color.sample_green), "Item Three"),
                new Contacts(ContextCompat.getColor(this, R.color.sample_yellow), "Item Four"),
                new Contacts(ContextCompat.getColor(this, R.color.sample_blue), "Item Five"),
                new Contacts(ContextCompat.getColor(this, R.color.sample_green), "Item Six")
        );
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupLayout() {
        RecyclerView recyclerView = findViewById(R.id.sample_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ContactsRecyclerAdapter contactsRecyclerAdapter = new ContactsRecyclerAdapter(this, contacts);
        recyclerView.setAdapter(contactsRecyclerAdapter);
    }
}
