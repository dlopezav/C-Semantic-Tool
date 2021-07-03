package com.cppsemantictool.backend.visitor;

import com.cppsemantictool.backend.gen.CPP14Parser;
import com.cppsemantictool.backend.gen.CPP14ParserBaseVisitor;
import com.cppsemantictool.backend.model.SemanticError;
import com.cppsemantictool.backend.model.Variable;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class CppVisitor <T> extends CPP14ParserBaseVisitor<T> {
    private List<Variable> variables;
    private List<SemanticError> detectedErrors;

    public CppVisitor(List<Variable> variables) {
        this.variables = variables;
        this.detectedErrors = new ArrayList<>();
    }

    public List<SemanticError> getDetectedErrors() {
        return detectedErrors;
    }

    @Override
    public T visitLiteral(CPP14Parser.LiteralContext ctx) {
        if(ctx.IntegerLiteral() != null){
            TerminalNode node = ctx.IntegerLiteral();
            long value;
            if(node.getText().startsWith("0b") || node.getText().startsWith("0B")){
                // Entero binario.
                value = Long.parseLong(node.getText().substring(2), 2);
            }else if(node.getText().startsWith("0x") || node.getText().startsWith("0X")){
                // Entero hexadecimal.
                value = Long.parseLong(node.getText().substring(2), 16);
            }else if(!node.getText().equals("0") && node.getText().startsWith("0")){
                // Entero octal.
                value = Long.parseLong(node.getText().substring(1), 8);
            }else{
                // Entero decimal.
                value = Long.parseLong(node.getText());
            }
            System.out.println(value);
            if(/*value > Math.pow(2, 31) - 1 */true){
                // ERROR! OVERFLOW
                this.detectedErrors.add(new SemanticError(node.getSymbol().getLine() ,node.getSymbol().getStartIndex(), SemanticError.ErrorType.OVERFLOW, "Riesgo de overflow"));
            }
        }else if(ctx.FloatingLiteral() != null){

        }
        return null;
    }

    @Override
    public T visitIdExpression(CPP14Parser.IdExpressionContext ctx) {
        return super.visitIdExpression(ctx);
    }

    @Override
    public T visitMultiplicativeExpression(CPP14Parser.MultiplicativeExpressionContext ctx) {
        return super.visitMultiplicativeExpression(ctx);
    }
}
