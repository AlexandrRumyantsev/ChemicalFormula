public class Main {
    public static void main(String[] args) {
        String formula = "H2O10H12Fe(OH)";
        FormulaParser parser = new FormulaParser();
        parser.parseFormula(formula);
    }
}