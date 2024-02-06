package potenday.app.query.model.content;

import java.util.List;
import lombok.Builder;

@Builder
public record CatContentSummaries(
    List<CatContentSummary> items,
    int currentPage,
    int pageSize,
    int totalPages,
    long totalItems,
    Boolean isEnd
) {

}
