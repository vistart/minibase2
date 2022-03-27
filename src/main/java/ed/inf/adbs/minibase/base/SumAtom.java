package ed.inf.adbs.minibase.base;

import ed.inf.adbs.minibase.Utils;

public class SumAtom extends Atom {
	Term term;
	public SumAtom(Term term) {
		this.term = term;
	}
	public Term getTerm() {
		return this.term;
	}
	@Override
    public String toString() {
        return "SUM" + "(" + term.toString() + ")";
    }
}
