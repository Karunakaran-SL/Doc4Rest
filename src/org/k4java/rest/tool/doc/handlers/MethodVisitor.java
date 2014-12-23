package org.k4java.rest.tool.doc.handlers;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodVisitor extends ASTVisitor{
	private MethodDeclaration method;

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodDeclaration)
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		method = node;
		return super.visit(node);
	}
	
	

	/**
	 * @return the methods
	 */
	public MethodDeclaration getMethod() {
		return method;
	}
	
	
}
