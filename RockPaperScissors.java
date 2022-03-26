import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

@SuppressWarnings("unused")
public class RockPaperScissors extends Applet implements ActionListener {

    Button buttonRock;
    Button buttonPaper;
    Button buttonScissors;
    Button buttonReset;
    int count = 0;
    int count2 = 0;
    int count3 = 0;

    int pre = 0;
    
    int gamesWon = 0;
    int gamesLost = 0;
    int gamesTied = 0;
    
    int posX = -100;
    int posY = -100;

    int gameFinishedFull = 0;
    int highscore = 0;

    boolean wonGame = true;
    boolean gameFinishedWon = false;
    boolean gameFinishedLost = false;
    boolean gameFinishedTied = false;
    
    int gameFinished = 0; // 0 = reset, 1 = won game, 2 = tied game, 3 = lost game
    
    Font f1;
    Font f2;

    String ans = "Waiting...";
    String playerAns = "Waiting...";

    public void init() {
        buttonRock = new Button("Rock");
        add(buttonRock);
        buttonRock.addActionListener(this);

        buttonPaper = new Button("Paper");
        add(buttonPaper);
        buttonPaper.addActionListener(this);

        buttonScissors = new Button("Scissors");
        add(buttonScissors);
        buttonScissors.addActionListener(this);
        
        buttonReset = new Button("Reset Score");
        add(buttonReset);
        buttonReset.addActionListener(this);
        
        f1 = new Font("Arial", Font.BOLD, 18);
        f2 = new Font("Arial", Font.PLAIN, 18);
        
        resize(750, 750);
    }

