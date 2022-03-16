// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class UnmatchedIfElse extends Unmatched {

    private IfCatchCondError IfCatchCondError;
    private IfEnd IfEnd;
    private ElseBegin ElseBegin;

    public UnmatchedIfElse (IfCatchCondError IfCatchCondError, IfEnd IfEnd, ElseBegin ElseBegin) {
        this.IfCatchCondError=IfCatchCondError;
        if(IfCatchCondError!=null) IfCatchCondError.setParent(this);
        this.IfEnd=IfEnd;
        if(IfEnd!=null) IfEnd.setParent(this);
        this.ElseBegin=ElseBegin;
        if(ElseBegin!=null) ElseBegin.setParent(this);
    }

    public IfCatchCondError getIfCatchCondError() {
        return IfCatchCondError;
    }

    public void setIfCatchCondError(IfCatchCondError IfCatchCondError) {
        this.IfCatchCondError=IfCatchCondError;
    }

    public IfEnd getIfEnd() {
        return IfEnd;
    }

    public void setIfEnd(IfEnd IfEnd) {
        this.IfEnd=IfEnd;
    }

    public ElseBegin getElseBegin() {
        return ElseBegin;
    }

    public void setElseBegin(ElseBegin ElseBegin) {
        this.ElseBegin=ElseBegin;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfCatchCondError!=null) IfCatchCondError.accept(visitor);
        if(IfEnd!=null) IfEnd.accept(visitor);
        if(ElseBegin!=null) ElseBegin.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfCatchCondError!=null) IfCatchCondError.traverseTopDown(visitor);
        if(IfEnd!=null) IfEnd.traverseTopDown(visitor);
        if(ElseBegin!=null) ElseBegin.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfCatchCondError!=null) IfCatchCondError.traverseBottomUp(visitor);
        if(IfEnd!=null) IfEnd.traverseBottomUp(visitor);
        if(ElseBegin!=null) ElseBegin.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("UnmatchedIfElse(\n");

        if(IfCatchCondError!=null)
            buffer.append(IfCatchCondError.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IfEnd!=null)
            buffer.append(IfEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ElseBegin!=null)
            buffer.append(ElseBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [UnmatchedIfElse]");
        return buffer.toString();
    }
}
