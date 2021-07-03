package com.cppsemantictool.backend.visitor;

import com.cppsemantictool.backend.gen.CPP14ParserBaseVisitor;
import com.cppsemantictool.backend.model.SemanticError;
import com.cppsemantictool.backend.model.Variable;

import java.util.List;

public class CppVisitor <T> extends CPP14ParserBaseVisitor<T> {
    private List<Variable> variables;
    private List<SemanticError> detectedErrors;

    public CppVisitor(List<Variable> variables) {
        this.variables = variables;
    }

    public List<SemanticError> getDetectedErrors() {
        return detectedErrors;
    }
}
