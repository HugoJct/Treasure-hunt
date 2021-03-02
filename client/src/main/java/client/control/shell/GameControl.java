package client.control.shell;

import java.util.Scanner;

public class GameControl {
    private Scanner _input;

    public ControlShell() {
        this._input = new Scanner(System.in);
    }

    public void reader() {
        String com;
        com = this._input.nextLine();
    }
}
