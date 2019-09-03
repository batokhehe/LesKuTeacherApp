package com.lescepat.teacher.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinishedOrder {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("study_class_id")
    @Expose
    private int studyClassId;
    @SerializedName("subject_id")
    @Expose
    private int subjectId;
    @SerializedName("study_start_at")
    @Expose
    private String studyStartAt;
    @SerializedName("study_end_at")
    @Expose
    private String studyEndAt;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("student_image")
    @Expose
    private String studentImage;
    @SerializedName("student_address")
    @Expose
    private String studentAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudyClassId() {
        return studyClassId;
    }

    public void setStudyClassId(int studyClassId) {
        this.studyClassId = studyClassId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(String studentImage) {
        this.studentImage = studentImage;
    }

    public String getStudyStartAt() {
        return studyStartAt;
    }

    public void setStudyStartAt(String studyStartAt) {
        this.studyStartAt = studyStartAt;
    }

    public String getStudyEndAt() {
        return studyEndAt;
    }

    public void setStudyEndAt(String studyEndAt) {
        this.studyEndAt = studyEndAt;
    }
}
