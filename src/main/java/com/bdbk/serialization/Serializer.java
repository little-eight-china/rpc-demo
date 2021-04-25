package com.bdbk.serialization;

import java.io.IOException;

/**
 * 序列化接口
 * @author little_eight
 * @since 2021/4/23
 */
public interface Serializer {
    /**
     * java对象转换为二进制
     */
    byte[] serialize(Object object) throws IOException;

    /**
     * 二进制转换成java对象
     */
    <T> T deserialize(byte[] bytes) throws IOException;
}
