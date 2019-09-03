package com.lescepat.teacher.views.fragments.order;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.lescepat.teacher.views.adapters.order.FinishedOrderAdapter;
import com.lescepat.teacher.views.fragments.BaseFragment;
import id.co.leskuteacher.R;
import com.lescepat.teacher.data.DataManager;
import id.co.leskuteacher.databinding.FragmentFinishedOrderBinding;
import com.lescepat.teacher.model.FinishedOrder;
import com.lescepat.teacher.utils.RetrofitErrorAdapter;
import com.lescepat.teacher.viewmodels.FinishedOrderListViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class FinishedOrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    FragmentFinishedOrderBinding mBinding;
    List<FinishedOrder> mFinishedOrder;
    private OnFragmentInteractionListener mListener;
    FinishedOrderAdapter adapter;
    private boolean allowRefresh;

    public FinishedOrderFragment() {
        // Required empty public constructor
        setArguments(new Bundle());
        mFinishedOrder = new ArrayList<>();
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initEvent() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_finished_order, container, false);
        mBinding.setOrders(new FinishedOrderListViewModel());

        adapter = new FinishedOrderAdapter(mFinishedOrder, getContext());

        mBinding.rvDoneOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvDoneOrder.setAdapter(adapter);

        adapter.setOnClickListener(new FinishedOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FinishedOrder waitingOrder) {
                allowRefresh = true;
            }
        });

        // SwipeRefreshLayout
        onCreateSwipeToRefresh(mBinding.swipeDoneOrder);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mBinding.swipeDoneOrder.post(new Runnable() {
            @Override
            public void run() {
                // Fetching data from server
                loadRecyclerViewData();
            }
        });

        return mBinding.getRoot();
    }

    private void onCreateSwipeToRefresh(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (allowRefresh)
        {
            allowRefresh = false;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onRefresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                loadRecyclerViewData();
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void loadRecyclerViewData()
    {
        // Showing refresh animation before making http call
        mBinding.swipeDoneOrder.setRefreshing(true);
        DataManager.can().getDoneOrderList().observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<FinishedOrder>())
                .subscribe(new Consumer<List<FinishedOrder>>()
                {
                    @Override
                    public void accept (List<FinishedOrder> waitingOrders) throws Exception
                    {
                        mFinishedOrder.clear();
                        mFinishedOrder.addAll(waitingOrders);

                        for (int i = 0; i< mFinishedOrder.size(); i++){
                            Log.i("Done order: ", mFinishedOrder.get(i).getSubjectName());
                        }
                        mBinding.rvDoneOrder.getAdapter().notifyDataSetChanged();
                        if (mFinishedOrder.size() == 0)
                        {
                            mBinding.llDoneList.showEmptyView(true);
                        } else {
                            mBinding.llDoneList.showEmptyView(false);
                        }
                        mBinding.swipeDoneOrder.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.swipeDoneOrder.setRefreshing(false);
                    }
                });
    }
}
