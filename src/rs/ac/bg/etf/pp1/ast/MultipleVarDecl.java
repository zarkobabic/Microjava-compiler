// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class MultipleVarDecl extends SingleOrMultipleVarDecl {

    private SingleOrMultipleVarDecl SingleOrMultipleVarDecl;
    private TypesOfVar TypesOfVar;

    public MultipleVarDecl (SingleOrMultipleVarDecl SingleOrMultipleVarDecl, TypesOfVar TypesOfVar) {
        this.SingleOrMultipleVarDecl=SingleOrMultipleVarDecl;
        if(SingleOrMultipleVarDecl!=null) SingleOrMultipleVarDecl.setParent(this);
        this.TypesOfVar=TypesOfVar;
        if(TypesOfVar!=null) TypesOfVar.setParent(this);
    }

    public SingleOrMultipleVarDecl getSingleOrMultipleVarDecl() {
        return SingleOrMultipleVarDecl;
    }

    public void setSingleOrMultipleVarDecl(SingleOrMultipleVarDecl SingleOrMultipleVarDecl) {
        this.SingleOrMultipleVarDecl=SingleOrMultipleVarDecl;
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
        if(SingleOrMultipleVarDecl!=null) SingleOrMultipleVarDecl.accept(visitor);
        if(TypesOfVar!=null) TypesOfVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleOrMultipleVarDecl!=null) SingleOrMultipleVarDecl.traverseTopDown(visitor);
        if(TypesOfVar!=null) TypesOfVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleOrMultipleVarDecl!=null) SingleOrMultipleVarDecl.traverseBottomUp(visitor);
        if(TypesOfVar!=null) TypesOfVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleVarDecl(\n");

        if(SingleOrMultipleVarDecl!=null)
            buffer.append(SingleOrMultipleVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TypesOfVar!=null)
            buffer.append(TypesOfVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleVarDecl]");
        return buffer.toString();
    }
}
