package com.javaone.number42.fronthand;

import com.robo4j.brick.logging.SimpleLoggingUtil;
import com.robo4j.brick.service.LcdService;
import com.robo4j.commons.agent.AgentProducer;
import com.robo4j.commons.concurrent.CoreBusQueue;
import lejos.hardware.sensor.EV3TouchSensor;

import java.util.concurrent.Exchanger;

/**
 * Agent is responsible for recognising touch signal
 *
 * @author Miro Kopecky (@miragemiko)
 * @since 03.07.2016
 */
public class ClientFrontHandTouchProducer implements AgentProducer, Runnable {

    private volatile EV3TouchSensor touchSensor;
    private Exchanger<Boolean> exchanger;

    /* Represent signal producer */

    public ClientFrontHandTouchProducer(Exchanger<Boolean> exchanger, EV3TouchSensor touchSensor) {
        this.exchanger = exchanger;
        this.touchSensor = touchSensor;
    }

    public EV3TouchSensor getTouchSensor() {
        return touchSensor;
    }

    @Override
    public CoreBusQueue getMessageQueue() {
        throw new ClientFrontHandException("not implemented message queue");
    }

    @Override
    public void run() {
        LcdService lcdService = new LcdService("MAGIC ROBOT");
        lcdService.printText("Touch ACTIVE");
        while(true){
            boolean state = getTouchSensorPressState(touchSensor);
            SimpleLoggingUtil.print(getClass(), "TOUCH STARTS");
            try {
                exchanger.exchange(state);
            } catch (InterruptedException e) {
                throw new ClientFrontHandException("TOUCH PRODUCER e", e);
            } finally {
                SimpleLoggingUtil.print(ClientFrontHandTouchProducer.class, "TOUCH ENDS thread= " + Thread.currentThread().getName());
            }
        }
    }

    //Private Methods
    private boolean getTouchSensorPressState(EV3TouchSensor touchSensor){
        SimpleLoggingUtil.print(getClass(),"getTouchSensorPressState touchSensor= " + touchSensor.getName());
        boolean touched = false;

        int sampleSize = touchSensor.getTouchMode().sampleSize();
        final float[] samples = new float[sampleSize];
        touchSensor.fetchSample(samples, 0);
        if(samples[0] == 1F){
            touched = true;
        }
        SimpleLoggingUtil.print(getClass(),"getTouchSensorPressState touched= " + touched);
        return touched;
    }
}
