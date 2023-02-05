package S16030021049;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Point;
import core.board.PieceColor;
import core.game.Move;

public class Board extends JFrame 
{
	public int flag=0;
	public Point point1=new Point(0,0);
	public Point point2=new Point(0,0);
	public int centreX = 238;
	public int centreY = 238;
	public int WrongX=13;
	public int WrongY=13;
	public Point[] p=new Point[2]; 
	public StaticBoard board = new StaticBoard();
	final BufferedImage ig = ImageIO.read(this.getClass().getResource("/Black.png"));
	final BufferedImage White = ImageIO.read(this.getClass().getResource("/White.png"));
	final BufferedImage TBlack = ImageIO.read(this.getClass().getResource("/TBlack.png"));
	final BufferedImage TWhite = ImageIO.read(this.getClass().getResource("/TWhite.png"));
	public Board() throws IOException
	{
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		BufferedImage bg = null;	
		bg = ImageIO.read(this.getClass().getResource("/Board.jpg"));
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		//设置窗口位置
		int frameWidth = bg.getWidth()+18;
		int frameHeight = bg.getHeight()+36;
		int frameX = (screenWidth - frameWidth)/2;
		int frameY = (screenHeight - frameHeight)/2;
		//设置图标
		this.setIconImage(bg);
		//将背景图片放置到计算好的位置
		this.setBounds(frameX, frameY, frameWidth, frameHeight);
		this.setTitle("棋盘");
		getContentPane().setLayout(null);
		
		
		JLabel boardLabel = new JLabel(new ImageIcon(bg));
		boardLabel.setBounds(0, 0, bg.getWidth(), bg.getHeight());
		JPanel imagePanel = (JPanel)this.getContentPane();
		
		imagePanel.setOpaque(false);
		this.getLayeredPane().add(boardLabel,0);
		      
        JLabel blackLabel = new JLabel(new ImageIcon(ig));
 		blackLabel.setBounds(frameWidth/2-21, frameHeight/2-30, 25, 25);
        this.getLayeredPane().add(blackLabel,0);
         
        Dicer3 computer=new Dicer3();
        final JLabel blackLabeltemp = new JLabel(new ImageIcon(TBlack));
        getLayeredPane().add(blackLabeltemp,0);
		this.addMouseListener(new MouseAdapter()
		{  //匿名内部类，鼠标事件
            public void mouseClicked(MouseEvent e)
            {   //鼠标完成点击事件
                if(e.getButton() == MouseEvent.BUTTON1)
                { 
                	//每次点击先调用setpiece函数在棋盘上显示一枚棋子             	
                	Point point = findlocation(e.getX(),e.getY()); 
                 	p[flag] = board.changeToMatrix(point);flag++; 
                	if(board.checkLegal(board.changeToMatrix(point)))
                	{//如果落子点合法则显示棋子
                		setPiece(point, PieceColor.WHITE);
                	}
             	    else flag--;//否则本次点击无效
                	//如果连续点击两次，通知findmove函数通知电脑计算对策
                    if(flag==2)
                    {
                    	flag=0;
                    	Point x;
                    	Move move = computer.findMove(new Move(p[0].x*19+p[0].y,p[1].x*19+p[1].y));
                    	//EndingOrNot();
                    	int move1=move.index1();
                    	int move2=move.index2();
                    	
                    	point = indexToLocation(move1);
                    	x = new Point(point.y,point.x);
                    	setPiece(x, PieceColor.BLACK);
          
                        point = indexToLocation(move2);
                        x = new Point(point.y,point.x);
                    	setPiece(x, PieceColor.BLACK);    
                    	EndingOrNot();
                    	                    
                    }
                }
            }           
        });
		
		this.addMouseMotionListener(new MouseAdapter()
		{
			public void mouseMoved(MouseEvent e)
	        {	            	
				getLayeredPane().remove(blackLabeltemp);
              	Point point=e.getPoint();
              	point = findlocation(point.x,point.y);
              	if(board.IsEmpty(point))
              	{
              		blackLabeltemp.setBounds(point.x, point.y, 25, 25);
                  	getLayeredPane().add(blackLabeltemp,0);
                  	repaint();
              	}
              	else
              	{
              		getLayeredPane().add(blackLabeltemp,0);
              	}
              	
	        }         
		});
				
		
	}
	public boolean EndingOrNot()
	{
		if(StaticBoard.table.roadTable.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Deal!", "ENDING", 
                    JOptionPane.CLOSED_OPTION);
             System.exit(0);
		}
		Road road = StaticBoard.table.roadTable.peek();
		if(road.value==10000000)
		{
			if(road.Whitenum==0)
			{
				JOptionPane.showMessageDialog(null, "Computer Won!", "ENDING", 
                        JOptionPane.CLOSED_OPTION);
                 System.exit(0);
			}
			if(road.Blacknum==0)
			{
				JOptionPane.showMessageDialog(null, "Computer lost again!", "ENDING", 
                        JOptionPane.CLOSED_OPTION);
                System.exit(0);
			}
		}
		else return false;
		return rootPaneCheckingEnabled;
	}
	public Point findlocation(int x,int y)
	{
		x=x-21;y=y-45;
		Point point =new Point();
		int newx,newy;
		newx=(x-WrongX)%25;
		newy=(y-WrongY)%25;
		
		if(newx>12.5)newx=x+(25-newx);
		else newx=x-newx;
		
		if(newy>12.5)newy=y+(25-newy);
		else newy=y-newy;
		
		if(newx<0)newx=13;
		if(newy<0)newy=13;
		if(newx>450)newx=463;
		if(newy>450)newy=463;
		point.x=newx;
		point.y=newy;	
		return point;	
	}
	
	public Point indexToLocation(int index)
	{
		Point point=new Point();
		point.x=index/19;
		point.y=index%19;
		
		point.x=point.x*25+13;
		point.y=point.y*25+13;
		return point;
	}
	
	public  Boolean setPiece(Point point,PieceColor piece)
	{
		JLabel b;
		if(piece==PieceColor.BLACK)
		{
			b = new JLabel(new ImageIcon(ig));
		}
		else
		{
			b = new JLabel(new ImageIcon(White));
		}
      	b.setBounds(point.x,point.y, 25, 25);
      	getLayeredPane().add(b,0);
      	repaint();
		return true;
		
	}
	
	
	 public static void main(String[] args) throws IOException
	 {
	       new Board();
	 }

}
