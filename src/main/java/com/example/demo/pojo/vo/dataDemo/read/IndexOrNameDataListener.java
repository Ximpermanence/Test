package com.example.demo.pojo.vo.dataDemo.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: chenhao
 * @create:2020/8/25 16:20
 **/
public class IndexOrNameDataListener<IndexOrNameData> extends AnalysisEventListener<IndexOrNameData> {

    public static final Logger LOGGER = LoggerFactory.getLogger(IndexOrNameDataListener.class);

    /**
     * 每隔5条存储数据库，实际中可以适用3000条，然后清理List,方便内存回收
     */
    public static final int BATCH_COUNT = 5;
    List<IndexOrNameData> list = new ArrayList<>();

    private DemoDao demoDao;

    public IndexOrNameDataListener() {
        //这里是demo，所以随便new一个，实际如果使用到了spring,请使用下面的有参构造
        demoDao = new DemoDao();
    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param demoDao
     */
    public IndexOrNameDataListener(DemoDao demoDao) {
        this.demoDao = demoDao;
    }


    @Override
    public void invoke(IndexOrNameData data, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        //达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            //存储完清理list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("不知道这个方法被调用了几次");
    }

    /**
     * 存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        demoDao.save(list);
        LOGGER.info("存储成功");
    }

}
