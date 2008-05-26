package board;
import java.util.*;

public class Board {

	/**
	 * Board contains the blocks, like a chess board 
	 * 
	 * n - width, m - height
	 * 
	 * @author Pawe³
	 * */
	
	LinkedList<Block> list = new LinkedList<Block>();
	
	public Board(int width,int height){
		
		int id=0;
		for(int col=1; col<=height; col++){
			
			for(int row=1; row<=width; row++){
				
				Block tmp = new Block(col,row,++id );
				list.add(tmp);
			}
		}
		
	}
	
	public void writeToConsoleBlocks(){
		
		for (Block b : list) {
			System.out.println(b.getId()+" , column:"+b.getColumn()+", row: "+b.getRow());
		}
		
		
	}
	
	
	
}
