// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class RelopIsE extends Relop {

    private String e;

    public RelopIsE (String e) {
        this.e=e;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e=e;
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
        buffer.append("RelopIsE(\n");

        buffer.append(" "+tab+e);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RelopIsE]");
        return buffer.toString();
    }
}