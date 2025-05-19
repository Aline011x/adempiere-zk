package org.adempiere.webui.controller;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.theme.ThemeManager;
import org.adempiere.webui.window.LoginWindow;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

public class LoginController extends SelectorComposer<Component> {
    @Wire
    private Borderlayout mainLayout;
    @Wire
    private North northRegion;
    @Wire
    private South southRegion;
    @Wire
    private West westRegion;
    @Wire
    private East eastRegion;
    @Wire
    private LoginWindow loginWin;

    private Window browserWarningWindow;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        loadThemePanels();
        checkBrowserSupport();
    }

    private void loadThemePanels() {
        loadPanel(northRegion, ThemeManager.getLoginTopPanel());
        loadPanel(southRegion, ThemeManager.getLoginBottomPanel());
        loadPanel(westRegion, ThemeManager.getLoginLeftPanel());
        loadPanel(eastRegion, ThemeManager.getLoginRightPanel());
    }

    private void loadPanel(Component parent, String zulPath) {
        try {
            if (zulPath != null) {
                Executions.createComponents(zulPath, parent, null);
            }
        } catch (UiException e) {
            if (!e.getMessage().startsWith("Page not found")) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkBrowserSupport() {
        if (!AEnv.isBrowserSupported()) {
            String msg = "Browser no soportado. Use Firefox, Chrome o Safari.";
            browserWarningWindow = new Window();
            browserWarningWindow.setPosition("top,right");
            browserWarningWindow.setWidth("550px");
            Div div = new Div();
            div.setStyle("font-size: 9pt; padding: 10px;");
            div.appendChild(new Label(msg));
            browserWarningWindow.appendChild(div);
            browserWarningWindow.setParent(mainLayout.getPage());
            browserWarningWindow.doOverlapped();
        }
    }

    // Métodos de delegación para LoginWindow
    public String getTypedPassword() {
        return loginWin.getTypedPassword();
    }

    public void cleanup() {
        if (browserWarningWindow != null) {
            browserWarningWindow.detach();
        }
        mainLayout.detach();
    }
}