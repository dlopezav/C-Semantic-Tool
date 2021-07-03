package com.cppsemantictool.backend.listener;


import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.Utils;

import java.util.ArrayList;
import java.util.List;

public class SyntaxErrorListener extends BaseErrorListener {
    private final List<SyntaxError> syntaxErrors = new ArrayList<>();

    public List<SyntaxError> getSyntaxErrors(){
        return this.syntaxErrors;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e){
        this.syntaxErrors.add(new SyntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e));
    }

    @Override
    public String toString(){
        return Utils.join(this.syntaxErrors.iterator(), "\n");
    }

    public String ToString(){
        return Utils.join(this.syntaxErrors.iterator(), "\n");
    }
}
