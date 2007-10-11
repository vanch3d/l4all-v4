// $Id: window.js 5928 2007-01-11 11:48:11Z slip $
/**
 * The Zapatec DHTML Window
 *
 * Copyright (c) 2004-2006 by Zapatec, Inc.
 * http://www.zapatec.com
 * 1700 MLK Way, Berkeley, California,
 * 94709, U.S.A.
 * All rights reserved.
 *
 * Windows Widget
 * $$
 *
 */

Zapatec.windowPath = Zapatec.getPath("Zapatec.WindowWidget");

// Include required scripts
Zapatec.Transport.include(Zapatec.zapatecPath + 'button.js', "Zapatec.Button");
Zapatec.Transport.include(Zapatec.zapatecPath + 'zpobjects.js');
Zapatec.Transport.include(Zapatec.zapatecPath + 'dom.js');
Zapatec.Transport.include(Zapatec.zapatecPath + 'movable.js', "Zapatec.Utils.Movable");
Zapatec.Transport.include(Zapatec.zapatecPath + 'draggable.js', "Zapatec.Utils.Draggable");
Zapatec.Transport.include(Zapatec.zapatecPath + 'sizable.js', "Zapatec.Utils.Sizable");
Zapatec.Transport.include(Zapatec.zapatecPath + 'resizable.js', "Zapatec.Utils.Resizable");
Zapatec.Transport.include(Zapatec.zapatecPath + 'indicator.js', "Zapatec.Indicator");
Zapatec.Transport.include(Zapatec.zapatecPath + 'pane.js', "Zapatec.Pane");

Zapatec.Transport.include(Zapatec.windowPath + 'window-core.js', "Zapatec.Window");
Zapatec.Transport.include(Zapatec.windowPath + 'window-setup.js');
Zapatec.Transport.include(Zapatec.windowPath + 'window-globals.js');
Zapatec.Transport.include(Zapatec.windowPath + 'window-states-modes.js');
Zapatec.Transport.include(Zapatec.windowPath + 'window-controlling.js');
Zapatec.Transport.include(Zapatec.windowPath + 'window-utils.js');
