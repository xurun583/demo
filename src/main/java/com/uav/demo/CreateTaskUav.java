package com.uav.demo;

import org.apache.log4j.Logger;
import org.omg.CORBA.OBJ_ADAPTER;
import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author Run.Xu
 * @Date Created in 13:01 2019/2/21
 */
public class CreateTaskUav implements Runnable{
    final Map<String,Object> taskIds ;
    final Logger    logger  =  Logger.getLogger(CreateTaskUav. class );

    public CreateTaskUav(Map<String, Object> taskIds) {
        this.taskIds = taskIds;
    }

    @Override
    public void run() {
        try {
            taskIds.put("Action","CreateTask");
            taskIds.put("StartTime",new Date().getTime());
            logger.debug("The uav delivery parma is "+ taskIds);
            System.out.print("send sucess ing  " + Thread.currentThread().getName());
            HttpConnect.send("127.0.0.1:8081",taskIds,"utf-8");
            logger.debug("The uav delivery is sucessed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
