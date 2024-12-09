
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
 
 public class Line extends Shape
	{
		private Point p2;
		public Line(Point p1, Point p2,Color color,float w)
		{
			super(p1,color,w);
			this.p2=p2;
		}
		public void draw(Graphics2D g)
		{
			  g.setColor(color);
			  g.setStroke(new BasicStroke(penWidth));
			  //move(dx,dy);
			 // p1.move(dx,dy);
			  //p2.move(dx,dy);
		     g.drawLine(p1.x+moveX,p1.y+moveY,p2.x+moveX,p2.y+moveY);
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