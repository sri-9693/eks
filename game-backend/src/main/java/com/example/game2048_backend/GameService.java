package com.example.game2048_backend;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class GameService {

    private int[][] board = new int[4][4];
    private final Random random = new Random();

    public int[][] initBoard() {
        board = new int[4][4];
        addRandomTile();
        addRandomTile();
        return board;
    }

    public int[][] move(String direction) {
        boolean moved = false;

        switch (direction) {
            case "up":
                moved = moveUp();
                break;
            case "down":
                moved = moveDown();
                break;
            case "left":
                moved = moveLeft();
                break;
            case "right":
                moved = moveRight();
                break;
        }

        if (moved) {
            addRandomTile();
        }

        return board;
    }

    private void addRandomTile() {
        List<int[]> empty = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (board[i][j] == 0)
                    empty.add(new int[]{i, j});

        if (!empty.isEmpty()) {
            int[] pos = empty.get(random.nextInt(empty.size()));
            board[pos[0]][pos[1]] = random.nextInt(10) < 9 ? 2 : 4;
        }
    }

    // Movement Helpers
    private boolean moveLeft() {
        boolean moved = false;
        for (int i = 0; i < 4; i++) {
            int[] newRow = mergeRow(board[i]);
            if (!Arrays.equals(board[i], newRow)) {
                moved = true;
                board[i] = newRow;
            }
        }
        return moved;
    }

    private boolean moveRight() {
        boolean moved = false;
        for (int i = 0; i < 4; i++) {
            int[] reversed = reverse(board[i]);
            int[] merged = mergeRow(reversed);
            int[] restored = reverse(merged);
            if (!Arrays.equals(board[i], restored)) {
                moved = true;
                board[i] = restored;
            }
        }
        return moved;
    }

    private boolean moveUp() {
        boolean moved = false;
        for (int col = 0; col < 4; col++) {
            int[] column = new int[4];
            for (int row = 0; row < 4; row++) {
                column[row] = board[row][col];
            }
            int[] merged = mergeRow(column);
            for (int row = 0; row < 4; row++) {
                if (board[row][col] != merged[row]) {
                    moved = true;
                }
                board[row][col] = merged[row];
            }
        }
        return moved;
    }

    private boolean moveDown() {
        boolean moved = false;
        for (int col = 0; col < 4; col++) {
            int[] column = new int[4];
            for (int row = 0; row < 4; row++) {
                column[row] = board[row][col];
            }
            int[] merged = reverse(mergeRow(reverse(column)));
            for (int row = 0; row < 4; row++) {
                if (board[row][col] != merged[row]) {
                    moved = true;
                }
                board[row][col] = merged[row];
            }
        }
        return moved;
    }

    // Merge logic: e.g. [2,2,4,0] → [4,4,0,0]
    private int[] mergeRow(int[] row) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int num : row) {
            if (num != 0) list.add(num);
        }

        LinkedList<Integer> merged = new LinkedList<>();
        while (!list.isEmpty()) {
            if (list.size() > 1 && Objects.equals(list.get(0), list.get(1))) {
                merged.add(list.removeFirst() * 2);
                list.removeFirst();
            } else {
                merged.add(list.removeFirst());
            }
        }

        while (merged.size() < 4) merged.add(0);
        return merged.stream().mapToInt(i -> i).toArray();
    }

    private int[] reverse(int[] row) {
        int[] reversed = new int[row.length];
        for (int i = 0; i < row.length; i++) {
            reversed[i] = row[row.length - i - 1];
        }
        return reversed;
    }
}
