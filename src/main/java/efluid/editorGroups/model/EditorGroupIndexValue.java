package efluid.editorGroups.model;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import efluid.editorGroups.IndexCache;
import efluid.editorGroups.support.Utils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditorGroupIndexValue extends EditorGroup {

  /*definitions*/
  private String ownerPath = "";
  private String id = "";
  private String root = "";
  private String title = "";
  private String backgroundColor = "";
  private String foregroundColor = "";
  private final List<String> relatedPaths = new ArrayList<>();

  /*runtime data*/
  private transient volatile List<Link> links;
  private transient volatile boolean valid = true;
  private transient volatile Color bgColorInstance = null;
  private transient volatile Color fgColorInstance = null;

  public EditorGroupIndexValue() {
  }

  public EditorGroupIndexValue(String id, String title, boolean valid) {
    this.id = id;
    this.title = title;
    this.valid = valid;
  }

  public EditorGroupIndexValue setTitle(String title) {
    this.title = StringUtil.notNullize(title);
    return this;
  }

  @Override
  public String getOwnerPath() {
    return ownerPath;
  }

  public void setOwnerPath(String ownerPath) {
    this.ownerPath = FileUtil.toSystemIndependentName(ownerPath);
  }

  @NotNull
  public String getId() {
    return id;
  }


  public EditorGroupIndexValue setId(String id) {
    this.id = StringUtil.notNullize(id);
    return this;
  }

  public String getRoot() {
    return root;
  }

  public EditorGroupIndexValue setRoot(String root) {
    this.root = root;
    return this;
  }

  public List<String> getRelatedPaths() {
    return relatedPaths;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public boolean isValid() {
    return valid;
  }

  @Override
  public Icon icon() {
    return AllIcons.Actions.GroupByModule;
  }

  public void invalidate() {
    this.valid = false;
  }

  @Override
  public int size(Project project) {
    return getLinks(project).size();
  }


  @Override
  @NotNull
  public List<Link> getLinks(Project project) {
    if (links == null) {
      IndexCache.getInstance(project).initGroup(this);
    }

    return links;
  }

  public EditorGroupIndexValue setBackgroundColor(String value) {
    backgroundColor = StringUtil.notNullize(value).toLowerCase();
    return this;
  }

  public EditorGroupIndexValue setForegroundColor(String value) {
    this.foregroundColor = StringUtil.notNullize(value).toLowerCase();
    return this;
  }


  public void addRelated(String value) {
    relatedPaths.add(value);
  }

  @Override
  public boolean isOwner(@NotNull String canonicalPath) {
    return ownerPath.equals(canonicalPath);
  }

  public String getBackgroundColor() {
    return backgroundColor;
  }


  @Override
  public Color getBgColor() {
    if (bgColorInstance == null) {
      if (!backgroundColor.isEmpty()) {
        try {
          if (backgroundColor.startsWith("0x") || backgroundColor.startsWith("#")) {
            bgColorInstance = Color.decode(backgroundColor);
          } else {
            bgColorInstance = Utils.getColorInstance(backgroundColor);
          }
        } catch (Exception ignored) {
        }
      }
    }
    return bgColorInstance;
  }

  @Override
  public Color getFgColor() {
    if (fgColorInstance == null) {
      if (!foregroundColor.isEmpty()) {
        try {
          if (foregroundColor.startsWith("0x") || foregroundColor.startsWith("#")) {
            fgColorInstance = Color.decode(foregroundColor);
          } else {
            fgColorInstance = Utils.getColorInstance(foregroundColor);
          }
        } catch (Exception ignored) {
        }
      }
    }
    return fgColorInstance;
  }

  @Override
  public boolean needSmartMode() {
    return true;
  }


  public String getForegroundColor() {
    return foregroundColor;
  }


  public EditorGroupIndexValue setLinks(List<Link> links) {
    this.links = links;
    return this;
  }

  /**
   * FOR INDEX STORE
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    EditorGroupIndexValue that = (EditorGroupIndexValue) o;

    if (!Objects.equals(id, that.id)) return false;
    if (!Objects.equals(ownerPath, that.ownerPath)) return false;
    if (!Objects.equals(root, that.root)) return false;
    if (!Objects.equals(title, that.title)) return false;
    if (!Objects.equals(backgroundColor, that.backgroundColor))
      return false;
    if (!Objects.equals(foregroundColor, that.foregroundColor))
      return false;
    return Objects.equals(relatedPaths, that.relatedPaths);
  }

  /**
   * FOR INDEX STORE
   */
  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (ownerPath != null ? ownerPath.hashCode() : 0);
    result = 31 * result + (root != null ? root.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (backgroundColor != null ? backgroundColor.hashCode() : 0);
    result = 31 * result + (foregroundColor != null ? foregroundColor.hashCode() : 0);
    result = 31 * result + relatedPaths.hashCode();
    return result;
  }


  @Override
  public String toString() {
    return "EditorGroupIndexValue{" +
      "id='" + id + '\'' +
      ", ownerFile='" + ownerPath + '\'' +
      ", root='" + root + '\'' +
      ", title='" + title + '\'' +
      ", backgroundColor='" + backgroundColor + '\'' +
      ", foregroundColor='" + foregroundColor + '\'' +
      ", relatedPaths=" + relatedPaths +
      ", valid=" + valid +
      '}';
  }

}
