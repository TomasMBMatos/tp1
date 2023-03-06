package pack1Revisoes;

import java.util.Scanner;

public class P02FourInaRow {
    /**
     * Shows (prints) the board on the console
     *
     * @param board The board
     */
    private static void showboard(char[][] board) {
        System.out.println("+---------------+");
        for(int i = 0; i < board[i].length; i++) {
            System.out.print("|");
            // Making 1 row
            for (char[] col : board) {
                if (col[i] == 0) {
                    System.out.print(" ");
                    System.out.print("O");
                } else {
                    System.out.print(" ");
                    System.out.printf("%s", col[i]);
                }
            }
            System.out.print(" ");
            System.out.println("|");
        }

        System.out.println("+---------------+");
    }

    /**
     * Puts one piece for the received player. First asks the user to choose one
     * column, then validates it and repeat it until a valid column is chosen.
     * Finally, puts the player character on top of selected column.
     *
     * @param player   The player: 'A' or 'B'. Put this character on the board
     * @param board    The board
     * @param keyboard The keyboard Scanner
     * @return The column selected by the user.
     */
    private static int play(char player, char[][] board, Scanner keyboard) {
        boolean played = false;
        System.out.println("Choose a column (Player "+ player + ")");
        int col = Integer.parseInt(keyboard.nextLine());
        if(col > board.length) play(player, board, keyboard);
        for(int i = board[col-1].length-1; i >= 0; i--) {
            if(board[col-1][i] == 0) {
                board[col-1][i] = player;
                played = true;
                break;
            }
        }
        if(!played) {
            play(player, board, keyboard);
        }
        lastPlayerWon(board, col-1);
        return 0;
    }

    /**
     * Checks if the player, with the character on top on the received column, won
     * the game or not. It will get the top move on that column, and check if there
     * are 4 pieces in a row, in relation to that piece and from the same player.
     * Returns true is yes, false is not.
     *
     * @param board The board
     * @param col   The last played column
     * @return True is that player won the game, or false if not.
     */
    private static boolean lastPlayerWon(char[][] board, int col) {
        int row = board[col].length-1;
        while(board[col][row] == 0) {
            row--;
        }
        for(int i = 0; i<=4;i++) {
            if(checkAll(board, col, row, i)) return true;
        }
        return false;
    }

    private static boolean checkAll(char[][] board, int col, int row, int n) {
        switch(n) {
            // left
            case 0:
                if(check(board, col, row, -1, 0)) return true;
            // right
            case 1:
                if(check(board, col, row, 1, 0)) return true;
            // down
            case 2:
                if(check(board, col, row, 0, -1)) return true;
            // left diagonal
            case 3:
                if(check(board, col, row, -1, -1)) return true;
            // right diagonal
            case 4:
                if(check(board, col, row, 1, -1)) return true;

        }
        return false;
    }

    private static boolean check(char[][] board, int col, int row, int i, int j) {
        char player = board[col][row];
        int count = 0;
        for(int k = 0; k < 4; k++) {
            int col2 = col+i*k;
            int row2 = row+j*k;
            if(col2 < board.length && col2 >= 0  && row2 < board[col2].length && row2 >= 0) {
                if(board[col+i*k][row+j*k] == player) count++;
            }
        }
        return count == 4;
    }

    /**
     * Check if there are at least one free position on board.
     *
     * @param board The board
     * @return True if there is, at least, one free position on board
     */
    private static boolean existsFreePlaces(char[][] board) {
        for(int i=0; i<board[i].length; i++) {
            for(int j=0; j<board.length; j++) {
                if(board[j][i] == 0) return true;
            }
        }
        return false;
    }

    /**
     * Main method - this method should not be changed
     */
    public static void main(String[] args) {
        final int NCOLs = 7;
        final int NROWS = 6;

        // program variables
        Scanner keyboard = new Scanner(System.in);
        char[][] board = new char[NCOLs][NROWS];
        char winner = ' ';

        // show empty board
        showboard(board);

        // game cycle
        do {
            int col = play('A', board, keyboard);
            showboard(board);
            if (lastPlayerWon(board, col)) {
                winner = 'A';
                break;
            }
            if (!existsFreePlaces(board))
                break;

            col = play('B', board, keyboard);
            showboard(board);
            if (lastPlayerWon(board, col)) {
                winner = 'B';
                break;
            }

        } while (existsFreePlaces(board));

        // show final result
        switch (winner) {
            case ' ':
                System.out.println("We have a draw....");
                break;
            case 'A':
                System.out.println("Winner: Player A. Congratulations...");
                break;
            case 'B':
                System.out.println("Winner: Player B. Congratulations...");
                break;
        }

        // close keyboard
        keyboard.close();
    }
}
