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
    public T visitTranslationUnit(CPP14Parser.TranslationUnitContext ctx) {
        return super.visitTranslationUnit(ctx);
    }

    @Override
    public T visitAdditiveExpression(CPP14Parser.AdditiveExpressionContext ctx) {
        return super.visitAdditiveExpression(ctx);
    }

    @Override
    public T visitMultiplicativeExpression(CPP14Parser.MultiplicativeExpressionContext ctx) {
        T initialValue = this.visitPointerMemberExpression(ctx.pointerMemberExpression(0));
        for(int i = 1; i < ctx.getChildCount(); i += 2){
            T otherValue = this.visitPointerMemberExpression(ctx.pointerMemberExpression(i + 1));
            if(ctx.getChild(i).getText().equals("*")) {
                // Multiplicación
                // TODO: Probar si la division es igual al producto segun sea el tipo de dato.
            }else if(ctx.getChild(i).getText().equals("/")){
                // Division
                // TODO: Perdida de información segun los tipos de datos
            }else if(ctx.getChild(i).getText().equals("%")){
                // Módulo
                //TODO: Verificar tipos de datos.
            }

        }
        return initialValue;
    }

    @Override
    public T visitPointerMemberExpression(CPP14Parser.PointerMemberExpressionContext ctx) {
        return this.visitCastExpression(ctx.castExpression(0)); // Siempre se usa solo el primer CastExpresion
    }

    @Override
    public T visitCastExpression(CPP14Parser.CastExpressionContext ctx) {
        if(ctx.unaryExpression() != null){
            return this.visitUnaryExpression(ctx.unaryExpression());
        }else{
            // Verificación del nuevo tipo de dato
            T element = this.visitCastExpression(ctx.castExpression());
            if(ctx.theTypeId().getText().equals("int")){
                //TODO: Cambiar el número de bits.
            }else if(ctx.theTypeId().getText().equals("long long")){
                //TODO: Cambiar el número de bits
            }
            return element;
        }
    }

    @Override
    public T visitUnaryExpression(CPP14Parser.UnaryExpressionContext ctx) {
        if(ctx.postfixExpression() != null){
            return this.visitPostfixExpression(ctx.postfixExpression());
        }else if(ctx.unaryExpression() != null){
            if(ctx.PlusPlus() != null){
                T element = this.visitUnaryExpression(ctx.unaryExpression());
                //TODO: añadir 1;
                return element;
            }else if(ctx.MinusMinus() != null){
                //TODO: disminuir 1;
            }else if(ctx.Sizeof() != null){
                //TODO: T = 64 bits;
            }
        }else if(ctx.Sizeof() != null){
            //TODO: T = 64 bits;
        }else if(ctx.Alignof() != null){
            //TODO: T = 64 bits;
        }else if(ctx.noExceptExpression() != null) {
            return this.visitNoExceptExpression(ctx.noExceptExpression());
        }else if(ctx.newExpression() != null) {
            return this.visitNewExpression(ctx.newExpression());
        }else if(ctx.deleteExpression() != null) {
            return this.visitDeleteExpression(ctx.deleteExpression());
        }
        return null;
    }

    @Override
    public T visitPostfixExpression(CPP14Parser.PostfixExpressionContext ctx) {
        if(ctx.primaryExpression() != null){
            return this.visitPrimaryExpression(ctx.primaryExpression());
        }else {
            throw new UnsupportedOperationException("Visita a PostFixException");
            //TODO: acceso a arreglos, y elementos de objetos estructuras y apuntadores

        }
    }

    @Override
    public T visitPrimaryExpression(CPP14Parser.PrimaryExpressionContext ctx) {
        if(ctx.literal() != null){
            return this.visitLiteral(ctx.literal(0)); // Los literales deben ir solos
        }else if(ctx.idExpression() != null){
            // TODO: Variables
            throw new UnsupportedOperationException("Visita a PrimaryException");
        }else {
            throw new UnsupportedOperationException("Visita a PrimaryException");
            // TODO: paréntesis, this, expresiones lambda
        }
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

}
