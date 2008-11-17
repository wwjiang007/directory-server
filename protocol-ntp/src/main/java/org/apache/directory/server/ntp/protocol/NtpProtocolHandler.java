/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */

package org.apache.directory.server.ntp.protocol;


import org.apache.directory.server.ntp.NtpService;
import org.apache.directory.server.ntp.messages.NtpMessage;
import org.apache.directory.server.ntp.service.NtpServiceImpl;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$, $Date$
 */
public class NtpProtocolHandler extends IoHandlerAdapter
{
    /** the log for this class */
    private static final Logger log = LoggerFactory.getLogger( NtpProtocolHandler.class );

    private NtpService ntpService = new NtpServiceImpl();


    /**
     * {@inheritDoc}
     */
    public void sessionCreated( IoSession session ) throws Exception
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "{} CREATED", session.getRemoteAddress() );
        }

        // Inject the Codec filter in the chain 
        //session.getFilterChain().addFirst( "codec", new ProtocolCodecFilter( NtpProtocolCodecFactory.getInstance() ) );
    }


    /**
     * {@inheritDoc}
     *
    public void sessionOpened( IoSession session )
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "{} OPENED", session.getRemoteAddress() );
        }
    }


    /**
     * {@inheritDoc}
     */
    public void sessionClosed( IoSession session )
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "{} CLOSED", session.getRemoteAddress() );
        }
    }


    /**
     * {@inheritDoc}
     *
    public void sessionIdle( IoSession session, IdleStatus status )
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "{} IDLE ({})", session.getRemoteAddress(), status );
        }
    }


    /**
     * {@inheritDoc}
     */
    public void exceptionCaught( IoSession session, Throwable cause )
    {
        log.error( session.getRemoteAddress() + " EXCEPTION", cause );
        session.close( true );
    }


    /**
     * {@inheritDoc}
     */
    public void messageReceived( IoSession session, Object message )
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "{} RCVD:  {}", session.getRemoteAddress(), message );
        }

        NtpMessage reply = ntpService.getReplyFor( ( NtpMessage ) message );

        session.write( reply );
    }


    /**
     * {@inheritDoc}
     *
    public void messageSent( IoSession session, Object message )
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "{} SENT:  {}", session.getRemoteAddress(), message );
        }
    }*/
}
