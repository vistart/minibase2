package ed.inf.adbs.minibase;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

import ed.inf.adbs.minibase.base.RelationalAtom;
import ed.inf.adbs.minibase.base.Term;


public class ScanOperator extends Operator {
	private CatalogSingleton cat;
	private String relationName;
	private Scanner myReader;
	private ArrayList<String> schema;
	private ArrayList<Term> terms;
	private HashMap<String,Integer> varRef;
	
	public ScanOperator(RelationalAtom atom, String databaseDir) {
		this.cat = CatalogSingleton.getInstance(databaseDir);
		this.relationName = atom.getName();
		this.reset();
		this.schema = cat.getSchemas().get(relationName);
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
			String fDir = this.cat.getCSVdir().get(this.relationName);
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
