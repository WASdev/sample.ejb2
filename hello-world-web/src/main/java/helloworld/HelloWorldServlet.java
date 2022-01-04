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

import java.io.IOException;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HelloWorldServlet", urlPatterns = { "/" })
public class HelloWorldServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	HelloWorldHome helloWorldHome;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		ServletOutputStream out = response.getOutputStream();
		
		try {
			InitialContext context = new InitialContext();

			Object foundUsingGlobal = context
					.lookup("java:global/hello-world/hello-world-ejb/HelloWorld!helloworld.HelloWorldHome");
			Object foundUsingApp = context.lookup("java:app/hello-world-ejb/HelloWorld!helloworld.HelloWorldHome");
			Object foundUsingWebSphere = context.lookup("ejb/session/HelloWorld");

			HelloWorldHome helloWorldHomeUsingGlobal = (HelloWorldHome) PortableRemoteObject.narrow(foundUsingGlobal,
					HelloWorldHome.class);
			HelloWorldHome helloWorldHomeUsingApp = (HelloWorldHome) PortableRemoteObject.narrow(foundUsingApp,
					HelloWorldHome.class);
			HelloWorldHome helloWorldHomeUsingWebSphere = (HelloWorldHome) PortableRemoteObject
					.narrow(foundUsingWebSphere, HelloWorldHome.class);

			out.println(helloWorldHomeUsingGlobal.create().helloWorld("Access EJB using java:global"));
			out.println(helloWorldHomeUsingApp.create().helloWorld("Access EJB using java:app"));
			out.println(helloWorldHomeUsingWebSphere.create().helloWorld("Access EJB using ejb/"));
			out.println(helloWorldHome.create().helloWorld("Access EJB using @EJB"));
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		}
	}
}
