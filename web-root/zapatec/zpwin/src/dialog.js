// $Id: dialog.js 4519 2006-09-21 12:35:54Z slip $
/**
 *
 * Copyright (c) 2004-2006 by Zapatec, Inc.
 * http://www.zapatec.com
 * 1700 MLK Way, Berkeley, California,
 * 94709, U.S.A.
 * All rights reserved.
 */
Zapatec.dialogsPath = Zapatec.getPath("Zapatec.DialogsWidget");

// Include required scripts
Zapatec.Transport.include(Zapatec.dialogsPath + 'alert.js', "Zapatec.AlertWindow");
Zapatec.Transport.include(Zapatec.dialogsPath + 'confirm.js', "Zapatec.ConfirmWindow");
Zapatec.Transport.include(Zapatec.dialogsPath + 'generic-dialog.js', "Zapatec.DialogWindow");

