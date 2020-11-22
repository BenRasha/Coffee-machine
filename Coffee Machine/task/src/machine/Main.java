package machine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        CoffeeMachine machine=new CoffeeMachine(400,540,120,9,550);
        machine.start();
        while (machine.getCurrentState()!=StateOfTheMachine.OFF) {
            machine.processInput(scanner.next());
        }
    }
}
