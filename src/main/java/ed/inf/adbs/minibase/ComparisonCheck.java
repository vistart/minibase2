package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.ComparisonAtom;
import ed.inf.adbs.minibase.base.StringConstant;
import ed.inf.adbs.minibase.base.Term;
import ed.inf.adbs.minibase.base.Variable;

import java.util.HashMap;

/***
 * a class that provide methods to check if tuple pass the condition from the Comparison Atom
 * @author JD
 *
 */
public class ComparisonCheck {
	public ComparisonCheck() {}
	
	/***
	 * To check if the tuple pass the condition from the comparison Atom
	 * @param tuple  the tuple to check if it pass the condition
	 * @param condition  the comparison condition
	 * @return true if the tuple pass the condition   false if the tuple does not pass
	 */
	public static boolean checkCondition(Tuple tuple, ComparisonAtom condition) {
		String comparisonOp = condition.getOp().toString();
		Term term1 = condition.getTerm1();
		Term term2 = condition.getTerm2();
		HashMap<String,Integer> varRef = tuple.getReferences();
		//if the term of the condition not in this tuple, return true
		if(!varRef.containsKey(term1.toString()) && !varRef.containsKey(term2.toString())) {
			return true;
		}
		boolean pass = false;
		switch(comparisonOp) {
			case "=":
				pass = ComparisonCheck.equal(tuple, term1, term2, varRef);
				break;
			case "!=":
				pass = ComparisonCheck.notEqual(tuple, term1, term2, varRef);
				break;
			case ">":
				pass = ComparisonCheck.greaterThan(tuple, term1, term2, varRef);
				break;
			case ">=":
				pass = ComparisonCheck.greaterEqual(tuple, term1, term2, varRef);
				break;
			case "<":
				pass = ComparisonCheck.lessThan(tuple, term1, term2, varRef);
				break;
			case "<=":
				pass = ComparisonCheck.lessEqual(tuple, term1, term2, varRef);
				break;
		}
		return pass;
	}
	
	/***
	 * to check equal condition
	 * @param tuple  the tuple to check
	 * @param term1  the left term in the comparison atom
	 * @param term2  the right term in the comparison atom
	 * @param varRef  the variable reference that record the corresponding position of the term in tuple
	 * @return true if the tuple data in position term1 is equal to the tuple data in position term2, false if not equal
	 */
	public static boolean equal(Tuple tuple, Term term1, Term term2, HashMap<String,Integer> varRef) {
		if(term1 instanceof Variable) {
			int pos1 = varRef.get(term1.toString());
			String value1 = tuple.getValueAt(pos1);
			if(term2 instanceof Variable) {
				int pos2 = varRef.get(term2.toString());
				String value2 = tuple.getValueAt(pos2);
				String valType = tuple.getSchemaAt(pos1);
				if(valType.equals("string")) {
					return value1.equals(value2.toString());
				}
				else if(valType.equals("int")){
					return Integer.parseInt(value1) == Integer.parseInt(value2);
				}
			}
			else {
				String valType = tuple.getSchemaAt(pos1);
				if(valType.equals("string")) {
					return value1.equals(term2.toString());
				}
				else if(valType.equals("int")){
					return Integer.parseInt(value1) == Integer.parseInt(term2.toString());
				}
			}
		}
		else if(term1 instanceof StringConstant) {
			return term1.toString().equals(term2.toString());
		}
		else {
			return Integer.parseInt(term1.toString()) == Integer.parseInt(term2.toString());
		}
		return true;
	}
	
