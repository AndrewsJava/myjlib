package harlequinmettle.utils.filetools;
 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationTool {
  
	// /////////// ////////////////////////////
	//should check for null return value; it is possible
	public static <T> T  deserialize(Class<T> cls,String path) {
		 T   forRestore = null;
		try {
			FileInputStream filein = new FileInputStream(path);
			ObjectInputStream objin = new ObjectInputStream(filein);
			try {
				forRestore =  cls.cast(objin.readObject());
			} catch (ClassCastException cce) {
				System.out.println("CLASSCASTEXCEPTION");
			}
			objin.close();
		} catch (Exception e) {
			System.out.println("NO resume: saver");
			e.printStackTrace();
		} 
	 
		return  (forRestore);
	} //

	public static void serialize(Object ob, String obFileName) {
		System.out.println("memorizing object ... ");
 
		try {
			FileOutputStream fileout = new FileOutputStream(obFileName);

			ObjectOutputStream objout = new ObjectOutputStream(fileout);
			objout.writeObject(ob);
			objout.flush();
			objout.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("UNABLE TO SAVE OBJECT TO: " + obFileName);
		}
		System.out.println("done memorizing object to: " + obFileName);
	}

}
