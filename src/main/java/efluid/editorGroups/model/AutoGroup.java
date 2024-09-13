package efluid.editorGroups.model;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import efluid.editorGroups.AutoGroupProvider;
import efluid.editorGroups.support.LinkComparator;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AutoGroup extends EditorGroup {
  private static final Logger LOG = Logger.getInstance(AutoGroup.class);

  public static final String SAME_FILE_NAME = "FILE_NAME";
  public static final String DIRECTORY = "DIRECTORY";

  public static final HidePanelGroup HIDE_GROUP_INSTANCE = new HidePanelGroup();


  protected List<Link> links;
  protected volatile boolean valid = true;

  public AutoGroup(List<Link> links) {
    this.links = links;
    links.sort(LinkComparator.INSTANCE);
  }


  @NotNull
  @Override
  public abstract String getId();


  @Override
  public abstract String getTitle();

  @Override
  public boolean isValid() {
    return valid;
  }

  @Override
  public void invalidate() {
    valid = false;

  }

  @Override
  public int size(Project project) {
    return links.size();
  }

  @Override
  public List<Link> getLinks(Project project) {
    return links;
  }

  @Override
  public boolean isOwner(String ownerPath) {
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    return obj.getClass().equals(this.getClass());
  }


  public boolean isEmpty() {
    return links.isEmpty();
  }

  public boolean hasIndexing() {
    for (Link link : links) {
      if (AutoGroupProvider.INDEXING.equals(link.getName())) {
        return true;
      }
    }
    return false;
  }

}
