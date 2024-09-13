package efluid.editorGroups.actions;

import com.intellij.openapi.actionSystem.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class PopupMenu {
  public static void popupInvoked(Component component, int x, int y) {
    DefaultActionGroup group = getDefaultActionGroup();
    ActionPopupMenu menu = ActionManager.getInstance().createActionPopupMenu(ActionPlaces.UNKNOWN, group);
    menu.getComponent().show(component, x, y);
  }

  @NotNull
  public static DefaultActionGroup getDefaultActionGroup() {
    DefaultActionGroup group = new DefaultActionGroup();
    group.add(ActionManager.getInstance().getAction("efluid.editorGroups.SwitchGroup"));
    group.add(ActionManager.getInstance().getAction("efluid.editorGroups.SwitchFile"));
    group.add(new Separator());
    group.add(ActionManager.getInstance().getAction("efluid.editorGroups.Refresh"));
    group.add(new Separator());
    group.add(ActionManager.getInstance().getAction("efluid.editorGroups.Next"));
    group.add(ActionManager.getInstance().getAction("efluid.editorGroups.Previous"));
    group.add(new Separator());
    group.add(ActionManager.getInstance().getAction("efluid.editorGroups.ReindexThisFile"));
    group.add(ActionManager.getInstance().getAction("efluid.editorGroups.Reindex"));
    group.add(new Separator());
    group.add(ActionManager.getInstance().getAction("efluid.editorGroups.ToggleAutoSameNameGroups"));
    group.add(ActionManager.getInstance().getAction("efluid.editorGroups.ToggleFolderEditorGroups"));
    group.add(ActionManager.getInstance().getAction("efluid.editorGroups.ToggleForce"));
    group.add(ActionManager.getInstance().getAction("efluid.editorGroups.ToggleHideEmpty"));
//		group.add(ActionManager.getInstance().getAction("efluid.editorGroups.ToggleShowSize"));
    group.add(new Separator());
    group.add(ActionManager.getInstance().getAction("efluid.editorGroups.OpenConfiguration"));
    return group;
  }
}
