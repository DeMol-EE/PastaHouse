/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer;

import java.awt.print.Printable;
import java.awt.print.PrinterJob;

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
    
    public void setPrintJob(Printable pj){
	job.setPrintable(pj);
    }
    
    public void tryPrint(){
	// ask for a page dialog to format the page?
//	job.pageDialog(job.defaultPage());
	
	boolean shouldPrint = job.printDialog();
	
	System.out.println("Print dialog returned "+shouldPrint);
	
	if (shouldPrint) {
	    try{
		job.print();
		System.out.println("Finished printing!");
	    } catch (Exception e){
		System.err.println("Printing failed with exception:\n"+e.getMessage());
	    }
	}
    }
}