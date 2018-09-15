package com.squad.betakuma.adherence_app.SwipableCards;


import android.view.View;
import android.widget.LinearLayout;

public class CardItem {

    private boolean isExpanded = false;
    private LinearLayout expandableContent;
    private int mTextResource;
    private int mTitleResource;

    public CardItem(int title, int text) {
        mTitleResource = title;
        mTextResource = text;
    }

    public boolean isExpanded() {
        return isExpanded;
    }
    public void setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

    public void setExpandableContent(LinearLayout expandableContent) {
        this.expandableContent = expandableContent;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }

    public void toggleExpanded() {
        if (isExpanded) {
            expandableContent.setVisibility(View.GONE);
        } else {
            expandableContent.setVisibility(View.VISIBLE);
        }
        isExpanded = !isExpanded;
    }
}