package com.cppsemantictool.backend.visitor;

import com.cppsemantictool.backend.gen.CPP14Parser;
import com.cppsemantictool.backend.gen.CPP14ParserBaseVisitor;
import com.cppsemantictool.backend.model.MemoryVariable;
import com.cppsemantictool.backend.model.SemanticError;
import com.cppsemantictool.backend.model.Variable;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Arrays;
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
    public T visitExpression(CPP14Parser.ExpressionContext ctx) {
        return super.visitExpression(ctx);
    }

    @Override
    public T visitAssignmentExpression(CPP14Parser.AssignmentExpressionContext ctx) {
        return super.visitAssignmentExpression(ctx);
    }

    @Override
    public T visitLogicalOrExpression(CPP14Parser.LogicalOrExpressionContext ctx) {
        return super.visitLogicalOrExpression(ctx);
    }

    @Override
    public T visitLogicalAndExpression(CPP14Parser.LogicalAndExpressionContext ctx) {
        return super.visitLogicalAndExpression(ctx);
    }

    @Override
    public T visitInclusiveOrExpression(CPP14Parser.InclusiveOrExpressionContext ctx) {
        return super.visitInclusiveOrExpression(ctx);
    }

    @Override
    public T visitExclusiveOrExpression(CPP14Parser.ExclusiveOrExpressionContext ctx) {
        return super.visitExclusiveOrExpression(ctx);
    }

    @Override
    public T visitAndExpression(CPP14Parser.AndExpressionContext ctx) {
        return super.visitAndExpression(ctx);
    }

    @Override
    public T visitEqualityExpression(CPP14Parser.EqualityExpressionContext ctx) {
        return super.visitEqualityExpression(ctx);
    }

    @Override
    public T visitRelationalExpression(CPP14Parser.RelationalExpressionContext ctx) {
        return super.visitRelationalExpression(ctx);
    }

    @Override
    public T visitShiftExpression(CPP14Parser.ShiftExpressionContext ctx) {
        return super.visitShiftExpression(ctx);
    }

    @Override
    public T visitAdditiveExpression(CPP14Parser.AdditiveExpressionContext ctx) {
        MemoryVariable initialValue = (MemoryVariable) this.visitMultiplicativeExpression(ctx.multiplicativeExpression(0));
        if(initialValue == null){
            return null;
        }
        for(int i = 1; i < ctx.multiplicativeExpression().size(); i++) {
            MemoryVariable otherValue = (MemoryVariable) this.visitMultiplicativeExpression(ctx.multiplicativeExpression(i));
            if(otherValue == null){
                return null;
            }
            if(ctx.getChild(2 * i - 1).getText().equals("+")) {
                //TODO: Overflow
            }else if(ctx.getChild(2 * i - 1).getText().equals("-")){
                //TODO: overflow
            }
        }
        return super.visitAdditiveExpression(ctx);
    }

    @Override
    public T visitMultiplicativeExpression(CPP14Parser.MultiplicativeExpressionContext ctx) {
        MemoryVariable initialValue = (MemoryVariable) this.visitPointerMemberExpression(ctx.pointerMemberExpression(0));
        if(initialValue == null){
            return null;
        }
        for(int i = 1; i < ctx.pointerMemberExpression().size(); i++){
            MemoryVariable otherValue = (MemoryVariable) this.visitPointerMemberExpression(ctx.pointerMemberExpression(i));
            if(otherValue == null){
                return null;
            }
            if(ctx.getChild(2 * i - 1).getText().equals("*")) {
                MemoryVariable product = MemoryVariable.Multiply(initialValue, otherValue);
                MemoryVariable division = MemoryVariable.Divide(product, initialValue);
                if(!Arrays.equals(division.getArray(), otherValue.getArray())){
                    this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getStartIndex(), SemanticError.ErrorType.OVERFLOW, "Este producto es demasiado grande para su tipo de dato."));
                    return null;
                }
                initialValue = product;
            }else if(ctx.getChild(2 * i - 1).getText().equals("/")){
                if(initialValue.getRepresentation() == MemoryVariable.NumberType.INTEGER && otherValue.getRepresentation() == MemoryVariable.NumberType.INTEGER){
                    this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getStartIndex(), SemanticError.ErrorType.CASTING, "Esta división es entera, revisa no perder decimales."));
                }
                MemoryVariable division = MemoryVariable.Divide(initialValue, otherValue);
                initialValue = division;
            }else if(ctx.getChild(2 * i - 1).getText().equals("%")){
                if(initialValue.getRepresentation() != MemoryVariable.NumberType.INTEGER || otherValue.getRepresentation() != MemoryVariable.NumberType.INTEGER){
                    this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getStartIndex(), SemanticError.ErrorType.CASTING, "La operación módulo debe realizarse entre dos números enteros."));
                }
            }
        }
        return (T) initialValue;
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
            if(ctx.theTypeId().getText().equals("short int")){
                ((MemoryVariable)(element)).setMemorySize(MemoryVariable.ByteSize.BITS_16);
                ((MemoryVariable)(element)).setRepresentation(MemoryVariable.NumberType.INTEGER);
            }else if(ctx.theTypeId().getText().equals("unsigned short int")){
                ((MemoryVariable)(element)).setMemorySize(MemoryVariable.ByteSize.BITS_16);
                ((MemoryVariable)(element)).setRepresentation(MemoryVariable.NumberType.INTEGER);
            }else if(ctx.theTypeId().getText().equals("unsigned int")){
                ((MemoryVariable)(element)).setMemorySize(MemoryVariable.ByteSize.BITS_32);
                ((MemoryVariable)(element)).setRepresentation(MemoryVariable.NumberType.INTEGER);
            }else if(ctx.theTypeId().getText().equals("int")){
                ((MemoryVariable)(element)).setMemorySize(MemoryVariable.ByteSize.BITS_32);
                ((MemoryVariable)(element)).setRepresentation(MemoryVariable.NumberType.INTEGER);
            }else if(ctx.theTypeId().getText().equals("long int")){
                ((MemoryVariable)(element)).setMemorySize(MemoryVariable.ByteSize.BITS_32);
                ((MemoryVariable)(element)).setRepresentation(MemoryVariable.NumberType.INTEGER);
            }else if(ctx.theTypeId().getText().equals("unsigned long")){
                ((MemoryVariable)(element)).setMemorySize(MemoryVariable.ByteSize.BITS_32);
                ((MemoryVariable)(element)).setRepresentation(MemoryVariable.NumberType.INTEGER);
            }else if(ctx.theTypeId().getText().equals("long long")){
                ((MemoryVariable)(element)).setMemorySize(MemoryVariable.ByteSize.BITS_64);
                ((MemoryVariable)(element)).setRepresentation(MemoryVariable.NumberType.INTEGER);
            }else if(ctx.theTypeId().getText().equals("unsigned long long")){
                ((MemoryVariable)(element)).setMemorySize(MemoryVariable.ByteSize.BITS_64);
                ((MemoryVariable)(element)).setRepresentation(MemoryVariable.NumberType.INTEGER);
            }else if(ctx.theTypeId().getText().equals("float")){
                ((MemoryVariable)(element)).setMemorySize(MemoryVariable.ByteSize.BITS_32);
                ((MemoryVariable)(element)).setRepresentation(MemoryVariable.NumberType.FLOATING);
            }else if(ctx.theTypeId().getText().equals("double")){
                ((MemoryVariable)(element)).setMemorySize(MemoryVariable.ByteSize.BITS_64);
                ((MemoryVariable)(element)).setRepresentation(MemoryVariable.NumberType.FLOATING);
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
                ((MemoryVariable)(element)).AddOne();
                return element;
            }else if(ctx.MinusMinus() != null){
                T element = this.visitUnaryExpression(ctx.unaryExpression());
                ((MemoryVariable)(element)).RestOne();
                return element;
            }else if(ctx.Sizeof() != null){
                T element = this.visitUnaryExpression(ctx.unaryExpression());
                ((MemoryVariable)(element)).setMemorySize(MemoryVariable.ByteSize.BITS_64);
                return element;
            }else if(ctx.unaryOperator() != null){
                //TODO: Operadores unitarios
                if(ctx.unaryOperator().getText().equals("-")){




                }
                throw new UnsupportedOperationException("Expresión Unitaria");
            }else{
                throw new UnsupportedOperationException("Expresión Unitaria");
            }
        }else if(ctx.Sizeof() != null){
            //MemoryVariable element = new MemoryVariable(MemoryVariable.ByteSize.BITS_64, MemoryVariable.NumberType.INTEGER, 0L);
            //return ((T)(new MemoryVariable(MemoryVariable.ByteSize.BITS_64, MemoryVariable.NumberType.INTEGER, 0L)));
        }else if(ctx.Alignof() != null){
            //MemoryVariable element = new MemoryVariable(MemoryVariable.ByteSize.BITS_64, MemoryVariable.NumberType.INTEGER, 0L);
            //return ((T)(new MemoryVariable(MemoryVariable.ByteSize.BITS_64, MemoryVariable.NumberType.INTEGER, 0L)));
        }else if(ctx.newExpression() != null) {
            return this.visitNewExpression(ctx.newExpression());
        }else if(ctx.deleteExpression() != null) {
            return this.visitDeleteExpression(ctx.deleteExpression());
        }else{
            throw new UnsupportedOperationException("Expresión Unitaria");
        }
        return null;
    }

    @Override
    public T visitPostfixExpression(CPP14Parser.PostfixExpressionContext ctx) {
        if(ctx.primaryExpression() != null){
            return this.visitPrimaryExpression(ctx.primaryExpression());
        }else if(ctx.PlusPlus() != null){
            T element = this.visitPostfixExpression(ctx.postfixExpression());
            ((MemoryVariable)(element)).AddOne();
            return element;
        }else if(ctx.MinusMinus() != null){
            T element = this.visitPostfixExpression(ctx.postfixExpression());
            ((MemoryVariable)(element)).RestOne();
            return element;
        }else{
            throw new UnsupportedOperationException("Expresión PostFix");
        }
    }

    @Override
    public T visitPrimaryExpression(CPP14Parser.PrimaryExpressionContext ctx) {
        if(ctx.literal() != null){
            return this.visitLiteral(ctx.literal(0));
        }else if(ctx.idExpression() != null){
            throw new UnsupportedOperationException();// TODO: Variables
        }else if(ctx.expression() != null) {
            return this.visitExpression(ctx.expression());
        }else{
            throw new UnsupportedOperationException("Expresión lambda encontrada");
        }
    }

    @Override
    public T visitLiteral(CPP14Parser.LiteralContext ctx) {
        if(ctx.IntegerLiteral() != null){
            TerminalNode node = ctx.IntegerLiteral();
            int base;
            int prefix;
            if(node.getText().startsWith("0b") || node.getText().startsWith("0B")){
                base = 2; prefix = 2;
            }else if(node.getText().startsWith("0x") || node.getText().startsWith("0X")){
                base = 16; prefix = 2;
            }else if(!node.getText().equals("0") && node.getText().startsWith("0")){
                base = 8; prefix = 1;
            }else{
                base = 10; prefix = 0;
            }
            if(node.getText().endsWith("LL") || node.getText().endsWith("ll")){
                try{
                    Long value = Long.parseLong(node.getText().substring(prefix, node.getText().length() - 2), base);
                    return (T) (new MemoryVariable(value));
                }catch(Exception e){
                    this.detectedErrors.add(new SemanticError(node.getSymbol().getLine(), node.getSymbol().getStartIndex(), SemanticError.ErrorType.OVERFLOW, "La constante " + node.getText() + " es demasiado grande para su tipo."));
                    return null;
                }
            }else {
                try {
                    Integer value = Integer.parseInt(node.getText().substring(prefix), base);
                    return (T) (new MemoryVariable(value));
                } catch (Exception e) {
                    this.detectedErrors.add(new SemanticError(node.getSymbol().getLine(), node.getSymbol().getStartIndex(), SemanticError.ErrorType.OVERFLOW, "La constante " + node.getText() + " es demasiado grande para su tipo."));
                    return null;
                }
            }
        }else if(ctx.FloatingLiteral() != null){
            TerminalNode node = ctx.FloatingLiteral();
            if(node.getText().endsWith("f") || node.getText().endsWith("F")){
                try{
                    Float value = Float.parseFloat(node.getText().substring(0, node.getText().length() - 1));
                    return (T) (new MemoryVariable(value));
                }catch(Exception e){
                    this.detectedErrors.add(new SemanticError(node.getSymbol().getLine(), node.getSymbol().getStartIndex(), SemanticError.ErrorType.OVERFLOW, "La constante " + node.getText() + " es demasiado grande para su tipo."));
                    return null;
                }
            }else{
                try{
                    Double value = Double.parseDouble(node.getText());
                    return (T) (new MemoryVariable(value));
                }catch(Exception e){
                    this.detectedErrors.add(new SemanticError(node.getSymbol().getLine(), node.getSymbol().getStartIndex(), SemanticError.ErrorType.OVERFLOW, "La constante " + node.getText() + " es demasiado grande para su tipo."));
                }
            }
        }
        return null;
    }

    @Override
    public T visitIdExpression(CPP14Parser.IdExpressionContext ctx) {
        return super.visitIdExpression(ctx);
    }

}
