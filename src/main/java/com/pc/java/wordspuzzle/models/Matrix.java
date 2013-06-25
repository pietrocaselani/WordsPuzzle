package com.pc.java.wordspuzzle.models;

import java.util.Iterator;

/**
 * Created by Pietro Caselani
 * On 18/06/13
 * WordsPuzzle
 */
public class Matrix<T> implements Iterable<T> {
    //region Fields
    private Object[] mMatrix;
    private int mColumnCount, mRowCount;
    //endregion

    //region Constructors
    public Matrix(int columnCount, int rowCount) {
        this(columnCount, rowCount, null);
    }

    public Matrix(int columnCount, int rowCount, T defaultValue) {
        mColumnCount = columnCount;
        mRowCount = rowCount;

        int size = rowCount * columnCount;

        mMatrix = new Object[size];

        for (int i = 0; i < size; i++) {
            mMatrix[i] = -1;
        }
    }
    //endregion

    //region Getters and Setters
    public int getColumnCount() {
        return mColumnCount;
    }

    public int getRowCount() {
        return mRowCount;
    }
    //endregion

    //region Public methods
    public T getValue(int row, int column) {
        return getValue(row * mColumnCount + column);
    }

    @SuppressWarnings("unchecked")
    public T getValue(int index) {
        return (T) mMatrix[index];
    }

    public void setValue(T value, int row, int column) {
        setValue(value, row * mColumnCount + column);
    }

    public void setValue(T value, int index) {
        mMatrix[index] = value;
    }
    //endregion

    //region Iterable methods
    @Override public Iterator<T> iterator() {
        return new MatrixIteratorImpl();
    }
    //endregion

    private class MatrixIteratorImpl implements Iterator<T> {
        private final int mSize;
        private int mIndex;

        private MatrixIteratorImpl() {
            mSize = mMatrix.length - 1;
            mIndex = 0;
        }

        @Override public boolean hasNext() {
            return mIndex < mSize;
        }

        @Override public T next() {
            return getValue(mIndex++);
        }

        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}