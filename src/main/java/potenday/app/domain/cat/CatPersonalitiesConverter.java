package potenday.app.domain.cat;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import potenday.app.domain.cat.status.CatPersonalities;
import potenday.app.global.error.ExteneralException;

@Converter
@Slf4j
public class CatPersonalitiesConverter implements AttributeConverter<CatPersonalities, String> {

  @Override
  public String convertToDatabaseColumn(CatPersonalities catPersonalities) {
    if (catPersonalities == null || catPersonalities.getPersonalities().isEmpty()) {
      return CatPersonalities.DEFAULT_PERSONALITY.name();
    }
    return catPersonalities.concatenateCatPersonalitiesWithComma();
  }

  @Override
  public CatPersonalities convertToEntityAttribute(String personalitiesAsString) {
    if (personalitiesAsString == null || personalitiesAsString.trim().isEmpty()) {
      return null;
    }
    // 하드코딩으로 DB의 값을 임의로 수정하지 않는 이상 넣을 때 잘 들어가면 문제가 없다. 최대한 DB 의 에러가 클라이언트까지 전파 안되도록 한다.
    try {
      return CatPersonalities.fromSting(personalitiesAsString);
    } catch (ExteneralException e) {
      log.error("CatPersonalitiesConverter.convertToEntityAttribute error: {}, personalitiesAsString :  {}", e.getMessage(), personalitiesAsString);
      // 유저가 자체적으로 수정할 수 있다.
      return CatPersonalities.of(List.of(CatPersonalities.DEFAULT_PERSONALITY.name()));
    }
  }
}
