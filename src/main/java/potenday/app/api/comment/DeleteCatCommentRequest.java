package potenday.app.api.comment;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteCatCommentRequest {

  @NotNull
  Long commentId;
}
