package id.co.leskuteacher.views.adapters.order;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.leskuteacher.R;
import id.co.leskuteacher.data.DataManager;
import id.co.leskuteacher.databinding.RvItemUpcomingOrderBinding;
import id.co.leskuteacher.model.UpcomingOrder;
import id.co.leskuteacher.model.UpcomingOrder;
import id.co.leskuteacher.utils.RetrofitErrorAdapter;
import id.co.leskuteacher.viewmodels.UpcomingOrderViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class UpcomingOrderAdapter extends RecyclerView.Adapter<UpcomingOrderAdapter.ListViewHolder>  {
    private RvItemUpcomingOrderBinding upcomingItemBinding;
    private List<UpcomingOrder> mUpcomingOrder;
    private Context mContext;
    private OnItemClickListener listener;
    private Button btnConfirmOrder, btnRescheduleOrder;

    public UpcomingOrderAdapter(List<UpcomingOrder> upcomingOrders, Context context) {
        mUpcomingOrder = upcomingOrders;
        mContext = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        upcomingItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_upcoming_order, parent, false);

        UpcomingOrderAdapter.ListViewHolder vh = new UpcomingOrderAdapter.ListViewHolder(upcomingItemBinding.getRoot());
        vh.setBinding(upcomingItemBinding);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingOrderAdapter.ListViewHolder holder, int position) {
        holder.setOrder(mUpcomingOrder.get(position));
    }

    @Override
    public int getItemCount() {
        return (mUpcomingOrder == null) ? 0 : mUpcomingOrder.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public ListViewHolder(View itemView) {
            super(itemView);

            btnConfirmOrder = (Button) itemView.findViewById(R.id.btn_confirm_order);
            btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final UpcomingOrder upcomingOrder = mUpcomingOrder.get(position);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            mContext);
                    builder.setMessage("Accept Order?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        //do something
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            confirmOrder(upcomingOrder.getId(), position);
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

            btnRescheduleOrder = (Button) itemView.findViewById(R.id.btn_reschedule_order);
            btnRescheduleOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final UpcomingOrder upcomingOrder = mUpcomingOrder.get(position);
//                    Toast.makeText(mContext, "" + UpcomingOrder.getId(), Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            mContext);
                    builder.setMessage("Decline Order?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        //do something
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            rescheduleOrder(upcomingOrder.getId(), position);
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
                        listener.onItemClick(mUpcomingOrder.get(position));
                    }
                }
            });
        }

        void setBinding (RvItemUpcomingOrderBinding binding)
        {
            upcomingItemBinding = binding;
        }

        public void setOrder (UpcomingOrder UpcomingOrders)
        {
            if (upcomingItemBinding.getUpcomingOrder() == null)
            {
                upcomingItemBinding.setUpcomingOrder(new UpcomingOrderViewModel(UpcomingOrders));
            }
            else
            {
                upcomingItemBinding.getUpcomingOrder().setOrder(UpcomingOrders);
            }

        }
    }

    public interface OnItemClickListener{
        void onItemClick(UpcomingOrder UpcomingOrders);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void confirmOrder(int id, final int position) {
        DataManager.can().confirmOrder(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                {
                    @Override
                    public void accept (JsonObject object) throws Exception
                    {
                        Toast.makeText(mContext, "Order Confirmed", Toast.LENGTH_SHORT).show();
                        // Reload current fragment
//                        removeAt(position);
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

    public void rescheduleOrder(int id, final int position) {
        DataManager.can().rescheduleOrder(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                {
                    @Override
                    public void accept (JsonObject object) throws Exception
                    {
                        Toast.makeText(mContext, "Order Rescheduled", Toast.LENGTH_SHORT).show();
                        // Reload current fragment
//                        removeAt(position);
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
}
