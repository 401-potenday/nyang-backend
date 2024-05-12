package potenday.app.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CatContentReportRepository extends JpaRepository<CatContentReport, Long> {

  @Query(
      "SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END "
          + "FROM CatContentReport r "
          + "WHERE r.contentId = :contentId and r.status = 'PENDING'"
  )
  boolean findPendingReportByContentId(@Param("contentId") long contentId);

  @Query(
      "SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END "
          + "FROM CatContentReport r "
          + "WHERE r.contentId = :contentId and r.status = 'COMPLETED' or r.status = 'PENDING'"
  )
  boolean findPendingOrCompletedReportByContentId(@Param("contentId") long contentId);

}
