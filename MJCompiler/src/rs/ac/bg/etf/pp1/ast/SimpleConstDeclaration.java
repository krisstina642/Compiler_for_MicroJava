// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class SimpleConstDeclaration extends ConstDecl {

    private Type Type;
    private ConstTail ConstTail;

    public SimpleConstDeclaration (Type Type, ConstTail ConstTail) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConstTail=ConstTail;
        if(ConstTail!=null) ConstTail.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
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
        if(Type!=null) Type.accept(visitor);
        if(ConstTail!=null) ConstTail.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstTail!=null) ConstTail.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstTail!=null) ConstTail.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SimpleConstDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstTail!=null)
            buffer.append(ConstTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SimpleConstDeclaration]");
        return buffer.toString();
    }
}
