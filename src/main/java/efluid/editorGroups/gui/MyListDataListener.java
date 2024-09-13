package efluid.editorGroups.gui;


import efluid.editorGroups.model.RegexGroupModel;
import efluid.editorGroups.model.RegexGroupModels;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.List;

/**
 */
public class MyListDataListener implements ListDataListener {
  private final DefaultListModel<RegexGroupModel> model;
  private final RegexGroupModels models;

  public MyListDataListener(DefaultListModel<RegexGroupModel> model, RegexGroupModels models) {
    this.model = model;
    this.models = models;
  }

  @Override
  public void intervalAdded(ListDataEvent e) {
    listChanged();
  }

  @Override
  public void intervalRemoved(ListDataEvent e) {
    listChanged();
  }

  @Override
  public void contentsChanged(ListDataEvent e) {
    listChanged();
  }

  private void listChanged() {
    List<RegexGroupModel> list = this.models.getRegexGroupModels();
    list.clear();
    for (int i = 0; i < model.getSize(); i++) {
      list.add(model.getElementAt(i));
    }
  }
}
