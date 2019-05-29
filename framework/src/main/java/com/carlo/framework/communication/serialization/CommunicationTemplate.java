package com.carlo.framework.communication.serialization;

public class CommunicationTemplate {
    private String className;
    private String data;

    public String getClassName() {
        return className;
    }

    public void setClassName(String classType) {
        this.className = classType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
