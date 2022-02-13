package jclipper.common.concurrent;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/3/16 20:30.
 */
public class DisruptorExecutor implements Executor {

    private final Disruptor<Element> disruptor;

    private static class Element {

        public Runnable getValue() {
            return value;
        }

        public void setValue(Runnable value) {
            this.value = value;
        }

        private Runnable value;

    }

    public DisruptorExecutor() {
        this(1024, 1);
    }


    public DisruptorExecutor(int bufSize, int threadSize) {
        if (bufSize < 0 || threadSize < 0) {
            throw new IllegalArgumentException("size must be greater than 0");
        }
        AtomicInteger threadCounter = new AtomicInteger(0);
        // 生产者的线程工厂
        ThreadFactory threadFactory = r -> new Thread(r, "DisruptorExecutor_" + threadCounter.incrementAndGet());

        // RingBuffer生产工厂,初始化RingBuffer的时候使用
        EventFactory<Element> factory = Element::new;


        // 处理Event的handler
        EventHandler<Element> handler = (element, sequence, endOfBatch) -> element.value.run();

        // 阻塞策略
        BlockingWaitStrategy strategy = new BlockingWaitStrategy();

        // 指定RingBuffer的大小


        // 创建disruptor，采用单生产者模式
        this.disruptor = new Disruptor<>(factory, bufSize, threadFactory, ProducerType.MULTI, strategy);


        // 设置EventHandler
        EventHandler<Element>[] handlers = new EventHandler[threadSize];
        Arrays.fill(handlers, handler);
        disruptor.handleEventsWith(handlers);

        // 启动disruptor的线程
        disruptor.start();
    }

    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException("command not null");
        }
        RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();
        // 获取下一个可用位置的下标
        long sequence = ringBuffer.next();
        try {
            // 返回可用位置的元素
            Element event = ringBuffer.get(sequence);
            // 设置该位置元素的值
            event.setValue(command);
        } finally {
            ringBuffer.publish(sequence);
        }
    }


}
