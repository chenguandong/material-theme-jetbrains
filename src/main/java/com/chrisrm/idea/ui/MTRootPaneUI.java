/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Chris Magnussen and Elior Boukhobza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 */

package com.chrisrm.idea.ui;

import com.chrisrm.idea.MTConfig;
import com.intellij.ide.ui.laf.darcula.ui.DarculaRootPaneUI;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.registry.Registry;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicRootPaneUI;

/**
 * Created by chris on 26/03/16.
 */
public final class MTRootPaneUI extends DarculaRootPaneUI {
  public static ComponentUI createUI(final JComponent c) {
    return isCustomDecoration() ? new MTRootPaneUI() : createDefaultWindowsRootPaneUI();
  }

  private static boolean isCustomDecoration() {
    return SystemInfo.isMac;
    //    return Registry.is("ide.win.frame.decoration");
  }

  private static ComponentUI createDefaultWindowsRootPaneUI() {
    try {
      return (ComponentUI) Class.forName("com.sun.java.swing.plaf.windows.WindowsRootPaneUI").newInstance();
    } catch (final Exception e) {
      return new BasicRootPaneUI();
    }
  }

  @Override
  public void installUI(final JComponent c) {
    super.installUI(c);
    final boolean themeIsDark = MTConfig.getInstance().getSelectedTheme().getThemeIsDark();
    final boolean darkTitleBar = MTConfig.getInstance().isDarkTitleBar();
    final boolean allowDarkWindowDecorations = Registry.get("ide.mac.allowDarkWindowDecorations").asBoolean();

    if (SystemInfo.isMac) {
      if (darkTitleBar) {
        c.putClientProperty("jetbrains.awt.windowDarkAppearance", themeIsDark);
        c.putClientProperty("jetbrains.awt.transparentTitleBarAppearance", true);
      } else {
        c.putClientProperty("jetbrains.awt.windowDarkAppearance", themeIsDark && allowDarkWindowDecorations);
        c.putClientProperty("jetbrains.awt.transparentTitleBarAppearance", false);
      }
    }
  }
}
