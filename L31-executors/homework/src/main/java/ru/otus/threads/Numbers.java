package ru.otus.threads;

import com.google.common.collect.Iterators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class Numbers {

    private static final Logger logger = LoggerFactory.getLogger(Numbers.class);

    private static final String FIRST_THREAD_NAME = "t1";

    private static final String SECOND_THREAD_NAME = "t2";

    private final Iterator<Integer> firstThreadNumberSequence;
    private final Iterator<Integer> secondThreadNumberSequence;

    private String lasThreadName = "t2";

    public Numbers() {
        this.firstThreadNumberSequence = Iterators.cycle(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        this.secondThreadNumberSequence = Iterators.cycle(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    public static void main(String[] args) {
        Numbers numbers = new Numbers();

        Thread t1 = new Thread(numbers::print);
        t1.setName(FIRST_THREAD_NAME);
        t1.start();

        Thread t2 = new Thread(numbers::print);
        t2.setName(SECOND_THREAD_NAME);
        t2.start();
    }

    private synchronized void print() {
        while (!Thread.currentThread().isInterrupted()) {

            try {
                while (lasThreadName.equals(Thread.currentThread().getName())) {
                    this.wait();
                }

                lasThreadName = Thread.currentThread().getName();
                Integer currentSeqVal;

                if (FIRST_THREAD_NAME.equals(lasThreadName)) {
                    currentSeqVal = firstThreadNumberSequence.next();
                } else {
                    currentSeqVal = secondThreadNumberSequence.next();
                }

                logger.info("Thread: {} : {}", lasThreadName, currentSeqVal);
                sleep();
                notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    private void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }
}
