package com.bdbk.serialization;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 采用hessian做序列化
 * @author little_eight
 * @since 2021/4/23
 */
public class HessianSerializer implements Serializer{

    @Override
    public byte[] serialize(Object object) throws IOException {
        HessianOutput ho = null;
        try{
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ho = new HessianOutput(os);
            ho.writeObject(object);
            return os.toByteArray();
        } finally {
            if(ho != null){
                ho.close();
            }
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] bytes) throws IOException {
        HessianInput hi = null;
        try{
            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
            hi = new HessianInput(is);
            return (T) hi.readObject();
        } finally {
            if(hi != null){
                hi.close();
            }
        }
    }
}
