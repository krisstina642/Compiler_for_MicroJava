// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclarationTail extends ConstLoop {

    private ConstTail ConstTail;

    public ConstDeclarationTail (ConstTail ConstTail) {
        this.ConstTail=ConstTail;
        if(ConstTail!=null) ConstTail.setParent(this);
    }

    public ConstTail getConstTail() {
        return ConstTail;
    }

    public void setConstTail(ConstTail ConstTail) {
        this.ConstTail=ConstTail;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstTail!=null) ConstTail.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstTail!=null) ConstTail.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstTail!=null) ConstTail.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclarationTail(\n");

        if(ConstTail!=null)
            buffer.append(ConstTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclarationTail]");
        return buffer.toString();
    }
}
