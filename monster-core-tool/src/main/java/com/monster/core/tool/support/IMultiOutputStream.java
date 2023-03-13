package com.monster.core.tool.support;

import java.io.OutputStream;

/**
 * Create by monster gan on 2023/3/13 11:22
 */
public interface IMultiOutputStream {

    /**
     * Builds the output stream.
     *
     * @param params the params
     * @return the output stream
     */
    OutputStream buildOutputStream(Integer... params);

}
