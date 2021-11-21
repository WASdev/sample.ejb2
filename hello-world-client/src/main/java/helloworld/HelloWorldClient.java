/**
 * (C) Copyright Microsoft Corporation 2021.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package helloworld;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public class HelloWorldClient {

	public static void main(String[] args) {

		try {
			InitialContext context = new InitialContext();

			Object foundUsingGlobal = context
					.lookup("java:global/hello-world/hello-world-ejb/HelloWorld!helloworld.HelloWorldHome");
			Object foundUsingApp = context.lookup("java:app/hello-world-ejb/HelloWorld!helloworld.HelloWorldHome");
			Object foundUsingCorba = context.lookup(
					"corbaname::localhost:2809#ejb/global/hello-world/hello-world-ejb/HelloWorld!helloworld%5c.HelloWorldHome");
			Object foundUsingRef = context.lookup("java:comp/env/ejb/session/HelloWorld");

			HelloWorldHome helloWorldHomeUsingGlobal = (HelloWorldHome) PortableRemoteObject.narrow(foundUsingGlobal,
					HelloWorldHome.class);
			HelloWorldHome helloWorldHomeUsingApp = (HelloWorldHome) PortableRemoteObject.narrow(foundUsingApp,
					HelloWorldHome.class);
			HelloWorldHome helloWorldHomeUsingCorba = (HelloWorldHome) PortableRemoteObject.narrow(foundUsingCorba,
					HelloWorldHome.class);
			HelloWorldHome helloWorldHomeUsingRef = (HelloWorldHome) PortableRemoteObject.narrow(foundUsingRef,
					HelloWorldHome.class);

			System.out.println(helloWorldHomeUsingGlobal.create().helloWorld("Access EJB using java:global"));
			System.out.println(helloWorldHomeUsingApp.create().helloWorld("Access EJB using java:app"));
			System.out.println(helloWorldHomeUsingCorba.create().helloWorld("Access EJB using corbaname:"));
			System.out.println(helloWorldHomeUsingRef.create().helloWorld("Access EJB using java:comp/env"));
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		}
	}
}
