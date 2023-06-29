package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor{

	
	protected int count;
	
	public int getCount(){
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor{
	
		public void visit(SingleFormParameter singleFormParameter){
			count++;
		}
		
		public void visit(MultipleFormParameter multipleFormParameter){
			count++;
		}
		
	}
		
	public static class VarCounter extends CounterVisitor{
		public void visit(SingleVarDecl singleVarDecl){
			count++;
		}
		public void visit(MultipleVarDecl multipleVarDecl){
			count++;
		}
	}
	
	public static class CondFactCounter extends CounterVisitor{
		public void visit(CondTermSingleFact condTermSingleFact){
			count++;
		}
		public void visit(ConditionMultipleFact conditionMultipleFact){
			count++;
		}
	}
	
	public static class CondTermCounter extends CounterVisitor{
		public void visit(ConditionSingleTerm conditionSingleTerm){
			count++;
		}
		public void visit(ConditionMultipleTerm conditionMultipleTerm){
			count++;
		}
	}
	
	
	
	
}
