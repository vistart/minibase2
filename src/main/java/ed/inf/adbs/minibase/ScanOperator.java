package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.RelationalAtom;
import ed.inf.adbs.minibase.base.Term;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class ScanOperator extends Operator {
	private Catalog catalog;
	private String relation;
	private Scanner scanner;
	private ArrayList<String> schemas;
	private ArrayList<Term> terms;
	private HashMap<String, Integer> references;

	public ScanOperator(RelationalAtom atom, String dbDir) {
		this.catalog = Catalog.getInstance(dbDir);
		this.relation = atom.getName();
		this.reset();
		this.schemas = catalog.getSchemaMap().get(relation);
		this.parseAtom(atom);
	}

	@Override
	Tuple getNextTuple() {
		if (scanner.hasNextLine()) {
			return new Tuple(new ArrayList<>(Arrays.asList(scanner.nextLine().split(", "))), this.schemas, this.terms, this.references);
		}
		return null;
	}
	@Override
	void reset() {
		try {
			String fDir = this.catalog.getNameMap().get(this.relation);
			scanner = new Scanner(new File(fDir));
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void parseAtom(RelationalAtom atom) {
		this.references = new HashMap<>();
		this.terms = new ArrayList<>();
		List<Term> ts = atom.getTerms();
		for(int i=0; i<ts.size(); i++) {
			Term term = ts.get(i);
			this.terms.add(term);
			references.put(term.toString(), i);
		}
	}
}
