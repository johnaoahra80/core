/* 
 * JBoss, Home of Professional Open Source 
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved. 
 * See the copyright.txt in the distribution for a 
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use, 
 * modify, copy, or redistribute it subject to the terms and conditions 
 * of the GNU Lesser General Public License, v. 2.1. 
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details. 
 * You should have received a copy of the GNU Lesser General Public License, 
 * v.2.1 along with this distribution; if not, write to the Free Software 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 */
package org.switchyard.security.jboss.callback;

import java.io.IOException;
import java.util.Set;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.picketlink.identity.federation.core.wstrust.auth.TokenCallback;
import org.switchyard.security.callback.SwitchYardCallbackHandler;
import org.switchyard.security.credential.AssertionCredential;
import org.switchyard.security.credential.Credential;

/**
 * STSTokenCallbackHandler.
 *
 * @author David Ward &lt;<a href="mailto:dward@jboss.org">dward@jboss.org</a>&gt; &copy; 2012 Red Hat Inc.
 */
public class STSTokenCallbackHandler extends SwitchYardCallbackHandler {

    /**
     * Constructs a new STSTokenCallbackHandler.
     */
    public STSTokenCallbackHandler() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        Set<Credential> credentials = getCredentials();
        if (credentials == null) {
            throw new IllegalStateException("Credentials not set");
        }
        for (Callback cb : callbacks) {
            if (cb instanceof TokenCallback) {
                for (Credential cred : credentials) {
                    if (cred instanceof AssertionCredential) {
                        ((TokenCallback)cb).setToken(((AssertionCredential)cred).getAssertion());
                    }
                }
            }
        }
    }

}
