package potenday.app.api.validation;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;
import potenday.app.api.validation.ValidationGroups.NotBlankGroup;
import potenday.app.api.validation.ValidationGroups.NotWhiteSpaceGroup;
import potenday.app.api.validation.ValidationGroups.SizeGroup;

@GroupSequence({Default.class, NotBlankGroup.class, NotWhiteSpaceGroup.class, SizeGroup.class})
public interface NicknameValidationSequence {

}
