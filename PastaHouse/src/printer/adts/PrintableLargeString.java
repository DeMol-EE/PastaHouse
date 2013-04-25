/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printer.adts;

import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Warkst
 */
public class PrintableLargeString extends PrintableHorizontalLineObject{

    private final String line;
    private final int x;
    
    public PrintableLargeString(int height, String line, int x){
	super(height);
	this.line = line;
	this.x = x;
    }
    
    @Override
    public void print(Graphics g, int y) {
	Font f = g.getFont();
	g.setFont(new Font(f.getName(), f.getStyle(), f.getSize()+3));
	g.drawString(line, x, y);
	g.setFont(f);
    }

    @Override
    public String toString() {
	return line;
    }   
}