package harlequinmettle.utils.filetools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationTool implements SerializationMechanismInterface {

	// /////////// ////////////////////////////
	// should check for null return value; it is possible
	@Override
	public <T> T deserialize(Class<T> cls, String path) {
		T forRestore = null;
		try {
			FileInputStream filein = new FileInputStream(path);
			ObjectInputStream objin = new ObjectInputStream(filein);
			try {
				forRestore = cls.cast(objin.readObject());
			} catch (ClassCastException cce) {
				System.out.println("CLASSCASTEXCEPTION");
			}
			objin.close();
		} catch (Exception e) {
			System.out.println("NO resume: saver "+path);
		  e.printStackTrace();
			System.out.println("NO resume: saver "+path);
		}

		return (forRestore);
	} //
		// /////////// ////////////////////////////
		// should check for null return value; it is possible

	public static <T> T deserializeObject(Class<T> cls, String path) {
		T forRestore = null;
		try {
			FileInputStream filein = new FileInputStream(path);
			ObjectInputStream objin = new ObjectInputStream(filein);
			try {
				forRestore = cls.cast(objin.readObject());
			} catch (ClassCastException cce) {
				System.out.println("CLASSCASTEXCEPTION");
			}
			objin.close();
		} catch (Exception e) {
			System.out.println("NO resume: saver");
			// e.printStackTrace();
		}

		return (forRestore);
	} //

	@Override
	public boolean serialize(Object ob, String obFileName) {
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
			return false;
		}
		System.out.println("done memorizing object to: " + obFileName);
		return true;
	}

	public static void serializeObject(Object ob, String obFileName) {
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
