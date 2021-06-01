package javaapplication7;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Adrian
 */ 
class Board{
    byte[][] board = new byte[6][7];
    
    public Board(){
        board = new byte[][]{
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},    
        };
    } 
    
    public boolean isLegalMove(int column){
        return board[0][column]==0;
    }
    
    //Umieszcza ruch na planszy
    public boolean placeMove(int column, int player){ 
        if(!isLegalMove(column)) {System.out.println("Illegal move!"); return false;}
        for(int i=5;i>=0;--i){
            if(board[i][column] == 0) {
                board[i][column] = (byte)player;
                return true;
            }
        }
        return false;
    }
    
    public void undoMove(int column){
        for(int i=0;i<=5;++i){
            if(board[i][column] != 0) {
                board[i][column] = 0;
                break;
            }
        }        
    }
  
}
public class czworki {

    public Board b;
    private int nextMoveLocationAI=-1;
    private int nextMoveLocationPlayer=-1;
    private int maxDepth = 5;
    static public MouseTestPanel pan;
        
        
        
        
    public static void main(String[] args) throws InterruptedException {
        do
        {
        Board b = new Board();
        czworki ai = new czworki(b); 
        pan.nowa_gra=false;
        pan.b=b;
        ai.pan=pan;
        ai.playAgainstAIConsole();
        }
        while(true);
    }
     
       public czworki(Board b){
        this.b = b;
     
    } 
        
           //Ruch przeciwnika
    
    
    public void ruchOp1() throws InterruptedException{
        
        while(pan.wybcol<0 || pan.wybcol > 6 || !b.isLegalMove(pan.wybcol)){
            pan.blok=false;
            TimeUnit.MILLISECONDS.sleep(500);
           if(pan.nowa_gra)return;
           if(pan.blok2)
           {
               pan.blok=true;
               pan.blok2=false;
             b.placeMove(getPlayerMove(), (byte)2); 
             return;
           }
        }
        b.placeMove(pan.wybcol, (byte)2); 
        pan.wybcol=8;
       
    }
    
