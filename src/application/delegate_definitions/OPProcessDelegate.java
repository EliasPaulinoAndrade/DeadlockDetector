package application.delegate_definitions;

import application.op_graph.OPProcess;
import application.op_graph.OPProcessNode;
import application.op_graph.OPResource;
import application.op_graph.OPResourceNode;

public interface OPProcessDelegate {
	void processWillClaimResource(OPProcessNode<OPProcess> processNode, OPResourceNode<OPResource> resourceNode);
	void processDidAcquireResource(OPProcessNode<OPProcess> processNode, OPResourceNode<OPResource> resourceNode);
	void processNeedReleaseResource(OPProcessNode<OPProcess> processNode, OPResourceNode<OPResource> resourceNode);
}