	/***
	 * to check equal condition
	 * @param tuple  the tuple to check
	 * @param term1  the left term in the comparison atom
	 * @param term2  the right term in the comparison atom
	 * @param varRef  the variable reference that record the corresponding position of the term in tuple
	 * @return true if the tuple data in position term1 is not equal to the tuple data in position term2, false if equal
	 */
	public static boolean notEqual(Tuple tuple, Term term1, Term term2, HashMap<String,Integer> varRef) {
		if(term1 instanceof Variable) {
			int pos1 = varRef.get(term1.toString());
			String value1 = tuple.getValueAt(pos1);
			if(term2 instanceof Variable) {
				int pos2 = varRef.get(term2.toString());
				String value2 = tuple.getValueAt(pos2);
				String valType = tuple.getSchemaAt(pos1);
				if(valType.equals("string")) {
					return !value1.equals(value2.toString());
				}
				else if(valType.equals("int")){
					return Integer.parseInt(value1) != Integer.parseInt(value2);
				}
			}
			else {
				String valType = tuple.getSchemaAt(pos1);
				if(valType.equals("string")) {
					return !value1.equals(term2.toString());
				}
				else if(valType.equals("int")){
					return Integer.parseInt(value1) != Integer.parseInt(term2.toString());
				}
			}
		}
		else if(term1 instanceof StringConstant) {
			return !term1.toString().equals(term2.toString());
		}
		else {
			return Integer.parseInt(term1.toString()) != Integer.parseInt(term2.toString());
		}
		return true;
	}
	/***
	 * to check equal condition
	 * @param tuple  the tuple to check
	 * @param term1  the left term in the comparison atom
	 * @param term2  the right term in the comparison atom
	 * @param varRef  the variable reference that record the corresponding position of the term in tuple
	 * @return true if the tuple data in position term1 is greater than the tuple data in position term2, false if not
	 */
	public static boolean greaterThan(Tuple tuple, Term term1, Term term2, HashMap<String,Integer> varRef) {
		if(term1 instanceof Variable) {
			int pos1 = varRef.get(term1.toString());
			String value1 = tuple.getValueAt(pos1);
			if(term2 instanceof Variable) {
				int pos2 = varRef.get(term2.toString());
				String value2 = tuple.getValueAt(pos2);
				String valType = tuple.getSchemaAt(pos1);
				if(valType.equals("string")) {
					return value1.compareTo(value2) > 0;
				}
				else if(valType.equals("int")){
					return Integer.parseInt(value1) > Integer.parseInt(value2);
				}
			}
			else {
				String valType = tuple.getSchemaAt(pos1);
				if(valType.equals("string")) {
					return value1.compareTo(term2.toString()) > 0;
				}
				else if(valType.equals("int")){
					return Integer.parseInt(value1) > Integer.parseInt(term2.toString());
				}
			}
		}
		else if(term1 instanceof StringConstant) {
			return term1.toString().compareTo(term2.toString()) > 0;
		}
		else {
			return Integer.parseInt(term1.toString()) > Integer.parseInt(term2.toString());
		}
		return true;
	}
	/***
	 * to check equal condition
	 * @param tuple  the tuple to check
	 * @param term1  the left term in the comparison atom
	 * @param term2  the right term in the comparison atom
	 * @param varRef  the variable reference that record the corresponding position of the term in tuple
	 * @return true if the tuple data in position term1 is greater than or equal to the tuple data in position term2, false if not
	 */
	public static boolean greaterEqual(Tuple tuple, Term term1, Term term2, HashMap<String,Integer> varRef) {
		if(term1 instanceof Variable) {
			int pos1 = varRef.get(term1.toString());
			String value1 = tuple.getValueAt(pos1);
			if(term2 instanceof Variable) {
				int pos2 = varRef.get(term2.toString());
				String value2 = tuple.getValueAt(pos2);
				String valType = tuple.getSchemaAt(pos1);
				if(valType.equals("string")) {
					return value1.compareTo(value2) >= 0;
				}
				else if(valType.equals("int")){
					return Integer.parseInt(value1) >= Integer.parseInt(value2);
				}
			}
			else {
				String valType = tuple.getSchemaAt(pos1);
				if(valType.equals("string")) {
					return value1.compareTo(term2.toString()) >= 0;
				}
				else if(valType.equals("int")){
					return Integer.parseInt(value1) >= Integer.parseInt(term2.toString());
				}
			}
		}
		else if(term1 instanceof StringConstant) {
			return term1.toString().compareTo(term2.toString()) >= 0;
		}
		else {
			return Integer.parseInt(term1.toString()) >= Integer.parseInt(term2.toString());
		}
		return true;
	}
	/***
	 * to check equal condition
	 * @param tuple  the tuple to check
	 * @param term1  the left term in the comparison atom
	 * @param term2  the right term in the comparison atom
	 * @param varRef  the variable reference that record the corresponding position of the term in tuple
	 * @return true if the tuple data in position term1 is less than the tuple data in position term2, false if not
	 */
	public static boolean lessThan(Tuple tuple, Term term1, Term term2, HashMap<String,Integer> varRef) {
		if(term1 instanceof Variable) {
			int pos1 = varRef.get(term1.toString());
			String value1 = tuple.getValueAt(pos1);
			if(term2 instanceof Variable) {
				int pos2 = varRef.get(term2.toString());
				String value2 = tuple.getValueAt(pos2);
				String valType = tuple.getSchemaAt(pos1);
				if(valType.equals("string")) {
					return value1.compareTo(value2) < 0;
				}
				else if(valType.equals("int")){
					return Integer.parseInt(value1) < Integer.parseInt(value2);
				}
			}
			else {
				String valType = tuple.getSchemaAt(pos1);
				if(valType.equals("string")) {
					return value1.compareTo(term2.toString()) < 0;
				}
				else if(valType.equals("int")){
					return Integer.parseInt(value1) < Integer.parseInt(term2.toString());
				}
			}
		}
		else if(term1 instanceof StringConstant) {
			return term1.toString().compareTo(term2.toString()) < 0;
		}
		else {
			return Integer.parseInt(term1.toString()) < Integer.parseInt(term2.toString());
		}
		return true;
	}
	/***
	 * to check equal condition
	 * @param tuple  the tuple to check
	 * @param term1  the left term in the comparison atom
	 * @param term2  the right term in the comparison atom
	 * @param varRef  the variable reference that record the corresponding position of the term in tuple
	 * @return true if the tuple data in position term1 is less than or equal to the tuple data in position term2, false if not
	 */
	public static boolean lessEqual(Tuple tuple, Term term1, Term term2, HashMap<String,Integer> varRef) {
		if(term1 instanceof Variable) {
			int pos1 = varRef.get(term1.toString());
			String value1 = tuple.getValueAt(pos1);
			if(term2 instanceof Variable) {
				int pos2 = varRef.get(term2.toString());
				String value2 = tuple.getValueAt(pos2);
				String valType = tuple.getSchemaAt(pos1);
				if(valType.equals("string")) {
					return value1.compareTo(value2) <= 0;
				}
				else if(valType.equals("int")){
					return Integer.parseInt(value1) <= Integer.parseInt(value2);
				}
			}
			else {
				String valType = tuple.getSchemaAt(pos1);
				if(valType.equals("string")) {
					return value1.compareTo(term2.toString()) <= 0;
				}
				else if(valType.equals("int")){
					return Integer.parseInt(value1) <= Integer.parseInt(term2.toString());
				}
			}
		}
		else if(term1 instanceof StringConstant) {
			return term1.toString().compareTo(term2.toString()) <= 0;
		}
		else {
			return Integer.parseInt(term1.toString()) <= Integer.parseInt(term2.toString());
		}
		return true;
	}
}
