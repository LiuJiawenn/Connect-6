package S16030021049;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import core.board.PieceColor;
import core.game.Move;
import core.player.Player;

public class Dicer3 extends Player {
	public StaticBoard b;
//	private static int order[]= {2,3,1,4,0,5}; 
	public Dicer3()
	{
		super();
		b = new StaticBoard();	
	}
	@Override
	public Move findMove(Move youmove) 
	{
		int c,v;
		Road road ;
		c=youmove.index1();
		v=youmove.index2();
		b.YousetPiece(new Point(c/19,c%19), PieceColor.WHITE);
		b.YousetPiece(new Point(v/19,v%19), PieceColor.WHITE);
		road = StaticBoard.table.roadTable.peek();
		if(road.value==10000000)
		{
			return new Move(-2,-2);
		}
		int[] p=new int[2];	
		
		HashMap<Integer,Integer> h ;
		
		for(int i=0;i<2;i++)
		{
			//ȡ�����׵�·
			road = StaticBoard.table.roadTable.peek();
			h = road.emptyPos;//ȡ����·�����п�λ
//			for(Map.Entry<Integer, Integer> entry:h.entrySet())
//				{//��λ��ѡ��һ������  
//					p[i] = entry.getKey();	break;
//				}
			if(road.value>9000)
			{			
				for(Map.Entry<Integer, Integer> entry:h.entrySet())
				{//��λ��ѡ��һ������  
					p[i] = entry.getKey();	break;
				}
			}
			else
			{
				p[i] = findnextMove(h);
			}
			
			b.IsetPiece(new Point(p[i]/19,p[i]%19), PieceColor.BLACK);
		}
		Move move = new Move(p[0],p[1]);
		return move;
		//return null;
		
	}
	
