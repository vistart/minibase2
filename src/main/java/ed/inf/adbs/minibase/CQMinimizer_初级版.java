package ed.inf.adbs.minibase;

import ed.inf.adbs.minibase.base.Atom;
import ed.inf.adbs.minibase.base.Constant;
import ed.inf.adbs.minibase.base.IntegerConstant;
import ed.inf.adbs.minibase.base.Query;
import ed.inf.adbs.minibase.base.RelationalAtom;
import ed.inf.adbs.minibase.base.StringConstant;
import ed.inf.adbs.minibase.base.Term;
import ed.inf.adbs.minibase.base.Variable;
import ed.inf.adbs.minibase.parser.QueryParser;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Minimization of conjunctive queries
 *
 */
public class CQMinimizer {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: CQMinimizer input_file output_file");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        minimizeCQ(inputFile, outputFile);

        //parsingExample(inputFile);
    }

    /**
     * CQ minimization procedure
     *
     * Assume the body of the query from inputFile has no comparison atoms
     * but could potentially have constants in its relational atoms.
     *
     */
    public static void minimizeCQ(String inputFile, String outputFile) {
        // TODO: add your implementation
        Query query;
        try {
            query = QueryParser.parse(Paths.get(inputFile));
        } catch (Exception e) {
            System.err.println("Exception occured during parsing.");
            e.printStackTrace();
            return;
        }

        RelationalAtom head = query.getHead();
        List<Atom> body = query.getBody();

        Set<String> headTerms = new HashSet();
        for (Term t: head.getTerms()) {
            headTerms.add(t.toString());
        }
        for (int i = 0; i < body.size(); i++) {
            HashMap<String, String> map = new HashMap();
            List<Atom> nBody = new ArrayList(body);
            RelationalAtom rAtom = (RelationalAtom)body.get(i);
            nBody.remove(i);
            for(int j = 0; j < nBody.size(); j++) {
                RelationalAtom atom = (RelationalAtom)nBody.get(j);
                HashMap<String, String> m = findMapping(rAtom, atom, headTerms);
                List<RelationalAtom> b = mapping(m, body);
                removeDuplicate(b);

                if(bodyIsEqual(b, nBody)) {
                    body = nBody;
                    i--;
                    break;
                }
            }
        }
        System.out.println(head);
        System.out.println(body);
    }
    private static HashMap<String,String> findMapping(RelationalAtom removedAtom, RelationalAtom atom, Set<String> headTerms){
        HashMap<String,String> map;
        HashMap<String,String> pmap = new HashMap();
        if(atom.getName().equals(removedAtom.getName())) {
            List<Term> raTerms = removedAtom.getTerms();
            List<Term> aTerms = atom.getTerms();
            for(int k=0; k<raTerms.size(); k++) {
                Term raT = raTerms.get(k);
                Term aT = aTerms.get(k);
                if(headTerms.contains(raT.toString())) {
                    continue;
                }
                if(raT instanceof Constant) {
                    continue;
                }

                pmap.put(raT.toString(), aT.toString());
            }
        }
        map = pmap;
        return map;
    }
    private static List<RelationalAtom> mapping(HashMap<String,String> m, List<Atom> body){
        List<RelationalAtom> b = new ArrayList();
        for(int i=0; i<body.size(); i++) {
            List<Term> terms = ((RelationalAtom)body.get(i)).getTerms();
            List<Term> newTerms = new ArrayList();
            for(int j=0; j<terms.size(); j++) {
                Term term = terms.get(j);
                if(m.containsKey(term.toString())) {
                    if(m.get(term.toString()).charAt(0)=='\'') {
                        newTerms.add(new StringConstant(m.get(term.toString())));
                    }
                    else if(!Character.isDigit(m.get(term.toString()).charAt(0))) {
                        newTerms.add(new Variable(m.get(term.toString())));
                    }
                    else {
                        newTerms.add(new IntegerConstant(Integer.parseInt(m.get(term.toString()))));
                    }
                }
                else {
                    String s = term.toString();
                    if(term instanceof Variable) {
                        newTerms.add(new Variable(s));
                    }
                    else if(term instanceof StringConstant) {
                        newTerms.add(new StringConstant(s.substring(1,s.length()-1)));
                    }
                    else {
                        newTerms.add(new IntegerConstant(Integer.parseInt(s)));
                    }
                }
            }
            b.add(new RelationalAtom(((RelationalAtom)body.get(i)).getName(), newTerms));
        }
        return b;
    }
    public static void removeDuplicate(List<RelationalAtom> body) {
        for(int i = 0; i < body.size(); i++) {
            RelationalAtom a = body.get(i);
            List<Term> terms1 = a.getTerms();
            for(int j = 0; j < body.size(); j++) {
                if(j == i) {
                    continue;
                }
                boolean isSame = false;
                RelationalAtom b = body.get(j);
                List<Term> terms2 = b.getTerms();
                if(a.getName().equals(b.getName())) {
                    for(int k = 0; k < terms1.size(); k++) {
                        Term t1 = terms1.get(k);
                        Term t2 = terms2.get(k);
                        if(!t1.toString().equals(t2.toString())) {
                            break;
                        }
                        if(k == terms1.size()-1) {
                            isSame = true;
                        }
                    }
                }
                if(isSame) {
                    body.remove(i);
                    i--;
                    break;
                }
            }
        }
    }

    public static boolean bodyIsEqual(List<RelationalAtom> a, List<Atom> b) {
        if (a.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < a.size(); i++) {
            RelationalAtom atom_a = a.get(i);
            for (int j = 0; j < a.size(); j++) {
                RelationalAtom atom_b = (RelationalAtom)b.get(j);
                if (atomIsEqual(atom_a, atom_b)) {
                    break;
                }
                if (j == b.size() - 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean atomIsEqual(RelationalAtom a, RelationalAtom b)
    {
        if (!a.getName().equals(b.getName())){
            return false;
        }
        for (int i = 0; i < a.getTerms().size(); i++)
        {
            if (!a.getTerms().get(i).toString().equals(b.getTerms().get(i).toString())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Example method for getting started with the parser.
     * Reads CQ from a file and prints it to screen, then extracts Head and Body
     * from the query and prints them to screen.
     */

    public static void parsingExample(String filename) {

        try {
            Query query = QueryParser.parse(Paths.get(filename));
//            Query query = QueryParser.parse("Q(x, y) :- R(x, z), S(y, z, w)");
//            Query query = QueryParser.parse("Q() :- R(x, 'z'), S(4, z, w)");

            System.out.println("Entire query: " + query);
            RelationalAtom head = query.getHead();
            System.out.println("Head: " + head);
            List<Atom> body = query.getBody();
            System.out.println("Body: " + body);
        }
        catch (Exception e)
        {
            System.err.println("Exception occurred during parsing");
            e.printStackTrace();
        }
    }

}
