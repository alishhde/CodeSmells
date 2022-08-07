public final class CSelectionCriteriumPanel extends JPanel {
 /**
   * The criterium edited in this panel.
   */
 private final CSelectionCriterium m_criterium;


 /**
   * Allows the user to select the selection state.
   */
 private final JComboBox<SelectionState> selectionStateBox = new JComboBox<>();


 /**
   * Updates the GUI on user input.
   */
 private final InternalComboboxListener selectionStateBoxListener = new InternalComboboxListener();


 /**
   * Creates a new panel object.
   *
   * @param criterium The criterium edited in this panel.
   */
 public CSelectionCriteriumPanel(final CSelectionCriterium criterium) {
 super(new BorderLayout());
 m_criterium = criterium;
 selectionStateBox.addActionListener(selectionStateBoxListener);
 initPanel();
  }


 /**
   * Creates the GUI of the panel.
   */
 private void initPanel() {
 final JPanel mainPanel = new JPanel(new BorderLayout());
 mainPanel.setBorder(new TitledBorder("Edit Selection Condition"));


 final JPanel comboPanel = new JPanel(new BorderLayout());
 comboPanel.setBorder(new EmptyBorder(5, 5, 5, 5));


 selectionStateBox.addItem(SelectionState.SELECTED);
 selectionStateBox.addItem(SelectionState.UNSELECTED);


 comboPanel.add(selectionStateBox, BorderLayout.CENTER);


 mainPanel.add(comboPanel, BorderLayout.NORTH);


 add(mainPanel, BorderLayout.CENTER);
  }


 /**
   * Frees allocated resources.
   */
 public void dispose() {
 selectionStateBox.removeActionListener(selectionStateBoxListener);
  }


 /**
   * Returns the selection state selected by the user.
   *
   * @return The selection state selected by the user.
   */
 public SelectionState getSelectionState() {
 return (SelectionState) selectionStateBox.getSelectedItem();
  }


 /**
   * Updates the GUI on user input.
   */
 private class InternalComboboxListener implements ActionListener {
 @Override
 public void actionPerformed(final ActionEvent event) {
 m_criterium.notifyListeners();
    }
  }
}