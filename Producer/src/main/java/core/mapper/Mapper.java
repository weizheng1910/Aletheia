package core.mapper;

import ch.qos.logback.classic.joran.action.EvaluatorAction;
import core.avro.Insider;
import org.apache.avro.*;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;

import java.beans.*;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Mapper {

  private Mapper() {}

  private Insider createSpecificRecordInstance(String className) throws Exception {
    Class<?> c = Class.forName(className);
    Constructor<?> cons = c.getConstructor();
    return (Insider) cons.newInstance();
  }

  @Deprecated
  private GenericRecord createAvroRecord(String className) throws Exception {
    var specificRecord = createSpecificRecordInstance(className);
    Schema schema = specificRecord.getSchema();
    return new GenericData.Record(schema);
  }

  private Map<String, Object> createObjectMap(Object object) throws Exception {

    var propertyDescriptors = getPropertyDescriptors(object);

    var map = new LinkedHashMap<String, Object>();

    for (var desc : propertyDescriptors) {
      var nameOfField = desc.getName();
      var valueOfField = desc.getReadMethod().invoke(object);
      map.put(nameOfField, valueOfField);
    }

    return map;
  }

  private List<PropertyDescriptor> getPropertyDescriptors(Object object)
      throws IntrospectionException {

    var clazz = object.getClass();
    var beanInfo = Introspector.getBeanInfo(clazz);

    return Arrays.stream(beanInfo.getPropertyDescriptors())
        .filter(pd -> !pd.getName().equalsIgnoreCase("class"))
        .collect(Collectors.toList());
  }

  public Object mapJavaPojoToAvroRecord(Object javaPojo, String avroClassName) throws Exception {

    var javaPojoMap = createObjectMap(javaPojo);

    var insider = new Insider();
    var builder = Insider.newBuilder(insider);

    var avroSpecificRecord = builder
        .setId((String) javaPojoMap.get("id"))
        .setQuantityOwnedFollowingTransaction((BigDecimal) javaPojoMap.get("quantityOwnedFollowingTransaction"))
        .setTransactionDate((LocalDate) javaPojoMap.get("transactionDate"))
        .build();

    return (SpecificRecord) avroSpecificRecord;
  }
}
