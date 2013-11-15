package com.metakeeper.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.metakeeper.main.MetaKeeper;

@RunWith(JUnit4.class)
public class UnitTest {
    
    @Test
    public void testdotToVerticalBar() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnknownHostException{
        MetaKeeper mk = new MetaKeeper("127.0.0.1");
        Method m = MetaKeeper.class.getDeclaredMethod("dotToVerticalBar", String.class);
        m.setAccessible(true);
        String str = (String) m.invoke(mk, "10.1.1.1");
        Assert.assertEquals("10|1|1|1", str);
    }
    
    @Test
    public void testVerticalBarToDot() throws UnknownHostException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        MetaKeeper mk = new MetaKeeper("127.0.0.1");
        Method m = MetaKeeper.class.getDeclaredMethod("verticalBarToDot", String.class);
        m.setAccessible(true);
        String str = (String) m.invoke(mk, "10|1|1|1");
        Assert.assertEquals("10.1.1.1", str);
    }
    
    @Test
    public void testWriteMetaInfo() throws UnknownHostException{
        MetaKeeper mk = new MetaKeeper("127.0.0.1");
        Assert.assertTrue(mk.writeMetaInfo("10.0.0.1", "/cluster/tmp"));
    }
    
    @Test
    public void testReadMetaInfo() throws UnknownHostException {
        MetaKeeper mk = new MetaKeeper("127.0.0.1");
        mk.writeMetaInfo("10.1.2.5", "/tmp3");
        mk.writeMetaInfo("10.1.2.2", "/tmp3");
        mk.writeMetaInfo("10.1.2.1", "/tmp3");
        Assert.assertEquals("10.1.2.5", mk.readMetaInfo("/tmp3").get(0));
        Assert.assertEquals("10.1.2.2", mk.readMetaInfo("/tmp3").get(1));
        Assert.assertEquals("10.1.2.1", mk.readMetaInfo("/tmp3").get(2));
    }
    
    @Test
    public void testDelMetaInfo() throws UnknownHostException{
        MetaKeeper mk = new MetaKeeper("127.0.0.1");
        mk.writeMetaInfo("10.1.2.1", "/tmp4");
        Assert.assertTrue(mk.delMetaInfo("10.1.2.1"));
    }
    
    @Test
    public void testExistMetaInfo() throws UnknownHostException{
        MetaKeeper mk = new MetaKeeper("127.0.0.1");
        mk.writeMetaInfo("10.9.2.5", "/tmp");
        Assert.assertTrue(mk.existMetaInfo("10.9.2.5", "/tmp"));
    }
}
