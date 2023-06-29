// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class NextFormParameterList extends FormPars {

    private FormParameterList FormParameterList;

    public NextFormParameterList (FormParameterList FormParameterList) {
        this.FormParameterList=FormParameterList;
        if(FormParameterList!=null) FormParameterList.setParent(this);
    }

    public FormParameterList getFormParameterList() {
        return FormParameterList;
    }

    public void setFormParameterList(FormParameterList FormParameterList) {
        this.FormParameterList=FormParameterList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParameterList!=null) FormParameterList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParameterList!=null) FormParameterList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParameterList!=null) FormParameterList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NextFormParameterList(\n");

        if(FormParameterList!=null)
            buffer.append(FormParameterList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NextFormParameterList]");
        return buffer.toString();
    }
}
