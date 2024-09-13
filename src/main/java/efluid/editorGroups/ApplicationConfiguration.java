package efluid.editorGroups;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.JBColor;
import com.intellij.util.xmlb.annotations.Transient;
import efluid.editorGroups.model.RegexGroupModels;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class ApplicationConfiguration {
  private static final Logger LOG = Logger.getInstance(ApplicationConfiguration.class);

  private Tabs tabs = new Tabs();
  private RegexGroupModels regexGroupModels = new RegexGroupModels();

  private boolean selectRegexGroup = true;
  private boolean autoFolders = true;
  private boolean autoSameName = true;
  private boolean forceSwitch = true;
  private boolean hideEmpty = true;
  private boolean showSize;
  private boolean continuousScrolling;
  private boolean initializeSynchronously = false;
  private boolean indexOnlyEditorGroupsFiles;
  private boolean excludeEditorGroupsFiles;
  private boolean compactTabs;
  private Integer tabBgColor;
  private boolean tabBgColorEnabled;
  private Integer tabFgColor;
  private boolean tabFgColorEnabled;
  private boolean rememberLastGroup = true;
  private boolean groupSwitchGroupAction = false;
  private boolean showPanel = true;
  private int groupSizeLimit = 10000;
  private int tabSizeLimit = 50;

  public static ApplicationConfiguration state() {
    return ApplicationConfigurationComponent.getInstance().getState();
  }

  public RegexGroupModels getRegexGroupModels() {
    return regexGroupModels;
  }

  public void setRegexGroupModels(RegexGroupModels regexGroupModels) {
    this.regexGroupModels = regexGroupModels;
  }

  public boolean isShowPanel() {
    return showPanel;
  }

  public void setShowPanel(boolean showPanel) {
    this.showPanel = showPanel;
  }

  public Tabs getTabs() {
    return tabs;
  }

  public void setTabs(Tabs tabs) {
    this.tabs = tabs;
  }

  public boolean isAutoFolders() {
    return autoFolders;
  }

  public void setAutoFolders(boolean autoFolders) {
    this.autoFolders = autoFolders;
  }

  public boolean isAutoSameName() {
    return autoSameName;
  }

  public void setAutoSameName(boolean autoSameName) {
    this.autoSameName = autoSameName;
  }

  public boolean isForceSwitch() {
    return forceSwitch;
  }

  public void setForceSwitch(boolean forceSwitch) {
    this.forceSwitch = forceSwitch;
  }

  public boolean isHideEmpty() {
    return hideEmpty;
  }

  public void setHideEmpty(boolean hideEmpty) {
    this.hideEmpty = hideEmpty;
  }

  public boolean isShowSize() {
    return showSize;
  }

  public void setShowSize(boolean showSize) {
    this.showSize = showSize;
  }


  public boolean isContinuousScrolling() {
    return continuousScrolling;
  }

  public void setContinuousScrolling(final boolean continuousScrolling) {
    this.continuousScrolling = continuousScrolling;
  }

  public boolean isInitializeSynchronously() {
    return initializeSynchronously;
  }

  public void setInitializeSynchronously(final boolean initializeSynchronously) {
    this.initializeSynchronously = initializeSynchronously;
  }

  public boolean isIndexOnlyEditorGroupsFiles() {
    return indexOnlyEditorGroupsFiles;
  }

  public void setIndexOnlyEditorGroupsFiles(final boolean indexOnlyEditorGroupsFiles) {
    this.indexOnlyEditorGroupsFiles = indexOnlyEditorGroupsFiles;
  }

  public boolean isExcludeEditorGroupsFiles() {
    return excludeEditorGroupsFiles;
  }

  public void setExcludeEditorGroupsFiles(final boolean excludeEditorGroupsFiles) {
    this.excludeEditorGroupsFiles = excludeEditorGroupsFiles;
  }

  public boolean isCompactTabs() {
    return compactTabs;
  }

  public void setCompactTabs(final boolean compactTabs) {
    this.compactTabs = compactTabs;
  }

  public void setTabBgColor(Integer tabBgColor) {
    this.tabBgColor = tabBgColor;
  }

  public void setTabFgColor(Integer tabFgColor) {
    this.tabFgColor = tabFgColor;
  }

  public Integer getTabBgColor() {
    return tabBgColor;
  }

  public Integer getTabFgColor() {
    return tabFgColor;
  }


  public boolean isTabBgColorEnabled() {
    return tabBgColorEnabled;
  }

  public void setTabBgColorEnabled(boolean tabBgColorEnabled) {
    this.tabBgColorEnabled = tabBgColorEnabled;
  }

  public boolean isTabFgColorEnabled() {
    return tabFgColorEnabled;
  }

  public void setTabFgColorEnabled(boolean tabFgColorEnabled) {
    this.tabFgColorEnabled = tabFgColorEnabled;
  }

  @Transient
  public Color getTabBgColorAsAWT() {
    return asAWT(tabBgColor);
  }

  @Transient
  public void setTabBgColorAWT(Color color) {
    if (color != null) {
      this.tabBgColor = color.getRGB();
    }
  }

  @Transient
  public Color getTabFgColorAsAWT() {
    return asAWT(tabFgColor);
  }

  @Transient
  public void setTabFgColorAWT(Color color) {
    if (color != null) {
      this.tabFgColor = color.getRGB();
    }
  }

  public boolean isRememberLastGroup() {
    return rememberLastGroup;
  }

  public void setRememberLastGroup(final boolean rememberLastGroup) {
    this.rememberLastGroup = rememberLastGroup;
  }

  public boolean isGroupSwitchGroupAction() {
    return groupSwitchGroupAction;
  }

  public void setGroupSwitchGroupAction(final boolean groupSwitchGroupAction) {
    this.groupSwitchGroupAction = groupSwitchGroupAction;
  }

  public boolean isSelectRegexGroup() {
    return selectRegexGroup;
  }

  public void setSelectRegexGroup(final boolean selectRegexGroup) {
    this.selectRegexGroup = selectRegexGroup;
  }

  @Transient
  public String getGroupSizeLimit() {
    return String.valueOf(groupSizeLimit);
  }

  @Transient
  public void setGroupSizeLimit(final String groupSizeLimit) {
    this.groupSizeLimit = Integer.parseInt(groupSizeLimit);
  }

  public int getGroupSizeLimitInt() {
    return groupSizeLimit;
  }

  public void setGroupSizeLimitInt(final int groupSizeLimit) {
    this.groupSizeLimit = groupSizeLimit;
  }

  public int getTabSizeLimitInt() {
    return tabSizeLimit;
  }

  public void setTabSizeLimitInt(final int tabSizeLimit) {
    this.tabSizeLimit = tabSizeLimit;
  }

  @Transient
  public String getTabSizeLimit() {
    return String.valueOf(tabSizeLimit);
  }

  @Transient
  public void setTabSizeLimit(final String tabSizeLimit) {
    this.tabSizeLimit = Integer.parseInt(tabSizeLimit);
  }


  public static class Tabs {
    public static final Color DEFAULT_MASK = new JBColor(new Color(0x262626), new Color(0x262626));
    public static final int DEFAULT_OPACITY = 20;
    public static final Color DEFAULT_TAB_COLOR = JBColor.WHITE;

    public static final Color DEFAULT_DARCULA_MASK = new JBColor(new Color(0x262626), new Color(0x262626));
    public static final int DEFAULT_DARCULA_OPACITY = 50;
    public static final Color DEFAULT_DARCULA_TAB_COLOR = new JBColor(new Color(0x515658), new Color(0x262626));


    private boolean patchPainter;

    private Integer mask = DEFAULT_MASK.getRGB();
    private int opacity = DEFAULT_OPACITY;

    private Integer darcula_mask = DEFAULT_DARCULA_MASK.getRGB();
    private int darcula_opacity = DEFAULT_DARCULA_OPACITY;

    public Integer getMask() {
      return mask;
    }

    public void setMask(Integer mask) {
      this.mask = mask;
    }

    public int getOpacity() {
      return opacity;
    }

    public void setOpacity(int opacity) {
      this.opacity = opacity;
    }

    public Integer getDarcula_mask() {
      return darcula_mask;
    }

    public void setDarcula_mask(Integer darcula_mask) {
      this.darcula_mask = darcula_mask;
    }

    public int getDarcula_opacity() {
      return darcula_opacity;
    }

    public void setDarcula_opacity(int darcula_opacity) {
      this.darcula_opacity = darcula_opacity;
    }

    public boolean isPatchPainter() {
      return patchPainter;
    }

    public void setPatchPainter(boolean patchPainter) {
      this.patchPainter = patchPainter;
    }

    @Transient
    public void setOpacity(String text) {
      try {
        opacity = Integer.parseInt(text);
        if (opacity > 100) {
          opacity = 100;
        } else if (opacity < 0) {
          opacity = 0;
        }
      } catch (Exception e) {
        opacity = DEFAULT_OPACITY;
      }
    }

    @Transient
    public void setDarcula_opacity(String text) {
      try {
        darcula_opacity = Integer.parseInt(text);
        if (darcula_opacity > 100) {
          darcula_opacity = 100;
        } else if (darcula_opacity < 0) {
          darcula_opacity = 0;
        }
      } catch (Exception e) {
        darcula_opacity = DEFAULT_DARCULA_OPACITY;
      }
    }
  }

  @Nullable
  private static Color asAWT(Integer color) {
    if (color == null) {
      return null;
    }
    return new JBColor(new Color(color), new Color(color));
  }

}
