package com.example.dto;

public enum StatusEnum {
    FAILED(0, "Failed", "FF0000"),
    SUCCESS(1, "Passed", "00FF00"),
    ;

    private final int code;
    private final String message;
    private final String bgColor;
    StatusEnum(int code, String message, String bgColor) {
        this.code = code;
        this.message = message;
        this.bgColor = bgColor;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getBgColor() {
        return bgColor;
    }

    public static StatusEnum getValidate(Boolean isSuccess) {
        return isSuccess ? SUCCESS : FAILED;
    }
}
