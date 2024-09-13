/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package efluid.editorGroups.tabs2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeSupport;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.*;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.ui.Queryable;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.reference.SoftReference;
import com.intellij.ui.ClientProperty;
import com.intellij.ui.PlaceProvider;
import com.intellij.ui.SimpleColoredText;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.content.AlertIcon;
import com.intellij.util.ui.JBUI;
import efluid.editorGroups.tabs2.impl.KrTabsImpl;

public class KrTabInfo implements Queryable, PlaceProvider {

  public static final String ACTION_GROUP = "actionGroup";
  public static final String ICON = "icon";
  public static final String TAB_COLOR = "color";
  public static final String COMPONENT = "component";
  public static final String TEXT = "text";
  public static final String TAB_ACTION_GROUP = "tabActionGroup";
  public static final String ALERT_ICON = "alertIcon";

  public static final String ALERT_STATUS = "alertStatus";
  public static final String HIDDEN = "hidden";
  public static final String ENABLED = "enabled";

  private JComponent myComponent;
  private JComponent myPreferredFocusableComponent;

  private ActionGroup myGroup;

  private final PropertyChangeSupport myChangeSupport = new PropertyChangeSupport(this);

  private Icon myIcon;
  private @NonNls String myPlace;
  private Object myObject;
  private JComponent mySideComponent;
  private Reference<JComponent> myLastFocusOwner;


  private ActionGroup myTabLabelActions;
  private @Nullable ActionGroup myTabPaneActions;
  private String myTabActionPlace;

  private AlertIcon myAlertIcon;

  private int myBlinkCount;
  private boolean myAlertRequested;
  private boolean myHidden;
  private JComponent myActionsContextComponent;

  private final SimpleColoredText myText = new SimpleColoredText();
  private @NlsContexts.Tooltip String myTooltipText;

  private int myDefaultStyle = -1;
  private Color myDefaultForeground;
  private TextAttributes editorAttributes;
  private SimpleTextAttributes myDefaultAttributes;
  private static final AlertIcon DEFAULT_ALERT_ICON = new AlertIcon(AllIcons.Nodes.TabAlert, 0, -JBUI.scale(6));

  private boolean myEnabled = true;
  private Color myTabColor;

  private Queryable myQueryable;
  private KrTabInfo.DragOutDelegate myDragOutDelegate;
  private KrTabInfo.DragDelegate myDragDelegate;

  /**
   * The tab which was selected before the mouse was pressed on this tab. Focus will be transferred to that tab if this tab is dragged
   * out of its container. (IDEA-61536)
   */
  private WeakReference<KrTabInfo> myPreviousSelection = new WeakReference<>(null);

  public KrTabInfo(final JComponent component) {
    myComponent = component;
    myPreferredFocusableComponent = component;
  }

  public @NotNull PropertyChangeSupport getChangeSupport() {
    return myChangeSupport;
  }

  public @NotNull KrTabInfo setText(@NlsContexts.TabTitle @NotNull String text) {
    List<SimpleTextAttributes> attributes = myText.getAttributes();
    TextAttributes textAttributes = attributes.size() == 1 ? attributes.get(0).toTextAttributes() : null;
    SimpleTextAttributes defaultAttributes = getDefaultAttributes();
    if (!myText.toString().equals(text) || !Comparing.equal(textAttributes, defaultAttributes.toTextAttributes())) {
      clearText(false);
      append(text, defaultAttributes);
    }
    return this;
  }

  private @NotNull SimpleTextAttributes getDefaultAttributes() {
    if (myDefaultAttributes == null) {
      int style = (myDefaultStyle != -1 ? myDefaultStyle : SimpleTextAttributes.STYLE_PLAIN)
                  | SimpleTextAttributes.STYLE_USE_EFFECT_COLOR;
      if (editorAttributes != null) {
        SimpleTextAttributes attr = SimpleTextAttributes.fromTextAttributes(editorAttributes);
        attr = SimpleTextAttributes.merge(new SimpleTextAttributes(style, myDefaultForeground), attr);
        myDefaultAttributes = attr;
      } else {
        myDefaultAttributes = new SimpleTextAttributes(style, myDefaultForeground);
      }
    }
    return myDefaultAttributes;
  }

  public @NotNull KrTabInfo clearText(final boolean invalidate) {
    final String old = myText.toString();
    myText.clear();
    if (invalidate) {
      myChangeSupport.firePropertyChange(TEXT, old, myText.toString());
    }
    return this;
  }

