package efluid.editorGroups.tabs2.impl

import efluid.editorGroups.tabs2.KrTabPainter
import efluid.editorGroups.tabs2.impl.themes.KrTabTheme
import java.awt.Graphics


interface KrTabPainterAdapter {
    fun paintBackground(label: KrTabLabel, g: Graphics, tabs: KrTabsImpl)
    val tabPainter: KrTabPainter
    fun getTabTheme(): KrTabTheme = tabPainter.getTabTheme()
}
