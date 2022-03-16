// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class VarClasssDeclNoListt extends VarClassDeclListNo {

    private VarClassDecl VarClassDecl;
    private VarClassDeclList VarClassDeclList;

    public VarClasssDeclNoListt (VarClassDecl VarClassDecl, VarClassDeclList VarClassDeclList) {
        this.VarClassDecl=VarClassDecl;
        if(VarClassDecl!=null) VarClassDecl.setParent(this);
        this.VarClassDeclList=VarClassDeclList;
        if(VarClassDeclList!=null) VarClassDeclList.setParent(this);
    }

    public VarClassDecl getVarClassDecl() {
        return VarClassDecl;
    }

    public void setVarClassDecl(VarClassDecl VarClassDecl) {
        this.VarClassDecl=VarClassDecl;
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
        if(VarClassDecl!=null) VarClassDecl.accept(visitor);
        if(VarClassDeclList!=null) VarClassDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarClassDecl!=null) VarClassDecl.traverseTopDown(visitor);
        if(VarClassDeclList!=null) VarClassDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarClassDecl!=null) VarClassDecl.traverseBottomUp(visitor);
        if(VarClassDeclList!=null) VarClassDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarClasssDeclNoListt(\n");

        if(VarClassDecl!=null)
            buffer.append(VarClassDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarClassDeclList!=null)
            buffer.append(VarClassDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarClasssDeclNoListt]");
        return buffer.toString();
    }
}
