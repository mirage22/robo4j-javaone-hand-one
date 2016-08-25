package com.javaone.number42;

import com.javaone.number42.engine.FrontHandEngine;
import com.javaone.number42.sensor.FrontHandTouchSensor;
import com.javaone.number42.unit.FrontHandUnit;
import com.robo4j.brick.client.AbstractClient;
import com.robo4j.brick.client.request.RequestProcessorCallable;
import com.robo4j.brick.client.request.RequestProcessorFactory;
import com.robo4j.brick.logging.SimpleLoggingUtil;
import com.robo4j.brick.service.LcdService;
import com.robo4j.brick.util.ConstantUtil;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 * Program to control Robot Number42 Hand
 *
 * @author Miro Kopecky (@miragemiko)
 * @since 22.08.2016
 */
public class Number42HandMain extends AbstractClient {

    private static final int PORT = 8025;

    public static void main(String[] args) {
        SimpleLoggingUtil.print(Number42HandMain.class, "... Robo4j running...");
        new Number42HandMain();
    }

    @SuppressWarnings(value = "unchecked")
    private Number42HandMain(){
        //Logic how the robot will be initiated
        super(Stream.of(FrontHandEngine.class),
                Stream.of(FrontHandTouchSensor.class),
                Stream.of(FrontHandUnit.class));
        SimpleLoggingUtil.print(Number42HandMain.class, "SERVER starts...");

        LcdService lcdService = new LcdService("MAGIC ROBOT");
        lcdService.printName();
        lcdService.printText("Wait for ROBOT initiation");

        final AtomicBoolean active = new AtomicBoolean(true);
        submit(new RequestProcessorCallable(null, getEngineCache(), getSensorCache(), getUnitCache()));

        try(ServerSocket server = new ServerSocket(PORT)){

            Button.ESCAPE.addKeyListener(new KeyListener() {
                @Override
                public void keyPressed(Key key) {
                    lcdService.printText("Pressed KEY ESC");
                    RequestProcessorFactory.getInstance(null, null, null).deactivate();
                    active.set(false);
                    try {
                        server.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void keyReleased(Key key) {

                }
            });

            while(active.get()){
                Socket request = server.accept();
                Future<String> result = submit(new RequestProcessorCallable(request, getEngineCache(),
                        getSensorCache(), getUnitCache()));
                if(result.get().equals(ConstantUtil.EXIT)){
                    SimpleLoggingUtil.print(Number42HandMain.class, "IS EXIT");
                    active.set(false);
                }
            }
        } catch (InterruptedException | ExecutionException | IOException e){
            SimpleLoggingUtil.print(Number42HandMain.class, "SERVER CLOSED");
        }
        end();
        SimpleLoggingUtil.print(Number42HandMain.class, "FINAL END");
        System.exit(0);
    }


}
