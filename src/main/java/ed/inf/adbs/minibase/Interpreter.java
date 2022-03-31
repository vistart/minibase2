package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.*;

import java.util.ArrayList;
import java.util.List;

public class Interpreter {
	private Query query;
	private RelationalAtom head;
	private List<Atom> body;
	private String dbPath;
	private String sumVariable;
	private String avgVariable;
	
	private ArrayList<ComparisonAtom> compAtoms = new ArrayList<ComparisonAtom>();
	private ArrayList<RelationalAtom> relaAtoms = new ArrayList<RelationalAtom>();
	
	private ArrayList<ComparisonAtom> selectComparisonAtoms = new ArrayList<ComparisonAtom>();
	private ArrayList<ComparisonAtom> joinComparisonAtoms = new ArrayList<ComparisonAtom>();
	
	public Interpreter(Query query, String dbPath) {
		this.query = query;
    	this.head = this.query.getHead();
    	this.body = this.query.getBody();
    	this.dbPath = dbPath;
    	
    	this.seperateBodyAtoms(this.body);
    	this.seperateComparisons(this.compAtoms);
    	this.getSumOrAvgVar();
	}
	
	/***
	 * if there is aggregation, get the aggregation var
	 */
	private void getSumOrAvgVar() {	
		Atom lastAtom = this.body.get(body.size()-1);
		if(lastAtom instanceof SumAtom) {
			this.sumVariable = ((SumAtom) lastAtom).getTerm().toString();
		}
		else if(lastAtom instanceof AverageAtom) {
			this.avgVariable = ((AverageAtom) lastAtom).getTerm().toString();
		}
	}
	/**
     * Separate the Atoms in the Query Body into two list compAtoms and relaAtoms.
     */
	private void seperateBodyAtoms(List<Atom> body) {
		for(int i=0; i<body.size(); i++) {
			if(body.get(i) instanceof RelationalAtom) {
				this.relaAtoms.add((RelationalAtom)body.get(i));
			}
			else if(body.get(i) instanceof ComparisonAtom){
				this.compAtoms.add((ComparisonAtom)body.get(i));
			}
		}
	}
	/**
     * Separate the ComparisonAtom in the Query Body into two list selectComparisons and joinComparisons.
     */
	private void seperateComparisons(List<ComparisonAtom> compAtoms) {
		for(int i=0; i<compAtoms.size(); i++) {
			Term term1 = compAtoms.get(i).getTerm1();
			Term term2 = compAtoms.get(i).getTerm2();
			if(term1 instanceof Constant || term2 instanceof Constant) {
				this.selectComparisonAtoms.add(compAtoms.get(i));
			}
			else {
				this.joinComparisonAtoms.add(compAtoms.get(i));
			}
		}
	}
	
	/***
	 * Build the query plan and get the final results tuples base on the query
	 * @return  a list of tuple of the results
	 */
	public ArrayList<Tuple> getResult(){
		//1. construct a scan operator for each relational atom, and then construct a select operator for each scan operator
		ArrayList<Operator> selectOPs = new ArrayList<Operator>();
		for(int i=0; i<this.relaAtoms.size(); i++) {
			Operator scOp = new ScanOperator(this.relaAtoms.get(i), this.dbPath);
			selectOPs.add(new SelectOperator(scOp, this.selectComparisonAtoms));
		}
		
		//2. construct the left join tree
		ArrayList<Operator> joinOPs = new ArrayList<Operator>();
		if(selectOPs.size()>1) {
			Operator preJoinOP = new JoinOperator(selectOPs.get(0), selectOPs.get(1), this.joinComparisonAtoms);
			joinOPs.add(preJoinOP);
			for(int i=1; i<selectOPs.size()-1; i++) {
				Operator joinOP = new JoinOperator(joinOPs.get(i-1), selectOPs.get(i+1), this.joinComparisonAtoms);
				joinOPs.add(joinOP);
			}
		}
		
		//3. construct the project operator
		ProjectOperator projectOP;
		if(selectOPs.size()==1) {
			projectOP = new ProjectOperator(selectOPs.get(0), this.head);
		}
		else {
			projectOP = new ProjectOperator(joinOPs.get(joinOPs.size()-1), this.head);
		}
		
		
		//4. if there are aggregation, then construct an aggregation operator as the root, if not, use the projection operator as root
		/**
		if(this.sumVariable!=null) {
			SumOperator sumOP = new SumOperator(projectOP, this.sumVariable);
			return sumOP.dump();
		}
		else if(this.avgVariable!=null){
			AvgOperator avgOP = new AvgOperator(projectOP, this.avgVariable);
			return avgOP.dump();
		}
		 */
		return projectOP.dump();
		
		
	}

	
}
