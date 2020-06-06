package com.hackerrank.github.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

/**
 * @author Shubham Sharma
 * @date 6/6/20
 */
public class ObjectMapperUtil {
    private static ObjectMapperUtil util = null;

    private ObjectMapper mapper = null;

    private ObjectMapperUtil() {
    }

    public static synchronized ObjectMapperUtil getInstance() {
        if (Objects.isNull(util)) {
            util = new ObjectMapperUtil();
        }
        return util;
    }

    public ObjectMapper getObjectMapper() {
        if (Objects.isNull(mapper)) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }
}
