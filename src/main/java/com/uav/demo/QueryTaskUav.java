package com.uav.demo;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * @Description:
 * @Author Run.Xu
 * @Date Created in 13:01 2019/2/21
 */
public class QueryTaskUav implements Runnable{
    final Map<String,Object> taskIds ;
    final Logger logger  =  Logger.getLogger(QueryTaskUav. class );

    public QueryTaskUav(Map<String, Object> taskIds) {
        this.taskIds = taskIds;
    }

    @Override
    public void run() {
        taskIds.put("Action","QueryTask");
        taskIds.put("StartTime",new Date().getTime());
        try {
            logger.debug("The uav delivery query parma is " + taskIds);
            System.out.print("1111111send sucess ing  " + Thread.currentThread().getName());
            HttpConnect.send("127.0.0.1:8081", taskIds, "utf-8");
            logger.debug("The uav delivery query is sucessed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
