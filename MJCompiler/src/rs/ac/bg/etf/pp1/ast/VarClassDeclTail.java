// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class VarClassDeclTail extends VarClassLoop {

    private VarClassTail VarClassTail;

    public VarClassDeclTail (VarClassTail VarClassTail) {
        this.VarClassTail=VarClassTail;
        if(VarClassTail!=null) VarClassTail.setParent(this);
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
        if(VarClassTail!=null) VarClassTail.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarClassTail!=null) VarClassTail.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarClassTail!=null) VarClassTail.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarClassDeclTail(\n");

        if(VarClassTail!=null)
            buffer.append(VarClassTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarClassDeclTail]");
        return buffer.toString();
    }
}
