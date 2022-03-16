// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class VarClStart extends VarClassStart {

    private VarClassDeclList VarClassDeclList;

    public VarClStart (VarClassDeclList VarClassDeclList) {
        this.VarClassDeclList=VarClassDeclList;
        if(VarClassDeclList!=null) VarClassDeclList.setParent(this);
    }

    public VarClassDeclList getVarClassDeclList() {
        return VarClassDeclList;
    }

    public void setVarClassDeclList(VarClassDeclList VarClassDeclList) {
        this.VarClassDeclList=VarClassDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarClassDeclList!=null) VarClassDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarClassDeclList!=null) VarClassDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarClassDeclList!=null) VarClassDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarClStart(\n");

        if(VarClassDeclList!=null)
            buffer.append(VarClassDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarClStart]");
        return buffer.toString();
    }
}
