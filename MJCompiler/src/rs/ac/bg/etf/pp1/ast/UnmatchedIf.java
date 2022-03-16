// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class UnmatchedIf extends Unmatched {

    private IfCatchCondError IfCatchCondError;
    private Statement Statement;

    public UnmatchedIf (IfCatchCondError IfCatchCondError, Statement Statement) {
        this.IfCatchCondError=IfCatchCondError;
        if(IfCatchCondError!=null) IfCatchCondError.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public IfCatchCondError getIfCatchCondError() {
        return IfCatchCondError;
    }

    public void setIfCatchCondError(IfCatchCondError IfCatchCondError) {
        this.IfCatchCondError=IfCatchCondError;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfCatchCondError!=null) IfCatchCondError.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfCatchCondError!=null) IfCatchCondError.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfCatchCondError!=null) IfCatchCondError.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("UnmatchedIf(\n");

        if(IfCatchCondError!=null)
            buffer.append(IfCatchCondError.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [UnmatchedIf]");
        return buffer.toString();
    }
}
