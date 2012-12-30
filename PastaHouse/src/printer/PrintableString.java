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
public class PrintableString implements PrintableObject{

    private final String line;
    private final int x;
    private final int y;
    
    public PrintableString(String line, int x, int y){
	this.line = line;
	this.x = x;
	this.y = y;
    }
    
    @Override
    public void print(Graphics g) {
	g.drawString(line, x, y);
    }
}
