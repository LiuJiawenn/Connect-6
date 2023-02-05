package S16030021049;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import core.board.PieceColor;

public class Road 
{
	 public final int startPoint;
	 public final Direction dir;
	 
	 public int Blacknum = 0;
	 public int Whitenum = 0;
	 public int Emptynum = 6;
	 
	 public HashMap<Integer,Integer>  emptyPos = new HashMap<>();
	 public Stack<Integer> indexCopy = new Stack<>();
	 public Stack<Integer> valueCopy = new Stack<>();
	 	 
	// public boolean Interrupt = false;
	 public int value=0;

	//���ڳ�ʼ��ʱʹ��   
	 public Road(int Start,Direction Dir) 
	 {
		 startPoint = Start;
		 dir = Dir;
		 InitemptyPos();
	 }
	 public void InitemptyPos()
	 {
		 int t;
		 switch(dir)
		 {
		 case RUp:for(int i=0;i<6;i++)
		          {
			          t=startPoint-19*i+i;
			          emptyPos.put(t,i);
		          }break;
			         
		 case Right:for(int i=0;i<6;i++)
                    {
	                  t=startPoint+i;
	                  emptyPos.put(t,i);
                    }break;
		 case RDown:for(int i=0;i<6;i++)
		            {
			          t=startPoint+19*i+i;
			          emptyPos.put(t,i);
		            }break;
		 case Down:for(int i=0;i<6;i++)
		            {
                      t=startPoint+i*19;
                      emptyPos.put(t,i);
		            }
		 }	 
	 }
	 //�����Ӻ�ĸ��º���
	 public boolean SelfUpdate(int index,PieceColor color,int youOrMe)
	 {   //1�����ҷ�2����з�
		 int key = 0 ;
		 int key2 = 0;
		 if(color==PieceColor.BLACK)
		 {
			 Blacknum++;
			 key= youOrMe*1000+Blacknum*100;
		 }
		 else 
		 {
			 Whitenum++;
			 key= youOrMe*1000+Whitenum*100;
		 }
		 Emptynum--;
		 valueCopy.push(value);
		 indexCopy.push(emptyPos.get(index));
		 emptyPos.remove(index);
		 if(Blacknum!=0 && Whitenum!=0)
			 return false;
		 
		
		 
		 for(Map.Entry<Integer, Integer> entry:emptyPos.entrySet())
		 {
			 key2 += (int)Math.pow(2,entry.getValue());
		 }
		 
		 
		 if(ReadRoadValue.values.containsKey(key+key2))
		 {
			 value=ReadRoadValue.values.get(key+key2);
		 }	 
		 else
		 {
			 value= ReadRoadValue.values.
					 get(key);
		 }		 
		 return true;
	 }
	 
	 public void SelfReturnBack(int index,int youOrMe)
	 {
		 PieceColor t = StaticBoard.board[index/19][index%19];
		 if(t == PieceColor.BLACK)
		 {
			 Blacknum--;
		 }
		 else
		 {
			 Whitenum--;
		 }
		 Emptynum++;
		 emptyPos.put(index, indexCopy.pop());
		 value = valueCopy.pop();		 
	 }
}
