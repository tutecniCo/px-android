package com.mercadopago.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mercadopago.uicontrollers.CustomViewController;
import com.mercadopago.uicontrollers.discounts.DiscountSearchViewController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mromar on 3/21/17.
 */

public class DiscountSearchItemAdapter extends RecyclerView.Adapter<DiscountSearchItemAdapter.ViewHolder> {

    private List<DiscountSearchViewController> mItems;

    public DiscountSearchItemAdapter() {
        mItems = new ArrayList<>();
    }

    @Override
    public DiscountSearchItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        CustomViewController item = mItems.get(position);

        item.inflateInParent(parent, false);

        return new DiscountSearchItemAdapter.ViewHolder(item);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(DiscountSearchItemAdapter.ViewHolder holder, int position) {
        DiscountSearchViewController viewController = mItems.get(position);
        viewController.draw();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addItems(List<DiscountSearchViewController> items) {
        mItems.addAll(items);
    }

    public void clear() {
        int size = this.mItems.size();
        this.mItems.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void notifyItemInserted() {
        notifyItemInserted(mItems.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomViewController mViewController;

        public ViewHolder(CustomViewController viewController) {
            super(viewController.getView());
            mViewController = viewController;
            mViewController.initializeControls();
        }
    }
}
