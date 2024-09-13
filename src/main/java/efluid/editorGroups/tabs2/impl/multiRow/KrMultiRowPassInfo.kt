package efluid.editorGroups.tabs2.impl.multiRow

import efluid.editorGroups.tabs2.impl.KrTabsImpl
import java.awt.Rectangle


class KrMultiRowPassInfo(
    val tabs: KrTabsImpl,
    visibleInfos: List<_root_ide_package_.efluid.editorGroups.tabs2.KrTabInfo>,
    val toFitRec: Rectangle,
    val scrollOffset: Int
) : _root_ide_package_.efluid.editorGroups.tabs2.impl.KrLayoutPassInfo(visibleInfos) {
    val rows: MutableList<KrTabsRow> = mutableListOf()
    val lengths: MutableMap<_root_ide_package_.efluid.editorGroups.tabs2.KrTabInfo, Int> = HashMap()

    val rowHeight: Int
        get() = tabs.headerFitSize!!.height

    var tabsRectangle: Rectangle = Rectangle()
    var reqLength: Int = toFitRec.width - toFitRec.x
    var tabsLength: Int = reqLength

    override fun getRowCount(): Int = rows.size

    override fun getHeaderRectangle(): Rectangle = tabsRectangle.clone() as Rectangle

    override fun getRequiredLength(): Int = reqLength

    override fun getScrollExtent(): Int = tabsLength
}