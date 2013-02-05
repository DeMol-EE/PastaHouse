/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer.printables;

import database.extra.InvoiceItem;
import database.tables.Invoice;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts an invoice to a printable, so the printer can print it.
 * 
 * ASSUMPTION: Invoices can always fit on one page.
 *
 * @author Warkst
 */
public class PrintableInvoice implements Printable{
    
    private final Invoice model;
    
    private Font font;
    private int lineHeight;
    private int linesPerPage;
    
    public PrintableInvoice(Invoice model){
	this.model = model;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
	System.out.println("PrintableInvoice \""+model.getNumber()+"\" received print request");
	
	if (font==null) {
	    this.font = new Font("Serif", Font.PLAIN, 12);
	    this.lineHeight = g.getFontMetrics(font).getHeight();
	    this.linesPerPage = (int)(pf.getImageableHeight()/lineHeight);
	}
	
	Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
	
	int y = 0;
	int width = (int)pf.getImageableWidth();
	width-=5; // correction...
	int half = (int)(pf.getImageableWidth()/2);
	half-=2;
	
	int[] tabs = new int[]{0, half, 3*width/5, 4*width/5, width};
	
	/*
	 * Print header
	 */
	g.drawLine(half, y, width, y);
	y+=lineHeight;
	y+=lineHeight;
	g.drawString("FACTUUR: "+model.getNumber(), half+3, y);
	String dateString = "DATUM: "+model.getDate();
	int dateAnchor = width - g.getFontMetrics(font).charsWidth(dateString.toCharArray(), 0, dateString.length());
	g.drawString(dateString, dateAnchor-15, y);
	y+=lineHeight;
	g.drawLine(half, y, width, y);
	y+=lineHeight;
	
	/*
	 * Print client information
	 */
	y+=lineHeight;
	y+=lineHeight;
	y+=lineHeight;
	
	g.drawString(model.getClient().getContact(), half+3, y);
	y+=lineHeight;
	
	y+=lineHeight;
	
	g.drawString(model.getClient().getAddress(), half+3, y);
	y+=lineHeight;
	
	y+=lineHeight;
	
	g.drawString(model.getClient().getZipcode()+" "+model.getClient().getMunicipality(), half+3, y);
	y+=lineHeight;
	
	y+=lineHeight;
	y+=lineHeight;
	g.drawString(model.getClient().getTaxnumber(), half+3, y);
	y+=lineHeight;
	
	y+=lineHeight;
	y+=lineHeight;
	y+=lineHeight;
	
	/*
	 * Print article header
	 */
	g.drawLine(0, y, width, y);
	y+=lineHeight;
	y+=lineHeight;
	g.drawString("Omschrijving artikels", tabs[0], y);
	g.drawString("BTW", tabs[1], y);
	g.drawString("Hoeveelheid", tabs[2], y);
	g.drawString("Prijs", tabs[3], y);
	g.drawString("Totaal", tabs[4]-g.getFontMetrics(font).charsWidth("Totaal".toCharArray(), 0, "Totaal".length()), y);
	y+=lineHeight;
	g.drawLine(0, y, width, y);
	y+=lineHeight;
	y+=lineHeight;
	
	/*
	 * Print invoice articles (InvoiceItems)
	 */
	DecimalFormat threeFormatter = new DecimalFormat("0,000");
	DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
	otherSymbols.setDecimalSeparator(',');
	otherSymbols.setGroupingSeparator('.'); 
	threeFormatter.setDecimalFormatSymbols(otherSymbols);
	
	for (InvoiceItem invoiceItem : model.items()) {
	    /*
	     * Group per savings?
	     */
	    g.drawString(invoiceItem.getArticle().getName(), tabs[0], y);
	    g.drawString(threeFormatter.format(invoiceItem.getArticle().getTaxes()), tabs[1], y);
	    g.drawString(invoiceItem.getAmount()+" "+invoiceItem.getArticle().getUnit(), tabs[2], y);
	    g.drawString(threeFormatter.format(invoiceItem.getArticle().getPriceForCode(model.getPriceCode())), tabs[3], y);
	    g.drawString(threeFormatter.format(invoiceItem.getArticle().getPriceForCode(model.getPriceCode())*invoiceItem.getAmount()), tabs[4], y);
	    
	    y+=lineHeight;
	    y+=lineHeight;
	}
	
	
	
	/*
	 * Print savings at bottom: set y from bottom upwards
	 */
	
	Map<Double, List<InvoiceItem>> categories = new HashMap<Double, List<InvoiceItem>>();
	
	for (InvoiceItem invoiceItem : model.items()) {
	    if (categories.containsKey(invoiceItem.getArticle().getTaxes())) {
		categories.get(invoiceItem.getArticle().getTaxes()).add(invoiceItem);
	    } else {
		List<InvoiceItem> items = new ArrayList<InvoiceItem>();
		items.add(invoiceItem);
		categories.put(invoiceItem.getArticle().getTaxes(), items);
	    }
	}
	
	y = (linesPerPage-11)*lineHeight;
	if (model.getSave()>0) {
	    y -= 7*lineHeight;
	    
	    /*
	     * Print savings
	     */
	}
	
	
	g.drawLine(0, y, width, y);
	y+=lineHeight;
	y+=lineHeight;
	g.drawString("BTW %", 0, y);
	for (Double savings : categories.keySet()) {
	    
	}
	
	y+=lineHeight;
	y+=lineHeight;
	g.drawString("Excl.", 0, y);
	for (Double savings : categories.keySet()) {
	    
	}
	
	y+=lineHeight;
	y+=lineHeight;
	g.drawString("BTW", 0, y);
	for (Double savings : categories.keySet()) {
	    
	}
	y+=lineHeight;
	
	g.drawLine(0, y, width, y);
	y+=lineHeight;
	y+=lineHeight;
	g.drawString("Totaal", 0, y);
	for (Double savings : categories.keySet()) {
	    
	}
	
	y+=lineHeight;
	g.drawLine(0, y, width, y);
	
	return PAGE_EXISTS;
    }   
}