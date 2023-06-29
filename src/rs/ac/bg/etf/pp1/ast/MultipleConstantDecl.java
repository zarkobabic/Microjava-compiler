// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class MultipleConstantDecl extends SingleOrMultipleConstantDecl {

    private SingleOrMultipleConstantDecl SingleOrMultipleConstantDecl;
    private String ConstName;
    private Constants Constants;

    public MultipleConstantDecl (SingleOrMultipleConstantDecl SingleOrMultipleConstantDecl, String ConstName, Constants Constants) {
        this.SingleOrMultipleConstantDecl=SingleOrMultipleConstantDecl;
        if(SingleOrMultipleConstantDecl!=null) SingleOrMultipleConstantDecl.setParent(this);
        this.ConstName=ConstName;
        this.Constants=Constants;
        if(Constants!=null) Constants.setParent(this);
    }

    public SingleOrMultipleConstantDecl getSingleOrMultipleConstantDecl() {
        return SingleOrMultipleConstantDecl;
    }

    public void setSingleOrMultipleConstantDecl(SingleOrMultipleConstantDecl SingleOrMultipleConstantDecl) {
        this.SingleOrMultipleConstantDecl=SingleOrMultipleConstantDecl;
    }

    public String getConstName() {
        return ConstName;
    }

    public void setConstName(String ConstName) {
        this.ConstName=ConstName;
    }

    public Constants getConstants() {
        return Constants;
    }

    public void setConstants(Constants Constants) {
        this.Constants=Constants;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SingleOrMultipleConstantDecl!=null) SingleOrMultipleConstantDecl.accept(visitor);
        if(Constants!=null) Constants.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleOrMultipleConstantDecl!=null) SingleOrMultipleConstantDecl.traverseTopDown(visitor);
        if(Constants!=null) Constants.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleOrMultipleConstantDecl!=null) SingleOrMultipleConstantDecl.traverseBottomUp(visitor);
        if(Constants!=null) Constants.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleConstantDecl(\n");

        if(SingleOrMultipleConstantDecl!=null)
            buffer.append(SingleOrMultipleConstantDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+ConstName);
        buffer.append("\n");

        if(Constants!=null)
            buffer.append(Constants.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleConstantDecl]");
        return buffer.toString();
    }
}
