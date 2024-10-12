package potenday.app.api.common;

import java.util.List;

public record ScrollContent<T>(
    List<T> items,
    boolean isLast,
    boolean isFirst,
    int currentPage,
    int pageSize
) { }
