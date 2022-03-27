package ed.inf.adbs.minibase;

import java.util.ArrayList;
import java.util.HashMap;

import ed.inf.adbs.minibase.base.ComparisonAtom;
import ed.inf.adbs.minibase.base.Term;
import ed.inf.adbs.minibase.base.Variable;

public class JoinOperator extends Operator {
	private Operator leftChildOP;
	private Operator rightChildOP;
	private ArrayList<ComparisonAtom> conditions;
	private Tuple leftTuple;
	
	public JoinOperator(Operator leftChildOP, Operator rightChildOP, ArrayList<ComparisonAtom> conditions) {
		this.leftChildOP = leftChildOP;
		this.rightChildOP = rightChildOP;
		this.conditions = conditions;
		this.leftTuple = this.leftChildOP.getNextTuple();
	}
	
	@Override
	Tuple getNextTuple() {
		if(leftTuple == null) {
			return null;
		}
		Tuple rightTuple = rightChildOP.getNextTuple();
		while(rightTuple != null) {
			Tuple mergedTuple = this.tryMergeTuple(leftTuple, rightTuple);
			if(mergedTuple != null) {
				boolean pass = true;
				for(int i=0; i<conditions.size(); i++) {
					if(!ComparisonCheck.checkCondition(mergedTuple, conditions.get(i))) {
						pass = false;
					}
				}
				if(pass) {
					return mergedTuple;
				}
			}
			rightTuple = rightChildOP.getNextTuple();
		}
		leftTuple = leftChildOP.getNextTuple();
		if(leftTuple!=null) {
			rightChildOP.reset();
			return this.getNextTuple();
		}
		return null;
	}
	
	/***
	 * Merge two tuples, if they have same variable name, then check the corresponding value of that variable of two tuples.
	 * if the values are different, then tuple1 and tuple2 can not be merged
	 * @param tuple1
	 * @param tuple2
	 * @return null if tuple1 and tuple2 can not be merged,  return a new tuple if tuple1 and tuple2 can merge
	 */
	private Tuple tryMergeTuple(Tuple tuple1, Tuple tuple2) {
		int newTupleSize = 0;
		ArrayList<String> newValues = new ArrayList<String>();
		ArrayList<String> newSchema = new ArrayList<String>();
		ArrayList<Term> newTerms = new ArrayList<Term>();
		HashMap<String, Integer> newVarRef = new HashMap<String, Integer>();
		
		ArrayList<String> values1 = tuple1.getValues();
		ArrayList<String> schema1 = tuple1.getSchema();
		ArrayList<Term> terms1 = tuple1.getTerms();
		
		ArrayList<String> values2 = tuple2.getValues();
		ArrayList<String> schema2 = tuple2.getSchema();
		ArrayList<Term> terms2 = tuple2.getTerms();
		
		for(int i=0; i<values1.size(); i++) {
			newValues.add(values1.get(i));
			newSchema.add(schema1.get(i));
			newTerms.add(terms1.get(i));
			newVarRef.put(terms1.get(i).toString(), i);
			newTupleSize++;
		}
		for(int i=0; i<values2.size(); i++) {
			String val2 = values2.get(i);
			Term term2 = terms2.get(i);
			
			if(term2 instanceof Variable) {
				if(newVarRef.containsKey(term2.toString())) {
					String newVal = newValues.get(newVarRef.get(term2.toString()));
					newTupleSize--;
					if(!newVal.equals(val2)) {
						return null;
					}
				}
				else {

					newValues.add(values2.get(i));
					newSchema.add(schema2.get(i));
					newTerms.add(terms2.get(i));
					newVarRef.put(terms2.get(i).toString(), newTupleSize+i);
				}
			}
			else {

				newValues.add(values2.get(i));
				newSchema.add(schema2.get(i));
				newTerms.add(terms2.get(i));
				newVarRef.put(terms2.get(i).toString(), newTupleSize+i);
			}
		}
		return new Tuple(newValues, newSchema, newTerms, newVarRef);
	}

	@Override
	void reset() {
		this.leftChildOP.reset();
		this.rightChildOP.reset();
	}

}
