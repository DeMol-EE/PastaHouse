/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer.printables;

import database.extra.InvoiceItem;
import database.tables.Invoice;
import java.awt.Font;
import java.awt.FontMetrics;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import printer.adts.PrintableHorizontalLineObject;
import printer.adts.PrintableLine;
import printer.adts.PrintableMulti;
import printer.adts.PrintableNewline;
import printer.adts.PrintableString;

/**
 *
 * @author Warkst
 */
public class PrintableInvoiceNew extends MyPrintable{

    /*
     * Data model.
     */
    private final Invoice model;
    
    public PrintableInvoiceNew(Invoice model) {
	super(new Font("Serif", Font.PLAIN, 12));
	this.model = model;
    }
    
    @Override
    public List<PrintableHorizontalLineObject> transformBody(int width, int margin, FontMetrics fontMetrics) {
	/*
	 * Formatting variables
	 */
	int half = width/2;
	width-=5; // small correction...
	half-=2;
	int[] tabs = new int[]{0, half, 3*width/5, 4*width/5, width};
	
	/*
	 * Incremental print model
	 */
	List<PrintableHorizontalLineObject> printModel = new ArrayList<PrintableHorizontalLineObject>();
	
	/*
	 * Actual transformation
	 * Start by printing header
	 */
	printModel.add(new PrintableLine(half, width));
	printModel.add(new PrintableNewline());
	
	ArrayList<PrintableHorizontalLineObject> header = new ArrayList<PrintableHorizontalLineObject>();
	
	header.add(new PrintableString("FACTUUR: "+model.getNumber(), half+3));
	String dateString = "DATUM: "+model.getDate();
	int dateAnchor = width - fontMetrics.charsWidth(dateString.toCharArray(), 0, dateString.length());
	header.add(new PrintableString(dateString, dateAnchor-15));
	
	printModel.add(new PrintableMulti(header));
	
	printModel.add(new PrintableLine(half, width));
	
	/*
	 * Print client information
	 */
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableNewline());
	
	printModel.add(new PrintableString(model.getClient().getContact(), half+3));
	
	printModel.add(new PrintableNewline());
	
	printModel.add(new PrintableString(model.getClient().getAddress(), half+3));
	
	printModel.add(new PrintableNewline());
	
	printModel.add(new PrintableString(model.getClient().getZipcode()+" "+model.getClient().getMunicipality(), half+3));
	
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableString(model.getClient().getTaxnumber(), half+3));
	
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableNewline());
	
	/*
	 * Print article header
	 */
	printModel.add(new PrintableLine(0, width));
	printModel.add(new PrintableNewline());
	ArrayList<PrintableHorizontalLineObject> group = new ArrayList<PrintableHorizontalLineObject>();
	group.add(new PrintableString("Omschrijving artikels", tabs[0]));
	group.add(new PrintableString("BTW", tabs[1]));
	group.add(new PrintableString("Hoeveelheid", tabs[2]));
	group.add(new PrintableString("Prijs", tabs[3]));
	group.add(new PrintableString("Totaal", tabs[4]-fontMetrics.charsWidth("Totaal".toCharArray(), 0, "Totaal".length())));
	printModel.add(new PrintableMulti(group));
	printModel.add(new PrintableLine(0, width));
	printModel.add(new PrintableNewline());
	
	/*
	 * Print invoice articles (InvoiceItems)
	 */
	DecimalFormat threeFormatter = new DecimalFormat("0,000");
	DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
	otherSymbols.setDecimalSeparator(',');
	otherSymbols.setGroupingSeparator('.'); 
	threeFormatter.setDecimalFormatSymbols(otherSymbols);
	
	for (InvoiceItem invoiceItem : model.items().values()) {
	    /*
	     * Group per savings?
	     */
	    ArrayList<PrintableHorizontalLineObject> ii = new ArrayList<PrintableHorizontalLineObject>();
	    ii.add(new PrintableString(invoiceItem.getArticle().getName(), tabs[0]));
	    ii.add(new PrintableString(threeFormatter.format(invoiceItem.getTaxes()), tabs[1]));
	    ii.add(new PrintableString(invoiceItem.getAmount()+" "+invoiceItem.getArticle().getUnit(), tabs[2]));
	    ii.add(new PrintableString(threeFormatter.format(invoiceItem.getArticle().getPriceForCode(model.getPriceCode())), tabs[3]));
	    ii.add(new PrintableString(threeFormatter.format(invoiceItem.getArticle().getPriceForCode(model.getPriceCode())*invoiceItem.getAmount()), tabs[4]));
	    printModel.add(new PrintableMulti(ii));
	    
	    printModel.add(new PrintableNewline());
	}
	
	return printModel;
    }

    @Override
    public List<PrintableHorizontalLineObject> transformFooter(int width, int margin, FontMetrics fontMetrics) {
	/*
	 * Incremental print model
	 */
	List<PrintableHorizontalLineObject> printModel = new ArrayList<PrintableHorizontalLineObject>();
	
	/*
	 * Actual transformation
	 * Print savings at bottom: set y from bottom upwards
	 */
	
	Map<Double, List<InvoiceItem>> categories = new HashMap<Double, List<InvoiceItem>>();
	
	for (InvoiceItem invoiceItem : model.items().values()) {
	    if (categories.containsKey(invoiceItem.getTaxes())) {
		categories.get(invoiceItem.getTaxes()).add(invoiceItem);
	    } else {
		List<InvoiceItem> items = new ArrayList<InvoiceItem>();
		items.add(invoiceItem);
		categories.put(invoiceItem.getTaxes(), items);
	    }
	}
	
	if (model.getSave()>0) {
	    
	    printModel.add(0, new PrintableNewline());
	    
	    /*
	     * Print savings
	     */
	}
	
	
	printModel.add(new PrintableLine(0, width));
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableString("BTW %", 0));
	for (Double savings : categories.keySet()) {
	    
	}
	
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableString("Excl.", 0));
	for (Double savings : categories.keySet()) {
	    
	}
	
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableString("BTW", 0));
	for (Double savings : categories.keySet()) {
	    
	}
	
	printModel.add(new PrintableLine(0, width));
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableString("Totaal", 0));
	for (Double savings : categories.keySet()) {
	    
	}
	
	printModel.add(new PrintableLine(0, width));
	
	
	/*
	 * Keep 7 white lines from the bottom
	 */
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableNewline());
	
	printModel.add(new PrintableNewline());
	printModel.add(new PrintableNewline());
	
	return printModel;
    }
}
