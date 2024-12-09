
import java.awt.*;
import java.awt.BasicStroke;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class Sketch extends JFrame implements ActionListener
{
	private JLabel widthLabel;
	private JTextField widthField;
	private Color color;
	private float penWidth;
	private Canvas canvas;
	private JMenuBar bar;
	private JMenu shapeMenu,cMenu,modeMenu;
	private JRadioButtonMenuItem  lineMenu,rectMenu,circleMenu,curveMenu,arcMenu,ellipseMenu,normalMenu,filledMenu;
	private JMenuItem colorMenu;
	private ButtonGroup group,group1;
	private int xStart,yStart,xEnd,yEnd;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private Shape shape;
	private Point point1,point2,p;
	private ArrayList<Point> points;// = new ArrayList<Point>();
	private boolean isMouseDragged=false;
	//private enum ShapeType{LINE,CIRCLE,RECTANGLE,CURVE,ARC,ELLIPSE};
	private final int LINE = 1;
	private final int CIRCLE = 2;
	private final int RECTANGLE = 3;
	private final int CURVE = 4;
	private final int ARC = 5;
	private final int ELLIPSE = 6;
	//private ShapeType type;
	private int type;
	private boolean isFilled,isShiftKeyDown;
	
	
	public Sketch()
	{
		int width,height;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        width = d.width;
        height = d.height;
		setSize(width,height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComp();
		type = LINE;
		isFilled = false;
		lineMenu.setSelected(true);
		normalMenu.setSelected(true);
		color=Color.black;
		penWidth=1;
		
	}
	public void initComp()
	{
		this.setTitle("SketchPad by Piemicah");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/logo.png"));
		canvas = new Canvas();
		this.add(canvas);
		group = new ButtonGroup();
		group1 = new ButtonGroup();
		bar = new JMenuBar();
		shapeMenu = new JMenu("Shapes");
		cMenu = new JMenu("Color");
		modeMenu = new JMenu("Mode");
		colorMenu =new JMenuItem("Choose Color");
		colorMenu.addActionListener(this);
		cMenu.add(colorMenu);
		lineMenu = new JRadioButtonMenuItem("line");
		lineMenu.addActionListener(this);
		rectMenu = new JRadioButtonMenuItem("Rectangle");
		rectMenu.addActionListener(this);
		circleMenu = new JRadioButtonMenuItem("Circle");
		circleMenu.addActionListener(this);
		curveMenu = new JRadioButtonMenuItem("Curve");
		curveMenu.addActionListener(this);
		arcMenu = new JRadioButtonMenuItem("Arc");
		arcMenu.addActionListener(this);
		ellipseMenu = new JRadioButtonMenuItem("Ellipse");
		ellipseMenu.addActionListener(this);
		
		
		normalMenu = new JRadioButtonMenuItem("Normal Shape");
		normalMenu.addActionListener(this);
		filledMenu = new JRadioButtonMenuItem("Filled Shape");
		filledMenu.addActionListener(this);
		group.add(lineMenu);
		group.add(rectMenu);
		group.add(circleMenu);
		group.add(curveMenu);
		group.add(arcMenu);
		group.add(ellipseMenu);
				
		shapeMenu.add(lineMenu);
		shapeMenu.add(rectMenu);
		shapeMenu.add(circleMenu);
		shapeMenu.add(curveMenu);
		shapeMenu.add(arcMenu);
		shapeMenu.add(ellipseMenu);
		
		group1.add(normalMenu);
		group1.add(filledMenu);
		modeMenu.add(normalMenu);
		modeMenu.add(filledMenu);
		
		bar.add(shapeMenu);
		//bar.add(new JSeparator());
		bar.add(cMenu);
		bar.add(modeMenu);
		bar.add(Box.createHorizontalGlue());
		widthField = new JTextField(5);
		widthField.setMaximumSize(widthField.getPreferredSize());
		widthLabel = new JLabel("pen width");
		widthField.addActionListener(this);
		bar.add(widthField);
		bar.add(widthLabel);
		bar.setBackground(new Color(90,171,241));
		setJMenuBar(bar);
		
	}
	
	/**  menu listeners **/
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==lineMenu) type=LINE;
		else if(e.getSource()==rectMenu) type=RECTANGLE;
		else if(e.getSource()==circleMenu) type=CIRCLE;
		else if(e.getSource()==curveMenu) type=CURVE;
		else if(e.getSource()==arcMenu) type=ARC;
		else if(e.getSource()==ellipseMenu) type=ELLIPSE;
		else if(e.getSource()==normalMenu) isFilled = false;
		else if(e.getSource()==filledMenu) isFilled = true;
		else if(e.getSource()==colorMenu)
		{
			color = JColorChooser.showDialog(this,"Choose a color", color);
		}
		else if(e.getSource()==widthField){
			try{
			penWidth = Float.parseFloat(widthField.getText().toString());
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Numeric values only","Piemicah SketchPad",JOptionPane.PLAIN_MESSAGE);
				widthField.setText("1");
				return;
			}
		}
		else{}
		
		
	}
	
	
	private class Canvas extends JPanel implements ActionListener
	{
		private Shape highlightedShape = null;
		private JPopupMenu pop;
		private JMenuItem delpop,movepop,clearpop;
		private boolean moved, highlighted;
		private int dx,dy,highlightedIndex;
		
		public Canvas()
		{
			setBackground(Color.white);
			addMouseListener(new MouseEar());
		    addMouseMotionListener(new MouseMotionEar());
			pop = new JPopupMenu();
			delpop = new JMenuItem("delete");
			delpop.addActionListener(this);
			movepop = new JMenuItem("move");
			clearpop = new JMenuItem("Clear All");
			clearpop.addActionListener(this);
			pop.add(delpop);
			pop.add(movepop);
			pop.add(clearpop);
		  	addMouseListener(new PopEar());
			
		}
		
		public void actionPerformed(ActionEvent e)
		{
			try {
			if(e.getSource()==delpop) 
			{
			   for(Shape s : shapes)
				{
					if(s.equals(highlightedShape))
					{
					  shapes.remove(s);
				      repaint();
					}
				
				}
			}
			else if(e.getSource()==movepop)
				    {
						if(highlighted) moved=true;
						
					}
            else if(e.getSource()==clearpop)
			       {
					   shapes.clear();
					   repaint();
				   }
            else{}		

			}
            catch(Exception ex)	{}		
		}
		
		private class MouseEar extends MouseAdapter
	    {
		  public void mousePressed(MouseEvent e)
		   {
			   moved = false;
			   highlighted = false;
			if(!e.isMetaDown()) 
			{
			points = new ArrayList<Point>();
			 xStart = e.getX();
			 yStart= e.getY();
			 point1 = new Point(xStart,yStart);
			 points.add(point1);
			isMouseDragged=false;
			}
			
			
			
			
		   }
		
		 public void mouseReleased(MouseEvent e)
		  {
			  if(!e.isMetaDown()){
			isMouseDragged=false;
			repaint();
			 xEnd = e.getX();
			 yEnd= e.getY();
			point2 = new Point(xEnd,yEnd);
			//points.add(point2);
			if(type==LINE)
			{
				shape = new Line(point1,point2,color,penWidth);
			    shapes.add(shape);
			}
			else if(type==RECTANGLE)
			{
				shape = new Rectangle(point1,point2,color,penWidth,isFilled);
				shapes.add(shape);
			}
			else if(type==CIRCLE)
			{
				shape = new Circle(point1,point2,color,penWidth,isFilled);
				shapes.add(shape);
			}
			else if(type==CURVE)
			{
				shape = new Curve(point1,points,color,penWidth);
				shapes.add(shape);
			}
			else if(type==ARC)
			{
				shape = new Arc(point1,point2,color,penWidth,isFilled);
				shapes.add(shape);
			}
			
			else if(type==ELLIPSE)
			{
				int width=Math.abs(xEnd-xStart);
				int height = Math.abs(yEnd-yStart);
				if(isShiftKeyDown) width=height;
				shape = new Ellipse(point1,point2,color,penWidth,isFilled,width,height);
				shapes.add(shape);
			}
			
			else {}
		  }
		  else
			  {
				for(Shape s : shapes)
				{
					if(s.contains(e.getPoint()))
					{
						
						
				      s.setColor(new Color(255,204,204));
					  repaint();
					  highlightedShape=s;
					  highlighted = true;
					  highlightedIndex=shapes.indexOf(s);
					}
				
				}
			}
		  
		  } 
		  
		 
	    }
	
	   private class MouseMotionEar extends MouseMotionAdapter
	   {
		public void mouseDragged(MouseEvent e)
		{
			moved = false;
			highlighted=false;
			if(!e.isMetaDown())
			{
				
				if(e.isShiftDown()) isShiftKeyDown=true;
				else isShiftKeyDown = false;
				isMouseDragged=true;
				xEnd = e.getX();
				yEnd= e.getY();
				p = new Point(xEnd,yEnd);
				points.add(p);
				repaint();
			}
			
		}
		
		public void mouseMoved(MouseEvent e)
		{
			isMouseDragged=false;
			if(highlighted)
			{
				
				{
					dx=e.getX()-xStart;
                dy=e.getY()-yStart;
			    //moved=true;
				shapes.get(highlightedIndex).move(dx,dy);
			    repaint();
				}
				
			}
			
			Color c=null;
			Shape sh =null;
			Graphics gg = getGraphics();
			Graphics2D g = (Graphics2D)gg;

				for(Shape s : shapes)
				{
					c=s.getColor();
					if(s.contains(e.getPoint()))
					{
						
						s.setColor(Color.pink);
						s.draw(g);
					    //highlightedShape=s;
						//repaint();
						
					}
					else
					{
						s.setColor(c);
						
				        repaint();
					}
				   
				   
				   s.setColor(c);
						
				        repaint();
				}
			 
		}
		
	   }
	   
	   private class PopEar extends MouseAdapter
	   {
		  
		   public void mouseReleased(MouseEvent e)
		  {
			  moved = false;
			  //highlighted =false;
			  int x=e.getX();
			  int y=e.getY();
			 if(e.isMetaDown())
			   {
			                  if(e.isPopupTrigger())
			                   pop.show(e.getComponent(),x,y);
				}	 
		  }
	   }
	
	   public void paintComponent(Graphics gg)
	   {
		super.paintComponent(gg);
		Graphics2D g = (Graphics2D)gg;
		g.setColor(color);
		
		
	if(isMouseDragged)
		switch(type)
		{
			case LINE:
			{
				if(moved) {xStart=dx;yStart=dy;xEnd=dx;yEnd=dy;}
				
		       g.drawLine(xStart,yStart,xEnd,yEnd);
		     
			  break;
			}
		
		   case RECTANGLE:
		   {
			
				int width=Math.abs(xEnd-xStart);
				int height = Math.abs(yEnd-yStart);
				int x = Math.min(xStart,xEnd);
				int y= Math.min(yStart,yEnd);
				if(isFilled)	g.fillRect(x,y,width,height);
				else g.drawRect(x,y,width,height);
			
		     
			break;
		   }
		
		  case CIRCLE:
		  {
			
				int radius = Math.abs(xEnd-xStart);
				int x = Math.abs(xStart-radius);
				int y = Math.abs(yStart-radius);
				if(isFilled) g.fillOval(x,y,2*radius,2*radius);
				else g.drawOval(x,y,2*radius,2*radius);
			
		   break;   
		  }
		  
		   case CURVE:
		   {
			    for(int i = 0;i<points.size()-1;i++)
		        {
			     Point p1 = (Point)points.get(i);
			     Point p2 = (Point)points.get(i+1);
		         g.drawLine(p1.x,p1.y,p2.x,p2.y);
		        }
			break;
		   }
		   
		   case ARC:
		   {
			   int width = Math.abs(xEnd-xStart);
			   int height = Math.abs(yEnd-yStart);
			    int x = Math.min(xStart,xEnd);
				int y= Math.min(yStart,yEnd);
		
		    if(isFilled) g.fillArc(x,y,width,height,0,120);
		    else g.drawArc(x,y,width,height,0,120);
			
			break;
		   }
		   
		   case ELLIPSE:
		   {
			
				int width=Math.abs(xEnd-xStart);
				int height = Math.abs(yEnd-yStart);
				int x = Math.min(xStart,xEnd);
				int y= Math.min(yStart,yEnd);
				if(isShiftKeyDown) width=height;
				if(isFilled)	g.fillOval(x,y,width,height);
				else g.drawOval(x,y,width,height);
			
		     
			break;
		   }
		} //end of switch 
		
		 for(Shape s : shapes) s.draw(g);
		
		     
	   }
	
	}
	
	
}