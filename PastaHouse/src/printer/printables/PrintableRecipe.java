/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer.printables;

import database.extra.Component;
import database.tables.Recipe;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;
import java.util.Stack;
import tools.StringTools;

/**
 *
 * @author Warkst
 */
public class PrintableRecipe implements Printable{

    private final Recipe recipe;
    private double toMake;
    private boolean isWeight;
    
    int[] pageBreaks;
    String[] lines;
    
    public PrintableRecipe(Recipe recipe){
	this(recipe, 1.0, false);
    }
    
    public PrintableRecipe(Recipe recipe, double toMake, boolean isWeight){
	this.recipe = new Recipe(recipe);
	this.toMake = toMake;
	this.isWeight = isWeight;
    }

    public Recipe getRecipe() {
	return recipe;
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
	return isWeight? toMake/recipe.getNetWeight() : toMake;
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
	
	System.out.println("PrintableRecipe:: PRINTING PAGE "+pageIndex);
	
	double toMakeToNet = getToMakeToNet();
	
	// The recipe should fit on one page
	if (pageIndex > 0) {
	    return NO_SUCH_PAGE;
	}

	// Get the system's monospaced font and calculate the font's line height
	Font mono = new Font("Monospaced", Font.PLAIN, 12);
	FontMetrics metrics = graphics.getFontMetrics(mono);
	int lineHeight = metrics.getHeight();
//	int charWidth = metrics.getWidths()[0]; // monospaced font has same width for all chars
//	int[] charWidths = metrics.getWidths();
	int charWidth = 7;
	
	
	
//	if (pageBreaks == null) {
//	    // split ERRYTHING in lines
//	    
//	    
//	    // calculate page breaks
//	    int linesPerPage = (int)(pageFormat.getImageableHeight()/lineHeight);
//            int numBreaks = (lines.length-1)/linesPerPage;
//            pageBreaks = new int[numBreaks];
//            for (int b=0; b<numBreaks; b++) {
//                pageBreaks[b] = (b+1)*linesPerPage; 
//            }
//	}

	int ingrNameLength = 30;
	int[] tabs = new int[]{5,ingrNameLength*charWidth,50*charWidth,68*charWidth};
	int x = (int)pageFormat.getImageableX();
	int y = (int)pageFormat.getImageableY();
	DecimalFormat three = new DecimalFormat("0.000");
	DecimalFormat two = new DecimalFormat("0.00");
	
	Graphics2D g2d = (Graphics2D) graphics;
	g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
	int imageableWidth = (int)pageFormat.getImageableWidth() - 50;

	graphics.setFont(mono);
	
//	int y = 0; 
//        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
//        int end   = (pageIndex == pageBreaks.length)
//                         ? lines.length : pageBreaks[pageIndex];
//        for (int line=start; line<end; line++) {
//            y += lineHeight;
//            graphics.drawString(lines[line], 0, y);
//        }

	
	// Now we perform our rendering
//	graphics.drawString("Hello world!", 100, 100);
	
	// Draw a horizontal divider
//	graphics.drawLine((int)pageFormat.getImageableX(), 100+lineHeight+10, 
//		(int)(pageFormat.getImageableX()+pageFormat.getImageableWidth()), 100+lineHeight+10);
	
	/*
	 * Print a pretty header
	 */
	// print the name of the recipe in the middle
	// print something like: for preparing amount x, you need ...
	y+=lineHeight;
	
	graphics.drawString("Recept "+recipe.getName().toUpperCase(), x+charWidth*10, y);
	y+=lineHeight;
	y+=lineHeight;
	
	graphics.drawString(isWeight?"Om "+two.format(toMake)+" kg te bereiden heeft u de volgende hoeveelheden nodig:" : 
		"Om "+toMakeToNet+" "
		+ (toMakeToNet==1? "eenheid":"eenheden")
		+" te maken heeft u de volgende hoeveelheden nodig: ", x, y);
	y+=lineHeight;
	y+=lineHeight;
	y+=lineHeight;
	
	/*
	 * Print the ingredient list
	 */
	graphics.drawString("Ingrediënt", x+tabs[0], y);
	graphics.drawString("Verpakking", x+tabs[1], y);
	graphics.drawString("Stuks", x+tabs[2], y);
	graphics.drawString("Kg", x+tabs[3], y);
	y+=lineHeight;
	
	graphics.drawLine(x, y, x+imageableWidth, y);
	y+=lineHeight;
	
	double sum = 0.0;
	
	for (Component component : recipe.getComponents().values()) {
//	    graphics.drawString(StringTools.capitalize(StringTools.padClip(component.getIngredient().getName(), '.', ingrNameLength-1)), x+tabs[0], y);
	    graphics.drawString(StringTools.capitalize(StringTools.clip(component.getIngredient().getName(), ingrNameLength)), x+tabs[0], y);
	    graphics.drawString(StringTools.capitalize(StringTools.clip(component.getIngredient().getPackaging(), 15)), x+tabs[1], y);
//	    graphics.drawString(two.format(component.getUnits()), x+tabs[2], y);
	    graphics.drawString(component.getFormattedUnits(), x+tabs[2], y);
//	    double quantity = component.getGrossQuantity()*toMakeToNet;
	    double quantity = component.getQuantity();
	    String s = three.format(quantity);
	    int chars = s.substring(0, s.indexOf(".")).length();
	    
	    graphics.drawString(three.format(quantity), x+tabs[3]-chars*charWidth, y);
	    
	    y+=lineHeight;
	    
	    sum+=quantity;
	}
	/*
	 * Print total gross weight
	 */
	String s = three.format(sum);
	int chars = s.substring(0, s.indexOf(".")).length();
	graphics.drawLine(x+tabs[3]-chars*charWidth, y-10, x+tabs[3]+charWidth*4, y-10);
	graphics.drawString(three.format(sum), x+tabs[3]-chars*charWidth, y);
	y+=lineHeight;
	
	if (!recipe.getPreparation().isEmpty()) {
	    /*
	    * Print the preparation
	    */
	   y+=lineHeight;
	   y+=lineHeight;
	   graphics.drawString("BEREIDINGSWIJZE:", x+charWidth*10, y);
	   y+=lineHeight;
	   y+=lineHeight;


	   /*
	    * Split into paragraphs, separated by '\n'
	    */
	   String[] paragraphs = recipe.getPreparation().split("\n");
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
		   int length = metrics.charsWidth(word.toCharArray(), 0, word.length());

		   System.out.println("PrintableRecipe::\t Handling word: "+word+", l="+length);

		   /*
		    * If the word does not fit the line, split it.
		    * (I honestly don't care about proper hyphenation -.-)
		    */
		   if (length > imageableWidth) {
		       int spaceLeftOnLine = (imageableWidth-metrics.charWidth('-')) - xOffset;	// save 1 charWidth for hyphen
		       String part1 = word.substring(0, spaceLeftOnLine);
		       String part2 = word.substring(spaceLeftOnLine);
		       builder.append(part1).append('-');
		       wordsToPrint.push(part2);
		       builder = new StringBuilder();
		       xOffset = 0;
		       y += lineHeight;
		       System.out.println("PrintableRecipe:: Split a word...");
		   } else {
		       if (xOffset + length <= imageableWidth) {
			   builder.append(word).append(" ");
			   xOffset += length+charWidth;
		       } else {
			   graphics.drawString(builder.toString(), x, y);
			   y+=lineHeight;
			   builder = new StringBuilder(word+" ");
			   xOffset = length+charWidth;
			   System.out.println("PrintableRecipe:: NEW LINE!");
		       }
		   }
	       }

	       // empty the buffer
	       graphics.drawString(builder.toString(), x, y);
	       y+=lineHeight; // new line
	       System.out.println("PrintableRecipe:: NEW LINE!");
	   }

	   y+=lineHeight;
	}
	
	// tell the caller that this page is part of the printed document
	return PAGE_EXISTS;
    }
}
