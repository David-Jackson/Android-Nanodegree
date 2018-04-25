package com.udacity.sandwichclub.handlers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


// This class enables simple control (set text, visibility) of the multiple elements that are
// included in the sandwich attributes (i.e. Description, origin, ingredients)
// these attributes each have an icon, text, and bottom divider associated with them
public class SandwichAttributeHandler {

    private TextView attributeTextView;
    private ImageView attributeImageView;
    private View attributeDividerView;

    public SandwichAttributeHandler(TextView attributeTextView,
                                    ImageView attributeImageView,
                                    View attributeDividerView) {
        this.attributeTextView = attributeTextView;
        this.attributeImageView = attributeImageView;
        this.attributeDividerView = attributeDividerView;
    }

    public void setText(CharSequence text) {
        this.attributeTextView.setText(text);
    }

    public void setVisibility(int visibility) {
        this.attributeTextView.setVisibility(visibility);
        this.attributeImageView.setVisibility(visibility);
        this.attributeDividerView.setVisibility(visibility);
    }
}
