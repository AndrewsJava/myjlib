package harlequinmettle.utils.filetools;

public interface SerializationMechanismInterface {
public  <T> T deserialize(Class<T> type,String identifier) ;
public boolean serialize(Object obj,String identifier);
}
