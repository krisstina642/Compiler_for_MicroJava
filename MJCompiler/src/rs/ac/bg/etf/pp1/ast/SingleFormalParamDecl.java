// generated with ast extension for cup
// version 0.8
// 25/7/2021 5:8:23


package rs.ac.bg.etf.pp1.ast;

public class SingleFormalParamDecl extends FormalParamList {

    private Type Type;
    private String iden;
    private ArrayOrNot ArrayOrNot;

    public SingleFormalParamDecl (Type Type, String iden, ArrayOrNot ArrayOrNot) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.iden=iden;
        this.ArrayOrNot=ArrayOrNot;
        if(ArrayOrNot!=null) ArrayOrNot.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getIden() {
        return iden;
    }

    public void setIden(String iden) {
        this.iden=iden;
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
        if(Type!=null) Type.accept(visitor);
        if(ArrayOrNot!=null) ArrayOrNot.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ArrayOrNot!=null) ArrayOrNot.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ArrayOrNot!=null) ArrayOrNot.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleFormalParamDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+iden);
        buffer.append("\n");

        if(ArrayOrNot!=null)
            buffer.append(ArrayOrNot.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleFormalParamDecl]");
        return buffer.toString();
    }
}
