package rs.ac.bg.etf.pp1;

import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.AddExpr;
import rs.ac.bg.etf.pp1.ast.AddopMinus;
import rs.ac.bg.etf.pp1.ast.AddopPlus;
import rs.ac.bg.etf.pp1.ast.BreakStatement;
import rs.ac.bg.etf.pp1.ast.CondFactRelop;
import rs.ac.bg.etf.pp1.ast.CondTerms;
import rs.ac.bg.etf.pp1.ast.Conditions;
import rs.ac.bg.etf.pp1.ast.ContinueStatement;
import rs.ac.bg.etf.pp1.ast.DesName;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.DesignatorOpts;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementActPars;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementAsign;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementDec;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementInc;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementNoActPars;
import rs.ac.bg.etf.pp1.ast.DoBeg;
import rs.ac.bg.etf.pp1.ast.DoStatement;
import rs.ac.bg.etf.pp1.ast.ElseBegUnmatched;
import rs.ac.bg.etf.pp1.ast.FactorConstBool;
import rs.ac.bg.etf.pp1.ast.FactorConstChar;
import rs.ac.bg.etf.pp1.ast.FactorConstNum;
import rs.ac.bg.etf.pp1.ast.FactorNewWithExpr;
import rs.ac.bg.etf.pp1.ast.FuncCallNoPars;
import rs.ac.bg.etf.pp1.ast.FuncCallWithPars;
import rs.ac.bg.etf.pp1.ast.IfCondError;
import rs.ac.bg.etf.pp1.ast.IfEnd;
import rs.ac.bg.etf.pp1.ast.MethodDeclar;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.MethodTypeVoid;
import rs.ac.bg.etf.pp1.ast.MullopDiv;
import rs.ac.bg.etf.pp1.ast.MullopMul;
import rs.ac.bg.etf.pp1.ast.PrintStatement;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.ReadStatement;
import rs.ac.bg.etf.pp1.ast.ReturnExpr;
import rs.ac.bg.etf.pp1.ast.ReturnStatement;
import rs.ac.bg.etf.pp1.ast.Scalarr;
import rs.ac.bg.etf.pp1.ast.SimpleCondFact;
import rs.ac.bg.etf.pp1.ast.SimpleCondTerm;
import rs.ac.bg.etf.pp1.ast.SimpleCondition;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.TermExpr;
import rs.ac.bg.etf.pp1.ast.TermExprMinus;
import rs.ac.bg.etf.pp1.ast.TermsMulop;
import rs.ac.bg.etf.pp1.ast.UnmatchedIf;
import rs.ac.bg.etf.pp1.ast.UnmatchedIfElse;
import rs.ac.bg.etf.pp1.ast.Var;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;

	public int getMainPc(){
		return mainPc;
	}
	
	private static Stack<Integer> ifFalse = new Stack<Integer>(); // ako je if false treba da preskoci naredbe i skoci na else
	
	private static Stack<Integer> endOfElse = new Stack<Integer>(); //na kraju svakog else izraza ukoliko je tacan treba da skoci na kraj

	private static Stack<Integer> endOfAndCond = new Stack<Integer>(); 	// Stek adresa za AND condition
	
	private static Stack<Integer> endOfOrCond = new Stack<Integer>(); // Stek adresa za or condition

	private static Stack<Integer> doStack = new Stack<Integer>();
	
	private static Stack<Integer> doBreak = new Stack<Integer>();

	Struct boolType=rs.ac.bg.etf.pp1.SemanticAnalyzer.boolType;
	Struct intArray=rs.ac.bg.etf.pp1.SemanticAnalyzer.intArray;
	
	private static int methodAddAddress = 0;
	private static int methodScalArr = 0;
	private static int methodArrArr = 0;
	private Obj DesignaatorAssignArr;

	public void visit(PrintStatement printStmt){
		if(printStmt.getExpr().obj.getType() == Tab.intType || printStmt.getExpr().obj.getType() == boolType){
			Code.loadConst(5);
			Code.put(Code.print);

		}else{
			Code.loadConst(1);
			Code.put(Code.bprint);
		}

	}

	public void visit(FactorConstNum cnst){
		Obj con = Tab.insert(Obj.Con, "$", cnst.obj.getType());
		con.setLevel(0);
		con.setAdr(cnst.getN());

		Code.load(con);
	}

	public void visit(FactorConstChar cnst){
		Obj con = Tab.insert(Obj.Con, "$", cnst.obj.getType());
		con.setLevel(0);
		con.setAdr(cnst.getT());

		Code.load(con);
	}

	public void visit(FactorConstBool cnst){
		Obj con = Tab.insert(Obj.Con, "$", cnst.obj.getType());
		con.setLevel(0);
		con.setAdr(cnst.getBoolConst().obj.getAdr());

		Code.load(con);
	}

	public void visit(FactorNewWithExpr expr){
		Code.put(Code.newarray);
		Code.put(expr.obj.getType().getElemType() == Tab.charType ? 0: 1);

	}
	public void visit(TermExprMinus expr){
		expr.obj=expr.getTerm().obj;
		Code.loadConst(-1); 
		Code.put(Code.mul);
	}
	
	public void visit(TermExpr expr){
		expr.obj=expr.getTerm().obj;	
	}
	

	public void visit(CondTerms a){ //and

		if(a.getParent().getClass()!=CondTerms.class) {
			if (a.getParent().getParent().getClass()!=Conditions.class && a.getParent().getClass()!=SimpleCondition.class) {
				Code.loadConst(0);

				Code.putFalseJump(Code.eq, 0); // ako je 1 ceo or ce biti tacan, skace na kraj
				int orTrue = Code.pc - 2;
				Code.loadConst(0);  
				Code.putJump(0);
				int andEnd = Code.pc - 2;

				Code.fixup(orTrue); 
				while (!endOfOrCond.isEmpty()) {
					Code.fixup(endOfOrCond.pop());	
				}
				Code.loadConst(1);  
				Code.fixup(andEnd);

			}
			else if (a.getParent().getParent().getClass()==Conditions.class && a.getParent().getClass()!=SimpleCondition.class) {
				Code.loadConst(0);
				Code.putFalseJump(Code.eq, 0);
				endOfOrCond.push(Code.pc - 2);	

			}
		}
	}

	public void visit(SimpleCondTerm a){ //and

	if(a.getParent().getClass()==CondTerms.class) return;
		if (a.getParent().getParent().getClass()!=Conditions.class && a.getParent().getClass()!=SimpleCondition.class) {
			Code.loadConst(0);

			Code.putFalseJump(Code.eq, 0); // ako je 1 ceo or ce biti tacan, skace na kraj
			int orTrue = Code.pc - 2;
			Code.loadConst(0); 
			Code.putJump(0);
			int andEnd = Code.pc - 2;

			Code.fixup(orTrue); 
			while (!endOfOrCond.isEmpty()) {
				Code.fixup(endOfOrCond.pop());	
			}
			Code.loadConst(1);  
			Code.fixup(andEnd);

		}
		else if (a.getParent().getParent().getClass()==Conditions.class && a.getParent().getClass()!=SimpleCondition.class) {
			Code.loadConst(0);
			Code.putFalseJump(Code.eq, 0);
			endOfOrCond.push(Code.pc - 2);	
		}

	}


	public void visit(SimpleCondFact a){


		if (a.getParent().getParent().getClass()!=CondTerms.class && a.getParent().getClass()!=SimpleCondTerm.class) {

			Code.loadConst(0);

			Code.putFalseJump(Code.ne, 0); // ako je nula da skoci na kraj
			int andFalse = Code.pc - 2;
			Code.loadConst(1);  // true
			Code.putJump(0);
			int andEnd = Code.pc - 2;

			Code.fixup(andFalse); 
			while (!endOfAndCond.isEmpty()) {
				Code.fixup(endOfAndCond.pop());	
			}
			Code.loadConst(0);  // false
			Code.fixup(andEnd);

		}
		else if (a.getParent().getParent().getClass()==CondTerms.class ) {
			Code.loadConst(0);
			Code.putFalseJump(Code.ne, 0);
			endOfAndCond.push(Code.pc - 2);	
		}
	}



	public void visit(CondFactRelop a){

		int relFalse;  // skok na false ako uslov nije ispunjen
		int relEnd;      // skok na kraj ako je ispunjen i propali smo dalje


		switch (a.getRelop().obj.getName())
		{
		case "==":
			Code.putFalseJump(Code.eq, 0); // ako nisu jednaki skoci na false
			relFalse = Code.pc - 2;
			Code.loadConst(1);  // true
			Code.putJump(0);
			relEnd = Code.pc - 2;

			Code.fixup(relFalse); 
			Code.loadConst(0);  // false
			Code.fixup(relEnd);
			break;
		case "!=":
			Code.putFalseJump(Code.ne, 0);
			relFalse = Code.pc - 2;
			Code.loadConst(1);  // true
			Code.putJump(0);
			relEnd = Code.pc - 2;

			Code.fixup(relFalse);
			Code.loadConst(0);  // false
			Code.fixup(relEnd);
			break;
		case ">":
			Code.putFalseJump(Code.gt, 0);
			relFalse = Code.pc - 2;
			Code.loadConst(1);  // true
			Code.putJump(0);
			relEnd = Code.pc - 2;

			Code.fixup(relFalse);
			Code.loadConst(0);  // false
			Code.fixup(relEnd);
			break;
		case ">=":
			Code.putFalseJump(Code.ge, 0);
			relFalse = Code.pc - 2;
			Code.loadConst(1);  // true
			Code.putJump(0);
			relEnd = Code.pc - 2;

			Code.fixup(relFalse);
			Code.loadConst(0);  // false
			Code.fixup(relEnd);
			break;
		case "<":
			Code.putFalseJump(Code.lt, 0);
			relFalse = Code.pc - 2;
			Code.loadConst(1);  // true
			Code.putJump(0);
			relEnd = Code.pc - 2;

			Code.fixup(relFalse);
			Code.loadConst(0);  // false
			Code.fixup(relEnd);
			break;
		case "<=":
			Code.putFalseJump(Code.le, 0);
			relFalse = Code.pc - 2;
			Code.loadConst(1);  // true
			Code.putJump(0);
			relEnd = Code.pc - 2;

			Code.fixup(relFalse);
			Code.loadConst(0);  // false
			Code.fixup(relEnd);
			break;

		}   

		if (a.getParent().getParent().getClass()!=CondTerms.class && a.getParent().getClass()!=SimpleCondTerm.class) {

			Code.loadConst(0);

			Code.putFalseJump(Code.ne, 0); // ako je nula da skoci na kraj
			int andFalse = Code.pc - 2;
			Code.loadConst(1);  // true
			Code.putJump(0);
			int andEnd = Code.pc - 2;

			Code.fixup(andFalse); 
			while (!endOfAndCond.isEmpty()) {
				Code.fixup(endOfAndCond.pop());	
			}
			Code.loadConst(0);  // false
			Code.fixup(andEnd);

		}
		else if (a.getParent().getParent().getClass()==CondTerms.class ) {

			Code.loadConst(0);
			Code.putFalseJump(Code.ne, 0);
			endOfAndCond.push(Code.pc - 2);	
		}

	}
	
	public void visit(ContinueStatement c) {
		int i=doStack.pop();
		doStack.push(i);
		Code.putJump(i);
		
	}

	public void visit(MethodTypeName methodTypeName){
		//System.out.println(""+methodTypeName.getMetName());

		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();

		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);

		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());

	}

	public void visit(MethodTypeVoid methodTypeName){
		//System.out.println(""+methodTypeName.getMetName());
		if("main".equalsIgnoreCase(methodTypeName.getMetName())){
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();

		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);

		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());

	}

	public void visit(MethodDeclar methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit (DesignatorStatementAsign assign) {
		//System.out.println(Tab.find(assign.getDesignator().obj.getName()).getKind()+ " "+assign.getDesignator().obj.getName()+" "+assign.getDesignator().obj.getType().getKind());
		if (assign.getExpr().obj.getType()==intArray && assign.getExpr().obj==assign.getDesignator().obj) {
			return;
			
		}
		if (assign.getExpr().obj.getType()==intArray && assign.obj.getName().equals(assign.getExpr().obj.getName())) return;
			
		
		Code.store(assign.getDesignator().obj);	 
		
		//Code.put(Code.store);
	}
	public void visit(DesignatorStatementActPars  procCall){
		
		Obj functionObj = procCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if(procCall.getDesignator().obj.getType() != Tab.noType){
			Code.put(Code.pop);
		}
	}
	
	public void visit (DesName d) {
		//System.out.println(""+d.getName()+" "+d.obj.getType().getKind());
		
		if(d.obj!=null && d.obj.getType().getKind()==Struct.Array) {	//System.out.println(d.obj.getName()+ " "+ d.obj.getName());
		Code.load(d.obj);
		}
	}
	
	public void visit(FuncCallWithPars funcCall){
		if (funcCall.getDesignator().obj.getName().equals("len")) {
			Code.put(Code.arraylength);
			return;
		}
		if (funcCall.getDesignator().obj.getName().equals("ord")) {
			return;
		}
		if (funcCall.getDesignator().obj.getName().equals("chr")) {
			return;
		}
		Obj functionObj = funcCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	public void visit (Designator d) {
		SyntaxNode s=d.getParent();

		if (Var.class==s.getClass()) {

			Code.load(d.obj);
		}
		else if (DesignatorStatementAsign.class==s.getClass() && d.obj.getType().getKind()==Struct.Array) {
			DesignaatorAssignArr=d.obj;
		}
		else if (DesignatorStatementInc.class==s.getClass()) {
			
			if (d.getDesignatorOptList().getClass()==DesignatorOpts.class) {
				Code.put(Code.dup2);
			}
			
			Code.load(d.obj);
			Code.loadConst(1);;
			Code.put(Code.add);
			Code.store(d.obj);
		}
		else if (DesignatorStatementDec.class==s.getClass()) {
			
			if (d.getDesignatorOptList().getClass()==DesignatorOpts.class) {
				Code.put(Code.dup2);
			}
			Code.load(d.obj);
			Code.loadConst(1);
			Code.put(Code.sub);
			Code.store(d.obj);
		}
		else {
			
		}

	}

	public void visit (BreakStatement d) {
		Code.loadConst(1);
		Code.loadConst(1);
		Code.putFalseJump(Code.ne, 0);
		doBreak.push(Code.pc-2);
	
	}

	public void visit (ReadStatement r) {
		Obj design=r.getDesignator().obj;

		if (design.getType() == Tab.intType || design.getType() == boolType)
		{
			Code.put(Code.read);
			Code.store(design);
		}
		else if (design.getType() == Tab.charType)
		{
			Code.put(Code.bread);
			Code.store(design);
		}

	}

	public void visit(FuncCallNoPars funcCall){
		Obj functionObj = funcCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	public void visit(DesignatorStatementNoActPars  procCall){
		Obj functionObj = procCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if(procCall.getDesignator().obj.getType() != Tab.noType){
			Code.put(Code.pop);
		}
	}

	public void visit(ReturnExpr returnExpr){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(ReturnStatement returnNoExpr){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	private void printString(String s) {
		for(int i=0;i<s.length(); i++) {
			Code.load(new Obj(Obj.Con,"&&", Tab.charType, s.charAt(i), 0));
			Code.loadConst(1);
			Code.put(Code.bprint);
			
		}
		
	}
	// +1 plus -1 minus
	public void visit(AddExpr addExpr){
		
		if(addExpr.getExpr().obj.getType().getKind()==Struct.Array && addExpr.getTerm().obj.getType().getKind()==Struct.Array ) {
			Code.put(Code.pop);
			Code.put(Code.arraylength);
			
			Code.load(addExpr.getTerm().obj);
			Code.put(Code.arraylength);
	
			Code.putFalseJump(Code.ne, 0);
			int fix=Code.pc-2;
			printString("----- Nisu jednake duzine nizovi -----");
			//Code.call(Code.error("dfsdfsd");)
			Code.put(Code.exit);
			Code.put(Code.return_);
			
			Code.putJump(0);
			int fix2=Code.pc-2;
		
			Code.fixup(fix);
			
			if (addExpr.getAddop().getClass()!=Scalarr.class)  { 
				Code.load(addExpr.getExpr().obj);
				Code.put(Code.arraylength);
				Code.put(Code.newarray);
				Code.put(1);
				Code.store(addExpr.obj);
			}
		
			if (addExpr.getAddop().getClass()!=Scalarr.class) Code.load(addExpr.obj);
			Code.load(addExpr.getExpr().obj);
			Code.load(addExpr.getTerm().obj);
			if (addExpr.getAddop().getClass()==AddopPlus.class) Code.put(Code.const_1);
			else if (addExpr.getAddop().getClass()==AddopMinus.class) Code.put(Code.const_n);
								
			Code.put(Code.call);
			if (addExpr.getAddop().getClass()==Scalarr.class) Code.put2(methodArrArr + 1 - Code.pc);
			else Code.put2(methodAddAddress + 1 - Code.pc);

			Code.fixup(fix2);
						
		}
		else if (addExpr.getExpr().obj.getType().getKind()==Struct.Array && addExpr.getTerm().obj.getType()==Tab.intType) {
			Code.put(Code.pop);
			Code.put(Code.arraylength);
			Code.put(Code.newarray);
			Code.put(1);
			Code.store(addExpr.obj);
			
			Code.load(addExpr.obj);
			Code.load(addExpr.getExpr().obj);
			Code.load(addExpr.getTerm().obj);
				
			Code.put(Code.call);
			Code.put2(methodScalArr + 1 - Code.pc);
			
		}
		
		else if (addExpr.getTerm().obj.getType().getKind()==Struct.Array && addExpr.getExpr().obj.getType()==Tab.intType) {
			Code.put(Code.pop);
			Code.put(Code.pop);
			
			Code.load(addExpr.getTerm().obj);
			Code.put(Code.arraylength);
			Code.put(Code.newarray);
			Code.put(1);
			Code.store(addExpr.obj);
			
			Code.load(addExpr.obj);
			Code.load(addExpr.getTerm().obj);
			Code.load(addExpr.getExpr().obj);
			
			
			Code.put(Code.call);
			Code.put2(methodScalArr + 1 - Code.pc);
			
		}
		else if (addExpr.getAddop().struct.getKind()==1) Code.put(Code.add);
		else if (addExpr.getAddop().struct.getKind()==-1) Code.put(Code.sub);
		else {
		
		}
	}
	
	// 1 *; 2 /; 3% 
	public void visit(TermsMulop addExpr){
		
		///System.out.println(""+addExpr.getTerm().obj.getName()+ "  "+ addExpr.getFactor().obj.getName());
		
		if(addExpr.getFactor().obj.getType().getKind()==Struct.Array && addExpr.getTerm().obj.getType().getKind()==Struct.Array && addExpr.getMulop().getClass()==MullopMul.class) {
			//System.out.println("mmullop");
			Code.put(Code.pop);
			Code.put(Code.arraylength);
			
			Code.load(addExpr.getTerm().obj);
			Code.put(Code.arraylength);
	
			Code.putFalseJump(Code.ne, 0);
			int fix=Code.pc-2;
			printString("----- Nisu jednake duzine nizovi -----");
			//Code.call(Code.error("dfsdfsd");)
			Code.put(Code.exit);
			Code.put(Code.return_);
			
			Code.putJump(0);
			int fix2=Code.pc-2;
		
			Code.fixup(fix);
		
			Code.load(addExpr.getFactor().obj);
			Code.load(addExpr.getTerm().obj);
								
			Code.put(Code.call);
			 Code.put2(methodArrArr + 1 - Code.pc);
			 
			Code.fixup(fix2);
						
		}
		else if (addExpr.getFactor().obj.getType().getKind()==Struct.Array && addExpr.getTerm().obj.getType()==Tab.intType) {
			addExpr.obj=DesignaatorAssignArr;
			Code.put(Code.pop);
			Code.put(Code.arraylength);
			Code.put(Code.newarray);
			Code.put(1);
			Code.store(DesignaatorAssignArr);
			
			Code.load(DesignaatorAssignArr);
			Code.load(addExpr.getFactor().obj);
			Code.load(addExpr.getTerm().obj);
				
			Code.put(Code.call);
			Code.put2(methodScalArr + 1 - Code.pc);
			
		}
		
		else if (addExpr.getTerm().obj.getType().getKind()==Struct.Array && addExpr.getFactor().obj.getType()==Tab.intType) {
			addExpr.obj=DesignaatorAssignArr;
			Code.put(Code.pop);
			Code.put(Code.pop);
			
			Code.load(addExpr.getTerm().obj);
			Code.put(Code.arraylength);
			Code.put(Code.newarray);
			Code.put(1);
			Code.store(DesignaatorAssignArr);
			
			Code.load(DesignaatorAssignArr);
			Code.load(addExpr.getTerm().obj);
			Code.load(addExpr.getFactor().obj);
			
			
			Code.put(Code.call);
			Code.put2(methodScalArr + 1 - Code.pc);
			
		}
		else if (addExpr.getMulop().getClass()==MullopMul.class) Code.put(Code.mul);
		else if (addExpr.getMulop().getClass()==MullopDiv.class) Code.put(Code.div);
		else {	Code.put(Code.rem);} 
	}

	public void visit(IfEnd b) { //nakon izvrsenih funkcija prethodnom ifu postavi adresu za skok ukoliko je if bio 0
		Code.putJump(0);			//ukoliko je if bio tacan u endIf se ugradjuje skok na kraj
		endOfElse.push(Code.pc - 2);

		if (!ifFalse.isEmpty())
		{
			Code.fixup(ifFalse.pop());
		}	
	}


	public void visit(IfCondError ice){  //ako uslov nije tacan treba da skoci na sledeci else

			Code.loadConst(0);
			Code.putFalseJump(Code.ne, 0);
			ifFalse.push(Code.pc - 2);
			
	}

	public void visit(UnmatchedIf i) {
		if (!endOfElse.isEmpty())
		{
			Code.fixup(endOfElse.pop());
		}
		if (i.getParent().getClass()!=ElseBegUnmatched.class && !ifFalse.isEmpty())
		{
			Code.fixup(ifFalse.pop());
		}
	}
	public void visit(UnmatchedIfElse i) {
		if (!endOfElse.isEmpty())
		{
			Code.fixup(endOfElse.pop());
		}
		if (i.getParent().getClass()!=ElseBegUnmatched.class && !ifFalse.isEmpty())
		{
			Code.fixup(ifFalse.pop());
		}
	}

	public void visit(DoBeg db) {
		doStack.push(Code.pc);
	}

	public void visit(DoStatement d) {

		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
		int curPc = Code.pc - 2;
		Code.putJump(doStack.pop());
		Code.fixup(curPc);
		
		if (doBreak.isEmpty()) return;
		Code.fixup(doBreak.pop());

	}
	
	public void visit(ProgName pr) {
		if (SemanticAnalyzer.methodAddArr) {
		Obj newMethodAdd= Tab.insert(Obj.Meth, "AddArrayMethod", Tab.noType);
		newMethodAdd.setAdr(Code.pc);
		methodAddAddress = Code.pc;
				
		Code.put(Code.enter);
		Code.put(4);
		Code.put(6);
			
		 Code.put(Code.load_1);
			Code.put(Code.arraylength);
			Code.put(Code.store);
			Code.put(4);
			Code.loadConst(0);	
			Code.put(Code.store);
			Code.put(5);
			Code.put(Code.load_3);
			Code.loadConst(0);
			Code.putFalseJump(Code.eq, Code.pc + 7);
			Code.loadConst(1);
			Code.putJump(Code.pc+4);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(Code.ne, Code.pc + 19);
			Code.put(Code.load_n);
			Code.put(Code.load);
			Code.put(5);
			Code.put(Code.load_1);
			Code.put(Code.load);
			Code.put(5);
			Code.put(Code.aload);
			Code.put(Code.load_2);
			Code.put(Code.load);
			Code.put(5);
			Code.put(Code.aload);
			Code.put(Code.sub);
			Code.put(Code.astore);
			Code.putJump(Code.pc + 30);
			Code.put(Code.load_3);
			Code.loadConst(1);
			Code.putFalseJump(Code.eq, Code.pc + 7);
			Code.loadConst(1);
			Code.putJump(Code.pc + 4);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(Code.ne, Code.pc + 16);
			Code.put(Code.load_n);
			Code.put(Code.load);
			Code.put(5);
			Code.put(Code.load_1);
			Code.put(Code.load);
			Code.put(5);
			Code.put(Code.aload);
			Code.put(Code.load_2);
			Code.put(Code.load);
			Code.put(5);
			Code.put(Code.aload);
			Code.put(Code.add);
			Code.put(Code.astore);
			Code.put(Code.load);
			Code.put(5);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.put(Code.store);
			Code.put(5);
			Code.put(Code.load);
			Code.put(5);
			Code.put(Code.load);
			Code.put(4);
			Code.putFalseJump(Code.lt, Code.pc + 7);
			Code.loadConst(1);
			Code.putJump(Code.pc + 4);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(Code.ne, Code.pc + 6);
			Code.putJump(Code.pc - 79);
	    	
		Code.put(Code.exit);
		Code.put(Code.return_);  //add sud
		}
		
		if (SemanticAnalyzer.methodScalArr) {
		Obj newMethodScalArr= Tab.insert(Obj.Meth, "AddArrayScalMethod", Tab.noType);
		newMethodScalArr.setAdr(Code.pc);
		methodScalArr = Code.pc;
		
		Code.put(Code.enter); //scalar*array
		Code.put(3);
		Code.put(4);
		
		Code.put(Code.load_1);
		Code.put(Code.arraylength);
		Code.put(Code.store);
		Code.put(3);
		Code.loadConst(0);	
		Code.put(Code.store);
		Code.put(4);
		Code.put(Code.load_n);
		Code.put(Code.load);
		Code.put(4);
		Code.put(Code.load_1);
		Code.put(Code.load);
		Code.put(4);
		Code.put(Code.aload);
		Code.put(Code.load_2);
		Code.put(Code.mul);
		Code.put(Code.astore);
		Code.put(Code.load);
		Code.put(4);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.store);
		Code.put(4);
		Code.put(Code.load);
		Code.put(4);
		Code.put(Code.load_3);
		Code.putFalseJump(Code.lt, Code.pc + 7);
		Code.loadConst(1);
		Code.putJump(Code.pc+4);
		Code.loadConst(0);
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, Code.pc + 6);
		Code.putJump(Code.pc-31);
		
		Code.put(Code.exit);
		Code.put(Code.return_);
		}
		
		if (SemanticAnalyzer.methodArrArr) {
		Obj newMethodArrArr= Tab.insert(Obj.Meth, "AddArrayScalMethod", Tab.intType);
		newMethodArrArr.setAdr(Code.pc);
		methodArrArr = Code.pc;
		
		Code.put(Code.enter);
		Code.put(2);
		Code.put(4);
		Code.loadConst(0);	
		Code.put(Code.store_2);
		Code.loadConst(0);	
		Code.put(Code.store_3);
		 Code.put(Code.load_2);
		 Code.put(Code.load_n);
		 Code.put(Code.load_3);
		 Code.put(Code.aload);
		 Code.put(Code.load_1);
		 Code.put(Code.load_3);
		 Code.put(Code.aload);
		 Code.put(Code.mul);
		 Code.put(Code.add);
		 Code.put(Code.store_2);
		 Code.put(Code.load_3);
		 Code.loadConst(1);
		 Code.put(Code.add);
		 Code.put(Code.store_3);
		 Code.put(Code.load_3);
		 Code.put(Code.load_n);
		 Code.put(Code.arraylength);
		 Code.putFalseJump(Code.lt, Code.pc + 7);
		 Code.loadConst(1);
		 Code.putJump(Code.pc + 4);
		 Code.loadConst(0);
		 Code.loadConst(0);
		 Code.putFalseJump(Code.ne, Code.pc + 6);
		 Code.putJump(Code.pc - 29);
		 Code.put(Code.load_2);
		 Code.put(Code.exit);
		 Code.put(Code.return_);
		 
		}
		
	/*	 int sc(int arr1[], int arr2[])
			int n; int i;	
			{
			n=0;
			i=0;
			do
			{
				n = n + arr1[i] * arr2[i];
				i++;
			}
			while( i < len(arr1));
			
			return n;
				
			} */ 
	}
	
	/*void sub(int arr3[], int arr1[], int arr2[], int operacija)
		int n; int i;	
		{
		n=len(arr1);
		i=0;
		do
		{
			if (operacija==0) arr3[i] = arr1[i] - arr2[i];
			else if (operacija==1) arr3[i] = arr1[i] + arr2[i];
			i++;
		}
		while( i < n);
			
		} 
		*/
	
	/*void scallar(int dest[], int niz[], int s)
	int n; int i;	
	{	
	n=len(niz);
	i=0;
	do
	{
		dest[i] = niz[i] * s;
		i++;
	}
	while( i < n);		
}*/
	


}
