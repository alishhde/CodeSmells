 @Override
 protected void onRender(Element parent, int index) {
 super.onRender(parent, index);
 setLayout(new FitLayout());
 setBorders(false);


 // init components
 initToolBar();
 initGrid();


 ContentPanel devicesBundlesPanel = new ContentPanel();
 devicesBundlesPanel.setBorders(false);
 devicesBundlesPanel.setBodyBorder(true);
 devicesBundlesPanel.setHeaderVisible(false);
 devicesBundlesPanel.setLayout(new FitLayout());
 devicesBundlesPanel.setScrollMode(Scroll.AUTO);
 devicesBundlesPanel.setTopComponent(toolBar);
 devicesBundlesPanel.add(grid);


 add(devicesBundlesPanel);
 initialized = true;
    }