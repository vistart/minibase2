package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.Term;

import java.util.ArrayList;
import java.util.HashMap;

public class Tuple {
	private ArrayList<String> values;
	private ArrayList<String> schemas;
	private ArrayList<Term> terms;
	private HashMap<String, Integer> references;

	public Tuple(ArrayList<String> values, ArrayList<String> schemas, ArrayList<Term> terms, HashMap<String, Integer> references)
	{
		this.values = values;
		this.schemas = schemas;
		this.terms = terms;
		this.references = references;
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

	public HashMap<String, Integer> getReferences() {
		return this.references;
	}

	public ArrayList<Term> getTerms() {
		return this.terms;
	}

	public void setValueAt(int i, String value) {
		this.values.set(i, value);
	}
}



//////Tuple 实现2///////////////
package ed.inf.adbs.minibase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tuple {

    private List<String> list = new ArrayList<>();

    public Tuple(String record) {list.addAll(Arrays.asList(record.split(",")));}

    public Tuple(List<String> list) {
        this.list = list;
    }

    public String toString(){
        StringBuilder stringB = new StringBuilder();
        for (String s: list){
            stringB.append(s).append(",");
        }
        return stringB.substring(0, stringB.length()-1); // return full String from Stringbuilder
    }
    
    public List<String> toList(){
     return list;
    }

}
