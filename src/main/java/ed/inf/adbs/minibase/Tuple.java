package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.Term;

import java.util.ArrayList;
import java.util.HashMap;

public class Tuple {
	private final ArrayList<String> values;
	private final ArrayList<String> schemas;
	private final ArrayList<Term> terms;
	private final HashMap<String, Integer> refs;

	public Tuple(ArrayList<String> values, ArrayList<String> schemas, ArrayList<Term> terms, HashMap<String, Integer> refs)
	{
		this.values = values;
		this.schemas = schemas;
		this.terms = terms;
		this.refs = refs;
	}

	public ArrayList<String> getValues() {
		return this.values;
	}

	public String getValueAt(int i) {
		return this.values.get(i);
	}

	public ArrayList<String> getSchemas() {
		return this.schemas;
	}

	public String getSchemaAt(int i){
		return this.schemas.get(i);
	}

	public ArrayList<Term> getTerms() {
		return this.terms;
	}

	public HashMap<String, Integer> getRefs() {return this.refs;}

	public void setValueAt(int i, String value) {
		this.values.set(i, value);
	}
}