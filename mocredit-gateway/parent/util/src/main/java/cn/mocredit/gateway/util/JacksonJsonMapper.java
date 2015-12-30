package cn.mocredit.gateway.util;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.codehaus.jackson.JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS;
import static org.codehaus.jackson.JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS;
import static org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES;
import static org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;
import static org.codehaus.jackson.map.DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;

public class JacksonJsonMapper {
    private static Logger logger = LoggerFactory.getLogger(JacksonJsonMapper.class);
    private static volatile ObjectMapper objectMapper = null;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private JacksonJsonMapper() {}

	private static ObjectMapper getInstance() {
		if (objectMapper == null) {
			synchronized (ObjectMapper.class) {
				if (objectMapper == null) {
					objectMapper = new ObjectMapper();
                    objectMapper.configure(ALLOW_SINGLE_QUOTES, true);
                    objectMapper.configure(ALLOW_UNQUOTED_FIELD_NAMES, true);
                    objectMapper.configure(WRITE_NUMBERS_AS_STRINGS, true);
                    objectMapper.configure(QUOTE_NON_NUMERIC_NUMBERS, true);
                    //空值转换-异常情况
                    objectMapper.configure(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true) ;
                    objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
                        @Override
                        public void serialize(Object value, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
                            jg.writeString("");
                        }
                    });
                    //设置在序列化的时候，日期格式设置
                    objectMapper.getSerializationConfig().setDateFormat(DATE_FORMAT);
                    objectMapper.setDateFormat(DATE_FORMAT);
                    objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
				} 
			}
		}
		return objectMapper;
	}

	public static String objectToJson(Object obj) {
		try {
			return getInstance().writeValueAsString(obj);
		} catch (Exception e) {
            logger.error(getStackTrace(e));
            return null;
		}
	}
	
	public static <T> T jsonToObject(String jsonString, Class<T> clazz) {
        try {
			return getInstance().readValue(jsonString, clazz);
        } catch (Exception e) {
            logger.error(getStackTrace(e));
            return null;
        }
	}
	
	public static <E, T> ArrayList<E> jsonToObjList(String jsonString, Class<T> claszz) {
		ObjectMapper mapper = getInstance();
		try {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, claszz);   
			return mapper.readValue(jsonString, javaType);
        } catch (Exception e) {
            logger.error(getStackTrace(e));
            return null;
        }
	}
}
