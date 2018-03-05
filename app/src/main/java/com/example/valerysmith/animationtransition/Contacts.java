package com.example.valerysmith.animationtransition;

import android.databinding.BindingAdapter;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;
import java.io.Serializable;

/**
 * Created by Valery Smith on 25.12.2017.
 */

public class Contacts implements Serializable {
    private final int color;
    private final String name;

    public Contacts(@ColorInt int color, String name) {
        this.color = color;
        this.name = name;
    }

    @BindingAdapter("bind:colorTint")
    public static void setColorTint(ImageView view, @ColorInt int color) {
        DrawableCompat.setTint(view.getDrawable(), color);
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
