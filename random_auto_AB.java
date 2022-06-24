import java.util.HashSet;
import java.util.Set;
import java.io.*;

public class test {
	public static void main(String args[]) {
		GameGroup game = new GameGroup();
		new Thread(game,"giveNumberThread").start();    
		new Thread(game,"guessNumberThread").start();
	}
}
 
class GameGroup implements Runnable {     //兩位玩家互猜
	Set < Integer > hs = new HashSet < Integer > (); 
	int A_realNumber, B_guessNumber, B_bingo = 0;   
	int B_realNumber, A_guessNumber, A_bingo = 0;  
	
	String output_S = "";
	boolean guess = false, give = false;	  

	public void run() {
		for (int count = 1; true; count++) {
			
			if (B_bingo == 1 || A_bingo == 1)// 玩家A 或 玩家B 其中一個猜到就結束
			{
    			// 輸出結果為檔案
    	        try 
    	        {
    	        	FileWriter myWriter = new FileWriter("output.txt");
					myWriter.write(output_S);
					myWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
				
				
			}
			else
			{
				startGame(count);     //開啟遊戲回合
			}
			
		}
	}
 
	public synchronized void startGame(int count) {
		if (Thread.currentThread().getName() .equals("giveNumberThread") && give == false) { 
			A_realNumber = (int)(Math.random()*9000)+1000;    // 玩家A心中數生成
			B_realNumber = (int)(Math.random()*9000)+1000;	  // 玩家B心中數生成	
			give = true;    //玩家A 、玩家B 心中數已決定
		}
		
		if (Thread.currentThread().getName() .equals( "giveNumberThread")) {   
			while (!guess)   
			{
				try {
					wait(); 
				} 
				catch (InterruptedException e) {}
			}
			
			
			if (count % 2 == 0)
			{
				if (A_realNumber == B_guessNumber)
				{
					B_bingo = 1;
					
					System.out.println("玩家A:恭喜，玩家B答對了!");
					System.out.println("========================");
					System.out.println("玩家A心中數字為" + A_realNumber + "，遊戲結束");

					output_S += ("玩家A:恭喜，玩家B答對了！ \n");
					output_S +=       ("========================" + "\n");
					output_S += ("玩家A心中數字為" + A_realNumber + "，遊戲結束\n");
					
					return;   
				}
				else 
				{
					int A_counts = 0,B_counts = 0;  //A數量、B數量
					boolean check[]=new boolean[4]; //紀錄每個位數是否檢查過
					String ANS = String.valueOf(A_realNumber);
					String GUESS = String.valueOf(B_guessNumber);
					
					//檢查有幾A
	    			for(int i=0;i<4;i++){
	    				if(ANS.charAt(i)==GUESS.charAt(i))
	    				{
	    					A_counts++;
	    					check[i]=true;
	    				}
	    			}
	    			
	    			//檢查有幾B
	    			for(int i=0;i<4;i++)
	    			{
	    				for(int j=0;j<4;j++)
	    				{
	    					if(!check[j] && ANS.charAt(j)==GUESS.charAt(i))
	    					{
	    						B_counts++;
	    						check[j]=true;
	    						break;
	    					}
	    				}
	    			}
	    			
	    			System.out.println("玩家A:"+A_counts+"A"+B_counts+"B");
//					System.out.println("========================");
//					output_S +=       ("========================" + "\n");
	    			
				}
				
			}
			else
			{
				if (B_realNumber == A_guessNumber)
				{
					A_bingo = 1;
					System.out.println("玩家B:恭喜，玩家A答對了!");
					System.out.println("========================");
					System.out.println("玩家B心中數字為" + B_realNumber + "，遊戲結束");


					output_S += ("玩家B:恭喜，玩家A答對了!\n");
					output_S +=       ("========================" + "\n");
					output_S += ("玩家B心中數字為" + B_realNumber + "，遊戲結束\n");
					return; 
				}
				else
				{
					int A_counts = 0,B_counts = 0;  //A數量、B數量
					boolean check[]=new boolean[4]; //紀錄每個位數是否檢查過
					String ANS = String.valueOf(B_realNumber);
					String GUESS = String.valueOf(A_guessNumber);
					
					//檢查有幾A
	    			for(int i=0;i<4;i++){
	    				if(ANS.charAt(i)==GUESS.charAt(i))
	    				{
	    					A_counts++;
	    					check[i]=true;
	    				}
	    			}
	    			
	    			//檢查有幾B
	    			for(int i=0;i<4;i++)
	    			{
	    				for(int j=0;j<4;j++)
	    				{
	    					if(!check[j] && ANS.charAt(j)==GUESS.charAt(i))
	    					{
	    						B_counts++;
	    						check[j]=true;
	    						break;
	    					}
	    				}
	    			}
	    			
	    			System.out.println("玩家B:"+A_counts+"A"+B_counts+"B");
//					System.out.println("========================");
//					output_S +=       ("========================" + "\n");
					
				}
				
			}
			
			guess = false;  
		}
		
		if (Thread.currentThread().getName() .equals( "guessNumberThread") && give == true) {  
			while (guess)    
			{
				try {
					wait(); 
				} catch (InterruptedException e) {}
			}

			if(B_bingo != 1 && A_bingo != 1)
			{
				if (count %2 == 0)
				{
					System.out.println("======第"+count+"回合======");
					output_S +=       ("======第"+count+"回合======" + "\n");
					
					
					B_guessNumber = (int)(Math.random()*9000)+1000;// 玩家B猜的數字
					System.out.println("玩家B 猜 玩家A 的數字為:" + B_guessNumber);
					output_S += ("玩家B 猜 玩家A 的數字為:" + B_guessNumber +"\n");
					
				}
				else
				{
					System.out.println("======第"+count+"回合======");
					output_S +=       ("======第"+count+"回合======" + "\n");
					
					A_guessNumber = (int)(Math.random()*9000)+1000; // 玩家A猜的數字
					System.out.println("玩家A 猜 玩家B 的數字為:" + A_guessNumber);
					output_S += ("玩家A 猜 玩家B 的數字為:" + A_guessNumber +"\n");
					
				}

				


			}

			guess = true;
		}
		notifyAll();   //通知執行緒
	}
}
