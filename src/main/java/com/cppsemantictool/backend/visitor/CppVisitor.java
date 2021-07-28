package com.cppsemantictool.backend.visitor;

import com.cppsemantictool.backend.gen.CPP14Parser;
import com.cppsemantictool.backend.gen.CPP14ParserBaseVisitor;
import com.cppsemantictool.backend.model.MemoryVariable;
import com.cppsemantictool.backend.model.SemanticError;
import com.cppsemantictool.backend.model.Variable;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

public class CppVisitor <T> extends CPP14ParserBaseVisitor<T> {
    private HashMap<String, Pair<MemoryVariable, MemoryVariable>> variables;
    //private HashMap<String, Pair<MemoryVariable, MemoryVariable>> arrays;
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
            try{
                this.visitForInitStatement(ctx.forInitStatement());

                String condition = ctx.condition().getText();
                String ands[] = condition.trim().split("&&");
                for(String and : ands){
                    String ors[] = and.trim().split("\\|\\|");
                }



                String expression = ctx.expression().getText();


                //TODO: evaluar si el ciclo tiene fin
            }catch(Exception e){ }

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
    public T visitForInitStatement(CPP14Parser.ForInitStatementContext ctx) {
        return super.visitForInitStatement(ctx);
    }

    @Override
    public T visitStatement(CPP14Parser.StatementContext ctx) {
        if(ctx.declarationStatement()!=null) {
            return this.visitDeclarationStatement(ctx.declarationStatement());
        }
        return null;
    }

    @Override
    public T visitDeclarationStatement(CPP14Parser.DeclarationStatementContext ctx) {
        return this.visitBlockDeclaration(ctx.blockDeclaration());
    }

    @Override
    public T visitBlockDeclaration(CPP14Parser.BlockDeclarationContext ctx) {
        return this.visitSimpleDeclaration(ctx.simpleDeclaration());
    }

