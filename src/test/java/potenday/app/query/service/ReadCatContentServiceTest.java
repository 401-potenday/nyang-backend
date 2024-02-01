package potenday.app.query.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import potenday.app.query.model.CatContentDetails;

@SpringBootTest(properties = "jasypt.encryptor.password=potenday401!")
@ActiveProfiles("dev")
class ReadCatContentServiceTest {

  @Autowired
  private ReadCatContentService readCatContentService;

  @Test
  void fetchContentDetails() {
    CatContentDetails fetchedContentDetails = readCatContentService.fetchContentDetails(30);
  }
}