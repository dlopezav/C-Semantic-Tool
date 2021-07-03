package com.cppsemantictool.backend.model.requestmodel;

import com.cppsemantictool.backend.model.Variable;

import java.util.List;

public class EvaluateModel {
    private String code;
    private List<Variable> variables;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }
}
