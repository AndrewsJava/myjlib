package harlequinmettle.utils.filetools;


public class SerializationToolExplicit {

	
//	// /////////// ////////////////////////////
//	//should check for null return value; it is possible
//	public static   TreeMap<String, TreeMap<String, Float>>  deserializeCurrentFundamentalsDatabase( String path) {
//		 TreeMap<String, TreeMap<String, Float>>  forRestore = null;
//		try {
//			FileInputStream filein = new FileInputStream(path);
//			ObjectInputStream objin = new ObjectInputStream(filein);
//			try {
//				forRestore = ( TreeMap<String, TreeMap<String, Float>>)  (objin.readObject());
//			} catch (ClassCastException cce) {
//				System.out.println("CLASSCASTEXCEPTION");
//			}
//			objin.close();
//		} catch (Exception e) {
//			System.out.println("NO resume: saver");
//			e.printStackTrace();
//		} 
//	 
//		return  (forRestore);
//	} //
}
