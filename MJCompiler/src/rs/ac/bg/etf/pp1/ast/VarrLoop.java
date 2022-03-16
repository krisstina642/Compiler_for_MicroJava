// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class VarrLoop extends VarLoop {

    private VarMid VarMid;
    private VarLoop VarLoop;

    public VarrLoop (VarMid VarMid, VarLoop VarLoop) {
        this.VarMid=VarMid;
        if(VarMid!=null) VarMid.setParent(this);
        this.VarLoop=VarLoop;
        if(VarLoop!=null) VarLoop.setParent(this);
    }

    public VarMid getVarMid() {
        return VarMid;
    }

    public void setVarMid(VarMid VarMid) {
        this.VarMid=VarMid;
    }

    public VarLoop getVarLoop() {
        return VarLoop;
    }

    public void setVarLoop(VarLoop VarLoop) {
        this.VarLoop=VarLoop;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarMid!=null) VarMid.accept(visitor);
        if(VarLoop!=null) VarLoop.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarMid!=null) VarMid.traverseTopDown(visitor);
        if(VarLoop!=null) VarLoop.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarMid!=null) VarMid.traverseBottomUp(visitor);
        if(VarLoop!=null) VarLoop.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarrLoop(\n");

        if(VarMid!=null)
            buffer.append(VarMid.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarLoop!=null)
            buffer.append(VarLoop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarrLoop]");
        return buffer.toString();
    }
}
