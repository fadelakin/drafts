package com.fisheradelakin.drafts;

/**
 * Created by temidayo on 2/29/16.
 */
public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
