// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class DesignatorOpts extends DesignatorOptList {

    private DesOptBeginArray DesOptBeginArray;
    private Expr Expr;

    public DesignatorOpts (DesOptBeginArray DesOptBeginArray, Expr Expr) {
        this.DesOptBeginArray=DesOptBeginArray;
        if(DesOptBeginArray!=null) DesOptBeginArray.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesOptBeginArray getDesOptBeginArray() {
        return DesOptBeginArray;
    }

    public void setDesOptBeginArray(DesOptBeginArray DesOptBeginArray) {
        this.DesOptBeginArray=DesOptBeginArray;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesOptBeginArray!=null) DesOptBeginArray.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesOptBeginArray!=null) DesOptBeginArray.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesOptBeginArray!=null) DesOptBeginArray.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorOpts(\n");

        if(DesOptBeginArray!=null)
            buffer.append(DesOptBeginArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorOpts]");
        return buffer.toString();
    }
}
