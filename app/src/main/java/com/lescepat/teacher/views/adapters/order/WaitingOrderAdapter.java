package com.lescepat.teacher.views.adapters.order;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.List;

import com.lescepat.teacher.R;
import com.lescepat.teacher.data.DataManager;
import com.lescepat.teacher.model.WaitingOrder;
import com.lescepat.teacher.databinding.RvItemWaitingOrderBinding;
import com.lescepat.teacher.utils.RetrofitErrorAdapter;
import com.lescepat.teacher.viewmodels.WaitingOrderViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class WaitingOrderAdapter extends RecyclerView.Adapter<WaitingOrderAdapter.ListViewHolder>  {
    private RvItemWaitingOrderBinding waitingItemBinding;
    private List<WaitingOrder> mWaitingOrder;
    private Context mContext;
    private OnItemClickListener listener;
    private Button btnAcceptOrder, btnDeclineOrder;

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

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public ListViewHolder(View itemView) {
            super(itemView);

            btnAcceptOrder = (Button) itemView.findViewById(R.id.btn_accept_order);
            btnAcceptOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final WaitingOrder waitingOrder = mWaitingOrder.get(position);
//                    Toast.makeText(mContext, "" + waitingOrder.getId(), Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            mContext);
                    builder.setMessage("Accept Order?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        //do something
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            acceptOrder(waitingOrder.getId(), position);
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                        }
                                    });
                    final AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            btnDeclineOrder = (Button) itemView.findViewById(R.id.btn_decline_order);
            btnDeclineOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final WaitingOrder waitingOrder = mWaitingOrder.get(position);
//                    Toast.makeText(mContext, "" + waitingOrder.getId(), Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            mContext);
                    builder.setMessage("Decline Order?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        //do something
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            declineOrder(waitingOrder.getId(), position);
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                        }
                                    });
                    final AlertDialog alert = builder.create();
                    alert.show();
                }
            });

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

    public void acceptOrder(int id, final int position) {
        DataManager.can().acceptOrder(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                {
                    @Override
                    public void accept (JsonObject object) throws Exception
                    {
                        Toast.makeText(mContext, "Order Accepted", Toast.LENGTH_SHORT).show();
                        // Reload current fragment
//                        WaitingOrderAdapter.this.notifyDataSetChanged();
                        removeAt(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void declineOrder(int id, final int position) {
        DataManager.can().declineOrder(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                {
                    @Override
                    public void accept (JsonObject object) throws Exception
                    {
                        Toast.makeText(mContext, "Order Decline", Toast.LENGTH_SHORT).show();
                        // Reload current fragment
                        removeAt(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void removeAt(int position) {
        mWaitingOrder.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mWaitingOrder.size());
    }
}
