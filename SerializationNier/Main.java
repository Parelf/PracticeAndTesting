import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {

//		YoRHaUnit unit2B = new YoRHaUnit("2B", 99, new ArrayList<>(List.of(
//				"Virtuous Contract", "Virtuous Treaty")), "Alpha-555");

		YoRHaUnit unit2BV2 = new YoRHaUnit("2B", 99, new ArrayList<>(List.of(
				"Virtuous Contract", "Virtuous Treaty")), "Alpha-555", "Hacking");

		String filename = "yoRHaUnit.dat";
		String filenameV2 = "yoRHaUnitV2.dat";

		// Serialize
//		serializeYoRHaUnit(unit2B,filename);
		serializeYoRHaUnit(unit2BV2, filenameV2);

		System.out.println("-".repeat(30));

		// Deserialize
		YoRHaUnit loadedUnit = deSerializeYoRHaUnit(filename);
		System.out.println("-".repeat(30));
		YoRHaUnit loadedUnit2 = deSerializeYoRHaUnit(filenameV2);
	}

	public static void serializeYoRHaUnit(YoRHaUnit unit, String pathName){

		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pathName))) {
			out.writeObject(unit);
			System.out.println("Serialized: " + unit);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static YoRHaUnit deSerializeYoRHaUnit(String pathName){
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(pathName))) {
			YoRHaUnit loadedUnit = (YoRHaUnit) in.readObject();
			System.out.println("Deserialized: " + loadedUnit);
			return loadedUnit;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Unable to return loadedUnit. Aborting...");
		return null;
	}

}

