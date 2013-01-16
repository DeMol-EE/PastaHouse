/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer.adts;

import printer.adts.PrintableHorizontalLineObject;
import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author Warkst
 */
public class PrintableMulti implements PrintableHorizontalLineObject{

    private final List<PrintableHorizontalLineObject> objectsOnThisLine;

    public PrintableMulti(List<PrintableHorizontalLineObject> objectsOnThisLine) {
	this.objectsOnThisLine = objectsOnThisLine;
    }
    
    @Override
    public void print(Graphics g, int y) {
	for (PrintableHorizontalLineObject printableHorizontalLineObject : objectsOnThisLine) {
	    printableHorizontalLineObject.print(g, y);
	}
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder("[");
	
	for (PrintableHorizontalLineObject printableHorizontalLineObject : objectsOnThisLine) {
	    sb.append(printableHorizontalLineObject.toString()).append(", ");
	}
	
	sb.append("]");
	
	return sb.toString();
    }
}
