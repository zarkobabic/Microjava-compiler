// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class ConstantsBool extends Constants {

    private Boolean Boolean;

    public ConstantsBool (Boolean Boolean) {
        this.Boolean=Boolean;
    }

    public Boolean getBoolean() {
        return Boolean;
    }

    public void setBoolean(Boolean Boolean) {
        this.Boolean=Boolean;
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
        buffer.append("ConstantsBool(\n");

        buffer.append(" "+tab+Boolean);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstantsBool]");
        return buffer.toString();
    }
}
