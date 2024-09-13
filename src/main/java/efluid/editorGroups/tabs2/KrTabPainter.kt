package efluid.editorGroups.tabs2

import efluid.editorGroups.tabs2.impl.KrDefaultTabPainter
import efluid.editorGroups.tabs2.impl.KrEditorTabPainter
import efluid.editorGroups.tabs2.impl.KrToolWindowTabPainter
import efluid.editorGroups.tabs2.impl.themes.KrDebuggerTabTheme
import efluid.editorGroups.tabs2.impl.themes.KrTabTheme
import efluid.editorGroups.tabs2.my.EditorGroupsTabTheme
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Rectangle

interface KrTabPainter {
  companion object {
    @JvmStatic
    val DEFAULT: KrTabPainter = KrDefaultTabPainter(EditorGroupsTabTheme())

    @JvmStatic
    val EDITOR: KrEditorTabPainter = KrEditorTabPainter()

    @JvmStatic
    val TOOL_WINDOW: KrTabPainter = KrToolWindowTabPainter()

    @JvmStatic
    val DEBUGGER: KrTabPainter = KrDefaultTabPainter(KrDebuggerTabTheme())
  }

  fun getTabTheme(): _root_ide_package_.efluid.editorGroups.tabs2.impl.themes.KrTabTheme

  fun getBackgroundColor(): Color

  /** Color that should be painted on top of [KrTabTheme.background] */
  fun getCustomBackground(tabColor: Color?, selected: Boolean, active: Boolean, hovered: Boolean): Color? {
    return tabColor
  }

  fun paintBorderLine(g: Graphics2D, thickness: Int, from: Point, to: Point)

  fun fillBackground(g: Graphics2D, rect: Rectangle)

  fun paintTab(position: _root_ide_package_.efluid.editorGroups.tabs2.KrTabsPosition,
               g: Graphics2D,
               rect: Rectangle,
               borderThickness: Int,
               tabColor: Color?,
               active: Boolean,
               hovered: Boolean)

  fun paintSelectedTab(position: _root_ide_package_.efluid.editorGroups.tabs2.KrTabsPosition,
                       g: Graphics2D,
                       rect: Rectangle,
                       borderThickness: Int,
                       tabColor: Color?,
                       active: Boolean,
                       hovered: Boolean)

  fun paintUnderline(position: _root_ide_package_.efluid.editorGroups.tabs2.KrTabsPosition,
                     rect: Rectangle,
                     borderThickness: Int,
                     g: Graphics2D,
                     active: Boolean)
}