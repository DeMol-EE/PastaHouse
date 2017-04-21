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
import printer.adts.PrintableLargeString;
import printer.adts.PrintableLine;
import printer.adts.PrintableMulti;
import printer.adts.PrintableNewline;
import printer.adts.PrintableString;

/**
 *
 * @author Warkst
 */
public class PrintableInvoice extends MyPrintable {
	private DecimalFormat threeFormatter = new DecimalFormat("0.000");
	private DecimalFormat twoFormatter = new DecimalFormat("0.00");
	private DecimalFormat oneFormatter = new DecimalFormat("0.0");

	/*
	 * Data model.
	 */
	private final Invoice model;

	public PrintableInvoice(Invoice model) {
		super(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		this.model = model;

		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator(',');
		otherSymbols.setGroupingSeparator('.');
		this.threeFormatter.setDecimalFormatSymbols(otherSymbols);
		this.twoFormatter.setDecimalFormatSymbols(otherSymbols);
		this.oneFormatter.setDecimalFormatSymbols(otherSymbols);
//	this.threeFormatter.setGroupingUsed(true);
//	this.threeFormatter.setGroupingSize(3);
//	this.twoFormatter.setGroupingUsed(true);
//	this.twoFormatter.setGroupingSize(3);
//	this.oneFormatter.setGroupingUsed(true);
//	this.oneFormatter.setGroupingSize(3);
	}

	@Override
	public List<PrintableHorizontalLineObject> transformBody(int lineHeight, int width, int margin, FontMetrics fontMetrics) {
		/*
		 * Formatting variables
		 */
		int half = width / 2;
		width -= 10; // small correction...
		half -= 5;
		// tabs
		int LEFT = 0;
		int LOT = 2 * width / 5;
		int BTW = half;
		int AMOUNT = 3 * width / 5 + 10;
		int PRICE = 4 * width / 5 + 5;
		int TOTAL = width - 9;
		//int[] tabs = new int[]{0, half, 3*width/5+10, 4*width/5+5, width-9};
		//int[] tabs = new int[]{LEFT, LOT, BTW, AMOUNT, PRICE, TOTAL};
		int threeZeroesWidth = fontMetrics.charsWidth(",000".toCharArray(), 0, 4);
		int commaWidth = fontMetrics.charsWidth(",".toCharArray(), 0, 1);
		int zeroWidth = fontMetrics.charsWidth("0".toCharArray(), 0, 1);

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
		printModel.add(new PrintableNewline(lineHeight / 2));
		printModel.add(new PrintableLine(lineHeight, half, width));
		printModel.add(new PrintableNewline(lineHeight));

		ArrayList<PrintableHorizontalLineObject> header = new ArrayList<PrintableHorizontalLineObject>();

		header.add(new PrintableString(lineHeight, "FACTUUR: " + model.getNumber(), half + 3));
		String dateString = "DATUM : " + model.getDate();
		int dateAnchor = width - fontMetrics.charsWidth(dateString.toCharArray(), 0, dateString.length()) - 5;
		header.add(new PrintableString(lineHeight, dateString, dateAnchor - 15));

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
		printModel.add(new PrintableLargeString(lineHeight, model.getClient().getFirm(), half + 3));

		printModel.add(new PrintableNewline(lineHeight));

		printModel.add(new PrintableLargeString(lineHeight, model.getClient().getAddress(), half + 3));

		printModel.add(new PrintableNewline(lineHeight));

		printModel.add(new PrintableLargeString(lineHeight, model.getClient().getZipcode() + " " + model.getClient().getMunicipality(), half + 3));

		printModel.add(new PrintableNewline(lineHeight));
		printModel.add(new PrintableNewline(lineHeight));
		printModel.add(new PrintableNewline(lineHeight));
		printModel.add(new PrintableString(lineHeight, "BE 0" + model.getClient().getTaxnumber(), half + 3));

		printModel.add(new PrintableNewline(lineHeight));
		printModel.add(new PrintableNewline(lineHeight));
		printModel.add(new PrintableNewline(lineHeight));

		/*
		 * Print article header
		 */
		printModel.add(new PrintableLine(lineHeight, 0, width));
		printModel.add(new PrintableNewline(lineHeight));
		ArrayList<PrintableHorizontalLineObject> group = new ArrayList<PrintableHorizontalLineObject>();
		group.add(new PrintableString(lineHeight, "Omschrijving artikels", LEFT));
		group.add(new PrintableString(lineHeight, "Lot", LOT));
		group.add(new PrintableString(lineHeight, "BTW", BTW - commaWidth - zeroWidth * 2));
		group.add(new PrintableString(lineHeight, "Hoeveelheid", AMOUNT - threeZeroesWidth));
		group.add(new PrintableString(lineHeight, "Prijs", PRICE));
		group.add(new PrintableString(lineHeight, "Totaal", TOTAL - fontMetrics.charsWidth("Totaal".toCharArray(), 0, "Totaal".length())));
		printModel.add(new PrintableMulti(lineHeight, group));
		printModel.add(new PrintableLine(lineHeight, 0, width));
		printModel.add(new PrintableNewline(lineHeight));

		/*
		 * Print invoice articles (InvoiceItems)
		 */
//	int offsetPix = fontMetrics.charsWidth("00".toCharArray(), 0, 2) - fontMetrics.charWidth('0');
//	int offsetPix = fontMetrics.charsWidth("00".toCharArray(), 0, 2) - zeroWidth;
//	int offsetPix = zeroWidth+2;
		int offsetPix = zeroWidth;

		for (InvoiceItem invoiceItem : model.items()) {
			/*
			 * Group per savings?
			 */
			ArrayList<PrintableHorizontalLineObject> ii = new ArrayList<PrintableHorizontalLineObject>();
			ii.add(new PrintableString(lineHeight, invoiceItem.getArticlename(), LEFT));
			
			String tax = oneFormatter.format(invoiceItem.getTaxes());
			String[] tp = tax.split(",");
			int offset = tp[0].length();
			for (char c : tp[0].toCharArray()) {
				ii.add(new PrintableString(lineHeight, "" + c, BTW - offsetPix * offset));
				offset--;
			}
//	    ii.add(new PrintableString(lineHeight, tp[0], tabs[1]-commaWidth-fontMetrics.charsWidth(tp[0].toCharArray(), 0, tp[0].length())));
			ii.add(new PrintableString(lineHeight, "," + tp[1], BTW));
			
			ii.add(new PrintableString(lineHeight, invoiceItem.getLot(), LOT));

			String amount = threeFormatter.format(invoiceItem.getAmount());
			String[] ap = amount.split(",");
			offset = ap[0].length();
			for (char c : ap[0].toCharArray()) {
				ii.add(new PrintableString(lineHeight, "" + c, AMOUNT - offsetPix * offset));
				offset--;
			}
//	    ii.add(new PrintableString(lineHeight, ap[0], tabs[2]-commaWidth-fontMetrics.charsWidth(ap[0].toCharArray(), 0, ap[0].length())));
			ii.add(new PrintableString(lineHeight, "," + ap[1] + " " + invoiceItem.getArticle().getUnit(), AMOUNT));

			String price = threeFormatter.format(invoiceItem.getPrice());
			String[] pp = price.split(",");
			offset = pp[0].length();
			for (char c : pp[0].toCharArray()) {
				ii.add(new PrintableString(lineHeight, "" + c, PRICE - offsetPix * offset));
				offset--;
			}
//	    ii.add(new PrintableString(lineHeight, pp[0], tabs[3]-commaWidth-fontMetrics.charsWidth(pp[0].toCharArray(), 0, pp[0].length())));
			ii.add(new PrintableString(lineHeight, "," + pp[1], PRICE));

			String tot = threeFormatter.format(invoiceItem.getPrice() * invoiceItem.getAmount());
			String[] parts = tot.split(",");
			offset = parts[0].length();
			for (char c : parts[0].toCharArray()) {
				ii.add(new PrintableString(lineHeight, "" + c, TOTAL - threeZeroesWidth - offsetPix * offset));
				offset--;
			}
//	    ii.add(new PrintableString(lineHeight, parts[0], tabs[4]-commaWidth-threeZeroesWidth-fontMetrics.charsWidth(parts[0].toCharArray(), 0, parts[0].length())));
			ii.add(new PrintableString(lineHeight, "," + parts[1], TOTAL - threeZeroesWidth));

			printModel.add(new PrintableMulti(lineHeight, ii));

			printModel.add(new PrintableNewline(lineHeight / 2));
		}

		return printModel;
	}

	@Override
	public List<PrintableHorizontalLineObject> transformFooter(int lineHeight, int width, int margin, FontMetrics fontMetrics) {
		width -= 10;
		int threeZeroesWithCommaWidth = fontMetrics.charsWidth(",000".toCharArray(), 0, 4);
		int commaWidth = fontMetrics.charsWidth(",".toCharArray(), 0, 1);
		int zeroWidth = fontMetrics.charsWidth("0".toCharArray(), 0, 1);
//	int offsetPix = zeroWidth+2;
		int offsetPix = zeroWidth;
		int offset = 0;

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
			if (categories.containsKey(invoiceItem.getTaxes())) {
				categories.get(invoiceItem.getTaxes()).add(invoiceItem);
			} else {
				List<InvoiceItem> items = new ArrayList<InvoiceItem>();
				items.add(invoiceItem);
				categories.put(invoiceItem.getTaxes(), items);
			}
		}

		int[] tabs = new int[categories.size() + 3];
		tabs[0] = margin;
		int base = 30;
		for (int i = 0; i < categories.size(); i++) {
			tabs[i + 1] = margin + 80 * (i + 1);
		}
//	tabs[1]-=30;
		tabs[tabs.length - 2] = 4 * width / 5 - 35;
		tabs[tabs.length - 1] = width - 9;

		int threeZeroesWidth = fontMetrics.charsWidth("000".toCharArray(), 0, 3);

		if (model.getSave() > 0) {
			printModel.add(new PrintableNewline(lineHeight));

			/*
			 * BTW
			 */
			printModel.add(new PrintableLine(lineHeight, 0, width));
			printModel.add(new PrintableNewline(lineHeight / 2));
			ArrayList<PrintableHorizontalLineObject> savingsCategories = new ArrayList<PrintableHorizontalLineObject>();
			savingsCategories.add(new PrintableString(lineHeight, "BTW %", 0));
			int index = 1;
			for (Double savings : categories.keySet()) {
				String printMe = oneFormatter.format(savings) + " %";
				String[] pp = printMe.split(",");
				offset = pp[0].length();
				for (char c : pp[0].toCharArray()) {
					savingsCategories.add(new PrintableString(lineHeight, "" + c, tabs[index] - threeZeroesWithCommaWidth - offsetPix * offset));
					offset--;
				}
				savingsCategories.add(new PrintableString(lineHeight, "," + pp[1], tabs[index] - threeZeroesWithCommaWidth));
//		savingsCategories.add(new PrintableString(lineHeight, printMe, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(printMe.toCharArray(), 0, printMe.length()-4)));
				index++;
			}
			printModel.add(new PrintableMulti(lineHeight, savingsCategories));

			/*
			 * Net before save
			 */
			printModel.add(new PrintableNewline(lineHeight / 2));
			ArrayList<PrintableHorizontalLineObject> prices = new ArrayList<PrintableHorizontalLineObject>();
			ArrayList<Double> netPrices = new ArrayList<Double>();
			prices.add(new PrintableString(lineHeight, "Bedrag", 0));
			index = 1;
			for (List<InvoiceItem> list : categories.values()) {
				double price = 0;
				for (InvoiceItem invoiceItem : list) {
					price += invoiceItem.getPrice() * invoiceItem.getAmount();
				}
				netPrices.add(price);
				String pr = threeFormatter.format(price);
				String[] pp = pr.split(",");
				System.out.println("n 0: " + pp[0]);
				System.out.println("n 1: " + pp[1]);
				offset = pp[0].length();
				for (char c : pp[0].toCharArray()) {
					prices.add(new PrintableString(lineHeight, "" + c, tabs[index] - threeZeroesWithCommaWidth - offsetPix * offset));
					offset--;
				}
				prices.add(new PrintableString(lineHeight, "," + pp[1], tabs[index] - threeZeroesWithCommaWidth));
//		prices.add(new PrintableString(lineHeight, pr, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(pr.toCharArray(), 0, pr.length()-4)));
				index++;
			}
			printModel.add(new PrintableMulti(lineHeight, prices));

			/*
			 * Save %
			 */
			printModel.add(new PrintableNewline(lineHeight / 2));
			ArrayList<PrintableHorizontalLineObject> savings = new ArrayList<PrintableHorizontalLineObject>();
			ArrayList<Double> saved = new ArrayList<Double>();
			savings.add(new PrintableString(lineHeight, "- " + model.getSave() + "%", 0));
			index = 1;
			for (List<InvoiceItem> list : categories.values()) {
				double sav = netPrices.get(index - 1) * model.getSave() / 100;
				saved.add(sav);
				String pr = threeFormatter.format(sav);
				String[] pp = pr.split(",");
				offset = pp[0].length();
				for (char c : pp[0].toCharArray()) {
					savings.add(new PrintableString(lineHeight, "" + c, tabs[index] - threeZeroesWithCommaWidth - offsetPix * offset));
					offset--;
				}
				savings.add(new PrintableString(lineHeight, "," + pp[1], tabs[index] - threeZeroesWithCommaWidth));
//		savings.add(new PrintableString(lineHeight, pr, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(pr.toCharArray(), 0, pr.length()-4)));
				index++;
			}
			printModel.add(new PrintableMulti(lineHeight, savings));

			/*
			 * Net after save
			 */
			printModel.add(new PrintableNewline(lineHeight / 2));
			ArrayList<PrintableHorizontalLineObject> savedPrices = new ArrayList<PrintableHorizontalLineObject>();
			savedPrices.add(new PrintableString(lineHeight, "Netto", 0));
			index = 1;
			for (List<InvoiceItem> list : categories.values()) {
				double price = netPrices.get(index - 1) - saved.get(index - 1);
				netPrices.add(price);
				String pr = threeFormatter.format(price);
				String[] pp = pr.split(",");
				offset = pp[0].length();
				for (char c : pp[0].toCharArray()) {
					savedPrices.add(new PrintableString(lineHeight, "" + c, tabs[index] - threeZeroesWithCommaWidth - offsetPix * offset));
					offset--;
				}
				savedPrices.add(new PrintableString(lineHeight, "," + pp[1], tabs[index] - threeZeroesWithCommaWidth));
//		savedPrices.add(new PrintableString(lineHeight, pr, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(pr.toCharArray(), 0, pr.length()-4)));
				index++;
			}
			printModel.add(new PrintableMulti(lineHeight, savedPrices));
		}

		/*
		 * BTW %
		 */
		printModel.add(new PrintableLine(lineHeight, 0, width));
		printModel.add(new PrintableNewline(lineHeight / 2));
		ArrayList<PrintableHorizontalLineObject> savingsCategories = new ArrayList<PrintableHorizontalLineObject>();
		savingsCategories.add(new PrintableString(lineHeight, "BTW %", 0));
		int index = 1;
		for (Double savings : categories.keySet()) {
			String printMe = oneFormatter.format(savings) + " %";
			String[] pp = printMe.split(",");
			offset = pp[0].length();
			for (char c : pp[0].toCharArray()) {
				savingsCategories.add(new PrintableString(lineHeight, "" + c, tabs[index] - threeZeroesWithCommaWidth - offsetPix * offset));
				offset--;
			}
			savingsCategories.add(new PrintableString(lineHeight, "," + pp[1], tabs[index] - threeZeroesWithCommaWidth));
//	    savingsCategories.add(new PrintableString(lineHeight, printMe, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(printMe.toCharArray(), 0, printMe.length()-4)));
			index++;
		}
		printModel.add(new PrintableMulti(lineHeight, savingsCategories));

		/*
		 * Net after save
		 */
		printModel.add(new PrintableNewline(lineHeight / 2));
		ArrayList<PrintableHorizontalLineObject> prices = new ArrayList<PrintableHorizontalLineObject>();
		prices.add(new PrintableString(lineHeight, "Excl.", 0));
		index = 1;
		double pricesTot = 0.0;
		for (Double price : model.netAfterSave()) {
			pricesTot += price;
			String pr = threeFormatter.format(price);
			String[] prp = pr.split(",");
			offset = prp[0].length();
			for (char c : prp[0].toCharArray()) {
				prices.add(new PrintableString(lineHeight, "" + c, tabs[index] - threeZeroesWithCommaWidth - offsetPix * offset));
				offset--;
			}
			prices.add(new PrintableString(lineHeight, "," + prp[1], tabs[index] - threeZeroesWithCommaWidth));
//	    prices.add(new PrintableString(lineHeight, pr, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(pr.toCharArray(), 0, pr.length()-4)));
			index++;
		}
		prices.add(new PrintableString(lineHeight, "Tot. Excl.", tabs[tabs.length - 2]));
		String prTot = threeFormatter.format(pricesTot);
		String[] ptp = prTot.split(",");
		offset = ptp[0].length();
		for (char c : ptp[0].toCharArray()) {
			prices.add(new PrintableString(lineHeight, "" + c, tabs[tabs.length - 1] - threeZeroesWithCommaWidth - offsetPix * offset));
			offset--;
		}
		prices.add(new PrintableString(lineHeight, "," + ptp[1], tabs[tabs.length - 1] - threeZeroesWithCommaWidth));
//	prices.add(new PrintableString(lineHeight, prTot, tabs[tabs.length-1]-fontMetrics.charsWidth(prTot.toCharArray(), 0, prTot.length())));
		printModel.add(new PrintableMulti(lineHeight, prices));

		/*
		 * BTW price
		 */
		printModel.add(new PrintableNewline(lineHeight / 2));
		ArrayList<PrintableHorizontalLineObject> taxes = new ArrayList<PrintableHorizontalLineObject>();
		taxes.add(new PrintableString(lineHeight, "BTW", 0));
		index = 1;
		double taxesTot = 0.0;
		List<Double> nets = model.netAfterSave();
		List<Double> adds = new ArrayList<Double>();
		for (Map.Entry<Double, List<InvoiceItem>> entry : categories.entrySet()) {
			double tax = nets.get(index - 1) * entry.getKey() / 100;
			taxesTot += tax;
			String t = threeFormatter.format(tax);
			String[] tp = t.split(",");
			offset = tp[0].length();
			for (char c : tp[0].toCharArray()) {
				taxes.add(new PrintableString(lineHeight, "" + c, tabs[index] - threeZeroesWithCommaWidth - offsetPix * offset));
				offset--;
			}
			taxes.add(new PrintableString(lineHeight, "," + tp[1], tabs[index] - threeZeroesWithCommaWidth));
//	    taxes.add(new PrintableString(lineHeight, t, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth(t.toCharArray(), 0, t.length()-4)));
			index++;
			adds.add(tax);
		}
		taxes.add(new PrintableString(lineHeight, "BTW", tabs[tabs.length - 2]));
		String tTot = threeFormatter.format(taxesTot);
		String[] totp = tTot.split(",");
		offset = totp[0].length();
		for (char c : totp[0].toCharArray()) {
			taxes.add(new PrintableString(lineHeight, "" + c, tabs[tabs.length - 1] - threeZeroesWithCommaWidth - offsetPix * offset));
			offset--;
		}
		taxes.add(new PrintableString(lineHeight, "," + totp[1], tabs[tabs.length - 1] - threeZeroesWithCommaWidth));
//	taxes.add(new PrintableString(lineHeight, tTot, tabs[tabs.length-1]-fontMetrics.charsWidth(tTot.toCharArray(), 0, tTot.length())));
		printModel.add(new PrintableMulti(lineHeight, taxes));

		/*
		 * Totals
		 */
		printModel.add(new PrintableLine(lineHeight, 0, width));
		printModel.add(new PrintableNewline(lineHeight / 2));
		ArrayList<PrintableHorizontalLineObject> totals = new ArrayList<PrintableHorizontalLineObject>();
		totals.add(new PrintableString(lineHeight, "Totaal", 0));
		double total = 0.0;
		index = 1;
		for (Map.Entry<Double, List<InvoiceItem>> entry : categories.entrySet()) {
			double tot = nets.get(index - 1) + adds.get(index - 1);
			total += tot;
			String t = twoFormatter.format(tot);
			String[] ttp = t.split(",");
			offset = ttp[0].length();
			for (char c : ttp[0].toCharArray()) {
				totals.add(new PrintableString(lineHeight, "" + c, tabs[index] - threeZeroesWithCommaWidth - offsetPix * offset));
				offset--;
			}
			totals.add(new PrintableString(lineHeight, "," + ttp[1], tabs[index] - threeZeroesWithCommaWidth));
//	    totals.add(new PrintableString(lineHeight, t, tabs[index]+threeZeroesWidth-fontMetrics.charsWidth((t+"0").toCharArray(), 0, t.length()-3)));
			index++;
		}
		totals.add(new PrintableString(lineHeight, "TOTAAL", tabs[tabs.length - 2]));
		String tot = twoFormatter.format(total);
		String[] tp = tot.split(",");
		offset = tp[0].length();
		for (char c : tp[0].toCharArray()) {
			totals.add(new PrintableString(lineHeight, "" + c, tabs[tabs.length - 1] - threeZeroesWithCommaWidth - offsetPix * offset));
			offset--;
		}
		totals.add(new PrintableString(lineHeight, "," + tp[1], tabs[tabs.length - 1] - threeZeroesWithCommaWidth));
//	totals.add(new PrintableString(lineHeight, tot, tabs[tabs.length-1]-fontMetrics.charsWidth((tot+"0").toCharArray(), 0, tot.length()+1)));
		printModel.add(new PrintableMulti(lineHeight, totals));

		printModel.add(new PrintableLine(0, 0, width));

		/*
		 * Keep 7 white lines from the bottom
		 */
		printModel.add(new PrintableNewline(lineHeight));
		printModel.add(new PrintableNewline(lineHeight));
		printModel.add(new PrintableNewline(lineHeight));
		printModel.add(new PrintableNewline(lineHeight));
		printModel.add(new PrintableNewline(lineHeight));

//	printModel.add(new PrintableNewline());
//	printModel.add(new PrintableNewline());
		return printModel;
	}
}
