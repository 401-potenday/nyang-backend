package potenday.app.query.model.content;

import java.util.List;
import org.springframework.data.domain.Page;
import potenday.app.domain.cat.CatContent;

public record CatContentSummariesResponse(
    List<CatContentSummary> items,
    int currentPage,
    int pageSize,
    int totalPages,
    long totalItems,
    Boolean isEnd
) {

  public static CatContentSummariesResponse of(Page<CatContent> catContents) {
    return new CatContentSummariesResponse(
        catContents.map(CatContentSummary::of).stream().toList(),
        catContents.getPageable().getPageNumber(),
        catContents.getPageable().getPageSize(),
        catContents.getTotalPages(),
        catContents.getTotalElements(),
        catContents.isLast()
    );
  }

}