  public @NotNull KrTabInfo append(@NotNull @NlsContexts.Label String fragment, @NotNull SimpleTextAttributes attributes) {
    final String old = myText.toString();
    myText.append(fragment, attributes);
    myChangeSupport.firePropertyChange(TEXT, old, myText.toString());
    return this;
  }

  public @NotNull KrTabInfo setIcon(Icon icon) {
    Icon old = myIcon;
    if (!Objects.equals(old, icon)) {
      myIcon = icon;
      myChangeSupport.firePropertyChange(ICON, old, icon);
    }
    return this;
  }

  public @NotNull KrTabInfo setComponent(Component c) {
    if (myComponent != c) {
      JComponent old = myComponent;
      myComponent = (JComponent) c;
      myChangeSupport.firePropertyChange(COMPONENT, old, myComponent);
    }
    return this;
  }

  public ActionGroup getGroup() {
    return myGroup;
  }

  public JComponent getComponent() {
    return myComponent;
  }

  public boolean isPinned() {
    return ClientProperty.isTrue(getComponent(), KrTabsImpl.PINNED);
  }

  public @NotNull @NlsContexts.TabTitle String getText() {
    return myText.toString();
  }

  public @NotNull SimpleColoredText getColoredText() {
    return myText;
  }

  public Icon getIcon() {
    return myIcon;
  }

  @Override
  public String getPlace() {
    return myPlace;
  }

  public @NotNull KrTabInfo setSideComponent(JComponent comp) {
    mySideComponent = comp;
    return this;
  }

  public JComponent getSideComponent() {
    return mySideComponent;
  }

  public @NotNull KrTabInfo setActions(ActionGroup group, @NonNls String place) {
    ActionGroup old = myGroup;
    myGroup = group;
    myPlace = place;
    myChangeSupport.firePropertyChange(ACTION_GROUP, old, myGroup);
    return this;
  }

  public @NotNull KrTabInfo setActionsContextComponent(JComponent c) {
    myActionsContextComponent = c;
    return this;
  }

  public JComponent getActionsContextComponent() {
    return myActionsContextComponent;
  }

  public @NotNull KrTabInfo setObject(final Object object) {
    myObject = object;
    return this;
  }

  public Object getObject() {
    return myObject;
  }

  public JComponent getPreferredFocusableComponent() {
    return myPreferredFocusableComponent != null ? myPreferredFocusableComponent : myComponent;
  }

  public @NotNull KrTabInfo setPreferredFocusableComponent(final JComponent component) {
    myPreferredFocusableComponent = component;
    return this;
  }

  public void setLastFocusOwner(final JComponent owner) {
    myLastFocusOwner = owner == null ? null : new WeakReference<>(owner);
  }

  public ActionGroup getTabLabelActions() {
    return myTabLabelActions;
  }

  public String getTabActionPlace() {
    return myTabActionPlace;
  }

  public @NotNull KrTabInfo setTabLabelActions(final ActionGroup tabActions, @NotNull String place) {
    ActionGroup old = myTabLabelActions;
    myTabLabelActions = tabActions;
    myTabActionPlace = place;
    myChangeSupport.firePropertyChange(TAB_ACTION_GROUP, old, myTabLabelActions);
    return this;
  }

  public @Nullable ActionGroup getTabPaneActions() {
    return myTabPaneActions;
  }

  /**
   * Sets the actions that will be displayed on the right side of the tabs
   */
  public @NotNull KrTabInfo setTabPaneActions(final @Nullable ActionGroup tabPaneActions) {
    myTabPaneActions = tabPaneActions;
    return this;
  }

  public @Nullable JComponent getLastFocusOwner() {
    return SoftReference.dereference(myLastFocusOwner);
  }

  public @NotNull KrTabInfo setAlertIcon(final AlertIcon alertIcon) {
    AlertIcon old = myAlertIcon;
    myAlertIcon = alertIcon;
    myChangeSupport.firePropertyChange(ALERT_ICON, old, myAlertIcon);
    return this;
  }

  public void fireAlert() {
    myAlertRequested = true;
    myChangeSupport.firePropertyChange(ALERT_STATUS, null, true);
  }

  public void stopAlerting() {
    myAlertRequested = false;
    myChangeSupport.firePropertyChange(ALERT_STATUS, null, false);
  }

