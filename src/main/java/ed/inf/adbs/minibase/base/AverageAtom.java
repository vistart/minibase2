package ed.inf.adbs.minibase.base;

public class AverageAtom extends Atom {
	Term term;
	public AverageAtom(Term term) {
		this.term = term;
	}
	public Term getTerm() {
		return this.term;
	}
	
	@Override
    public String toString() {
        return "AVG" + "(" + term.toString() + ")";
    }
}
