package cn.mocredit.gateway;

import java.io.IOException;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.CharacterEncodingFilter;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

@SpringBootApplication
@PropertySource({"classpath:do_not_modify.properties"})
public class WangposApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(WangposApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean characterEncodingFilter() {
		CharacterEncodingFilter f = new CharacterEncodingFilter();
		f.setEncoding("UTF-8");
		f.setForceEncoding(true);
		FilterRegistrationBean ret = new FilterRegistrationBean();
		ret.setFilter(f);
		return ret;
	}

    @Bean(name="PropertiesStringParser")
    public PropertiesStringParser maper(){
        return new PropertiesStringParser();
    }

    public class PropertiesStringParser {
        public Map<String, String> stringToMap(String s) {
            s = s == null ? "" : s.trim();
            return stream(s.split(",")).collect(toMap(k -> getKeyValue(k, 0), v -> getKeyValue(v, 1)));
        }

        private String getKeyValue(String s, int index) {
            s = s == null ? "" : s.trim();
            String[] split = s.trim().split(":");
            return split.length <= index ? "" : split[index].trim();
        }
    }
}
