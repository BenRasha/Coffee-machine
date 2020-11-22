package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private int waterMachineHas;
    private int milkMachineHas;
    private int beansMachineHas;
    private int cupsMachineHas;
    private int moneyMachineHas;
    private String input;
    private StateOfTheMachine currentState = StateOfTheMachine.READY;

    CoffeeMachine(int waterMachineHas, int milkMachineHas, int beansMachineHas, int cupsMachineHas, int moneyMachineHas) {
        this.waterMachineHas = waterMachineHas;
        this.milkMachineHas = milkMachineHas;
        this.beansMachineHas = beansMachineHas;
        this.cupsMachineHas = cupsMachineHas;
        this.moneyMachineHas = moneyMachineHas;
    }

    StateOfTheMachine getCurrentState() {
        return currentState;
    }

    void start() {
        ready();
    }

    void stop() {
        this.currentState = StateOfTheMachine.OFF;
    }

    private void ready() {
        this.currentState = StateOfTheMachine.READY;
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    void processInput(String input) {
        this.input = input;
        switch (currentState) {
            case READY:
                processReadyCommand();
                break;
            case CHOOSING_COFFEE:
                buy();
                break;
            case WATER_FILLING:
            case MILK_FILLING:
            case BEANS_FILLING:
            case CUPS_FILLING:
                fill();
                break;
            default:
                System.out.println("Unknown input state");
                ready();
                break;
        }
    }

    private void processReadyCommand() {
        switch (input) {
            case "buy":
                buy();
                break;
            case "fill":
                fill();
                break;
            case "take":
                take();
                break;
            case "remaining":
                printRemaining();
                break;
            case "exit":
                stop();
                break;
            default:
                System.out.println("Unknown command");
                break;
        }
    }

    private void buy() {
        switch (this.currentState) {
            case READY:
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                this.currentState = StateOfTheMachine.CHOOSING_COFFEE;
                break;
            case CHOOSING_COFFEE:
                boolean enough=isEnough(this.input);

                switch (this.input) {
                    case "1":
                        if (enough) {
                            this.waterMachineHas-=250;
                            this.beansMachineHas-=16;
                            this.cupsMachineHas-=1;
                            this.moneyMachineHas+=4;
                        }
                        break;
                    case "2":
                        if (enough) {
                            this.waterMachineHas-=350;
                            this.milkMachineHas-=75;
                            this.beansMachineHas-=20;
                            this.cupsMachineHas-=1;
                            this.moneyMachineHas+=7;
                        }
                        break;
                    case "3":
                        if (enough){
                            this.waterMachineHas-=200;
                            this.milkMachineHas-=100;
                            this.beansMachineHas-=12;
                            this.cupsMachineHas-=1;
                            this.moneyMachineHas+=6;
                        }
                        break;
                    case "back":
                        break;
                    default:
                        System.out.println("Unknown buy command");
                        break;
                }
                ready();
                break;
            default:
                System.out.println("Unknown buy state");
                ready();
                break;
        }
    }

    private boolean isEnough(String type){
        boolean enough=false;

        int waterLimit;
        int milkLimit;
        int beansLimit;

        switch (type) {
            case "1":
                waterLimit=250;
                milkLimit=0;
                beansLimit=16;
                break;
            case "2":
                waterLimit=350;
                milkLimit=75;
                beansLimit=20;
                break;
            case "3":
                waterLimit=200;
                milkLimit=100;
                beansLimit=12;
                break;
            default:
                return false;
        }
        if(this.waterMachineHas<waterLimit) {
            System.out.println("Sorry, not enough water!");
        } else if (this.milkMachineHas<milkLimit) {
            System.out.println("Sorry, not enough milk!");
        } else if (this.beansMachineHas<beansLimit) {
            System.out.println("Sorry, not enough coffee beans! ");
        } else if (this.cupsMachineHas<1) {
            System.out.println("Sorry, not enough disposable cups!");
        }else {
            enough=true;
            System.out.println("I have enough resources, making you a coffee!");
        }
        return enough;
    }

    private void fill() {
        switch (this.currentState) {
            case READY:
                System.out.println("Write how many ml of water do you want to add: ");
                this.currentState=StateOfTheMachine.WATER_FILLING;
                break;
            case WATER_FILLING:
                this.waterMachineHas+=Integer.parseInt(this.input);
                System.out.println("Write how many ml of milk do you want to add: ");
                this.currentState=StateOfTheMachine.MILK_FILLING;
                break;
            case MILK_FILLING:
                this.milkMachineHas+=Integer.parseInt(this.input);
                System.out.println("Write how many grams of coffee beans do you want to add: ");
                this.currentState=StateOfTheMachine.BEANS_FILLING;
                break;
            case BEANS_FILLING:
                this.beansMachineHas+=Integer.parseInt(this.input);
                System.out.println("Write how many disposable cups of coffee do you want to add: ");
                this.currentState=StateOfTheMachine.CUPS_FILLING;
                break;
            case CUPS_FILLING:
                this.cupsMachineHas+=Integer.parseInt(this.input);
                ready();
                break;
            default:
                System.out.println("Unknown fill state");
                ready();
                break;
        }
    }
    
    private void take() {
        System.out.println("I gave you $"+this.moneyMachineHas);
        this.moneyMachineHas=0;
        ready();
    }
    
    private void printRemaining() {
        System.out.println("The coffee machine has:");
        System.out.println(this.waterMachineHas+" of water");
        System.out.println(this.milkMachineHas+" of milk");
        System.out.println(this.beansMachineHas+" of coffee beans");
        System.out.println(this.cupsMachineHas+" of disposable cups");
        System.out.println("$"+this.moneyMachineHas+" of money");
        ready();
    }
}