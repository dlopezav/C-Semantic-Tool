package com.cppsemantictool.backend.visitor;

import com.cppsemantictool.backend.gen.CPP14Parser;
import com.cppsemantictool.backend.gen.CPP14ParserBaseVisitor;
import com.cppsemantictool.backend.model.MemoryVariable;
import com.cppsemantictool.backend.model.SemanticError;
import com.cppsemantictool.backend.model.Variable;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CppVisitor <T> extends CPP14ParserBaseVisitor<T> {
    private HashMap<String, Pair<MemoryVariable, MemoryVariable>> variables;
    private List<SemanticError> detectedErrors;

    public CppVisitor(List<Variable> variables) {
        this.variables = new HashMap<>();
        for(Variable v : variables){
            MemoryVariable min = new MemoryVariable(v.getMin());
            MemoryVariable max = new MemoryVariable(v.getMax());
            this.variables.put(v.getName(), new Pair<>(min, max));
        }
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
    public T visitIterationStatement(CPP14Parser.IterationStatementContext ctx) {
        if(ctx.Do() != null) {
            List<CPP14Parser.StatementContext> statements = ctx.statement().compoundStatement().statementSeq().statement();
            for (CPP14Parser.StatementContext statement : statements){
                if(statement.labeledStatement() != null){
                    this.visitLabeledStatement(statement.labeledStatement());
                }else if(statement.declarationStatement() != null){
                    this.visitDeclarationStatement(statement.declarationStatement());
                }else if(statement.expressionStatement() != null){
                    this.visitExpressionStatement(statement.expressionStatement());
                }else if(statement.compoundStatement() != null){
                    this.visitCompoundStatement(statement.compoundStatement());
                }else if(statement.selectionStatement() != null){
                    this.visitSelectionStatement(statement.selectionStatement());
                }else if(statement.iterationStatement() != null){
                    this.visitIterationStatement(statement.iterationStatement());
                }else if(statement.jumpStatement() != null){
                    this.visitJumpStatement(statement.jumpStatement());
                }else if(statement.tryBlock() != null){
                    this.visitTryBlock(statement.tryBlock());
                }
            }
        }else if(ctx.While() != null){
            List<CPP14Parser.StatementContext> statements = ctx.statement().compoundStatement().statementSeq().statement();
            for (CPP14Parser.StatementContext statement : statements){
                if(statement.labeledStatement() != null){
                    this.visitLabeledStatement(statement.labeledStatement());
                }else if(statement.declarationStatement() != null){
                    this.visitDeclarationStatement(statement.declarationStatement());
                }else if(statement.expressionStatement() != null){
                    this.visitExpressionStatement(statement.expressionStatement());
                }else if(statement.compoundStatement() != null){
                    this.visitCompoundStatement(statement.compoundStatement());
                }else if(statement.selectionStatement() != null){
                    this.visitSelectionStatement(statement.selectionStatement());
                }else if(statement.iterationStatement() != null){
                    this.visitIterationStatement(statement.iterationStatement());
                }else if(statement.jumpStatement() != null){
                    this.visitJumpStatement(statement.jumpStatement());
                }else if(statement.tryBlock() != null){
                    this.visitTryBlock(statement.tryBlock());
                }
            }
        }else if(ctx.For() != null){
            List<CPP14Parser.StatementContext> statements = ctx.statement().compoundStatement().statementSeq().statement();
            for (CPP14Parser.StatementContext statement : statements){
                if(statement.labeledStatement() != null){
                    this.visitLabeledStatement(statement.labeledStatement());
                }else if(statement.declarationStatement() != null){
                    this.visitDeclarationStatement(statement.declarationStatement());
                }else if(statement.expressionStatement() != null){
                    this.visitExpressionStatement(statement.expressionStatement());
                }else if(statement.compoundStatement() != null){
                    this.visitCompoundStatement(statement.compoundStatement());
                }else if(statement.selectionStatement() != null){
                    this.visitSelectionStatement(statement.selectionStatement());
                }else if(statement.iterationStatement() != null){
                    this.visitIterationStatement(statement.iterationStatement());
                }else if(statement.jumpStatement() != null){
                    this.visitJumpStatement(statement.jumpStatement());
                }else if(statement.tryBlock() != null){
                    this.visitTryBlock(statement.tryBlock());
                }
            }
        }
        return null;
    }

    @Override
    public T visitSimpleDeclaration(CPP14Parser.SimpleDeclarationContext ctx) {
        String type = null;
        if(ctx.declSpecifierSeq() != null){
            type = ctx.declSpecifierSeq().getText();
            /*if(type.equals("short int") || type.equals("short")){
                //variable = new MemoryVariable();
            }else if(type.equals("int") || type.equals("long") || type.equals("long int")){
                
            }else if(type.equals("long long")){

            }else if(type.equals("float")){

            }else if(type.equals("double")){
                
            }*/
        }
        if(ctx.initDeclaratorList() != null){
            List<CPP14Parser.InitDeclaratorContext> vars = ctx.initDeclaratorList().initDeclarator();
            for(CPP14Parser.InitDeclaratorContext var : vars){
                if(type == null){
                    var.declarator(); // este retornaría el nombre de la variable
                    var.initializer(); // Este retornaría el memoryvariable
                }else{
                    this.variables.put(type, new Pair<>(new MemoryVariable(0), new MemoryVariable(0)));
                }
            }

        } //TODO: Errores de casting en asignaciones y declaraciones tipos diferentes detectados
        return null;
    }

    @Override
    public T visitAssignmentExpression(CPP14Parser.AssignmentExpressionContext ctx) {
        return super.visitAssignmentExpression(ctx);
    }

    @Override
    public T visitConditionalExpression(CPP14Parser.ConditionalExpressionContext ctx) {
        // Expresión Lambda
        return super.visitConditionalExpression(ctx);
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
        //cin, cout;
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
                if(initialValue.Sign()){
                    if(otherValue.GreaterOverflow(otherValue, initialValue)){
                        this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "Esta suma es demasiado grande para su tipo de dato."));
                    }
                    initialValue = MemoryVariable.Add(initialValue, otherValue);
                }else{
                    if(otherValue.LowerOverflow(otherValue, initialValue)){
                        this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "Esta resta es demasiado grande para su tipo de dato."));
                    }
                    initialValue = MemoryVariable.Add(initialValue, otherValue);
                }

                //TODO: Overflow
            }else{
                if(initialValue.Sign()){
                    if(otherValue.GreaterOverflow(otherValue, initialValue)){
                        this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "Esta suma es demasiado grande para su tipo de dato."));
                    }
                    initialValue = MemoryVariable.Add(initialValue, otherValue);
                }else{
                    if(otherValue.LowerOverflow(otherValue, initialValue)){
                        this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "Esta resta es demasiado grande para su tipo de dato."));
                    }
                    initialValue = MemoryVariable.Substract(initialValue, otherValue);
                }
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
                    this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "Este producto es demasiado grande para su tipo de dato."));
                    return null;
                }
                initialValue = product;
            }else if(ctx.getChild(2 * i - 1).getText().equals("/")){
                if(initialValue.getRepresentation() == MemoryVariable.NumberType.INTEGER && otherValue.getRepresentation() == MemoryVariable.NumberType.INTEGER) {
                    this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.CASTING, "Esta división es entera, revisa no perder decimales."));
                }
                initialValue = MemoryVariable.Divide(initialValue, otherValue);
            }else if(ctx.getChild(2 * i - 1).getText().equals("%")){
                if(initialValue.getRepresentation() != MemoryVariable.NumberType.INTEGER || otherValue.getRepresentation() != MemoryVariable.NumberType.INTEGER){
                    this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+ 1 , SemanticError.ErrorType.CASTING, "La operación módulo debe realizarse entre dos números enteros."));
                }
                initialValue = MemoryVariable.Module(initialValue, otherValue);
            }
        }
        return (T) initialValue;
    }

    @Override
    public T visitPointerMemberExpression(CPP14Parser.PointerMemberExpressionContext ctx) {
        return this.visitCastExpression(ctx.castExpression(0));
    }

    @Override
    public T visitCastExpression(CPP14Parser.CastExpressionContext ctx) {
        if(ctx.unaryExpression() != null){
            return this.visitUnaryExpression(ctx.unaryExpression());
        }else if(ctx.theTypeId() != null){
            String type = ctx.theTypeId().getText();
            MemoryVariable value = (MemoryVariable) this.visitCastExpression(ctx.castExpression());
            if(value == null){
                return null;
            } // int a = (int) number;
            if(type.equals("short int") || type.equals("short")){
                value.CastToShort();
            }else if(type.equals("int") || type.equals("long") || type.equals("long int")){
                value.CastToInt();
            }else if(type.equals("long long")){
                value.CastToLong();
            }else if(type.equals("float")){
                value.CastToFloat();
            }else if(type.equals("double")){
                value.CastToDouble();
            }
            return (T) value;
        }
        return null;
    }

    @Override
    public T visitUnaryExpression(CPP14Parser.UnaryExpressionContext ctx) {
        if(ctx.postfixExpression() != null){
            return this.visitPostfixExpression(ctx.postfixExpression());
        }else if(ctx.unaryExpression() != null){
            if(ctx.PlusPlus() != null){
                MemoryVariable element = (MemoryVariable) this.visitPostfixExpression(ctx.postfixExpression());
                if(element == null){
                    return null;
                }
                element.AddOne();
                return (T) element;
            }else if(ctx.MinusMinus() != null){
                MemoryVariable element = (MemoryVariable) this.visitPostfixExpression(ctx.postfixExpression());
                if(element == null){
                    return null;
                }
                element.SubtractOne();
                return (T) element;
            }else if(ctx.Sizeof() != null){
                return (T) new MemoryVariable(1L);
            }else if(ctx.unaryOperator() != null){
                if(ctx.unaryOperator().getText().equals("-")){
                    if(ctx.unaryExpression().postfixExpression().primaryExpression().literal(0).IntegerLiteral() != null){
                        TerminalNode node = ctx.unaryExpression().postfixExpression().primaryExpression().literal(0).IntegerLiteral();
                        int base; int prefix;
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
                                Long value = Long.parseLong("-" + node.getText().substring(prefix, node.getText().length() - 2), base);
                                return (T) (new MemoryVariable(value));
                            }catch(Exception e){
                                this.detectedErrors.add(new SemanticError(node.getSymbol().getLine(), node.getSymbol().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "La constante -" + node.getText() + " es demasiado grande para su tipo."));
                                return null;
                            }
                        }else {
                            try {
                                Integer value = Integer.parseInt("-" + node.getText().substring(prefix), base);
                                return (T) (new MemoryVariable(value));
                            } catch (Exception e) {
                                this.detectedErrors.add(new SemanticError(node.getSymbol().getLine(), node.getSymbol().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "La constante -" + node.getText() + " es demasiado grande para su tipo."));
                                return null;
                            }
                        }
                    }else if(ctx.unaryExpression().postfixExpression().primaryExpression().literal(0) != null){
                        TerminalNode node = ctx.unaryExpression().postfixExpression().primaryExpression().literal(0).FloatingLiteral();
                        if(node.getText().endsWith("f") || node.getText().endsWith("F")){
                            try{
                                Float value = Float.parseFloat("-" + node.getText().substring(0, node.getText().length() - 1));
                                return (T) (new MemoryVariable(value));
                            }catch(Exception e){
                                this.detectedErrors.add(new SemanticError(node.getSymbol().getLine(), node.getSymbol().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "La constante -" + node.getText() + " es demasiado grande para su tipo."));
                                return null;
                            }
                        }else{
                            try{
                                Double value = Double.parseDouble("-" + node.getText());
                                return (T) (new MemoryVariable(value));
                            }catch(Exception e){
                                this.detectedErrors.add(new SemanticError(node.getSymbol().getLine(), node.getSymbol().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "La constante -" + node.getText() + " es demasiado grande para su tipo."));
                            }
                        }
                    }
                    return null;
                }/*else if(ctx.unaryOperator().getText().equals("!")){
                }*/
            }else{
                throw new UnsupportedOperationException("visitUnaryExpression");
            }
        }else if(ctx.Sizeof() != null){
            return (T) new MemoryVariable(1L);
        }else if(ctx.Alignof() != null){
            return (T) new MemoryVariable(1L);
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
            MemoryVariable element = (MemoryVariable) this.visitPostfixExpression(ctx.postfixExpression());
            if(element == null){
                return null;
            }
            element.AddOne();
            return (T) element;
        }else if(ctx.MinusMinus() != null){
            MemoryVariable element = (MemoryVariable) this.visitPostfixExpression(ctx.postfixExpression());
            if(element == null){
                return null;
            }
            element.SubtractOne();
            return (T) element;
        }else{
            throw new UnsupportedOperationException("visitPostfixExpression");
        }
    }

    @Override
    public T visitPrimaryExpression(CPP14Parser.PrimaryExpressionContext ctx) {
        if(ctx.literal().size() > 0){
            return this.visitLiteral(ctx.literal(0));
        }else if(ctx.idExpression() != null){
            return this.visitIdExpression(ctx.idExpression());
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
                    this.detectedErrors.add(new SemanticError(node.getSymbol().getLine(), node.getSymbol().getCharPositionInLine() + 1 , SemanticError.ErrorType.OVERFLOW, "La constante " + node.getText() + " es demasiado grande para su tipo."));
                    return null;
                }
            }else {
                try {
                    Integer value = Integer.parseInt(node.getText().substring(prefix), base);
                    return (T) (new MemoryVariable(value));
                } catch (Exception e) {
                    this.detectedErrors.add(new SemanticError(node.getSymbol().getLine(), node.getSymbol().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "La constante " + node.getText() + " es demasiado grande para su tipo."));
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
                    this.detectedErrors.add(new SemanticError(node.getSymbol().getLine(), node.getSymbol().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "La constante " + node.getText() + " es demasiado grande para su tipo."));
                    return null;
                }
            }else{
                try{
                    Double value = Double.parseDouble(node.getText());
                    return (T) (new MemoryVariable(value));
                }catch(Exception e){
                    this.detectedErrors.add(new SemanticError(node.getSymbol().getLine(), node.getSymbol().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "La constante " + node.getText() + " es demasiado grande para su tipo."));
                }
            }
        }
        return null;
    }

    @Override
    public T visitIdExpression(CPP14Parser.IdExpressionContext ctx) {
        if(ctx.unqualifiedId() != null){
            return this.visitUnqualifiedId(ctx.unqualifiedId());
        }else if(ctx.qualifiedId() != null){
            return this.visitQualifiedId(ctx.qualifiedId());
        }
        return null;
    }

    @Override
    public T visitUnqualifiedId(CPP14Parser.UnqualifiedIdContext ctx) {
        if(ctx.Identifier() != null){
            String key = ctx.Identifier().getText();
            if(this.variables.containsKey(key)){
                return (T) this.variables.get(key).b;
            }else{
                return null;
            }
        }
        return null;
    }
}
