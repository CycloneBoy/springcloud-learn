package com.cycloneboy.springcloud.common.command;

/**
 * Create by  sl on 2019-08-11 19:44
 */
public class Client {

  public static void main(String[] args) {
    Receiver receiver = new Receiver();
    Command command = new ConcreteCommand(receiver);

    Invoker invoker = new Invoker();
    invoker.setCommand(command);
    invoker.action();

  }
}
