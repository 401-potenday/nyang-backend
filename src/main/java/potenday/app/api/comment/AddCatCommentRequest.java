package potenday.app.api.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import potenday.app.domain.cat.comment.AddCatComment;
import potenday.app.domain.cat.comment.AddCatCommentImages;

@Getter
@NoArgsConstructor
public class AddCatCommentRequest {

  @NotNull(message = "C006")
  @NotEmpty(message = "C006")
  @Size(max = 3, message = "C007")
  private List<String> commentImageUris;

  @Length(max = 300, message = "C008")
  private String commentDesc;

  public AddCatComment toAddCatComment(long contentId) {
    return new AddCatComment(contentId, commentDesc, commentImageUris);
  }

  public AddCatCommentImages toAddCatCommentImages() {
    return new AddCatCommentImages(commentImageUris);
  }
}
