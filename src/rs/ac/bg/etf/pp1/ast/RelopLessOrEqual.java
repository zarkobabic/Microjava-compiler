// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class RelopLessOrEqual extends Relop {

    public RelopLessOrEqual () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RelopLessOrEqual(\n");

        buffer.append(tab);
        buffer.append(") [RelopLessOrEqual]");
        return buffer.toString();
    }
}
