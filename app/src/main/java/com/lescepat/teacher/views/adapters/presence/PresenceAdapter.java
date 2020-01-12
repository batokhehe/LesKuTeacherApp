package com.lescepat.teacher.views.adapters.presence;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.List;

import com.lescepat.teacher.R;
import com.lescepat.teacher.data.DataManager;
import com.lescepat.teacher.databinding.RvItemPresenceBinding;
import com.lescepat.teacher.viewmodels.PresenceViewModel;
import com.lescepat.teacher.model.Presence;
import com.lescepat.teacher.utils.RetrofitErrorAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class PresenceAdapter extends RecyclerView.Adapter<PresenceAdapter.ListViewHolder>  {
    private RvItemPresenceBinding presenceItemBinding;
    private List<Presence> mPresence;
    private Context mContext;
    private OnItemClickListener listener;
    private Button btnPresence;

    public PresenceAdapter(List<Presence> Presences, Context context) {
        mPresence = Presences;
        mContext = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        presenceItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_presence, parent, false);

        PresenceAdapter.ListViewHolder vh = new PresenceAdapter.ListViewHolder(presenceItemBinding.getRoot());
        vh.setBinding(presenceItemBinding);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PresenceAdapter.ListViewHolder holder, int position) {
        holder.setOrder(mPresence.get(position));
    }

    @Override
    public int getItemCount() {
        return (mPresence == null) ? 0 : mPresence.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public ListViewHolder(View itemView) {
            super(itemView);

            btnPresence = (Button) itemView.findViewById(R.id.btn_presence);
            btnPresence.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final Presence presence = mPresence.get(position);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            mContext);
                    final EditText etUniqueCode = new EditText(mContext);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(10, 5, 10, 5);
                    etUniqueCode.setLayoutParams(lp);
                    builder.setMessage("Kode Unik : ")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        //do something
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            String uniqueCode = etUniqueCode.getText().toString();
                                            if(uniqueCode.isEmpty()){
                                                Toast.makeText(mContext, "Isi Kode Unik!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                presence(presence.getId(), uniqueCode, position);
                                            }
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
                    alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alert.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog);
                    alert.setView(etUniqueCode);
                    alert.show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(mPresence.get(position));
                    }
                }
            });
        }

        void setBinding (RvItemPresenceBinding binding)
        {
            presenceItemBinding = binding;
        }

        public void setOrder (Presence Presences)
        {
            if (presenceItemBinding.getPresence() == null)
            {
                presenceItemBinding.setPresence(new PresenceViewModel(Presences));
            }
            else
            {
                presenceItemBinding.getPresence().setOrder(Presences);
            }

        }
    }

    public interface OnItemClickListener{
        void onItemClick(Presence Presences);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void presence(int id, String uniqueCode, final int position) {
        DataManager.can().confirmPresence(id, uniqueCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                {
                    @Override
                    public void accept (JsonObject object) throws Exception
                    {
                        Toast.makeText(mContext, "Presence Accepted", Toast.LENGTH_SHORT).show();
                        // Reload current fragment
//                        PresenceAdapter.this.notifyDataSetChanged();
                        removeAt(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(mContext, "Wrong Unique Code", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void removeAt(int position) {
        mPresence.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mPresence.size());
    }
}