  public int getBlinkCount() {
    return myBlinkCount;
  }

  public void setBlinkCount(final int blinkCount) {
    myBlinkCount = blinkCount;
  }

  @Override
  public String toString() {
    return getText();
  }

  public @NotNull AlertIcon getAlertIcon() {
    return myAlertIcon == null ? DEFAULT_ALERT_ICON : myAlertIcon;
  }

  public void resetAlertRequest() {
    myAlertRequested = false;
  }

  public boolean isAlertRequested() {
    return myAlertRequested;
  }

  public void setHidden(boolean hidden) {
    boolean old = myHidden;
    myHidden = hidden;
    myChangeSupport.firePropertyChange(HIDDEN, old, myHidden);
  }

  public boolean isHidden() {
    return myHidden;
  }

  public void setEnabled(boolean enabled) {
    boolean old = myEnabled;
    myEnabled = enabled;
    myChangeSupport.firePropertyChange(ENABLED, old, myEnabled);
  }

  public boolean isEnabled() {
    return myEnabled;
  }

  public @NotNull KrTabInfo setDefaultStyle(@SimpleTextAttributes.StyleAttributeConstant int style) {
    myDefaultStyle = style;
    myDefaultAttributes = null;
    update();
    return this;
  }

  public @NotNull KrTabInfo setDefaultForeground(final Color fg) {
    myDefaultForeground = fg;
    myDefaultAttributes = null;
    update();
    return this;
  }

  public Color getDefaultForeground() {
    return myDefaultForeground;
  }

  public @NotNull KrTabInfo setDefaultAttributes(@Nullable TextAttributes attributes) {
    editorAttributes = attributes;
    myDefaultAttributes = null;
    update();
    return this;
  }


  private void update() {
    setText(getText());
  }

  public void revalidate() {
    myDefaultAttributes = null;
    update();
  }

  public @NotNull KrTabInfo setTooltipText(@NlsContexts.Tooltip String text) {
    String old = myTooltipText;
    if (!Objects.equals(old, text)) {
      myTooltipText = text;
      myChangeSupport.firePropertyChange(TEXT, old, myTooltipText);
    }
    return this;
  }

  public @NlsContexts.Tooltip String getTooltipText() {
    return myTooltipText;
  }

  public @NotNull KrTabInfo setTabColor(Color color) {
    Color old = myTabColor;
    if (!Comparing.equal(color, old)) {
      myTabColor = color;
      myChangeSupport.firePropertyChange(TAB_COLOR, old, color);
    }
    return this;
  }

  public Color getTabColor() {
    return myTabColor;
  }

  public @NotNull KrTabInfo setTestableUi(Queryable queryable) {
    myQueryable = queryable;
    return this;
  }

  @Override
  public void putInfo(@NotNull Map<? super String, ? super String> info) {
    if (myQueryable != null) {
      myQueryable.putInfo(info);
    }
  }

  public @NotNull KrTabInfo setDragOutDelegate(KrTabInfo.DragOutDelegate delegate) {
    myDragOutDelegate = delegate;
    return this;
  }

  public boolean canBeDraggedOut() {
    return myDragOutDelegate != null;
  }

  public KrTabInfo.DragOutDelegate getDragOutDelegate() {
    return myDragOutDelegate;
  }

  public void setPreviousSelection(@Nullable KrTabInfo previousSelection) {
    myPreviousSelection = new WeakReference<>(previousSelection);
  }

  public KrTabInfo.DragDelegate getDragDelegate() {
    return myDragDelegate;
  }

  public void setDragDelegate(KrTabInfo.DragDelegate dragDelegate) {
    myDragDelegate = dragDelegate;
  }

  public @Nullable KrTabInfo getPreviousSelection() {
    return myPreviousSelection.get();
  }

  public interface DragDelegate {

    void dragStarted(@NotNull MouseEvent mouseEvent);

    void dragFinishedOrCanceled();
  }

  public interface DragOutDelegate {

    void dragOutStarted(@NotNull MouseEvent mouseEvent, @NotNull KrTabInfo info);

    void processDragOut(@NotNull MouseEvent event, @NotNull KrTabInfo source);

    void dragOutFinished(@NotNull MouseEvent event, KrTabInfo source);

    void dragOutCancelled(KrTabInfo source);
  }

}
