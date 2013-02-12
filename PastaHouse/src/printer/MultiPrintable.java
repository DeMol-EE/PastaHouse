/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Warkst
 */
public class MultiPrintable implements Printable{

    private List<Printable> collection;
    private int index = 0;
    private int printedPages = 0;
    
    public MultiPrintable(){
	this.collection = new ArrayList<Printable>();
    }
    
    public void add(Printable p){
	collection.add(p);
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
	
	System.out.println(">>Multiprintable printing page index "+pageIndex+"\nChecking page "+index);
	
	if (index>=collection.size()) {
	    System.out.println(">>index out of document range!");
	    return NO_SUCH_PAGE;
	}
	pageIndex-=printedPages;
	System.out.println(">>Recalculated page index to "+pageIndex);
	try{
	    int result = collection.get(index).print(graphics, pageFormat, pageIndex);
	    if (result != NO_SUCH_PAGE) {
		System.out.println(">> >>Printed page!");
		return PAGE_EXISTS;
	    } else {
		printedPages=pageIndex;
		index++;
		System.out.println(">> >>Finished printing a document of "+pageIndex+" pages, trying if there is a next doc...");
		return print(graphics, pageFormat, pageIndex);
	    }
	} catch(Exception e){
	    e.printStackTrace();
	    return NO_SUCH_PAGE;
	}
    }
    
}
