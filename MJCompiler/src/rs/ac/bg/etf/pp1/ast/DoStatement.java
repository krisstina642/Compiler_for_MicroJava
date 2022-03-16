// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class DoStatement extends Matched {

    private DoBegin DoBegin;
    private StatementList StatementList;
    private Condition Condition;

    public DoStatement (DoBegin DoBegin, StatementList StatementList, Condition Condition) {
        this.DoBegin=DoBegin;
        if(DoBegin!=null) DoBegin.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
    }

    public DoBegin getDoBegin() {
        return DoBegin;
    }

    public void setDoBegin(DoBegin DoBegin) {
        this.DoBegin=DoBegin;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoBegin!=null) DoBegin.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoBegin!=null) DoBegin.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoBegin!=null) DoBegin.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoStatement(\n");

        if(DoBegin!=null)
            buffer.append(DoBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoStatement]");
        return buffer.toString();
    }
}
