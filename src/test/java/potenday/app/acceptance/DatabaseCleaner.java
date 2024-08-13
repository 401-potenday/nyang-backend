package potenday.app.acceptance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner implements InitializingBean {

  @PersistenceContext
  private EntityManager entityManager;

  private List<String> tableNames;

  @Override
  public void afterPropertiesSet() {
    tableNames = entityManager.getMetamodel().getEntities().stream()
        .map(it -> it.getJavaType().getAnnotation(Table.class))
        .filter(Objects::nonNull)
        .map(Table::name)
        .collect(Collectors.toList());
  }

  @Transactional
  public void execute() {
    entityManager.flush();
    entityManager.createNativeQuery("SET foreign_key_checks = 0;").executeUpdate();
    tableNames.forEach(tableName -> executeQueryWithTable(tableName));
    entityManager.createNativeQuery("SET foreign_key_checks = 1;").executeUpdate();
  }

  private void executeQueryWithTable(String tableName) {
    entityManager.createNativeQuery("TRUNCATE TABLE " + tableName + ";").executeUpdate();
  }
}