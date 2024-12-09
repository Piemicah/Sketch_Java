
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


	
	public class Circle extends Shape
	{
		private Point p2;
		private boolean isFilled;
		public Circle(Point p1, Point p2,Color color,float pw,boolean filled )
		{
			super(p1,color,pw);
			this.p2=p2;
			isFilled = filled;
		}
		public void draw(Graphics2D g)
		{
			  
		      int radius = Math.abs(p2.x-p1.x);
				int x = Math.abs(p1.x-radius);
				int y = Math.abs(p1.y-radius);
				g.setColor(color);
				g.setStroke(new BasicStroke(penWidth));
				if(isFilled) g.fillOval(x,y,2*radius,2*radius);
				else g.drawOval(x,y,2*radius,2*radius);
		}
		
		public boolean contains(Point p)
		{
			    int radius = Math.abs(p2.x-p1.x);
				int x1 = Math.abs(p1.x-radius);
				int y1 = Math.abs(p1.y-radius);
				
			
			int x2 = p1.x+radius;
			
			int y2 = p1.y+radius;
			
			if((p.x>=x1 && p.x<=x2) && (p.y>=y1 && p.y<=y2)) return true;
			else return false;
		}
	}