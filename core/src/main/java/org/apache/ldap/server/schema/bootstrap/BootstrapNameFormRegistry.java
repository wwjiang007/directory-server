/*
 *   Copyright 2004 The Apache Software Foundation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.apache.ldap.server.schema.bootstrap;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.ldap.common.schema.NameForm;
import org.apache.ldap.common.util.StringTools;
import org.apache.ldap.server.schema.NameFormRegistry;
import org.apache.ldap.server.schema.OidRegistry;


/**
 * A plain old java object implementation of an NameFormRegistry.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$
 */
public class BootstrapNameFormRegistry implements NameFormRegistry
{
    /** maps an OID to an NameForm */
    private final Map byOid;

    /** maps an OID to a schema name*/
    private final Map oidToSchema;
    
    /** the registry used to resolve names to OIDs */
    private final OidRegistry oidRegistry;


    // ------------------------------------------------------------------------
    // C O N S T R U C T O R S
    // ------------------------------------------------------------------------


    /**
     * Creates an empty BootstrapNameFormRegistry.
     */
    public BootstrapNameFormRegistry( OidRegistry oidRegistry )
    {
        this.byOid = new HashMap();
        this.oidToSchema = new HashMap();
        this.oidRegistry = oidRegistry;
    }

    // ------------------------------------------------------------------------
    // Service Methods
    // ------------------------------------------------------------------------

    public void register( String schema, NameForm nameForm ) throws NamingException
    {
        if ( byOid.containsKey( nameForm.getOid() ) )
        {
            NamingException e = new NamingException( "nameForm w/ OID " +
                nameForm.getOid() + " has already been registered!" );
            throw e;
        }

        oidToSchema.put( nameForm.getOid(), schema );
        oidRegistry.register( nameForm.getName(), nameForm.getOid() );
        byOid.put( nameForm.getOid(), nameForm );
    }


    public NameForm lookup( String id ) throws NamingException
    {
        id = oidRegistry.getOid( id );

        if ( ! byOid.containsKey( id ) )
        {
            NamingException e = new NamingException( "nameForm w/ OID " + id
                + " not registered!" );
            throw e;
        }

        NameForm nameForm = ( NameForm ) byOid.get( id );
        return nameForm;
    }


    public boolean hasNameForm( String id )
    {
        if ( oidRegistry.hasOid( id ) )
        {
            try
            {
                return byOid.containsKey( oidRegistry.getOid( id ) );
            }
            catch ( NamingException e )
            {
                return false;
            }
        }

        return false;
    }


    public String getSchemaName( String id ) throws NamingException
    {
        id = oidRegistry.getOid( id );
        if ( oidToSchema.containsKey( id ) )
        {
            return ( String ) oidToSchema.get( id );
        }

        throw new NamingException( "OID " + id + " not found in oid to " +
            "schema name map!" );
    }


    public Iterator list()
    {
        return byOid.values().iterator();
    }
    
    /**
     * A String representation of this class
     */
    public String toString( String tabs )
    {
    	StringBuffer sb = new StringBuffer();
    	
    	sb.append( tabs ).append( "BootstrapNameFormRegistry : {\n" );
    	
    	sb.append( tabs ).append(  "  By oid : \n" );
    	
    	sb.append( tabs ).append(  StringTools.mapToString( byOid, "    " ) ) .append( '\n' );
    	
    	sb.append( tabs ).append(  "  Oid to schema : \n" );

    	sb.append( tabs ).append(  StringTools.mapToString( oidToSchema, "    " ) ) .append( '\n' );
    	
    	sb.append( tabs ).append(  "  OidRegistry :\n" );
    	
    	sb.append( oidRegistry.toString( tabs + "    " ) );
    	
    	sb.append( tabs ).append(  "}\n" );
    	
    	return sb.toString();
    }

    /**
     * A String representation of this class
     */
    public String toString()
    {
    	return toString( "" );
    }
}
