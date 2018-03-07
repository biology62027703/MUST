package qrcode;

import java.awt.LayoutManager;

import javax.swing.JPanel;

public class QrCodePaintPanel extends JPanel {

	private java.awt.image.BufferedImage image = null;
	public QrCodePaintPanel() {
		
	}

	public QrCodePaintPanel(LayoutManager arg0) {
		super(arg0);
		
	}

	public QrCodePaintPanel(boolean arg0) {
		super(arg0);
		
	}

	public QrCodePaintPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		
	}
	public void paintMakeQrcode(String str)
	{
		EnCodeTest qrcode = new EnCodeTest(str);
		 try {
		      //java.awt.MediaTracker mt = new java.awt.MediaTracker (this);
		      // for Applet, change the method to retrieve the image
		      // and of course use your own image!
		     image = qrcode.makeqrcode();
		     // mt.addImage(image, 0);
		      //mt.waitForID(0);
		   }
		   catch (Exception e) {
		       e.printStackTrace();
		   }
		   qrcode = null;
		   this.getParent().repaint();
		   
	}
	public void update(java.awt.Graphics g) {
		paint(g);
	}

	public void paint(java.awt.Graphics g) {
		if (image != null) {
			g.drawImage(image,60, 20, this);
		} else {
			g.clearRect(0, 0, getSize().width, getSize().height);
		}
	}

}
