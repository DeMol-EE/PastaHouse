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
public class PrintableString extends PrintableHorizontalLineObject{

    private final String line;
    private final int x;
    
    public PrintableString(int height, String line, int x){
	super(height);
	this.line = line;
	this.x = x;
    }
    
    @Override
    public void print(Graphics g, int y) {
	g.drawString(line, x, y);
    }

    @Override
    public String toString() {
	return line;
    }
    
    
}
