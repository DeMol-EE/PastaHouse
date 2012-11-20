/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer;

import database.Recipe;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 *
 * @author Warkst
 */
public class PrintableRecipe implements Printable{

    private final Recipe recipe;
    
    public PrintableRecipe(Recipe recipe){
	this.recipe = recipe;
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
	throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
