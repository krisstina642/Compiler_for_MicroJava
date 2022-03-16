// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class DeclarationList extends DeclList {

    private DeclList DeclList;
    private DeclD DeclD;

    public DeclarationList (DeclList DeclList, DeclD DeclD) {
        this.DeclList=DeclList;
        if(DeclList!=null) DeclList.setParent(this);
        this.DeclD=DeclD;
        if(DeclD!=null) DeclD.setParent(this);
    }

    public DeclList getDeclList() {
        return DeclList;
    }

    public void setDeclList(DeclList DeclList) {
        this.DeclList=DeclList;
    }

    public DeclD getDeclD() {
        return DeclD;
    }

    public void setDeclD(DeclD DeclD) {
        this.DeclD=DeclD;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DeclList!=null) DeclList.accept(visitor);
        if(DeclD!=null) DeclD.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DeclList!=null) DeclList.traverseTopDown(visitor);
        if(DeclD!=null) DeclD.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DeclList!=null) DeclList.traverseBottomUp(visitor);
        if(DeclD!=null) DeclD.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationList(\n");

        if(DeclList!=null)
            buffer.append(DeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DeclD!=null)
            buffer.append(DeclD.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationList]");
        return buffer.toString();
    }
}
