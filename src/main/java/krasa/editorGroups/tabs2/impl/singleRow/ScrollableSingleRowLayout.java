// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package krasa.editorGroups.tabs2.impl.singleRow;

import krasa.editorGroups.tabs2.TabInfo;
import krasa.editorGroups.tabs2.impl.JBTabsImpl;
import krasa.editorGroups.tabs2.impl.TabLabel;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

/**
 * @author yole
 */
public class ScrollableSingleRowLayout extends SingleRowLayout {
  private int myScrollOffset = 0;
  private boolean myScrollSelectionInViewPending = false;

  public ScrollableSingleRowLayout(final JBTabsImpl tabs) {
    super(tabs);
  }

  @Override
  int getScrollOffset() {
    return myScrollOffset;
  }

  @Override
  public void scroll(int units) {
    myScrollOffset += units;
    clampScrollOffsetToBounds(myLastSingRowLayout);
  }

  @Override
  protected boolean checkLayoutLabels(krasa.editorGroups.tabs2.impl.singleRow.SingleRowPassInfo data) {
    if (myScrollSelectionInViewPending) {
      return true;
    }
    return super.checkLayoutLabels(data);
  }

  private void clampScrollOffsetToBounds(@Nullable krasa.editorGroups.tabs2.impl.singleRow.SingleRowPassInfo data) {
    if (data == null) {
      return;
    }
    if (data.requiredLength < data.toFitLength) {
      myScrollOffset = 0;
    } else {
      myScrollOffset = Math.max(0, Math.min(myScrollOffset, data.requiredLength - data.toFitLength + getStrategy().getMoreRectAxisSize()));
    }
  }

  @Override
  public void scrollSelectionInView() {
    myScrollSelectionInViewPending = true;
  }

  @Override
  public int getScrollUnitIncrement() {
    if (myLastSingRowLayout != null) {
      final List<TabInfo> visibleInfos = myLastSingRowLayout.myVisibleInfos;
      if (!visibleInfos.isEmpty()) {
        final TabInfo info = visibleInfos.get(0);
        return getStrategy().getScrollUnitIncrement(myTabs.myInfo2Label.get(info));
      }
    }
    return 0;
  }

  private void doScrollSelectionInView(krasa.editorGroups.tabs2.impl.singleRow.SingleRowPassInfo passInfo) {
    if (myTabs.isMouseInsideTabsArea()) {
      return;
    }
    int offset = -myScrollOffset;
    for (TabInfo info : passInfo.myVisibleInfos) {
      final int length = getRequiredLength(info);
      if (info == myTabs.getSelectedInfo()) {
        if (offset < 0) {
          scroll(offset);
        } else {
          final int maxLength = passInfo.toFitLength - getStrategy().getMoreRectAxisSize();
          if (offset + length > maxLength) {
            scroll(offset + length - maxLength);
          }
        }
        break;
      }
      offset += length;
    }
  }

  @Override
  protected void recomputeToLayout(krasa.editorGroups.tabs2.impl.singleRow.SingleRowPassInfo data) {
    TabInfo info = myTabs.getSelectedInfo();
    if (info != null && myScrollSelectionInViewPending) {
      TabLabel label = myTabs.getTabLabel(info);
      label.setActionPanelVisible(true);
    }
    calculateRequiredLength(data);
    clampScrollOffsetToBounds(data);
    if (myScrollSelectionInViewPending || myLastSingRowLayout == null || !data.layoutSize.equals(myLastSingRowLayout.layoutSize)) {
      myScrollSelectionInViewPending = false;
      doScrollSelectionInView(data);
      clampScrollOffsetToBounds(data);
    }
  }

  @Override
  protected void layoutMoreButton(krasa.editorGroups.tabs2.impl.singleRow.SingleRowPassInfo data) {
    if (data.requiredLength > data.toFitLength) {
      data.moreRect = getStrategy().getMoreRect(data);
    }
  }

  @Override
  protected boolean applyTabLayout(krasa.editorGroups.tabs2.impl.singleRow.SingleRowPassInfo data, TabLabel label, int length, int deltaToFit) {
    if (data.requiredLength > data.toFitLength) {
      length = getStrategy().getLengthIncrement(label.getPreferredSize());
      final int moreRectSize = getStrategy().getMoreRectAxisSize();
      if (data.position + length > data.toFitLength - moreRectSize) {
        final int clippedLength = getStrategy().drawPartialOverflowTabs()
          ? data.toFitLength - data.position - moreRectSize - 4 : 0;
        super.applyTabLayout(data, label, clippedLength, deltaToFit);
        label.setAlignmentToCenter(false);
        label.setActionPanelVisible(false);
        return false;
      }
    }
    label.setActionPanelVisible(true);
    return super.applyTabLayout(data, label, length, deltaToFit);
  }

  @Override
  public boolean isTabHidden(TabInfo tabInfo) {
    final TabLabel label = myTabs.myInfo2Label.get(tabInfo);
    final Rectangle bounds = label.getBounds();
    return getStrategy().getMinPosition(bounds) < -10 || bounds.isEmpty();
  }

  @Nullable
  @Override
  protected TabLabel findLastVisibleLabel(SingleRowPassInfo data) {
    int i = data.toLayout.size() - 1;
    while (i > 0) {
      final TabInfo info = data.toLayout.get(i);
      final TabLabel label = myTabs.myInfo2Label.get(info);
      if (!label.getBounds().isEmpty()) {
        return label;
      }
      i--;
    }
    return null;
  }


  public int getMyScrollOffset() {
    return myScrollOffset;
  }
}
