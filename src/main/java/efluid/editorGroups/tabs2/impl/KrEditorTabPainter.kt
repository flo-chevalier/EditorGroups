@file:Suppress("UnstableApiUsage")

package efluid.editorGroups.tabs2.impl

import com.intellij.ui.ExperimentalUI
import efluid.editorGroups.tabs2.KrTabsPosition
import efluid.editorGroups.tabs2.impl.themes.KrEditorTabTheme
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Rectangle

class KrEditorTabPainter : KrDefaultTabPainter(_root_ide_package_.efluid.editorGroups.tabs2.impl.themes.KrEditorTabTheme()) {
  fun paintLeftGap(position: _root_ide_package_.efluid.editorGroups.tabs2.KrTabsPosition, g: Graphics2D, rect: Rectangle, borderThickness: Int) {
    val maxY = rect.y + rect.height - borderThickness

    paintBorderLine(g, borderThickness, Point(rect.x, rect.y), Point(rect.x, maxY))
  }

  fun paintRightGap(position: _root_ide_package_.efluid.editorGroups.tabs2.KrTabsPosition, g: Graphics2D, rect: Rectangle, borderThickness: Int) {
    val maxX = rect.x + rect.width - borderThickness
    val maxY = rect.y + rect.height - borderThickness

    paintBorderLine(g, borderThickness, Point(maxX, rect.y), Point(maxX, maxY))
  }

  fun paintTopGap(position: _root_ide_package_.efluid.editorGroups.tabs2.KrTabsPosition, g: Graphics2D, rect: Rectangle, borderThickness: Int) {
    val maxX = rect.x + rect.width

    paintBorderLine(g, borderThickness, Point(rect.x, rect.y), Point(maxX, rect.y))
  }

  fun paintBottomGap(position: _root_ide_package_.efluid.editorGroups.tabs2.KrTabsPosition, g: Graphics2D, rect: Rectangle, borderThickness: Int) {
    val maxX = rect.x + rect.width - borderThickness
    val maxY = rect.y + rect.height - borderThickness

    paintBorderLine(g, borderThickness, Point(rect.x, maxY), Point(maxX, maxY))
  }

  override fun underlineRectangle(position: _root_ide_package_.efluid.editorGroups.tabs2.KrTabsPosition, rect: Rectangle, thickness: Int): Rectangle {
    return when (position) {
      _root_ide_package_.efluid.editorGroups.tabs2.KrTabsPosition.bottom -> Rectangle(rect.x, rect.y, rect.width, thickness)
      _root_ide_package_.efluid.editorGroups.tabs2.KrTabsPosition.left -> {
        if (ExperimentalUI.isNewUI()) {
          Rectangle(rect.x, rect.y, thickness, rect.height)
        } else Rectangle(rect.x + rect.width - thickness, rect.y, thickness, rect.height)
      }

      _root_ide_package_.efluid.editorGroups.tabs2.KrTabsPosition.right -> Rectangle(rect.x, rect.y, thickness, rect.height)
      else -> super.underlineRectangle(position, rect, thickness)
    }
  }
}