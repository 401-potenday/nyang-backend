package potenday.app.global.converter;

import org.springframework.core.convert.converter.Converter;
import potenday.app.api.content.search.CreateTimeOrder;

public class CreateTimeOrderConverter implements Converter<String, CreateTimeOrder> {

    @Override
    public CreateTimeOrder convert(String value) {
        return CreateTimeOrder.from(value);
    }
}