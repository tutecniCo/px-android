package com.mercadopago.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mercadopago.R;
import com.mercadopago.callbacks.OnSelectedCallback;
import com.mercadopago.model.FinancialInstitution;

import com.mercadopago.uicontrollers.financialInstitutions.FinancialInstitutionsView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by marlanti on 3/13/17.
 */

public class FinancialInstitutionsAdapter extends RecyclerView.Adapter<FinancialInstitutionsAdapter.ViewHolder> {


    private Context mContext;
    private List<FinancialInstitution> mFinancialInstitutions;
    private OnSelectedCallback<Integer> mCallback;

    public FinancialInstitutionsAdapter(Context context, OnSelectedCallback<Integer> callback) {
        this.mContext = context;
        this.mFinancialInstitutions = new ArrayList<>();
        this.mCallback = callback;
    }

    public void addResults(List<FinancialInstitution> list) {
        mFinancialInstitutions.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mFinancialInstitutions.clear();
        notifyDataSetChanged();
    }

    @Override
    public com.mercadopago.adapters.FinancialInstitutionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View adapterView = inflater.inflate(R.layout.mpsdk_adapter_financial_institution, parent, false);
        com.mercadopago.adapters.FinancialInstitutionsAdapter.ViewHolder viewHolder = new com.mercadopago.adapters.FinancialInstitutionsAdapter.ViewHolder(adapterView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(com.mercadopago.adapters.FinancialInstitutionsAdapter.ViewHolder holder, int position) {
        FinancialInstitution financialInstitution = mFinancialInstitutions.get(position);
        holder.mFinancialInstitutionsView.drawFinancialInstitution(financialInstitution);
    }


    public FinancialInstitution getItem(int position) {
        return mFinancialInstitutions.get(position);
    }

    @Override
    public int getItemCount() {
        return mFinancialInstitutions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout mFinancialInstitutionsContainer;
        public FinancialInstitutionsView mFinancialInstitutionsView;

        public ViewHolder(View itemView) {
            super(itemView);
            mFinancialInstitutionsContainer = (FrameLayout) itemView.findViewById(R.id.mpsdkFinancialInstitutionAdapterContainer);
            mFinancialInstitutionsView = new FinancialInstitutionsView(mContext);
            mFinancialInstitutionsView.inflateInParent(mFinancialInstitutionsContainer, true);
            mFinancialInstitutionsView.initializeControls();

            itemView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event != null && event.getAction() == KeyEvent.ACTION_DOWN
                            && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
                        mCallback.onSelected(getLayoutPosition());
                        return true;
                    }
                    return false;
                }
            });
        }
    }

}
