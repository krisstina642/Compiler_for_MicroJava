package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.Tab;

import rs.ac.bg.etf.pp1.test.CompilerError;
import rs.ac.bg.etf.pp1.test.CompilerError.CompilerErrorType;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.util.Log4JUtils;

import rs.etf.pp1.symboltable.Tab;

public final class Compiler2 implements rs.ac.bg.etf.pp1.test.Compiler{
	static List<CompilerError> compileErrors;
	
	public static void addError(int line, String message, int type) {
		switch(type) {
		case 0: compileErrors.add(new CompilerError(line, message, CompilerErrorType.LEXICAL_ERROR));
				break;
		case 1: compileErrors.add(new CompilerError(line, message, CompilerErrorType.SYNTAX_ERROR));
		break;
		case 2: compileErrors.add(new CompilerError(line, message, CompilerErrorType.SEMANTIC_ERROR));
		break;
		
		} 
	}
	
	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void tsdump() {
		Tab.dump();
	}
	@Override
	public List<CompilerError> compile(String sourceFilePath, String outputFilePath) {
		compileErrors=new ArrayList<>();
		
		
		Logger log = Logger.getLogger(Compiler2.class);
		
		Reader br = null;
		try {
			
			File sourceCode = new File(sourceFilePath);
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        
	        Program prog = (Program)(s.value); 
	     //  if (!p.errorDetected)
			//log.info(prog.toString(""));
	       //else
			log.info("===================================");
			Tab.init();
			SemanticAnalyzer v = new SemanticAnalyzer();
			prog.traverseBottomUp(v); 
			
			log.info("===================================");
	      
			log.info(" Print count calls = " + v.printCallCount);
			if (!v.mainDef) log.error("Nije definisana main funkcija");
			
			log.info("===================================");
			tsdump();
			// ispis prepoznatih programskih konstrukcija
		
	  	
			log.info("===================================");
			
			PrintStream out = new PrintStream(new FileOutputStream("test/"+sourceFilePath.substring(5, sourceFilePath.length()-3)+".out"));
			System.setOut(out);
			
			if(!p.errorDetected && v.passed()){
				File objFile = new File(outputFilePath);
				if(objFile.exists()) objFile.delete();
				
				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = v.nVars;
				Code.mainPc = codeGenerator.getMainPc();
				Code.write(new FileOutputStream(objFile));
				log.info("Parsiranje uspesno zavrseno!\n");
				
				String[] args= {outputFilePath};
				
				disasm.main(args);
				Run.main(args);
				
			}else{
				System.out.println("Parsiranje NIJE uspesno zavrseno!");
				log.error("Parsiranje NIJE uspesno zavrseno!\n");
			}
			
			out = new PrintStream(new FileOutputStream("test/"+sourceFilePath.substring(5, sourceFilePath.length()-3)+".err"));
			System.setOut(out);
			for (CompilerError compilerError : compileErrors) {
				System.out.println(compilerError);
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}
		
		return compileErrors;
	}

}
