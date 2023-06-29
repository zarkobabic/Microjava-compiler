// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class IfElseStatement extends IfStatement {

    private IfStatementFlag IfStatementFlag;
    private Condition Condition;
    private IfRightParenthesis IfRightParenthesis;
    private Statement Statement;
    private IfElsePart IfElsePart;
    private Statement Statement1;

    public IfElseStatement (IfStatementFlag IfStatementFlag, Condition Condition, IfRightParenthesis IfRightParenthesis, Statement Statement, IfElsePart IfElsePart, Statement Statement1) {
        this.IfStatementFlag=IfStatementFlag;
        if(IfStatementFlag!=null) IfStatementFlag.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.IfRightParenthesis=IfRightParenthesis;
        if(IfRightParenthesis!=null) IfRightParenthesis.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.IfElsePart=IfElsePart;
        if(IfElsePart!=null) IfElsePart.setParent(this);
        this.Statement1=Statement1;
        if(Statement1!=null) Statement1.setParent(this);
    }

    public IfStatementFlag getIfStatementFlag() {
        return IfStatementFlag;
    }

    public void setIfStatementFlag(IfStatementFlag IfStatementFlag) {
        this.IfStatementFlag=IfStatementFlag;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public IfRightParenthesis getIfRightParenthesis() {
        return IfRightParenthesis;
    }

    public void setIfRightParenthesis(IfRightParenthesis IfRightParenthesis) {
        this.IfRightParenthesis=IfRightParenthesis;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public IfElsePart getIfElsePart() {
        return IfElsePart;
    }

    public void setIfElsePart(IfElsePart IfElsePart) {
        this.IfElsePart=IfElsePart;
    }

    public Statement getStatement1() {
        return Statement1;
    }

    public void setStatement1(Statement Statement1) {
        this.Statement1=Statement1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfStatementFlag!=null) IfStatementFlag.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(IfRightParenthesis!=null) IfRightParenthesis.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(IfElsePart!=null) IfElsePart.accept(visitor);
        if(Statement1!=null) Statement1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfStatementFlag!=null) IfStatementFlag.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(IfRightParenthesis!=null) IfRightParenthesis.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(IfElsePart!=null) IfElsePart.traverseTopDown(visitor);
        if(Statement1!=null) Statement1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfStatementFlag!=null) IfStatementFlag.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(IfRightParenthesis!=null) IfRightParenthesis.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(IfElsePart!=null) IfElsePart.traverseBottomUp(visitor);
        if(Statement1!=null) Statement1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfElseStatement(\n");

        if(IfStatementFlag!=null)
            buffer.append(IfStatementFlag.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IfRightParenthesis!=null)
            buffer.append(IfRightParenthesis.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IfElsePart!=null)
            buffer.append(IfElsePart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement1!=null)
            buffer.append(Statement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfElseStatement]");
        return buffer.toString();
    }
}
