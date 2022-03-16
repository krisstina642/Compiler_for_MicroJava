// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class ConstMidLoopTail extends ConstDecl {

    private Type Type;
    private ConstMid ConstMid;
    private ConstLoop ConstLoop;

    public ConstMidLoopTail (Type Type, ConstMid ConstMid, ConstLoop ConstLoop) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConstMid=ConstMid;
        if(ConstMid!=null) ConstMid.setParent(this);
        this.ConstLoop=ConstLoop;
        if(ConstLoop!=null) ConstLoop.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ConstMid getConstMid() {
        return ConstMid;
    }

    public void setConstMid(ConstMid ConstMid) {
        this.ConstMid=ConstMid;
    }

    public ConstLoop getConstLoop() {
        return ConstLoop;
    }

    public void setConstLoop(ConstLoop ConstLoop) {
        this.ConstLoop=ConstLoop;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstMid!=null) ConstMid.accept(visitor);
        if(ConstLoop!=null) ConstLoop.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstMid!=null) ConstMid.traverseTopDown(visitor);
        if(ConstLoop!=null) ConstLoop.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstMid!=null) ConstMid.traverseBottomUp(visitor);
        if(ConstLoop!=null) ConstLoop.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstMidLoopTail(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstMid!=null)
            buffer.append(ConstMid.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstLoop!=null)
            buffer.append(ConstLoop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstMidLoopTail]");
        return buffer.toString();
    }
}
