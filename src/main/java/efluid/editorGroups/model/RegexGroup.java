package efluid.editorGroups.model;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.impl.ProjectRootUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import efluid.editorGroups.icons.MyIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

public class RegexGroup extends AutoGroup {
  public static final String ID_PREFIX = "RegexGroup: ";
  @NotNull
  private final RegexGroupModel regexGroupModel;
  @Nullable
  private final VirtualFile folder;
  @Nullable
  private final String fileName;

  public RegexGroup(@NotNull RegexGroupModel regexGroupModel, @Nullable VirtualFile folder, List<Link> links, @Nullable String fileName) {
    super(links);
    this.regexGroupModel = regexGroupModel.copy();
    this.folder = folder;
    this.fileName = fileName;
  }

  public RegexGroup(@NotNull RegexGroupModel model) {
    this(model, null, Collections.emptyList(), null);
    setStub(true);
  }

  public RegexGroup(@NotNull RegexGroupModel model, VirtualFile folder) {
    this(model, folder, Collections.emptyList(), null);
    setStub(true);
  }

  public RegexGroup(@NotNull RegexGroupModel model, VirtualFile folder, String fileName) {
    this(model, folder, Collections.emptyList(), fileName);
    setStub(true);
  }

  @NotNull
  public RegexGroupModel getRegexGroupModel() {
    return regexGroupModel;
  }

  @Nullable
  public VirtualFile getFolder() {
    return folder;
  }

  @Nullable
  public String getFolderPath() {
    if (folder != null) {
      return folder.getPath();
    }
    return null;
  }

  @Nullable
  public String getFileName() {
    return fileName;
  }

  @Override
  public Icon icon() {
    return MyIcons.regex;
  }

  @Override
  public String getPresentableTitle(Project project, String presentableNameForUI, boolean showSize) {
    return "Regex: " + regexGroupModel.getRegexPattern();
  }

  @Override
  public boolean isSelected(EditorGroup groupLink) {
    if (groupLink instanceof RegexGroup) {
      return this.regexGroupModel.equals(((RegexGroup) groupLink).getRegexGroupModel());
    } else {
      return super.isSelected(groupLink);
    }
  }

  @Override
  public boolean needSmartMode() {
    return regexGroupModel.getScope() == RegexGroupModel.Scope.WHOLE_PROJECT;
  }

  @NotNull
  @Override
  public String getId() {
    return ID_PREFIX + regexGroupModel.serialize();
  }

  @Override
  public String getTitle() {
    return getId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    RegexGroup that = (RegexGroup) o;

    if (!Objects.equals(regexGroupModel, that.regexGroupModel))
      return false;
    return Objects.equals(folder, that.folder);
  }

  @Override
  public int hashCode() {
    int result = regexGroupModel.hashCode();
    result = 31 * result + (folder != null ? folder.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "RegexGroup{" +
      "model='" + regexGroupModel + '\'' +
      ", fileName='" + fileName + '\'' +
      ", folderPath='" + folder + '\'' +
      ", links=" + links.size() +
      ", stub='" + isStub() + '\'' +
      '}';
  }

  public List<VirtualFile> getScopes(Project project) {
    List<VirtualFile> paths = new ArrayList<>();
    if (regexGroupModel.getScope() == RegexGroupModel.Scope.WHOLE_PROJECT) {
      PsiDirectory[] allContentRoots = ProjectRootUtil.getAllContentRoots(project);
      for (PsiDirectory allContentRoot : allContentRoots) {
        VirtualFile virtualFile = allContentRoot.getVirtualFile();
        paths.add(virtualFile);
      }
    } else {
      paths.add(folder);
    }


    return paths;
  }

  @Nullable
  public Matcher getReferenceMatcher() {
    Matcher referenceMatcher = null;
    String fileName = getFileName();
    if (fileName != null) {
      referenceMatcher = regexGroupModel.getRegexPattern().matcher(fileName);
      boolean matches = referenceMatcher.matches();
      if (!matches) {
        throw new RuntimeException(fileName + " does not match " + getRegexGroupModel());
      }
    }
    return referenceMatcher;
  }
}
