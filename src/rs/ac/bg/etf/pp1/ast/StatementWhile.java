// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class StatementWhile extends Statement {

    private WhileInstructionFlag WhileInstructionFlag;
    private Condition Condition;
    private WhileRightParenthesis WhileRightParenthesis;
    private Statement Statement;

    public StatementWhile (WhileInstructionFlag WhileInstructionFlag, Condition Condition, WhileRightParenthesis WhileRightParenthesis, Statement Statement) {
        this.WhileInstructionFlag=WhileInstructionFlag;
        if(WhileInstructionFlag!=null) WhileInstructionFlag.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.WhileRightParenthesis=WhileRightParenthesis;
        if(WhileRightParenthesis!=null) WhileRightParenthesis.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public WhileInstructionFlag getWhileInstructionFlag() {
        return WhileInstructionFlag;
    }

    public void setWhileInstructionFlag(WhileInstructionFlag WhileInstructionFlag) {
        this.WhileInstructionFlag=WhileInstructionFlag;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public WhileRightParenthesis getWhileRightParenthesis() {
        return WhileRightParenthesis;
    }

    public void setWhileRightParenthesis(WhileRightParenthesis WhileRightParenthesis) {
        this.WhileRightParenthesis=WhileRightParenthesis;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(WhileInstructionFlag!=null) WhileInstructionFlag.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(WhileRightParenthesis!=null) WhileRightParenthesis.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(WhileInstructionFlag!=null) WhileInstructionFlag.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(WhileRightParenthesis!=null) WhileRightParenthesis.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(WhileInstructionFlag!=null) WhileInstructionFlag.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(WhileRightParenthesis!=null) WhileRightParenthesis.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementWhile(\n");

        if(WhileInstructionFlag!=null)
            buffer.append(WhileInstructionFlag.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(WhileRightParenthesis!=null)
            buffer.append(WhileRightParenthesis.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementWhile]");
        return buffer.toString();
    }
}
