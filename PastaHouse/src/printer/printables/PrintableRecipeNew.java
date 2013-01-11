/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer.printables;

import database.extra.Component;
import database.tables.Recipe;
import java.awt.Font;
import java.awt.FontMetrics;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import printer.adts.PrintableHorizontalLineObject;
import printer.adts.PrintableLine;
import printer.adts.PrintableMulti;
import printer.adts.PrintableNewline;
import printer.adts.PrintableString;
import tools.StringTools;

/**
 *
 * @author Warkst
 */
public class PrintableRecipeNew extends MyPrintable{

    /*
     * Data model
     */
    private final Recipe model;
    
    /*
     * Charwidth for Monospaced font
     */
    private final int charWidth = 7;
    
    /*
     * Mutable vars for print dialog
     */
    private double toMake;
    private boolean isWeight;
    
    public PrintableRecipeNew(Recipe recipe){
	this(recipe, 1.0, false);
    }
    
    public PrintableRecipeNew(Recipe model, double toMake, boolean isWeight) {
	super(new Font("Monospaced", Font.PLAIN, 12));
	this.model = new Recipe(model);
	this.toMake = toMake;
	this.isWeight = isWeight;
    }
    
    /*
     * Accessors for printdialog
     */
    public Recipe getRecipe() {
	return model;
    }
    
    public double getToMake() {
	return toMake;
    }

    public void setToMake(double toMake) {
	this.toMake = toMake;
    }

    public boolean isWeight() {
	return isWeight;
    }

    public void setIsWeight(boolean isWeight) {
	this.isWeight = isWeight;
    }
    
    public double getToMakeToNet(){
	return isWeight? toMake/model.getNetWeight() : toMake;
    }
    
    /*
     * MyPrintable overrides
     */
    
    @Override
    public List<PrintableHorizontalLineObject> transformBody(int width, FontMetrics fontMetrics) {
	/*
	 * Empty list, to be added to
	 */
	List<PrintableHorizontalLineObject> printModel = new ArrayList<PrintableHorizontalLineObject>();
	
	/*
	 * Formatting variables
	 */
	int ingrNameLength = 30;
	int[] tabs = new int[]{5,ingrNameLength*charWidth,50*charWidth,68*charWidth};
	DecimalFormat three = new DecimalFormat("0.000");
	DecimalFormat two = new DecimalFormat("0.00");
	int imageableWidth = width - 50;
	
	double toMakeToNet = getToMakeToNet();
	/*
	 * Actual transformation
	 * Print a pretty header
	 */
	// print the name of the recipe in the middle
	// print something like: for preparing amount x, you need ...
	printModel.add(new PrintableNewline());
	
	printModel.add(new PrintableString("Recept "+model.getName().toUpperCase(), charWidth*10));
//	printModel.add(new PrintableNewline());
	printModel.add(new PrintableNewline());
	
	printModel.add(new PrintableString(isWeight?"Om "+two.format(toMake)+" kg te bereiden heeft u de volgende hoeveelheden nodig:" : 
		"Om "+toMakeToNet+" "
		+ (toMakeToNet==1? "eenheid":"eenheden")
		+" te maken heeft u de volgende hoeveelheden nodig: ", 0));
//	printModel.add(new PrintableNewline());
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableNewline());
	
	/*
	 * Print the ingredient list
	 */
	ArrayList<PrintableHorizontalLineObject> headers = new ArrayList<PrintableHorizontalLineObject>();
	headers.add(new PrintableString("Ingrediënt", tabs[0]));
	headers.add(new PrintableString("Verpakking", tabs[1]));
	headers.add(new PrintableString("Stuks", tabs[2]));
	headers.add(new PrintableString("Kg", tabs[3]));
	printModel.add(new PrintableMulti(headers));
//	printModel.add(new PrintableNewline());
	
	printModel.add(new PrintableLine(0, imageableWidth));
//	printModel.add(new PrintableNewline());
	
	double sum = 0.0;
	
