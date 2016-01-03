package cn.mocredit.gateway.util;


import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class EnvironmentLog implements CommandLineRunner {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Environment environment;

	private String[] keysToSanitize = new String[] { "password", "secret", "key" };

	@SuppressWarnings("unchecked")
	public Map<String, Object> invoke() {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("profiles", this.environment.getActiveProfiles());
		for (PropertySource<?> source : getPropertySources()) {
			if (source instanceof EnumerablePropertySource) {
				addMap(result, (EnumerablePropertySource<?>) source);
			} else if (source instanceof CompositePropertySource) {
				try {
					Field f = CompositePropertySource.class.getDeclaredField("propertySources");
					f.setAccessible(true);
					for (PropertySource<?> ps : (Set<PropertySource<?>>) f.get(source)) {
						if (ps instanceof EnumerablePropertySource) {
							addMap(result, (EnumerablePropertySource<?>) ps);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private void addMap(Map<String, Object> result, EnumerablePropertySource<?> enumerable) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (String name : enumerable.getPropertyNames()) {
			map.put(name, sanitize(name, enumerable.getProperty(name)));
		}
		result.put(enumerable.getName(), map);
	}

	private Iterable<PropertySource<?>> getPropertySources() {
		if (this.environment != null && this.environment instanceof ConfigurableEnvironment) {
			return ((ConfigurableEnvironment) this.environment).getPropertySources();
		}
		return new StandardEnvironment().getPropertySources();
	}

	public Object sanitize(String name, Object object) {
		for (String keyToSanitize : this.keysToSanitize) {
			if (name.toLowerCase().endsWith(keyToSanitize)) {
				return (object == null ? null : "******");
			}
		}
		return object;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void run(String... args) throws Exception {
		Map<String, Object> map = invoke();
		map.put("args", args);
		String str = "All Environment:\r\n{\r\n";
		for (String k : map.keySet().stream().sorted().collect(toList())) {
			str += "\t'" + k + "' : {\r\n";
			Object obj = map.get(k);
			if (obj != null && obj instanceof Map) {
				Map m = (Map) obj;
				for (Object key : (List) m.keySet().stream().sorted().collect(toList())) {
					if ("java.class.path".equals(key)) {
						str += "\t\t'" + key + "' : '\r\n";
						for (String s : Arrays.stream(m.get(key).toString().split(";")).sorted().collect(toList())) {
							str += "\t\t\t" + s + "\r\n";
						}
						str += "\t\t\t'\r\n";
					} else {
						str += "\t\t'" + key + "' : '" + m.get(key) + "'\r\n";
					}
				}
			} else if (obj != null && obj.getClass().isArray()) {
				for (Object v : (Object[]) obj) {
					str += "\t\t'" + v + "'\r\n";
				}
			}
			str += "\t}\r\n";
		}
		logger.info(str);
	}
}
