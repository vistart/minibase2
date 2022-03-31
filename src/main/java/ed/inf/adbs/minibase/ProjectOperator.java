package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.RelationalAtom;
import ed.inf.adbs.minibase.base.Term;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectOperator extends Operator {
	private final Operator childOperator;
	private ArrayList<Term> projectVar;
	
	public ProjectOperator(Operator childOperator, RelationalAtom headAtom) {
		this.childOperator = childOperator;
		this.projectVar = this.getProjectionVar(headAtom);
	}
	@Override
	Tuple getNextTuple() {
		Tuple tuple = childOperator.getNextTuple();
		while(tuple != null) {
			return this.projection(tuple);
		}
		return null;
	}
	
	/***
	 * project the tuple to a new tuple base on the variables on the query head
	 * @param tuple  the given tuple that needed to be projected
	 * @return a new tuple after projection
	 */
	private Tuple projection(Tuple tuple) {
		ArrayList<String> newValues = new ArrayList<String>();
		ArrayList<String> newSchema = new ArrayList<String>();
		ArrayList<Term> newTerms = new ArrayList<Term>();
		HashMap<String, Integer> newRefs = new HashMap<>();
		
		ArrayList<String> values = tuple.getValues();
		ArrayList<String> schema = tuple.getSchemas();
		ArrayList<Term> terms = tuple.getTerms();
		HashMap<String, Integer> refs = tuple.getRefs();
		for(int i=0; i<this.projectVar.size(); i++) {
			Term t = projectVar.get(i);
			int pos = refs.get(t.toString());
			newValues.add(values.get(pos));
			newSchema.add(schema.get(pos));
			newTerms.add(terms.get(pos));
			newRefs.put(terms.get(pos).toString(), i);
		}
		
		return new Tuple(newValues, newSchema, newTerms, newRefs);
	}
	
	/***
	 * get the projection variable from the query head
	 * @param atom The query head
	 * @return an ArrayList<String> that contains the variable name of the head
	 */
	private ArrayList<Term> getProjectionVar(RelationalAtom atom) {
		return new ArrayList<>(atom.getTerms());
	}

	@Override
	void reset() {
		this.childOperator.reset();
	}

}
