import java.util.*;
import java.lang.Character;
public class FormulaParser {

    private Integer mainMultiplier;
    private String formula;
    private HashMap<String, Integer> formulaMap;
    private boolean isValidFormula(){
        return formula != "";
    }
    public void parseFormula(String str){

        formulaMap = new HashMap<>();
        formula = str;
        if (!isValidFormula()){
            System.out.println("Формула не валидна");
            return;
        }
        countMainMultiplier();
        if (isContainsStaples()){
            countElementsInStaples();
        }
        countElementsInClearFormula(returnClearFormula());
        formulaMap.entrySet().forEach(elem -> elem.setValue(elem.getValue() * mainMultiplier));
        printFormulaMap();
    }
    public void printFormulaMap(){
        System.out.println("Разложение формулы " + formula+" :");
        for (Map.Entry<String, Integer> entry : formulaMap.entrySet()){
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
    private void countElementsInClearFormula(String clearFormula){
        StringBuilder sbElement = new StringBuilder();
        StringBuilder sbMultiplier = new StringBuilder();
        int multiplier;
        String currentElement;
        for(int i = 0; i < clearFormula.length(); i++){
            char currentChar = clearFormula.charAt(i);
            if(Character.isUpperCase(currentChar)){
                if (!sbElement.isEmpty()) {
                    multiplier = 1;
                    currentElement = sbElement.toString();
                    if (!sbMultiplier.isEmpty()) {multiplier = Integer.parseInt(sbMultiplier.toString());};
                    if (formulaMap.containsKey(currentElement)){
                        formulaMap.put(currentElement, formulaMap.get(currentElement) + multiplier);
                    }
                    else  {formulaMap.put(currentElement, multiplier);};
                    sbElement.setLength(0);
                    sbMultiplier.setLength(0);
                }
                sbElement.append(currentChar);
            }
            else {
                if (!Character.isDigit(currentChar)) {sbElement.append(currentChar);}
                else {sbMultiplier.append(currentChar);}
            }
        }
        multiplier = 1;
        currentElement = sbElement.toString();
        if (!sbMultiplier.isEmpty()) {multiplier = Integer.parseInt(sbMultiplier.toString());};
        if (formulaMap.containsKey(currentElement)){
            formulaMap.put(currentElement, formulaMap.get(currentElement) + multiplier);
        }
        else  {formulaMap.put(currentElement, multiplier);};
    }

    private void countMainMultiplier(){
        mainMultiplier = 1;
        final char firstSymbol = formula.charAt(0);
        if (Character.isDigit(firstSymbol)){
            StringBuilder sbMultiplier = new StringBuilder();
            sbMultiplier.append(firstSymbol);
            for(int i = 1; i < formula.length(); i++){
                if (Character.isDigit(formula.charAt(i))){
                    sbMultiplier.append(formula.charAt(i));
                }
                else{
                    break;
                }
            }
            mainMultiplier = Integer.parseInt(sbMultiplier.toString());
        }
    }
    private boolean isContainsStaples() {
        return formula.contains("(");
    }
    private boolean hasMultiplierAfterStaples(){
        return formula.charAt(formula.length()-1) != ')';
    }
    private void countElementsInStaples(){

        String clearFormula = formula.substring(formula.indexOf("(")+1, formula.indexOf(")"));
        int multiplierAfterStaples;

        if (hasMultiplierAfterStaples()){
            multiplierAfterStaples = Integer.parseInt(formula.substring(formula.indexOf(")")+1));
        } else {
            multiplierAfterStaples = 1;
        }
        countElementsInClearFormula(clearFormula);
        formulaMap.entrySet().forEach(elem -> elem.setValue(elem.getValue() * multiplierAfterStaples));
    }
    private String returnFormulaWithoutStaples(){
        if (!formula.contains("(")){
            return formula;
        }
        return formula.substring(0, formula.indexOf("("));
    }
    private String returnClearFormula(){
        String formulaWithoutStaples = returnFormulaWithoutStaples();
        return mainMultiplier == 1 ? formulaWithoutStaples : formulaWithoutStaples.substring(Integer.toString(mainMultiplier).length());
    }
}
