package efluid.editorGroups.model;

import com.intellij.openapi.project.Project;
import efluid.editorGroups.icons.MyIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class SameNameGroup extends AutoGroup {

  public static final SameNameGroup INSTANCE = new SameNameGroup("SAME_NAME_INSTANCE", Collections.emptyList());
  private final String fileNameWithoutExtension;

  public SameNameGroup(String fileNameWithoutExtension, List<Link> links) {
    super(links);
    this.fileNameWithoutExtension = fileNameWithoutExtension;
  }

  @NotNull
  @Override
  public String getId() {
    return SAME_FILE_NAME;
  }

  @Override
  public String getTitle() {
    return SAME_FILE_NAME;
  }

  @Override
  public Icon icon() {
    return MyIcons.copy;
  }

  @Override
  public String getPresentableTitle(Project project, String presentableNameForUI, boolean showSize) {
    return "By same file name";
  }

  @Override
  public String toString() {
    return "SameNameGroup{" +
      "fileNameWithoutExtension='" + fileNameWithoutExtension + '\'' +
      ", links=" + links +
      ", valid=" + valid +
      ", stub='" + isStub() + '\'' +
      '}';
  }
}