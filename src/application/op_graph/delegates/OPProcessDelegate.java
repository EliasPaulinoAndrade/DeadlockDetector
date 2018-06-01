package application.op_graph.delegates;

import application.op_graph.OPProcess;
import application.op_graph.OPProcessNode;
import application.op_graph.OPResource;
import application.op_graph.OPResourceNode;

/*classes that implement this will be responsible for handling changes when events occur in the process*/
public interface OPProcessDelegate {
	void processWillClaimResource(OPProcessNode<OPProcess> processNode, OPResourceNode<OPResource> resourceNode);
	void processDidAcquireResource(OPProcessNode<OPProcess> processNode, OPResourceNode<OPResource> resourceNode);
	void processNeedReleaseResource(OPProcessNode<OPProcess> processNode, OPResourceNode<OPResource> resourceNode);
	void processWasSetToDie(OPProcessNode<OPProcess> processNode);
}
