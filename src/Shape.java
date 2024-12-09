import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
 
 public abstract class Shape 
	{
		protected Color color;
		protected Point p1;
		protected float penWidth;
		protected int moveX,moveY;
		public Shape(Point p1,Color color,float w)
		{
			this.p1=p1;
			this.color=color;
			if(w>=1)this.penWidth=w;
			else this.penWidth=1.0f;
		}
		public abstract boolean contains(Point p);	
		public abstract void draw(Graphics2D g);
		public void move(int mx, int my)
		{
			moveX = mx;
			moveY = my;
		}
		
		public void setColor(Color c)
		{
			color=c;
		}
		public Color getColor()
		{
			return color;
		}
		//public abstract void 
	}