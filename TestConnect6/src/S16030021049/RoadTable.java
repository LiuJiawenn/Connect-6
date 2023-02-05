package S16030021049;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import core.board.PieceColor;

public class RoadTable
{
	private final int SIZE=19;  
	//hashMap以线性坐标作为索引返回该坐标影响到的所有路,最多24条
	public HashMap<Integer, ArrayList<Road>> relateRoads =new HashMap<>();
	
	//所有路，以权值正序排序，队首路为权值最高的路
	Queue<Road>  roadTable= new PriorityQueue<>(idComparator);
	public static Comparator<Road> idComparator =new Comparator<Road>()
	{
		public int compare(Road r1,Road r2)
		{
			return (int)(r2.value-r1.value);
		}
	};
	//路表初始化
	public RoadTable()
	{
		for(int i=0;i<SIZE;i++)
		{
			for(int j=0;j<SIZE;j++)
			{
				int pos = i*SIZE+j;
				relateRoads.put(pos, new  ArrayList<Road>());
				
				if(i<=5)
				{
					roadTable.add(new Road(pos,Direction.Down));
					if(j<=13)
					{
						roadTable.add(new Road(pos,Direction.RDown));
						roadTable.add(new Road(pos,Direction.Right));
					}
				}
				else if(i<=13)//i>5&&i<=13
				{
					roadTable.add(new Road(pos,Direction.Down));
					if(j<=13)
					{
						roadTable.add(new Road(pos,Direction.RUp));
						roadTable.add(new Road(pos,Direction.Right));
						roadTable.add(new Road(pos,Direction.RDown));
					}
				}
				else //i>13
				{
					if(j<=13)
					{
						roadTable.add(new Road(pos,Direction.RUp));
						roadTable.add(new Road(pos,Direction.Right));
					}
				}//end else
			}//end for（j）
		}//end for（i）
		
		roadTable.forEach(integer->
		{//初始化relateRoads
			HashMap<Integer,Integer> temp=integer.emptyPos;
			for(Map.Entry<Integer, Integer> entry:temp.entrySet())
			{
				relateRoads.get(entry.getKey()).add(integer);
			}
		});	
	    roadTableUpdate(180, PieceColor.BLACK, 1);
	}//end 构造函数
	
	public ArrayList<Road> temp = new ArrayList<>();
	public void roadTableUpdate(int index,PieceColor color, int youOrMe) 
	{   //落子后此函数可更新两个路表
		//在temp中备份相关路径
		ArrayList<Road> R = relateRoads.get(index);
		int len=R.size();
	    Road o;
		for(int i=0;i<len;i++)//遍历该落子点能影响到的所有路
		{
			o =R.get(i);
			roadTable.remove(o);//调用这条路的自我更新函数
			if(!o.SelfUpdate(index, color, youOrMe))
			{//如果这条路已经被打断了（同时含有黑白子），则删除这条路的所有记录
				//relateRoads.get(index).remove(o);			
				R.remove(o);//因为它已经没有任何价值了
				i--;len--;//调整指针
			}
			else
			{//如果更新后这条路仍然只有一个颜色的棋子，则将它插回优先队列，触发优先队列排序
				roadTable.add(o);
			}		
		}
	}	
	public void roadTableUpdate(int index,PieceColor color, int youOrMe,Backups backup)
	{
		ArrayList<Road> R = relateRoads.get(index);
		int len=R.size();
	    Road o;
		for(int i=0;i<len;i++)//遍历该落子点能影响到的所有路
		{
			o =R.get(i);
			roadTable.remove(o);//调用这条路的自我更新函数
			if(!o.SelfUpdate(index, color, youOrMe))
			{//如果这条路已经被打断了（同时含有黑白子），则删除这条路的所有记录
				backup.Interrupt.add(R.remove(i));//因为它已经没有任何价值了
				i--;len--;//调整指针
			}
			else
			{//如果更新后这条路仍然只有一个颜色的棋子，则将它插回优先队列，触发优先队列排序
				roadTable.add(o);
			}		
		}
	}
	
	
	public void roadTableReturnBack(int index,int youOrMe,Backups backup) 
	{
		ArrayList<Road> R = relateRoads.get(index);
		//R.addAll(backup.Interrupt);		
		for(Road r : R)
		{
			roadTable.remove(r);
			r.SelfReturnBack(index, 1);
			if(r.Blacknum !=0 && r.Whitenum != 0)
				R.remove(r);
			else
			roadTable.add(r);
		}
		for(Road r : backup.Interrupt)
		{
			r.SelfReturnBack(index, 1);
			if(r.Blacknum ==0 || r.Whitenum == 0)
			{		
				R.add(r);
				roadTable.add(r);
			}
			
		}
	}
	
}
