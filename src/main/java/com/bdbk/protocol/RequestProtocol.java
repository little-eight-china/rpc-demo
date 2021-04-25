package com.bdbk.protocol;

import com.bdbk.serialization.Serializer;
import lombok.Data;

import java.io.Serializable;

/**
 * request protocol
 * @author little_eight
 * @since 2021/4/23
 */
@Data
public class RequestProtocol implements Serializable {
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 入参
     */
    private Object[] parameters;
}