    public void paint(Graphics g) {
        // Get current highscore before painting anything
        highscore = getHighscore();

        // Button Background
        g.setColor(new Color(36,37,38));
        g.fillRect(25, 25, 200, 425);

        buttonRock.setLocation(50,50);
        buttonRock.setSize(150,60);
        // g.drawString("Button clicked: " + count,50,100);

        buttonPaper.setLocation(50,150);
        buttonPaper.setSize(150,60);
        // g.drawString("Button clicked: " + count2,50,200);

        buttonScissors.setLocation(50,250);
        buttonScissors.setSize(150,60);
        // g.drawString("Button clicked: " + count3,50,300);
        
        buttonReset.setLocation(50,350);
        buttonReset.setSize(150, 60);

        g.drawString("Computer chose: " + ans, 350,350);
        g.drawString("Player Chose: " + playerAns, 350, 400);
        
        g.drawString("Rounds Won (Player): " + gamesWon, 350, 450);
        g.drawString("Rounds Won (Computer): " + gamesLost, 350, 500);
        g.drawString("Rounds Tied: " + gamesTied, 350, 550);
        g.drawString("Games Won: " + gameFinishedFull, 350, 600);
        
        g.drawString("Is game finished?: " + gameFinishedWon, 350, 650);

        g.drawString("Highscore: " + highscore, 350, 700);
        
        g.setFont(f2);
        g.drawString("Games Won: " + gamesWon, 325, 80);
        
        drawSelected(g, posX, posY);

        if (gameFinishedWon) {
            winSprite(g);
        }
        else if (gameFinishedLost) {
            loseSprite(g);
        }

    }

    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == buttonRock) {
            playerAns = "Rock";
            posX = 37;
            posY = 37;
        }
        else if (e.getSource() == buttonPaper) {
            playerAns = "Paper";
            posX = 37;
            posY = 150-13;
        }
        else if (e.getSource() == buttonScissors) {
            playerAns = "Scissors";
            posX = 37;
            posY = 250-13;
        }
        else if (e.getSource() == buttonReset) {
            playerAns = "Reset scores!";
            posX = 37;
            posY = 350-13;
        }
        else {
            playerAns = "Button not assigned";
        }

        
        wonGame = true;
        if (e.getSource() == buttonReset) {
            gamesWon = 0;
            gamesLost = -1;
            gamesTied = 0;
            if (gameFinishedFull > highscore) {
                try {
                    FileWriter writer = new FileWriter("C:\\Users\\chase\\OneDrive\\Documents\\School Work\\Real Comp Science\\Workspace\\HiScore.txt", false);
                    writer.write("" + gameFinishedFull);
                    writer.write("\r\n");
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }


            gameFinishedFull = 0;
            
            gameFinishedWon = false;
            gameFinishedLost = false;
            gameFinishedTied = false;
            
            gameFinished = 0;

            repaint();
        }
        else {
            ans = computerDecision();
            switch(ans) {
                

                case "Rock": if (e.getSource() == buttonRock) {
                                gameFinished = 2;
                            }
                            else if (e.getSource() == buttonPaper) { 
                                gameFinished = 1;
                            }
                            else {
                                gameFinished = 3;
                            }
                            break;

                case "Paper": if (e.getSource() == buttonPaper) {
                                gameFinished = 2;
                             }
                             else if (e.getSource() == buttonScissors) {
                                 gameFinished = 1;
                             }
                             else {
                                 gameFinished = 3;
                             }
                             break;

                case "Scissors": if (e.getSource() == buttonScissors) { 
                                    gameFinished = 2;
                                }
                                else if (e.getSource() == buttonRock) {
                                    gameFinished = 1;
                                }
                                else {
                                    gameFinished = 3;
                                }
                                break;
                
                default: wonGame = false; break;

            }
        }
        
        if (gameFinished == 1) {
            gamesWon++;
        }
        else if (gameFinished == 2) {
            gamesTied++;
        }
        else {
            gamesLost++;
        }
        
        if (gamesWon == 5) {
            gameFinishedWon = true;

            gameFinishedFull++;


        }
        else if (gamesLost == 5) {
            if (gameFinishedFull > highscore) { 
                try {
                    FileWriter writer = new FileWriter("C:\\Users\\chase\\OneDrive\\Documents\\School Work\\Real Comp Science\\Workspace\\HiScore.txt", false);
                    writer.write("" + gameFinishedFull);
                    writer.write("\r\n");
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            gameFinishedFull =0;

            gameFinishedLost = true;
        }
        // else { gameFinishedWon = false; gameFinishedLost = false; }
        
        repaint();
    }

    public String computerDecision() {
        pre = getRand(1,4);
        String dec = "Waiting...";

        switch(pre) {

            case 1: dec = "Rock";
            break;

            case 2: dec = "Paper";
            break;

            case 3: dec = "Scissors";
            break;

        }

        return dec;

    }
    
    public void winSprite(Graphics g) {
    
        g.setColor(Color.yellow);
        
        g.fillRect(750/4, 750/4, 750/2, 750/2);
        
        g.setColor(Color.black);
        g.setFont(f1);
        g.drawString("You Win!", 750/2-50, 750/2);

        sleep(1500);

        gamesWon = 0;
        gamesLost = 0;
        gamesTied = 0;
        gameFinishedWon = false;
        gameFinishedLost = false;

        repaint();
        
    }
    
    public void loseSprite(Graphics g) {
        g.setColor(Color.red);
        
        g.fillRect(750/4, 750/4, 750/2, 750/2);
        
        g.setColor(Color.black);
        g.setFont(f1);
        g.drawString("You Lose!", 750/2-50, 750/2);

        sleep(1500);

        gamesWon = 0;
        gamesLost = 0;
        gamesTied = 0;
        gameFinishedWon = false;
        gameFinishedLost = false;

        repaint();
    }
    
    public void drawArrow(Graphics g, int posX, int posY) {
        g.setColor(Color.black);
        g.drawLine(posX, posY, posX+20, posY);
        g.drawLine(posX, posY, posX+5, posY-5);
        g.drawLine(posX, posY, posX+5, posY+5);
    }
    
    public void drawSelected(Graphics g, int posX, int posY) {
    
        g.setColor(Color.yellow);
        g.fillRect(posX, posY, 176, 86);
        
    }

    public int getRand(int min, int max) {
        int range = max - min;
        return (int)(Math.random()*range)+min;

    }

    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException exc) {
            exc.printStackTrace();
        }
    }

    public int getHighscore() {

        File file = new File("C:\\Users\\chase\\OneDrive\\Documents\\School Work\\Real Comp Science\\Workspace\\HiScore.txt");

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(scanner.hasNextInt()) {
            highscore = scanner.nextInt();
        }

        return highscore;
    }

}