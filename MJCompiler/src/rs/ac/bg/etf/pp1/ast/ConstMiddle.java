// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class ConstMiddle extends ConstMid {

    private String varName;
    private ConstD ConstD;

    public ConstMiddle (String varName, ConstD ConstD) {
        this.varName=varName;
        this.ConstD=ConstD;
        if(ConstD!=null) ConstD.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public ConstD getConstD() {
        return ConstD;
    }

    public void setConstD(ConstD ConstD) {
        this.ConstD=ConstD;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstD!=null) ConstD.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstD!=null) ConstD.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstD!=null) ConstD.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstMiddle(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(ConstD!=null)
            buffer.append(ConstD.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstMiddle]");
        return buffer.toString();
    }
}
