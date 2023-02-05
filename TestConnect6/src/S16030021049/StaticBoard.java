package S16030021049;

import java.awt.Point;

import core.board.PieceColor;

public class StaticBoard
{
	public static PieceColor[][] board = new PieceColor[19][19];
	public static RoadTable table = new RoadTable();	
	static
	{
		Initboard();
	}
	public StaticBoard(){
		;}
	
	public static void Initboard()
	{
		for(int i=0;i<19;i++)
		{
			for(int j=0;j<19;j++)
			{
				board[i][j]=PieceColor.EMPTY;
			}
		}
		board[9][9]=PieceColor.BLACK;
	}
	
	public void IsetPiece(Point point,PieceColor piece) 
	{
		board[point.x][point.y]=piece;
		table.roadTableUpdate(point.x*19+point.y, piece, 1);
	}
	public void YousetPiece(Point point,PieceColor piece) 
	{
		board[point.x][point.y]=piece;
		table.roadTableUpdate(point.x*19+point.y, piece, 2);
	}
	public void ITry(Point point,PieceColor piece,Backups backup) 
	{
		board[point.x][point.y]=piece;
		table.roadTableUpdate(point.x*19+point.y, piece, 1,backup);
	}
	public void YouTry(Point point,PieceColor piece,Backups backup) 
	{
		board[point.x][point.y]=piece;
		table.roadTableUpdate(point.x*19+point.y, piece, 2,backup);
	}
	public void Returnback(Point point,int who,Backups backup)
	{
		table.roadTableReturnBack(point.x*19+point.y ,who,backup);
		board[point.x][point.y]=PieceColor.EMPTY;
	}
	public boolean checkLegal(Point point)
	{
		if(board[point.x][point.y]==PieceColor.EMPTY)return true;
		else return false;	
	}

	public Point changeToMatrix(Point point)
	{
		Point p=new Point();
		p.y=(point.x-13)/25;
		p.x=(point.y-13)/25;	
		return p;
	}
	public boolean IsEmpty(Point point)
	{
		Point p = changeToMatrix(point);
		if(board[p.x][p.y]!=PieceColor.EMPTY)return false;
		else return true;
	}

}
