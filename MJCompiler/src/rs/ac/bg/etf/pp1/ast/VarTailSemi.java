// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class VarTailSemi extends VarTail {

    private String varName;
    private ArrayOrNot ArrayOrNot;

    public VarTailSemi (String varName, ArrayOrNot ArrayOrNot) {
        this.varName=varName;
        this.ArrayOrNot=ArrayOrNot;
        if(ArrayOrNot!=null) ArrayOrNot.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public ArrayOrNot getArrayOrNot() {
        return ArrayOrNot;
    }

    public void setArrayOrNot(ArrayOrNot ArrayOrNot) {
        this.ArrayOrNot=ArrayOrNot;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrayOrNot!=null) ArrayOrNot.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrayOrNot!=null) ArrayOrNot.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrayOrNot!=null) ArrayOrNot.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarTailSemi(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(ArrayOrNot!=null)
            buffer.append(ArrayOrNot.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarTailSemi]");
        return buffer.toString();
    }
}
