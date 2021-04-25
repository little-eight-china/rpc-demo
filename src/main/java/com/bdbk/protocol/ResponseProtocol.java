package com.bdbk.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * response protocol
 * @author little_eight
 * @since 2021/4/23
 */
@Data
public class ResponseProtocol implements Serializable {
    /**
     * 错误信息
     */
    private String error;
    /**
     * 返回的结果
     */
    private Object result;
}
