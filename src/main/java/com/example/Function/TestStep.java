package com.example.Function;

public class TestStep {
    private int stepNumber;
    private String stepName;
    private String status;

    public TestStep(int stepNumber, String stepName, String status) {
        this.stepNumber = stepNumber;
        this.stepName = stepName;
        this.status = status;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public String getStepName() {
        return stepName;
    }

    public String getStatus() {
        return status;
    }
}
