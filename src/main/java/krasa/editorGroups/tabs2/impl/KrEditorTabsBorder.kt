package krasa.editorGroups.tabs2.impl

import com.intellij.openapi.rd.paint2DLine
import com.intellij.openapi.util.registry.Registry
import com.intellij.ui.ExperimentalUI
import com.intellij.ui.paint.LinePainter2D
import com.intellij.util.animation.Easing
import com.intellij.util.animation.JBAnimator
import com.intellij.util.animation.animation
import com.intellij.util.ui.JBUI
import krasa.editorGroups.tabs2.KrTabInfo
import krasa.editorGroups.tabs2.KrTabsBorder
import krasa.editorGroups.tabs2.KrTabsListener
import krasa.editorGroups.tabs2.KrTabsPosition
import java.awt.*

@Suppress("UnstableApiUsage")
class KrEditorTabsBorder(tabs: KrTabsImpl) : KrTabsBorder(tabs) {
  private val animator = JBAnimator()
  private var start: Int = -1
  private var end: Int = -1
  private var animationId = -1L

  init {
    tabs.addListener(object : KrTabsListener {
      override fun selectionChanged(oldSelection: KrTabInfo?, newSelection: KrTabInfo?) {
        val from = bounds(oldSelection) ?: return
        val to = bounds(newSelection) ?: return
        val dur = 100
        val del = 50
        val s1 = from.x
        val s2 = to.x
        val d1 = if (s1 > s2) 0 else del
        val e1 = from.x + from.width
        val e2 = to.x + to.width
        val d2 = if (e1 > e2) del else 0
        animationId = animator.animate(
          animation(s1, s2) {
            start = it
            tabs.component.repaint()
          }.apply {
            duration = dur - d1
            delay = d1
            easing = if (d1 != 0) Easing.EASE_OUT else Easing.LINEAR
          },
          animation(e1, e2) {
            end = it
            tabs.component.repaint()
          }.apply {
            duration = dur - d2
            delay = d2
            easing = if (d2 != 0) Easing.EASE_OUT else Easing.LINEAR
          }
        )
      }

      private fun bounds(tabInfo: KrTabInfo?): Rectangle? {
        return tabs.infoToLabel.get(tabInfo ?: return null)?.bounds
      }
    })
  }

  override val effectiveBorder: Insets
    get() = JBUI.insetsTop(thickness)

  override fun paintBorder(c: Component, g: Graphics, x: Int, y: Int, width: Int, height: Int) {
    g as Graphics2D

    if (ExperimentalUI.isNewUI()) {
      g.paint2DLine(Point(x, y), Point(x + width, y), LinePainter2D.StrokeType.INSIDE,
        thickness.toDouble(), JBUI.CurrentTheme.EditorTabs.borderColor())
    } else {
      tabs.tabPainter.paintBorderLine(g, thickness, Point(x, y), Point(x + width, y))
    }

    if (tabs.isEmptyVisible || tabs.isHideTabs) {
      return
    }

    val myInfo2Label = tabs.infoToLabel
    val firstLabel = myInfo2Label[tabs.getVisibleInfos()[0]] ?: return

    when (tabs.position) {
      KrTabsPosition.top -> {
        val startY = firstLabel.y - if (tabs.position == KrTabsPosition.bottom) 0 else thickness
        val startRow = if (ExperimentalUI.isNewUI()) 1 else 0
        val lastRow = tabs.lastLayoutPass!!.rowCount
        for (eachRow in startRow until lastRow) {
          val yl = (eachRow * tabs.headerFitSize!!.height) + startY
          tabs.tabPainter.paintBorderLine(g, thickness, Point(x, yl), Point(x + width, yl))
        }
        if (!ExperimentalUI.isNewUI() || (tabs as? KrEditorTabs)?.shouldPaintBottomBorder() == true) {
          val yl = lastRow * tabs.headerFitSize!!.height + startY
          tabs.tabPainter.paintBorderLine(g, thickness, Point(x, yl), Point(x + width, yl))
        }
      }

      KrTabsPosition.bottom -> {
        val rowCount = tabs.lastLayoutPass!!.rowCount
        for (rowInd in 0 until rowCount) {
          val curY = height - (rowInd + 1) * tabs.headerFitSize!!.height
          tabs.tabPainter.paintBorderLine(g, thickness, Point(x, curY), Point(x + width, curY))
        }
      }

      KrTabsPosition.right -> {
        val lx = firstLabel.x
        tabs.tabPainter.paintBorderLine(g, thickness, Point(lx, y), Point(lx, y + height))
      }

      KrTabsPosition.left -> {
        val bounds = firstLabel.bounds
        val i = bounds.x + bounds.width - thickness
        tabs.tabPainter.paintBorderLine(g, thickness, Point(i, y), Point(i, y + height))
      }
    }

    if (hasAnimation()) {
      tabs.tabPainter.paintUnderline(tabs.position, calcRectangle()
        ?: return, thickness, g, tabs.isActiveTabs(tabs.selectedInfo))
    } else {
      val selectedLabel = tabs.selectedLabel ?: return
      tabs.tabPainter.paintUnderline(tabs.position,
        selectedLabel.bounds,
        thickness,
        g,
        tabs.isActiveTabs(tabs.selectedInfo))
    }
  }


  private fun calcRectangle(): Rectangle? {
    val selectedLabel = tabs.selectedLabel ?: return null
    if (animator.isRunning(animationId)) {
      return Rectangle(start, selectedLabel.y, end - start, selectedLabel.height)
    }
    return selectedLabel.bounds
  }

  companion object {
    internal fun hasAnimation(): Boolean = Registry.`is`("ide.editor.tab.selection.animation", false)
  }
}