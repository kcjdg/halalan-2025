package ph.dgtech.halalan.ballot.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        int totalPages
) {
}
