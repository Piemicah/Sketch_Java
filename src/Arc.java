
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


	public class Arc extends Shape
	{
		
		private Point p2;
		private boolean isFilled;
		
		public Arc(Point p1, Point p2,Color color,float pw,boolean filled)
		{
			super(p1,color,pw);
			this.p2=p2;
			isFilled=filled;
		}
		public void draw(Graphics2D g)
		{
			  
			  g.setColor(color);
			  g.setStroke(new BasicStroke(penWidth));
		     
		       int width = Math.abs(p2.x-p1.x);
			   int height = Math.abs(p2.y-p1.y);
			    int x = Math.min(p1.x,p2.x);
				int y= Math.min(p1.y,p2.y);
		
		  if(isFilled) g.fillArc(x,y,width,height,0,120);
		  else g.drawArc(x,y,width,height,0,120);
		}
		
		public boolean contains(Point p)
		{
			int x1 = Math.min(p2.x,p1.x);
			int x2 = Math.max(p2.x,p1.x);
			int y1 = Math.min(p2.y,p1.y);
			int y2 = Math.max(p2.y,p1.y);
			
			if((p.x>=x1 && p.x<=x2) && (p.y>=y1 && p.y<=y2)) return true;
			else return false;
			
		} 
	}
