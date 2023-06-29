// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class NextConstDeclInJoinedList extends VarConstDeclList {

    private VarConstDeclList VarConstDeclList;
    private ConstDecl ConstDecl;

    public NextConstDeclInJoinedList (VarConstDeclList VarConstDeclList, ConstDecl ConstDecl) {
        this.VarConstDeclList=VarConstDeclList;
        if(VarConstDeclList!=null) VarConstDeclList.setParent(this);
        this.ConstDecl=ConstDecl;
        if(ConstDecl!=null) ConstDecl.setParent(this);
    }

    public VarConstDeclList getVarConstDeclList() {
        return VarConstDeclList;
    }

    public void setVarConstDeclList(VarConstDeclList VarConstDeclList) {
        this.VarConstDeclList=VarConstDeclList;
    }

    public ConstDecl getConstDecl() {
        return ConstDecl;
    }

    public void setConstDecl(ConstDecl ConstDecl) {
        this.ConstDecl=ConstDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarConstDeclList!=null) VarConstDeclList.accept(visitor);
        if(ConstDecl!=null) ConstDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarConstDeclList!=null) VarConstDeclList.traverseTopDown(visitor);
        if(ConstDecl!=null) ConstDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarConstDeclList!=null) VarConstDeclList.traverseBottomUp(visitor);
        if(ConstDecl!=null) ConstDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NextConstDeclInJoinedList(\n");

        if(VarConstDeclList!=null)
            buffer.append(VarConstDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDecl!=null)
            buffer.append(ConstDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NextConstDeclInJoinedList]");
        return buffer.toString();
    }
}
