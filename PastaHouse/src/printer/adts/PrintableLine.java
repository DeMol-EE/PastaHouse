/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer.adts;

import java.awt.Graphics;

/**
 *
 * @author Warkst
 */
public class PrintableLine extends PrintableHorizontalLineObject{

    private final int x1, x2;
    
    public PrintableLine(int height, int x1, int x2){
	super(height);
	this.x1=x1;
	this.x2=x2;
    }
    
    @Override
    public void print(Graphics g, int y) {
	g.drawLine(x1, y, x2, y);
    }

    @Override
    public String toString() {
	return " --- LINE ("+x1+", "+x2+") ---";
    }
    
    
}
