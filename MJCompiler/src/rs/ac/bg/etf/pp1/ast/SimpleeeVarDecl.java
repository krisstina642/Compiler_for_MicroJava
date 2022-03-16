// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class SimpleeeVarDecl extends VarDecl {

    private Type Type;
    private VarTail VarTail;

    public SimpleeeVarDecl (Type Type, VarTail VarTail) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarTail=VarTail;
        if(VarTail!=null) VarTail.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarTail getVarTail() {
        return VarTail;
    }

    public void setVarTail(VarTail VarTail) {
        this.VarTail=VarTail;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarTail!=null) VarTail.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarTail!=null) VarTail.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarTail!=null) VarTail.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SimpleeeVarDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarTail!=null)
            buffer.append(VarTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SimpleeeVarDecl]");
        return buffer.toString();
    }
}
