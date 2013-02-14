package printer.printables;


import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import printer.adts.PrintableHorizontalLineObject;
import printer.adts.PrintableNewline;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Warkst
 */
public abstract class MyPrintable implements Printable{
    /*
     * Printing model.
     * Calculated once and used for printing callbacks.
     * 
     */
    private Map<Integer, List<PrintableHorizontalLineObject>> printablesPerPage;
    private List<PrintableHorizontalLineObject> printModel;
    private final Font font;
    private int[] pageBreaks;
    
    private int pages;
    
    public MyPrintable() {
	this(new Font("SansSerif", Font.PLAIN, 12));
    }
    
    public MyPrintable(Font font) {
	this.font = font;
    }

    @Override
    public final int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
	System.out.println("Received print request for page: "+pageIndex);
	/*
	 * Prepare the "canvas"
	 */
	Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
	g.setFont(font);
	
	System.out.println(font.toString()+" - "+font.getFamily()+"; "+font.getFontName());
	
	int lineHeight = g.getFontMetrics(font).getHeight();
	
	if (printablesPerPage == null) {
	    printablesPerPage = new TreeMap<Integer, List<PrintableHorizontalLineObject>>();
	    /*
	     * Calculate how many lines fit on one page
	     */
	    int linesPerPage = (int)(pf.getImageableHeight()/lineHeight);
	    
	    /*
	     * Transform the data model to the printing model
	     */
	    List<PrintableHorizontalLineObject> printModelBody = transformBody(lineHeight, (int)pf.getImageableWidth(), (int)pf.getImageableX(), g.getFontMetrics(font));
	    List<PrintableHorizontalLineObject> printModelFooter = transformFooter(lineHeight, (int)pf.getImageableWidth(), (int)pf.getImageableY(), g.getFontMetrics(font));
	    
	    /*
	     * Calculate the amount of needed pages
	     */
	    int totalLines = printModelBody.size() + printModelFooter.size();
	    int bodyHeight = 0;
	    for (PrintableHorizontalLineObject printableHorizontalLineObject : printModelBody) {
		bodyHeight+=printableHorizontalLineObject.height();
	    }
	    int footerHeight = 0;
	    for (PrintableHorizontalLineObject printableHorizontalLineObject : printModelFooter) {
		footerHeight+=printableHorizontalLineObject.height();
	    }
	    int totalHeight = bodyHeight+footerHeight;
//	    int amountOfPages = (int)Math.ceil((1.0*totalHeight)/pf.getImageableHeight());
//	    int amountOfPages = (int)Math.ceil((1.0*totalLines)/linesPerPage);
//	    int totalGrossLines = amountOfPages*linesPerPage;
	    
//	    pages = amountOfPages;
	    
//	    System.out.println("Total lines: "+totalLines);
//	    System.out.println("\tat "+linesPerPage+" per page, makes "+amountOfPages+" pages");
//	    System.out.println("\tso there will be "+totalGrossLines+" lines.");
//	    System.out.println("\tBreakdown: body="+printModelBody.size()+", footer="+printModelFooter.size()+", filling="+(totalGrossLines-totalLines));
	    
	    int amountOfPages = (int)Math.ceil((1.0*totalHeight)/pf.getImageableHeight());
	    int fillerHeight = amountOfPages * (int)pf.getImageableHeight() - bodyHeight - footerHeight;
	    
//	    int amountOfPages = 1;
	    
//	    int fillerHeight = (int)pf.getImageableHeight() - bodyHeight - footerHeight;
//	    while (fillerHeight<0){
//		fillerHeight+=(int)pf.getImageableHeight();
//		amountOfPages++;
//	    }
	    
	    System.out.println("Total height: "+totalHeight);
	    System.out.println("\tat "+(int)pf.getImageableHeight()+" per page makes "+amountOfPages+" pages");
	    System.out.println("\tBreakdown: body height="+bodyHeight+", footer="+footerHeight+", filling="+fillerHeight);
	    
	    /*
	     * Combine the body and footer into one model, padding with newlines in the space between
	     */
	    int printHeight = 0;
	    printModel = new ArrayList<PrintableHorizontalLineObject>();
	    for (int line = 0; line < printModelBody.size(); line++) {
		printModel.add(printModelBody.get(line));
		printHeight+=printModelBody.get(line).height();
	    }
	    if (!printModelFooter.isEmpty()) {
//		while (printModel.size() < totalGrossLines-printModelFooter.size()) {
//		    printModel.add(new PrintableNewline(lineHeight));
//		}
//		for (int footerLine = 0; footerLine < printModelFooter.size(); footerLine++) {
//		    printModel.add(printModelFooter.get(footerLine));
//		}
		int currentHeight = bodyHeight;
		while (fillerHeight>lineHeight){
		    if (currentHeight+lineHeight<=(int)pf.getImageableHeight()) {
			printModel.add(new PrintableNewline(lineHeight));
			currentHeight+=lineHeight;
		    } else {
			currentHeight = 0;
		    }
		    fillerHeight-=lineHeight;
		}
		if (fillerHeight%lineHeight!=0) {
		    fillerHeight-=fillerHeight%lineHeight;
		}
		for (int footerLine = 0; footerLine < printModelFooter.size(); footerLine++) {
		    printModel.add(printModelFooter.get(footerLine));
		}
	    } else {
		System.err.println("No footer to print!");
	    }
	    
	    int h = 0;
	    for (PrintableHorizontalLineObject printableHorizontalLineObject : printModel) {
		h+=printableHorizontalLineObject.height();
	    }
	    System.out.println("Total height with fillers: "+h);
	    
	    /*
	     * Calculate page breaks
	     */
	    
	    int height = 0;
	    int page = 0;
	    ArrayList<PrintableHorizontalLineObject> printables = new ArrayList<PrintableHorizontalLineObject>();
	    printablesPerPage.put(0, printables);
	    for (PrintableHorizontalLineObject printableHorizontalLineObject : printModel) {
		height += printableHorizontalLineObject.height();
		if (height>(int)pf.getImageableHeight()) {
		    System.out.println("Height on page "+page+": "+(height-printableHorizontalLineObject.height()));
		    page++;
		    ArrayList<PrintableHorizontalLineObject> _printables = new ArrayList<PrintableHorizontalLineObject>();
		    _printables.add(printableHorizontalLineObject);
		    height = printableHorizontalLineObject.height();
		    printablesPerPage.put(page, _printables);
		} else {
		    printablesPerPage.get(page).add(printableHorizontalLineObject);
		}
	    }
	    
	    System.out.println("Height on page "+page+": "+height);
	    
//	    int amountOfPageBreaks = amountOfPages-1;
////	    int amountOfPageBreaks = (printModel.size()-1)/linesPerPage;
//	    pageBreaks = new int[amountOfPageBreaks];
//	    for (int i = 0; i < amountOfPageBreaks; i++) {
//		pageBreaks[i] = (i+1)*linesPerPage;
	    
//	    int i = 0;
//	    for (PrintableHorizontalLineObject printableHorizontalLineObject : printModel) {
//		System.out.println("L"+i+":\t"+printableHorizontalLineObject);
//		i++;
//	    }
	    
	    int i;
	    for (Map.Entry<Integer, List<PrintableHorizontalLineObject>> entry : printablesPerPage.entrySet()) {
		System.out.println(" *** PAGE "+entry.getKey()+" ***");
		i = 0;
		for (PrintableHorizontalLineObject printableHorizontalLineObject : entry.getValue()) {
		    System.out.println("P"+entry.getKey()+"L"+i+":\t"+printableHorizontalLineObject);
		    i++;
		}
	    }
	    
	    System.out.println("Finished calculating print model!");
	}
	
