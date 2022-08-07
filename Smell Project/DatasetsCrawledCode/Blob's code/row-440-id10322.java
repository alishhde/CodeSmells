 static final class DemoControls extends CustomControls implements ActionListener, ChangeListener {


 TransformAnim demo;
 JSlider shapeSlider, stringSlider, imageSlider;
 Font font = new Font(Font.SERIF, Font.BOLD, 10);
 JToolBar toolbar;
 ButtonBorder buttonBorder = new ButtonBorder();


 @SuppressWarnings("LeakingThisInConstructor")
 public DemoControls(TransformAnim demo) {
 super(demo.name);
 this.demo = demo;
 setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
 add(Box.createVerticalStrut(5));


 JToolBar bar = new JToolBar(SwingConstants.VERTICAL);
 bar.setFloatable(false);
 shapeSlider = new JSlider(SwingConstants.HORIZONTAL,0,20,demo.numShapes);
 shapeSlider.addChangeListener(this);
 TitledBorder tb = new TitledBorder(new EtchedBorder());
 tb.setTitleFont(font);
 tb.setTitle(String.valueOf(demo.numShapes) + " Shapes");
 shapeSlider.setBorder(tb);
 shapeSlider.setOpaque(true);
 shapeSlider.setPreferredSize(new Dimension(80,44));
 bar.add(shapeSlider);
 bar.addSeparator();


 stringSlider = new JSlider(SwingConstants.HORIZONTAL,0,10,demo.numStrings);
 stringSlider.addChangeListener(this);
 tb = new TitledBorder(new EtchedBorder());
 tb.setTitleFont(font);
 tb.setTitle(String.valueOf(demo.numStrings) + " Strings");
 stringSlider.setBorder(tb);
 stringSlider.setOpaque(true);
 stringSlider.setPreferredSize(new Dimension(80,44));
 bar.add(stringSlider);
 bar.addSeparator();


 imageSlider = new JSlider(SwingConstants.HORIZONTAL,0,10,demo.numImages);
 imageSlider.addChangeListener(this);
 tb = new TitledBorder(new EtchedBorder());
 tb.setTitleFont(font);
 tb.setTitle(String.valueOf(demo.numImages) + " Images");
 imageSlider.setBorder(tb);
 imageSlider.setOpaque(true);
 imageSlider.setPreferredSize(new Dimension(80,44));
 bar.add(imageSlider);
 bar.addSeparator();
 add(bar);


 toolbar = new JToolBar();
 toolbar.setFloatable(false);
 addButton("T", "translate", demo.doTranslate);
 addButton("R", "rotate", demo.doRotate);
 addButton("SC", "scale", demo.doScale);
 addButton("SH", "shear", demo.doShear);
 add(toolbar);
        }




 public void addButton(String s, String tt, boolean state) {
 JToggleButton b = (JToggleButton) toolbar.add(new JToggleButton(s));
 b.setFont(font);
 b.setSelected(state);
 b.setToolTipText(tt);
 b.setFocusPainted(false);
 b.setBorder(buttonBorder);
 b.addActionListener(this);
        }




 @Override
 public void actionPerformed(ActionEvent e) {
 JToggleButton b = (JToggleButton) e.getSource();
 if (b.getText().equals("T")) {
 demo.doTranslate = b.isSelected();
            } else if (b.getText().equals("R")) {
 demo.doRotate = b.isSelected();
            } else if (b.getText().equals("SC")) {
 demo.doScale = b.isSelected();
            } else if (b.getText().equals("SH")) {
 demo.doShear = b.isSelected();
            }
 if (!demo.animating.running()) {
 demo.repaint();
            }
        }




 @Override
 public void stateChanged(ChangeEvent e) {
 JSlider slider = (JSlider) e.getSource();
 int value = slider.getValue();
 TitledBorder tb = (TitledBorder) slider.getBorder();
 if (slider.equals(shapeSlider)) {
 tb.setTitle(String.valueOf(value) + " Shapes");
 demo.setShapes(value);
            } else if (slider.equals(stringSlider)) {
 tb.setTitle(String.valueOf(value) + " Strings");
 demo.setStrings(value);
            } else if (slider.equals(imageSlider)) {
 tb.setTitle(String.valueOf(value) + " Images");
 demo.setImages(value);
            }
 if (!demo.animating.running()) {
 demo.repaint();
            }
 slider.repaint();
        }




 @Override
 public Dimension getPreferredSize() {
 return new Dimension(80,38);
        }




 @Override
 @SuppressWarnings("SleepWhileHoldingLock")
 public void run() {
 Thread me = Thread.currentThread();
 while (thread == me) {
 for (int i = 1; i < toolbar.getComponentCount(); i++) {
 try {
 Thread.sleep(4444);
                    } catch (InterruptedException e) { return; }
                    ((AbstractButton) toolbar.getComponentAtIndex(i)).doClick();
                }
            }
 thread = null;
        }
    } // End DemoControls