package com.buyfull.openapiv1.dao;

public enum CodeType {

    A(1,"A"),
    B(2,"B");

    private final int type;
    private final String soundType;

    private CodeType(int type, String soundType) {
        this.type = type;
        this.soundType = soundType;
    }

    public int getType() {
        return type;
    }

    public String getSoundType() {
        return soundType;
    }
}
