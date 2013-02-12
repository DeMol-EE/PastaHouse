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
    private DecimalFormat threeFormatter = new DecimalFormat("0.000");
    private DecimalFormat twoFormatter = new DecimalFormat("0.00");
    private DecimalFormat oneFormatter = new DecimalFormat("0.0");
    
    /*
     * Data model.
     */
    private final Invoice model;
    
    public PrintableInvoiceNew(Invoice model) {
	super(new Font("Sans-Serif", Font.PLAIN, 8));
	this.model = model;
	
	DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
	otherSymbols.setDecimalSeparator(',');
	otherSymbols.setGroupingSeparator('.'); 
	this.threeFormatter.setDecimalFormatSymbols(otherSymbols);
	this.twoFormatter.setDecimalFormatSymbols(otherSymbols);
	this.oneFormatter.setDecimalFormatSymbols(otherSymbols);
    }
    
    @Override
    public List<PrintableHorizontalLineObject> transformBody(int lineHeight, int width, int margin, FontMetrics fontMetrics) {
	/*
	 * Formatting variables
	 */
	int half = width/2;
	width-=10; // small correction...
	half-=5;
	int[] tabs = new int[]{0, half, 3*width/5, 4*width/5, width-9};
	
	/*
	 * Incremental print model
	 */
	List<PrintableHorizontalLineObject> printModel = new ArrayList<PrintableHorizontalLineObject>();
	
	/*
	 * Actual transformation
	 * Start by printing header
	 */
	printModel.add(new PrintableNewline(lineHeight));
	printModel.add(new PrintableNewline(lineHeight));
	printModel.add(new PrintableNewline(lineHeight));
	printModel.add(new PrintableNewline(lineHeight/2));
	printModel.add(new PrintableLine(lineHeight, half, width));
	printModel.add(new PrintableNewline(lineHeight));
	
	ArrayList<PrintableHorizontalLineObject> header = new ArrayList<PrintableHorizontalLineObject>();
	
	header.add(new PrintableString(lineHeight, "FACTUUR: "+model.getNumber(), half+3));
	String dateString = "DATUM : "+model.getDate();
	int dateAnchor = width - fontMetrics.charsWidth(dateString.toCharArray(), 0, dateString.length()) - 10;
	header.add(new PrintableString(lineHeight, dateString, dateAnchor-15));
	
	printModel.add(new PrintableMulti(lineHeight, header));
	
	printModel.add(new PrintableLine(lineHeight, half, width));
	
	/*
	 * Print client information
	 */
	printModel.add(new PrintableNewline(lineHeight));
	printModel.add(new PrintableNewline(lineHeight));
	printModel.add(new PrintableNewline(lineHeight));
	printModel.add(new PrintableNewline(lineHeight));
	printModel.add(new PrintableNewline(lineHeight));
	
//	printModel.add(new PrintableString(model.getClient().getContact(), half+3));
	printModel.add(new PrintableString(lineHeight, model.getClient().getFirm(), half+3));
	
	printModel.add(new PrintableNewline(lineHeight));
	
	printModel.add(new PrintableString(lineHeight, model.getClient().getAddress(), half+3));
	
	printModel.add(new PrintableNewline(lineHeight));
	
	printModel.add(new PrintableString(lineHeight, model.getClient().getZipcode()+" "+model.getClient().getMunicipality(), half+3));
	
	printModel.add(new PrintableNewline(lineHeight));
	printModel.add(new PrintableNewline(lineHeight));
	printModel.add(new PrintableNewline(lineHeight));
	printModel.add(new PrintableString(lineHeight, "BE 0"+model.getClient().getTaxnumber(), half+3));
	
	printModel.add(new PrintableNewline(lineHeight));
	printModel.add(new PrintableNewline(lineHeight));
	printModel.add(new PrintableNewline(lineHeight));
	
	/*
	 * Print article header
	 */
	printModel.add(new PrintableLine(lineHeight, 0, width));
	printModel.add(new PrintableNewline(lineHeight));
	ArrayList<PrintableHorizontalLineObject> group = new ArrayList<PrintableHorizontalLineObject>();
	group.add(new PrintableString(lineHeight, "Omschrijving artikels", tabs[0]));
	group.add(new PrintableString(lineHeight, "BTW", tabs[1]));
	group.add(new PrintableString(lineHeight, "Hoeveelheid", tabs[2]));
	group.add(new PrintableString(lineHeight, "Prijs", tabs[3]));
	group.add(new PrintableString(lineHeight, "Totaal", tabs[4]-fontMetrics.charsWidth("Totaal".toCharArray(), 0, "Totaal".length())));
	printModel.add(new PrintableMulti(lineHeight, group));
	printModel.add(new PrintableLine(lineHeight, 0, width));
	printModel.add(new PrintableNewline(lineHeight));
	
	/*
	 * Print invoice articles (InvoiceItems)
	 */
	for (InvoiceItem invoiceItem : model.items()) {
	    /*
	     * Group per savings?
	     */
	    ArrayList<PrintableHorizontalLineObject> ii = new ArrayList<PrintableHorizontalLineObject>();
	    ii.add(new PrintableString(lineHeight, invoiceItem.getArticle().getName(), tabs[0]));
	    ii.add(new PrintableString(lineHeight, oneFormatter.format(invoiceItem.getArticle().getTaxes()), tabs[1]));
	    ii.add(new PrintableString(lineHeight, threeFormatter.format(invoiceItem.getAmount())+" "+invoiceItem.getArticle().getUnit(), tabs[2]));
	    ii.add(new PrintableString(lineHeight, threeFormatter.format(invoiceItem.getArticle().getPriceForCode(model.getPriceCode())), tabs[3]));
	    
	    String tot = threeFormatter.format(invoiceItem.getArticle().getPriceForCode(model.getPriceCode())*invoiceItem.getAmount());
	    
	    ii.add(new PrintableString(lineHeight, tot, tabs[4]-5-fontMetrics.charsWidth(tot.toCharArray(), 0, tot.length())));
//	    ii.add(new PrintableString(lineHeight, tot, tabs[4]-15-fontMetrics.charsWidth(",000".toCharArray(), 0, ",000".length())));
	    printModel.add(new PrintableMulti(lineHeight, ii));
	    
	    printModel.add(new PrintableNewline(lineHeight/2));
	}
	
	return printModel;
    }

    @Override
    public List<PrintableHorizontalLineObject> transformFooter(int lineHeight, int width, int margin, FontMetrics fontMetrics) {
	width-=10;
	
	/*
	 * Incremental print model
	 */
	List<PrintableHorizontalLineObject> printModel = new ArrayList<PrintableHorizontalLineObject>();
	
	/*
	 * Formatting variables
	 */
//	int[] tabs = new int[]{0};
	
	/*
	 * Actual transformation
	 * Print savings at bottom: set y from bottom upwards
	 * Group the invoice items per tax value
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
	
	int[] tabs = new int[categories.size()+3];
	tabs[0] = margin;
	int base = 30;
	for (int i = 0; i < categories.size(); i++) {
	    tabs[i+1] = margin + 60*(i+1);
	}
	tabs[1]-=30;
	tabs[tabs.length-2] = 4*width/5-35;
	tabs[tabs.length-1] = width-9;
	
	int threeZeroesWidth = fontMetrics.charsWidth("000".toCharArray(), 0 , 3);
	
	if (model.getSave()>0) {
	    printModel.add(new PrintableNewline(lineHeight));
	    
	    /*
	     * BTW
	     */
	    printModel.add(new PrintableLine(lineHeight, 0, width));
	    printModel.add(new PrintableNewline(lineHeight/2));
	    ArrayList<PrintableHorizontalLineObject> savingsCategories = new ArrayList<PrintableHorizontalLineObject>();
	    savingsCategories.add(new PrintableString(lineHeight, "BTW %", 0));
	    int index = 1;
	    for (Double savings : categories.keySet()) {
		String printMe = oneFormatter.format(savings)+" %";
		savingsCategories.add(new PrintableString(lineHeight, printMe, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(printMe.toCharArray(), 0, printMe.length()-4)));
		index++;
	    }
	    printModel.add(new PrintableMulti(lineHeight, savingsCategories));
	    
	    /*
	     * Net before save
	     */
	    printModel.add(new PrintableNewline(lineHeight/2));
	    ArrayList<PrintableHorizontalLineObject> prices = new ArrayList<PrintableHorizontalLineObject>();
	    ArrayList<Double> netPrices = new ArrayList<Double>();
	    prices.add(new PrintableString(lineHeight, "Bedrag", 0));
	    index = 1;
	    for (List<InvoiceItem> list : categories.values()) {
		double price = 0;
		for (InvoiceItem invoiceItem : list) {
		    price += invoiceItem.getArticle().getPriceForCode(model.getPriceCode())*invoiceItem.getAmount();
		}
		netPrices.add(price);
		String pr = threeFormatter.format(price);
		prices.add(new PrintableString(lineHeight, pr, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(pr.toCharArray(), 0, pr.length()-4)));
		index++;
	    }
	    printModel.add(new PrintableMulti(lineHeight, prices));
	    
	    /*
	     * Save %
	     */
	    printModel.add(new PrintableNewline(lineHeight/2));
	    ArrayList<PrintableHorizontalLineObject> savings = new ArrayList<PrintableHorizontalLineObject>();
	    ArrayList<Double> saved = new ArrayList<Double>();
	    savings.add(new PrintableString(lineHeight, "- "+model.getSave()+"%", 0));
	    index = 1;
	    for (List<InvoiceItem> list : categories.values()) {
		double sav = netPrices.get(index-1) * model.getSave()/100;
		saved.add(sav);
		String pr = threeFormatter.format(sav);
		savings.add(new PrintableString(lineHeight, pr, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(pr.toCharArray(), 0, pr.length()-4)));
		index++;
	    }
	    printModel.add(new PrintableMulti(lineHeight, savings));
	    
	    /*
	     * Net after save
	     */
	    printModel.add(new PrintableNewline(lineHeight/2));
	    ArrayList<PrintableHorizontalLineObject> savedPrices = new ArrayList<PrintableHorizontalLineObject>();
	    savedPrices.add(new PrintableString(lineHeight, "Netto", 0));
	    index = 1;
	    for (List<InvoiceItem> list : categories.values()) {
		double price = netPrices.get(index-1)-saved.get(index-1);
		netPrices.add(price);
		String pr = threeFormatter.format(price);
		savedPrices.add(new PrintableString(lineHeight, pr, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(pr.toCharArray(), 0, pr.length()-4)));
		index++;
	    }
	    printModel.add(new PrintableMulti(lineHeight, savedPrices));
	}
	
	/*
	 * BTW %
	 */
	printModel.add(new PrintableLine(lineHeight, 0, width));
	printModel.add(new PrintableNewline(lineHeight/2));
	ArrayList<PrintableHorizontalLineObject> savingsCategories = new ArrayList<PrintableHorizontalLineObject>();
	savingsCategories.add(new PrintableString(lineHeight, "BTW %", 0));
	int index = 1;
	for (Double savings : categories.keySet()) {
	    String printMe = oneFormatter.format(savings)+" %";
	    savingsCategories.add(new PrintableString(lineHeight, printMe, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(printMe.toCharArray(), 0, printMe.length()-4)));
	    index++;
	}
	printModel.add(new PrintableMulti(lineHeight, savingsCategories));
	
	/*
	 * Net after save
	 */
	printModel.add(new PrintableNewline(lineHeight/2));
	ArrayList<PrintableHorizontalLineObject> prices = new ArrayList<PrintableHorizontalLineObject>();
	prices.add(new PrintableString(lineHeight, "Excl.", 0));
	index = 1;
	double pricesTot = 0.0;
	for (Double price : model.netAfterSave()) {
	    pricesTot+= price;
	    String pr = threeFormatter.format(price);
	    prices.add(new PrintableString(lineHeight, pr, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(pr.toCharArray(), 0, pr.length()-4)));
	    index++;
	}
	prices.add(new PrintableString(lineHeight, "Tot. Excl.", tabs[tabs.length-2]));
	String prTot = threeFormatter.format(pricesTot);
	prices.add(new PrintableString(lineHeight, prTot, tabs[tabs.length-1]-fontMetrics.charsWidth(prTot.toCharArray(), 0, prTot.length())));
	printModel.add(new PrintableMulti(lineHeight, prices));
	
	/*
	 * BTW price
	 */
	printModel.add(new PrintableNewline(lineHeight/2));
	ArrayList<PrintableHorizontalLineObject> taxes = new ArrayList<PrintableHorizontalLineObject>();
	taxes.add(new PrintableString(lineHeight, "BTW", 0));
	index = 1;
	double taxesTot = 0.0;
	List<Double> nets = model.netAfterSave();
	List<Double> adds = new ArrayList<Double>();
	for (Map.Entry<Double, List<InvoiceItem>> entry : categories.entrySet()) {
	    double tax = nets.get(index-1) * entry.getKey()/100;
	    taxesTot+= tax;
	    String t = threeFormatter.format(tax);
	    taxes.add(new PrintableString(lineHeight, t, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(t.toCharArray(), 0, t.length()-4)));
	    index++;
	    adds.add(tax);
	}
	taxes.add(new PrintableString(lineHeight, "BTW", tabs[tabs.length-2]));
	String tTot = threeFormatter.format(taxesTot);
	taxes.add(new PrintableString(lineHeight, tTot, tabs[tabs.length-1]-fontMetrics.charsWidth(tTot.toCharArray(), 0, tTot.length())));
	printModel.add(new PrintableMulti(lineHeight, taxes));
	
	/*
	 * Totals
	 */
	printModel.add(new PrintableLine(lineHeight, 0, width));
	printModel.add(new PrintableNewline(lineHeight/2));
	ArrayList<PrintableHorizontalLineObject> totals = new ArrayList<PrintableHorizontalLineObject>();
	totals.add(new PrintableString(lineHeight, "Totaal", 0));
	double total = 0.0;
	index = 1;
	for (Map.Entry<Double, List<InvoiceItem>> entry : categories.entrySet()) {
	    double tot = nets.get(index-1)+adds.get(index-1);
	    total+=tot;
	    String t = twoFormatter.format(tot);
	    totals.add(new PrintableString(lineHeight, t, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth((t+"0").toCharArray(), 0, t.length()-3)));
	    index++;
	}
	totals.add(new PrintableString(lineHeight, "TOTAAL", tabs[tabs.length-2]));
	String tot = twoFormatter.format(total);
	totals.add(new PrintableString(lineHeight, tot, tabs[tabs.length-1]-fontMetrics.charsWidth((tot+"0").toCharArray(), 0, tot.length()+1)));
	printModel.add(new PrintableMulti(lineHeight, totals));
	
	printModel.add(new PrintableLine(0, 0, width));
	
	
	/*
	 * Keep 7 white lines from the bottom
	 */
//	printModel.add(new PrintableNewline(lineHeight));
//	printModel.add(new PrintableNewline(lineHeight));
//	printModel.add(new PrintableNewline(lineHeight));
//	printModel.add(new PrintableNewline(lineHeight));
//	printModel.add(new PrintableNewline(lineHeight));
	
//	printModel.add(new PrintableNewline());
//	printModel.add(new PrintableNewline());
	
	return printModel;
    }
}
