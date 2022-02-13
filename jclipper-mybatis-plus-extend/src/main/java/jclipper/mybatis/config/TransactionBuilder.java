package jclipper.mybatis.config;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TransactionBuilder {
    private static String AOP_POINTCUT_EXPRESSION = "execution (* jclipper..*.service..impl.*.*(..))";

    public static TransactionInterceptor buildAdvice(TransactionManager transactionManager){
        return new TransactionInterceptor(transactionManager,createTransactionAttributeSource());
    }

    public static Advisor buildAdvisor(Advice advice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    private static NameMatchTransactionAttributeSource createTransactionAttributeSource(){
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        Map<String, TransactionAttribute> attributeMap = new HashMap<>();
        attributeMap.put("add*", requiredTx);
        attributeMap.put("save*", requiredTx);
        attributeMap.put("insert*", requiredTx);
        attributeMap.put("del*", requiredTx);
        attributeMap.put("update*", requiredTx);
        attributeMap.put("modify*", requiredTx);
        attributeMap.put("*WithTx", requiredTx);
        attributeMap.put("set*", requiredTx);
        source.setNameMap( attributeMap );
        return source;
    }
}
