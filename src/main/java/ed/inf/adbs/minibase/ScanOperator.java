package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.RelationalAtom;
import ed.inf.adbs.minibase.base.Term;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class ScanOperator extends Operator {
	private Catalog cat;
	private String relationName;
	private Scanner myReader;
	private ArrayList<String> schema;
	private ArrayList<Term> terms;
	private HashMap<String,Integer> varRef;
	
	public ScanOperator(RelationalAtom atom, String databaseDir) {
		this.cat = Catalog.getInstance(databaseDir);
		this.relationName = atom.getName();
		this.reset();
		this.schema = cat.getSchemaMap().get(relationName);
		this.parseAtom(atom);
	}
	@Override
	Tuple getNextTuple() {
		if(myReader.hasNextLine()) {
			String[] vals = myReader.nextLine().split(", ");
			ArrayList<String> values = new ArrayList<String>();
			for(int i=0; i<vals.length; i++) {
				values.add(vals[i]);
			}
			
			return new Tuple(values, this.schema, this.terms, this.varRef);
		}
		return null;
	}

	@Override
	void reset() {
		try {
			String fDir = this.cat.getNameMap().get(this.relationName);
			File f = new File(fDir);
			myReader = new Scanner(f);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
     * Save the Atom terms into a list and record the position of them
     * @param atom  the atom to parse
     */
	private void parseAtom(RelationalAtom atom){
		this.varRef = new HashMap<String,Integer>();
		this.terms = new ArrayList<Term>();
		List<Term> ts = atom.getTerms();
		for(int i=0; i<ts.size(); i++) {
			Term term = ts.get(i);
			this.terms.add(term);
			varRef.put(term.toString(), i);
		}
	}
	
}
