// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class MapVariable implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String globalOrLocalVar;

    public MapVariable (String globalOrLocalVar) {
        this.globalOrLocalVar=globalOrLocalVar;
    }

    public String getGlobalOrLocalVar() {
        return globalOrLocalVar;
    }

    public void setGlobalOrLocalVar(String globalOrLocalVar) {
        this.globalOrLocalVar=globalOrLocalVar;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("MapVariable(\n");

        buffer.append(" "+tab+globalOrLocalVar);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MapVariable]");
        return buffer.toString();
    }
}
