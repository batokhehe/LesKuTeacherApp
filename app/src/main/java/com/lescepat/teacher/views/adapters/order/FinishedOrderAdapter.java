package com.lescepat.teacher.views.adapters.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.lescepat.teacher.R;
import com.lescepat.teacher.model.FinishedOrder;
import com.lescepat.teacher.databinding.RvItemFinishedOrderBinding;
import com.lescepat.teacher.viewmodels.FinishedOrderViewModel;

public class FinishedOrderAdapter extends RecyclerView.Adapter<FinishedOrderAdapter.ListViewHolder>  {
    private RvItemFinishedOrderBinding finishedItemBinding;
    private List<FinishedOrder> mFinishedOrder;
    private Context mContext;
    private OnItemClickListener listener;

    public FinishedOrderAdapter(List<FinishedOrder> finishedOrders, Context context) {
        mFinishedOrder = finishedOrders;
        mContext = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        finishedItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_finished_order, parent, false);

        FinishedOrderAdapter.ListViewHolder vh = new FinishedOrderAdapter.ListViewHolder(finishedItemBinding.getRoot());
        vh.setBinding(finishedItemBinding);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FinishedOrderAdapter.ListViewHolder holder, int position) {
        holder.setOrder(mFinishedOrder.get(position));
    }

    @Override
    public int getItemCount() {
        return (mFinishedOrder == null) ? 0 : mFinishedOrder.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public ListViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(mFinishedOrder.get(position));
                    }
                }
            });
        }

        void setBinding (RvItemFinishedOrderBinding binding)
        {
            finishedItemBinding = binding;
        }

        public void setOrder (FinishedOrder finishedOrder)
        {
            if (finishedItemBinding.getFinishedOrder() == null)
            {
                finishedItemBinding.setFinishedOrder(new FinishedOrderViewModel(finishedOrder));
            }
            else
            {
                finishedItemBinding.getFinishedOrder().setOrder(finishedOrder);
            }

        }
    }

    public interface OnItemClickListener{
        void onItemClick(FinishedOrder finishedOrders);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
