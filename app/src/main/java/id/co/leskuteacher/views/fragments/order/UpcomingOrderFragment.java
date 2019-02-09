package id.co.leskuteacher.views.fragments.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.co.leskuteacher.R;
import id.co.leskuteacher.views.fragments.BaseFragment;

public class UpcomingOrderFragment extends BaseFragment {
    @Override
    public void initUI() {

    }

    @Override
    public void initEvent() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_upcoming_order, container, false);

        return rootView;
    }

}
