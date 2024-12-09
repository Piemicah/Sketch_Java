
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


	public class Curve extends Shape
	{
		
		private ArrayList<Point> points;
		
		public Curve(Point p,ArrayList<Point> pointArray,Color color,float pw)
		{
			super(p,color,pw);
			this.points=pointArray;
		}
		public void draw(Graphics2D g)
		{
			  
			  g.setColor(color);
			  g.setStroke(new BasicStroke(penWidth));
		     
		  for(int i = 0;i<points.size()-1;i++)
		  {
			Point p1 = (Point)points.get(i);
			Point p2 = (Point)points.get(i+1);
		    g.drawLine(p1.x,p1.y,p2.x,p2.y);
		  }
		}
		
		public Point min()
		{
			Point pm = (Point)points.get(0);
			int xMin=pm.x;
			int yMin=pm.y;
			for(int i = 1;i<points.size();i++)
			{
				Point p = (Point)points.get(i);
			    if(p.x < xMin) xMin = p.x;
								
				if(p.y < yMin) yMin = p.y;
				
			}
			
			Point p = new Point(xMin,yMin);
			return p;
		}
		
		public Point max()
		{
			Point pm = (Point)points.get(0);
			int xMax=pm.x;
			int yMax=pm.y;
			for(int i = 1;i<points.size();i++)
			{
				Point p = (Point)points.get(i);
			    if(p.x > xMax) xMax = p.x;
								
				if(p.y > yMax) yMax = p.y;
				
			}
			
			Point p = new Point(xMax,yMax);
			return p;
		}
		
		public boolean contains(Point p)
		{
			
			int x1 = min().x;
			int x2 = max().x;
			int y1 = min().y;
			int y2 = max().y;
			
			if((p.x>=x1 && p.x<=x2) && (p.y>=y1 && p.y<=y2)) return true;
			else return false;
			
		} 
	}
