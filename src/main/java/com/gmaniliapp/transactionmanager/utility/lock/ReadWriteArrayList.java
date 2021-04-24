package com.gmaniliapp.transactionmanager.utility.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteArrayList<E> extends ArrayList<E> {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public void lockReading() {
        readLock.lock();
    }

    public void unlockReading() {
        readLock.unlock();
    }

    public void lockWriting() {
        writeLock.lock();
    }

    public void unlockWriting() {
        writeLock.unlock();
    }

    @Override
    public boolean add(E element) {
        try {
            lockWriting();
            return super.add(element);
        } finally {
            unlockWriting();
        }
    }

    @Override
    public E get(int index) {
        try {
            lockReading();
            return super.get(index);
        } finally {
            unlockReading();
        }
    }

    @Override
    public E remove(int index) {
        try {
            lockWriting();
            return super.remove(index);
        } finally {
            unlockWriting();
        }
    }

    @Override
    public void clear() {
        try {
            lockWriting();
            super.clear();
        } finally {
            unlockWriting();
        }
    }
}
