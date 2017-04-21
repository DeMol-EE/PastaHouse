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
public abstract class PrintableHorizontalLineObject {

	private final int height;

	public PrintableHorizontalLineObject(int height) {
		this.height = height;
	}

	public int height() {
		return height;
	}

	public abstract void print(Graphics g, int y);
}
