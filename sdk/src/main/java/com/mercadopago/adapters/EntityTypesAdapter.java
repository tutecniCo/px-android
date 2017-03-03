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
import com.mercadopago.model.Issuer;
import com.mercadopago.uicontrollers.entitytypes.EntityTypesView;
import com.mercadopago.uicontrollers.issuers.IssuersView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marlanti on 3/3/17.
 */


public class EntityTypesAdapter extends RecyclerView.Adapter<EntityTypesAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mEntityTypes;
    private OnSelectedCallback<Integer> mCallback;

    public EntityTypesAdapter(Context context, OnSelectedCallback<Integer> callback) {
        this.mContext = context;
        this.mEntityTypes = new ArrayList<>();
        this.mCallback = callback;
    }

    public void addResults(List<String> list) {
        mEntityTypes.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mEntityTypes.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View adapterView = inflater.inflate(R.layout.mpsdk_adapter_issuer, parent, false);
        ViewHolder viewHolder = new ViewHolder(adapterView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String entityType = mEntityTypes.get(position);
        holder.mEntityTypeView.drawEntityType(entityType);
    }


    public String getItem(int position) {
        return mEntityTypes.get(position);
    }

    @Override
    public int getItemCount() {
        return mEntityTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout mEntityTypeContainer;
        public EntityTypesView mEntityTypeView;

        public ViewHolder(View itemView) {
            super(itemView);
            mEntityTypeContainer = (FrameLayout) itemView.findViewById(R.id.mpsdkIssuerAdapterContainer);
            mEntityTypeView = new EntityTypesView(mContext);
            mEntityTypeView.inflateInParent(mEntityTypeContainer, true);
            mEntityTypeView.initializeControls();

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
