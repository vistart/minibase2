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
			String[] val = scanner.nextLine().split(", ");
			ArrayList<String> values = new ArrayList<String>();
			for(int i=0; i<val.length; i++) {
				values.add(vals[i]);
			}

			return new Tuple(values, this.schemas, this.terms, this.references);
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
		this.references = new HashMap<String,Integer>();
		this.terms = new ArrayList<Term>();
		List<Term> ts = atom.getTerms();
		for(int i=0; i<ts.size(); i++) {
			Term term = ts.get(i);
			this.terms.add(term);
			references.put(term.toString(), i);
		}
	}
}




///////实现2////////
package ed.inf.adbs.minibase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ScanOperator extends Operator{


    private String file;
    ArrayList<Tuple> table;
    int counter;
    private BufferedReader reader = null;

    public ScanOperator(String tableName) {

//        String file = database_catalog.getInstance().getFileByTableName(tableName);
//
//        ArrayList<Tuple> table = database_catalog.getInstance().getTable(database_catalog.getInstance().
//                getFileByTableName(tableName));
        table = database_catalog.getInstance().getTable(database_catalog.getInstance().getFileByTableName(tableName));
        counter = 0;
//        this.file = file;
//        try {
//            this.reader = new BufferedReader(new FileReader(file));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


//    public static ArrayList<String> readQuery(String inputFile){
//        ArrayList<String> output = new ArrayList<>();
//        try{
//            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
//            String line;
//            while ((line= reader.readLine()) != null){
//                output.add(line);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return output;
//    }


    /**
     * Get the next tuple of this operator
     * @return next tuple, if available, otherwise return null
     */
    @Override
    public Tuple getNextTuple() {
        if (counter == table.size()){ //reset when reaches end of list
            reset();
            return null;
        }
        Tuple next = table.get(counter);
        counter ++;
        return next;
    }

    /**
     * Reset the operator
     */
    @Override
    public void reset() {
        counter=0;
    }
//
//    @Override
//    public void dump
}
