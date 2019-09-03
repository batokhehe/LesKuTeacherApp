package com.lescepat.teacher.viewmodels;

import com.lescepat.teacher.model.WaitingOrder;
import com.lescepat.teacher.viewmodels.inputs.WaitingOrderViewModelInputs;
import com.lescepat.teacher.viewmodels.outputs.WaitingOrderViewModelOutputs;

public class WaitingOrderViewModel extends BaseViewModel implements WaitingOrderViewModelInputs, WaitingOrderViewModelOutputs {

    private WaitingOrder mWaitingOrder;

    public WaitingOrderViewModel(WaitingOrder waitingOrder)
    {
        mWaitingOrder = waitingOrder;
        notifyChange();
    }

    @Override
    public void setOrder(WaitingOrder mWaitingOrder) {
        mWaitingOrder = mWaitingOrder;
        notifyChange();
    }

    @Override
    public String getId() {
        return String.valueOf(mWaitingOrder.getId());
    }

    @Override
    public String getStudyClassId() {
        return String.valueOf(mWaitingOrder.getStudyClassId());
    }

    @Override
    public String getSubjectId() {
        return String.valueOf(mWaitingOrder.getSubjectId());
    }

    @Override
    public String getSubjectName() {
        return String.valueOf(mWaitingOrder.getSubjectName());
    }

    @Override
    public String getStudentName() {
        return String.valueOf(mWaitingOrder.getStudentName());
    }

    @Override
    public String getStudentAddress() {
        return String.valueOf(mWaitingOrder.getStudentAddress());
    }

    @Override
    public String getStudentImage() {
        return String.valueOf(mWaitingOrder.getStudentImage());
    }

    @Override
    public String getStudyStartAt() {
        return "Tanggal Pertemuan : " + String.valueOf(mWaitingOrder.getStudyStartAt());
    }
}
