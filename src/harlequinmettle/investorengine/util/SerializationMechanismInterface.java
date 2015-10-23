package harlequinmettle.investorengine.util;

public interface SerializationMechanismInterface {
public  <T> T deserialize(Class<T> type,String identifier) ;
public boolean serialize(Object obj,String identifier);
}
