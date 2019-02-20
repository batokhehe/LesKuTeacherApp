package id.co.leskuteacher.viewmodels;

import id.co.leskuteacher.model.UpcomingOrder;
import id.co.leskuteacher.model.UpcomingOrder;
import id.co.leskuteacher.viewmodels.inputs.UpcomingOrderViewModelInputs;
import id.co.leskuteacher.viewmodels.inputs.UpcomingOrderViewModelInputs;
import id.co.leskuteacher.viewmodels.outputs.UpcomingOrderViewModelOutputs;

public class UpcomingOrderViewModel extends BaseViewModel implements UpcomingOrderViewModelInputs, UpcomingOrderViewModelOutputs {

    private UpcomingOrder mUpcomingOrder;

    public UpcomingOrderViewModel(UpcomingOrder upcomingOrder)
    {
        mUpcomingOrder = upcomingOrder;
        notifyChange();
    }

    @Override
    public void setOrder(UpcomingOrder mUpcomingOrder) {
        mUpcomingOrder = mUpcomingOrder;
        notifyChange();
    }

    @Override
    public String getId() {
        return String.valueOf(mUpcomingOrder.getId());
    }

    @Override
    public String getStudyClassId() {
        return String.valueOf(mUpcomingOrder.getStudyClassId());
    }

    @Override
    public String getSubjectId() {
        return String.valueOf(mUpcomingOrder.getSubjectId());
    }

    @Override
    public String getSubjectName() {
        return String.valueOf(mUpcomingOrder.getSubjectName());
    }

    @Override
    public String getStudentName() {
        return String.valueOf(mUpcomingOrder.getStudentName());
    }

    @Override
    public String getStudentAddress() {
        return String.valueOf(mUpcomingOrder.getStudentAddress());
    }

    @Override
    public String getStudentImage() {
        return String.valueOf(mUpcomingOrder.getStudentImage());
    }

    @Override
    public String getStudyStartAt() {
        return "Tanggal Pertemuan : " + String.valueOf(mUpcomingOrder.getStudyStartAt());
    }
}
