/**
 * @(#)RubixJava2D.java
 *
 *
 * @author Brandon Sheets
 * @version 0.3.3 2016/2/4
 */
import java.util.*;
import java.net.*;
import static java.lang.System.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.io.*;

public class RubixJava2D {
public static final int REDC=0,ORANGEC=1,BLUEC=2,GREENC=3,YELLOWC=4,WHITEC=5;
public static boolean turning=false;
public static final Color C_ORANGE = new Color(255,128,0), C_GREEN = new Color(0,123,0);
public static int[][] f = {{0,0,0},{0,0,0},{0,0,0}},
					  b = {{1,1,1},{1,1,1},{1,1,1}},
					  l = {{2,2,2},{2,2,2},{2,2,2}},
					  r = {{3,3,3},{3,3,3},{3,3,3}},
					  u = {{4,4,4},{4,4,4},{4,4,4}},
					  d = {{5,5,5},{5,5,5},{5,5,5}};
public ArrayList<String> moves = new ArrayList<String>();
public static int moveCounter=0;
public String[] rotations = {"F","F'","B","B'","L","L'","R","R'","U","U'","D","D'"};
public String[] randomMoves = new String[50];
public JButton l_Button, li_Button, r_Button, ri_Button, u_Button, ui_Button, d_Button, di_Button, f_Button, fi_Button, 

b_Button, bi_Button;
public JButton rotate_left, rotate_right, rotate_up, rotate_down;
private RubixDisplay display;
private ArrayList<ScoreEntry> scoreboard;
public Timer timer;
private String scoreS="";
public String clock;
private PrintStream stream = null;
private static int time=0;
private static boolean win=false;
    public RubixJava2D()
    {
    	scoreboard = new ArrayList<ScoreEntry>();
    	try{Scanner scan = new Scanner(new File("scoreboard.dat"));
    		while(scan.hasNext())
    		{
    			String entry = scan.nextLine();
    			String[] entryA = entry.split(",");
    			String entryName = entryA[0].substring(1);
    			String entryTime = entryA[1];
    			int entryMoves = Integer.valueOf(entryA[2].substring(0,entryA[2].length()-1));
    			scoreboard.add(new ScoreEntry(entryName,entryTime,entryMoves));
    		}
    		}catch(FileNotFoundException e){try{stream = new PrintStream("scoreboard.dat");}catch(Exception 

ex){out.print(ex);}}
    	display = new RubixDisplay();
    	timer = new Timer(1000,timeAction);
    	restart();
    }

