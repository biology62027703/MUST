package qrcode;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JFrame;
//import java.awt.Dimension;
import javax.swing.JTextArea;


import qrcode.util.Qrcode;

import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class GUIDisplay extends JFrame {

	public static void main(String args[])
	{
		GUIDisplay d = new GUIDisplay();
		d.setVisible(true);
	}
	/*public static void main(String[] args) throws Exception {
        String content = "http://google.com.tw";
        String charsetName =  "UTF-8";
        String qrCodeImagePath = "c:/qrCode.jpg";
   
        int version = 2; 
        char errorCorrect = 'M';
        char encodeMode ='B';
   
        Color backgroundColor = Color.WHITE;
        Color paintColor = Color.BLACK;
        int matrixWidth = 3;
        int borderWidth = 2;
        String imageFormat = "png";
   
        int matrix = 17 + version * 4;
        int imageWidth = (matrix)*matrixWidth+borderWidth*2;
   
        Qrcode qrcodeHandler = new Qrcode();
        qrcodeHandler.setQrcodeVersion(version);
        qrcodeHandler.setQrcodeErrorCorrect(errorCorrect);
        qrcodeHandler.setQrcodeEncodeMode(encodeMode); 

        byte[] contentBytes = content.getBytes(charsetName); 
       
        BufferedImage bufImg = new BufferedImage(imageWidth, imageWidth, BufferedImage.TYPE_INT_RGB); 
        Graphics2D gs = bufImg.createGraphics();   
        gs.setBackground(backgroundColor); 
        gs.clearRect(0, 0, imageWidth, imageWidth); 
        gs.setColor(paintColor);

        if (contentBytes.length > 0) { 
            boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes); 
            for (int i = 0; i < codeOut.length; i++) { 
                for (int j = 0; j < codeOut.length; j++) { 
                    if (codeOut[j][i]) { 
                        gs.fillRect(j * matrixWidth + borderWidth, i * matrixWidth + borderWidth, matrixWidth, matrixWidth); 
                    } 
                } 
            } 
        } 
       
        bufImg.flush(); 
   
        File imgFile = new File(qrCodeImagePath); 
        ImageIO.write(bufImg, imageFormat, imgFile); 

}*/
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	
	private QrCodePaintPanel jPanel = null;
	private JTextArea jTextArea = null;
	private JButton jButton = null;
	/**
	 * This is the default constructor
	 */
	public GUIDisplay() {
		super();
		initialize();
		
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(443, 440);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJTextArea(), null);
			jContentPane.add(getJButton(), null);
		}
		return jContentPane;
	}



	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private QrCodePaintPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new QrCodePaintPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBounds(new Rectangle(30, 183, 292, 215));
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(new Rectangle(30, 3, 296, 177));
		}
		return jTextArea;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(329, 30, 66, 98));
			jButton.setText("����");
			jButton.addActionListener(new java.awt.event.ActionListener() {
	               
	            public void actionPerformed(java.awt.event.ActionEvent e)
	            {
	            	getJPanel().paintMakeQrcode(getJTextArea().getText().trim().length()==0?null:getJTextArea().getText());
	            }
	        }
			);
		}
		return jButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
