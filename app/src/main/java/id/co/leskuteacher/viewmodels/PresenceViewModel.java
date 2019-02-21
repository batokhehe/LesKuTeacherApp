package id.co.leskuteacher.viewmodels;

import id.co.leskuteacher.model.Presence;
import id.co.leskuteacher.viewmodels.inputs.PresenceViewModelInputs;
import id.co.leskuteacher.viewmodels.outputs.PresenceViewModelOutputs;

public class PresenceViewModel extends BaseViewModel implements PresenceViewModelInputs, PresenceViewModelOutputs {

    private Presence mPresence;

    public PresenceViewModel(Presence presence)
    {
        mPresence = presence;
        notifyChange();
    }

    @Override
    public void setOrder(Presence mPresence) {
        mPresence = mPresence;
        notifyChange();
    }

    @Override
    public String getId() {
        return String.valueOf(mPresence.getId());
    }

    @Override
    public String getStudyClassId() {
        return String.valueOf(mPresence.getStudyClassId());
    }

    @Override
    public String getSubjectId() {
        return String.valueOf(mPresence.getSubjectId());
    }

    @Override
    public String getSubjectName() {
        return String.valueOf(mPresence.getSubjectName());
    }

    @Override
    public String getStudentName() {
        return String.valueOf(mPresence.getStudentName());
    }

    @Override
    public String getStudentAddress() {
        return String.valueOf(mPresence.getStudentAddress());
    }

    @Override
    public String getStudentImage() {
        return String.valueOf(mPresence.getStudentImage());
    }

    @Override
    public String getStudyStartAt() {
        return "Tanggal Pertemuan : " + String.valueOf(mPresence.getStudyStartAt());
    }
}
