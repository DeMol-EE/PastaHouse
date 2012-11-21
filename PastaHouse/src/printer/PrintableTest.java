/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 *
 * @author Warkst
 */
public class PrintableTest implements Printable {

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
	// We have only one page, and 'page'
	// is zero-based
	if (pageIndex > 0) {
	    return NO_SUCH_PAGE;
	}

	// Get the system's monospaced font and calculate the font's line height
	Font mono = new Font("Monospaced", Font.PLAIN, 12);
	FontMetrics metrics = graphics.getFontMetrics(mono);
	int lineHeight = metrics.getHeight();

	double pageHeight = pageFormat.getImageableHeight();

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
//	Graphics2D g2d = (Graphics2D) graphics;
//	g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

	// Set the printing font to the monospaced font
	graphics.setFont(mono);
	
	// Now we perform our rendering
	graphics.drawString("Hello world!", 100, 100);
	
	// Draw a horizontal divider
	graphics.drawLine((int)pageFormat.getImageableX(), 100+lineHeight+10, 
		(int)(pageFormat.getImageableX()+pageFormat.getImageableWidth()), 100+lineHeight+10);


	// tell the caller that this page is part
	// of the printed document
	return PAGE_EXISTS;
    }
}
