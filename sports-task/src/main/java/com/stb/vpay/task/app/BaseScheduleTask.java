package com.stb.vpay.task.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zzwei
 * @since 2017/06/06
 */
public abstract class BaseScheduleTask  {
    protected final Log logger = LogFactory.getLog(this.getClass());
    //是否开启
    protected boolean devmode = false;

    public void doTask() {
        if (!devmode) {
            doSpecificTask();
        }
    }

    protected abstract void doSpecificTask();
}
