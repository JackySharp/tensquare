package com.zelin.canal.test;

import com.zelin.canal.dao.CanalDao;
import org.junit.Before;
import org.junit.Test;

public class CanalTest {

    private CanalDao canalDao;

    @Before
    public void init() {
        canalDao = new CanalDao();
    }

    @Test
    public void test() {
        try {
            canalDao.fetchData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
