package rs.ac.bg.etf.pp1;

import java.util.HashMap;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class SemanticAnalyzer extends VisitorAdaptor {

	int printCallCount = 0;
	int varDeclCount = 0;
	Obj currentObjectForTypeForVarsAndConsts = null;
	Obj currentMethod = null;
	HashMap<Integer, Struct> currentArguments = null;
	boolean returnFound = false;
	HashMap<String, HashMap<Integer, Struct>> allFunctions = new HashMap<>();
	// za svaku funkciju HashMap<Integer,Struct> arguments = new HashMap<>();
	int parsCounter = 0;
	int argsCounter = 0;
	int whileActive = 0;
	boolean mainFounded = false;
	boolean mainReturnTypeVoid = false;
	boolean errorDetected = false;
	int nVars;

	// TODO dodaj oporavak od greske za parser!!!
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	Logger log = Logger.getLogger(getClass());

	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getNameOfProg(), Tab.noType);
		Tab.openScope();
	}

	public void visit(Program program) {
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
		if (!mainFounded) {
			report_error("Greska: Semanticka greska, funkcija main ne postoji u programu!", null);
		}
		if(!mainReturnTypeVoid){
			report_error("Greska: Semanticka greska, funkcija main nije tipa void!", null);
		}
	}

	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola!", null);
			type.struct = Tab.noType;
		} else {
			if (typeNode.getKind() == Obj.Type) {
				// dohvaceni objekat Obj jeste objekat koji predstavlja tip
				currentObjectForTypeForVarsAndConsts = typeNode;
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
				type.struct = Tab.noType;
			}

		}
	}

	
	// promenljiva ne moze imati isto ime kao neka lokalna itd

	public boolean checkIfNameAlreadyExists(String name){
		Obj symbol = Tab.currentScope.findSymbol(name);
		if(symbol != null){
			return false;
		}
		else return true;
	}
	
	
	public void visit(SingleVarDecl singleVarDecl) {
		// dohvatamo neterminal koji se mapirao u polje klase SingleVarDecl
		TypesOfVar typesOfVar = singleVarDecl.getTypesOfVar();

		String name = null;
		Obj varNode = null;
		if (typesOfVar instanceof Identifier) {
			name = ((Identifier) typesOfVar).getVarName();
			
			if(!checkIfNameAlreadyExists(name)){
				report_error("Greska: Simbol sa imenom" + name + "je vec definisan u ovom opsegu!", singleVarDecl);
			}
			
			varNode = Tab.insert(Obj.Var, name, currentObjectForTypeForVarsAndConsts.getType());
		} else if (typesOfVar instanceof ArrayIdent) {
			name = ((ArrayIdent) typesOfVar).getVarName();
			Struct typeForArrayOfElems = new Struct(Struct.Array, currentObjectForTypeForVarsAndConsts.getType());
			
			if(!checkIfNameAlreadyExists(name)){
				report_error("Greska: Simbol sa imenom" + name + "je vec definisan u ovom opsegu!", singleVarDecl);
			}
			
			varNode = Tab.insert(Obj.Var, name, typeForArrayOfElems);

		} else {
			name = ((MatrixIdent) typesOfVar).getVarName();
			Struct typeForOneRowMatrix = new Struct(Struct.Array, currentObjectForTypeForVarsAndConsts.getType());
			Struct matrixType = new Struct(Struct.Array, typeForOneRowMatrix);
			if(!checkIfNameAlreadyExists(name)){
				report_error("Greska: Simbol sa imenom" + name + "je vec definisan u ovom opsegu!", singleVarDecl);
			}
			varNode = Tab.insert(Obj.Var, name, matrixType);
		}

		varDeclCount++;
		report_info("Deklarisana promenljiva " + name, singleVarDecl);
	}

	public void visit(MultipleVarDecl multipleVarDecl) {
		// dohvatamo neterminal koji se mapirao u polje klase SingleVarDecl
		TypesOfVar typesOfVar = multipleVarDecl.getTypesOfVar();

		String name = null;
		Obj varNode = null;
		if (typesOfVar instanceof Identifier) {
			name = ((Identifier) typesOfVar).getVarName();
			if(!checkIfNameAlreadyExists(name)){
				report_error("Greska: Simbol sa imenom" + name + "je vec definisan u ovom opsegu!", multipleVarDecl);
			}
			varNode = Tab.insert(Obj.Var, name, currentObjectForTypeForVarsAndConsts.getType());
		} else if (typesOfVar instanceof ArrayIdent) {
			name = ((ArrayIdent) typesOfVar).getVarName();
			Struct typeForArrayOfElems = new Struct(Struct.Array, currentObjectForTypeForVarsAndConsts.getType());
			if(!checkIfNameAlreadyExists(name)){
				report_error("Greska: Simbol sa imenom" + name + "je vec definisan u ovom opsegu!", multipleVarDecl);
			}
			varNode = Tab.insert(Obj.Var, name, typeForArrayOfElems);

		} else {
			name = ((MatrixIdent) typesOfVar).getVarName();
			Struct typeForOneRowMatrix = new Struct(Struct.Array, currentObjectForTypeForVarsAndConsts.getType());
			Struct matrixType = new Struct(Struct.Array, typeForOneRowMatrix);
			if(!checkIfNameAlreadyExists(name)){
				report_error("Greska: Simbol sa imenom" + name + "je vec definisan u ovom opsegu!", multipleVarDecl);
			}
			varNode = Tab.insert(Obj.Var, name, matrixType);
		}

		varDeclCount++;
		report_info("Deklarisana promenljiva " + name, multipleVarDecl);
	}

	// Obezbedjuje da prilikom definisanja konstanti izraz bude isti kao tip
	// konstante
	public void visit(SingleConstantDecl singleConstantDecl) {

		Constants constants = singleConstantDecl.getConstants();
		Obj varNode;
		String name = singleConstantDecl.getConstName();

		if ((constants instanceof ConstantsNum) && currentObjectForTypeForVarsAndConsts.getType().getKind() == Struct.Int) {
			if(!checkIfNameAlreadyExists(name)){
				report_error("Greska: Simbol sa imenom" + name + "je vec definisan u ovom opsegu!", singleConstantDecl);
			}
			varNode = Tab.insert(Obj.Con, name, Tab.intType);
			varNode.setAdr(((ConstantsNum) constants).getNumber());
			report_info("Definisana konstanta " + name + " sa vrenoscu " + varNode.getAdr(), singleConstantDecl);
		} else if (constants instanceof ConstantsChar && currentObjectForTypeForVarsAndConsts.getType().getKind() == Struct.Char) {
			if(!checkIfNameAlreadyExists(name)){
				report_error("Greska: Simbol sa imenom " + name + " je vec definisan u ovom opsegu!", singleConstantDecl);
			}
			varNode = Tab.insert(Obj.Con, name, Tab.charType);
			varNode.setAdr(((ConstantsChar) constants).getCharacter());
			report_info("Definisana konstanta " + name + " sa vrenoscu " + varNode.getAdr(), singleConstantDecl);
		} else if (constants instanceof ConstantsBool && currentObjectForTypeForVarsAndConsts.getType().getKind() == Struct.Bool) {
			if(!checkIfNameAlreadyExists(name)){
				report_error("Greska: Simbol sa imenom " + name + " je vec definisan u ovom opsegu!", singleConstantDecl);
			}
			varNode = Tab.insert(Obj.Con, name, Compiler.boolType);

			if (((ConstantsBool) constants).getBoolean()) {
				varNode.setAdr(1);
			} else
				varNode.setAdr(0);
			report_info("Definisana konstanta " + name + " sa vrenoscu " + varNode.getAdr(), singleConstantDecl);
		} else {
			report_error("Greska: Dodeljeni izraz konstanti nije odgovarajuceg tipa kao konstanta", singleConstantDecl);
		}

	}

	public void visit(MultipleConstantDecl multipleConstantDecl) {

		Constants constants = multipleConstantDecl.getConstants();
		Obj varNode;
		String name = multipleConstantDecl.getConstName();

		if ((constants instanceof ConstantsNum) && currentObjectForTypeForVarsAndConsts.getType().getKind() == Struct.Int) {
			if(!checkIfNameAlreadyExists(name)){
				report_error("Greska: Simbol sa imenom " + name + " je vec definisan u ovom opsegu!", multipleConstantDecl);
			}
			varNode = Tab.insert(Obj.Con, name, Tab.intType);
			varNode.setAdr(((ConstantsNum) constants).getNumber());
			report_info("Definisana konstanta " + name + " sa vrenoscu " + varNode.getAdr(), multipleConstantDecl);
		} else if (constants instanceof ConstantsChar && currentObjectForTypeForVarsAndConsts.getType().getKind() == Struct.Char) {
			if(!checkIfNameAlreadyExists(name)){
				report_error("Greska: Simbol sa imenom " + name + " je vec definisan u ovom opsegu!", multipleConstantDecl);
			}
			varNode = Tab.insert(Obj.Con, name, Tab.charType);
			varNode.setAdr(((ConstantsChar) constants).getCharacter());
			report_info("Definisana konstanta " + name + " sa vrenoscu " + varNode.getAdr(), multipleConstantDecl);
		} else if (constants instanceof ConstantsBool && currentObjectForTypeForVarsAndConsts.getType().getKind() == Struct.Bool) {
			if(!checkIfNameAlreadyExists(name)){
				report_error("Greska: Simbol sa imenom " + name + " je vec definisan u ovom opsegu!", multipleConstantDecl);
			}
			varNode = Tab.insert(Obj.Con, name, Compiler.boolType);

			if (((ConstantsBool) constants).getBoolean()) {
				varNode.setAdr(1);
			} else
				varNode.setAdr(0);
			report_info("Definisana konstanta " + name + " sa vrenoscu " + varNode.getAdr(), multipleConstantDecl);
		} else {
			report_error("Greska: Dodeljeni izraz konstanti nije odgovarajuceg tipa kao konstanta", multipleConstantDecl);
		}

	}

	public void visit(MethodTypeName methodTypeName) {
		ReturnType returnType = methodTypeName.getReturnType();
		String name = methodTypeName.getRealMethodName();

		if ("main".equalsIgnoreCase(name)) {
			mainFounded = true;
			if(methodTypeName.getReturnType() instanceof ReturnTypeVoid){
				mainReturnTypeVoid = true;
			}
		}

		if (returnType instanceof ReturnTypeType) {
			Struct structForTypeForReturnedVar = ((ReturnTypeType) returnType).getType().struct;
			// ima povratnu vrednost koja je neki tip
			currentMethod = Tab.insert(Obj.Meth, name, structForTypeForReturnedVar);

		} else if (returnType instanceof ReturnTypeVoid) {
			//
			currentMethod = Tab.insert(Obj.Meth, name, Tab.noType);
		}

		parsCounter = 0;
		/* Pravimo HashMapu za parametre */
		HashMap<Integer, Struct> arguments = new HashMap<>();
		allFunctions.put(name, arguments);

		methodTypeName.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + methodTypeName.getRealMethodName(), methodTypeName);

	}

	public void visit(MethodDecl methodDecl) {

		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Greska: Semanticka greska, ne postoji return iskaz za funkciju" + methodDecl.getMethodTypeName().getRealMethodName(), methodDecl);
		}

		Tab.chainLocalSymbols(currentMethod);
		currentMethod.setLevel(parsCounter);
		Tab.closeScope();
		returnFound = false;
		currentMethod = null;
	}

	public void visit(StatementReturnExpr statementReturnExpr) {
		if (currentMethod != null) {
			returnFound = true;
			Expr expr = statementReturnExpr.getExpr();
			if (!(expr.struct.assignableTo(currentMethod.getType()))) {
				report_error("Greska: Tip izraza u return naredbi nije odgovarajuci", statementReturnExpr);
			}
		} else {
			report_error("Greska: Return se ne moze naci van funkcije", statementReturnExpr);
		}
	}

	public void visit(StatementReturn statementReturn) {
		if (currentMethod != null) {
			if (currentMethod.getType() != Tab.noType) {
				report_error("Greska: Return bez vrednosti se pojavilo u metodi koja ima povratnu vrednost", statementReturn);
			}
		} else {
			report_error("Greska: Return se ne moze naci van funkcije", statementReturn);
		}
	}

	public void visit(DesignatorStatementIncrement designatorStatementIncrement) {
		Designator designator = designatorStatementIncrement.getDesignator();
		String name;
		Obj objWithName;

		if (designator instanceof DesignatorIdent) {
			name = ((DesignatorIdent) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", designatorStatementIncrement);
			}

			if (objWithName.getKind() == Obj.Var /*
												 * &&
												 * objWithName.getType().getKind
												 * () != Struct.Array
												 */) {

				if (!(objWithName.getType().assignableTo(Tab.intType))) {
					report_error("Greska: Semanticka greska, objekat sa imenom " + name + " jeste promenljiva, ali nije odgovarajuceg tipa koriscenog u izrazu, promenljiva koja se inkrementira mora biti tipa int", designatorStatementIncrement);
				}
			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao promenljiva, a nije tipa promenljive", designatorStatementIncrement);
			}

		} else if (designator instanceof DesignatorArrayElement) {
			name = ((DesignatorArrayElement) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", designatorStatementIncrement);
			}

			if (objWithName.getKind() == Obj.Var && objWithName.getType().getKind() == Struct.Array && objWithName.getType().getElemType().getKind() != Struct.Array && objWithName.getType().getElemType().assignableTo(Tab.intType)) {
				// Moramo da vidimo da li je element niza
				if ((((DesignatorArrayElement) designator).getExpr().struct.assignableTo(Tab.intType))) {
					report_info("Pristup elementu niza " + name, designatorStatementIncrement);
				} else {
					report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao element niza sa tipom integer u izrazu u kombinaciji sa operatorom inkrementiranja, a ne predstavlja niz", designatorStatementIncrement);
				}

			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao element niza sa tipom integer u izrazu u kombinaciji sa operatorom inkrementiranja, a ne predstavlja niz", designatorStatementIncrement);
			}
		}

		else if (designator instanceof DesignatorMatrixElement) {
			name = ((DesignatorMatrixElement) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", designatorStatementIncrement);
			}
			if (objWithName.getKind() == Obj.Var && objWithName.getType().getKind() == Struct.Array && objWithName.getType().getElemType().getKind() == Struct.Array) {
				if (((DesignatorMatrixElement) designator).getExpr().struct.assignableTo(Tab.intType) && ((DesignatorMatrixElement) designator).getExpr1().struct.assignableTo(Tab.intType)) {

				} else {
					report_error("Greska: Semanticka greska, objekat sa imenom " + name + "nije kompaktibilan sa celobrojnim tipom", designatorStatementIncrement);
				}
			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao matrica a ne predstavlja matricu", designatorStatementIncrement);
			}
		}

	}

	public void visit(DesignatorStatementDecrement designatorStatementDecrement) {
		Designator designator = designatorStatementDecrement.getDesignator();
		String name;
		Obj objWithName;

		if (designator instanceof DesignatorIdent) {
			name = ((DesignatorIdent) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", designatorStatementDecrement);
			}

			if (objWithName.getKind() == Obj.Var /*
												 * &&
												 * objWithName.getType().getKind
												 * () != Struct.Array
												 */) {

				if (!(objWithName.getType().assignableTo(Tab.intType))) {
					report_error("Greska: Semanticka greska, objekat sa imenom " + name + " jeste promenljiva, ali nije odgovarajuceg tipa koriscenog u izrazu, promenljiva koja se inkrementira mora biti tipa int", designatorStatementDecrement);
				}
			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao promenljiva, a nije tipa promenljive", designatorStatementDecrement);
			}

		} else if (designator instanceof DesignatorArrayElement) {
			name = ((DesignatorArrayElement) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", designatorStatementDecrement);
			}

			if (objWithName.getKind() == Obj.Var && objWithName.getType().getKind() == Struct.Array && objWithName.getType().getElemType().getKind() != Struct.Array && objWithName.getType().getElemType().assignableTo(Tab.intType)) {
				// Moramo da vidimo da li je element niza
				if ((((DesignatorArrayElement) designator).getExpr().struct.assignableTo(Tab.intType))) {
					report_info("Pristup elementu niza " + name, designatorStatementDecrement);
				} else {
					report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao element niza sa tipom integer u izrazu u kombinaciji sa operatorom inkrementiranja, a ne predstavlja niz", designatorStatementDecrement);
				}

			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao element niza sa tipom integer u izrazu u kombinaciji sa operatorom inkrementiranja, a ne predstavlja niz", designatorStatementDecrement);
			}
		}

		else if (designator instanceof DesignatorMatrixElement) {
			name = ((DesignatorMatrixElement) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", designatorStatementDecrement);
			}
			if (objWithName.getKind() == Obj.Var && objWithName.getType().getKind() == Struct.Array && objWithName.getType().getElemType().getKind() == Struct.Array) {
				if (((DesignatorMatrixElement) designator).getExpr().struct.assignableTo(Tab.intType) && ((DesignatorMatrixElement) designator).getExpr1().struct.assignableTo(Tab.intType)) {

				} else {
					report_error("Greska: Semanticka greska, objekat sa imenom " + name + "nije kompaktibilan sa celobrojnim tipom", designatorStatementDecrement);
				}
			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao matrica a ne predstavlja matricu", designatorStatementDecrement);
			}
		}

	}

	public void visit(DesignatorStatementAssignment designatorStatementAssignment) {
		Designator designator = designatorStatementAssignment.getDesignator();
		String name;
		Obj objWithName;
		Expr expr = designatorStatementAssignment.getExpr();

		if (designator instanceof DesignatorIdent) {
			name = ((DesignatorIdent) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", designatorStatementAssignment);
			}

			if (objWithName.getKind() == Obj.Var) {

				if (!(objWithName.getType().assignableTo(expr.struct))) {
					report_error("Greska: Semanticka greska, objekat sa imenom " + name + " jeste promenljiva, ali u izrazu Tip leve strane nije odgovarajuci sa tipom desne strane jednakosti", designatorStatementAssignment);
				}
			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " jeste promenljiva, ali u izrazu Tip leve strane nije odgovarajuci sa tipom desne strane jednakosti", designatorStatementAssignment);
			}

		} else if (designator instanceof DesignatorArrayElement) {
			name = ((DesignatorArrayElement) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", designatorStatementAssignment);
			}

			if (objWithName.getKind() == Obj.Var && objWithName.getType().getKind() == Struct.Array && objWithName.getType().getElemType().assignableTo(expr.struct)) {
				// uspeh
			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " ili nije promenljiva, element niza, element matrice, ili u izrazu Tip leve strane nije odgovarajuci sa tipom desne strane jednakosti", designatorStatementAssignment);
			}
		}

		else if (designator instanceof DesignatorMatrixElement) {

			name = ((DesignatorMatrixElement) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", designatorStatementAssignment);
			}
			if (objWithName.getKind() == Obj.Var && objWithName.getType().getKind() == Struct.Array && objWithName.getType().getElemType().getKind() == Struct.Array) {
				if (expr.struct.assignableTo(objWithName.getType().getElemType().getElemType())) {
					// uspeh
				} else {
					report_error("Greska: Semanticka greska, objekat sa imenom " + name + " jeste promenljiva, ali u izrazu Tip leve strane nije odgovarajuci sa tipom desne strane jednakosti", designatorStatementAssignment);
				}
			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " nije tipa nizovske promenljive koja se ocekuje u izrazu", designatorStatementAssignment);
			}
		}
	}

	public void visit(DesignatorStatementFunctionCall designatorStatementFunctionCall) {
		Designator designator = designatorStatementFunctionCall.getDesignator();
		String name;
		Obj objWithName;

		if (designator instanceof DesignatorIdent) {
			name = ((DesignatorIdent) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "nije funkcija!", designatorStatementFunctionCall);
			}
			if (objWithName.getKind() == Obj.Meth) {
				int numOfParameters = 0;
				int numOfArguments = 0;

				// Ako jeste tip metoda
				if (name.contentEquals("ord")) {
					if (currentArguments == null || currentArguments.size() != 1 || (!(currentArguments.get(1).assignableTo(Tab.charType)))) {
						report_error("Greska: Semanticka greska, neodgovarajuci argumenti prilikom poziva funkcije " + name + " ili nije prosledjen dovoljan broj parametara, ili je prosledjeno previse parametara, ili parametar nije tipa char", designatorStatementFunctionCall);
					}
				} else if (name.contentEquals("chr")) {
					if (currentArguments == null || (currentArguments.size() != 1) || (!(currentArguments.get(1).assignableTo(Tab.intType)))) {
						report_error("Greska: Semanticka greska, neodgovarajuci argumenti prilikom poziva funkcije " + name + " ili nije prosledjen dovoljan broj parametara, ili je prosledjeno previse parametara, ili parametar nije izraz koji rezultira celobrojnom vrednoscu",
								designatorStatementFunctionCall);
					}
				} else if (name.contentEquals("len")) {
					if ((currentArguments == null) || (currentArguments.size() != 1) || (!(currentArguments.get(1).getKind() == Struct.Array))) {
						report_error("Greska: Semanticka greska, neodgovarajuci argumenti prilikom poziva funkcije " + name + "ili nije prosledjen dovoljan broj parametara, ili je prosledjeno previse parametara, ili parametar nije tipa niz ili znakovni niz", designatorStatementFunctionCall);
					}

				} else {

					if (currentArguments != null) {
						numOfArguments = currentArguments.size();
					} else {
						numOfArguments = 0;
					}

					if (allFunctions.get(name) != null) {
						numOfParameters = allFunctions.get(name).size();
					} else {
						numOfParameters = 0;
					}

					if (numOfParameters != numOfArguments) {
						report_error("Greska: Semanticka greska neodgovarajuci broj prosledjenih argumenata za funkciju!" + name, designatorStatementFunctionCall);
					} else {
						if (argsCounter > 0) {
							// ovoga dole
							for (int i = 1; i <= currentArguments.size(); i++) {
								if (!(((allFunctions.get(name).get(i)).assignableTo(currentArguments.get(i))))) {
									report_error("Greska: Semanticka greska, neodgovarajuci argumenti prilikom poziva funkcije " + name + " neslaganje izmedju tipova " + allFunctions.get(name).get(i).getKind() + " i " + currentArguments.get(i).getKind(), designatorStatementFunctionCall);
								}
							}
						}
					}

				}

				currentArguments = null;
				argsCounter = 0;

			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao tip funkcije, a nije tipa funkcije", designatorStatementFunctionCall);

			}
		} else {
			report_error("Greska: Semanticka greska, objekat nije funkcija, a koristi se na mestu gde se zahteva identifikator funkcije!", designatorStatementFunctionCall);
		}
	}

	public void visit(FactorDesignator factorDesignator) {
		Designator designator = factorDesignator.getDesignator();
		String name;
		Obj objWithName;

		if (designator instanceof DesignatorIdent) {
			name = ((DesignatorIdent) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi!", factorDesignator);
				factorDesignator.struct = Tab.noType;
			}
			if (objWithName.getKind() == Obj.Var || objWithName.getKind() == Obj.Con) {
				// Ako jeste sve okej
				factorDesignator.struct = objWithName.getType();
			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao promenljiva, a nije tipa promenljive", factorDesignator);
				factorDesignator.struct = Tab.noType;
			}

		} else if (designator instanceof DesignatorArrayElement) {
			name = ((DesignatorArrayElement) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi kao niz", factorDesignator);
				factorDesignator.struct = Tab.noType;
			}

			if (objWithName.getKind() == Obj.Var && objWithName.getType().getKind() == Struct.Array) {
				factorDesignator.struct = objWithName.getType().getElemType();
			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao element niza a ne predstavlja niz", factorDesignator);
				factorDesignator.struct = Tab.noType;
			}
		}

		else if (designator instanceof DesignatorMatrixElement) {
			name = ((DesignatorMatrixElement) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", factorDesignator);
				factorDesignator.struct = Tab.noType;
			}

			if (objWithName.getKind() == Obj.Var && objWithName.getType().getKind() == Struct.Array && objWithName.getType().getElemType().getKind() == Struct.Array) {
				factorDesignator.struct = objWithName.getType().getElemType().getElemType();
			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao matrica a ne predstavlja matricu", factorDesignator);
				factorDesignator.struct = Tab.noType;
			}

		}

	}

	public void visit(FactorFunctionCall factorFunctionCall) {
		Designator designator = factorFunctionCall.getDesignator();
		String name;
		Obj objWithName;

		if (designator instanceof DesignatorIdent) {
			name = ((DesignatorIdent) designator).getMyObj().getName();
			objWithName = Tab.find(name);
			if (objWithName == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "nije funkcija!", factorFunctionCall);
				factorFunctionCall.struct = Tab.noType;
			}
			if (objWithName.getKind() == Obj.Meth) {
				int numOfParameters = 0;
				int numOfArguments = 0;

				// Ako jeste tip metoda
				if (name.contentEquals("ord")) {
					if (currentArguments == null || currentArguments.size() != 1 || (!(currentArguments.get(1).assignableTo(Tab.charType)))) {
						report_error("Greska: Semanticka greska, neodgovarajuci argumenti prilikom poziva funkcije " + name + " ili nije prosledjen dovoljan broj parametara, ili je prosledjeno previse parametara, ili parametar nije tipa char", factorFunctionCall);
					}
				} else if (name.contentEquals("chr")) {
					if (currentArguments == null || (currentArguments.size() != 1) || (!(currentArguments.get(1).assignableTo(Tab.intType)))) {
						report_error("Greska: Semanticka greska, neodgovarajuci argumenti prilikom poziva funkcije " + name + " ili nije prosledjen dovoljan broj parametara, ili je prosledjeno previse parametara, ili parametar nije izraz koji rezultira celobrojnom vrednoscu", factorFunctionCall);
					}
				} else if (name.contentEquals("len")) {
					if ((currentArguments == null) || (currentArguments.size() != 1) || (!(currentArguments.get(1).getKind() == Struct.Array))) {
						report_error("Greska: Semanticka greska, neodgovarajuci argumenti prilikom poziva funkcije " + name + "ili nije prosledjen dovoljan broj parametara, ili je prosledjeno previse parametara, ili parametar nije tipa niz ili znakovni niz", factorFunctionCall);
					}

				} else {

					if (currentArguments != null) {
						numOfArguments = currentArguments.size();
					} else {
						numOfArguments = 0;
					}

					if (allFunctions.get(name) != null) {
						numOfParameters = allFunctions.get(name).size();
					} else {
						numOfParameters = 0;
					}

					if (numOfParameters != numOfArguments) {
						report_error("Greska: Semanticka greska neodgovarajuci broj prosledjenih argumenata za funkciju!" + name, factorFunctionCall);
					} else {
						if (argsCounter > 0) {
							// ovoga dole
							for (int i = 1; i <= currentArguments.size(); i++) {
								if (!(((allFunctions.get(name).get(i)).assignableTo(currentArguments.get(i))))) {
									report_error("Greska: Semanticka greska, neodgovarajuci argumenti prilikom poziva funkcije " + name + " neslaganje izmedju tipova " + allFunctions.get(name).get(i).getKind() + " i " + currentArguments.get(i).getKind(), factorFunctionCall);
								}
							}
						}
					}

				}

				currentArguments = null;
				argsCounter = 0;

				factorFunctionCall.struct = objWithName.getType();
			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao tip funkcije, a nije tipa funkcije", factorFunctionCall);
				factorFunctionCall.struct = Tab.noType;
			}
		} else {
			report_error("Greska: Semanticka greska, objekat nije funkcija, a koristi se na mestu gde se zahteva identifikator funkcije!", factorFunctionCall);
			factorFunctionCall.struct = Tab.noType;
		}
	}

	public void visit(FactorConstants factorConstants) {
		Constants constants = factorConstants.getConstants();
		if (constants instanceof ConstantsNum) {
			factorConstants.struct = Tab.intType;
		} else if (constants instanceof ConstantsChar) {
			factorConstants.struct = Tab.charType;
		} else if (constants instanceof ConstantsBool) {
			factorConstants.struct = Compiler.boolType;
		}
	}

	public void visit(FactorNewArray factorNewArray) {
		if (!(factorNewArray.getExpr().struct.assignableTo(Tab.intType))) {
			report_error("Greska: Semanticka greska  izraz nije odgovarajuc sa tipom int", factorNewArray);
			factorNewArray.struct = Tab.noType;
		} else {
			Struct newArrayType = new Struct(Struct.Array, factorNewArray.getType().struct);
			factorNewArray.struct = newArrayType;
		}
	}

	public void visit(FactorCalculation factorCalculation) {
		factorCalculation.struct = factorCalculation.getExpr().struct;
	}

	public void visit(FactorNewMatrix factorNewMatrix) {
		if (!(factorNewMatrix.getExpr().struct.assignableTo(Tab.intType) && factorNewMatrix.getExpr1().struct.assignableTo(Tab.intType))) {
			factorNewMatrix.struct = Tab.noType;
			report_error("Greska: Semanticka greska  izraz nije odgovarajuc sa tipom int", factorNewMatrix);
		} else {

			Struct newArrayTypeForOneMatrixRow = new Struct(Struct.Array, factorNewMatrix.getType().struct);
			Struct newMatrixType = new Struct(Struct.Array, newArrayTypeForOneMatrixRow);

			factorNewMatrix.struct = newMatrixType;
		}
	}

	public void visit(TermFactor termFactor) {
		termFactor.struct = termFactor.getFactor().struct;
	}

	public void visit(TermMulopFactor termMulopFactor) {
		if (termMulopFactor.getTerm().struct.assignableTo(Tab.intType) && termMulopFactor.getFactor().struct.assignableTo(Tab.intType)) {
			termMulopFactor.struct = Tab.intType;
		} else {
			report_error("Greska: Semanticka greska izracunati izraz mora biti celobrojnog tipa!", termMulopFactor);
			termMulopFactor.struct = Tab.noType;
		}
	}

	public void visit(ExprMinusTerm exprMinusTerm) {
		if (exprMinusTerm.getTerm().struct.assignableTo(Tab.intType)) {
			exprMinusTerm.struct = Tab.intType;
		} else {
			report_error("Greska: Semanticka greska izracunati izraz mora biti celobrojnog tipa!", exprMinusTerm);
			exprMinusTerm.struct = Tab.noType;
		}
	}

	public void visit(ExprTerm exprTerm) {
		exprTerm.struct = exprTerm.getTerm().struct;
	}

	public void visit(ExprAddopTerm exprAddopTerm) {
		if (exprAddopTerm.getExpr().struct.assignableTo(Tab.intType) && exprAddopTerm.getTerm().struct.assignableTo(Tab.intType)) {
			exprAddopTerm.struct = Tab.intType;
		} else {
			exprAddopTerm.struct = Tab.noType;
			report_error("Greska: Semanticka greska izracunati izraz mora biti celobrojnog tipa!", exprAddopTerm);
		}
	}

	public void visit(CondFactExprRelopExpr condFactExprRelopExpr) {
		Expr expr = condFactExprRelopExpr.getExpr();
		Expr expr1 = condFactExprRelopExpr.getExpr1();
		Relop relop = condFactExprRelopExpr.getRelop();

		if (expr.struct.assignableTo(expr1.struct)) {
			if (expr.struct.getKind() == Struct.Array) { // ako je niz ili
															// matrica
				if ((relop instanceof RelopEqual) || (relop instanceof RelopNotEqual)) {
				} else {
					report_error("Greska: Uz nizovske tipove nije dozovljen ovaj tip operatora!", condFactExprRelopExpr);
				}
			}
			condFactExprRelopExpr.struct = Compiler.boolType;
		} else {
			report_error("Greska: Tipovi izraza nisu kompaktibilni", condFactExprRelopExpr);
		}
	}

	public void visit(CondFactExpr condFactExpr) {
		if (condFactExpr.getExpr().struct.assignableTo(Compiler.boolType)) {
			condFactExpr.struct = Compiler.boolType;
		}

	}

	public void visit(CondTermSingleFact condTermSingleFact) {
		condTermSingleFact.struct = condTermSingleFact.getCondFact().struct;
	}

	public void visit(ConditionMultipleFact conditionMultipleFact) {
		conditionMultipleFact.struct = conditionMultipleFact.getCondFact().struct;
	}

	public void visit(ConditionSingleTerm conditionSingleTerm) {
		if (conditionSingleTerm.getCondTerm().struct.assignableTo(Compiler.boolType)) {
		} else {
			report_error("Greska: Tip izraza condition mora biti boolean", conditionSingleTerm);
		}
	}

	public void visit(ConditionMultipleTerm conditionMultipleTerm) {
		if (conditionMultipleTerm.getCondTerm().struct.assignableTo(Compiler.boolType)) {

		} else {
			report_error("Greska: Tip izraza condition mora biti boolean", conditionMultipleTerm);
		}
	}

	public void visit(SingleFormParameter singleFormParameter) {
		TypesOfVar typesOfVar = singleFormParameter.getTypesOfVar();
		if (typesOfVar instanceof Identifier) {
			allFunctions.get(currentMethod.getName()).put(++parsCounter, singleFormParameter.getType().struct);
			Tab.insert(Obj.Var, ((Identifier) typesOfVar).getVarName(), singleFormParameter.getType().struct);
		} else if (typesOfVar instanceof ArrayIdent) {
			Struct arrayType = new Struct(Struct.Array, singleFormParameter.getType().struct);
			allFunctions.get(currentMethod.getName()).put(++parsCounter, arrayType);
			Tab.insert(Obj.Var, ((ArrayIdent) typesOfVar).getVarName(), arrayType);
		} else if (typesOfVar instanceof MatrixIdent) {
			Struct arrayType = new Struct(Struct.Array, singleFormParameter.getType().struct);
			Struct matrixType = new Struct(Struct.Array, arrayType);
			allFunctions.get(currentMethod.getName()).put(++parsCounter, matrixType);
			Tab.insert(Obj.Var, ((MatrixIdent) typesOfVar).getVarName(), matrixType);
		}

	}

	public void visit(MultipleFormParameter multipleFormParameter) {
		TypesOfVar typesOfVar = multipleFormParameter.getTypesOfVar();
		if (typesOfVar instanceof Identifier) {
			allFunctions.get(currentMethod.getName()).put(++parsCounter, multipleFormParameter.getType().struct);
			Tab.insert(Obj.Var, ((Identifier) typesOfVar).getVarName(), multipleFormParameter.getType().struct);
		} else if (typesOfVar instanceof ArrayIdent) {
			Struct arrayType = new Struct(Struct.Array, multipleFormParameter.getType().struct);
			allFunctions.get(currentMethod.getName()).put(++parsCounter, arrayType);
			Tab.insert(Obj.Var, ((ArrayIdent) typesOfVar).getVarName(), arrayType);
		} else if (typesOfVar instanceof MatrixIdent) {
			Struct arrayType = new Struct(Struct.Array, multipleFormParameter.getType().struct);
			Struct matrixType = new Struct(Struct.Array, arrayType);
			allFunctions.get(currentMethod.getName()).put(++parsCounter, matrixType);
			Tab.insert(Obj.Var, ((MatrixIdent) typesOfVar).getVarName(), matrixType);
		}

	}

	public void visit(SingleActParsExpr singleActParsExpr) {
		if (currentArguments == null) {
			currentArguments = new HashMap<>(); // hashmapa za argumente
												// funkcije koja se trenutno
												// poziva
		}
		currentArguments.put(++argsCounter, singleActParsExpr.getExpr().struct);
	}

	public void visit(MultipleActParsExpr multipleActParsExpr) {
		if (currentArguments == null) {
			currentArguments = new HashMap<>();
		}
		currentArguments.put(++argsCounter, multipleActParsExpr.getExpr().struct);
	}	
	
	public void visit(DesignatorStatementMap designatorStatementMap) {
		Designator designator = designatorStatementMap.getDesignator();
		Designator designator1 = designatorStatementMap.getDesignator1();
		Expr expr = designatorStatementMap.getExpr();
		String globalOrLocalVar = designatorStatementMap.getMapVariable().getGlobalOrLocalVar();
		String resultArrayName = null;

		Obj variable = Tab.find(globalOrLocalVar);
		if (variable == Tab.noObj) {
			report_error("Greska: Promenljiva ident nije pronadjena u tabeli simbola", designatorStatementMap);
		} else {

			if (designator instanceof DesignatorIdent) {
				resultArrayName = ((DesignatorIdent) designator).getMyObj().getName();
			} else if (designator instanceof DesignatorArrayElement) {
				resultArrayName = ((DesignatorArrayElement) designator).getMyObj().getName();
			} else if (designator instanceof DesignatorMatrixElement) {
				resultArrayName = ((DesignatorMatrixElement) designator).getMyObj().getName();
			}

			Obj resArr = Tab.find(resultArrayName);
			if (resArr == Tab.noObj) {
				report_error("Greska: Promenljiva ident nije pronadjena u tabeli simbola", designatorStatementMap);
			} else {

				// jednodimenzioni niz
				if (designator.struct.getKind() == Struct.Array && designator.struct.getElemType().getKind() != Struct.Array && designator1.struct.getKind() == Struct.Array && designator1.struct.getElemType().getKind() != Struct.Array
						&& designator.struct.getElemType().assignableTo(designator1.struct.getElemType())) {
					if (designator.struct.getElemType().assignableTo(variable.getType()) && designator.struct.getElemType().assignableTo(expr.struct)) {
						designatorStatementMap.getMapVariable().obj = variable;
					} else {
						report_error("Greska: Tipovi niza i promenljive za iteriranje se razlikuju", designatorStatementMap);
					}
				} else {
					report_error("Greska: Designator nije jednodimenzioni niz", designatorStatementMap);
				}
			}
		}
	}

	public void visit(WhileInstructionFlag whileInstructionFlag) {
		whileActive++;
	}

	public void visit(StatementWhile statementWhile) {
		whileActive--;
	}

	public void visit(StatementBreak statementBreak) {
		if (whileActive <= 0) { // nismo ni u jednom breaku
			report_error("Greska: Break je pozvan van while metode", statementBreak);
			// TODO da li treba da stavim ovde whileActive = false, u tom
			// slucaju bi moralo da bude counter da bi brojalo ugnezdavanja; da
			// li je ovako ili da li treba da setuje flag kad se desi break
		}
	}

	public void visit(StatementContinue statementContinue) {
		if (whileActive <= 0) {
			report_error("Greska: Continue je pozvan van while metode", statementContinue);
		}
	}

	public void visit(StatementPrintWithNum statementPrintWithNum) {
		if (statementPrintWithNum.getExpr().struct.assignableTo(Tab.intType) || statementPrintWithNum.getExpr().struct.assignableTo(Tab.charType) || statementPrintWithNum.getExpr().struct.assignableTo(Compiler.boolType)) {
			// svefull
		} else {
			report_error("Greska: Tip " + statementPrintWithNum.getExpr().struct + " nije dozvoljen u print izrazima", statementPrintWithNum);
		}
	}

	public void visit(StatementPrint statementPrint) {
		if (statementPrint.getExpr().struct.assignableTo(Tab.intType) || statementPrint.getExpr().struct.assignableTo(Tab.charType) || statementPrint.getExpr().struct.assignableTo(Compiler.boolType)) {
			// svefull
		} else {
			report_error("Greska: Tip " + statementPrint.getExpr().struct + " nije dozvoljen u print izrazima", statementPrint);
		}
		printCallCount++;
	}

	public void visit(DesignatorIdent designatorIdent) {
		String name = designatorIdent.getMyObj().getName();
		Obj objWithName = Tab.find(name);
		designatorIdent.getMyObj().obj = objWithName;
		if (objWithName == Tab.noObj) {
			report_error("Greska: DESIGNATOR_IDENT: Identifikator koji koristite ne postoji u tabeli simbola", designatorIdent);
		}
		designatorIdent.struct = objWithName.getType();
		// ovo ne sme ako je ime funkcije! Za ime funkcije mora da ostane
		// onakokao malopre
	}

	
	public void visit(DesignatorArrayElement designatorArrayElement) {
		String name = designatorArrayElement.getMyObj().getName();
		Obj objWithName = Tab.find(name);
		designatorArrayElement.getMyObj().obj = objWithName;
		if (objWithName == Tab.noObj) {
			report_error("Greska: DESIGNATOR_IDENT: Identifikator koji koristite ne postoji u tabeli simbola", designatorArrayElement);
		}
		if (objWithName.getType().getKind() == Struct.Array && designatorArrayElement.getExpr().struct.assignableTo(Tab.intType)) {
			designatorArrayElement.struct = objWithName.getType().getElemType();
		} else {
			report_error("Greska: Pogresno prosledjen identifikator, ocekivan je niz", designatorArrayElement);
			designatorArrayElement.struct = Tab.noType;
		}
	}

	public void visit(DesignatorMatrixElement designatorMatrixElement) {
		String name = designatorMatrixElement.getMyObj().getName();
		Obj objWithName = Tab.find(name);
		designatorMatrixElement.getMyObj().obj = objWithName;
		if (objWithName == Tab.noObj) {
			report_error("Greska: METRIX_IDENT: Identifikator koji koristite ne postoji u tabeli simbola", designatorMatrixElement);
		}
		if (objWithName.getType().getKind() == Struct.Array && objWithName.getType().getElemType().getKind() == Struct.Array) {
			designatorMatrixElement.struct = objWithName.getType().getElemType().getElemType();
		} else {
			report_error("Greska: Pogresno prosledjen identifikator, ocekivana je matrica", designatorMatrixElement);
			designatorMatrixElement.struct = Tab.noType;
		}
	}

	// TODO provera da li se isto zovu lokalna promenljiva i parametar
	public void visit(StatementRead statementRead) {
		Designator designator = statementRead.getDesignator();

		if (designator instanceof DesignatorIdent) {

			Obj objWithName = Tab.find(((DesignatorIdent) designator).getMyObj().getName());
			if (objWithName == Tab.noObj) {
				report_error("Greska: Ne postoji promenljiva koju bismo koristili za iteriranje kroz niz", statementRead);
			}

			if (objWithName.getKind() == Obj.Var) {

			} else {
				report_error("Greska: statementRead: Identifikator koji koristite ne postoji u tabeli simbola", statementRead);
			}

		} else if (designator instanceof DesignatorArrayElement) {

			Obj objWithName = Tab.find(((DesignatorArrayElement) designator).getMyObj().getName());
			if (objWithName == Tab.noObj) {
				report_error("Greska: Ne postoji promenljiva koju bismo koristili za iteriranje kroz niz", statementRead);
			}
			if (objWithName.getKind() == Obj.Var && objWithName.getType().getKind() == Struct.Array) {
				if (objWithName.getType().getElemType().assignableTo(Tab.intType) || objWithName.getType().getElemType().assignableTo(Tab.charType) || objWithName.getType().getElemType().assignableTo(Compiler.boolType)) {

				} else {
					report_error("Greska: statementRead: Promenljiva koriscena u izrazu nije odgovarajuceg tipa", statementRead);
				}
			}

		} else if (designator instanceof DesignatorMatrixElement) {
			Obj objWithName = Tab.find(((DesignatorMatrixElement) designator).getMyObj().getName());
			if (objWithName == Tab.noObj) {
				report_error("Greska: Ne postoji promenljiva koju bismo koristili za iteriranje kroz niz", statementRead);
			}
			if (objWithName.getType().getKind() == Struct.Array && objWithName.getType().getElemType().getKind() == Struct.Array) {
				if (objWithName.getType().getElemType().getElemType().assignableTo(Tab.intType) || objWithName.getType().getElemType().getElemType().assignableTo(Tab.charType) || objWithName.getType().getElemType().getElemType().assignableTo(Compiler.boolType)) {

				} else {
					report_error("Greska: statementRead: Identifikator koji koristite ne postoji u tabeli simbola", statementRead);
				}
			} else {
				report_error("Greska: statementRead: Identifikator koji koristite ne postoji u tabeli simbola", statementRead);
			}
		}
	}

	public boolean passed() {
		return !errorDetected;
	}

}
