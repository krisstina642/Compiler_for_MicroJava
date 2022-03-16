// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class VarClassDeclaration extends VarClassDecl {

    private Type Type;
    private VarClassMid VarClassMid;
    private VarClassLoop VarClassLoop;

    public VarClassDeclaration (Type Type, VarClassMid VarClassMid, VarClassLoop VarClassLoop) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarClassMid=VarClassMid;
        if(VarClassMid!=null) VarClassMid.setParent(this);
        this.VarClassLoop=VarClassLoop;
        if(VarClassLoop!=null) VarClassLoop.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarClassMid getVarClassMid() {
        return VarClassMid;
    }

    public void setVarClassMid(VarClassMid VarClassMid) {
        this.VarClassMid=VarClassMid;
    }

    public VarClassLoop getVarClassLoop() {
        return VarClassLoop;
    }

    public void setVarClassLoop(VarClassLoop VarClassLoop) {
        this.VarClassLoop=VarClassLoop;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarClassMid!=null) VarClassMid.accept(visitor);
        if(VarClassLoop!=null) VarClassLoop.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarClassMid!=null) VarClassMid.traverseTopDown(visitor);
        if(VarClassLoop!=null) VarClassLoop.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarClassMid!=null) VarClassMid.traverseBottomUp(visitor);
        if(VarClassLoop!=null) VarClassLoop.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarClassDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarClassMid!=null)
            buffer.append(VarClassMid.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarClassLoop!=null)
            buffer.append(VarClassLoop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarClassDeclaration]");
        return buffer.toString();
    }
}
