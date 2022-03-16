// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class VarClassSimpleDeclaration extends VarClassDecl {

    private Type Type;
    private VarClassTail VarClassTail;

    public VarClassSimpleDeclaration (Type Type, VarClassTail VarClassTail) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarClassTail=VarClassTail;
        if(VarClassTail!=null) VarClassTail.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarClassTail getVarClassTail() {
        return VarClassTail;
    }

    public void setVarClassTail(VarClassTail VarClassTail) {
        this.VarClassTail=VarClassTail;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarClassTail!=null) VarClassTail.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarClassTail!=null) VarClassTail.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarClassTail!=null) VarClassTail.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarClassSimpleDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarClassTail!=null)
            buffer.append(VarClassTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarClassSimpleDeclaration]");
        return buffer.toString();
    }
}
