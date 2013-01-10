/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer;

import java.awt.Graphics;

/**
 *
 * @author Warkst
 */
public class PrintableLine implements PrintableHorizontalLineObject{

    private final int x1, x2;
    
    public PrintableLine(int x1, int x2){
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
