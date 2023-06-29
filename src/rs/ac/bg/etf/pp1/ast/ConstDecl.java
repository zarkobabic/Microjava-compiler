// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private SingleOrMultipleConstantDecl SingleOrMultipleConstantDecl;

    public ConstDecl (Type Type, SingleOrMultipleConstantDecl SingleOrMultipleConstantDecl) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.SingleOrMultipleConstantDecl=SingleOrMultipleConstantDecl;
        if(SingleOrMultipleConstantDecl!=null) SingleOrMultipleConstantDecl.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public SingleOrMultipleConstantDecl getSingleOrMultipleConstantDecl() {
        return SingleOrMultipleConstantDecl;
    }

    public void setSingleOrMultipleConstantDecl(SingleOrMultipleConstantDecl SingleOrMultipleConstantDecl) {
        this.SingleOrMultipleConstantDecl=SingleOrMultipleConstantDecl;
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
        if(SingleOrMultipleConstantDecl!=null) SingleOrMultipleConstantDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(SingleOrMultipleConstantDecl!=null) SingleOrMultipleConstantDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(SingleOrMultipleConstantDecl!=null) SingleOrMultipleConstantDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SingleOrMultipleConstantDecl!=null)
            buffer.append(SingleOrMultipleConstantDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