	/*
	 * Check if we're in bounds of the print model
	 */
//	if (pageIndex > pageBreaks.length) {
//	    System.out.println("No such page...");
//            return NO_SUCH_PAGE;
//        }
	if (pageIndex >= printablesPerPage.size()) {
	    System.out.println("No such page...");
            return NO_SUCH_PAGE;
        }
	
	/*
	 * Find, based on page index, which lines are to be printed on the
	 * given page by calculating the correct start and end index.
	 * Print lines starting at the pagebreak according to the current page
	 */
	int y = 0;
	int lineToPrint = 0;
	for (PrintableHorizontalLineObject printableHorizontalLineObject : printablesPerPage.get(pageIndex)) {
	    printableHorizontalLineObject.print(g, y);
	    y+=printableHorizontalLineObject.height();
	}
	
//	int y = 0;
//	int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
//        int end   = (pageIndex == pageBreaks.length)
//                         ? printModel.size() : pageBreaks[pageIndex];
//	
//	for (int line=start; line<end; line++) {
//	    printModel.get(line).print(g, y);
//	    y+=printModel.get(line).height();
//        }
	
	System.out.println("Returning page exists ("+pageIndex+")...");
	return PAGE_EXISTS;
    }
    
    /**
     * Transforms a data model to be printed to an array of Printable Objects.
     * The printer driver will print one object per line. Specific implementations
     * should make sure the line width is not exceeded and are responsible for
     * line breaking properly.
     * 
     * @param width To easy the implementation, the width of the page is given as a parameter.
     * @param margin To easy the implementation, the left margin of the page is given as a parameter.
     * @param fontMetrics To easy the implementation, the font metrics for the font used for printing is given as a parameter.
     * @return Returns an array of PrintableObjects representing the data model in a printer-friendly format.
     */
    public abstract List<PrintableHorizontalLineObject> transformBody(int height, int width, int margin, FontMetrics fontMetrics);
    
    public abstract List<PrintableHorizontalLineObject> transformFooter(int height, int width, int margin, FontMetrics fontMetrics);
}
