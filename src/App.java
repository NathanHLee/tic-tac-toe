import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Objects;
import java.util.Scanner;


public class App {
    // Current spaces taken by player
    static ArrayList<Integer> player1Positions = new ArrayList<Integer>();
    static ArrayList<Integer> player2Positions = new ArrayList<Integer>();

    public static void main(String[] args) throws Exception
    {
        // Allow the player to play against a bot if they don't have a real player
        boolean is_computer = false;

        // Create player names
        String player1 = createPlayer(1);
        System.out.println();
        String player2 = createPlayer(2);
        System.out.println();
        System.out.println("Player 1 is: " + player1);
        // Check if the player wants to play with the computer
        if (Objects.equals(player2, new String("Com")))
        {
            player2 = "Computer-Player";
            System.out.println("Player 2 is: " + player2);
            is_computer = true;
        }
        else
        {
            System.out.println("Player 2 is: " + player2);
        }
        System.out.println();

        // Create the base board for the game
        char[][] board = 
        {{'1', '|', '2', '|', '3'},
         {'-', '+', '-', '+', '-'},
         {'4', '|', '5', '|', '6'},
         {'-', '+', '-', '+', '-'},
         {'7', '|', '8', '|', '9'}};

        // A bit of handling, board shows up differently with a computer
        if(!is_computer)
        {
            gameBoard(board);
            System.out.println();
        }
        // Run the game after information is collected
        while(true)
        {
            if(is_computer)
            {
                gameBoard(board);
                System.out.println();
            }

            // Player 1's marker placement
            Scanner input1 = new Scanner(System.in);
            System.out.print("Place your marker " + player1 + " (1-9): ");
            int player1Loc = input1.nextInt();
            System.out.println();

            // Check if that position is legal
            while(player1Positions.contains(player1Loc) || player2Positions.contains(player1Loc))
            {
                System.out.println("Position taken! Pick another spot with a number.");
                player1Loc = input1.nextInt();
            }

            // Apply the marker in the position
            playerTurn(board, player1Loc, 1, false);
            System.out.println();

            // Check if player 1 has any of the following win conditions
            String result1 = isGameWon(player1, 1);
            if(result1.length() > 1)
            {
                System.out.println();
                System.out.println(result1);
                System.out.println();
                break;
            }
            
            // If the user is playing against the computer
            if(is_computer)
            {
                // Computer's placement is random
                Random rand = new Random();
                int cpuLoc = rand.nextInt(9) + 1;
                // Check if the placement is legal
                while(player1Positions.contains(cpuLoc) || player2Positions.contains(cpuLoc))
                {
                    cpuLoc = rand.nextInt(9) + 1;
                }

                // Apply the marker in the position
                playerTurn(board, cpuLoc, 2, true);
                System.out.println();

                // Check if the computer has any of the following win conditions
                String result2 = isGameWon(player2, 2);
                if(result2.length() > 1)
                {
                    playerTurn(board, cpuLoc, 2, false);
                    System.out.println();
                    System.out.println(result2);
                    System.out.println();
                    break;
                }
            }
            // If the user is playing against a real player
            else
            {
                // Player 2's marker placement
                Scanner input2 = new Scanner(System.in);
                System.out.print("Place your maker " + player2 + " (1-9): ");
                int player2Loc = input2.nextInt();
                System.out.println();
                
                // Check if that placement is legal
                while(player1Positions.contains(player2Loc) || player2Positions.contains(player2Loc))
                {
                    System.out.println("Position taken! Pick another spot with a number.");
                    player2Loc = input1.nextInt();
                }

                // Apply the marker in that position
                playerTurn(board, player2Loc, 2, false);
                System.out.println();

                // Check if player 2 has any of the following win conditions
                String result2 = isGameWon(player2, 2);
                if(result2.length() > 1)
                {
                    System.out.println();
                    System.out.println(result2);
                    System.out.println();
                    break;
                }
            }
        }
    }

    // Print out the board
    public static void gameBoard(char[][] board)
    {
         for(int i = 0; i < 5; i++)
         {
             for(int j = 0; j < 5; j++)
             {
                System.out.print(board[i][j]);
             }
             System.out.println();
         }
    }

    // Create a player name
    public static String createPlayer(int playerValue)
    {
        // Take names for the players
        Scanner name = new Scanner(System.in);
        if(playerValue == 1)
        {
            System.out.print("Enter Name For Player 1: ");
            return name.nextLine();
        }
        if(playerValue == 2)
            System.out.print("Enter Name For Player 2 (Enter 'Com' for computer player): ");
            return name.nextLine();
    }

    // Update the board with the player's corresponding marker
    public static void playerTurn(char[][] board, int loc, int player, boolean is_computer)
    {
        char symbol = ' ';

        if(player == 1)
        {
            symbol = 'X';
            player1Positions.add(loc);
        }
        else if(player == 2)
        {
            symbol = 'O';
            player2Positions.add(loc);
        }

        // Place a marker wherever the user selected
        switch(loc)
        {
            case 1:
                board[0][0] = symbol;
                break;
            case 2:
                board[0][2] = symbol;
                break;
            case 3:
                board[0][4] = symbol;
                break;
            case 4:
                board[2][0] = symbol;
                break;
            case 5:
                board[2][2] = symbol;
                break;
            case 6:
                board[2][4] = symbol;
                break;
            case 7:
                board[4][0] = symbol;
                break;
            case 8:
                board[4][2] = symbol;
                break;
            case 9:
                board[4][4] = symbol;
                break;
            default:
                break;
        }
        if(is_computer == false)
        {
            gameBoard(board);
        }
    }

    // Check if the game is won with the following conditions
    public static String isGameWon(String playerName, int playerID)
    {
        // Horizontal win
        List topRow = Arrays.asList(1, 2, 3);
        List middleRow = Arrays.asList(4, 5, 6);
        List bottomRow = Arrays.asList(7, 8, 9);
        // Vertical win
        List leftCol = Arrays.asList(1, 4, 7);
        List middleCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);
        // Diagnal win
        List leftDiag = Arrays.asList(1, 5, 9);
        List rightDiag = Arrays.asList(3, 5, 7);
        
        // Make a list of the win conditions to create an easier search
        List<List> winConditions = new ArrayList<List>();
        winConditions.add(topRow);
        winConditions.add(middleRow);
        winConditions.add(bottomRow);
        winConditions.add(leftCol);
        winConditions.add(middleCol);
        winConditions.add(rightCol);
        winConditions.add(leftDiag);
        winConditions.add(rightDiag);

        // If the player meets any the win conditions, display a victory
        for(List i : winConditions)
        {
            if(player1Positions.containsAll(i) && playerID == 1)
            {
                return "Congrats " + playerName + ", you have won!";
            }
            else if(player2Positions.containsAll(i) && playerID == 2)
            {
                return "Congrats " + playerName + ", you have won!";
            }
            else if(player1Positions.size() + player2Positions.size() == 9)
            {
                return "It's a tie!";
            }
        }
        return "";
    }
}