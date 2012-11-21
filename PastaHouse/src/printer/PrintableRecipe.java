/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer;

import database.Component;
import database.Recipe;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;
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
	/*
	 * Calculate the ratio of net to gross for this recipe
	 */
	double grossToNet = recipe.getGrossWeight()/recipe.getNetWeight();
	double grossToMake = grossToNet * toMake;
	
	double toMakeToNet = toMake/recipe.getNetWeight();
	
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
	int[] tabs = new int[]{5,ingrNameLength*charWidth,50*charWidth,65*charWidth};
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
		"Om "+platters+" eenheden te maken heeft u de volgende hoeveelheden nodig: ", x, y);
	y+=lineHeight;
	y+=lineHeight;
	y+=lineHeight;
	
	/*
	 * Print the ingredient list
	 */
	graphics.drawString("Ingrediënt", x+tabs[0], y);
	graphics.drawString("Verpakking", x+tabs[1], y);
	graphics.drawString("Stuks", x+tabs[2], y);
	graphics.drawString("Kg", x+tabs[3]+charWidth*3, y);
	y+=lineHeight;
	
	graphics.drawLine(x, y, x+imageableWidth, y);
	y+=lineHeight;
	
	double sum = 0.0;
	
	for (Component component : recipe.getIngredients().values()) {
//	    graphics.drawString(StringTools.capitalize(StringTools.padClip(component.getIngredient().getName(), '.', ingrNameLength-1)), x+tabs[0], y);
	    graphics.drawString(StringTools.capitalize(StringTools.clip(component.getIngredient().getName(), ingrNameLength)), x+tabs[0], y);
	    graphics.drawString(StringTools.capitalize(StringTools.clip(component.getIngredient().getPackaging(), 15)), x+tabs[1], y);
	    graphics.drawString(two.format(component.getPieces()), x+tabs[2], y);
	    graphics.drawString(three.format(component.getGrossQuantity()*toMakeToNet), x+tabs[3], y);
	    
	    y+=lineHeight;
	    
	    sum+=component.getGrossQuantity()*toMakeToNet;
	}
	/*
	 * Print total gross weight
	 */
	graphics.drawLine(x+tabs[3], y-10, x+tabs[3]+charWidth*6, y-10);
	graphics.drawString(three.format(sum), x+tabs[3], y);
	y+=lineHeight;
	
	/*
	 * Print the preparation
	 */
	// split the preparation in lines
	y+=lineHeight;
	y+=lineHeight;
	graphics.drawString("BEREIDINGSWIJZE:", x+charWidth*10, y);
	y+=lineHeight;
	y+=lineHeight;
	graphics.drawString(recipe.getPreparation(), x, y);

	// tell the caller that this page is part of the printed document
	return PAGE_EXISTS;
    }
    
}