    ActionListener timeAction = new ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		time++;
    		display.repaint();

    	}
    };

    public void restart()
    {
    	time=0;
    	moveCounter=0;
    	randomize();
    	for(String move:randomMoves)
    		turn(move);

    	display.repaint();
    }

    public static void main(String[] args)
    {
  		new RubixJava2D();
    }

    public void saveResults()
    {
    	timer.stop();
    	try{stream = new PrintStream("scoreboard.dat");
    		Scanner scan = new Scanner(new File("scoreboard.dat"));
    		while(scan.hasNext())
    		{
    			String entry = scan.nextLine();
    			String[] entryA = entry.split(",");
    			String entryName = entryA[0].substring(1);
    			String entryTime = entryA[1];
    			int entryMoves = Integer.valueOf(entryA[2].substring(0,entryA[2].length()-1));
    			scoreboard.add(new ScoreEntry(entryName,entryTime,entryMoves));
    		}
    	}
    	catch(Exception ex){}
    	String name = JOptionPane.showInputDialog(null,"Enter your name","Save your result",1);
		if(name!=null)
		{ScoreEntry score = new ScoreEntry(name,clock,moveCounter);
		scoreboard.add(score);
		Collections.sort(scoreboard);
		if(scoreboard.size()>10)
		{
			while(scoreboard.size()!=10)
			{scoreboard.remove(scoreboard.size()-1);}
		}
		try{
		for(ScoreEntry entry:scoreboard)
			stream.append(entry+"\n");}catch(Exception e){}
		}

    }

	private void checkForWin()
	{
		//have it check each face to see if they're all correct and then display win window
		if(f[0][0]==f[0][1]&&f[0][0]==f[0][2]&&f[0][0]==f[1][0]&&f[0][0]==f[1][1]&&f[0][0]==f[1][2]&&f[00][0]==f[2][0]&&f[0][0]==f[2][1]&&f[0][0]==f[2][2])
			if(b[0][0]==b[0][1]&&b[0][0]==b[0][2]&&b[0][0]==b[1][0]&&b[0][0]==b[1][1]&&b[0][0]==b[1][2]&&b[00][0]==b[2][0]&&b[0][0]==b[2][1]&&b[0][0]==b[2][2])
				if(u[0][0]==u[0][1]&&u[0][0]==u[0][2]&&u[0][0]==u[1][0]&&u[0][0]==u[1][1]&&u[0][0]==u[1][2]&&u[00][0]==u[2][0]&&u[0][0]==u[2][1]&&u[0][0]==u[2][2])
					if(d[0][0]==d[0][1]&&d[0][0]==d[0][2]&&d[0][0]==d[1][0]&&d[0][0]==d[1][1]&&d[0][0]==d[1][2]&&d[00][0]==d[2][0]&&d[0][0]==d[2][1]&&d[0][0]==d[2][2])
						if(l[0][0]==l[0][1]&&l[0][0]==l[0][2]&&l[0][0]==l[1][0]&&l[0][0]==l[1][1]&&l[0][0]==l[1][2]&&l[00][0]==l[2][0]&&l[0][0]==l[2][1]&&l[0][0]==l[2][2])
							if(r[0][0]==r[0][1]&&r[0][0]==r[0][2]&&r[0][0]==r[1][0]&&r[0][0]==r[1][1]&&r[0][0]==r[1][2]&&r[00][0]==r[2][0]&&r[0][0]==r[2][1]&&r[0][0]==r[2][2])
							{
								Object[] options = {"New Game","Exit"};timer.stop();
								Integer result = JOptionPane.showOptionDialog(null,"Congratulations!!"+
												

								   "\nYou solved it in:\n"+clock.substring(0,2)+" min and "+
								   clock.substring(3)+"sec\n"+
								   "With "+moveCounter+" moves.","YOU WIN",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
								if(result==JOptionPane.YES_OPTION)
								{saveResults();restart();timer.start();}
								else
								{saveResults();restart();display.menu();}
							}
	}

	private void rotateRight()
	{
		int[][] temp = new int[3][3];
		for(int r=0;r<3;r++)
			for(int c=0;c<3;c++)
				temp[r][c]=f[r][c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				f[row][c]=r[row][c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				r[row][c]=b[row][c];
		for(int r=0;r<3;r++)
			for(int c=0;c<3;c++)
				b[r][c]=l[r][c];
		for(int r=0;r<3;r++)
			for(int c=0;c<3;c++)
				l[r][c]=temp[r][c];

		int temp0=u[0][0];
		int temp1=u[1][0];
		u[0][0]=u[2][0];
		u[1][0]=u[2][1];
		u[2][0]=u[2][2];
		u[2][1]=u[1][2];
		u[2][2]=u[0][2];
		u[1][2]=u[0][1];
		u[0][2]=temp0;
		u[0][1]=temp1;

		temp0=d[0][0];
		temp1=d[0][1];
		d[0][0]=d[0][2];
		d[0][1]=d[1][2];
		d[0][2]=d[2][2];
		d[1][2]=d[2][1];
		d[2][2]=d[2][0];
		d[2][1]=d[1][0];
		d[2][0]=temp0;
		d[1][0]=temp1;

	}

	private void rotateLeft()
	{
		int[][] temp = new int[3][3];
		for(int r=0;r<3;r++)
			for(int c=0;c<3;c++)
				temp[r][c]=f[r][c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				f[row][c]=l[row][c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				l[row][c]=b[row][c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				b[row][c]=r[row][c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				r[row][c]=temp[row][c];

		int temp0 =u[0][0];
		int temp1 =u[0][1];
		u[0][0]=u[0][2];
		u[0][1]=u[1][2];
		u[0][2]=u[2][2];
		u[1][2]=u[2][1];
		u[2][2]=u[2][0];
		u[2][1]=u[1][0];
		u[2][0]=temp0;
		u[1][0]=temp1;

		temp0=d[0][0];
		temp1=d[1][0];
		d[0][0]=d[2][0];
		d[1][0]=d[2][1];
		d[2][0]=d[2][2];
		d[2][1]=d[1][2];
		d[2][2]=d[0][2];
		d[1][2]=d[0][1];
		d[0][2]=temp0;
		d[0][1]=temp1;
	}

	private void rotateUp()
	{
		//rotate up
		int[][] temp = new int[3][3];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				temp[row][c]=f[row][c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				f[row][c]=d[row][c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				d[row][c]=b[2-row][2-c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				b[row][c]=u[2-row][2-c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				u[row][c]=temp[row][c];

		int temp0 =l[0][0];
		int temp1=l[0][1];
		l[0][0]=l[0][2];
		l[0][1]=l[1][2];
		l[0][2]=l[2][2];
		l[1][2]=l[2][1];
		l[2][2]=l[2][0];
		l[2][1]=l[1][0];
		l[2][0]=temp0;
		l[1][0]=temp1;

		temp0 =r[0][0];
		temp1=r[1][0];
		r[0][0]=r[2][0];
		r[1][0]=r[2][1];
		r[2][0]=r[2][2];
		r[2][1]=r[1][2];
		r[2][2]=r[0][2];
		r[1][2]=r[0][1];
		r[0][2]=temp0;
		r[0][1]=temp1;

	}

	private void rotateDown()
	{
		//rotate down
		int[][] temp = new int[3][3];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				temp[row][c]=f[row][c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				f[row][c]=u[row][c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				u[row][c]=b[2-row][2-c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				b[row][c]=d[2-row][2-c];
		for(int row=0;row<3;row++)
			for(int c=0;c<3;c++)
				d[row][c]=temp[row][c];

		int temp0=l[0][0];
		int temp1=l[1][0];
		l[0][0]=l[2][0];
		l[1][0]=l[2][1];
		l[2][0]=l[2][2];
		l[2][1]=l[1][2];
		l[2][2]=l[0][2];
		l[1][2]=l[0][1];
		l[0][2]=temp0;
		l[0][1]=temp1;

		temp0=r[0][0];
		temp1=r[0][1];
		r[0][0]=r[0][2];
		r[0][1]=r[1][2];
		r[0][2]=r[2][2];
		r[1][2]=r[2][1];
		r[2][2]=r[2][0];
		r[2][1]=r[1][0];
		r[2][0]=temp0;
		r[1][0]=temp1;

	}

	private void displayAll()
	{
		out.println("FRONT");
    	display(f);
    	out.println("BACK");
    	display(b);
    	out.println("LEFT");
    	display(l);
    	out.println("RIGHT");
    	display(r);
    	out.println("UP");
    	display(u);
    	out.println("DOWN");
    	display(d);
	}

	private void display(int[][] face)
    {
    	//out.println(faceSide.toUpp5erCase());
    	for(int[] row:face)
    	{
    		for(int data:row)
    		{
    			out.print( data==REDC?"R": (data==ORANGEC?"O": (data==BLUEC?"B": (data==GREENC?"G": (data==YELLOWC?"Y": (data==WHITEC?"W": "-"))))));
    		}
    		out.println();
    	}
    	out.println();
    }

    private void userTurn()
    {
    	ArrayList<String> possibleChoices = new ArrayList<String>(Arrays.asList("F","F'","B","B'","L","L'","R","R'","U","U'","D","D'","moves","undo","random moves","randomize","rotate right","rotate left"));
    	boolean moveValid = false;
    	Scanner moveScan = new Scanner(in);
    	out.println("Enter the move in the form of:\nU, D, L, R, F, or B for CW turns and\nU', D', L', R', F', or B' for CCW turns.\n You can display your moves with \"moves\" and \n undo with the \"undo\"");
    	String move="";
    	while(!moveValid&&!win)
    	{
    		move = moveScan.nextLine();
    		if(move.equalsIgnoreCase("stop"))
    			win=true;
    		else{
    		if(possibleChoices.contains(move))
    		{
  				if(move.equals("moves"))
  				{
  					out.println(movesMade());
  				}
  				else
  				{
  					if(move.equals("undo"))
  					{
  						if(moves.size()>0)
  							undo();
  						displayAll();
  					}
  					else
  						if(move.equals("random moves"))
  						{System.out.println(randomMoves);}
  						else
  							if(move.equals("randomize"))
  							{
  								randomMoves = randomize();
  								for(String data:randomMoves)
  									turn(move);
  							}
  						else
  						if(move.equals("rotate right"))
  						{rotateRight();displayAll();}
  						else
  						if(move.equals("rotate left"))
  						{rotateLeft();displayAll();}
  					else
  					{
    					moveValid=true;
    				if(moves.size()>0&&isOpposite(move,moves.get(moves.size()-1)))
    					moves.remove(moves.size()-1);
    				else
    					moves.add(move);
  					}
  				}
    		}
    		else
    		{
    			out.println("Please enter a valid move option!");
    			move = moveScan.nextLine();
    			displayAll();
    		}}

    	}
    	turn(move);
    	displayAll();
    }

    private boolean isOpposite(String move1, String move2)
    {
    	if((move1.equals("L")&&move2.equals("L'"))||(move1.equals("L'")&&move2.equals("L")))
    		return true;
    	else
    		if((move1.equals("B")&&move2.equals("B'"))||(move1.equals("B'")&&move2.equals("B")))
    			return true;
    		else
    			if((move1.equals("R")&&move2.equals("R'"))||(move1.equals("R'")&&move2.equals("R")))
    				return true;
    			else
    				if((move1.equals("U")&&move2.equals("U'"))||(move1.equals("U'")&&move2.equals("U")))
    					return true;
    				else
    					if((move1.equals("D")&&move2.equals("D'"))||(move1.equals("D'")&&move2.equals("D")))
    						return true;
    					else
    						if((move1.equals("F")&&move2.equals("F'"))||(move1.equals("F'")&&move2.equals("F")))
    							return true;
    						else
    							return false;
    }

	private String movesMade()
	{
		String data="";
		data="The User has made the moves:\n"+moves.toString();
		return data;
	}

	private String[] randomize()
	{
		for(int x=0;x<50;x++)
		{
			int rand = (int)(Math.random()*12);
			randomMoves[x]=rotations[rand];
		}

		return randomMoves;
	}

	private void faceCount()
	{
		int oC=0,rC=0,bC=0,gC=0,wC=0,yC=0;
		for(int[] line:f)
			for(int data:line)
			{
				if(data==REDC)
					rC++;
				else if(data==WHITEC)
					wC++;
				else if(data==BLUEC)
					bC++;
				else if(data==YELLOWC)
					yC++;
				else if(data==GREENC)
					gC++;
				else
					oC++;

			}
		for(int[] line:b)
			for(int data:line)
			{
				if(data==REDC)
					rC++;
				else if(data==WHITEC)
					wC++;
				else if(data==BLUEC)
					bC++;
				else if(data==YELLOWC)
					yC++;
				else if(data==GREENC)
					gC++;
				else
					oC++;

			}
		for(int[] line:l)
			for(int data:line)
			{
				if(data==REDC)
					rC++;
				else if(data==WHITEC)
					wC++;
				else if(data==BLUEC)
					bC++;
				else if(data==YELLOWC)
					yC++;
				else if(data==GREENC)
					gC++;
				else
					oC++;

			}
		for(int[] line:r)
			for(int data:line)
			{
				if(data==REDC)
					rC++;
				else if(data==WHITEC)
					wC++;
				else if(data==BLUEC)
					bC++;
				else if(data==YELLOWC)
					yC++;
				else if(data==GREENC)
					gC++;
				else
					oC++;

			}
		for(int[] line:u)
			for(int data:line)
			{
				if(data==REDC)
					rC++;
				else if(data==WHITEC)
					wC++;
				else if(data==BLUEC)
					bC++;
				else if(data==YELLOWC)
					yC++;
				else if(data==GREENC)
					gC++;
				else
					oC++;

			}
		for(int[] line:d)
			for(int data:line)
			{
				if(data==REDC)
					rC++;
				else if(data==WHITEC)
					wC++;
				else if(data==BLUEC)
					bC++;
				else if(data==YELLOWC)
					yC++;
				else if(data==GREENC)
					gC++;
				else
					oC++;

			}

		out.println("Red   : "+rC);
		out.println("Green : "+gC);
		out.println("Blue  : "+bC);
		out.println("Yellow: "+yC);
		out.println("Orange: "+oC);
		out.println("White : "+wC);
	}

	private String getOppositeMove(String move)
	{
		if(move.equals("F"))
			return "F'";
		if(move.equals("F'"))
			return "F";
		if(move.equals("L"))
			return "L'";
		if(move.equals("L'"))
			return "L";
		if(move.equals("R"))
			return "R'";
		if(move.equals("R'"))
			return "R";
		if(move.equals("B"))
			return "B'";
		if(move.equals("B'"))
			return "B";
		if(move.equals("U"))
			return "U'";
		if(move.equals("U'"))
			return "U";
		if(move.equals("D"))
			return "D'";
		if(move.equals("F'"))
			return "D";
		if(move.equals("U"))
			return "U'";

			return "U";
	}

	private void undo()
	{
		String lastMove = moves.get(moves.size()-1);
		moves.remove(moves.size()-1);
		turn(getOppositeMove(lastMove));
	}

    private void turn(String turn)
    {
    	int temp=0, temp0=0, temp1=0, temp2=0, temp3=0;
    	if(!turning)
    	{
    		turning=true;
    		moves.add(turn);
    	if(turn.equals("F"))
    	{
    		//out.println("Front CW");
    		//face
			temp =f[0][0];
			temp0=f[1][0];
			f[0][0]=f[2][0];
			f[1][0]=f[2][1];
			f[2][0]=f[2][2];
			f[2][1]=f[1][2];
			f[2][2]=f[0][2];
			f[1][2]=f[0][1];
			f[0][2]=temp;
			f[0][1]=temp0;
    		//sides
    		temp1=u[2][0];
    		temp2=u[2][1];
    		temp3=u[2][2];

    		u[2][0]=l[2][2];
    		u[2][1]=l[1][2];
    		u[2][2]=l[0][2];

    		l[2][2]=d[0][2];
    		l[1][2]=d[0][1];
    		l[0][2]=d[0][0];

    		d[0][2]=r[0][0];
    		d[0][1]=r[1][0];
    		d[0][0]=r[2][0];

    		r[0][0]=temp1;
    		r[1][0]=temp2;
    		r[2][0]=temp3;
    	}
    	if(turn.equals("F'"))
    	{
    		//out.println("f CCW");
			//face
			temp =f[0][0];
			temp0=f[0][1];
			f[0][0]=f[0][2];
			f[0][1]=f[1][2];
			f[0][2]=f[2][2];
			f[1][2]=f[2][1];
			f[2][2]=f[2][0];
			f[2][1]=f[1][0];
			f[2][0]=temp;
			f[1][0]=temp0;
			//sides
			temp1=u[2][2];
			temp2=u[2][1];
			temp3=u[2][0];

			u[2][2]=r[2][0];
			u[2][1]=r[1][0];
			u[2][0]=r[0][0];

			r[2][0]=d[0][0];
			r[1][0]=d[0][1];
			r[0][0]=d[0][2];

			d[0][0]=l[0][2];
			d[0][1]=l[1][2];
			d[0][2]=l[2][2];

			l[0][2]=temp1;
			l[1][2]=temp2;
			l[2][2]=temp3;
    	}
    	if(turn.equals("B"))
    	{
			//out.println("B CW");
			//face
			temp =b[0][0];
			temp0=b[0][1];
			b[0][0]=b[0][2];
			b[0][1]=b[1][2];
			b[0][2]=b[2][2];
			b[1][2]=b[2][1];
			b[2][2]=b[2][0];
			b[2][1]=b[1][0];
			b[2][0]=temp;
			b[1][0]=temp0;
			//sides
			temp1=u[0][0];
			temp2=u[0][1];
			temp3=u[0][2];

			u[0][0]=l[2][0];
			u[0][1]=l[1][0];
			u[0][2]=l[0][0];

			l[2][0]=d[2][2];
			l[1][0]=d[2][1];
			l[0][0]=d[2][0];

			d[2][0]=r[2][2];
			d[2][1]=r[1][2];
			d[2][2]=r[0][2];

			r[0][2]=temp1;
			r[1][2]=temp2;
			r[2][2]=temp3;
    	}
    	if(turn.equals("B'"))
    	{
			//out.println("B' CCW");
			//face
			temp =b[0][0];
			temp0=b[1][0];
			b[0][0]=b[2][0];
			b[1][0]=b[2][1];
			b[2][0]=b[2][2];
			b[2][1]=b[1][2];
			b[2][2]=b[0][2];
			b[1][2]=b[0][1];
			b[0][2]=temp;
			b[0][1]=temp0;
			//sides
			temp1=u[0][0];
			temp2=u[0][1];
			temp3=u[0][2];

			u[0][0]=r[0][2];
			u[0][1]=r[1][2];
			u[0][2]=r[2][2];

			r[0][2]=d[2][2];
			r[1][2]=d[2][1];
			r[2][2]=d[2][0];

			d[2][2]=l[2][0];
			d[2][1]=l[1][0];
			d[2][0]=l[0][0];

			l[2][0]=temp1;
			l[1][0]=temp2;
			l[0][0]=temp3;
    	}
    	if(turn.equals("L"))
    	{
			//out.println("L CW");
			//face
			temp =l[0][0];
			temp0=l[1][0];
			l[0][0]=l[2][0];
			l[1][0]=l[2][1];
			l[2][0]=l[2][2];
			l[2][1]=l[1][2];
			l[2][2]=l[0][2];
			l[1][2]=l[0][1];
			l[0][2]=temp;
			l[0][1]=temp0;
			//sides
			temp1=u[0][0];
			temp2=u[1][0];
			temp3=u[2][0];

			u[0][0]=b[2][2];
			u[1][0]=b[1][2];
			u[2][0]=b[0][2];
			b[0][2]=d[2][0];
			b[1][2]=d[1][0];
			b[2][2]=d[0][0];

			d[2][0]=f[2][0];
			d[1][0]=f[1][0];
			d[0][0]=f[0][0];

			f[0][0]=temp1;
			f[1][0]=temp2;
			f[2][0]=temp3;

    	}
    	if(turn.equals("L'"))
    	{
			//out.println("L CCW");
			//face
			temp =l[0][0];
			temp0=l[0][1];
			l[0][0]=l[0][2];
			l[0][1]=l[1][2];
			l[0][2]=l[2][2];
			l[1][2]=l[2][1];
			l[2][2]=l[2][0];
			l[2][1]=l[1][0];
			l[2][0]=temp;
			l[1][0]=temp0;
			//sides
			temp1=u[0][0];
			temp2=u[1][0];
			temp3=u[2][0];

			u[0][0]=f[0][0];
			u[1][0]=f[1][0];
			u[2][0]=f[2][0];

			f[0][0]=d[0][0];
			f[1][0]=d[1][0];
			f[2][0]=d[2][0];

			d[0][0]=b[2][2];
			d[1][0]=b[1][2];
			d[2][0]=b[0][2];

			b[2][2]=temp1;
			b[1][2]=temp2;
			b[0][2]=temp3;
    	}
    	if(turn.equals("R"))
    	{
			//out.println("R CW");
			//face
			temp =r[0][0];
			temp0=r[1][0];
			r[0][0]=r[2][0];
			r[1][0]=r[2][1];
			r[2][0]=r[2][2];
			r[2][1]=r[1][2];
			r[2][2]=r[0][2];
			r[1][2]=r[0][1];
			r[0][2]=temp;
			r[0][1]=temp0;
			//side
			temp1=u[0][2];
			temp2=u[1][2];
			temp3=u[2][2];

			u[0][2]=f[0][2];
			u[1][2]=f[1][2];
			u[2][2]=f[2][2];

			f[0][2]=d[0][2];
			f[1][2]=d[1][2];
			f[2][2]=d[2][2];

			d[0][2]=b[2][0];
			d[1][2]=b[1][0];
			d[2][2]=b[0][0];

			b[2][0]=temp1;
			b[1][0]=temp2;
			b[0][0]=temp3;

    	}
    	if(turn.equals("R'"))
    	{
			//out.println("R CCW");
			//face
			temp =r[0][0];
			temp0=r[0][1];
			r[0][0]=r[0][2];
			r[0][1]=r[1][2];
			r[0][2]=r[2][2];
			r[1][2]=r[2][1];
			r[2][2]=r[2][0];
			r[2][1]=r[1][0];
			r[2][0]=temp;
			r[1][0]=temp0;
			//side
			temp1=u[0][2];
			temp2=u[1][2];
			temp3=u[2][2];

			u[0][2]=b[2][0];
			u[1][2]=b[1][0];
			u[2][2]=b[0][0];

			b[2][0]=d[0][2];
			b[1][0]=d[1][2];
			b[0][0]=d[2][2];

			d[0][2]=f[0][2];
			d[1][2]=f[1][2];
			d[2][2]=f[2][2];

			f[0][2]=temp1;
			f[1][2]=temp2;
			f[2][2]=temp3;
    	}
    	if(turn.equals("U"))
    	{
			//out.println("U CW");
			//face
			temp =u[0][0];
			temp0=u[1][0];
			u[0][0]=u[2][0];
			u[1][0]=u[2][1];
			u[2][0]=u[2][2];
			u[2][1]=u[1][2];
			u[2][2]=u[0][2];
			u[1][2]=u[0][1];
			u[0][2]=temp;
			u[0][1]=temp0;
			//side
			temp1 = f[0][0];
			temp2 = f[0][1];
			temp3 = f[0][2];

			f[0][0]=r[0][0];
			f[0][1]=r[0][1];
			f[0][2]=r[0][2];

			r[0][0]=b[0][0];
			r[0][1]=b[0][1];
			r[0][2]=b[0][2];

			b[0][0]=l[0][0];
			b[0][1]=l[0][1];
			b[0][2]=l[0][2];

			l[0][0]=temp1;
			l[0][1]=temp2;
			l[0][2]=temp3;
    	}
    	if(turn.equals("U'"))
    	{
			//out.println("U CCW");
			//face
			temp =u[0][0];
			temp0=u[0][1];
			u[0][0]=u[0][2];
			u[0][1]=u[1][2];
			u[0][2]=u[2][2];
			u[1][2]=u[2][1];
			u[2][2]=u[2][0];
			u[2][1]=u[1][0];
			u[2][0]=temp;
			u[1][0]=temp0;
			//side
			temp1 = f[0][0];
			temp2 = f[0][1];
			temp3 = f[0][2];

			f[0][0]=l[0][0];
			f[0][1]=l[0][1];
			f[0][2]=l[0][2];

			l[0][0]=b[0][0];
			l[0][1]=b[0][1];
			l[0][2]=b[0][2];

			b[0][0]=r[0][0];
			b[0][1]=r[0][1];
			b[0][2]=r[0][2];

			r[0][0]=temp1;
			r[0][1]=temp2;
			r[0][2]=temp3;
    	}
    	if(turn.equals("D"))
    	{
			//out.println("D CW");
			//face
			temp =d[0][0];
			temp0=d[1][0];
			d[0][0]=d[2][0];
			d[1][0]=d[2][1];
			d[2][0]=d[2][2];
			d[2][1]=d[1][2];
			d[2][2]=d[0][2];
			d[1][2]=d[0][1];
			d[0][2]=temp;
			d[0][1]=temp0;
			//side
			temp1 = f[2][0];
			temp2 = f[2][1];
			temp3 = f[2][2];

			f[2][0]=l[2][0];
			f[2][1]=l[2][1];
			f[2][2]=l[2][2];

			l[2][0]=b[2][0];
			l[2][1]=b[2][1];
			l[2][2]=b[2][2];

			b[2][0]=r[2][0];
			b[2][1]=r[2][1];
			b[2][2]=r[2][2];

			r[2][0]=temp1;
			r[2][1]=temp2;
			r[2][2]=temp3;
    	}
    	if(turn.equals("D'"))
    	{
			//out.println("D CCW");
			//face
			temp =d[0][0];
			temp0=d[0][1];
			d[0][0]=d[0][2];
			d[0][1]=d[1][2];
			d[0][2]=d[2][2];
			d[1][2]=d[2][1];
			d[2][2]=d[2][0];
			d[2][1]=d[1][0];
			d[2][0]=temp;
			d[1][0]=temp0;
			//side
			temp1 = f[2][0];
			temp2 = f[2][1];
			temp3 = f[2][2];

			f[2][0]=r[2][0];
			f[2][1]=r[2][1];
			f[2][2]=r[2][2];

			r[2][0]=b[2][0];
			r[2][1]=b[2][1];
			r[2][2]=b[2][2];

			b[2][0]=l[2][0];
			b[2][1]=l[2][1];
			b[2][2]=l[2][2];

			l[2][0]=temp1;
			l[2][1]=temp2;
			l[2][2]=temp3;
    	}
    	turning=false;
    	//faceCount();
    	}
    }

    class RubixDisplay extends JFrame
    {
    	public RubixDisplay()
    	{
    		setSize(480,480);
    		setLocationRelativeTo(null);
    		setDefaultCloseOperation(3);
    		setContentPane(new MenuDisplay());
    		setResizable(false);
    		setTitle("Java Rubik's Cube - Brandon Sheets");
    		setIconImage(Toolkit.getDefaultToolkit().getImage("Rubiks-Cube.gif"));
    		setUndecorated(true);
    		setVisible(true);
    	}
    	public void menu()
    	{
    		setContentPane(new MenuDisplay());
    		try{Thread.sleep(1000);}catch(Exception e){}
    		setVisible(true);
    		repaint();
    	}
	class MenuDisplay extends JPanel
	{
		public MenuDisplay()
		{
			setLayout(null);
			JButton start = new JButton("Start");
    		start.setSize(75,25);
    		start.setLocation(202,358);
    		start.setFocusable(false);
    		start.addActionListener(new StartAction());
    		add(start);

    		JButton scoreBoard = new JButton("Scoreboard");
    		scoreBoard.setSize(102,25);
    		scoreBoard.setLocation(189,388);
    		scoreBoard.setFocusable(false);
    		scoreBoard.addActionListener(new ScoreAction());
    		add(scoreBoard);

    		JButton about = new JButton("About");
    		about.setSize(75,25);
    		about.setLocation(202,418);
    		about.setFocusable(false);
    		about.addActionListener(new AboutAction());
    		add(about);

    		JButton exit_Button = new JButton("X");
			exit_Button.setBackground(Color.RED);
			exit_Button.setForeground(Color.WHITE);
			exit_Button.setSize(45,15);
			exit_Button.setLocation(430,5);
			exit_Button.setFocusable(false);
			exit_Button.addActionListener(new ExitPgrmAction());
			add(exit_Button);

			try{
			Icon icon = new ImageIcon("rotating_cube.gif");
    		JLabel label = new JLabel(icon);
    		label.setSize(300,225);
    		label.setFocusable(false);
    		label.setVisible(true);
    		label.setLocation(90,128);
    		add(label);}catch(Exception e){System.out.println(e);}
		}
		public void paintComponent(Graphics g)
		{
			Font forte = new Font("Forte",1,48);
			g.drawRect(89,127,301,226);
			g.setFont(forte);
			g.drawString("Java Rubik's",(int)((480-g.getFontMetrics().stringWidth("Java Rubik's"))/2),80);
			g.drawString("Cube",(int)((480-g.getFontMetrics().stringWidth("Cube"))/2),120);
			g.drawRect(0,0,479,479);
			g.setFont(new Font("Times New Roman",1,15));
			g.setColor(Color.LIGHT_GRAY);
			g.drawString("© Brandon Sheets 2016",15,479-g.getFontMetrics().getHeight());
		}
	}
    class CubeDisplay extends JPanel
    {
    	public CubeDisplay()
    	{
    		setLayout(null);

    		setForeground(Color.GRAY);

    		l_Button = new JButton("L");
    		l_Button.setSize(50,25);
    		l_Button.setLocation(20,390);
    		l_Button.setFocusable(false);
    		l_Button.addActionListener(new TurnAction());
    		add(l_Button);

    		li_Button = new JButton("Li");
    		li_Button.setSize(50,25);
    		li_Button.setLocation(20,420);
    		li_Button.setFocusable(false);
    		li_Button.addActionListener(new TurnAction());
    		add(li_Button);

    		f_Button = new JButton("F");
    		f_Button.setSize(50,25);
    		f_Button.setLocation(75,390);
    		f_Button.setFocusable(false);
    		f_Button.addActionListener(new TurnAction());
    		add(f_Button);

    		fi_Button = new JButton("Fi");
    		fi_Button.setSize(50,25);
    		fi_Button.setLocation(75,420);
    		fi_Button.setFocusable(false);
    		fi_Button.addActionListener(new TurnAction());
    		add(fi_Button);

    		u_Button = new JButton("U");
    		u_Button.setSize(50,25);
    		u_Button.setLocation(130,390);
    		u_Button.setFocusable(false);
    		u_Button.addActionListener(new TurnAction());
    		add(u_Button);

    		ui_Button = new JButton("Ui");
    		ui_Button.setSize(50,25);
    		ui_Button.setLocation(130,420);
    		ui_Button.setFocusable(false);
    		ui_Button.addActionListener(new TurnAction());
    		add(ui_Button);

    		d_Button = new JButton("D");
    		d_Button.setSize(50,25);
    		d_Button.setLocation(185,390);
    		d_Button.setFocusable(false);
    		d_Button.addActionListener(new TurnAction());
    		add(d_Button);

    		di_Button = new JButton("Di");
    		di_Button.setSize(50,25);
    		di_Button.setLocation(185,420);
    		di_Button.setFocusable(false);
    		di_Button.addActionListener(new TurnAction());
    		add(di_Button);

    		b_Button = new JButton("B");
    		b_Button.setSize(50,25);
    		b_Button.setLocation(240,390);
    		b_Button.setFocusable(false);
    		b_Button.addActionListener(new TurnAction());
    		add(b_Button);

    		bi_Button = new JButton("Bi");
    		bi_Button.setSize(50,25);
    		bi_Button.setLocation(240,420);
    		bi_Button.setFocusable(false);
    		bi_Button.addActionListener(new TurnAction());
    		add(bi_Button);

    		r_Button = new JButton("R");
    		r_Button.setSize(50,25);
    		r_Button.setLocation(295,390);
    		r_Button.setFocusable(false);
    		r_Button.addActionListener(new TurnAction());
    		add(r_Button);

    		ri_Button = new JButton("Ri");
    		ri_Button.setSize(50,25);
    		ri_Button.setLocation(295,420);
    		ri_Button.setFocusable(false);
    		ri_Button.addActionListener(new TurnAction());
    		add(ri_Button);

//    		JButton solve_Button = new JButton("Solve");
//    		solve_Button.setSize(100,25);
//    		solve_Button.setLocation(360,420);
//    		solve_Button.setFocusable(false);
//    		add(solve_Button);

			JButton exit_Button = new JButton("X");
			exit_Button.setBackground(Color.RED);
			exit_Button.setForeground(Color.WHITE);
			exit_Button.setSize(45,15);
			exit_Button.setLocation(430,5);
			exit_Button.setFocusable(false);
			exit_Button.addActionListener(new ExitGameAction());
			add(exit_Button);

    		rotate_left = new JButton("L");
    		rotate_left.setSize(50,25);
    		rotate_left.setLocation(20,100);
    		rotate_left.setFocusable(false);
    		rotate_left.addActionListener(new RotateAction());
    		add(rotate_left);

			rotate_right = new JButton("R");
    		rotate_right.setSize(50,25);
    		rotate_right.setLocation(20,130);
    		rotate_right.setFocusable(false);
    		rotate_right.addActionListener(new RotateAction());
    		add(rotate_right);

    		rotate_up = new JButton("U");
    		rotate_up.setSize(50,25);
    		rotate_up.setLocation(20,160);
    		rotate_up.setFocusable(false);
    		rotate_up.addActionListener(new RotateAction());
    		add(rotate_up);

    		rotate_down = new JButton("D");
    		rotate_down.setSize(50,25);
    		rotate_down.setLocation(20,190);
    		rotate_down.setFocusable(false);
    		rotate_down.addActionListener(new RotateAction());
    		add(rotate_down);

    		JLabel turn = new JLabel();
    		turn.setFont(new Font("Times New Roman",1,15));
    		turn.setText("Rotate Cube:");
    		turn.setSize(120,50);
    		turn.setOpaque(true);
    		turn.setVisible(true);
    		turn.setLocation(20,65);
    		add(turn);

    		JLabel turn2 = new JLabel();
    		turn2.setFont(new Font("Times New Roman",1,15));
    		turn2.setText("Rotate Face:");
    		turn2.setSize(120,50);
    		turn2.setOpaque(true);
    		turn2.setVisible(true);
    		turn2.setLocation(20,355);
    		add(turn2);

    		JButton help = new JButton("?");
    		help.setSize(45,15);
    		help.setForeground(Color.WHITE);
    		help.setBackground(Color.BLUE);
    		help.setVisible(true);
    		help.setFocusable(false);
    		help.setLocation(380,5);
    		help.addActionListener(new HelpAction());
    		add(help);

    	}
    	public void paintComponent(Graphics g)
    	{
    		g.drawRect(0,0,479,479);

    		int clockT = time;

    		int min=0;
    		int sec=0;
    		if(clockT/60>=1)
    		{
    			min=clockT/60;
    			clockT-=(min*60);
    		}
    		sec=clockT;
    		clock = (min<10?"0"+min:min)+":"+(sec<10?"0"+sec:sec);
    		g.setFont(new Font("Times New Roman",1,20));
    		g.drawString("Time: "+clock,360,50);
    		g.drawString("Moves: "+moveCounter,360,75);

    		Polygon f00=new Polygon(),f01=new Polygon(),
    		f02=new Polygon(),f10=new Polygon(),f11=new Polygon(),
    		f12=new Polygon(),f20=new Polygon(),f21=new Polygon(),
    		f22=new Polygon();
    		Polygon l00=new Polygon(),l01=new Polygon(),
    		l02=new Polygon(),l10=new Polygon(),l11=new Polygon(),
    		l12=new Polygon(),l20=new Polygon(),l21=new Polygon(),
    		l22=new Polygon();
    		Polygon u00=new Polygon(),u01=new Polygon(),
    		u02=new Polygon(),u10=new Polygon(),u11=new Polygon(),
    		u12=new Polygon(),u20=new Polygon(),u21=new Polygon(),
    		u22=new Polygon();

    		f00.addPoint(235,137+50);
    		f00.addPoint(235,178+50);
    		f00.addPoint(265,170+50);
    		f00.addPoint(265,130+50);

    		f01.addPoint(265,130+50);
    		f01.addPoint(265,170+50);
    		f01.addPoint(292,163+50);
    		f01.addPoint(292,124+50);

    		f02.addPoint(292,124+50);
    		f02.addPoint(292,163+50);
    		f02.addPoint(317,156+50);
    		f02.addPoint(318,117+50);

    		f10.addPoint(235,178+50);
    		f10.addPoint(235,219+50);
    		f10.addPoint(264,210+50);
    		f10.addPoint(265,170+50);

    		f11.addPoint(265,170+50);
    		f11.addPoint(264,210+50);
    		f11.addPoint(291,201+50);
    		f11.addPoint(292,163+50);

    		f12.addPoint(292,163+50);
    		f12.addPoint(291,201+50);
    		f12.addPoint(315,194+50);
    		f12.addPoint(317,156+50);

    		f20.addPoint(235,219+50);
    		f20.addPoint(235,257+50);
    		f20.addPoint(264,247+50);
    		f20.addPoint(264,210+50);

    		f21.addPoint(264,210+50);
    		f21.addPoint(264,247+50);
    		f21.addPoint(290,238+50);
    		f21.addPoint(291,201+50);

    		f22.addPoint(291,201+50);
    		f22.addPoint(290,238+50);
    		f22.addPoint(314,229+50);
    		f22.addPoint(315,194+50);

    		g.setColor(f[0][0]==REDC?Color.RED:
    				  (f[0][0]==BLUEC?Color.BLUE:
    				  (f[0][0]==GREENC?C_GREEN:
    				  (f[0][0]==ORANGEC?C_ORANGE:
    				  (f[0][0]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(f00);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(f00);
    		g.setColor(f[0][1]==REDC?Color.RED:
    				  (f[0][1]==BLUEC?Color.BLUE:
    				  (f[0][1]==GREENC?C_GREEN:
    				  (f[0][1]==ORANGEC?C_ORANGE:
    				  (f[0][1]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(f01);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(f01);
    		g.setColor(f[0][2]==REDC?Color.RED:
    				  (f[0][2]==BLUEC?Color.BLUE:
    				  (f[0][2]==GREENC?C_GREEN:
    				  (f[0][2]==ORANGEC?C_ORANGE:
    				  (f[0][2]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(f02);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(f02);
    		g.setColor(f[1][0]==REDC?Color.RED:
    				  (f[1][0]==BLUEC?Color.BLUE:
    				  (f[1][0]==GREENC?C_GREEN:
    				  (f[1][0]==ORANGEC?C_ORANGE:
    				  (f[1][0]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(f10);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(f10);
    		g.setColor(f[1][1]==REDC?Color.RED:
    				  (f[1][1]==BLUEC?Color.BLUE:
    				  (f[1][1]==GREENC?C_GREEN:
    				  (f[1][1]==ORANGEC?C_ORANGE:
    				  (f[1][1]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(f11);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(f11);
    		g.setColor(f[1][2]==REDC?Color.RED:
    				  (f[1][2]==BLUEC?Color.BLUE:
    				  (f[1][2]==GREENC?C_GREEN:
    				  (f[1][2]==ORANGEC?C_ORANGE:
    				  (f[1][2]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(f12);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(f12);
    		g.setColor(f[2][0]==REDC?Color.RED:
    				  (f[2][0]==BLUEC?Color.BLUE:
    				  (f[2][0]==GREENC?C_GREEN:
    				  (f[2][0]==ORANGEC?C_ORANGE:
    				  (f[2][0]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(f20);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(f20);
    		g.setColor(f[2][1]==REDC?Color.RED:
    				  (f[2][1]==BLUEC?Color.BLUE:
    				  (f[2][1]==GREENC?C_GREEN:
    				  (f[2][1]==ORANGEC?C_ORANGE:
    				  (f[2][1]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(f21);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(f21);
    		g.setColor(f[2][2]==REDC?Color.RED:
    				  (f[2][2]==BLUEC?Color.BLUE:
    				  (f[2][2]==GREENC?C_GREEN:
    				  (f[2][2]==ORANGEC?C_ORANGE:
    				  (f[2][2]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(f22);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(f22);

    		l00.addPoint(152,117+50);
    		l00.addPoint(153,157+50);
    		l00.addPoint(178,163+50);
    		l00.addPoint(177,124+50);

    		l01.addPoint(177,124+50);
    		l01.addPoint(178,163+50);
    		l01.addPoint(206,171+50);
    		l01.addPoint(205,130+50);

    		l02.addPoint(205,130+50);
    		l02.addPoint(206,171+50);
    		l02.addPoint(235,178+50);
    		l02.addPoint(235,137+50);

    		l10.addPoint(153,157+50);
    		l10.addPoint(155,195+50);
    		l10.addPoint(179,202+50);
    		l10.addPoint(178,163+50);

    		l11.addPoint(178,163+50);
    		l11.addPoint(179,202+50);
    		l11.addPoint(206,210+50);
    		l11.addPoint(206,171+50);

    		l12.addPoint(206,171+50);
    		l12.addPoint(206,210+50);
    		l12.addPoint(235,219+50);
    		l12.addPoint(235,178+50);

    		l20.addPoint(155,195+50);
    		l20.addPoint(156,229+50);
    		l20.addPoint(180,238+50);
    		l20.addPoint(179,202+50);

    		l21.addPoint(179,202+50);
    		l21.addPoint(180,238+50);
    		l21.addPoint(207,247+50);
    		l21.addPoint(206,210+50);

    		l22.addPoint(206,210+50);
    		l22.addPoint(207,247+50);
    		l22.addPoint(235,257+50);
    		l22.addPoint(235,219+50);

    		g.setColor(l[0][0]==REDC?Color.RED:
    				  (l[0][0]==BLUEC?Color.BLUE:
    				  (l[0][0]==GREENC?C_GREEN:
    				  (l[0][0]==ORANGEC?C_ORANGE:
    				  (l[0][0]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(l00);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(l00);
    		g.setColor(l[0][1]==REDC?Color.RED:
    				  (l[0][1]==BLUEC?Color.BLUE:
    				  (l[0][1]==GREENC?C_GREEN:
    				  (l[0][1]==ORANGEC?C_ORANGE:
    				  (l[0][1]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(l01);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(l01);
    		g.setColor(l[0][2]==REDC?Color.RED:
    				  (l[0][2]==BLUEC?Color.BLUE:
    				  (l[0][2]==GREENC?C_GREEN:
    				  (l[0][2]==ORANGEC?C_ORANGE:
    				  (l[0][2]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(l02);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(l02);
    		g.setColor(l[1][0]==REDC?Color.RED:
    				  (l[1][0]==BLUEC?Color.BLUE:
    				  (l[1][0]==GREENC?C_GREEN:
    				  (l[1][0]==ORANGEC?C_ORANGE:
    				  (l[1][0]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(l10);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(l10);
    		g.setColor(l[1][1]==REDC?Color.RED:
    				  (l[1][1]==BLUEC?Color.BLUE:
    				  (l[1][1]==GREENC?C_GREEN:
    				  (l[1][1]==ORANGEC?C_ORANGE:
    				  (l[1][1]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(l11);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(l11);
    		g.setColor(l[1][2]==REDC?Color.RED:
    				  (l[1][2]==BLUEC?Color.BLUE:
    				  (l[1][2]==GREENC?C_GREEN:
    				  (l[1][2]==ORANGEC?C_ORANGE:
    				  (l[1][2]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(l12);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(l12);
    		g.setColor(l[2][0]==REDC?Color.RED:
    				  (l[2][0]==BLUEC?Color.BLUE:
    				  (l[2][0]==GREENC?C_GREEN:
    				  (l[2][0]==ORANGEC?C_ORANGE:
    				  (l[2][0]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(l20);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(l20);
    		g.setColor(l[2][1]==REDC?Color.RED:
    				  (l[2][1]==BLUEC?Color.BLUE:
    				  (l[2][1]==GREENC?C_GREEN:
    				  (l[2][1]==ORANGEC?C_ORANGE:
    				  (l[2][1]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(l21);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(l21);
    		g.setColor(l[2][2]==REDC?Color.RED:
    				  (l[2][2]==BLUEC?Color.BLUE:
    				  (l[2][2]==GREENC?C_GREEN:
    				  (l[2][2]==ORANGEC?C_ORANGE:
    				  (l[2][2]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(l22);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(l22);

    		u00.addPoint(152,117+50);
    		u00.addPoint(177,123+50);
    		u00.addPoint(207,117+50);
    		u00.addPoint(183,111+50);

    		u01.addPoint(183,111+50);
    		u01.addPoint(207,117+50);
    		u01.addPoint(235,111+50);
    		u01.addPoint(215,106+50);

    		u02.addPoint(215,106+50);
    		u02.addPoint(235,111+50);
    		u02.addPoint(261,106+50);
    		u02.addPoint(235,101+50);

    		u10.addPoint(177,123+50);
    		u10.addPoint(205,130+50);
    		u10.addPoint(235,123+50);
    		u10.addPoint(207,117+50);

    		u11.addPoint(207,117+50);
    		u11.addPoint(235,123+50);
    		u11.addPoint(263,117+50);
    		u11.addPoint(235,111+50);

    		u12.addPoint(235,111+50);
    		u12.addPoint(263,117+50);
    		u12.addPoint(289,111+50);
    		u12.addPoint(261,106+50);

    		u20.addPoint(205,130+50);
    		u20.addPoint(235,137+50);
    		u20.addPoint(266,130+50);
    		u20.addPoint(235,123+50);

    		u21.addPoint(235,123+50);
    		u21.addPoint(266,130+50);
    		u21.addPoint(293,124+50);
    		u21.addPoint(263,117+50);

    		u22.addPoint(263,117+50);
    		u22.addPoint(293,124+50);
    		u22.addPoint(319,117+50);
    		u22.addPoint(289,111+50);

    		g.setColor(u[0][0]==REDC?Color.RED:
    				  (u[0][0]==BLUEC?Color.BLUE:
    				  (u[0][0]==GREENC?C_GREEN:
    				  (u[0][0]==ORANGEC?C_ORANGE:
    				  (u[0][0]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(u00);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(u00);
    		g.setColor(u[0][1]==REDC?Color.RED:
    				  (u[0][1]==BLUEC?Color.BLUE:
    				  (u[0][1]==GREENC?C_GREEN:
    				  (u[0][1]==ORANGEC?C_ORANGE:
    				  (u[0][1]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(u01);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(u01);
    		g.setColor(u[0][2]==REDC?Color.RED:
    				  (u[0][2]==BLUEC?Color.BLUE:
    				  (u[0][2]==GREENC?C_GREEN:
    				  (u[0][2]==ORANGEC?C_ORANGE:
    				  (u[0][2]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(u02);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(u02);
    		g.setColor(u[1][0]==REDC?Color.RED:
    				  (u[1][0]==BLUEC?Color.BLUE:
    				  (u[1][0]==GREENC?C_GREEN:
    				  (u[1][0]==ORANGEC?C_ORANGE:
    				  (u[1][0]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(u10);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(u10);
    		g.setColor(u[1][1]==REDC?Color.RED:
    				  (u[1][1]==BLUEC?Color.BLUE:
    				  (u[1][1]==GREENC?C_GREEN:
    				  (u[1][1]==ORANGEC?C_ORANGE:
    				  (u[1][1]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(u11);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(u11);
    		g.setColor(u[1][2]==REDC?Color.RED:
    				  (u[1][2]==BLUEC?Color.BLUE:
    				  (u[1][2]==GREENC?C_GREEN:
    				  (u[1][2]==ORANGEC?C_ORANGE:
    				  (u[1][2]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(u12);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(u12);
    		g.setColor(u[2][0]==REDC?Color.RED:
    				  (u[2][0]==BLUEC?Color.BLUE:
    				  (u[2][0]==GREENC?C_GREEN:
    				  (u[2][0]==ORANGEC?C_ORANGE:
    				  (u[2][0]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(u20);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(u20);
    		g.setColor(u[2][1]==REDC?Color.RED:
    				  (u[2][1]==BLUEC?Color.BLUE:
    				  (u[2][1]==GREENC?C_GREEN:
    				  (u[2][1]==ORANGEC?C_ORANGE:
    				  (u[2][1]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(u21);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(u21);
    		g.setColor(u[2][2]==REDC?Color.RED:
    				  (u[2][2]==BLUEC?Color.BLUE:
    				  (u[2][2]==GREENC?C_GREEN:
    				  (u[2][2]==ORANGEC?C_ORANGE:
    				  (u[2][2]==YELLOWC? Color.YELLOW: Color.WHITE)))));
    		g.fillPolygon(u22);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(u22);
    	}
    }

    class StartAction implements ActionListener {
    	public void actionPerformed(ActionEvent event)
    	{
    		display.setContentPane(new CubeDisplay());
    		try{Thread.sleep(1000);}catch(Exception e){}
    		display.setVisible(true);
    		repaint();
    		timer.start();
    	}
    }

    class TurnAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(event.getSource()==l_Button)
				turn("L");
			else if(event.getSource()==li_Button)
				turn("L'");
			else if(event.getSource()==r_Button)
				turn("R");
			else if(event.getSource()==ri_Button)
				turn("R'");
			else if(event.getSource()==f_Button)
				turn("F");
			else if(event.getSource()==fi_Button)
				turn("F'");
			else if(event.getSource()==b_Button)
				turn("B");
			else if(event.getSource()==bi_Button)
				turn("B'");
			else if(event.getSource()==u_Button)
				turn("U");
			else if(event.getSource()==ui_Button)
				turn("U'");
			else if(event.getSource()==d_Button)
				turn("D");
			else turn("D'");
			moveCounter++;
			checkForWin();
			repaint();
	}}
	class RotateAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(event.getSource()==rotate_left)
				rotateLeft();
			else if(event.getSource()==rotate_right)
				rotateRight();
			else if(event.getSource()==rotate_up)
				rotateUp();
			else rotateDown();
			repaint();
	}}

	class ScoreAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String data = "";
			for(int x=0;x<10;x++) data+=(((x+1)<10?" "+(x+1):x+1)+")   "+(x<scoreboard.size()?scoreboard.get(x):"")+"\n");
			JOptionPane.showMessageDialog(null,data,"Scoreboard",3);
			repaint();
	}
	}

	class AboutAction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			JOptionPane.showMessageDialog(display,"Java Rubik's Cube\nVersion: 0.3.3\nMade by: Brandon Sheets © 2016","About",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	class ExitPgrmAction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(JOptionPane.showConfirmDialog(null,"Are you sure you want to quit?","Confirm Action",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0)
			{
				System.exit(0);
			}
		}
	}

	class HelpAction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			timer.stop();
			JOptionPane.showMessageDialog(display,
			"             Java Rubik's Cube Help:\n\n"

			+"Your objective is to solve the Rubik's Cube\n"
			+"by making each side a single different color\n\n"

			+"The Cube shown displays 3 sides: Front, Left\n"
			+"and Up. the face on the right is the Front.\n\n"

			+"You can rotate each face of the cube with\n"
			+"the buttons on the bottom labeled: \"L, Li,\n"
			+"U, Ui, F, Fi, B, Bi, D, Di, R, and Ri.\"\n\n"

			+"There are two versions of each button, one\n"
			+"with the face name,(L) and one with the face\n"
			+"name+\"i\",(Li). The normal face button rotates\n"
			+"the face in a clockwise direction, and the\n"
			+"other button rotates the face in a\n"
			+"counter-clockwise, inverse, direction\n\n"

			+"The rotate face rotates the whole cube Left,\n"
			+"Right, Up, and Down. When you rotate the face,\n"
			+"the face that is displayed on the right is the\n"
			+"new front face.\n\n"
			+"                       Good Luck!!\n",
			"Help",JOptionPane.INFORMATION_MESSAGE);
			timer.start();
		}
	}

	class ExitGameAction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			timer.stop();
			int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to quit to the Main Menu?","Confirm Action",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(result==0)
			{
				time=0;
				display.menu();
				restart();
			}
			else
			{
				timer.start();
			}
		}
	}

    }
}

class ScoreEntry implements Comparable{
private String entryName="";
private String entryTime="";
private int entryMoves=0;
    public ScoreEntry(String name, String time, int moves) {

    	entryName =name;
    	entryTime =time;
    	entryMoves=moves;
    }

    private int getMoves() {return entryMoves;}


	public int compareTo(Object entry)
	{
		int min=Integer.valueOf(this.entryTime.substring(0,2));int eMin=Integer.valueOf(((ScoreEntry)entry).entryTime.substring(0,2));
		int sec=Integer.valueOf(this.entryTime.substring(3,5));int eSec=Integer.valueOf(((ScoreEntry)entry).entryTime.substring(3,5));
		if(min<eMin)
			return -1;
		else if(min==eMin)
		{
			if(sec<eSec)
				return -1;
			else if(sec==eSec)
			{
				if(entryMoves<((ScoreEntry)entry).getMoves())
					return -1;
				else
					return 1;
			}
			else
				return 1;
		}

		return 1;
	}

	public String toString()
	{
		return "["+entryName+","+entryTime+","+entryMoves+"]";
	}

}
