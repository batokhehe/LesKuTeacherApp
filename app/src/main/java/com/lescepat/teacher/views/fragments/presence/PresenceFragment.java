package com.lescepat.teacher.views.fragments.presence;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.lescepat.teacher.R;
import com.lescepat.teacher.databinding.FragmentPresenceBinding;
import com.lescepat.teacher.views.fragments.BaseFragment;
import com.lescepat.teacher.data.DataManager;
import com.lescepat.teacher.model.Presence;
import com.lescepat.teacher.utils.RetrofitErrorAdapter;
import com.lescepat.teacher.viewmodels.PresenceListViewModel;
import com.lescepat.teacher.views.adapters.presence.PresenceAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class PresenceFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    FragmentPresenceBinding mBinding;
    List<Presence> mPresence;
    private OnFragmentInteractionListener mListener;
    PresenceAdapter adapter;
    private boolean allowRefresh;

    public PresenceFragment() {
        // Required empty public constructor
        setArguments(new Bundle());
        mPresence = new ArrayList<>();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_presence, container, false);
        mBinding.setOrders(new PresenceListViewModel());

        adapter = new PresenceAdapter(mPresence, getContext());

        mBinding.rvPresence.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvPresence.setAdapter(adapter);

        adapter.setOnClickListener(new PresenceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Presence Presence) {
                allowRefresh = true;
            }
        });

        // SwipeRefreshLayout
        onCreateSwipeToRefresh(mBinding.swipePresence);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mBinding.swipePresence.post(new Runnable() {
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
        mBinding.swipePresence.setRefreshing(true);
        DataManager.can().getPresenceList().observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<Presence>())
                .subscribe(new Consumer<List<Presence>>()
                {
                    @Override
                    public void accept (List<Presence> Presences) throws Exception
                    {
                        mPresence.clear();
                        mPresence.addAll(Presences);

                        for (int i = 0; i<mPresence.size(); i++){
                            Log.i("Waiting order: ", mPresence.get(i).getSubjectName());
                        }
                        mBinding.rvPresence.getAdapter().notifyDataSetChanged();
                        if (mPresence.size() == 0)
                        {
                            mBinding.llWaitingList.showEmptyView(true);
                        } else {
                            mBinding.llWaitingList.showEmptyView(false);
                        }
                        mBinding.swipePresence.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.swipePresence.setRefreshing(false);
                    }
                });
    }
}
