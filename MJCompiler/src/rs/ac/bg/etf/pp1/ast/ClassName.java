// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class ClassName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String className;
    private ClassExtendOpt ClassExtendOpt;

    public ClassName (String className, ClassExtendOpt ClassExtendOpt) {
        this.className=className;
        this.ClassExtendOpt=ClassExtendOpt;
        if(ClassExtendOpt!=null) ClassExtendOpt.setParent(this);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className=className;
    }

    public ClassExtendOpt getClassExtendOpt() {
        return ClassExtendOpt;
    }

    public void setClassExtendOpt(ClassExtendOpt ClassExtendOpt) {
        this.ClassExtendOpt=ClassExtendOpt;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassExtendOpt!=null) ClassExtendOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassExtendOpt!=null) ClassExtendOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassExtendOpt!=null) ClassExtendOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassName(\n");

        buffer.append(" "+tab+className);
        buffer.append("\n");

        if(ClassExtendOpt!=null)
            buffer.append(ClassExtendOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassName]");
        return buffer.toString();
    }
}
