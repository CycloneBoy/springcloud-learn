package com.cycloneboy.springcloud.common.command;

import lombok.Setter;

/**
 * Create by  sl on 2019-08-11 19:43
 */
@Setter
public class Invoker {

    private Command command;

    public void action(){
        this.command.execute();
    }
}
