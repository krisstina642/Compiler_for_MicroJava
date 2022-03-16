// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class Constant extends ConstD {

    private Integer t;

    public Constant (Integer t) {
        this.t=t;
    }

    public Integer getT() {
        return t;
    }

    public void setT(Integer t) {
        this.t=t;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Constant(\n");

        buffer.append(" "+tab+t);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Constant]");
        return buffer.toString();
    }
}
