// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class VarDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private SingleOrMultipleVarDecl SingleOrMultipleVarDecl;

    public VarDecl (Type Type, SingleOrMultipleVarDecl SingleOrMultipleVarDecl) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.SingleOrMultipleVarDecl=SingleOrMultipleVarDecl;
        if(SingleOrMultipleVarDecl!=null) SingleOrMultipleVarDecl.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public SingleOrMultipleVarDecl getSingleOrMultipleVarDecl() {
        return SingleOrMultipleVarDecl;
    }

    public void setSingleOrMultipleVarDecl(SingleOrMultipleVarDecl SingleOrMultipleVarDecl) {
        this.SingleOrMultipleVarDecl=SingleOrMultipleVarDecl;
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
        if(Type!=null) Type.accept(visitor);
        if(SingleOrMultipleVarDecl!=null) SingleOrMultipleVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(SingleOrMultipleVarDecl!=null) SingleOrMultipleVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(SingleOrMultipleVarDecl!=null) SingleOrMultipleVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SingleOrMultipleVarDecl!=null)
            buffer.append(SingleOrMultipleVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDecl]");
        return buffer.toString();
    }
}
