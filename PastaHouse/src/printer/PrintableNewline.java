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
public class PrintableNewline implements PrintableObject{

    private final int y;
    
    public PrintableNewline(int y){
	this.y=y;
    }
    
    @Override
    public void print(Graphics g) {
	g.drawString("", 0, y);
    }
    
}
