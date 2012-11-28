/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer;

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
import utilities.StringTools;

/**
 *
 * @author Warkst
 */
public class PrintableRecipe implements Printable{

    private final Recipe recipe;
    private final double toMake;
    private double platters;
    private final boolean weight;
    
    public PrintableRecipe(Recipe recipe, double quantity){
	this.recipe = recipe;
	this.toMake = quantity;
	this.weight = true;
    }
    
    public PrintableRecipe(Recipe recipe, double quantity, double amount){
	this.recipe = recipe;
	this.toMake = quantity;
	this.platters = amount;
	this.weight = false;
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
	
	System.out.println("PrintableRecipe:: PRINTING PAGE "+pageIndex);
	
//	if (!weight) {
//	    toMake = platters * recipe.getNetWeight();
//	}
	
	/*
	 * Calculate how much we need to make in total and for each ingredient
	 */
	double grossToNet = recipe.getGrossWeight()/recipe.getNetWeight();
	double grossToMake = grossToNet * toMake;
	
	double toMakeToNet = toMake/recipe.getNetWeight();
	
	double grossToMakeToNet = grossToMake/recipe.getNetWeight();
	
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

	int ingrNameLength = 30;
	int[] tabs = new int[]{5,ingrNameLength*charWidth,50*charWidth,68*charWidth};
	int x = (int)pageFormat.getImageableX();
	int y = (int)pageFormat.getImageableY();
	DecimalFormat three = new DecimalFormat("0.000");
	DecimalFormat two = new DecimalFormat("0.00");
	
	
//	double pageHeight = pageFormat.getImageableHeight();

//	int linesPerPage = ((int) pageHeight) / lineHeight);
//	int numBreaks = (textLines.length - 1) / linesPerPage;
//	int[] pageBreaks = new int[numBreaks];
//	for (int b = 0; b < numBreaks; b++) {
//	    pageBreaks[b] = (b + 1) * linesPerPage;
//	}

	// User (0,0) is typically outside the
	// imageable area, so we must translate
	// by the X and Y values in the PageFormat
	// to avoid clipping.
//	int oldX = (int)pageFormat.getImageableX();
	Graphics2D g2d = (Graphics2D) graphics;
	g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
//	int newX = (int)pageFormat.getImageableX();
//	int imageableWidth = (int)(pageFormat.getImageableWidth()-2*Math.abs(newX-oldX));
	int imageableWidth = (int)pageFormat.getImageableWidth() - 50;

	// Set the printing font to the monospaced font
	graphics.setFont(mono);
	
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
	
	graphics.drawString(weight?"Om "+two.format(toMake)+" kg te bereiden heeft u de volgende hoeveelheden nodig:" : 
		"Om "+platters+" "
		+ (platters==1? "eenheid":"eenheden")
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
	
	for (Component component : recipe.getIngredients().values()) {
//	    graphics.drawString(StringTools.capitalize(StringTools.padClip(component.getIngredient().getName(), '.', ingrNameLength-1)), x+tabs[0], y);
	    graphics.drawString(StringTools.capitalize(StringTools.clip(component.getIngredient().getName(), ingrNameLength)), x+tabs[0], y);
	    graphics.drawString(StringTools.capitalize(StringTools.clip(component.getIngredient().getPackaging(), 15)), x+tabs[1], y);
	    graphics.drawString(two.format(component.getUnits()*toMakeToNet), x+tabs[2], y);
//	    double quantity = component.getGrossQuantity()*toMakeToNet;
	    double quantity = component.getQuantity()*toMakeToNet;
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
	
//	int prepLength = recipe.getPreparation().length();
//	
////	System.out.println("preparation: "+recipe.getPreparation());
////	byte[] bytes = recipe.getPreparation().getBytes();
////	System.out.println("length: "+prepLength+", bytes: "+bytes.length);
//	int xOffset = 0;
//	StringBuilder sb = new StringBuilder("");
//	for (int i = 0; i < prepLength; i++) {
//	    char toAppend = recipe.getPreparation().charAt(i);
////	    char toAppend = (char)bytes[i];
//	    
////	    System.out.println("Writing char: "+toAppend + ", w="+metrics.charWidth(toAppend));
//	    
//	    if (toAppend == '\n') {
//		xOffset = 0;
//		graphics.drawString(sb.toString(), x, y);
//		sb = new StringBuilder();   // carry char to next line
//		y+=lineHeight;
//		System.out.println("newline");
//	    } else {
//		if (xOffset + metrics.charWidth(toAppend) < imageableWidth) {
//		    sb.append(toAppend);
//		    xOffset += metrics.charWidth(toAppend);
//		} else {
//		    xOffset = 0;
//		    graphics.drawString(sb.toString(), x, y);
//		    sb = new StringBuilder("");	// carry char to next line
//		    sb.append(toAppend);
//		    y+=lineHeight;
//		    System.out.println("newline");
//		}
//	    }
//	}
//	// empty the remaining buffer
//	graphics.drawString(sb.toString(), x, y);
//	y+=lineHeight;
//	y+=lineHeight;
	
//	graphics.drawString(recipe.getPreparation(), x, y);

	// tell the caller that this page is part of the printed document
	return PAGE_EXISTS;
    }
    
}