	public int findnextMove(HashMap<Integer,Integer> h)
	{
		int max = Integer.MIN_VALUE;
		int maxpos = 0;
		HashMap<Integer,Integer> p = new HashMap<Integer,Integer>() ;
		for(Map.Entry<Integer, Integer> entry:h.entrySet())
		{
			p.put(entry.getKey(), entry.getValue());
		}

		for(Map.Entry<Integer, Integer> entry:p.entrySet())
		{
			Backups backup= new Backups(entry.getKey());
			Point point =new Point(entry.getKey()/19,entry.getKey()%19);
			b.ITry(point, PieceColor.BLACK,backup);
			ArrayList<Road> temp = new ArrayList<>();
			int power = 0;
			Road t;
			for(int i=0;i<40;i++)
			{
				t = StaticBoard.table.roadTable.poll();
				power += (t.Blacknum-t.Whitenum)*100;
				temp.add(t);
			}
			StaticBoard.table.roadTable.addAll(temp);	
			if(power>max)
			{
				max = power;
				maxpos=entry.getKey();
			}
			b.Returnback(point, 1,backup);
		}	
		return maxpos;
		
	}
	
//	public int CalcuPos()
//	{
//		ArrayList<Integer> PossiblePos  = new ArrayList<>();
//		Road road ;//��ȡ�����׵�·
//		road = StaticBoard.table.roadTable.peek();
//		HashMap<Integer,Integer>h = road.emptyPos;//ȡ����·�����п�λ;
//		int max = Integer.MIN_VALUE;//��¼����߲��Ĺ�ֵ
//		int target = 0;//��¼���λ��
//		
//		if(road.Blacknum>=4 || road.Whitenum>=5 )
//		{
//			for(Map.Entry<Integer, Integer> entry:h.entrySet())
//			{//��λ��ѡ��һ������  
//				return entry.getKey();
//			}
//		}
//		else if(road.Whitenum==4)
//		{
//			for(Map.Entry<Integer, Integer> entry:h.entrySet())
//			{
//				PossiblePos.add(entry.getKey());
//			}
//		}
//		else
//		{
//			findPossiblePos(PossiblePos);		
//		}
//		//���Ͻ����п��ܵ�����λ�÷�����PossiblePos��
//		//��������п��ܵ�λ�ý��а�����������֦�ҵ����λ��
//		int maybeMax;
//		for(int a:PossiblePos)
//		{
//			maybeMax = AlphaBeta(a,Integer.MIN_VALUE,Integer.MAX_VALUE,1,9);
//			if(maybeMax > max)
//			{
//				max = maybeMax;
//				target = a;
//			}
//		}
//		return target;
//	}
//	public int AlphaBeta(int pos ,int alpha,int beta,int depth,int limit)
//	{
//		Road t = StaticBoard.table.roadTable.peek();
//		if(t.value>10000)
//			return t.value*(t.Blacknum-t.Whitenum);
//		if(depth == limit)
//		{//���ﵽ���Ʋ���ʱ��������������ֵ
//			ArrayList<Road> temp = new ArrayList<>();
//			int power = 0;
//			//Road t;
//			for(int i=0;i<10;i++)
//			{
//				t = StaticBoard.table.roadTable.poll();
//				power += (t.Blacknum-t.Whitenum)*100;
//				temp.add(t);
//			}
//			StaticBoard.table.roadTable.addAll(temp);	
//			return power;
//		}
//		int layer = depth%4;
//		ArrayList<Integer> PossiblePos  = new ArrayList<>();
//		int node;
//		int value = 0;
//		int len ;
//		int lim = Integer.MIN_VALUE;
//		Backups backup= new Backups(pos);
//		if(layer == 1 || layer == 2)//max��
//		{
//			b.IsetPiece(new Point(pos/19,pos%19), PieceColor.BLACK);
//			findPossiblePos(PossiblePos);
//			
//			if(PossiblePos.size() == 0)
//			{
//				backup.Returnback(pos);
//				return StaticBoard.table.roadTable.peek().value+limit-depth;
//			}
//				
//			
//			len = PossiblePos.size();
//			for(int i=0;i<len;i++)
//			{
//				node = PossiblePos.remove(0);
//				value = AlphaBeta(node ,alpha ,beta, depth+1, limit);
//				backup.Returnback(pos);
//				
//				if(value>beta)//��max�����betaҪ��֦
//				{
//					return beta;
//				}
//				else if(value>alpha)//����alphabeta�����ڣ���Ҫ����alphaֵ
//				{
//					alpha=value;
//					lim=value;
//				}
//				else if(value>lim)//��С��alphaֵ����Ҫ��¼��ǰvalue���Զ��ֵܽڵ�������Ѱ������߲�
//				{
//					lim=value;
//				}
//			}		
//		}
//		else//min��
//		{
//			b.YousetPiece(new Point(pos/19,pos%19), PieceColor.WHITE);
//			findPossiblePos(PossiblePos);
//			if(PossiblePos.size() == 0)
//			{
//				backup.Returnback(pos);
//				return StaticBoard.table.roadTable.peek().value+limit-depth;
//			}
//				
//			
//			len = PossiblePos.size();
//			for(int i=0;i<len;i++)
//			{
//				node = PossiblePos.remove(0);
//				value = AlphaBeta(node ,alpha ,beta, depth+1, limit);
//				if(value<alpha)//��min��С��alphaҪ��֦
//				{
//					return alpha;
//				}
//				else if(value<beta)//����alphabeta������Ҫ����betaֵ
//				{
//					beta=value;
//					lim=value;
//				}
//				else if(value<lim)//�򣬴���beta����Ҫ��¼��ǰvalue�Զ��ֵܽڵ�������������߲���
//				{
//					lim=value;
//				}
//			}		
//		}
//		backup.Returnback(pos);
//		return limit;
//	}
//	
//	public void findPossiblePos(ArrayList<Integer> PossiblePos)
//	{
//		ArrayList<Road> temp = new ArrayList<>();
//		//PossiblePos  = new ArrayList<>();
//		Road t;
////		Road t = StaticBoard.table.roadTable.peek();
////		if(t.value>10000)
////			return;
//		
//		for(int i=0;i<5;i++)
//		{
//			t = StaticBoard.table.roadTable.poll();
//			for(Map.Entry<Integer, Integer> entry:t.emptyPos.entrySet())
//			{
//				if(!PossiblePos.contains(entry.getKey()))
//				{
//					PossiblePos.add(entry.getKey());
//				}   
//			}
//			temp.add(t);
//		}
//		StaticBoard.table.roadTable.addAll(temp);				
//	}
//	
	@Override
	public boolean isManual() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "Computer";
	}

}
