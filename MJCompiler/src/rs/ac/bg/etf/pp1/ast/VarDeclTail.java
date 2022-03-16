// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class VarDeclTail extends VarLoop {

    private VarTail VarTail;

    public VarDeclTail (VarTail VarTail) {
        this.VarTail=VarTail;
        if(VarTail!=null) VarTail.setParent(this);
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
        if(VarTail!=null) VarTail.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarTail!=null) VarTail.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarTail!=null) VarTail.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclTail(\n");

        if(VarTail!=null)
            buffer.append(VarTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclTail]");
        return buffer.toString();
    }
}
