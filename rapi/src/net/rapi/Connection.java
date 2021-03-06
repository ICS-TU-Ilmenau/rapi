/******************************************************************************
 * Recursive API
 * Copyright 2013 Integrated Communication Systems Group, TU Ilmenau.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************/
package net.rapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.LinkedList;


/**
 * A connection represents the possibility of two or more peers to exchange
 * data. It can be set up via a layer with the method {@link Layer#connect}.
 * Depending on the requirements used to set up the connection, the features
 * of a connection differ. The features may include (but are not limited to)
 * encryption, in-order data delivery and error-free transmission.
 * 
 * Most important are the methods for data exchange ({@link #read}/
 * {@link #write}) and for terminating a connection.
 */
public interface Connection extends EventSource
{
	/**
	 * Has to be called by a higher layer in order to accept or reestablish
	 * a connection. In particular, it has to be called for incoming
	 * connections received from {@link Binding#getIncomingConnection()}.
	 * If a connection is already established or if the process for its
	 * creation is running, the call is ignored.
	 */
	public void connect();
	
	/**
	 * Check status of the socket.
	 * 
	 * @return If sendData can be called without errors
	 */
	public boolean isConnected();
	
	/**
	 * @return The name of the binding the connection was established to. 
	 */
	public Name getBindingName();
	
	/**
	 * Signatures may be provided by remote peer(s) to verify their authenticity.
	 * Whether such signatures are available depend on the remote peer and which
	 * parameter it used for its call to {@link Layer#connect}. 
	 * 
	 * @return List of signatures ({@code != null}). If no signatures are available an empty list is returned.
	 */
	public LinkedList<Signature> getAuthentications();
	
	/**
	 * The requirements for a connection are influenced by the requirements of a {@link Binding},
	 * the requirements of the {@link Layer#connect} call, and further requirements added by the
	 * layer management.
	 *  
	 * @return Set of requirements used for this connection ({@code != null})
	 */
	public Description getRequirements();
	
	/**
	 * Sends data through the layer to all peers of the connection.
	 * The method blocks as long as required to access the buffer of the send
	 * data. Depending on the implementation, the method may return after
	 * copying the data to a buffer, to wait until such a buffer is free, or
	 * to wait until the data was send. However, it never blocks until an
	 * acknowledgment is received. 
	 * 
	 * @param data Data to send
	 * @throws NetworkException On error during sending (e.g. connection is closed)
	 */
	public void write(Serializable data) throws NetworkException;
	
	/**
	 * Called by application in order to get new data received by this
	 * socket. This method does not block and returns {@code null} if
	 * no data is available.
	 * 
	 * @return Received data object
	 * @throws NetworkException On error (e.g. connection is closed)
	 */
	public Object read() throws NetworkException;
	
	/**
	 * @return Number of available bytes (if stream is used) or objects (if read is used)
	 */
	public int available();

	/**
	 * Opens output stream for sending data to all peers of a connection.
	 * Stream converts the data send through the stream to calls to {@link #write}.
	 * 
	 * @return Reference to output stream for sending data 
	 * @throws IOException On error
	 */
	public OutputStream getOutputStream() throws IOException;
	
	/**
	 * Opens input stream for receiving data from all peers of a connection.
	 * In contrast to {@link #read} the stream allows to receive bytes and not
	 * complete Java objects.
	 * 
	 * @return Reference to input stream for receiving data
	 * @throws IOException On error
	 */
	public InputStream getInputStream() throws IOException;
	
	/**
	 * Terminates the possibility to exchange data via this connection.
	 * If the connection is closed at the other peers depend on the
	 * requirements of a connection and the number of peers.
	 * The method does not block.
	 */
	public void close();
}
