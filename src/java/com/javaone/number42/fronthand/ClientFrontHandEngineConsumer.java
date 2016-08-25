package com.javaone.number42.fronthand;

import com.robo4j.brick.fronthand.ClientFrontHandException;
import com.robo4j.brick.logging.SimpleLoggingUtil;
import com.robo4j.brick.service.LcdService;
import com.robo4j.commons.agent.AgentConsumer;
import com.robo4j.commons.concurrent.CoreBusQueue;
import lejos.robotics.RegulatedMotor;

import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;

/**
 * @author Miro Kopecky (@miragemiko)
 * @since 03.07.2016
 */
public class ClientFrontHandEngineConsumer implements AgentConsumer, Callable<Boolean> {

    private static final int ROTATION = 20;
    private Exchanger<Boolean> exchanger;
    private RegulatedMotor motorHandPortA;

    public ClientFrontHandEngineConsumer(Exchanger<Boolean> exchanger, RegulatedMotor engine){
        this.exchanger = exchanger;
        this.motorHandPortA = engine;
    }

    @Override
    public void setMessageQueue(CoreBusQueue commandsQueue) {
        throw new com.robo4j.brick.fronthand.ClientFrontHandException("Not implemented messageQueue");
    }

    public RegulatedMotor getMotorHandPortA(){
        return motorHandPortA;
    }

    @Override
    public Boolean call() throws Exception {
        /* represent close  */
        boolean initValue = true;
        LcdService lcdService = new LcdService("MAGIC ROBOT");
        lcdService.printText("Engine ACTIVE");
        lcdService.printText("Press Touch sensor");
        while (true){
            SimpleLoggingUtil.print(getClass(),"Engine STARTS");
            try {
//                boolean state = exchanger.exchange(initValue);
                boolean state = exchanger.exchange(initValue);
                if(state) {
                    initValue = state;
                    SimpleLoggingUtil.print(ClientFrontHandEngineConsumer.class, "ENGINE  OFF = "+ Thread.currentThread().getName());
                    motorHandPortA.rotate(-ROTATION);
                    SimpleLoggingUtil.print(ClientFrontHandEngineConsumer.class, "ROTATION TOP= " + ROTATION);
                    motorHandPortA.rotate(ROTATION);
                    SimpleLoggingUtil.print(ClientFrontHandEngineConsumer.class, "Achieved BACK = " + ROTATION);
                }
                SimpleLoggingUtil.print(getClass(),"SEND SIGNAL");
            } catch (InterruptedException e) {
                throw new ClientFrontHandException("FRONT_HAND= ",e);
            } finally {
                SimpleLoggingUtil.print(getClass(),"ENGINE DONE = "+ Thread.currentThread().getName());

            }
        /* engine is not active */
        }

    }

}

