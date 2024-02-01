package potenday.app.global.converter;

import org.springframework.core.convert.converter.Converter;
import potenday.app.api.content.search.DistanceOrder;

public class DistanceOrderConverter implements Converter<String, DistanceOrder> {

  @Override
  public DistanceOrder convert(String source) {
    return DistanceOrder.from(source);
  }
}
