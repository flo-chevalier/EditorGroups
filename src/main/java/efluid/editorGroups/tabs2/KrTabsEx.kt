// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package efluid.editorGroups.tabs2

import com.intellij.openapi.actionSystem.DataKey
import org.intellij.lang.annotations.MagicConstant
import org.jetbrains.annotations.Nls
import javax.swing.Icon
import javax.swing.SwingConstants

/**
 * @author yole
 */
interface KrTabsEx : _root_ide_package_.efluid.editorGroups.tabs2.KrTabs {
  companion object {
    @JvmField
    val NAVIGATION_ACTIONS_KEY: DataKey<KrTabsEx> = DataKey.create("KrTabs")
  }

  val isEditorTabs: Boolean

  fun updateTabActions(validateNow: Boolean)

  fun addTabSilently(info: _root_ide_package_.efluid.editorGroups.tabs2.KrTabInfo, index: Int): _root_ide_package_.efluid.editorGroups.tabs2.KrTabInfo?

  fun removeTab(info: _root_ide_package_.efluid.editorGroups.tabs2.KrTabInfo, forcedSelectionTransfer: _root_ide_package_.efluid.editorGroups.tabs2.KrTabInfo?)

  fun getToSelectOnRemoveOf(info: _root_ide_package_.efluid.editorGroups.tabs2.KrTabInfo): _root_ide_package_.efluid.editorGroups.tabs2.KrTabInfo?

  fun sortTabs(comparator: Comparator<_root_ide_package_.efluid.editorGroups.tabs2.KrTabInfo>)

  val dropInfoIndex: Int

  @get:MagicConstant(intValues = [
    SwingConstants.TOP.toLong(),
    SwingConstants.LEFT.toLong(),
    SwingConstants.BOTTOM.toLong(),
    SwingConstants.RIGHT.toLong(),
    -1,
  ])
  val dropSide: Int

  val isEmptyVisible: Boolean

  fun setTitleProducer(titleProducer: (() -> Pair<Icon, @Nls String>)?)

  /**
   * true if tabs and top toolbar should be hidden from a view
   */
  var isHideTopPanel: Boolean
}
