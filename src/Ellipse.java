
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

//Ellipse
	public class Ellipse extends Shape
	{
		private Point p2;
		private int width,height;
		private boolean isFilled;
		
		public Ellipse(Point p1, Point p2,Color color,float pw,boolean filled,int w,int h)
		{
			super(p1,color,pw);
			this.p2=p2;
			isFilled = filled;
			width=w;
			height=h;
		}
		public void draw(Graphics2D g)
		{
			  
			   //width = Math.abs(p2.x-p1.x);
			   //height = Math.abs(p2.y-p1.y);
			  int x = Math.min(p1.x,p2.x);
				int y= Math.min(p1.y,p2.y);
			  g.setColor(color);
			  g.setStroke(new BasicStroke(penWidth));
		      if(isFilled) g.fillOval(x,y,width,height);
			  else g.drawOval(x,y,width,height);
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
