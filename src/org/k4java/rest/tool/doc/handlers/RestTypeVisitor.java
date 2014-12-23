package org.k4java.rest.tool.doc.handlers;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.k4java.rest.tool.doc.handlers.config.Doc4RestConfiguration;

public class RestTypeVisitor extends ASTVisitor{
	private Doc4RestConfiguration config = Doc4RestConfiguration.getInstance();
	private TypeDeclaration type;
	@Override
	public boolean visit(TypeDeclaration node) {
		if(node.isInterface() && config.isInterfaceScanRequired())
		{
			this.type = node;
		}
		return super.visit(node);
	}
	public TypeDeclaration getType() {
		return type;
	}
	
}
