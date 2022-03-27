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
