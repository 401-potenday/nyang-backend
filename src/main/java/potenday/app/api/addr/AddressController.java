package potenday.app.api.addr;

import static potenday.app.global.cache.CacheConst.SEARCH_ADDR;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import potenday.app.api.common.ApiResponse;

@RestController
public class AddressController {

  private final AddressQuery addressQuery;

  public AddressController(AddressQuery addressQuery) {
    this.addressQuery = addressQuery;
  }

  @GetMapping("/addr")
  @Cacheable(cacheNames = SEARCH_ADDR)
  public ApiResponse<?> getAddr() {
    return ApiResponse.success(addressQuery.findAll());
  }
}
