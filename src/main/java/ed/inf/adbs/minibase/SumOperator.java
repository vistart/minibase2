package ed.inf.adbs.minibase;

import java.util.ArrayList;
import java.util.HashMap;

public class SumOperator extends Operator {
	private Operator childOperator;
	private ArrayList<Tuple> allTuples = new ArrayList<Tuple>();
	private ArrayList<Tuple> tupleGroupSum = new ArrayList<Tuple>();
	private String sumVar;
	private int tupleListIdx = 0;
	
	public SumOperator(Operator childOperator, String sumVar) {
		this.childOperator = childOperator;
		this.getAllTuples();
		this.sumVar = sumVar;
		this.groupSum();
	}
	
	/***
	 * group the tuples and calculate the corresponding sum value of the aggregation variable
	 */
	private void groupSum() {
		while(!this.allTuples.isEmpty()) {
			ArrayList<Tuple> group = new ArrayList<Tuple>();
			Tuple tuple1 = this.allTuples.get(0);
			this.allTuples.remove(0);
			group.add(tuple1);
			for(int i=0; i<this.allTuples.size();i++) {
				Tuple tuple2 = this.allTuples.get(i);
				if(this.sameGroup(tuple1, tuple2, this.sumVar)) {
					group.add(tuple2);
					this.allTuples.remove(i);
					i--;
				}
			}

			int sum = 0;
			HashMap<String, Integer> varRef = tuple1.getReferences();
			for(int i=0; i<group.size(); i++) {
				int val = Integer.parseInt(group.get(i).getValueAt(varRef.get(sumVar)));
				sum += val;
			}
			ArrayList<String> newValues = tuple1.getValues();
			newValues.set(varRef.get(sumVar), String.valueOf(sum));
			this.tupleGroupSum.add(new Tuple(newValues, tuple1.getSchemas(), tuple1.getTerms(), varRef));
		}
	}
	
	/***
	 * check if tuple1 and tuple2 are in the same group, which means that they have the same values except the aggregation value
	 * @param tuple1
	 * @param tuple2
	 * @param sumVar  the sum aggregation variable
	 * @return true if tuple1 and tuple2 are in the same group
	 */
	private boolean sameGroup(Tuple tuple1, Tuple tuple2, String sumVar) {
		int sumVarPos = tuple1.getReferences().get(sumVar);
		for(int i=0; i<tuple1.getValues().size(); i++) {
			if(i==sumVarPos) {
				continue;
			}
			if(!tuple1.getValueAt(i).equals(tuple2.getValueAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/***
	 * get all the tuples from the child operator and save them to a list
	 */
	private void getAllTuples() {
		while(true) {
			Tuple tuple = this.childOperator.getNextTuple();
			if(tuple==null) {
				break;
			}
			else {
				allTuples.add(tuple);
			}
		}
	}
	@Override
	Tuple getNextTuple() {
		if(this.tupleListIdx < this.tupleGroupSum.size()) {
			Tuple tuple = tupleGroupSum.get(tupleListIdx);
			this.tupleListIdx++;
			return tuple;
		}
		else {
			return null;
		}
	}

	@Override
	void reset() {
		this.tupleListIdx = 0;
	}

}
