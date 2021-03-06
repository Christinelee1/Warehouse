//------------------------------------------------------
//範例程式4：判斷是否碰撞
//本程式以讀檔方式載入地圖，地圖檔名稱；map1.txt
//預設地圖大小為10*5，使用者可以自行定義
//陣列值說明 : 0=牆壁 ; 1=道路 ; 2:終點
//程式中已實作上下按鈕的程式碼
//程式中使用Judge()來判斷是否碰撞，已實作判斷是否碰到牆壁
//------------------------------------------------------

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class test1 extends JFrame {

	private final JLabel actor = new JLabel();
	private final JLabel wallLabel = new JLabel();
	private final JLabel roadLabel = new JLabel();
	private final JButton UpButton = new JButton();
	private final JButton RightButton = new JButton();
	private final JButton LeftButton = new JButton();
	private final JButton DownButton = new JButton();
	private final JLabel successLabel = new JLabel();
        private final JLabel box = new JLabel();
        private final JFrame message = new JFrame();

Icon road_icon = new ImageIcon(getClass().getResource("/png/road.PNG"));
	Icon wall_icon = new ImageIcon(getClass().getResource("/png/wall.PNG"));
	Icon success_icon = new ImageIcon(getClass().getResource("/png/success.PNG"));
	Icon box_icon = new ImageIcon(getClass().getResource("/png/box.PNG"));
	Icon ActorRight_icon = new ImageIcon(getClass().getResource("/png/b40right.png"));
        Icon ActorLeft_icon = new ImageIcon(getClass().getResource("/png/b40left.png"));

	private int[][] map;

	int current_x=0, current_y=0;
        int boxcurrent_x=1, boxcurrent_y=1;
	private final JLabel messageLabel = new JLabel();
	private final JLabel boxLabel = new JLabel();
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			test1 frame = new test1();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	public test1() {
		super();
		setBounds(100, 100, 500, 375);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			jbInit();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//
	}
	private void jbInit() throws Exception {
		getContentPane().setLayout(null);
                getContentPane().addKeyListener(new MyKeyListener());
		getContentPane().add(actor);
                getContentPane().add(box);
		actor.setIcon(ActorRight_icon);
                box.setIcon(box_icon);
		actor.setBounds(0, 0, 40, 40);
                box.setBounds(40, 40, 40, 40);
		getContentPane().getFocusListeners();
		
		map=readMap("map1.txt");//宣告一個二維陣列map來存取readMap回傳的二維陣列值
		
		for (int i=0; i<5; i++ )
		  for (int j=0; j<10; j++ )
		  {	
			  JLabel tmp = new JLabel();
			getContentPane().add(tmp);
			switch (map[i][j]){
			case 0: 
			    tmp.setIcon(road_icon);
			    break;
			case 1: 
			    tmp.setIcon( wall_icon);
			    break;
			case 2: 
			    tmp.setIcon(success_icon);
			    break;
                        
			default:
			    tmp.setIcon(road_icon);
				
			}
			tmp.setBounds(0+j*40, 0+i*40,40, 40);
		  } 

		getContentPane().add(wallLabel);
		wallLabel.setIcon(wall_icon);
		wallLabel.setText("wall");
		wallLabel.setBounds(171, 262, 40, 40);
		
		getContentPane().add(roadLabel);
		roadLabel.setIcon(road_icon);
		roadLabel.setText("road");
		roadLabel.setBounds(217, 262, 40, 40);
		
		getContentPane().add(UpButton);
		UpButton.addActionListener(new UpButtonActionListener());
		UpButton.setText("Up");
		UpButton.setBounds(57, 236, 56, 20);
		
		getContentPane().add(RightButton);
		RightButton.addActionListener(new RightButtonActionListener());
		RightButton.setText("Right");
		RightButton.setBounds(97, 262, 68, 20);
		
		getContentPane().add(LeftButton);
		LeftButton.addActionListener(new LeftButtonActionListener());
		LeftButton.setText("Left");
		LeftButton.setText("Left");
		LeftButton.setBounds(10, 262, 56, 20);
		
		getContentPane().add(DownButton);
		DownButton.addActionListener(new DownButtonActionListener());
		DownButton.setText("Down");
		DownButton.setBounds(46, 288, 68, 20);

		
		getContentPane().setFocusable(true);
		getContentPane().requestFocus();
	}
	private int[][] readMap(String mapfile) throws FileNotFoundException {
		
		File f = new File(mapfile);			//宣告一個File來讀取地圖檔
		int[][] map=new int[5][10];			//宣告一個二為陣列存取從地圖檔讀近來的值
		Scanner sc = new Scanner(f);		//利用Scanner來讀取地圖檔的值
			for(int i=0;i<5;i++){			//宣告一個巢狀回圈來讀取地圖檔
				for(int j=0;j<10;j++){
					map[i][j]=sc.nextInt();	//將從地圖檔讀取到的值放入陣列
				}
			}
		return map; 						//回傳讀取到的二維陣列值
	}
	private class UpButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			upButton_actionPerformed(e);
		}
	}
	private class DownButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			downButton_actionPerformed(e);
		}
	}
	private class RightButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			rightButton_actionPerformed(e);
		}
	}
	private class LeftButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			leftButton_actionPerformed(e);
		}
	}

        private class MyKeyListener extends KeyAdapter {
			
		public void keyPressed(KeyEvent e) {
			MykeyPressed(e);
		}
	}


	protected void upButton_actionPerformed(ActionEvent e) {
		if(current_y>0)
                {					//判斷current_y是否超過地圖範圍
			current_y--;					//取得移動角色上方的值
			if (judge())
                        {					//利用judge()來判斷地圖是否能行走
			   int x= actor.getX();			//取得移動角色目前的X座標
			   int y= actor.getY();			//取得移動角色目前的Y座標
                           if(box.getX() == x && box.getY() == y-40)
                           {
                              if(boxcurrent_y>0) 
                              {
                                 boxcurrent_y--;
                                 if(Bjudge()) 
                                 {
                                    box.setLocation(x, y-80); 
			            actor.setLocation(x, y-40);	//移動角色的y座標向上移動40
			         } else 
                                   {
                                      current_y++;				//若judge()判斷地圖不能行走，還原current_y
                                      boxcurrent_y++;
		                   }
                              }else 
                                current_y++;
			   }else actor.setLocation(x, y-40);	//移動角色的y座標向上移動40
			}
			else current_y++;				//若judge()判斷地圖不能行走，還原current_y
		}
		getContentPane().requestFocus();
	}
	protected void downButton_actionPerformed(ActionEvent e) {
		if(current_y<4){
			current_y++;
			if (judge())
			{
			   int x= actor.getX();
			   int y= actor.getY();
			    if(box.getX() == x && box.getY() == y+40){
				   if(boxcurrent_y<4){
					boxcurrent_y++;
					if (Bjudge()){
						box.setLocation(x, y+80);
						actor.setLocation(x, y+40);
					}else{
						current_y--;
						boxcurrent_y--;
					}
				}else current_y--;
			   }else actor.setLocation(x, y+ 40);	
			}
			else current_y--;
		}
		getContentPane().requestFocus();
	}
	protected void rightButton_actionPerformed(ActionEvent e) {
               actor.setIcon(ActorRight_icon);
               if(current_x<9){
			current_x++;
			if (judge())
			{
			   int x= actor.getX();
			   int y= actor.getY();
			   if(box.getX() == x+40 && box.getY() == y){
				   if(boxcurrent_x<9){
					boxcurrent_x++;
					if (Bjudge()){
						box.setLocation(x+80, y);
						actor.setLocation(x+40, y);
					}else{
						current_x--;
						boxcurrent_x--;
					}
				}else current_x--;
			   }else actor.setLocation(x+40, y);	
			}
			else current_x--;
		}
		getContentPane().requestFocus();

	}
                           
			
	protected void leftButton_actionPerformed(ActionEvent e) {
               actor.setIcon(ActorLeft_icon);
               if(current_x>0){					
			current_x--;					
			if (judge()){					
			   int x= actor.getX();			
			   int y= actor.getY();			
			   if(box.getX() == x-40 && box.getY() == y){
				   if(boxcurrent_x>0){
					boxcurrent_x--;
					if (Bjudge()){
						box.setLocation(x-80, y);
						actor.setLocation(x-40, y);
					}else{
						current_x++;
						boxcurrent_x++;
					}
				}else current_x++;
			   }else actor.setLocation(x-40, y);	
			}
			else current_x++;
		}
		getContentPane().requestFocus();

	}

	protected boolean judge() {
		boolean result = false;
		//0:road ; 1:wall ; 2:goal
		if (map[current_y][current_x]==0 || map[current_y][current_x]==2)		//若目前位置的map值為0=road
		  {result = true;}						//回傳可以移動
		
       return result;
	}

        protected boolean Bjudge() {
		boolean result = false;
		//0:road ; 1:wall ; 2:goal
		if (map[boxcurrent_y][boxcurrent_x]==0)		//若目前位置的map值為0=road
		  {result = true;}                              //回傳可以移動
                else if(map[boxcurrent_y][boxcurrent_x]==2){
			message.setLocation(200,200);
			  JOptionPane.showMessageDialog(message,"恭喜通關",
                                  "成功過關", JOptionPane.PLAIN_MESSAGE,
                                  success_icon);
		  
               }  						
		
       return result;
       }


  protected void MykeyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    if(key == 38) {
      if(current_y>0){					//判斷current_y是否超過地圖範圍
	current_y--;					//取得移動角色上方的值
	  if (judge()){					//利用judge()來判斷地圖是否能行走
	    int x= actor.getX();			//取得移動角色目前的X座標
	    int y= actor.getY();			//取得移動角色目前的Y座標
            if(box.getX() == x && box.getY() == y-40){
              if(boxcurrent_y>0){
                boxcurrent_y--;
                if(Bjudge()){
                  box.setLocation(x, y-80); 
	          actor.setLocation(x, y-40);	//移動角色的y座標向上移動40
	        } else{
                    current_y++;				//若judge()判斷地圖不能行走，還原current_y
                    boxcurrent_y++;
	        }
              }else 
                 current_y++;
	    }else 
               actor.setLocation(x, y-40);	//移動角色的y座標向上移動40
          }else 
             current_y++;				//若judge()判斷地圖不能行走，還原current_y
      }
    }
    else if(key == 40){
      if(current_y<4){
	current_y++;
	if (judge()){
	  int x= actor.getX();
	  int y= actor.getY();
	  if(box.getX() == x && box.getY() == y+40){
	    if(boxcurrent_y<4){
	      boxcurrent_y++;
	      if (Bjudge()){
	        box.setLocation(x, y+80);
	        actor.setLocation(x, y+40);
	      }else{
                 current_y--;
	         boxcurrent_y--;
	      }
	    }else 
               current_y--;
	  }else 
             actor.setLocation(x, y+ 40);	
        }else 
           current_y--;
      }
    }
    else if(key == 39){
      actor.setIcon(ActorRight_icon);
      if(current_x<9){
        current_x++;
	if (judge()){
          int x= actor.getX();
	  int y= actor.getY();
	  if(box.getX() == x+40 && box.getY() == y){
	    if(boxcurrent_x<9){
	      boxcurrent_x++;
	      if (Bjudge()){
                box.setLocation(x+80, y);
                actor.setLocation(x+40, y);
	      }else{
                 current_x--;
                 boxcurrent_x--;
	      }
	    }else 
              current_x--;
	  }else 
             actor.setLocation(x+40, y);	
	}else 
           current_x--;
      } 
    }
    else if(key == 37){  
      actor.setIcon(ActorLeft_icon);
      if(current_x>0){					
        current_x--;					
        if (judge()){					
          int x= actor.getX();			
	  int y= actor.getY();			
	  if(box.getX() == x-40 && box.getY() == y){
	    if(boxcurrent_x>0){
	    boxcurrent_x--;
	    if (Bjudge()){
	      box.setLocation(x-80, y);
	      actor.setLocation(x-40, y);
	    }else{
	       current_x++;
	       boxcurrent_x++;
					}
	    }else 
               current_x++;
	  }else 
             actor.setLocation(x-40, y);	
        }else 
           current_x++;
      }
    }
  } 
}
