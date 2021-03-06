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

public class NetworkException extends Exception
{
	private static final long serialVersionUID = 4067012136555907717L;
	

	public NetworkException(String errorMsg)
	{
		super(errorMsg);
	}
	
	public NetworkException(String errorMsg, Throwable cause)
	{
		super(errorMsg, cause);
	}

	public NetworkException(Object object, String errorMsg)
	{
		super(object.toString() +" - " +errorMsg);
	}
	
	public NetworkException(Object object, String errorMsg, Throwable cause)
	{
		super(object.toString() +" - " +errorMsg, cause);
	}
}
