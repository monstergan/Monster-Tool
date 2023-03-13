package com.monster.core.tool.function;

/**
 * 受检的 Comparator
 * <p>
 * Create by monster gan on 2023/3/13 10:51
 *
 * @author monster gan
 */
@FunctionalInterface
public interface CheckedComparator<T> {


    /**
     * Compares its two arguments for order.
     *
     * @param o1 o1
     * @param o2 o2
     * @return int
     * @throws Throwable CheckedException
     */
    int compare(T o1, T o2) throws Throwable;

}
