package com.uav.demo;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author Run.Xu
 * @Date Created in 15:43 2019/2/21
 */
public class Schedule {
    public  static void  queryTaskUav(Map<String,Object> parma) {
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(new QueryTaskUav(parma), 10, 1, TimeUnit.SECONDS);
    }
}
