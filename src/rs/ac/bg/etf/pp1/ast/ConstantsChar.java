// generated with ast extension for cup
// version 0.8
// 27/5/2023 17:32:48


package rs.ac.bg.etf.pp1.ast;

public class ConstantsChar extends Constants {

    private Character Character;

    public ConstantsChar (Character Character) {
        this.Character=Character;
    }

    public Character getCharacter() {
        return Character;
    }

    public void setCharacter(Character Character) {
        this.Character=Character;
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
        buffer.append("ConstantsChar(\n");

        buffer.append(" "+tab+Character);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstantsChar]");
        return buffer.toString();
    }
}
