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
import id.co.leskuteacher.databinding.RvItemWaitingOrderBinding;
import id.co.leskuteacher.model.WaitingOrder;
import id.co.leskuteacher.utils.RetrofitErrorAdapter;
import id.co.leskuteacher.viewmodels.WaitingOrderViewModel;
import id.co.leskuteacher.views.fragments.order.WaitingOrderFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class WaitingOrderAdapter extends RecyclerView.Adapter<WaitingOrderAdapter.ListViewHolder>  {
    private RvItemWaitingOrderBinding waitingItemBinding;
    private List<WaitingOrder> mWaitingOrder;
    private Context mContext;
    private OnItemClickListener listener;
    private Button btnAcceptOrder;

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

            btnAcceptOrder = (Button) itemView.findViewById(R.id.btnAcceptOrder);
            btnAcceptOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
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
                                            WaitingOrderFragment waitingOrderFragment = new WaitingOrderFragment();
                                            waitingOrderFragment.acceptOrder(waitingOrder.getId());
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
}
