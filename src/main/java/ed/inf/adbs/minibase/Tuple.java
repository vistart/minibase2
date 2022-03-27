package ed.inf.adbs.minibase;

import java.util.ArrayList;
import java.util.HashMap;

import ed.inf.adbs.minibase.base.Term;

public class Tuple {
	private ArrayList<String> values;
	private ArrayList<String> schema;
	private ArrayList<Term> terms;
	private HashMap<String, Integer> varRef;
	public Tuple(ArrayList<String> values, ArrayList<String> schema, ArrayList<Term> terms, HashMap<String,Integer> varRef) {
		this.values = values;
		this.schema = schema;
		this.varRef = varRef;
		this.terms = terms;
	}
	public ArrayList<String> getValues(){
		return this.values;
	}
	
	public String getValueAt(int i) {
		return this.values.get(i);
	}
	
	public ArrayList<String> getSchema() {
		return this.schema;
	}
	
	public String getSchemaAt(int i) {
		return this.schema.get(i);
	}
	
	public HashMap<String,Integer> getVariableRefernce(){
		return this.varRef;
	}
	
	public ArrayList<Term> getTerms(){
		return this.terms;
	}
	
	public void setValAt(int i, String val) {
		this.values.set(i, val);
	}
}
