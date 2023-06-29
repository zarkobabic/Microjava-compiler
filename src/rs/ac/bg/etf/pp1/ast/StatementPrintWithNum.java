// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class StatementPrintWithNum extends Statement {

    private Expr Expr;
    private Integer intConstForPrint;

    public StatementPrintWithNum (Expr Expr, Integer intConstForPrint) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.intConstForPrint=intConstForPrint;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public Integer getIntConstForPrint() {
        return intConstForPrint;
    }

    public void setIntConstForPrint(Integer intConstForPrint) {
        this.intConstForPrint=intConstForPrint;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementPrintWithNum(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+intConstForPrint);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementPrintWithNum]");
        return buffer.toString();
    }
}
