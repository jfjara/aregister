/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atransfer;

import atransfer.views.ATransferMainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author jfjara
 */
public class ObservadorCamposListener implements KeyListener {

    private ATransferMainWindow window;
    
    public void setWindow(ATransferMainWindow window) {
        this.window = window;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (window != null) {
            if (window.checkTextHost() && window.checkTextPassword() && window.checkTextPort() &&
                    window.checkTextSchema() && window.checkTextUser()) {
                window.activarBotonesBBDD(true);
            } else {
                window.activarBotonesBBDD(false);
            }
        }                
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (window != null) {
            if (window.checkTextHost() && window.checkTextPassword() && window.checkTextPort() &&
                    window.checkTextSchema() && window.checkTextUser()) {
                window.activarBotonesBBDD(true);
            } else {
                window.activarBotonesBBDD(false);
            }
        }                
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (window != null) {
            if (window.checkTextHost() && window.checkTextPassword() && window.checkTextPort() &&
                    window.checkTextSchema() && window.checkTextUser()) {
                window.activarBotonesBBDD(true);
            } else {
                window.activarBotonesBBDD(false);
            }
        }        
        
    }
    
}
