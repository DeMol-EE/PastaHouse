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
public class PrintableNewline extends PrintableHorizontalLineObject {

	public PrintableNewline(int height) {
		super(height);
	}

	@Override
	public void print(Graphics g, int y) {
		g.drawString("", 0, y);
	}

	@Override
	public String toString() {
		return " ";
	}

}
