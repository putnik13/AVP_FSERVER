package com.atanor.drawer.draw;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.lang.management.GarbageCollectorMXBean;

/**
 * Class Canvas - a class to allow for simple graphical drawing on a canvas.
 * 
 */

public class Canvas {
	private JFrame frame;
	private JPanel canvasPanel;
	private JPanel buttonPanel;
	private JTextField textField;
	private Button buttonText;
	private Button buttonPencil;
	private Button buttonRectangle;
	private Button buttonOval;
	private Button buttonEraser;
	private Button buttonClearAll;
	protected CanvasPane canvas;
	private Graphics2D graphic;
	private Color backgroundColor;
	private Color inkColor;
	private Image canvasImage;

	private boolean pencil = true;
	private boolean text = false;
	private boolean rectangle = false;
	private boolean oval = false;
	private boolean erasable = false;
	
	private int firstX = 0;
	private int firstY = 0;
	private int lastX = 0;
	private int lastY = 0;

	/**
	 * Create a Canvas with default height, width and background color (300,
	 * 300, white).
	 * 
	 * @param title
	 *            title to appear in Canvas Frame
	 */
	public Canvas(String title) {
		this(title, 300, 300, Color.white);
	}

	/**
	 * Create a Canvas with default title, height, width and background color
	 * ("Canvas", 300, 300, white).
	 */
	public Canvas() {
		this("Canvas", 300, 300, Color.white);
	}

	/**
	 * Create a Canvas with default background Color (white).
	 * 
	 * @param title
	 *            title to appear in Canvas Frame
	 * @param width
	 *            the desired width for the canvas
	 * @param height
	 *            the desired height for the canvas
	 */
	private Canvas(String title, int width, int height) {
		this(title, width, height, Color.white);
	}

