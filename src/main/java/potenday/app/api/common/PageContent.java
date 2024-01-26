package potenday.app.api.common;

import java.util.List;

public record PageContent<T>(
    List<T> items,
    int currentPage,
    int pageSize,
    int totalPages,
    int totalItems
) {
}
