package com.uav.demo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Description:
 * @Author Run.Xu
 * @Date Created in 12:47 2019/2/21
 */
public class CreateThreadPool {
    public static void main(String args[]) throws ParseException {
        String commend =  Commend.commed();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        List<Map<String,Object>> taskList = new ArrayList<Map<String,Object>>();
        String cmd[] = commend.split(";");
        for(int i=0;i<cmd.length;i++){
            String[] taskStr = cmd[i].split("#");
            Map<String,Object> param = new HashMap<>();
            param.put("TaskId",taskStr[0]);
            param.put("DroneName",taskStr[1]);
            param.put("DeliveryAddress",taskStr[2]);
            param.put("LongitudeLatitude",taskStr[3]);
            param.put("CompletedTime",dft.parse(taskStr[4]).getTime());
            param.put("Version",taskStr[5]);
            taskList.add(param);
        }
        //不建议的做法
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        //使用 guava 开源框架的 ThreadFactoryBuilder 给线程池的线程设置名字
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-thread-%d").build();

        ExecutorService pool = new ThreadPoolExecutor(4, 10, 0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(256),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        //并行处理派送无人机的请求
        taskList.stream().parallel().forEach(s ->{
            pool.execute(new CreateTaskUav(s));
        });
        //并行处理结束时间大于现在时间的执行查询请求
        taskList.stream().filter(s ->{
            return Long.valueOf(s.get("CompletedTime").toString())>new Date().getTime();
        }).parallel().forEach(s ->{
            pool.execute(() -> Schedule.queryTaskUav(s));
        });
        pool.shutdown();
    }
}