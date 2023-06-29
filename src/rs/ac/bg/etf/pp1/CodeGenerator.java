package rs.ac.bg.etf.pp1;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;




import rs.ac.bg.etf.pp1.CounterVisitor.CondTermCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class CodeGenerator extends VisitorAdaptor {

	public CodeGenerator() {
	
			Tab.chrObj.setAdr(Code.pc);
			Code.put(Code.enter);
			Code.put(1);
			Code.put(1);
			Code.put(Code.load_n);
			Code.put(Code.exit);
			Code.put(Code.return_);
		
			Tab.ordObj.setAdr(Code.pc);
			Code.put(Code.enter);
			Code.put(1);
			Code.put(1);
			Code.put(Code.load_n);
			Code.put(Code.exit);
			Code.put(Code.return_);
		
			Tab.lenObj.setAdr(Code.pc);
			Code.put(Code.enter);
			Code.put(1);
			Code.put(1);
			Code.put(Code.load_n);
			Code.put(Code.arraylength);
			Code.put(Code.exit);
			Code.put(Code.return_);

	}
	
	
	private int mainPc;
	private int labelMap;
	
	private int CondTermNum = 0;
	
	public List<Integer> patchCondTermList = new ArrayList<>();
	public List<Integer> patchThenAdressesList = new ArrayList<>();
	public List<Integer> patchElseAdressesList = new ArrayList<>();
	
	public Stack<List<Integer>> whilePatchEndStack = new Stack<>();
	public Stack<Integer> whileAddrStack = new Stack<>();
	
	public Stack<List<Integer>> ifPatchElseStack = new Stack<>();
	
	boolean ifFlag = false;

	public int getMainPc() {
		return mainPc;
	}

	public void visit(StatementPrintWithNum statementPrintWithNum) {

		// ucitavanje sirine
		Code.loadConst(statementPrintWithNum.getIntConstForPrint());

		if (statementPrintWithNum.getExpr().struct.assignableTo(Tab.intType)) {
			Code.put(Code.print);
		} else if (statementPrintWithNum.getExpr().struct.assignableTo(Tab.charType)) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}

	}

	public void visit(StatementPrint statementPrint) {
		if (statementPrint.getExpr().struct.assignableTo(Tab.intType)) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else if (statementPrint.getExpr().struct.assignableTo(Tab.charType)) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else {
			Code.loadConst(5);
			Code.put(Code.print); 
		}

	}

	public void visit(ConstantsNum constantsNum) {
		Obj con = Tab.insert(Obj.Con, "numConstanty", Tab.intType);
		con.setAdr(constantsNum.getNumber());

		Code.load(con); // stavljanje na expression stack

	}

	public void visit(ConstantsChar constantsChar) {
		Obj con = Tab.insert(Obj.Con, "charConstant", Tab.charType);
		con.setAdr(constantsChar.getCharacter());

		Code.load(con);
	}

	public void visit(ConstantsBool constantsBool) {
		Obj con = Tab.insert(Obj.Con, "boolConstant", Compiler.boolType);

		if (constantsBool.getBoolean()) {
			con.setAdr(1);
		} else {
			con.setAdr(0);
		}

		Code.load(con);
	}

	public void visit(MethodTypeName methodTypeName) {
		if ("main".equalsIgnoreCase(methodTypeName.getRealMethodName())) {
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc); // stavljamo zbog poziva da znamo
											// gde da skace
		// Count arguments and local vars
		SyntaxNode methodNode = methodTypeName.getParent();

		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);

		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());

	}

	public void visit(MethodDecl methodDecl) {
//		if(!(methodDecl.getMethodTypeName().getReturnType() instanceof ReturnTypeVoid)){
//			Code.put(Code.trap);
//			Code.loadConst(-1);
//		}
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(DesignatorIdent designatorIdent) {
		SyntaxNode parent = designatorIdent.getParent();
		Obj designatorObj = designatorIdent.getMyObj().obj;
		

		// za ove radi push, za ostale nista kad je oblika samo promenljive
		if (FactorDesignator.class == parent.getClass() || DesignatorStatementIncrement.class == parent.getClass() || DesignatorStatementDecrement.class == parent.getClass()) {
			Code.load(designatorObj);
		}
	}

	public void visit(DesignatorArrayElement designatorArrayElement) {
		SyntaxNode parent = designatorArrayElement.getParent();
		Obj designatorObj = designatorArrayElement.getMyObj().obj;

		if (FactorDesignator.class == parent.getClass()) {
			Code.load(designatorObj);
			// Code.loadConst(designatorArrayElement.getExpr());
			// ..., expr,obj
			// ..., obj, expr obj
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
			
			if(designatorObj.getType().getElemType().assignableTo(Tab.charType)){
				Code.put(Code.baload);
			}
			else{
				Code.put(Code.aload);
			}
			
		} else if (DesignatorStatementAssignment.class == parent.getClass() || StatementRead.class == parent.getClass() || DesignatorStatementIncrement.class == parent.getClass() || DesignatorStatementDecrement.class == parent.getClass()) {
			Code.load(designatorObj);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
		}

	}
	
	public void visit(DesignatorMatrixElement designatorMatrixElement) {
		SyntaxNode parent = designatorMatrixElement.getParent();
		Obj designatorObj = designatorMatrixElement.getMyObj().obj;

		if (FactorDesignator.class == parent.getClass()) {
			Code.load(designatorObj);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.aload);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
			
			if(designatorObj.getType().getElemType().assignableTo(Tab.charType)){
				Code.put(Code.baload);
			}
			else{
				Code.put(Code.aload);
			}
		} else if (DesignatorStatementAssignment.class == parent.getClass() || StatementRead.class == parent.getClass() || DesignatorStatementIncrement.class == parent.getClass() || DesignatorStatementDecrement.class == parent.getClass()) {
			Code.load(designatorObj);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.aload);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
		}

	}
	
	
	// Ako nam identifikator predstavlja konstantu onda odmah njeno
	// polje adr mapiramo u odgovarajucu const_n instrukciju
	public void visit(DesignatorStatementIncrement designatorStatementIncrement) {

		Designator designator = designatorStatementIncrement.getDesignator();
		Obj designatorObject = returnDesignatorObject(designator);

		if (designator instanceof DesignatorIdent) {
			Code.loadConst(1);
			Code.put(Code.add);
			Code.store(designatorObject);
		}
		else{ //if(designator instanceof DesignatorArrayElement){ isto za niz i matrice pa ne mora provera
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.put(Code.astore);
		}
	}

	public void visit(DesignatorStatementDecrement designatorStatementDecrement) {

		Designator designator = designatorStatementDecrement.getDesignator();
		Obj designatorObject = returnDesignatorObject(designator);

		if (designator instanceof DesignatorIdent) {
			Code.loadConst(1);
			Code.put(Code.sub);
			Code.store(designatorObject);
		}else{ //if(designator instanceof DesignatorArrayElement){
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.loadConst(1);
			Code.put(Code.sub);
			Code.put(Code.astore);
		}
	}

	public void visit(DesignatorStatementAssignment designatorStatementAssignment) {
		Designator designator = designatorStatementAssignment.getDesignator();
		Obj designatorObject = returnDesignatorObject(designator);

		if (designator instanceof DesignatorIdent) {
			Code.store(designatorObject);
		}
		else{ //if(designator instanceof DesignatorArrayElement){
			if(designatorObject.getType().getElemType().assignableTo(Tab.charType)){
				Code.put(Code.bastore);
			}
			else{
				Code.put(Code.astore);
			}
		}
	}

	public Obj returnDesignatorObject(Designator designator) {
		Obj obj = null;

		if (designator instanceof DesignatorIdent) {
			obj = ((DesignatorIdent) designator).getMyObj().obj;
		} else if (designator instanceof DesignatorArrayElement) {
			obj = ((DesignatorArrayElement) designator).getMyObj().obj;
		} else {
			obj = ((DesignatorMatrixElement) designator).getMyObj().obj;
		}
		return obj;
	}

	public void visit(DesignatorStatementFunctionCall designatorStatementFunctionCall) {
		Obj functionObj = returnDesignatorObject(designatorStatementFunctionCall.getDesignator());
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	public void visit(StatementReturn statementReturn){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(StatementReturnExpr statementReturnExpr){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(FactorFunctionCall factorFunctionCall) {
		Obj functionObj = returnDesignatorObject(factorFunctionCall.getDesignator());
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	public void visit(TermMulopFactor termMulopFactor) {
		if (termMulopFactor.getMulop() instanceof MulopStar) {
			Code.put(Code.mul);
		} else if (termMulopFactor.getMulop() instanceof MulopSlash) {
			Code.put(Code.div);
		} else { // percent
			Code.put(Code.rem);
		}
	}

	public void visit(ExprAddopTerm exprAddopTerm) {
		if (exprAddopTerm.getAddop() instanceof AddopPlus) {
			Code.put(Code.add);
		} else {// minus
			Code.put(Code.sub);
		}
	}

	public void visit(ExprMinusTerm exprMinusTerm) {
		Code.put(Code.neg);
	}

	public void visit(StatementRead statementRead) {
		
		Designator designator = statementRead.getDesignator();
		Obj designatorObject = returnDesignatorObject(designator);

		if (statementRead.getDesignator().struct.assignableTo(Tab.charType)) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		
		
		if (designator instanceof DesignatorIdent) {
			Code.store(designatorObject);
		}
		else{ //if(designator instanceof DesignatorArrayElement){
			if(designatorObject.getType().getElemType().assignableTo(Tab.charType)){
				Code.put(Code.bastore);
			}
			else{
				Code.put(Code.astore);
			}
		}
		
	}

	public void visit(FactorNewArray factorNewArray){
		if(factorNewArray.getType().struct.assignableTo(Tab.charType)){
			Code.put(Code.newarray);
			Code.loadConst(0);
		}
		else{
			Code.put(Code.newarray);
			Code.loadConst(1);
		}
	}
	
	public void visit(FactorNewMatrix factorNewMatrix){
		
		
		Designator designator =  ((DesignatorStatementAssignment)factorNewMatrix.getParent().getParent().getParent()).getDesignator();
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.put(Code.dup);
		
		
		Code.put(Code.newarray);
		Code.loadConst(1); //ovde pravimo niz pokazivaca
		
		Code.store(((DesignatorIdent)designator).getMyObj().obj);
		Code.loadConst(-1);
		int loop1 = Code.pc;
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.dup2);	
		Code.putFalseJump(Code.ne, 0);
		int adrToPatch = Code.pc - 2;
		Code.put(Code.dup_x2);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.put(Code.dup_x2);
		Code.load(((DesignatorIdent)designator).getMyObj().obj);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		
		Code.put(Code.newarray);
		if(factorNewMatrix.getType().struct.assignableTo(Tab.charType)){
			Code.loadConst(0);
		}
		else{
			Code.loadConst(1);
		}
		
		
		Code.put(Code.astore);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.putJump(loop1);
		Code.fixup(adrToPatch);
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.load(((DesignatorIdent)designator).getMyObj().obj);
	}

	public void visit(Assignop assignop){
		SyntaxNode parent = assignop.getParent();
		if(DesignatorStatementMap.class == parent.getClass()){
			
			Code.load(((DesignatorIdent)(((DesignatorStatementMap)parent).getDesignator1())).getMyObj().obj);
			Code.put(Code.arraylength);
			Code.put(Code.newarray);
			if((((DesignatorStatementMap)parent).getDesignator1()).struct.assignableTo(Tab.charType)){
				Code.loadConst(0);
			}
			else{
				Code.loadConst(1);
			}
			Code.store(((DesignatorIdent)(((DesignatorStatementMap)parent).getDesignator())).getMyObj().obj);
			
			Code.loadConst(0);
		}
	}
	
	public void visit(MapVariable mapVariable){
		labelMap = Code.pc;
		DesignatorStatementMap parent = (DesignatorStatementMap)mapVariable.getParent();
		
		//variable = source_array[i]
		Code.load(((DesignatorIdent)parent.getDesignator1()).getMyObj().obj);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.put(Code.dup_x1);
		Code.put(Code.aload);
		
		
		Obj variable = mapVariable.obj;
		Code.store(variable); //Proveriti ovo da li mora da vrati kao obj
		
		Code.load(((DesignatorIdent)parent.getDesignator()).getMyObj().obj);
		Code.put(Code.dup2);
		Code.put(Code.pop);
	}
	
	public void visit(DesignatorStatementMap designatorStatementMap){
		Code.put(Code.astore);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.dup);
		Code.load(((DesignatorIdent)designatorStatementMap.getDesignator1()).getMyObj().obj);
		Code.put(Code.arraylength);
		Code.putFalseJump(Code.eq, labelMap);
		Code.put(Code.pop);
	}

	public int returnRelationCode(CondFactExprRelopExpr condFactExprRelopExpr){
		
		if(condFactExprRelopExpr.getRelop() instanceof RelopEqual){
			return Code.eq;
		}
		if(condFactExprRelopExpr.getRelop() instanceof RelopNotEqual){
			return Code.ne;
		}
		if(condFactExprRelopExpr.getRelop() instanceof RelopGreater){
			return Code.gt;
		}
		if(condFactExprRelopExpr.getRelop() instanceof RelopGreaterOrEqual){
			return Code.ge;
		}
		if(condFactExprRelopExpr.getRelop() instanceof RelopLess){
			return Code.lt;
		}
		else {
			return Code.le;
		}
	}
	
	public void visit(CondTermSingleFact condTermSingleFact){
		
		SyntaxNode parent = condTermSingleFact.getParent();
		if(ifFlag){
			patchElseAdressesList = ifPatchElseStack.peek();
		}
		else {
			patchElseAdressesList = whilePatchEndStack.peek();
		}
		if(CondTermNum == 1){
			//Za poslednji CondTerm treba da se skoci na else deo ako je netacno

			
			if(condTermSingleFact.getCondFact() instanceof CondFactExpr){ //ako se stiglo do poslednjeg CondFact u jednom CondTerm i on je true skace se na then nezavisno od ostalih uslova jer je izmedju ||
				Code.loadConst(1);
				Code.putFalseJump(Code.eq, 0); //skok na then
				patchElseAdressesList.add(Code.pc-2);
			}
			else{
				Code.putFalseJump(returnRelationCode((CondFactExprRelopExpr)condTermSingleFact.getCondFact()), 0); //skok na then
				patchElseAdressesList.add(Code.pc-2);
			}
				
		}
		else{
			if(ConditionSingleTerm.class == parent.getClass() || ConditionMultipleTerm.class == parent.getClass()){
				//poslednji expr
				if(condTermSingleFact.getCondFact() instanceof CondFactExpr){ //ako se stiglo do poslednjeg CondFact u jednom CondTerm i on je true skace se na then nezavisno od ostalih uslova jer je izmedju ||
					Code.loadConst(1);
					Code.putFalseJump(Code.ne, 0); //skok na then
					patchThenAdressesList.add(Code.pc-2);
				}
				else{
					Code.putFalseJump(Code.inverse[returnRelationCode((CondFactExprRelopExpr)condTermSingleFact.getCondFact())], 0); //skok na then
					patchThenAdressesList.add(Code.pc-2);
				}
			}
			else{ //ako nije poslednji CondFact i nije tacan onda se skace na sledeci condFact
				if(condTermSingleFact.getCondFact() instanceof CondFactExpr){
					Code.loadConst(1);
					Code.putFalseJump(Code.eq, 0); //skok na sledeci condFact
					patchCondTermList.add(Code.pc-2);
				}
				else{
					Code.putFalseJump(returnRelationCode((CondFactExprRelopExpr)condTermSingleFact.getCondFact()), 0); //sledeciCondFact
					patchCondTermList.add(Code.pc-2);
				}
			}
		}
		
		
	}
	
	public void visit(ConditionMultipleFact conditionMultipleFact){
		SyntaxNode parent = conditionMultipleFact.getParent();
		
		if(ifFlag){
			patchElseAdressesList = ifPatchElseStack.peek();
		}
		else {
			patchElseAdressesList = whilePatchEndStack.peek();
		}

		if(CondTermNum == 1){
			
			if(conditionMultipleFact.getCondFact() instanceof CondFactExpr){ //ako se stiglo do poslednjeg CondFact u jednom CondTerm i on je true skace se na then nezavisno od ostalih uslova jer je izmedju ||
				Code.loadConst(1);
				Code.putFalseJump(Code.eq, 0); //skok na kraj
				patchElseAdressesList.add(Code.pc-2);
			}
			else{
				Code.putFalseJump(returnRelationCode((CondFactExprRelopExpr)conditionMultipleFact.getCondFact()), 0); //skok na then
				patchElseAdressesList.add(Code.pc-2);
			}
				
		}
		else{
			if(ConditionSingleTerm.class == parent.getClass() || ConditionMultipleTerm.class == parent.getClass()){
				//poslednji expr
				if(conditionMultipleFact.getCondFact() instanceof CondFactExpr){ //ako se stiglo do poslednjeg CondFact u jednom CondTerm i on je true skace se na then nezavisno od ostalih uslova jer je izmedju ||
					Code.loadConst(1);
					Code.putFalseJump(Code.ne, 0); //skok na then
					patchThenAdressesList.add(Code.pc-2);
				}
				else{
					Code.putFalseJump(Code.inverse[returnRelationCode((CondFactExprRelopExpr)conditionMultipleFact.getCondFact())], 0); //skok na then
					patchThenAdressesList.add(Code.pc-2);
				}
			}
			else{ //ako nije poslednji CondFact i nije tacan onda se skace na sledeci condFact
				if(conditionMultipleFact.getCondFact() instanceof CondFactExpr){
					Code.loadConst(1);
					Code.putFalseJump(Code.eq, 0); //skok na sledeci condFact
					patchCondTermList.add(Code.pc-2);
				}
				else{
					Code.putFalseJump(returnRelationCode((CondFactExprRelopExpr)conditionMultipleFact.getCondFact()), 0); //sledeciCondFact
					patchCondTermList.add(Code.pc-2);
				}
			}
		}
		
		
	}
	
	
	public void visit(ConditionSingleTerm conditionSingleTerm){
		CondTermNum--;
		while(patchCondTermList.size() > 0){
			int addressToFixup = patchCondTermList.remove(patchCondTermList.size()-1);
			Code.fixup(addressToFixup);
		}
	}
	
	public void visit(ConditionMultipleTerm conditionMultipleTerm){
		CondTermNum--;
		while(patchCondTermList.size() > 0){
			int addressToFixup = patchCondTermList.remove(patchCondTermList.size()-1);
			Code.fixup(addressToFixup);
		}
	}
	
	public void visit(WhileInstructionFlag whileInstructionFlag){
		
		StatementWhile whileNode = (StatementWhile) whileInstructionFlag.getParent();
		Condition cond = whileNode.getCondition();
		
		CondTermCounter condCounter = new CondTermCounter();
		cond.traverseTopDown(condCounter);
		
		CondTermNum = condCounter.getCount();
		whileAddrStack.add(Code.pc);
		whilePatchEndStack.add(new ArrayList<>());
		ifFlag = false;
	}
	
	public void visit(IfStatementFlag ifStatementFlag){
		Condition cond;
		if(ifStatementFlag.getParent() instanceof IfStatementWithoutElse){
			cond = ((IfStatementWithoutElse) ifStatementFlag.getParent()).getCondition();
		} else {
			cond = ((IfElseStatement) ifStatementFlag.getParent()).getCondition();
		}
		
		CondTermCounter condCounter = new CondTermCounter();
		cond.traverseTopDown(condCounter);
		
		CondTermNum = condCounter.getCount();
		ifPatchElseStack.add(new ArrayList<>());
		ifFlag = true;
	}
	
	public void visit(WhileRightParenthesis whileRightParenthesis){
		while(patchThenAdressesList.size() > 0){
			int addressToFixup = patchThenAdressesList.remove(patchThenAdressesList.size()-1);
			Code.fixup(addressToFixup);
		}
	}
	
	public void visit(IfRightParenthesis ifRightParenthesis){
		while(patchThenAdressesList.size() > 0){
			int addressToFixup = patchThenAdressesList.remove(patchThenAdressesList.size()-1);
			Code.fixup(addressToFixup);
		}
	}
	
	
	public void visit(IfElsePart ifElsePart){
		Code.putJump(0); //skok na kraj elsa
		patchElseAdressesList = ifPatchElseStack.pop();
		while(patchElseAdressesList.size() > 0){
			int addressToFixup = patchElseAdressesList.remove(patchElseAdressesList.size()-1);
			Code.fixup(addressToFixup);
		}
		patchElseAdressesList = new ArrayList<>();
		patchElseAdressesList.add(Code.pc - 2);
		ifPatchElseStack.push(patchElseAdressesList);
	}
	
	public void visit(StatementWhile statementWhile){
		Code.putJump(whileAddrStack.pop());
		patchElseAdressesList = whilePatchEndStack.pop();
		while(patchElseAdressesList.size() > 0){
			int addressToFixup = patchElseAdressesList.remove(patchElseAdressesList.size()-1);
			Code.fixup(addressToFixup);
		}
	}
	
	public void visit(IfStatementWithoutElse ifStatementWithoutElse){
		patchElseAdressesList = ifPatchElseStack.pop();
		while(patchElseAdressesList.size() > 0){
			int addressToFixup = patchElseAdressesList.remove(patchElseAdressesList.size()-1);
			Code.fixup(addressToFixup);
		} 
	}
	
	public void visit(IfElseStatement ifElseStatement){
		patchElseAdressesList = ifPatchElseStack.pop();
		while(patchElseAdressesList.size() > 0){
			int addressToFixup = patchElseAdressesList.remove(patchElseAdressesList.size()-1);
			Code.fixup(addressToFixup);
		} 
	}
	
	public void visit(StatementBreak statementBreak){
		Code.putJump(0);
		whilePatchEndStack.peek().add(Code.pc-2);
	}
	
	public void visit(StatementContinue statementContinue){
		Code.putJump(whileAddrStack.peek());
	}
	
}
