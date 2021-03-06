package rs.ac.bg.etf.pp1;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class SemanticAnalyzer extends VisitorAdaptor {

	boolean mainDef=false;

	boolean isCurrentMAIN;
	Obj program;
	public static Struct intArray= new Struct(Struct.Array,Tab.intType);
	public static Struct charArray= new Struct(Struct.Array,Tab.charType);
	Obj lastDesignator=Tab.noObj;
	public static Struct boolType=new Struct(Struct.Bool);
	static Obj boolObj;

	int printCallCount = 0;
	int varDeclCount = 0;
	boolean returnFound = false;
	boolean errorDetected = false;
	int nVars;
	Obj currentMethod = null;
	int formalParams = 0;
	int actParams = 0;

	public static boolean methodArrArr = false;
	public static boolean methodAddArr = false;
	public static boolean methodScalArr = false;


	ArrayList<String> varNames= new ArrayList<String>();
	ArrayList<Boolean> varArray = new ArrayList<Boolean>();

	ArrayList<Obj> constArray= new ArrayList<Obj>();
	Logger log = Logger.getLogger(getClass());

	public String dump(Obj aa) {

		DumpSymbolTableVisitor stv = new DumpSymbolTableVisitor();
		stv.visitObjNode(aa);

		return stv.getOutput();
	} 

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0) Compiler2.addError( line, message ,2);
			//msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
		//System.out.println(msg.toString());
	}

	public void pretraga(String ime, SyntaxNode pos, Obj var2) {
		if (var2!=null) this.log.info("Pretraga na "+ pos.getLine() + "("+ime+"), nadjeno "+ dump(var2) );
		else this.log.info("Nadjena null vrednost" );
	}

	public void pretraga(String ime, SyntaxNode pos, Obj var2, String dodatak) {
		if (var2!=null) this.log.info("Pretraga na "+ pos.getLine() + "("+ime+"), nadjeno "+ dump(var2) + dodatak);
		else this.log.info("Nadjena null vrednost" );
	}

	//----------------------- Var metode -----------------------

	public void visit(Array a) {
		a.struct=Tab.intType;
	}

	public void visit(NotArray a) {
		a.struct=Tab.noType;   	
	}

	public void visit(VarTailSemi varDecl){
		varDeclCount++;
		varNames.add(varDecl.getVarName());
		varArray.add(varDecl.getArrayOrNot().struct==Tab.intType);
		//this.report_info("Tip " + varDecl.getVarName()+" " +((varDecl.getArrayOrNot().struct==Tab.intType)? "Array":"NotArray"), null);
	}

	public void visit(VarMiddle varDecl){
		varDeclCount++;
		varNames.add(varDecl.getVarName());
		varArray.add(varDecl.getArrayOrNot().struct==Tab.intType);
		//this.report_info("Tip " + varDecl.getVarName()+" " +((varDecl.getArrayOrNot().struct==Tab.intType)? "Array":"NotArray"), null);
	}


	public void visit(SimpleeeVarDecl varDecl){

		Obj check;
		Obj varNode;
		for (; varNames.size()>0;) {
			check = Tab.find(varNames.get(0));
			if (check!=Tab.noObj && Tab.currentScope.getLocals().symbols().contains(check)) {
				report_error("Greska na " + varDecl.getLine() + "(" + varNames.remove(0)+ "), vec deklarisano " , varDecl);
				return;
			}

			if (varArray.remove(0)) {		
				String name=varNames.remove(0);
				if (varDecl.getType().struct==Tab.intType)	varNode = Tab.insert(Obj.Var, name , intArray);
				else if (varDecl.getType().struct==Tab.charType)	varNode = Tab.insert(Obj.Var, name, charArray);
				else report_error("Greska na " + varDecl.getLine() + "(" + name + "), postoje samo int i char nizovi",varDecl);

				//report_info("Pretraga na " + varDecl.getLine() + "(" + varNode.getName() + "), nadjeno " +dump(varNode), null);
			}
			else {
				varNode = Tab.insert(Obj.Var, varNames.remove(0), varDecl.getType().struct);
				//report_info("Pretraga na " + varDecl.getLine() + "(" + varNode.getName() + "), nadjeno " +dump(varNode), null);
			}
		}
	}
	public void visit(VarDeclLoopTail varDecl){
		Obj check;
		Obj varNode;
		for (; varNames.size()>0;) {
			check = Tab.find(varNames.get(0));
			if (check!=Tab.noObj && Tab.currentScope.getLocals().symbols().contains(check)) {	
				report_error("Greska na " + varDecl.getLine() + "(" + varNames.remove(0)+ "), vec deklarisano " , varDecl);
				return;
			}
			else if (varArray.remove(0)) {	
				String name=varNames.remove(0);
				if (varDecl.getType().struct==Tab.intType)	varNode = Tab.insert(Obj.Var, name , intArray);
				else if (varDecl.getType().struct==Tab.charType)	varNode = Tab.insert(Obj.Var, name, charArray);
				else report_error("Greska na " + varDecl.getLine() + "(" + name + "), postoje samo int i char nizovi", varDecl);
				//report_info("Pretraga na " + varDecl.getLine() + "(" + varNode.getName() + "), nadjeno " +dump(varNode), null);
			}
			else {
				varNode = Tab.insert(Obj.Var, varNames.remove(0), varDecl.getType().struct);
				//report_info("Pretraga na " + varDecl.getLine() + "(" + varNode.getName() + "), nadjeno " +dump(varNode), null);
			}
		}
	}




	public void visit(Type type){

		Obj typeNode = Tab.find(type.getTypeName());

		if(typeNode == Tab.noObj){
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", type);
			type.struct = Tab.noType;
		}else{  
			if(Obj.Type == typeNode.getKind()){
				type.struct =typeNode.getType();
			}else{
				report_error("Greska na "+type.getLine()+"(" + type.getTypeName() + ") ne predstavlja tip!", type);
				type.struct = Tab.noType;
			}
			//	report_info("Pronadjen TIP " + type.getTypeName() + " "+ type.get, type);
		}
	} 

	//----------------------- Const metode -----------------------

	public void visit(True t) {
		t.obj=new Obj(Obj.Con,"TRUE", boolType,1,0);	
	}

	public void visit(False t) {
		t.obj=new Obj(Obj.Con, "False", boolType,0,0);
	}

	public void visit(Constant t) {
		t.obj=new Obj(Obj.Con, "", Tab.intType, t.getT() ,0);	
	}

	public void visit(CharConst t) {
		t.obj=new Obj(Obj.Con, "", Tab.charType, t.getT() ,0);		
	}

	public void visit(BoolConstant t) {
		t.obj=new Obj(Obj.Con, "", boolType, t.getBoolConst().obj.getAdr() ,0);		
	}

	public void visit(TailConst varDecl){	
		Obj newObj=new Obj(Obj.Con,varDecl.getVarName(), varDecl.getConstD().obj.getType(),varDecl.getConstD().obj.getAdr(),0);
		constArray.add(newObj);		
		//this.report_info("Ubaceno "+ varDecl.getVarName(), null);			 

	}

	public void visit(ConstMiddle varDecl){	
		Obj newObj=new Obj(Obj.Con,varDecl.getVarName(), varDecl.getConstD().obj.getType(),varDecl.getConstD().obj.getAdr(),0);
		constArray.add(newObj);	
		//this.report_info("Ubaceno "+ varDecl.getVarName(), null);
	}

	public void visit(SimpleConstDeclaration varDecl){
		Type t=varDecl.getType();
		for (;constArray.size()>0;) 
		{	
			Obj newObj=constArray.remove(0);
			// this.report_error(newObj.getName()+ " "+newObj.getKind()+" "+newObj.getAdr(), null);
			if (t.struct.getKind()!=newObj.getType().getKind()) this.report_error("Greska na "+varDecl.getLine()+" ("+ newObj.getName()  +") konstanta je pogresnog tipa treba da bude: " + t.struct.getKind() + " a ona je: "+ newObj.getType().getKind(), varDecl);
			else {
				Obj o= Tab.insert(Obj.Con, newObj.getName(), newObj.getType());
				o.setAdr(newObj.getAdr());
				//report_info("Pretraga na " + varDecl.getLine() + "(" + newObj.getName() + "), nadjeno " +dump(newObj), null);
			}
		}
	}

	public void visit(ConstMidLoopTail varDecl){	
		Type t=varDecl.getType();
		for (;constArray.size()>0;) 
		{	
			Obj newObj=constArray.remove(0);
			// this.report_info(newObj.getName()+ " "+newObj.getKind()+" "+newObj.getAdr(), null);
			if (t.struct.getKind()!=newObj.getType().getKind())  this.report_error("Greska na "+varDecl.getLine()+" ("+ newObj.getName()  +") konstanta je pogresnog tipa treba da bude: " + t.struct.getKind() + " a ona je: "+ newObj.getType().getKind(), varDecl);
			else {
				Obj o= Tab.insert(Obj.Con, newObj.getName(), newObj.getType());
				o.setAdr(newObj.getAdr());
				//report_info("Pretraga na " + varDecl.getLine() + "(" + newObj.getName() + "), nadjeno " +dump(newObj), null);
			}
		}
	}

	//----------------------- Class Var -----------------------

	public void visit(VarClassTailSemi varDecl){
		varNames.add(varDecl.getVarName());
		varArray.add(varDecl.getArrayOrNot().struct==Tab.intType);
		//this.report_info("Tip " + varDecl.getVarName()+" " +((varDecl.getArrayOrNot().struct==Tab.intType)? "Array":"NotArray"), null);
	}

	public void visit(VarClassMiddle varDecl){
		varNames.add(varDecl.getVarName());
		varArray.add(varDecl.getArrayOrNot().struct==Tab.intType);
		//this.report_info("Tip " + varDecl.getVarName()+" " +((varDecl.getArrayOrNot().struct==Tab.intType)? "Array":"NotArray"), null);
	}

	public void visit(VarClassDeclaration varDecl){

		Obj varNode;
		for (; varNames.size()>0;) {

			if (varArray.remove(0)) {	
				String name=varNames.remove(0);
				if (varDecl.getType().struct==Tab.intType)	varNode = Tab.insert(Obj.Var, name , intArray);
				else if (varDecl.getType().struct==Tab.charType)	varNode = Tab.insert(Obj.Var, name, charArray);
				else report_error("Greska na " + varDecl.getLine() + "(" + name + "), postoje samo int i char nizovi", varDecl);//report_info("Pretraga na " + varDecl.getLine() + "(" + varNode.getName() + "), nadjeno " +dump(varNode), null);
			}
			else {
				varNode = Tab.insert(Obj.Var, varNames.remove(0), varDecl.getType().struct);
				//report_info("Pretraga na " + varDecl.getLine() + "(" + varNode.getName() + "), nadjeno " +dump(varNode), null);
			}
		}
	} 

	public void visit(VarClassSimpleDeclaration varDecl){

		Obj varNode;
		for (; varNames.size()>0;) {
			if (varArray.remove(0)) {	
				String name=varNames.remove(0);
				if (varDecl.getType().struct==Tab.intType)	varNode = Tab.insert(Obj.Var, name , intArray);
				else if (varDecl.getType().struct==Tab.charType)	varNode = Tab.insert(Obj.Var, name, charArray);
				else report_error("Greska na " + varDecl.getLine() + "(" + name + "), postoje samo int i char nizovi", varDecl);	
				//report_info("Pretraga na " + varDecl.getLine() + "(" + varNode.getName() + "), nadjeno " +dump(varNode), null);
			}
			else {
				varNode = Tab.insert(Obj.Var, varNames.remove(0), varDecl.getType().struct);
				//report_info("Pretraga na " + varDecl.getLine() + "(" + varNode.getName() + "), nadjeno " +dump(varNode), null);
			}
		}
	}

	//----------------------- Method -----------------------

	public void visit(FormParams methodTypeName){
		currentMethod.setLevel(formalParams);
		if (isCurrentMAIN)
			report_error("Greska na " + methodTypeName.getLine() + "(main) funkcija ne treba da ima parametre", methodTypeName);
		isCurrentMAIN=false;
		// report_info("PARAMETRI " + methodTypeName.getLine() , null);

	}

	public void visit(NoFormParam methodTypeName){	
		currentMethod.setLevel(0);
		//report_info("false"+methodTypeName.getLine(), null);
		isCurrentMAIN=false;
		// report_info("NEMA PARAMETRE " + methodTypeName.getLine(), null);

	}


	public void visit(MethodTypeName methodTypeName){
		if (methodTypeName.getMetName().equals("main"))	report_error("Greska na " + methodTypeName.getLine() + "(main) funkcija ne treba da ima povratnu vrednost", methodTypeName);

		Obj obje = Tab.find(methodTypeName.getMetName());
		if (obje!=Tab.noObj && obje.getKind()==Obj.Meth) {
			report_error("Greska na " + methodTypeName.getLine() + "(" + methodTypeName.getMetName() + ") funkcija je vec definisana!", methodTypeName); 
			return;
		}
		currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMetName(), methodTypeName.getType().struct);	
		methodTypeName.obj = currentMethod;
		Tab.openScope();

		//report_info("FUNKCIJA " + methodTypeName.getLine() + "(" + methodTypeName.getMetName() + "), FUNKCIJA NIJE VOID", null);
	}

	public void visit(MethodTypeVoid methodTypeName){
		Obj obje = Tab.find(methodTypeName.getMetName());
		if (obje!=Tab.noObj && obje.getKind()==Obj.Meth) {
			report_error("Greska na " + methodTypeName.getLine() + "(" + methodTypeName.getMetName() + ") funkcija je vec definisana!", methodTypeName); 
			return;
		}
		if (methodTypeName.getMetName().equals("main")) { isCurrentMAIN=true;
		mainDef=true;
		//report_info("FUNKCIJA MAIN" + methodTypeName.getLine() + "(" + methodTypeName.getMetName() + "), nadjeno " +dump(currentMethod), null);   
		}
		currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMetName(), Tab.noType); 	
		methodTypeName.obj = currentMethod;
		Tab.openScope();

		//report_info("FUNKCIJA " + methodTypeName.getLine() + "(" + methodTypeName.getMetName() + "), nadjeno " +dump(currentMethod), null);
	}

	public void visit(MethodDeclar methodDecl){
		if(!returnFound && currentMethod.getType()!= Tab.noType){
			report_error("Greska na " + methodDecl.getLine() + "(" + currentMethod.getName() + ") funkcija nema return iskaz!", methodDecl);
		}
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();

		returnFound = false;
		currentMethod = null;
		formalParams=0;
	} 
	
	public void visit (ActParsWithComma actPars) {
		actParams++;
		
	}
	
	public void visit (SimpleActPars actPars) {
		actParams++;
		
	}
	
	public void visit (DesignatorStatementActPars ds) {
		
		if (ds.getDesignator().obj.getLevel()!=actParams) report_error("Greska na liniji " + ds.getLine() + " : " + " poziv funkcije" +ds.getDesignator().getDesignatorName().obj.getName() + " nema odgovarajuci broj formalnih parametara ", ds);
		actParams=0;
		ds.obj=ds.getDesignator().obj;
		report_info("Pretraga na " + ds.getLine() + "(" + ds.obj.getName() + "), nadjen poziv funkcije " +dump(ds.obj), null);
		
	}
	
	public void visit (DesignatorStatementNoActPars ds) {
		
		if (ds.getDesignator().obj.getLevel()!=actParams) report_error("Greska na liniji " + ds.getLine() + " : " + " poziv funkcije" +ds.getDesignator().getDesignatorName().obj.getName() + " nema odgovarajuci broj formalnih parametara ", ds);
		actParams=0;
		ds.obj=ds.getDesignator().obj;
		report_info("Pretraga na " + ds.getLine() + "(" + ds.obj.getName() + "), nadjen poziv funkcije " +dump(ds.obj), null);
		
	}
	
	public void visit(ReturnExpr returnExpr){
		returnFound = true;
		Struct returnFoundType=returnExpr.getExpr().obj.getType();
		Struct currMethType = currentMethod.getType();
		if(currMethType==Tab.noType){
			report_error("Greska na liniji " + returnExpr.getLine() + " : " + " funkcija nema povratnu vrednost ", returnExpr);
		}
		else if(currMethType!=returnFoundType){
			report_error("Greska na liniji " + returnExpr.getLine() + " : " + " nekompatibilan tip u return naredbi ", returnExpr);
		}

		//	report_error("AAAAAA KIND"+ returnExpr.getExpr().struct.getElemType().getKind(), null);

		//if(!currMethType.compatibleWith(returnExpr.getExpr().obj)){
		//report_error("Greska na " + returnExpr.getLine() + "(" + currentMethod.getName() + ") tip u return naredbi se ne slaze sa povratnom vrednoscu", null);
		//}
	}

	public void visit(ReturnStatement returnExpr){
		//report_info(""+currentMethod.getName()+ " "+ currentMethod.getType().getKind(), null);

		returnFound = true;
		Struct currMethType = currentMethod.getType();
		if(currMethType!=Tab.noType){
			report_error("Greska u metodi " + currentMethod.getName() + " : return naredba treba da ima povratnu vrednost ", returnExpr.getParent());
		}
	}  

	public void visit(SingleFormalParamDecl param){
		Obj varNode;
		if (param.getArrayOrNot().struct==Tab.intType) {	
			varNode = Tab.insert(Obj.Var, param.getIden(), new Struct(Struct.Array, param.getType().struct));	
			//report_info("Pretraga na " + param.getLine() + "(" + varNode.getName() + "), nadjeno " +dump(varNode), null);
		}
		else {
			varNode = Tab.insert(Obj.Var, param.getIden(), param.getType().struct);
			//report_info("Pretraga na " + param.getLine() + "(" + varNode.getName() + "), nadjeno " +dump(varNode), null);
		}
		formalParams++;
		
	} 
	public void visit(FormalParamDeclNoBracket param){
		Obj varNode;
		if (param.getArrayOrNot().struct==Tab.intType) {	
			varNode = Tab.insert(Obj.Var, param.getIden(), new Struct(Struct.Array, param.getType().struct));	
			//report_info("Pretraga na " + param.getLine() + "(" + varNode.getName() + "), nadjeno " +dump(varNode), null);
		}
		else {
			varNode = Tab.insert(Obj.Var, param.getIden(), param.getType().struct);
			//report_info("Pretraga na " + param.getLine() + "(" + varNode.getName() + "), nadjeno " +dump(varNode), null);
		}
		formalParams++;
		
	}


	//----------------------- Factor -----------------------

	public void visit(DesName t) {
		t.obj=new Obj(0,t.getName(), boolType);
	}
	//ako nadje array[x] salje tip elementa
	public void visit(Designator t) {
		// report_info("DESIGNATOR OBICAN " + t.getLine() + "(" + t.getName() + "), nadjeno ", null);
		//report_info("DESINATOR " + t.getLine() + "(" + t.getName() + "), nadjeno ", null);

		String name=t.getDesignatorName().obj.getName();

		Obj find =Tab.find(t.getDesignatorName().obj.getName());
		t.obj=Tab.noObj;

		if (find==Tab.noObj ) {
			this.report_error("Greska na "+t.getLine()+"("+name+"), nije nadjeno", t);
		}
		else if (find.getType().getKind()==Struct.Array && t.getDesignatorOptList().struct==intArray) {
			t.obj=new Obj(Obj.Elem, name+"_e" ,find.getType().getElemType());
			t.getDesignatorName().obj=find;
			report_info("Pretraga na " + t.getLine() + "(" + name + "), nadjen pristup elementu niza " +dump(find), null);
			if (currentMethod!=null && find.getLevel()>0 && find.getAdr()<currentMethod.getLevel()) report_info("Pretraga na " + t.getLine() + "(" + name + "), nadjena upotreba formalnog parametra funkcije " +dump(find), null);
			else if (currentMethod!=null && find.getLevel()>0) report_info("Pretraga na " + t.getLine() + "(" + name + "), nadjena upotreba lokalne promenljive " +dump(find), null);
			
		}
		else if (find.getType().getKind()==Struct.Array) {
			t.obj=find;
			report_info("Pretraga na " + t.getLine() + "(" + name + "), nadjeno " +dump(find), null);  
		}
		else if (find.getType().getKind()!=Struct.Array && t.getDesignatorOptList().struct==intArray) {
			this.report_error("Greska na "+t.getLine()+"("+name+"), neispravna upotreba, promenljiva ne treba da bude niz", null);
		}
		else {
			t.obj=find;
			if (find.getKind()==Obj.Var || find.getKind()==Obj.Con || find.getKind()==Obj.Fld) {
				lastDesignator=find; 
				if (currentMethod!=null && find.getLevel()>0 && find.getAdr()<currentMethod.getLevel()) report_info("Pretraga na " + t.getLine() + "(" + name + "), nadjena upotreba formalnog parametra funkcije " +dump(find), null);
				else if (currentMethod!=null && find.getLevel()>0) report_info("Pretraga na " + t.getLine() + "(" + name + "), nadjena upotreba lokalne promenljive " +dump(find), null);
				else report_info("Pretraga na " + t.getLine() + "(" + name + "), nadjena upotreba globalne promenljive " +dump(find), null);
			}
		}

	}

	public void visit(FactorNew ne) {

		ne.obj=new Obj(Obj.Var, "new", ne.getType().struct);
	}

	public void visit( FactorNewWithExpr ne) {
		//  report_info("FAKTOR " + ne.getLine() + "(), nadjeno ", null);

		if(ne.getExpr().obj.getType()!=Tab.intType ) {
			this.report_error("Greska na "+ne.getExpr().getLine()+"("+ne.getExpr().obj.getName()+"), neispravan tip indeksa niza, mora da bude int", ne.getExpr());
			ne.obj=Tab.noObj;
			return;  
		}
		else if (ne.getType().struct==Tab.charType)  ne.obj=new Obj(Obj.Var, "new", charArray);
		else if (ne.getType().struct==Tab.intType)  ne.obj=new Obj(Obj.Var, "new", intArray);
		else {
			ne.obj=Tab.noObj;
		}
	}




	public void visit(DesignatorOpts t) {
		//report_info("DESIGNATOR " + t.getLine() + "(), nadjeno "+t.getExpr().obj.getName()+" "+t.getExpr().obj.getType().getKind(), null);

		//  this.report_info(""+t.getExpr().obj.getType().getKind(), t);
		t.struct=intArray;
		if (t.getExpr().obj==null || t.getExpr().obj==Tab.noObj) {
			t.struct=Tab.nullType;
			this.report_error("Opt Greska na "+t.getLine() +"("+t.getExpr().obj.getName()+"), promenljiva mora da bude Int tipa neispravnoa je", t);	
		}
		else if ( t.getExpr().obj.getType()!=Tab.intType) {
			t.struct=Tab.nullType;
			this.report_error("Opt Greska na "+t.getExpr().obj.getKind()+" "+t.getLine() +"("+t.getExpr().obj.getName()+"), promenljiva mora da bude Int tipa", t);	
		}
	}

	public void visit(NoDesignatorOpt t) {
		t.struct=Tab.noType;
		//report_info("DESIGNATOR beznastavka " + t.getLine() + "(), nadjeno ", null);
	}

	public void visit(FactorConstNum t) {
		t.obj=new Obj(Obj.Con, "", Tab.intType, t.getN() ,0);	
	}

	public void visit(FactorConstChar t) {
		t.obj=new Obj(Obj.Con, "", Tab.charType, t.getT() ,0);		
	}

	public void visit(FactorConstBool t) {

		t.obj=new Obj(Obj.Con, "", boolType, t.getBoolConst().obj.getAdr() ,0);		
	}

	public void visit(FactorExpr varDecl){	
		varDecl.obj=varDecl.getExpr().obj;
	}

	public void visit(TermFactor var1) {
		var1.obj = var1.getFactor().obj;
		//this.report_info("Pronadjen struct " + var1.getClass().getName()+ " na liniji " + var1.getLine(), (SyntaxNode)null);
	}

	public void visit(TermExprMinus var1) {
		var1.obj = var1.getTerm().obj;
		if (var1.obj.getType()!=Tab.intType) {
			this.report_error("Greska na "+var1.getLine() +"("+var1.getTerm().obj.getName()+"), promenljiva mora da bude Int tipa", var1);	
		}
	}

	public void visit(TermExpr var1) {
		var1.obj = var1.getTerm().obj;
	}
	public void visit(AddopPlus a) {
		a.struct=new Struct(1);
	}
	public void visit(Scalarr a) {
		a.struct=new Struct(0);
	}
	public void visit(AddopMinus a) {
		a.struct=new Struct(-1);
	}
	public void visit(MullopMul a) {
		a.struct=new Struct(1);
	}
	public void visit(MullopDiv a) {
		a.struct=new Struct(2);
	}
	public void visit(MullopMod a) {
		a.struct=new Struct(3);
	}

	public void visit(TermsMulop addExpr) {
		Struct te = addExpr.getFactor().obj.getType();
		Struct t = addExpr.getTerm().obj.getType();

		if(te.compatibleWith(t) && te==Tab.intType){
			addExpr.obj=addExpr.getFactor().obj;
		}
		else if (t==te && t==intArray && addExpr.getMulop().getClass()==MullopMul.class) {	
				methodArrArr = true;
				addExpr.obj = new Obj(Obj.Con, "as", Tab.intType);
		}
		else if (t==intArray && te==Tab.intType) {
			addExpr.obj = addExpr.getTerm().obj;
			methodScalArr = true;
		}
		else if (te==intArray && t==Tab.intType) {
			addExpr.obj=addExpr.getFactor().obj;
			methodScalArr = true;
		}
		else	
		{
			addExpr.obj = Tab.noObj;
			this.report_error("Greska na "+addExpr.getFactor().getLine()+"("+ addExpr.getTerm().obj.getName()+"), oba clana MUL izraza moraju da budu Int tipa", addExpr.getFactor());
		}
	}

	public void visit(Var var1) {
		var1.obj = var1.getDesignator().obj;
	}

	public void visit(AddExpr addExpr){
		//report_info("ADD "+addExpr.getTerm().obj.getName(), null);
		// report_error(""+addExpr.getExpr().obj.getType().getKind(), null);
		// report_error(""+addExpr.getTerm().obj.getType().getKind(), null);
		Struct te = addExpr.getExpr().obj.getType();
		Struct t = addExpr.getTerm().obj.getType();

		if(t==te && t==Tab.intType ){
			addExpr.obj = addExpr.getExpr().obj;
		}
		else if (t==te && t==intArray) {
			if (addExpr.getAddop().getClass()==Scalarr.class) {
				methodArrArr = true;
				addExpr.obj = new Obj(Obj.Con, "as", Tab.intType);
			}
			else if (addExpr.getAddop().getClass()==AddopMinus.class || addExpr.getAddop().getClass()==AddopPlus.class) { 
				methodAddArr = true;
				addExpr.obj = addExpr.getExpr().obj;	
			}
			
		}
		else if (t==intArray && te==Tab.intType) {
			addExpr.obj = addExpr.getTerm().obj;
			methodScalArr = true;
		}
		else if (te==intArray && t==Tab.intType) {
			addExpr.obj=addExpr.getExpr().obj;
			methodScalArr = true;
		}
		else{
			this.report_error("Greska na "+addExpr.getExpr().getLine()+"("+addExpr.getExpr().obj.getName()+""+ addExpr.getTerm().obj.getName()+"), oba sabirka moraju da budu Int tipa", addExpr.getExpr());
			addExpr.obj = Tab.noObj;
		}
	} 

	public void visit(DesignatorStatementInc d){
		if (d.getDesignator().obj.getKind()==Obj.Con) this.report_error("Greska na " + d.getLine() + " : " + "neispravan izraz, ne moze konstanta! ", d);  
		else if (d.getDesignator().obj.getType()!=Tab.intType) this.report_error("Greska na " + d.getLine() + " : " + "neispravan izraz, nije integer! ", d);  
		d.obj=d.getDesignator().obj;
	}

	public void visit(DesignatorStatementDec d){
		if (d.getDesignator().obj.getKind()==Obj.Con) this.report_error("Greska na " + d.getLine() + " : " + "neispravan izraz, ne moze konstanta! ",d);  
		else if (d.getDesignator().obj.getType()!=Tab.intType) this.report_error("Greska na " + d.getLine() + " : " + "neispravan izraz, nije integer! ", d);  
		d.obj=d.getDesignator().obj;

	}

	public void visit(DesignatorStatementAsign var1) {
		//report_info("ASSIGN "+""+var1.getDesignator().obj.getKind(), null);

		if (var1.getDesignator().obj == Tab.noObj || var1.getExpr().obj == Tab.noObj) {
			this.report_error("Greska na " + var1.getLine() + " : " + "neispravan izraz! ", var1);  
			var1.obj=Tab.noObj;
		}
		else if (var1.getDesignator().obj.getKind()==Obj.Con) {
			this.report_error("Greska na " + var1.getLine() + " : " + "ne moze konstanti da se dodeli vrednost! ", var1);  
			var1.obj=Tab.noObj;
		}
		else if (var1.getDesignator().obj.getKind()==Obj.Meth) {
			this.report_error("Greska na " + var1.getLine() + " : " + "ne moze metodi da se dodeli vrednost! ", var1);  
			var1.obj=Tab.noObj;
		}
		else if (var1.getDesignator().obj.getType().getKind()==var1.getExpr().obj.getType().getKind()) {
			var1.obj=var1.getDesignator().obj;
			if (var1.getExpr().getClass()==AddExpr.class) var1.getExpr().obj=var1.getDesignator().obj;

		}

		else if ((var1.getDesignator().obj.getType().getKind()==Struct.Array) && (var1.getExpr().obj.getType().getKind()==Struct.Array) && var1.getExpr().obj.getType().getElemType().getKind()==var1.getDesignator().obj.getType().getElemType().getKind()) {
			var1.obj=var1.getDesignator().obj;
			if (var1.getExpr().obj.getName().equals("new")) var1.getExpr().obj=var1.getDesignator().obj;
		}
		else if (!var1.getExpr().obj.getType().assignableTo(var1.getDesignator().obj.getType())) {
			this.report_error("Greska na " + var1.getLine() + " : " + "nekompatibilni tipovi u dodeli vrednosti! ", var1);
			var1.obj=Tab.noObj;
		}
		else {
			this.report_error("Greska na " + var1.getLine() + " : " + "nekompatibilni tipovi u dodeli vrednosti! ", var1);
			var1.obj=Tab.noObj;
		}

	} 

	public void visit(FuncCallWithPars o) {
		
		if (o.getDesignator().obj.getLevel()!=actParams) {
			report_error("Greska na liniji " + o.getLine() + " : " + " poziv funkcije "+o.getDesignator().getDesignatorName().obj.getName()+ " nema odgovarajuci broj formalnih parametara ", o);
			}
		actParams=0;
		o.obj=o.getDesignator().obj;
		report_info("Pretraga na " + o.getLine() + "(" + o.obj.getName() + "), nadjen poziv funkcije " +dump(o.obj), null);
	}

	public void visit(FuncCallNoPars o) {
		if(actParams>0) report_error("Greska na liniji " + o.getLine() + " : " + " poziv funkcije "+o.getDesignator().getDesignatorName().obj.getName()+ " ne treba da ima formalne parametre ", o);
		actParams=0;
		o.obj=o.getDesignator().obj;
		report_info("Pretraga na " + o.getLine() + "(" + o.obj.getName() + "), nadjen poziv funkcije " +dump(o.obj), null);		
		
	}


	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		program=progName.obj;
		Scope universe = Tab.currentScope ;
		boolObj=new Obj(Obj.Type, "bool", boolType);
		universe.addToLocals(boolObj);

		Tab.openScope();
	}

	public void visit(Program program) {
		nVars=Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}

	public void visit(ClassName className) {
		className.obj = Tab.insert(Obj.Type, className.getClassName(), Tab.nullType);
		Tab.openScope();
		//this.report_info("OTVORI KLASU", null);
		//report_info("Pretraga na " + className.getLine() + "(" + className.getClassName() + "), nadjeno " +dump(className.obj), null);		
	}

	public void visit(ClasssDecl cls) {

		Tab.chainLocalSymbols(cls.getClassName().obj);
		Tab.closeScope();
		//this.report_info("ZATVORI KLASU", null);
	}
	public void visit(ClassDeclNoMethod cls) {
		Tab.chainLocalSymbols(cls.getClassName().obj);
		Tab.closeScope();
		//this.report_info("ZATVORI KLASU BEZ METODA", null);
	}	


	public void visit (PrintStatement print) {
		//System.out.println(print.getExpr().obj.getType().getKind());
		printCallCount++;
		//if(print.getExpr().obj.getType() != Tab.intType && print.getExpr().obj.getType()!= Tab.charType && print.getExpr().obj.getType()!= boolType) report_error ("Semanticka greska na liniji " + print.getLine() + ": Operand instrukcije PRINT mora biti char ili int tipa", print );

	}

	public boolean passed() {
		return !this.errorDetected;
	}

	//----------------RelOp-------------

	public void visit(SimpleCondFact s) {
		s.obj=s.getExpr().obj;	   
	}

	public void visit(SimpleCondTerm s) {
		s.obj=s.getCondFact().obj;		   
	}

	public void visit(SimpleCondition s) {
		s.obj=s.getCondTerm().obj;		   
	}

	public void visit (CondFactRelop var1) {
		if (var1.getExpr().obj == Tab.noObj || var1.getExpr1().obj == Tab.noObj) {
			this.report_error("Greska na " + var1.getLine() + " : " + "neispravan izraz! ", var1);  
			var1.obj=Tab.noObj;
		}

		else if (var1.getExpr1().obj.getType()==var1.getExpr().obj.getType()) {
			var1.obj=var1.getExpr1().obj;
		}

		else if (var1.getExpr1().obj.getType()!=var1.getExpr().obj.getType()) {
			var1.obj=Tab.noObj;
			this.report_error("Greska na " + var1.getLine() + " : " + "nekompatibilni tipovi! ", var1);
		}

		else if ((var1.getExpr1().obj.getType().getKind()==Struct.Array) && (var1.getExpr().obj.getType().getKind()==Struct.Array) && var1.getExpr().obj.getType().getElemType().getKind()==var1.getExpr1().obj.getType().getElemType().getKind()) {
			var1.obj=var1.getExpr1().obj;
			if (var1.getExpr().obj.getName().equals("new")) var1.getExpr().obj=var1.getExpr1().obj;
		}
		else if (!var1.getExpr().obj.getType().compatibleWith(var1.getExpr1().obj.getType())) {
			this.report_error("Greska na " + var1.getLine() + " : " + "nekompatibilni tipovi! ", var1);
			var1.obj=Tab.noObj;
		}
		else {
			this.report_error("Greska na " + var1.getLine() + " : " + "nekompatibilni tipovi! ", var1);
			var1.obj=Tab.noObj;
		}
	}

	public void visit (RelopIsE r) {
		r.obj=new Obj(Obj.NO_VALUE, r.getE(), boolType);
	}
	public void visit (RelopNotE r) {
		r.obj=new Obj(Obj.NO_VALUE, r.getE(), boolType);
	}
	public void visit (RelopGr r) {
		r.obj=new Obj(Obj.NO_VALUE, r.getE(), boolType);
	}
	public void visit (RelopGrOrEq r) {
		r.obj=new Obj(Obj.NO_VALUE, r.getE(), boolType);
	}
	public void visit (RelopLe r) {
		r.obj=new Obj(Obj.NO_VALUE, r.getE(), boolType);
	}
	public void visit (RelopLeOrEq r) {
		r.obj=new Obj(Obj.NO_VALUE, r.getE(), boolType);
	}













}
