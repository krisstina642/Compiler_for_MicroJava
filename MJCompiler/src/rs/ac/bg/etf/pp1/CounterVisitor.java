package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.FormalParamDeclNoBracket;
import rs.ac.bg.etf.pp1.ast.SingleFormalParamDecl;
import rs.ac.bg.etf.pp1.ast.VarMiddle;
import rs.ac.bg.etf.pp1.ast.VarTailSemi;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;
	
	public int getCount(){
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor{
	
		public void visit(SingleFormalParamDecl formParamDecl){
			count++;
		}
		public void visit(FormalParamDeclNoBracket formParamDecl){
			count++;
		}
		
	}
	
	public static class VarCounter extends CounterVisitor{
		
		public void visit(VarTailSemi varDecl){
			count++;
		}
		
		public void visit(VarMiddle varDecl){
			count++;
		}
	}
}
