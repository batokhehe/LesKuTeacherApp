package id.co.leskuteacher.views.adapters.order;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import id.co.leskuteacher.R;
import id.co.leskuteacher.databinding.RvItemWaitingOrderBinding;
import id.co.leskuteacher.model.WaitingOrder;
import id.co.leskuteacher.viewmodels.WaitingOrderViewModel;

public class WaitingOrderAdapter extends RecyclerView.Adapter<WaitingOrderAdapter.ListViewHolder>  {
    private RvItemWaitingOrderBinding waitingItemBinding;
    private List<WaitingOrder> mWaitingOrder;
    private Context mContext;
    private OnItemClickListener listener;

    public WaitingOrderAdapter(List<WaitingOrder> waitingOrders, Context context) {
        mWaitingOrder = waitingOrders;
        mContext = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        waitingItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_waiting_order, parent, false);

        WaitingOrderAdapter.ListViewHolder vh = new WaitingOrderAdapter.ListViewHolder(waitingItemBinding.getRoot());
        vh.setBinding(waitingItemBinding);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull WaitingOrderAdapter.ListViewHolder holder, int position) {
        holder.setOrder(mWaitingOrder.get(position));
    }

    @Override
    public int getItemCount() {
        return (mWaitingOrder == null) ? 0 : mWaitingOrder.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public ListViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(mWaitingOrder.get(position));
                    }
                }
            });
        }

        void setBinding (RvItemWaitingOrderBinding binding)
        {
            waitingItemBinding = binding;
        }

        public void setOrder (WaitingOrder waitingOrders)
        {
            if (waitingItemBinding.getWaitingOrder() == null)
            {
                waitingItemBinding.setWaitingOrder(new WaitingOrderViewModel(waitingOrders));
            }
            else
            {
                waitingItemBinding.getWaitingOrder().setOrder(waitingOrders);
            }

        }
    }

    public interface OnItemClickListener{
        void onItemClick(WaitingOrder waitingOrders);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
