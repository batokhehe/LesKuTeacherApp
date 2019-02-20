package id.co.leskuteacher.views.fragments.order;

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

import id.co.leskuteacher.R;
import id.co.leskuteacher.data.DataManager;
import id.co.leskuteacher.databinding.FragmentUpcomingOrderBinding;
import id.co.leskuteacher.model.UpcomingOrder;
import id.co.leskuteacher.utils.RetrofitErrorAdapter;
import id.co.leskuteacher.viewmodels.UpcomingOrderListViewModel;
import id.co.leskuteacher.views.adapters.order.UpcomingOrderAdapter;
import id.co.leskuteacher.views.fragments.BaseFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class UpcomingOrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    FragmentUpcomingOrderBinding mBinding;
    List<UpcomingOrder> mUpcomingOrder;
    private OnFragmentInteractionListener mListener;
    UpcomingOrderAdapter adapter;
    private boolean allowRefresh;

    public UpcomingOrderFragment() {
        // Required empty public constructor
        setArguments(new Bundle());
        mUpcomingOrder = new ArrayList<>();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming_order, container, false);
        mBinding.setOrders(new UpcomingOrderListViewModel());

        adapter = new UpcomingOrderAdapter(mUpcomingOrder, getContext());

        mBinding.rvUpcomingOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvUpcomingOrder.setAdapter(adapter);

        adapter.setOnClickListener(new UpcomingOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UpcomingOrder UpcomingOrder) {
                allowRefresh = true;
            }
        });

        // SwipeRefreshLayout
        mBinding.swipeUpcomingOrder.setOnRefreshListener(this);
        mBinding.swipeUpcomingOrder.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mBinding.swipeUpcomingOrder.post(new Runnable() {
                @Override
                public void run() {

                    mBinding.swipeUpcomingOrder.setRefreshing(true);

                    // Fetching data from server
                    loadRecyclerViewData();
                }
        });

        return mBinding.getRoot();
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
        mBinding.swipeUpcomingOrder.setRefreshing(true);
        DataManager.can().getUpcomingOrderList().observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<UpcomingOrder>())
                .subscribe(new Consumer<List<UpcomingOrder>>()
                {
                    @Override
                    public void accept (List<UpcomingOrder> UpcomingOrders) throws Exception
                    {
                        mUpcomingOrder.clear();
                        mUpcomingOrder.addAll(UpcomingOrders);

                        for (int i = 0; i<mUpcomingOrder.size(); i++){
                            Log.i("Upcoming order: ", mUpcomingOrder.get(i).getSubjectName());
                        }
                        mBinding.rvUpcomingOrder.getAdapter().notifyDataSetChanged();
                        if (mUpcomingOrder.size() == 0)
                        {
                            mBinding.llUpcomingList.showEmptyView(true);
                        } else {
                            mBinding.llUpcomingList.showEmptyView(false);
                        }
                        mBinding.swipeUpcomingOrder.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.swipeUpcomingOrder.setRefreshing(false);
                    }
                });

    }

}
