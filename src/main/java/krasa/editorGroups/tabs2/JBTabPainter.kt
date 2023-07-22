// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package krasa.editorGroups.tabs2

import krasa.editorGroups.tabs2.impl.JBDefaultTabPainter
import krasa.editorGroups.tabs2.impl.JBEditorTabPainter
import krasa.editorGroups.tabs2.impl.ToolWindowTabPainter
import krasa.editorGroups.tabs2.impl.themes.DebuggerTabTheme
import krasa.editorGroups.tabs2.impl.themes.TabTheme
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Rectangle

interface JBTabPainter {
  companion object {
    @JvmStatic
    val DEFAULT: JBTabPainter = JBDefaultTabPainter()

    @JvmStatic
    val EDITOR = JBEditorTabPainter()

    @JvmStatic
    val TOOL_WINDOW: JBTabPainter = ToolWindowTabPainter()

    @JvmStatic
    val DEBUGGER: JBTabPainter = JBDefaultTabPainter(DebuggerTabTheme())
  }

  fun getTabTheme(): TabTheme

  fun getBackgroundColor(): Color

  fun paintBorderLine(g: Graphics2D, thickness: Int, from: Point, to: Point)

  fun fillBackground(g: Graphics2D, rect: Rectangle)

  fun paintTab(position: JBTabsPosition,
               g: Graphics2D,
               bounds: Rectangle,
               borderThickness: Int,
               tabColor: Color?,
               hovered: Boolean)

  fun paintSelectedTab(position: JBTabsPosition,
                       g: Graphics2D,
                       rect: Rectangle,
                       borderThickness: Int,
                       tabColor: Color?,
                       active: Boolean,
                       hovered: Boolean,
                       singleTab: Boolean)
}