    @Override
    public T visitSimpleDeclaration(CPP14Parser.SimpleDeclarationContext ctx) {
        List<String> declarations = new ArrayList<String>();
        String type = null;
        if(ctx.declSpecifierSeq() != null){
            type = ctx.declSpecifierSeq().getText();
            if(ctx.initDeclaratorList() != null){
                List<CPP14Parser.InitDeclaratorContext> vars = ctx.initDeclaratorList().initDeclarator();
                for(CPP14Parser.InitDeclaratorContext var : vars){
                    MemoryVariable value = (MemoryVariable) this.visitInitializer(var.initializer());
                    if(value != null) {
                        if (type.equals("int") || type.equals("long") || type.equals("longint")) {
                            if (value.getRepresentation() != MemoryVariable.NumberType.INTEGER) {
                                this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.CASTING, "La variable (" + var.declarator().getText() + ") puede perder precisión. No coincide el tipo de dato con el dato."));
                            } else if (value.getMemorySize() != MemoryVariable.ByteSize.BITS_32) {
                                this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.CASTING, "La variable (" + var.declarator().getText() + ") es más pequeña o más grande que su tipo de dato."));
                            }

                        } else if (type.equals("short")) {
                            if (value.getRepresentation() != MemoryVariable.NumberType.INTEGER) {
                                this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.CASTING, "La variable (" + var.declarator().getText() + ") puede perder precisión. No coincide el tipo de dato con el dato."));
                            } else if (value.getMemorySize() != MemoryVariable.ByteSize.BITS_16) {
                                this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.CASTING, "La variable (" + var.declarator().getText() + ") es más pequeña que el dato asignado."));

                            }
                        } else if (type.equals("float")) {
                            if (value.getRepresentation() != MemoryVariable.NumberType.FLOATING) {
                                this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.CASTING, "La variable (" + var.declarator().getText() + ") puede perder precisión. No coincide el tipo de dato con el dato."));
                            } else if (value.getMemorySize() != MemoryVariable.ByteSize.BITS_32) {
                                this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.CASTING, "La variable (" + var.declarator().getText() + ") es más pequeña o más grande que su tipo de dato."));
                            }
                        } else if (type.equals("double")) {
                            if (value.getRepresentation() != MemoryVariable.NumberType.FLOATING) {
                                this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.CASTING, "La variable (" + var.declarator().getText() + ") puede perder precisión. No coincide el tipo de dato con el dato."));
                            } else if (value.getMemorySize() != MemoryVariable.ByteSize.BITS_64) {
                                this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.CASTING, "La variable (" + var.declarator().getText() + ") es más pequeña o más grande que su tipo de dato."));
                            }

                        }else if (type.equals("longlong") ) {
                            if (value.getRepresentation() != MemoryVariable.NumberType.INTEGER) {
                                this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.CASTING, "La variable (" + var.declarator().getText() + ") puede perder precisión. No coincide el tipo de dato con el dato."));
                            } else if (value.getMemorySize() != MemoryVariable.ByteSize.BITS_64) {
                                this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.CASTING, "La variable (" + var.declarator().getText() + ") es más pequeña o más grande que su tipo de dato."));
                            }

                        }
                    }
                    // este retornaría el nombre de la variable
                    declarations.add(var.declarator().getText());
                     // Este retornaría el memoryvariable
                    this.variables.put(type, new Pair<>(new MemoryVariable(0), new MemoryVariable(0)));
                }

            }
        }
        //TODO: Errores de casting en asignaciones y declaraciones tipos diferentes detectados
        return (T) declarations;
    }



    @Override
    public T visitDeclarator(CPP14Parser.DeclaratorContext ctx) {
        String a= ctx.pointerDeclarator().getText();
        return this.visitPointerDeclarator(ctx.pointerDeclarator());
    }

    @Override
    public T visitPointerDeclarator(CPP14Parser.PointerDeclaratorContext ctx) {
        String a= ctx.noPointerDeclarator().getText();
        return this.visitNoPointerDeclarator(ctx.noPointerDeclarator());
    }

    @Override
    public T visitNoPointerDeclarator(CPP14Parser.NoPointerDeclaratorContext ctx) {
        if(ctx.declaratorid()!=null) {
            return this.visitDeclaratorid(ctx.declaratorid());
        }
        return null;
    }

    @Override
    public T visitDeclaratorid(CPP14Parser.DeclaratoridContext ctx) {
        return this.visitIdExpression(ctx.idExpression());
    }

    @Override
    public T visitInitializer(CPP14Parser.InitializerContext ctx) {
        if(ctx != null && ctx.braceOrEqualInitializer()!=null) {
            return this.visitBraceOrEqualInitializer(ctx.braceOrEqualInitializer());
        }
        return null;
    }

    @Override
    public T visitBraceOrEqualInitializer(CPP14Parser.BraceOrEqualInitializerContext ctx) {
        return this.visitInitializerClause(ctx.initializerClause());
    }

    @Override
    public T visitInitializerClause(CPP14Parser.InitializerClauseContext ctx) {
        return this.visitAssignmentExpression(ctx.assignmentExpression());
    }

    @Override
    public T visitAssignmentExpression(CPP14Parser.AssignmentExpressionContext ctx) {
        return this.visitConditionalExpression(ctx.conditionalExpression());
    }

    @Override
    public T visitConditionalExpression(CPP14Parser.ConditionalExpressionContext ctx) {
        return this.visitLogicalOrExpression(ctx.logicalOrExpression()); // Expresión ternaria
    }

    @Override
    public T visitLogicalOrExpression(CPP14Parser.LogicalOrExpressionContext ctx) {
        return this.visitLogicalAndExpression(ctx.logicalAndExpression(0));
    }

    @Override
    public T visitLogicalAndExpression(CPP14Parser.LogicalAndExpressionContext ctx) {
        return this.visitInclusiveOrExpression(ctx.inclusiveOrExpression(0));
    }

    @Override
    public T visitInclusiveOrExpression(CPP14Parser.InclusiveOrExpressionContext ctx) {
        return this.visitExclusiveOrExpression(ctx.exclusiveOrExpression(0));
    }

    @Override
    public T visitExclusiveOrExpression(CPP14Parser.ExclusiveOrExpressionContext ctx) {
        return this.visitAndExpression(ctx.andExpression(0));
    }

    @Override
    public T visitAndExpression(CPP14Parser.AndExpressionContext ctx) {
        return this.visitEqualityExpression(ctx.equalityExpression(0));
    }

    @Override
    public T visitEqualityExpression(CPP14Parser.EqualityExpressionContext ctx) {
        return this.visitRelationalExpression(ctx.relationalExpression(0));
    }

    @Override
    public T visitRelationalExpression(CPP14Parser.RelationalExpressionContext ctx) {
        return this.visitShiftExpression(ctx.shiftExpression(0));
    }

    @Override
    public T visitShiftExpression(CPP14Parser.ShiftExpressionContext ctx) {
        return this.visitAdditiveExpression(ctx.additiveExpression().get(0));
        //cin, cout;
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
                    if(MemoryVariable.GreaterOverflow(initialValue, otherValue)){
                        this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "Esta suma es demasiado grande para su tipo de dato."));
                        return null;
                    }
                }else{
                    if(MemoryVariable.LowerOverflow(initialValue, otherValue)){
                        this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "Esta suma es demasiado grande para su tipo de dato."));
                        return null;
                    }
                }
                initialValue = MemoryVariable.Add(initialValue, otherValue);
            }else if(ctx.getChild(2 * i - 1).getText().equals("-")){
                if(initialValue.Sign()){
                    if(MemoryVariable.GreaterOverflow(initialValue, MemoryVariable.AdditiveInverse(otherValue))){
                        this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "Esta resta es demasiado grande para su tipo de dato."));
                        return null;
                    }
                }else{
                    if(MemoryVariable.LowerOverflow(initialValue, MemoryVariable.AdditiveInverse(otherValue))){
                        this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "Esta resta es demasiado grande para su tipo de dato."));
                        return null;
                    }
                }
                initialValue = MemoryVariable.Substract(initialValue, otherValue);
            }
        }
        return (T) initialValue;
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
            switch (ctx.getChild(2 * i - 1).getText()) {
                case "*":
                    MemoryVariable product = MemoryVariable.Multiply(initialValue, otherValue);
                    MemoryVariable division = MemoryVariable.Divide(product, initialValue);
                    if (!Arrays.equals(division.getArray(), otherValue.getArray())) {
                        this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.OVERFLOW, "Este producto es demasiado grande para su tipo de dato."));
                        return null;
                    }
                    initialValue = product;
                    break;
                case "/":
                    if (initialValue.getRepresentation() == MemoryVariable.NumberType.INTEGER && otherValue.getRepresentation() == MemoryVariable.NumberType.INTEGER) {
                        this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.CASTING, "Esta división es entera, revisa no perder decimales."));
                    }
                    initialValue = MemoryVariable.Divide(initialValue, otherValue);
                    break;
                case "%":
                    if (initialValue.getRepresentation() != MemoryVariable.NumberType.INTEGER || otherValue.getRepresentation() != MemoryVariable.NumberType.INTEGER) {
                        this.detectedErrors.add(new SemanticError(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1, SemanticError.ErrorType.CASTING, "La operación módulo debe realizarse entre dos números enteros."));
                        return null;
                    }
                    initialValue = MemoryVariable.Module(initialValue, otherValue);
                    break;
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
