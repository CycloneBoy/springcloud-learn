package com.cycloneboy.springcloud.common.command;

/**
 * Create by  sl on 2019-08-11 19:41
 */
public class ConcreteCommand implements Command{

    private Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.action();
    }
}
