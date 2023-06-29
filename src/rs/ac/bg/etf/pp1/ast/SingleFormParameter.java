// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class SingleFormParameter extends FormParameterList {

    private Type Type;
    private TypesOfVar TypesOfVar;

    public SingleFormParameter (Type Type, TypesOfVar TypesOfVar) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.TypesOfVar=TypesOfVar;
        if(TypesOfVar!=null) TypesOfVar.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
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
        if(Type!=null) Type.accept(visitor);
        if(TypesOfVar!=null) TypesOfVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(TypesOfVar!=null) TypesOfVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(TypesOfVar!=null) TypesOfVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleFormParameter(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TypesOfVar!=null)
            buffer.append(TypesOfVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleFormParameter]");
        return buffer.toString();
    }
}
