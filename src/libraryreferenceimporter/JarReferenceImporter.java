package libraryreferenceimporter;

import org.apache.commons.collections4.queue.CircularFifoQueue;

public class JarReferenceImporter {

	private static final int BOUNDARY_SIZE = 10;
	CircularFifoQueue<Float> averageingQueue = new CircularFifoQueue<Float>(BOUNDARY_SIZE);
}
