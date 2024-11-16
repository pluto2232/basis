package test;

import drools.entity.Order;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class TestDrools {

    @Test
    public void test1(){
        System.setProperty("drools.dateformat","yyyy-MM-dd HH:mm");
        // 1. 获取 KieServices
        KieServices kieServices = KieServices.Factory.get();
        // 2. 获得Kie容器对象(kieContainer)
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        // 3. 从Kie容器对象中获取会话对象
        KieSession session = kieContainer.newKieSession();
        // 4. Fact对象，事实对象
        Order order = new Order();
        order.setOriginalPrice(100d);
        //      将Order对象插入到工作内存中
        session.insert(order);

        System.out.println("----优惠后价格："+order.getRealPrice());

        // 5. 激活规则，由Drools框架自动进行规则匹配，如果规则匹配成功，则执行当前规则
        session.fireAllRules();

        // 6. 关闭会话
        session.dispose();
        System.out.println("优惠后价格："+order.getRealPrice());
    }



}
