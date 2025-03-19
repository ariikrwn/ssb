package com.example.dto;

import java.io.File;

public class ResultCapture {
    private String remark;
    private File image;

    public ResultCapture(String remark, File image) {
        this.image = image;
        this.remark = remark;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
