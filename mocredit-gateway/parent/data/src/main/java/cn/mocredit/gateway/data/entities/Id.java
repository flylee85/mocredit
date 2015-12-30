package cn.mocredit.gateway.data.entities;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.core.style.ToStringCreator;
@MappedSuperclass
public abstract class Id {
	@javax.persistence.Id
	@Column(unique = true, nullable = false)
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Id other = (Id) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		SortedMap<String, Method> map = methodMap(this.getClass());
		ToStringCreator t = new ToStringCreator(this).append("id", id);
		t = build(this, map, t, true);
		return t.toString();
	}

	private static SortedMap<String, Method> methodMap(Class<? extends Id> c) {
		SortedMap<String, Method> m = cache.get(c);
		if (m != null) {
			return m;
		}
        Set<String> except = stream(new String[]{"getClass", "getId"}).collect(toSet());
        TreeMap<String, Method> ret =  stream(c.getMethods())
				.filter(x -> (x.getName().startsWith("get") && !except.contains(x.getName())))
				.collect(toMap(x -> x.getName(), x -> x,(m1, m2) -> m1,TreeMap::new ));
		cache.put(c, ret);
		return ret;
	}

	private static Map<Class<? extends Id>, SortedMap<String, Method>> cache = new HashMap<Class<? extends Id>, SortedMap<String,Method>>();

	private static ToStringCreator build(Id obj, SortedMap<String, Method> map, final ToStringCreator t, boolean needShowBig) {
        map.keySet().forEach(k->{
            try {
                String fieldName = k.substring(3, 4).toLowerCase() + k.substring(4);
                Object o = map.get(k).invoke(obj, (Object[]) null);
                boolean isBig = o instanceof List || o instanceof Set || o instanceof Map || (o != null && o.getClass().isArray());
                if (!(isBig && !needShowBig)) {
                    t.append(fieldName + (o instanceof Id ? ".getId()" : ""), o instanceof Id ? ((Id) o).getId() : o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
		return t;
	}
}
