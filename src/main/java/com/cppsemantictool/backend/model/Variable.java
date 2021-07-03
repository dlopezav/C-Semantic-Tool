package com.cppsemantictool.backend.model;

public class Variable {
    private String name;
    private int min;
    private int max;

    public Variable(String name, int min, int max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }

    public Variable() {}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