    public int gameResult(Board b){
        int aiScore = 0, humanScore = 0;
        for(int i=5;i>=0;--i){
            for(int j=0;j<=6;++j){
                if(b.board[i][j]==0) continue;
                
                //Sprawdzanie w poziomie
                if(j<=3){
                    for(int k=0;k<4;++k){ 
                            if(b.board[i][j+k]==1) aiScore++;
                            else if(b.board[i][j+k]==2) humanScore++;
                            else break; 
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                } 
                
                //Sprawdzanie w pionie
                if(i>=3){
                    for(int k=0;k<4;++k){
                            if(b.board[i-k][j]==1) aiScore++;
                            else if(b.board[i-k][j]==2) humanScore++;
                            else break;
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                } 
                
                //Sprawdzanie po ukosie
                if(j<=3 && i>= 3){
                    for(int k=0;k<4;++k){
                        if(b.board[i-k][j+k]==1) aiScore++;
                        else if(b.board[i-k][j+k]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                }
                
                //Sprawdzanie po ukosie
                if(j>=3 && i>=3){
                    for(int k=0;k<4;++k){
                        if(b.board[i-k][j-k]==1) aiScore++;
                        else if(b.board[i-k][j-k]==2) humanScore++;
                        else break;
                    } 
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                }  
            }
        }
        
        for(int j=0;j<7;++j){
            if(b.board[0][j]==0)return -1;
        }
        //Jeżeli remis to
        return 0;
    }
    
    int calculateScore(int aiScore, int moreMoves){   
        int moveScore = 4 - moreMoves;
        if(aiScore==0)return 0;
        else if(aiScore==1)return 1*moveScore;
        else if(aiScore==2)return 10*moveScore;
        else if(aiScore==3)return 100*moveScore;
        else return 1000;
    }
    
 
    public int evaluateBoard(Board b){
      
        int aiScore=1;
        int score=0;
        int blanks = 0;
        int k=0, moreMoves=0;
        for(int i=5;i>=0;--i){
            for(int j=0;j<=6;++j){
                
                if(b.board[i][j]==0 || b.board[i][j]==2) continue; 
                
                if(j<=3){ 
                    for(k=1;k<4;++k){
                        if(b.board[i][j+k]==1)aiScore++;
                        else if(b.board[i][j+k]==2){aiScore=0;blanks = 0;break;}
                        else blanks++;
                    }
                     
                    moreMoves = 0; 
                    if(blanks>0) 
                        for(int c=1;c<4;++c){
                            int column = j+c;
                            for(int m=i; m<= 5;m++){
                             if(b.board[m][column]==0)moreMoves++;
                                else break;
                            } 
                        } 
                    
                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1;   
                    blanks = 0;
                } 
                
                if(i>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j]==1)aiScore++;
                        else if(b.board[i-k][j]==2){aiScore=0;break;} 
                    } 
                    moreMoves = 0; 
                    
                    if(aiScore>0){
                        int column = j;
                        for(int m=i-k+1; m<=i-1;m++){
                         if(b.board[m][column]==0)moreMoves++;
                            else break;
                        }  
                    }
                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1;  
                    blanks = 0;
                }
                 
                if(j>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i][j-k]==1)aiScore++;
                        else if(b.board[i][j-k]==2){aiScore=0; blanks=0;break;}
                        else blanks++;
                    }
                    moreMoves=0;
                    if(blanks>0) 
                        for(int c=1;c<4;++c){
                            int column = j- c;
                            for(int m=i; m<= 5;m++){
                             if(b.board[m][column]==0)moreMoves++;
                                else break;
                            } 
                        } 
                    
                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1; 
                    blanks = 0;
                }
                 
                if(j<=3 && i>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j+k]==1)aiScore++;
                        else if(b.board[i-k][j+k]==2){aiScore=0;blanks=0;break;}
                        else blanks++;                        
                    }
                    moreMoves=0;
                    if(blanks>0){
                        for(int c=1;c<4;++c){
                            int column = j+c, row = i-c;
                            for(int m=row;m<=5;++m){
                                if(b.board[m][column]==0)moreMoves++;
                                else if(b.board[m][column]==1);
                                else break;
                            }
                        } 
                        if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                        aiScore=1;
                        blanks = 0;
                    }
                }
                 
                if(i>=3 && j>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j-k]==1)aiScore++;
                        else if(b.board[i-k][j-k]==2){aiScore=0;blanks=0;break;}
                        else blanks++;                        
                    }
                    moreMoves=0;
                    if(blanks>0){
                        for(int c=1;c<4;++c){
                            int column = j-c, row = i-c;
                            for(int m=row;m<=5;++m){
                                if(b.board[m][column]==0)moreMoves++;
                                else if(b.board[m][column]==1);
                                else break;
                            }
                        } 
                        if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                        aiScore=1;
                        blanks = 0;
                    }
                } 
            }
        }
        return score;
    } 
    
    public int minimax(int depth, int turn){
        int gameResult = gameResult(b);
        int countScore=0,countScore2=0;
        if(gameResult==1)return Integer.MAX_VALUE;
        else if(gameResult==2)return Integer.MIN_VALUE;
        else if(gameResult==0)return 0;
        
        if(depth==maxDepth)return evaluateBoard(b);
        
        int maxScore=Integer.MIN_VALUE, minScore = Integer.MAX_VALUE;
        int tab[]=new int[7];
        int tab2[]=new int[7];
        for(int j=0;j<=6;++j){
            if(!b.isLegalMove(j)) continue;
                
            if(turn==1){
                    b.placeMove(j, 1);
                    int currentScore = minimax(depth+1, 2);
                    if(maxScore<currentScore)countScore=0;
                    maxScore = Math.max(currentScore, maxScore);
                    if(depth==0){
                        //Kalkulowanie wartości ruchu
                        System.out.println(turn+" Score for location "+j+" = "+currentScore);
                        if(maxScore==currentScore){ tab[countScore]=j;
                        countScore++;
                        }
			if(maxScore==Integer.MAX_VALUE){
			//Jeżeli ruch daje wygraną
			b.undoMove(j);break;}
                    }
            }else if(turn==2){
                    b.placeMove(j, 2);
                    int currentScore = minimax(depth+1, 1);
                    if(minScore>currentScore)countScore2=0;
                    minScore = Math.min(currentScore, minScore);
                    if(depth==0){
                        //Kalkulowanie wartości ruchu
                        System.out.println(turn+" Score for location "+j+" = "+currentScore);
                        if(minScore==currentScore){ tab2[countScore2]=j;
                        countScore2++;
                        }
                        
			if(minScore==Integer.MIN_VALUE){
			//Jeżeli ruch daje wygraną
			b.undoMove(j);break;}
                    }
            }
            b.undoMove(j);
        }
        if(turn==1&&depth==0)
        {Random rand=new Random();
         nextMoveLocationAI = tab[rand.nextInt(countScore)];
        }
        
        if(turn==2&&depth==0)
        {Random rand=new Random();
         nextMoveLocationPlayer = tab2[rand.nextInt(countScore2)];
        }
        
        return turn==1?maxScore:minScore;
    }
    
    public int getAIMove(){
        nextMoveLocationAI = -1;
        minimax(0, 1);
        return nextMoveLocationAI;
    }
    public int getPlayerMove()
    {
        nextMoveLocationPlayer = -1;
        minimax(0, 2);
        return nextMoveLocationPlayer;
        
    }
    public void playAgainstAIConsole() throws InterruptedException
    {
       
        pan.repaint();
        int answer = JOptionPane.showConfirmDialog(new JFrame(),
        "Chcesz zacząć pierwszy?",
        "An Inane Question",
        JOptionPane.YES_NO_OPTION);
        if(answer==0) ruchOp1();
        pan.repaint();
        Random rand=new Random();
        b.placeMove(rand.nextInt(7), 1);
        pan.odmal();
        
        OUTER: 
        while (true) {
            if(pan.nowa_gra) break OUTER;
            ruchOp1();
            
            int gameResult = gameResult(b);
            switch (gameResult) {
                case 1:
                    pan.odmal(); 
                    System.out.println("AI Wins!");
                    JOptionPane.showMessageDialog(new JFrame(), "Przegrana");
                    break OUTER;
                case 2:
                    pan.odmal(); 
                    System.out.println("You Win!");
                    JOptionPane.showMessageDialog(new JFrame(), "Wygrana");
                    break OUTER;
                case 0:
                    pan.odmal(); 
                    System.out.println("Draw!");
                    JOptionPane.showMessageDialog(new JFrame(), "Remis");
                    break OUTER;
                default:
                    break;
            }
            b.placeMove(getAIMove(), 1);
            pan.odmal();
            gameResult = gameResult(b);
             switch (gameResult) {
                case 1:
                    pan.odmal(); 
                    System.out.println("Przegrana!");
                    JOptionPane.showMessageDialog(new JFrame(), "Przegrana");
                    break OUTER;
                case 2:
                    pan.odmal(); 
                    System.out.println("Wygrana!");
                    JOptionPane.showMessageDialog(new JFrame(), "Wygrana");
                    break OUTER;
                case 0:
                    pan.odmal(); 
                    System.out.println("Remis!");
                    JOptionPane.showMessageDialog(new JFrame(), "Remis");
                    break OUTER;
                default:
                    break;
            }
        }
       pan.odmal(); 
    }
}
