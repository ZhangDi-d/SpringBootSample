package com.ryze;

import com.ryze.util.IterList;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Arrays;
import java.util.List;

/**
 * LambdaExpressions Tester.
 *
 * @author zhangdi
 * @version 1.0
 * @since <pre>°ËÔÂ 1, 2019</pre>
 */
public class LambdaExpressionsTest {
    LambdaExpressions expressions = null;
    @Before
    public void before() throws Exception {
        expressions = new LambdaExpressions();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: strSortJava7(List list)
     */
    @Test
    public void testStrSortJava7() throws Exception {
        List<String> list = Arrays.asList("zhangsan","lisi","wangwu","chenliu");
        IterList.printEleOfList(list);
        System.out.println("-----------------");
        expressions.strSortJava7(list);
        IterList.printEleOfList(list);
    }

    /**
     * Method: strSortJava8(List list)
     */
    @Test
    public void testStrSortJava8() throws Exception {
        List<String> list = Arrays.asList("zhangsan","lisi","wangwu","chenliu");
        IterList.printEleOfList(list);
        System.out.println("-----------------");
        expressions.strSortJava8(list);
        IterList.printEleOfList(list);
    }

    /**
     * Method: strSortJava8Simple(List<String> list)
     */
    @Test
    public void testStrSortJava8Simple() throws Exception {
        List<String> list = Arrays.asList("zhangsan","lisi","wangwu","chenliu");
        IterList.printEleOfList(list);
        System.out.println("-----------------");
        expressions.strSortJava8Simple(list);
        IterList.printEleOfList(list);
    }

    /**
     * Method: strSortJava8SimpleMore(List<String> list)
     */
    @Test
    public void testStrSortJava8SimpleMore() throws Exception {

    }


} 
