/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Warkst
 */
public class ImagePanelBean extends JPanel implements Serializable {    
    protected ImageIcon backgroundImage = null;

    public ImagePanelBean() {
	setLayout(null);
    }
    
    public ImageIcon getBackgroundImage() {
	return backgroundImage;
    }

    public void setBackgroundImage(ImageIcon backgroundImage, Dimension size) {
	this.backgroundImage = scaleImage(backgroundImage, size);
	this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g); // hmm
	g.drawImage(backgroundImage.getImage(), 0, 0, getBackground(), null);
    }
    
    private ImageIcon scaleImage(ImageIcon imgIcon, Dimension size){
	return new ImageIcon(imgIcon.getImage().getScaledInstance((int)(size.getWidth()), (int)(size.getHeight()), Image.SCALE_SMOOTH));
    }
}
