package com.lescepat.teacher.viewmodels;

import com.lescepat.teacher.viewmodels.inputs.FinishedOrderViewModelInputs;
import com.lescepat.teacher.viewmodels.outputs.FinishedOrderViewModelOutputs;
import com.lescepat.teacher.model.FinishedOrder;

public class FinishedOrderViewModel extends BaseViewModel implements FinishedOrderViewModelInputs, FinishedOrderViewModelOutputs {

    private FinishedOrder mFinishedOrder;

    public FinishedOrderViewModel(FinishedOrder waitingOrder)
    {
        mFinishedOrder = waitingOrder;
        notifyChange();
    }

    @Override
    public void setOrder(FinishedOrder mFinishedOrder) {
        mFinishedOrder = mFinishedOrder;
        notifyChange();
    }

    @Override
    public String getId() {
        return String.valueOf(mFinishedOrder.getId());
    }

    @Override
    public String getStudyClassId() {
        return String.valueOf(mFinishedOrder.getStudyClassId());
    }

    @Override
    public String getSubjectId() {
        return String.valueOf(mFinishedOrder.getSubjectId());
    }

    @Override
    public String getSubjectName() {
        return String.valueOf(mFinishedOrder.getSubjectName());
    }

    @Override
    public String getStudentName() {
        return String.valueOf(mFinishedOrder.getStudentName());
    }

    @Override
    public String getStudentAddress() {
        return String.valueOf(mFinishedOrder.getStudentAddress());
    }

    @Override
    public String getStudentImage() {
        return String.valueOf(mFinishedOrder.getStudentImage());
    }

    @Override
    public String getStudyStartAt() {
        return "Tanggal Pertemuan : " + String.valueOf(mFinishedOrder.getStudyStartAt());
    }

    @Override
    public String getStudyEndAt() {
        return "Tanggal Selesai : " + String.valueOf(mFinishedOrder.getStudyEndAt());
    }
}
