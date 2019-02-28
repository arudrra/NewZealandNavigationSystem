import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class MapPanel extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {
	protected BufferedImage map;
	private boolean hasZoomed;
	private double scale;
	private double priorScale;
	private double xTranslation;
	private double yTranslation;
	private double xDistance;
	private double yDistance;
	private boolean hasDragged;
	private boolean hasReleased;
	private Point origin;
	
	public MapPanel(BufferedImage map) {
		this.map = map;
		this.scale = 0.235;
		this.priorScale = 0.235;
		this.hasZoomed = true;
		this.hasDragged = false;
		this.hasReleased = true;
		this.xTranslation = 0.0;
		this.yTranslation = 0.0;
		this.xDistance = 0.0;
		this.yDistance = 0.0;
		this.origin = MouseInfo.getPointerInfo().getLocation();
		this.setBounds(10, 30, 700, 880);
		AffineTransform affineTransform = new AffineTransform();
        affineTransform.scale(scale, scale);
	    addMouseListener(this);
		addMouseWheelListener(this);
	    addMouseMotionListener(this);
	}
	
	@Override
	public void paint(Graphics g) {		
		super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
		if (this.hasZoomed) {
			AffineTransform affineTransform = new AffineTransform();
            this.xTranslation = ((this.scale/this.priorScale) * (this.xTranslation)) + ((1 - this.scale/this.priorScale) * (MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX()));
            this.yTranslation = ((this.scale/this.priorScale) * (this.xTranslation)) + ((1 - this.scale/this.priorScale) * (MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY()));
            affineTransform.translate(this.xTranslation, this.yTranslation);
            affineTransform.scale(this.scale, this.scale);
            
            this.priorScale = this.scale;
            g2.transform(affineTransform);
            this.hasZoomed = false;
		}
		
		if (this.hasDragged) {
	            AffineTransform affineTransform = new AffineTransform();
	            affineTransform.translate(this.xTranslation + this.xDistance, this.yTranslation + this.yDistance);
	            affineTransform.scale(this.scale, this.scale);
	            g2.transform(affineTransform);

	            if (this.hasReleased) {
	                this.xTranslation += this.xDistance;
	                this.yTranslation += this.yDistance;
	                this.hasDragged = false;
	            }

	       }
		 
        g2.drawImage(map, 0, 0, this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	       this.xDistance = e.getLocationOnScreen().getX() - this.origin.x;
	       this.yDistance = e.getLocationOnScreen().getY() - this.origin.y;
	       this.hasDragged = true;
	       this.repaint();		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.hasReleased = false;
		this.origin = MouseInfo.getPointerInfo().getLocation();		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.hasReleased = true;
		if (this.hasDragged) this.repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.hasZoomed = true;

        if (e.getWheelRotation() < 0 && scale*1.1 < 2.0) {
            this.scale *= 1.1;
            this.repaint();
        }

        if (e.getWheelRotation() > 0 && scale/1.1 > 0.22) {
            this.scale /= 1.1;
            this.repaint();
        }
	}

}
