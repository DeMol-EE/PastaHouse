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
public class PrintableNewline implements PrintableHorizontalLineObject{
    
    @Override
    public void print(Graphics g, int y) {
	g.drawString("", 0, y);
    }

    @Override
    public String toString() {
	return " ";
    }
    
}
