// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclNoMethod extends ClassDecl {

    private ClassName ClassName;
    private VarClassDeclListNo VarClassDeclListNo;

    public ClassDeclNoMethod (ClassName ClassName, VarClassDeclListNo VarClassDeclListNo) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.VarClassDeclListNo=VarClassDeclListNo;
        if(VarClassDeclListNo!=null) VarClassDeclListNo.setParent(this);
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
    }

    public VarClassDeclListNo getVarClassDeclListNo() {
        return VarClassDeclListNo;
    }

    public void setVarClassDeclListNo(VarClassDeclListNo VarClassDeclListNo) {
        this.VarClassDeclListNo=VarClassDeclListNo;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassName!=null) ClassName.accept(visitor);
        if(VarClassDeclListNo!=null) VarClassDeclListNo.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(VarClassDeclListNo!=null) VarClassDeclListNo.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(VarClassDeclListNo!=null) VarClassDeclListNo.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclNoMethod(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarClassDeclListNo!=null)
            buffer.append(VarClassDeclListNo.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclNoMethod]");
        return buffer.toString();
    }
}
