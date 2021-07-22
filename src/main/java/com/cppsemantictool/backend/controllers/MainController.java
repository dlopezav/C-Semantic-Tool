package com.cppsemantictool.backend.controllers;

import com.cppsemantictool.backend.gen.CPP14Lexer;
import com.cppsemantictool.backend.gen.CPP14Parser;
import com.cppsemantictool.backend.listener.SyntaxError;
import com.cppsemantictool.backend.listener.SyntaxErrorListener;
import com.cppsemantictool.backend.model.requestmodel.EvaluateModel;
import com.cppsemantictool.backend.model.responsemodel.SyntaxErrorResponse;
import com.cppsemantictool.backend.visitor.CppVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {
    @PostMapping(value = "/evaluate")
    public HttpEntity<? extends List<? extends Object>> controller(@RequestBody EvaluateModel body){
        CommonTokenStream tokens = new CommonTokenStream(new CPP14Lexer(CharStreams.fromString(body.getCode())));
        CPP14Parser parser = new CPP14Parser(tokens);
        SyntaxErrorListener listener = new SyntaxErrorListener();
        parser.addErrorListener(listener);
        //ParseTree tree = parser.translationUnit();
        //ParseTree tree = parser.literal();
        ParseTree tree = parser.multiplicativeExpression();
        if(parser.getNumberOfSyntaxErrors() > 0){
            List<SyntaxErrorResponse> errors = new ArrayList<>();
            for (SyntaxError error : listener.getSyntaxErrors()) {
                errors.add(new SyntaxErrorResponse(error.getLine(), error.getCharPositionInLine(), error.getMessage()));
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        CppVisitor<Object> loader = new CppVisitor<>(body.getVariables());
        loader.visit(tree);
        return new ResponseEntity<>(loader.getDetectedErrors(), HttpStatus.ACCEPTED);
    }
}