	/**
	 * Create a Canvas.
	 * 
	 * @param title
	 *            title to appear in Canvas Frame
	 * @param width
	 *            the desired width for the canvas
	 * @param height
	 *            the desired height for the canvas
	 * @param bgColor
	 *            the desired background color of the canvas
	 */
	private Canvas(String title, int width, int height, Color bgColor) {
		frame = new JFrame();
		canvasPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel = new JPanel(new GridLayout(10, 10));
		canvas = new CanvasPane();
		
		frame.setResizable(false);
		frame.setContentPane(canvasPanel);
		//		canvas.setAutoscrolls(true);
		canvas.setPreferredSize(new Dimension(width, height));
		canvasPanel.add(canvas);

		buttonPanel.add(new JLabel("Draw components:"));
		buttonPanel.add(new JLabel());
		buttonPanel.add(new JSeparator(0));
		buttonPanel.add(new JSeparator(0));
		buttonPanel.add(textField = new JTextField());
		buttonPanel.add(buttonText = new Button("set text"));
		buttonPanel.add(new JLabel());
		buttonPanel.add(new JLabel());
		buttonPanel.add(buttonPencil = new Button("line"));
		buttonPanel.add(buttonRectangle = new Button("rectangle"));
		buttonPanel.add(new JLabel());
		buttonPanel.add(new JLabel());
		buttonPanel.add(buttonOval = new Button("oval"));
		buttonPanel.add(buttonEraser = new Button("eraser"));
		buttonPanel.add(new JLabel());
		buttonPanel.add(new JLabel());
		buttonPanel.add(buttonClearAll = new Button("clear"));
		buttonPanel.add(new JLabel());
		canvasPanel.add(buttonPanel);
		
		frame.setTitle(title);
		backgroundColor = bgColor;
		inkColor = Color.black;
		frame.pack();

		// Added by acd: this is a hack that will allow other components,
		// like buttons, to be added to the canvas and be visible.
		// basically, it's saying "when you repaint, don't fill the entire
		// canvas with the background color", i.e. leave the buttons and
		// stuff alone!
		canvas.setOpaque(false);
		// end of hack

		buttonPencil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pencil = true;
				text = false;
				rectangle = false;
				oval = false;
				erasable = false;
			}
		});

		buttonText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pencil = false;
				text = true;
				rectangle = false;
				oval = false;
				erasable = false;
			}
		});

		buttonRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pencil = false;
				text = false;
				rectangle = true;
				oval = false;
				erasable = false;
			}
		});
		
		buttonOval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pencil = false;
				text = false;
				rectangle = false;
				oval = true;
				erasable = false;
			}
		});
		
		buttonEraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pencil = false;
				text = false;
				rectangle = false;
				oval = false;
				erasable = true;
			}
		});
		
		buttonClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				erase();
			}
		});
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}
		});

		frame.addMouseMotionListener(new MouseMotionListener() {

			public void mouseMoved(MouseEvent e) {
			}

			public void mouseDragged(MouseEvent e) {
				if (pencil) {
					// delete previose line
					inkColor = Color.white;
					graphic.setColor(inkColor);
					graphic.drawLine(firstX, firstY, lastX, lastY);

					// draw line
					inkColor = Color.black;
					lastX = e.getX() - 6;
					lastY = e.getY() - 37;

					graphic.setColor(inkColor);
					drawLine(firstX, firstY, lastX, lastY);
				} else if (erasable) {
					eraseOval(e.getX() - 6, e.getY() - 37, 10, 10);
				}else if(rectangle){
					// delete previose line
					inkColor = Color.white;
					graphic.setColor(inkColor);
					drawRectangle(firstX, firstY, lastX, lastY);
					
					inkColor = Color.black;
					lastX = e.getX() - 6;
					lastY = e.getY() - 37;

					graphic.setColor(inkColor);
					drawRectangle(firstX, firstY, lastX, lastY);
				}else if(oval){
					// delete previose line
					inkColor = Color.white;
					graphic.setColor(inkColor);
					drawOval(firstX, firstY, lastX, lastY);
					
					inkColor = Color.black;
					lastX = e.getX() - 6;
					lastY = e.getY() - 37;

					graphic.setColor(inkColor);
					drawOval(firstX, firstY, lastX, lastY);
				}
			}
		});

		frame.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				if (pencil) {
					lastX = e.getX() - 6;
					lastY = e.getY() - 37;

					drawLine(firstX, firstY, lastX, lastY);
				}else if(text) {

					drawString(textField.getText(), firstX, firstY);
				}else if(rectangle){
					
					lastX = e.getX() - 6;
					lastY = e.getY() - 37;
					
					drawRectangle(firstX, firstY, lastX, lastY);
				}else if(oval){
					
					lastX = e.getX() - 6;
					lastY = e.getY() - 37;
					
					drawOval(firstX, firstY, lastX, lastY);
				}
			}

			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1) {
					firstX = e.getX() - 6;
					firstY = e.getY() - 37;
				} 
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});

		if (graphic == null) {
			// first time: instantiate the offscreen image and fill it with
			// the background color
			Dimension size = canvas.getSize();
			canvasImage = canvas.createImage(size.width, size.height);
			graphic = (Graphics2D) canvasImage.getGraphics();
			graphic.setColor(backgroundColor);
			graphic.fillRect(0, 0, size.width, size.height);
			graphic.setColor(inkColor);
		}
	}

	/**
	 * Sets the "pen" (outline) color for the Canvas.
	 * 
	 * @param newColor
	 *            The color to which to set the pen/drawing tool.
	 */
	public void setInkColor(Color newColor) {
		inkColor = newColor;
		graphic.setColor(inkColor);
	}

	/**
	 * Returns the current pen color.
	 */
	public Color getInkColor() {
		return inkColor;
	}

	/**
	 * Set the canvas visibility and brings canvas to the front of screen when
	 * made visible. This method can also be used to bring an already visible
	 * canvas to the front of other windows.
	 * 
	 * @param visible
	 *            boolean value representing the desired visibility of the
	 *            canvas (true or false)
	 */
	public void setVisible(boolean visible) {
		/*
		 * if (visible) frame.show(); else frame.hide();
		 */
		/*
		 * EDITED BY ACD: show() and hide() are deprecated in Java 1.5; replace
		 * with setVisible(boolean)
		 */
		frame.setVisible(visible);
	}

	/**
	 * Provide information on visibility of the Canvas.
	 * 
	 * @return true if canvas is visible, false otherwise
	 */
	public boolean isVisible() {
		return frame.isVisible();
	}

	/**
	 * Draw a given shape onto the canvas.
	 * 
	 * @param shape
	 *            the shape object to be drawn on the canvas
	 */
	private void draw(Shape shape) {
		graphic.draw(shape);
		canvas.repaint();
	}

	/**
	 * Fill the internal dimensions of a given shape with the current foreground
	 * color of the canvas.
	 * 
	 * @param shape
	 *            the shape object to be filled
	 */
	private void fill(Shape shape) {
		graphic.fill(shape);
		canvas.repaint();
	}

	/**
	 * Erase the whole canvas.
	 */
	public void erase() {
		Color original = graphic.getColor();
		graphic.setColor(backgroundColor);
		Dimension size = canvas.getSize();
		graphic.fill(new Rectangle(0, 0, size.width, size.height));
		graphic.setColor(original);
		canvas.repaint();
	}

	/**
	 * Erase a given shape's interior on the screen.
	 * 
	 * @param shape
	 *            the shape object to be erased
	 */
	private void erase(Shape shape) {
		Color original = graphic.getColor();
		graphic.setColor(backgroundColor);
		graphic.fill(shape); // erase by filling background color
		graphic.setColor(original);
		canvas.repaint();
	}

	/**
	 * Erases a given shape's outline on the screen.
	 * 
	 * @param shape
	 *            the shape object to be erased
	 */
	private void eraseOutline(Shape shape) {
		Color original = graphic.getColor();
		graphic.setColor(backgroundColor);
		graphic.draw(shape); // erase by drawing background color
		graphic.setColor(original);
		canvas.repaint();
	}

	/**
	 * Draws an image onto the canvas.
	 * 
	 * @param image
	 *            the Image object to be displayed
	 * @param x
	 *            x co-ordinate for Image placement
	 * @param y
	 *            y co-ordinate for Image placement
	 * @return returns boolean value representing whether the image was
	 *         completely loaded
	 */
	private boolean drawImage(Image image, int x, int y) {
		boolean result = graphic.drawImage(image, x, y, null);
		canvas.repaint();
		return result;
	}

	/**
	 * Draws a String on the Canvas.
	 * 
	 * @param text
	 *            the String to be displayed
	 * @param x
	 *            x co-ordinate for text placement
	 * @param y
	 *            y co-ordinate for text placement
	 */
	public void drawString(String text, int x, int y) {
		graphic.drawString(text, x, y);
		canvas.repaint();
	}

	/**
	 * Erases a String on the Canvas.
	 * 
	 * @param text
	 *            the String to be displayed
	 * @param x
	 *            x co-ordinate for text placement
	 * @param y
	 *            y co-ordinate for text placement
	 */
	public void eraseString(String text, int x, int y) {
		Color original = graphic.getColor();
		graphic.setColor(backgroundColor);
		graphic.drawString(text, x, y);
		graphic.setColor(original);
		canvas.repaint();
	}

	/**
	 * Draws a line on the Canvas.
	 * 
	 * @param x1
	 *            x co-ordinate of start of line
	 * @param y1
	 *            y co-ordinate of start of line
	 * @param x2
	 *            x co-ordinate of end of line
	 * @param y2
	 *            y co-ordinate of end of line
	 */
	public void drawLine(int x1, int y1, int x2, int y2) {
		graphic.drawLine(x1, y1, x2, y2);
		canvas.repaint();
	}

	/**
	 * Draws a rectangle on the Canvas.
	 * 
	 * @param x1
	 *            x co-ordinate of top left corner
	 * @param y1
	 *            y co-ordinate of top left corner
	 * @param x2
	 *            width
	 * @param y2
	 *            height
	 */
	public void drawRectangle(int x1, int y1, int x2, int y2) {
		graphic.draw(new Rectangle(x1, y1, x2, y2));
		canvas.repaint();
	}

	/**
	 * Draws a filled rectangle on the Canvas.
	 * 
	 * @param x1
	 *            x co-ordinate of top left corner
	 * @param y1
	 *            y co-ordinate of top left corner
	 * @param x2
	 *            width
	 * @param y2
	 *            height
	 */
	public void fillRectangle(int x1, int y1, int x2, int y2) {
		graphic.fill(new Rectangle(x1, y1, x2, y2));
		canvas.repaint();
	}

	/**
	 * Draws a polygon on the Canvas.
	 * 
	 * @param x
	 *            array of x co-ordinates of polygon points
	 * @param y
	 *            array of y co-ordinates of polygon points
	 * @param size
	 *            the number of points (vertices) in the polygon
	 */
	public void drawPolygon(int[] x, int[] y, int size) {
		graphic.draw(new Polygon(x, y, size));
		canvas.repaint();
	}

	/**
	 * Draws a filled polygon on the Canvas.
	 * 
	 * @param x
	 *            array of x co-ordinates of polygon points
	 * @param y
	 *            array of y co-ordinates of polygon points
	 * @param size
	 *            the number of points (vertices) in the polygon
	 */
	public void fillPolygon(int[] x, int[] y, int size) {
		graphic.fill(new Polygon(x, y, size));
		canvas.repaint();
	}

	/**
	 * Erases a rectangle on the Canvas.
	 * 
	 * @param x1
	 *            x co-ordinate of top left corner
	 * @param y1
	 *            y co-ordinate of top left corner
	 * @param x2
	 *            width
	 * @param y2
	 *            height
	 */
	public void eraseRectangle(int x1, int y1, int x2, int y2) {
		eraseOutline(new Rectangle(x1, y1, x2, y2));
	}

	/**
	 * Draws an oval on the Canvas.
	 * 
	 * @param x1
	 *            x co-ordinate of top left corner
	 * @param y1
	 *            y co-ordinate of top left corner
	 * @param x2
	 *            width
	 * @param y2
	 *            height
	 */
	public void drawOval(int x1, int y1, int x2, int y2) {
		graphic.draw(new Ellipse2D.Double(x1, y1, x2, y2));
		canvas.repaint();
	}

	/**
	 * Draws a filled oval on the Canvas.
	 * 
	 * @param x1
	 *            x co-ordinate of top left corner
	 * @param y1
	 *            y co-ordinate of top left corner
	 * @param x2
	 *            width
	 * @param y2
	 *            height
	 */
	public void fillOval(int x1, int y1, int x2, int y2) {
		graphic.fill(new Ellipse2D.Double(x1, y1, x2, y2));
		canvas.repaint();
	}

	/**
	 * Erases an oval on the Canvas.
	 * 
	 * @param x1
	 *            x co-ordinate of top left corner
	 * @param y1
	 *            y co-ordinate of top left corner
	 * @param x2
	 *            width
	 * @param y2
	 *            height
	 */
	public void eraseOval(int x1, int y1, int x2, int y2) {
		eraseOutline(new Ellipse2D.Double(x1, y1, x2, y2));
	}

	/**
	 * Sets the foreground color of the Canvas.
	 * 
	 * @param newColor
	 *            the new color for the foreground of the Canvas
	 */
	private void setForegroundColor(Color newColor) {
		graphic.setColor(newColor);
	}

	/**
	 * Returns the current color of the foreground.
	 * 
	 * @return the color of the foreground of the Canvas
	 */
	private Color getForegroundColor() {
		return graphic.getColor();
	}

	/**
	 * Sets the background color of the Canvas.
	 * 
	 * @param newColor
	 *            the new color for the background of the Canvas
	 */
	private void setBackgroundColor(Color newColor) {
		backgroundColor = newColor;
		graphic.setBackground(newColor);
	}

	/**
	 * Fills in the Canvas (background) with the specified color.
	 * 
	 * @param newColor
	 *            the new color for the background of the Canvas
	 */
	public void fillBackground(Color newColor) {
		Dimension size = canvas.getSize();
		backgroundColor = newColor;
		graphic.setColor(backgroundColor);
		graphic.fillRect(0, 0, size.width, size.height);
		graphic.setColor(inkColor);
	}

	/**
	 * Returns the current color of the background
	 * 
	 * @return the color of the background of the Canvas
	 */
	private Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * changes the current Font used on the Canvas
	 * 
	 * @param newFont
	 *            new font to be used for String output
	 */
	public void setFont(Font newFont) {
		graphic.setFont(newFont);
	}

	/**
	 * Returns the current font of the canvas.
	 * 
	 * @return the font currently in use
	 **/
	public Font getFont() {
		return graphic.getFont();
	}

	/**
	 * Sets the point size of the current font to the specified value. The style
	 * and font family remain the same.
	 *
	 * @param newSize
	 *            the new point size
	 */
	public void setFontSize(int newSize) {
		Font f = graphic.getFont().deriveFont((float) newSize);
		setFont(f);
	}

	/**
	 * Sets the size of the canvas.
	 * 
	 * @param width
	 *            new width
	 * @param height
	 *            new height
	 */
	public void setSize(int width, int height) {
		canvas.setPreferredSize(new Dimension(width, height));
		Image oldImage = canvasImage;
		canvasImage = canvas.createImage(width, height);
		graphic = (Graphics2D) canvasImage.getGraphics();
		graphic.setColor(backgroundColor);
		graphic.fillRect(0, 0, width, height);
		graphic.setColor(inkColor);
		graphic.drawImage(oldImage, 0, 0, null);
		frame.pack();
	}

	/**
	 * Returns the size of the canvas.
	 * 
	 * @return The current dimension of the canvas
	 */
	private Dimension getSize() {
		return canvas.getSize();
	}

	/**
	 * Waits for a specified number of milliseconds before finishing. This
	 * provides an easy way to specify a small delay which can be used when
	 * producing animations.
	 * 
	 * @param milliseconds
	 *            the number
	 */
	public void wait(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {
			// ignoring exception at the moment
		}
	}

	/************************************************************************
	 * Nested class CanvasPane - the actual canvas component contained in the
	 * Canvas frame. This is essentially a JPanel with added capability to
	 * refresh the image drawn on it. MODIFIED by acd: changed visibility to
	 * protected (from private) to allow subclassing (basically, so we can add
	 * mouse listeners to the canvas), and added the call to super.paint() (an
	 * additional hack to allow us to add components like buttons and menus to
	 * the canvas).
	 */
	protected class CanvasPane extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(canvasImage, 0, 0, null);
			super.paint(g);
		}
	}

}
