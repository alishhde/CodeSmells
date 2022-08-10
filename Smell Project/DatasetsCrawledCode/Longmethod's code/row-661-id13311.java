 public void paintComponent(Graphics g)  {
 XPStyle xp = XPStyle.getXP();


 paintTitleBackground(g);


 String title = frame.getTitle();
 if (title != null) {
 boolean isSelected = frame.isSelected();
 Font oldFont = g.getFont();
 Font newFont = (titleFont != null) ? titleFont : getFont();
 g.setFont(newFont);


 // Center text vertically.
 FontMetrics fm = SwingUtilities2.getFontMetrics(frame, g, newFont);
 int baseline = (getHeight() + fm.getAscent() - fm.getLeading() -
 fm.getDescent()) / 2;


 Rectangle lastIconBounds = new Rectangle(0, 0, 0, 0);
 if (frame.isIconifiable()) {
 lastIconBounds = iconButton.getBounds();
            } else if (frame.isMaximizable()) {
 lastIconBounds = maxButton.getBounds();
            } else if (frame.isClosable()) {
 lastIconBounds = closeButton.getBounds();
            }


 int titleX;
 int titleW;
 int gap = 2;
 if (WindowsGraphicsUtils.isLeftToRight(frame)) {
 if (lastIconBounds.x == 0) { // There are no icons
 lastIconBounds.x = frame.getWidth() - frame.getInsets().right;
                }
 titleX = systemLabel.getX() + systemLabel.getWidth() + gap;
 if (xp != null) {
 titleX += 2;
                }
 titleW = lastIconBounds.x - titleX - gap;
            } else {
 if (lastIconBounds.x == 0) { // There are no icons
 lastIconBounds.x = frame.getInsets().left;
                }
 titleW = SwingUtilities2.stringWidth(frame, fm, title);
 int minTitleX = lastIconBounds.x + lastIconBounds.width + gap;
 if (xp != null) {
 minTitleX += 2;
                }
 int availableWidth = systemLabel.getX() - gap - minTitleX;
 if (availableWidth > titleW) {
 titleX = systemLabel.getX() - gap - titleW;
                } else {
 titleX = minTitleX;
 titleW = availableWidth;
                }
            }
 title = getTitle(frame.getTitle(), fm, titleW);


 if (xp != null) {
 String shadowType = null;
 if (isSelected) {
 shadowType = xp.getString(this, Part.WP_CAPTION,
 State.ACTIVE, Prop.TEXTSHADOWTYPE);
                }
 if ("single".equalsIgnoreCase(shadowType)) {
 Point shadowOffset = xp.getPoint(this, Part.WP_WINDOW, State.ACTIVE,
 Prop.TEXTSHADOWOFFSET);
 Color shadowColor  = xp.getColor(this, Part.WP_WINDOW, State.ACTIVE,
 Prop.TEXTSHADOWCOLOR, null);
 if (shadowOffset != null && shadowColor != null) {
 g.setColor(shadowColor);
 SwingUtilities2.drawString(frame, g, title,
 titleX + shadowOffset.x,
 baseline + shadowOffset.y);
                    }
                }
            }
 g.setColor(isSelected ? selectedTextColor : notSelectedTextColor);
 SwingUtilities2.drawString(frame, g, title, titleX, baseline);
 g.setFont(oldFont);
        }
    }