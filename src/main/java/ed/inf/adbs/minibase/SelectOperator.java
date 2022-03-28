package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.ComparisonAtom;
import ed.inf.adbs.minibase.base.Constant;
import ed.inf.adbs.minibase.base.Term;

import java.util.ArrayList;
import java.util.List;


public class SelectOperator extends Operator {
	private Operator child;
	private ArrayList<ComparisonAtom> conditions;
	
	public SelectOperator(Operator child, ArrayList<ComparisonAtom> conditions) {
		this.child = child;
		this.conditions = conditions;
	}
	@Override
	Tuple getNextTuple() {
		Tuple tuple = child.getNextTuple();
		while(tuple != null) {
			boolean pass = true;
			if(!this.checkConstantTerms(tuple)) {
				pass = false;
			}
			for(int i=0; i<this.conditions.size(); i++) {
				if(!ComparisonCheck.checkCondition(tuple, this.conditions.get(i))) {
					pass = false;
				}
			}
			if(pass) {
				return tuple;
			}
			tuple = child.getNextTuple();
		}
		return null;
	}

	@Override
	void reset() {
		child.reset();
	}
	/***
	 * if the atom has constant term, check if the tuple match
	 * @param tuple  the tuple to check
	 * @return true if the tuple match that constant,  false if not
	 */
	private boolean checkConstantTerms(Tuple tuple) {
		List<String> values = tuple.getValues();
		List<Term> terms = tuple.getTerms();
		for(int i=0; i<terms.size(); i++) {
			Term term = terms.get(i);
			if(term instanceof Constant) {
				if(!term.toString().equals(values.get(i))) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	
	
}
