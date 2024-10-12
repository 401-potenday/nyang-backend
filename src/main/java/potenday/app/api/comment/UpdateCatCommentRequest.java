package potenday.app.api.comment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class UpdateCatCommentRequest {

  @NotNull(message = "C006")
  @Size(max = 3, message = "C007")
  private List<String> commentImageKeys;

  @Length(max = 300, message = "C008")
  private String commentDesc;

  public UpdateCatComment toUpdateComment(long commentId) {
    return new UpdateCatComment(commentId, commentDesc);
  }

  public UpdateCatCommentImages toUpdateImages() {
    return new UpdateCatCommentImages(commentImageKeys);
  }
}
