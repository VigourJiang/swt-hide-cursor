package com.company;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.awt.*;

public class CursorHideControl {

  public static void main(String[] args) {
    Display display = new Display();

    Shell shell = new Shell(display, SWT.NO_TRIM | SWT.ON_TOP);
    Shell shell2 = new Shell(display, SWT.NO_TRIM | SWT.ON_TOP);
    shell.setBackground(display.getSystemColor(SWT.COLOR_CYAN));
    shell2.setBackground(display.getSystemColor(SWT.COLOR_DARK_RED));

    {
      shell.setBounds(0, 0, 200, 200);

      // create a cursor with a transparent image
      Color white = display.getSystemColor(SWT.COLOR_WHITE);
      Color black = display.getSystemColor(SWT.COLOR_BLACK);
      PaletteData palette = new PaletteData(new RGB[]{white.getRGB(), black.getRGB()});
      ImageData sourceData = new ImageData(16, 16, 1, palette);
      sourceData.transparentPixel = 0;
      Cursor cursor = new Cursor(display, sourceData, 0, 0);
      shell.setCursor(cursor);

      shell.addMouseMoveListener(new MouseMoveListener() {
        @Override
        public void mouseMove(MouseEvent mouseEvent) {
          int x = mouseEvent.x + shell.getLocation().x;
          int y = mouseEvent.y + shell.getLocation().y;
          x = Math.max(x, shell2.getLocation().x);

          try {
            Robot r = new Robot();
            r.mouseMove(x, y);
          } catch (AWTException e) {
            e.printStackTrace();
          }
        }
      });
      shell.open();
    }

    {
      shell2.setBounds(200, 0, 300, 300);
      shell2.open();
    }

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
  }
}