/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import javax.print.attribute.Attribute;

/**
 *
 * @author Warkst
 */
public class Printer {
    
    private static Printer instance;
    private final PrinterJob job;
    
    private Printer(){
	job = PrinterJob.getPrinterJob();
    }
    
    public static Printer driver(){
	if (instance==null) {
	    instance = new Printer();
	}
	return instance;
    }
    
    /**
     * Sets a book of printables as print task for the printer.
     * 
     * @param b The book (sequence of printables) to print.
     */
    public void setPrintableBook(Book b){
	job.setPageable(b);
    }
    
    /**
     * Sets a single printable as print task for the printer.
     * 
     * @param pj The printable to print.
     */
    public void setPrintableJob(Printable pj){
	job.setPrintable(pj);
    }
    
    public void setPrintableJob(Printable pj, PageFormat pf){
	job.setPrintable(pj, pf);
    }
    
    public void tryPrint(){
	// ask for a page dialog to format the page?
//	job.pageDialog(job.defaultPage());
	
	boolean shouldPrint = job.printDialog();
	
	System.out.println("Print dialog returned "+shouldPrint);
	
	if (shouldPrint) {
	    try{
		job.print();
		
		for (Attribute attribute : job.getPrintService().getAttributes().toArray()) {
		    System.out.println(attribute.getName()+": "+attribute.toString());
		}
		
		System.out.println("Finished printing!");
	    } catch (Exception e){
		System.err.println("Printing failed with exception:\n"+e.getMessage());
	    }
	}
    }
}