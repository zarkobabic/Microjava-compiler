// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class NextVarDeclInJoinedList extends VarConstDeclList {

    private VarConstDeclList VarConstDeclList;
    private VarDecl VarDecl;

    public NextVarDeclInJoinedList (VarConstDeclList VarConstDeclList, VarDecl VarDecl) {
        this.VarConstDeclList=VarConstDeclList;
        if(VarConstDeclList!=null) VarConstDeclList.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public VarConstDeclList getVarConstDeclList() {
        return VarConstDeclList;
    }

    public void setVarConstDeclList(VarConstDeclList VarConstDeclList) {
        this.VarConstDeclList=VarConstDeclList;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarConstDeclList!=null) VarConstDeclList.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarConstDeclList!=null) VarConstDeclList.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarConstDeclList!=null) VarConstDeclList.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NextVarDeclInJoinedList(\n");

        if(VarConstDeclList!=null)
            buffer.append(VarConstDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NextVarDeclInJoinedList]");
        return buffer.toString();
    }
}
