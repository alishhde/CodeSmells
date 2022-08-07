@Singleton
public class GoIntoAction extends ProjectAction {


 private final ProjectExplorerPresenter projectExplorer;
 private final CoreLocalizationConstant localizationConstant;


 @Inject
 public GoIntoAction(
 ProjectExplorerPresenter projectExplorer, CoreLocalizationConstant localizationConstant) {
 super(localizationConstant.goIntoActionText());


 this.projectExplorer = projectExplorer;
 this.localizationConstant = localizationConstant;
  }


 /** {@inheritDoc} */
 @Override
 protected void updateProjectAction(ActionEvent e) {
 if (projectExplorer.isGoIntoActivated()) {
 e.getPresentation().setText(localizationConstant.goBackActionText());
 e.getPresentation().setEnabledAndVisible(true);
 return;
    }


 e.getPresentation().setText(localizationConstant.goIntoActionText());


 List<?> selection = projectExplorer.getSelection().getAllElements();


 e.getPresentation()
        .setEnabledAndVisible(
            !projectExplorer.isGoIntoActivated()
                && selection.size() == 1
                && isNodeSupportGoInto(selection.get(0)));
  }


 /** {@inheritDoc} */
 @Override
 public void actionPerformed(ActionEvent e) {
 if (projectExplorer.isGoIntoActivated()) {
 projectExplorer.goBack();
 return;
    }


 List<?> selection = projectExplorer.getSelection().getAllElements();


 if (selection.isEmpty() || selection.size() > 1) {
 throw new IllegalArgumentException("Node isn't selected");
    }


 Object node = selection.get(0);


 if (isNodeSupportGoInto(node)) {
 projectExplorer.goInto((Node) node);
    }
  }


 private boolean isNodeSupportGoInto(Object node) {
 return node instanceof Node && ((Node) node).supportGoInto();
  }
}