	for (Component component : model.getComponents().values()) {
	    
	    ArrayList<PrintableHorizontalLineObject> comp = new ArrayList<PrintableHorizontalLineObject>();
	    
	    comp.add(new PrintableString(StringTools.capitalize(StringTools.clip(component.getIngredient().getName(), ingrNameLength)), tabs[0]));
	    comp.add(new PrintableString(StringTools.capitalize(StringTools.clip(component.getIngredient().getPackaging(), 15)), tabs[1]));
	    comp.add(new PrintableString(component.getFormattedUnits(), tabs[2]));
	    double quantity = component.getQuantity();
	    String s = three.format(quantity);
	    int chars = s.substring(0, s.indexOf(".")).length();
	    comp.add(new PrintableString(three.format(quantity), tabs[3]-chars*charWidth));
	    printModel.add(new PrintableMulti(comp));
	    
//	    printModel.add(new PrintableNewline());
	    
	    sum+=quantity;
	}
	/*
	 * Print total gross weight
	 */
	String s = three.format(sum);
	int chars = s.substring(0, s.indexOf(".")).length();
	printModel.add(new PrintableLine(tabs[3]-chars*charWidth, tabs[3]+charWidth*4));
	printModel.add(new PrintableString(three.format(sum), tabs[3]-chars*charWidth));
//	printModel.add(new PrintableNewline());
	
	if (!model.getPreparation().isEmpty()) {
	    /*
	    * Print the preparation
	    */
	   printModel.add(new PrintableNewline());
	   printModel.add(new PrintableNewline());
	   printModel.add(new PrintableString("BEREIDINGSWIJZE:", charWidth*10));
//	   printModel.add(new PrintableNewline());
	   printModel.add(new PrintableNewline());


	   /*
	    * Split into paragraphs, separated by '\n'
	    */
	   String[] paragraphs = model.getPreparation().split("\n");
	   System.out.println("PrintableRecipe:: Split into "+paragraphs.length+" paragraphs!");

	   for (String paragraph : paragraphs) {
	       String[] words = paragraph.split(" ");

	       /*
		* Data structure for the words to print in this paragraph
		*/
	       Stack<String> wordsToPrint = new Stack<String>();
	       // add all words to a stack in the correct order (first word on top)
	       for (int i = words.length - 1 ; i >= 0; i--) {
		   wordsToPrint.push(words[i]);
	       }

	       System.out.println("PrintableRecipe::  Split line into "+words.length+" words!");

	       int xOffset = 0;
	       StringBuilder builder = new StringBuilder();

	       /*
		* Handle words in paragraph in order
		*/
	       while (!wordsToPrint.isEmpty()){
		   String word = wordsToPrint.pop();
		   int length = fontMetrics.charsWidth(word.toCharArray(), 0, word.length());

		   System.out.println("PrintableRecipe::\t Handling word: "+word+", l="+length);

		   /*
		    * If the word does not fit the line, split it.
		    * (I honestly don't care about proper hyphenation -.-)
		    */
		   if (length > imageableWidth) {
		       int spaceLeftOnLine = (imageableWidth-fontMetrics.charWidth('-')) - xOffset;	// save 1 charWidth for hyphen
		       String part1 = word.substring(0, spaceLeftOnLine);
		       String part2 = word.substring(spaceLeftOnLine);
		       builder.append(part1).append('-');
		       wordsToPrint.push(part2);
		       builder = new StringBuilder();
		       xOffset = 0;
//		       printModel.add(new PrintableNewline());
//		       System.out.println("PrintableRecipe:: Split a word...");
		   } else {
		       if (xOffset + length <= imageableWidth) {
			   builder.append(word).append(" ");
			   xOffset += length+charWidth;
		       } else {
			   printModel.add(new PrintableString(builder.toString(), 0));
//			   printModel.add(new PrintableNewline());
			   builder = new StringBuilder(word+" ");
			   xOffset = length+charWidth;
//			   System.out.println("PrintableRecipe:: NEW LINE!");
		       }
		   }
	       }

	       // empty the buffer
	       printModel.add(new PrintableString(builder.toString(), 0));
//	       printModel.add(new PrintableNewline());
//	       System.out.println("PrintableRecipe:: NEW LINE!");
	   }

	   printModel.add(new PrintableNewline());
	}
	
	return printModel;
    }

    @Override
    public List<PrintableHorizontalLineObject> transformFooter(int width, FontMetrics fontMetrics) {
	/*
	 * There is no footer
	 */
	return new ArrayList<PrintableHorizontalLineObject>();
    }
    
}
