// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class SingleVarDecl extends SingleOrMultipleVarDecl {

    private TypesOfVar TypesOfVar;

    public SingleVarDecl (TypesOfVar TypesOfVar) {
        this.TypesOfVar=TypesOfVar;
        if(TypesOfVar!=null) TypesOfVar.setParent(this);
    }

    public TypesOfVar getTypesOfVar() {
        return TypesOfVar;
    }

    public void setTypesOfVar(TypesOfVar TypesOfVar) {
        this.TypesOfVar=TypesOfVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(TypesOfVar!=null) TypesOfVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TypesOfVar!=null) TypesOfVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TypesOfVar!=null) TypesOfVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleVarDecl(\n");

        if(TypesOfVar!=null)
            buffer.append(TypesOfVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleVarDecl]");
        return buffer.toString();
    }
}
